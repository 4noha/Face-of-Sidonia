package moe.chyyran.sidonia.Drawables

import android.app.WallpaperManager
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.format.Time

import com.ustwo.clockwise.WatchFace

import moe.chyyran.sidonia.R

import moe.chyyran.sidonia.Drawables.createTextPaint

class TimeDrawable(watch: WatchFace) {
    internal var STATUS_WAIT_SEC: Int = 0

    internal var mFillPaint: Paint
    internal var mLinePaint: Paint
    internal var mBoldLinePaint: Paint
    internal var mTextPaint: Paint
    internal var mStatusTextPaint: Paint
    internal var mFontMetrics: Paint.FontMetrics
    internal var mTLTextPoint: PointF
    internal var mTRTextPoint: PointF
    internal var mBLTextPoint: PointF
    internal var mBRTextPoint: PointF
    internal var mTopStatusPoint: PointF
    internal var mMiddleStatusPoint: PointF
    internal var mBottomStatusPoint: PointF

    internal var mRoundBlockWidth: Float = 0.toFloat()
    internal var mCenterBlockWidth: Float = 0.toFloat()
    internal var mLineOffset: Float = 0.toFloat()
    internal var mWatchWidth: Float = 0.toFloat()
    internal var mCenterBoldLines: FloatArray
    internal var mMAXProgress = 0
    internal var mAlertColor: Int = 0
    internal var mRoundColor: Int = 0
    internal var mBackgroundColor: Int = 0

    init {
        val resources = watch.resources
        val desiredMinimumWidth = WallpaperManager.getInstance(watch).desiredMinimumWidth.toFloat()
        STATUS_WAIT_SEC = resources.getInteger(R.integer.animation_delay)

        var textSize = desiredMinimumWidth / 5f
        val typeface = Typeface.createFromAsset(watch.assets, "mplus-1m-regular.ttf")
        mAlertColor = ContextCompat.getColor(watch.applicationContext, R.color.alert)
        mRoundColor = ContextCompat.getColor(watch.applicationContext, R.color.round)
        mBackgroundColor = ContextCompat.getColor(watch.applicationContext, R.color.digital_background)

        mLineOffset = desiredMinimumWidth / 80f
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f
        mWatchWidth = desiredMinimumWidth
        mCenterBlockWidth = (desiredMinimumWidth - (mLineOffset * 2f + mRoundBlockWidth * 2f)) / 2f

        // CenterText
        mTextPaint = Paint()
        mTextPaint.typeface = typeface
        mTextPaint.textSize = textSize
        mFontMetrics = mTextPaint.fontMetrics
        mTextPaint.color = mAlertColor

        var halfTextWidth = mTextPaint.measureText("０") / 2f
        var halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f
        var top = mLineOffset + mRoundBlockWidth + mCenterBlockWidth / 2f
        mTLTextPoint = PointF(top - halfTextWidth, top - halfTextHeight)
        mTRTextPoint = PointF(top + mCenterBlockWidth - halfTextWidth, top - halfTextHeight)
        mBLTextPoint = PointF(top - halfTextWidth, top + mCenterBlockWidth - halfTextHeight)
        mBRTextPoint = PointF(top + mCenterBlockWidth - halfTextWidth, top + mCenterBlockWidth - halfTextHeight)

        // StatusText
        textSize = desiredMinimumWidth / 5.5f
        halfTextWidth = mTextPaint.measureText("0000") / 2f
        mFontMetrics = mTextPaint.fontMetrics
        halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f
        val left = mLineOffset + mRoundBlockWidth * 2f
        mStatusTextPaint = Paint()
        mStatusTextPaint.typeface = typeface
        mStatusTextPaint.textSize = textSize
        mStatusTextPaint.color = mRoundColor
        top = mLineOffset + mRoundBlockWidth * 1f + mRoundBlockWidth / 2f - halfTextHeight
        mTopStatusPoint = PointF(left - halfTextWidth, top)
        top = mLineOffset + mRoundBlockWidth * 2f + mRoundBlockWidth / 2f - halfTextHeight
        mMiddleStatusPoint = PointF(left - halfTextWidth, top)
        top = mLineOffset + mRoundBlockWidth * 3f + mRoundBlockWidth / 2f - halfTextHeight
        mBottomStatusPoint = PointF(left - halfTextWidth, top)

        // Line
        mBoldLinePaint = Paint()
        mBoldLinePaint.strokeWidth = mLineOffset / 2f
        mLinePaint = createTextPaint(mRoundColor)
        mLinePaint.strokeWidth = mLineOffset / 4f

        // Fill
        mFillPaint = Paint()
        mFillPaint.color = mBackgroundColor
        mFillPaint.isAntiAlias = true

        mCenterBoldLines = floatArrayOf(
                // 縦線
                mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset,
                // 横線
                mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset)
    }

