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

package jp.nokkii.apps.faceofsidonia;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.text.format.Time;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class FaceOfSidonia extends CanvasWatchFaceService {
    private static final Typeface NORMAL_TYPEFACE =
            Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);

    /**
     * Update rate in milliseconds for interactive mode. We update once a second since seconds are
     * displayed in interactive mode.
     */
    private static final long INTERACTIVE_UPDATE_RATE_MS = TimeUnit.SECONDS.toMillis(1);

    @Override
    public Engine onCreateEngine() {
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine {
        static final int MSG_UPDATE_TIME = 0;
        static final int STATUS_UPDATE_TIME = 10;
        static final boolean SENSING_START = true;
        static final boolean SENSING_END = false;

        int ANIMATION_DELAY;
        int STATUS_WAIT_SEC;

        /**
         * Handler to update the time periodically in interactive mode.
         */
        final Handler mUpdateTimeHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case MSG_UPDATE_TIME:
                        invalidate();
                        if (shouldTimerBeRunning()) {
                            long timeMs = System.currentTimeMillis();
                            long delayMs = INTERACTIVE_UPDATE_RATE_MS
                                    - (timeMs % INTERACTIVE_UPDATE_RATE_MS);
                            mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                        }
                        break;
                }

                if ( 0 < mStatusTick ) mStatusTick--;
                if (!mAmbient)
                    mUpdateWaitCount = mUpdateWaitCount >= STATUS_UPDATE_TIME ? 0 : mUpdateWaitCount + 1;
                if (mAmbient || !mAmbient && 1 == mUpdateWaitCount){
                    updateBatteryStatus();
                }
            }
        };

        final BroadcastReceiver mTimeZoneReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTime.clear(intent.getStringExtra("time-zone"));
                mTime.setToNow();
            }
        };

        boolean mRegisteredTimeZoneReceiver = false;
        boolean mAmbient;
        boolean mLowBitAmbient;
        boolean isCharging = false;
        boolean isSensing = false;

        int mDrawCenterStatus;
        int mStatusTick = 0;
        int mUpdateWaitCount = 0;
        int mBatteryPct = 0;

        Time mTime;
        Bitmap mWallpaper;
        private SensorManager mSensorManager;

        Background  mBackground;
        Status      mStatus;
        CenterPoint mCenter;
        LeftSide    mLeft;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

            setWatchFaceStyle(new WatchFaceStyle.Builder(FaceOfSidonia.this)
                    .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
                    .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
                    .setShowSystemUiTime(false)
                    .build());

            Resources resources = getResources();

            ANIMATION_DELAY = resources.getInteger(R.integer.animation_delay);
            STATUS_WAIT_SEC = resources.getInteger(R.integer.status_wait_sec);
            mWallpaper  = BitmapFactory.decodeResource(resources, R.mipmap.sidonia);

            mCenter     = new CenterPoint(FaceOfSidonia.this, this.getDesiredMinimumWidth());
            mStatus     = new Status(FaceOfSidonia.this, this.getDesiredMinimumWidth());
            mLeft       = new LeftSide(FaceOfSidonia.this, this.getDesiredMinimumWidth());
            mBackground = new Background(FaceOfSidonia.this, this.getDesiredMinimumWidth());
            mTime       = new Time();

            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            SensorListenerSwitch(SENSING_START);
            updateBatteryStatus();
        }

        @Override
        public void onDestroy() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            SensorListenerSwitch(SENSING_END);
            super.onDestroy();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);

            if (visible) {
                registerReceiver();
                // Update time zone in case it changed while we weren't visible.
                mTime.clear(TimeZone.getDefault().getID());
                mTime.setToNow();
            } else {
                unregisterReceiver();
            }
            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();
        }

        private void registerReceiver() {
            if (mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            FaceOfSidonia.this.registerReceiver(mTimeZoneReceiver, filter);
        }

        private void unregisterReceiver() {
            if (!mRegisteredTimeZoneReceiver) {
                return;
            }
            mRegisteredTimeZoneReceiver = false;
            FaceOfSidonia.this.unregisterReceiver(mTimeZoneReceiver);
        }

        private void SensorListenerSwitch(boolean on){
            mDrawCenterStatus = -STATUS_WAIT_SEC;
            if ( on ){
                if ( isSensing ) return;
                List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
                if(sensors.size() > 0) {
                    Sensor s = sensors.get(0);
                    mSensorManager.registerListener(mSensorEventListener, s, SensorManager.SENSOR_DELAY_UI);
                }
                isSensing = true;
            } else {
                if ( !isSensing ) return;
                isSensing = false;
                mSensorManager.unregisterListener(mSensorEventListener);
            }
        }

        private void updateBatteryStatus(){
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);
            int status = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1) : 0;
            int level  = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)  : 0;
            int scale  = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1)  : 0;

            mBatteryPct = ( level * 100 ) / scale;
            isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
        }

        @Override
        public void onApplyWindowInsets(WindowInsets insets) {
            super.onApplyWindowInsets(insets);
            // Load resources that have alternate values for round watches.
            // Resources resources = FaceOfSidonia.this.getResources();
            // boolean isRound = insets.isRound();
        }

        @Override
        public void onPropertiesChanged(Bundle properties) {
            super.onPropertiesChanged(properties);
            mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (mAmbient != inAmbientMode) {
                mAmbient = inAmbientMode;
                mUpdateWaitCount = 0;
                mStatusTick = 0;
                if (mLowBitAmbient) {
                    mBackground.setAntiAlias(!inAmbientMode);
                    mCenter.setAntiAlias(!inAmbientMode);
                    mStatus.setAntiAlias(!inAmbientMode);
                    mLeft.setAntiAlias(!inAmbientMode);
                }
                if (mAmbient)
                    SensorListenerSwitch(SENSING_END);
                else {
                    SensorListenerSwitch(SENSING_START);
                }
                invalidate();
            }

            // Whether the timer should be running depends on whether we're visible (as well as
            // whether we're in ambient mode), so we may need to start or stop the timer.
            updateTimer();
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            // Draw H:MM in ambient mode or H:MM:SS in interactive mode.
            //canvas.drawBitmap(mWallpaper, 0, 0, null);
            //if ( 1 > mDrawCenterStatus ) {
                canvas.drawBitmap(mWallpaper,
                        new Rect(0, 0, mWallpaper.getWidth(), mWallpaper.getHeight()),
                        new RectF(0, 0, bounds.width(), bounds.height()),
                        null
                );
                mTime.setToNow();
                mBackground.drawBackground(canvas);
                mStatus.drawWeekDay(canvas, mTime);
                //mStatus.drawTime(canvas, mTime);
                mStatus.drawDate(canvas, mTime);
                mCenter.drawTime(canvas, mTime);
                mLeft.drawBatteryPct(canvas, mBatteryPct, mAmbient, isCharging);
            //}
            if ( -STATUS_WAIT_SEC < mDrawCenterStatus ) {
                if ( mCenter.drawStatus(canvas, mDrawCenterStatus, mBatteryPct) ) {
                    mDrawCenterStatus--;
                    sleep(ANIMATION_DELAY);
                    invalidate();
                    updateTimer();
                } else {
                    mDrawCenterStatus--;
                }
            }
        }

        public synchronized void sleep(long msec)
        {
            try
            {
                wait(msec);
            }catch(InterruptedException e){}
        }

        /**
         * Starts the {@link #mUpdateTimeHandler} timer if it should be running and isn't currently
         * or stops it if it shouldn't be running but currently is.
         */
        private void updateTimer() {
            mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        /**
         * Returns whether the {@link #mUpdateTimeHandler} timer should be running. The timer should
         * only run when we're visible and in interactive mode.
         */
        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }

        private final SensorEventListener mSensorEventListener =
                new SensorEventListener() {
                    // ローパスフィルタ用変数
                    private float lowX;
                    // private float lowY;
                    // private float lowZ;

                    private static final float FILTERING_VALUE = 0.2f;
                    private static final float SLIDE_IN_X = -5.5f;

                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        lowX = getLowPassFilterValue(event.values[0], lowX);
                        // lowY = getLowPassFilterValue(event.values[1], lowY);
                        // lowZ = getLowPassFilterValue(event.values[2], lowZ);

                        if ( lowX < SLIDE_IN_X && mDrawCenterStatus < 0)
                            mDrawCenterStatus = mDrawCenterStatus <= -STATUS_WAIT_SEC ? 4 : 0;
                    }

                    float getLowPassFilterValue(float eventValue, float lowValue) {
                        return eventValue * FILTERING_VALUE + lowValue
                                * (1.0f - FILTERING_VALUE);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };
    }

    public Paint createTextPaint(int textColor) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setTypeface(NORMAL_TYPEFACE);
        paint.setAntiAlias(true);
        return paint;
    }
}
