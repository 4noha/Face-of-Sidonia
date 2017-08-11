package moe.chyyran.sidonia.Drawables

import android.app.WallpaperManager
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface

import com.ustwo.clockwise.WatchFace

import moe.chyyran.sidonia.R

class BatteryDrawable(watch: WatchFace) {
    internal var mFillPaint: Paint
    internal var mNumTextPaint: Paint
    internal var mTextPaint: Paint

    internal var mBackgroundColor: Int = 0
    internal var mRoundColor: Int = 0
    internal var mChargingColor: Int = 0
    internal var mRoundBlockWidth: Float = 0.toFloat()
    internal var mLineOffset: Float = 0.toFloat()
    internal var mTopBlockOffset: PointF
    internal var mTopTextPoint: PointF
    internal var mTopNumPoint: PointF

    init {
        val resources = watch.resources
        val desiredMinimumWidth = WallpaperManager.getInstance(watch).desiredMinimumWidth

        val numTextSize = desiredMinimumWidth / 5.5f
        val textSize = desiredMinimumWidth / 5.5f
        var typeface = Typeface.createFromAsset(watch.assets, "Browning.ttf")

        // 塗りつぶし用
        mFillPaint = Paint()
        mLineOffset = desiredMinimumWidth / 80f
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f
        mTopBlockOffset = PointF(mLineOffset, mRoundBlockWidth + mLineOffset)

        mRoundColor = resources.getColor(R.color.round)
        mFillPaint.color = mRoundColor
        mFillPaint.isAntiAlias = true

        // 数字用
        mNumTextPaint = Paint()
        mBackgroundColor = resources.getColor(R.color.digital_background)

        mNumTextPaint.typeface = typeface
        mNumTextPaint.color = mBackgroundColor
        mNumTextPaint.textSize = numTextSize
        mNumTextPaint.isAntiAlias = true

        // 文字用
        mTextPaint = Paint()
        typeface = Typeface.createFromAsset(watch.assets, "mplus-1m-regular.ttf")
        mChargingColor = resources.getColor(R.color.battery_charge)

        mTextPaint.typeface = typeface
        mTextPaint.color = mRoundColor
        mTextPaint.textSize = textSize
        mTextPaint.isAntiAlias = true

        // 上
        // mplus-1m-regular 漢字一字
        var halfTextWidth = mTextPaint.measureText("電") / 2f
        var fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
        var halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f
        mTopTextPoint = PointF(mLineOffset + mRoundBlockWidth / 2f - halfTextWidth,
                mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2f - halfTextHeight)
        // Browing 数字2字
        halfTextWidth = mNumTextPaint.measureText("00") / 2f
        fontMetrics = mTextPaint.fontMetrics
        halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f
        mTopNumPoint = PointF(mLineOffset + mRoundBlockWidth / 2f - halfTextWidth,
                mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2f - halfTextHeight)
    }

    fun setAntiAlias(mode: Boolean?) {
        mTextPaint.isAntiAlias = mode!!
        // mNumTextPaint.setAntiAlias(mode);
    }

    fun drawBatteryPercent(canvas: Canvas?, batteryPct: Int, ambient: Boolean, isCharging: Boolean) {
        if (ambient) {
            canvas?.drawRect(mTopBlockOffset.x, mTopBlockOffset.y,
                    mTopBlockOffset.x + mRoundBlockWidth,
                    mTopBlockOffset.y + mRoundBlockWidth, mFillPaint)
            canvas?.drawText(String.format("%02d", if (batteryPct != 100) batteryPct else 0), mTopNumPoint.x, mTopNumPoint.y, mNumTextPaint)
        } else {
            if (isCharging) {
                mTextPaint.color = mChargingColor
                canvas?.drawText("電", mTopTextPoint.x - 2f, mTopTextPoint.y, mTextPaint)
                mTextPaint.color = mRoundColor
            } else {
                canvas?.drawText("電", mTopTextPoint.x - 2f, mTopTextPoint.y, mTextPaint)
            }
        }
    }
}