    fun setAntiAlias(mode: Boolean?) {
        mTextPaint.isAntiAlias = mode!!
    }

    fun drawTime(canvas: Canvas?, time: Time) {
        mBoldLinePaint.color = mAlertColor
        canvas?.drawLine(mRoundBlockWidth * 1f + mLineOffset, mWatchWidth / 2f, mRoundBlockWidth * 4f + mLineOffset, mWatchWidth / 2f, mBoldLinePaint)
        canvas?.drawLine(mWatchWidth / 2f, mRoundBlockWidth * 1f + mLineOffset, mWatchWidth / 2f, mRoundBlockWidth * 4f + mLineOffset, mBoldLinePaint)
        canvas?.drawLines(mCenterBoldLines, mBoldLinePaint)
        canvas?.drawText(getKanjiNumeral(time.hour / 10), mTLTextPoint.x, mTLTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.hour % 10), mTRTextPoint.x, mTRTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.minute / 10), mBLTextPoint.x, mBLTextPoint.y, mTextPaint)
        canvas?.drawText(getKanjiNumeral(time.minute % 10), mBRTextPoint.x, mBRTextPoint.y, mTextPaint)
    }

    fun drawStatus(canvas: Canvas, progress: Int, batteryPct: Int): Boolean {
        var progress = progress
        if (-STATUS_WAIT_SEC > progress) return false
        var statusInvisible = true
        if (progress > mMAXProgress) {
            mMAXProgress = progress - 1
        } else if (progress < 1) {
            statusInvisible = false
            progress = 1
        }
        val right = mRoundBlockWidth * 1f + mLineOffset + mRoundBlockWidth * 2f / mMAXProgress.toFloat() * (mMAXProgress - progress + 1).toFloat()

        canvas.drawRect(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, right, mRoundBlockWidth * 4f + mLineOffset, mFillPaint)
        mBoldLinePaint.color = mRoundColor
        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 2f + mLineOffset, right, mRoundBlockWidth * 2f + mLineOffset, mBoldLinePaint)
        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 3f + mLineOffset, right, mRoundBlockWidth * 3f + mLineOffset, mBoldLinePaint)

        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, right, mRoundBlockWidth * 1f + mLineOffset, mBoldLinePaint)
        canvas.drawLine(right, mRoundBlockWidth * 1f + mLineOffset, right, mRoundBlockWidth * 4f + mLineOffset, mBoldLinePaint)
        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, right, mRoundBlockWidth * 4f + mLineOffset, mBoldLinePaint)
        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mBoldLinePaint)

        if (!statusInvisible) {
            canvas.drawText(String.format("%4d", batteryPct), mTopStatusPoint.x, mTopStatusPoint.y, mStatusTextPaint)
            canvas.drawText(String.format("%4d", 0), mMiddleStatusPoint.x, mMiddleStatusPoint.y, mStatusTextPaint)
            canvas.drawText(String.format("%4d", 0), mBottomStatusPoint.x, mBottomStatusPoint.y, mStatusTextPaint)
        }
        return statusInvisible
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
