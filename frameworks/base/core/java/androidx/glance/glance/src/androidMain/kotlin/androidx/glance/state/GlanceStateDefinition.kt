/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.glance.state

import android.content.Context
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

/**
 * Configuration definition for [GlanceState]. This defines where the data is stored and how the
 * underlying data store is created. Use a unique [GlanceStateDefinition] to get a [GlanceState], once
 * defined, the data should be updated using the state directly, this definition should not change.
 */
public interface GlanceStateDefinition<T> {

    /**
     * This file indicates the location of the persisted data.
     *
     * @param context The context used to create the file directory
     * @param fileKey The unique string key used to name and identify the data file corresponding to
     * a remote UI. Each remote UI has a unique UI key, used to key the data for that UI.
     */
    public fun getLocation(context: Context, fileKey: String): File

    /**
     * Creates the underlying data store.
     *
     * @param context The context used to create locate the file directory
     * @param fileKey The unique string key used to name and identify the data file corresponding to
     * a remote UI. Each remote UI has a unique UI key, used to key the data for that UI.
     */
    public suspend fun <T> getDataStore(context: Context, fileKey: String): DataStore<T>
}

/**
 * Data store for data specific to the glanceable view. Stored data should include information
 * relevant to the representation of views, but not surface specific view data. For example, the
 * month displayed on a calendar rather than actual calendar entries.
 */
public object GlanceState {
    // TODO(b/205496180): Make methods internal
    /**
     * Returns the stored data associated with the given UI key string.
     *
     * @param definition the configuration that defines this state.
     * @param fileName identifies the data file associated with the store, must be unique for any
     * remote UI in the app.
     */
    public suspend fun <T> getValue(
        context: Context,
        definition: GlanceStateDefinition<T>,
        fileName: String
    ): T = getDataStore(context, definition, fileName).data.first()

    /**
     * Updates the underlying data by applying the provided update block.
     *
     * @param definition the configuration that defines this state.
     * @param fileName identifies the date file associated with the store, must be unique for any
     * remote UI in the app.
     */
    public suspend fun <T> updateValue(
        context: Context,
        definition: GlanceStateDefinition<T>,
        fileName: String,
        updateBlock: suspend (T) -> T
    ) = getDataStore(context, definition, fileName).updateData(updateBlock)

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> getDataStore(
        context: Context,
        definition: GlanceStateDefinition<T>,
        fileName: String
    ): DataStore<T> =
        mutex.withLock {
            dataStores.getOrPut(fileName) {
                definition.getDataStore<T>(context, fileName)
            } as DataStore<T>
        }

    private val mutex = Mutex()

    // TODO: Move to a single, global source to manage the data lifecycle
    private val dataStores: MutableMap<String, DataStore<*>> = mutableMapOf()
}
