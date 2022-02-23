/*
 * Copyright 2018 The Android Open Source Project
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

package androidx.webkit;

import androidx.annotation.Nullable;

/**
 * The Java representation of the HTML5 PostMessage event. See
 * https://html.spec.whatwg.org/multipage/comms.html#the-messageevent-interfaces
 * for definition of a MessageEvent in HTML5.
 */
public class WebMessageCompat {

    private String mData;
    private WebMessagePortCompat[] mPorts;

    /**
     * Creates a WebMessage.
     * @param data  the data of the message.
     */
    public WebMessageCompat(@Nullable String data) {
        mData = data;
    }

    /**
     * Creates a WebMessage.
     * @param data  the data of the message.
     * @param ports  the ports that are sent with the message.
     */
    public WebMessageCompat(@Nullable String data, @Nullable WebMessagePortCompat[] ports) {
        mData = data;
        mPorts = ports;
    }

    /**
     * Returns the data of the message.
     */
    public @Nullable String getData() {
        return mData;
    }

    /**
     * Returns the ports that are sent with the message, or {@code null} if no port
     * is sent.
     */
    @Nullable
    public WebMessagePortCompat[] getPorts() {
        return mPorts;
    }
}
