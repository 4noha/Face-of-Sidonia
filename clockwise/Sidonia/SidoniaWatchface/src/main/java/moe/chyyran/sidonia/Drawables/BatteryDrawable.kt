package moe.chyyran.sidonia.Drawables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

import com.ustwo.clockwise.WatchFace

class BatteryDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    internal var mNumTextPaint: Paint
    internal var mTextPaint: Paint

    internal var mTopBlockOffset: PointF
    internal var mTopTextPoint: PointF
    internal var mTopNumPoint: PointF

    init {
        val textSize = desiredMinimumWidth / 5.5f
        mTopBlockOffset = this.getCellOffset(1, 0)

        // 数字用
        mNumTextPaint = Paint()

        mNumTextPaint.typeface = this.latinFont
        mNumTextPaint.color = this.backgroundColor
        mNumTextPaint.textSize = textSize
        mNumTextPaint.isAntiAlias = true

        // 文字用
        mTextPaint = Paint(mNumTextPaint)
        mTextPaint.typeface = this.kanjiFont
        mTextPaint.color = this.hudColor

        // Top
        // mplus-1m-regular 漢字一字
        val fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
        val chargedTextWidthDiff = (hudCellWidth - mTextPaint.measureText("電")) / 2f
        val chargedTextHeightDiff = (hudCellWidth - (fontMetrics.ascent + fontMetrics.descent)) / 2f

        mTopTextPoint = PointF(mTopBlockOffset.x + chargedTextWidthDiff,
                mTopBlockOffset.y + chargedTextHeightDiff)

        // Browing 数字2字
        val numFontMetrics = mNumTextPaint.fontMetrics
        val percentTextWidthDiff = (hudCellWidth - mNumTextPaint.measureText("00")) / 2f
        val percentTextHeightDiff = (hudCellWidth - (numFontMetrics.ascent + numFontMetrics.descent)) / 2f

        mTopNumPoint = PointF(mTopBlockOffset.x + percentTextWidthDiff,
                mTopBlockOffset.y + percentTextHeightDiff)
    }

    fun drawBatteryPercent(canvas: Canvas?, batteryPct: Int, isCharging: Boolean) {
        canvas?.save()
        if (isCharging) {
            mTextPaint.color = this.alertColor
            canvas?.drawText("電", mTopTextPoint.x - 2f, mTopTextPoint.y, mTextPaint)
            mTextPaint.color = this.hudColor
        } else {
            canvas?.drawRect(mTopBlockOffset.x, mTopBlockOffset.y,
                    mTopBlockOffset.x + hudCellWidth,
                    mTopBlockOffset.y + hudCellWidth, this.hudPaint)
            canvas?.drawText(String.format("%02d", if (batteryPct != 100) batteryPct else 0),
                    mTopNumPoint.x - 2f, mTopNumPoint.y, mNumTextPaint)
        }
        canvas?.restore()

    }
}
