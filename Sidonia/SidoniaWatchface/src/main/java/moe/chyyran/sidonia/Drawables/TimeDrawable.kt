package moe.chyyran.sidonia.Drawables

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.text.format.Time

import com.ustwo.clockwise.WatchFace

import moe.chyyran.sidonia.R

class TimeDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private var STATUS_WAIT_SEC: Int = 0

    private var mTextPaint: Paint
    private var mTLTextPoint: PointF
    private var mTRTextPoint: PointF
    private var mBLTextPoint: PointF
    private var mBRTextPoint: PointF

    private var mCenterBlockWidth: Float = 0.toFloat()
    private var mCenterBoldLines: FloatArray

    init {
        val resources = watch.resources
        STATUS_WAIT_SEC = resources.getInteger(R.integer.animation_delay)

        var textSize = desiredMinimumWidth / 5f

        mCenterBlockWidth = (desiredMinimumWidth - (edgeOffset * 2f + hudCellWidth * 2f)) / 2f

        // Digit Display
        mTextPaint = Paint()
        mTextPaint.typeface = this.kanjiFont
        mTextPaint.textSize = textSize
        val fontMetrics = mTextPaint.fontMetrics
        mTextPaint.color = alertColor
        mTextPaint.isAntiAlias = true

        var halfTextWidth = mTextPaint.measureText("０") / 2f
        var halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f
        var top = edgeOffset + hudCellWidth + mCenterBlockWidth / 2f

        mTLTextPoint = PointF(top - halfTextWidth, top - halfTextHeight)
        mTRTextPoint = PointF(top + mCenterBlockWidth - halfTextWidth, top - halfTextHeight)
        mBLTextPoint = PointF(top - halfTextWidth, top + mCenterBlockWidth - halfTextHeight)
        mBRTextPoint = PointF(top + mCenterBlockWidth - halfTextWidth, top + mCenterBlockWidth - halfTextHeight)


        mCenterBoldLines = floatArrayOf(
                // Vertical Lines
                hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset,
                // Horizontal Lines
                hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 1f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset, hudCellWidth * 4f + edgeOffset)
    }

    fun drawTime(canvas: Canvas?, time: Time) {
        canvas?.save()
        alertBoldPaint.color = alertColor
        canvas?.drawLine(hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, desiredMinimumWidth / 2f, alertBoldPaint)
        canvas?.drawLine(desiredMinimumWidth / 2f, hudCellWidth * 1f + edgeOffset, desiredMinimumWidth / 2f, hudCellWidth * 4f + edgeOffset, alertBoldPaint)
        canvas?.drawLines(mCenterBoldLines, alertBoldPaint)
        canvas?.drawText(getKanjiNumeral(time.hour / 10), mTLTextPoint.x, mTLTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.hour % 10), mTRTextPoint.x, mTRTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.minute / 10), mBLTextPoint.x, mBLTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.minute % 10), mBRTextPoint.x, mBRTextPoint.y, mTextPaint)
        canvas?.restore()
    }

    private fun getKanjiNumeral(num: Int): String {
        var c = ""

        when (num) {
            0 -> c = "零"
            1 -> c = "壱"
            2 -> c = "弐"
            3 -> c = "参"
            4 -> c = "四"
            5 -> c = "五"
            6 -> c = "六"
            7 -> c = "七"
            8 -> c = "八"
            9 -> c = "九"
        }
        return c
    }
}
