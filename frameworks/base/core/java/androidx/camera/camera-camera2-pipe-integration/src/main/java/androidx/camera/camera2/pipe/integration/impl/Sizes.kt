/*
 * Copyright 2020 The Android Open Source Project
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

@file:RequiresApi(21) // TODO(b/200306659): Remove and replace with annotation on package-info.java

package androidx.camera.camera2.pipe.integration.impl

import android.graphics.Point
import android.util.Size
import androidx.annotation.RequiresApi

fun Size.area(): Int = this.width * this.height
fun Size.asLandscape(): Size =
    if (this.width >= this.height) this else Size(this.height, this.width)
fun Size.asPortrait(): Size =
    if (this.width <= this.height) this else Size(this.height, this.width)

fun minByArea(left: Size, right: Size) = if (left.area() < right.area()) left else right
fun maxByArea(left: Size, right: Size) = if (left.area() > right.area()) left else right

fun Point.area(): Int = this.x * this.y
fun Point.toSize() = Size(this.x, this.y)