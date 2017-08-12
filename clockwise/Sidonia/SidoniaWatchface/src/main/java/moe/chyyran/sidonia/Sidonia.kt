/*
 * Copyright (C) 2014 The Android Open Source Project
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

package moe.chyyran.sidonia

import android.graphics.Canvas
import android.graphics.Color
import com.ustwo.clockwise.WatchFace
import moe.chyyran.sidonia.Drawables.*

/**
 * Digital watch face with seconds. In ambient mode, the seconds aren't displayed. On devices with
 * low-bit ambient mode, the text is drawn without anti-aliasing in ambient mode.
 */
class Sidonia : WatchFace() {
    internal lateinit var mGrid: GridDrawable
    internal lateinit var mOverlay: OverlayDrawable
    internal lateinit var mTime: TimeDrawable
    internal lateinit var mStatus: StatusDrawable
    internal lateinit var mBattery: BatteryDrawable
    override fun onCreate() {
        mGrid = GridDrawable(this)
        mOverlay = OverlayDrawable(this)
        mTime = TimeDrawable(this)
        mStatus = StatusDrawable(this)
        mBattery = BatteryDrawable(this)
        super.onCreate()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(Color.BLACK)
        mBattery.drawBatteryPercent(canvas, 100, false)
        mTime.drawTime(canvas, this.time)
        mGrid.drawGrid(canvas)
        mStatus.drawWeekDay(canvas, this.time)
        mStatus.drawDate(canvas, this.time)
        mOverlay.drawOverlay(canvas)

    }

    // todo: Update refresh rate?
    override fun getInteractiveModeUpdateRate(): Long {
        return super.getInteractiveModeUpdateRate()
    }
}
