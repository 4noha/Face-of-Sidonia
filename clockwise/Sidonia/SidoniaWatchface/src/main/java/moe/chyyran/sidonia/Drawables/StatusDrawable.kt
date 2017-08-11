package moe.chyyran.sidonia.Drawables

import android.app.WallpaperManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Typeface
import android.text.format.Time

import com.ustwo.clockwise.WatchFace

import moe.chyyran.sidonia.R

/**
 * Created by nokkii on 2015/05/20.
 */
class StatusDrawable(watch: WatchFace) {
    internal var mLeftTextPaint: Paint
    internal var mRightTextPaint: Paint
    internal var mCenterTextPaint: Paint
    internal var mFillPaint: Paint
    internal var mCenterFontMetrics: Paint.FontMetrics
    internal var mLeftTextOffset: PointF
    internal var mRightTextOffset: PointF
    internal var mCenterTextOffset: PointF

    internal var mRoundBlockWidth: Float = 0.toFloat()
    internal var mLineOffset: Float = 0.toFloat()

    init {
        val resources = watch.resources
        val desiredMinimumWidth = WallpaperManager.getInstance(watch).desiredMinimumWidth

        val leftTextSize = desiredMinimumWidth / 5.5f
        val centerTextSize = desiredMinimumWidth / 12.0f
        val textSize = desiredMinimumWidth / 5.5f
        var typeface = Typeface.createFromAsset(watch.assets, "mplus-1m-regular.ttf")

        // 塗りつぶし
        mFillPaint = createTextPaint(resources.getColor(R.color.round))
        mLineOffset = desiredMinimumWidth / 80f
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f

        // 左側
        mLeftTextPaint = Paint()

        mLeftTextPaint.typeface = typeface
        mLeftTextPaint.color = resources.getColor(R.color.round)
        mLeftTextPaint.textSize = leftTextSize
        mLeftTextPaint.isAntiAlias = true

        // 右側
        typeface = Typeface.createFromAsset(watch.assets, "Browning.ttf")
        mRightTextPaint = Paint()

        mRightTextPaint.typeface = typeface
        mRightTextPaint.color = resources.getColor(R.color.digital_background)
        mRightTextPaint.textSize = textSize
        mRightTextPaint.isAntiAlias = true

        // 中央
        mCenterTextPaint = Paint()

        mCenterTextPaint.typeface = typeface
        mCenterTextPaint.color = resources.getColor(R.color.digital_background)
        mCenterTextPaint.textSize = centerTextSize
        mCenterTextPaint.isAntiAlias = true

        var mFontMetrics: Paint.FontMetrics = mLeftTextPaint.fontMetrics
        var halfTextWidth = mLeftTextPaint.measureText("木") / 2f
        var halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f
        var top = mLineOffset + mRoundBlockWidth / 2f
        var left = mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2f
        mLeftTextOffset = PointF(left - halfTextWidth, top - halfTextHeight)

        mFontMetrics = mRightTextPaint.fontMetrics
        halfTextWidth = mRightTextPaint.measureText("000") / 2f
        halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f
        left = mLineOffset + desiredMinimumWidth / 1.60f - halfTextWidth
        mRightTextOffset = PointF(left, top - halfTextHeight)

        mCenterFontMetrics = mCenterTextPaint.fontMetrics
        halfTextWidth = mCenterTextPaint.measureText("T") / 2f
        halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f
        top = mLineOffset + mRoundBlockWidth / 9f
        left = mLineOffset + desiredMinimumWidth / 2.25f - halfTextWidth
        mCenterTextOffset = PointF(left, top - halfTextHeight)
    }

    fun setAntiAlias(mode: Boolean?) {
        mFillPaint.isAntiAlias = mode!!
        mLeftTextPaint.isAntiAlias = mode
        // こっちはめちゃ汚くなる
        // mCenterTextPaint.setAntiAlias(mode);
        // mRightTextPaint.setAntiAlias(mode);
    }

    fun drawTime(canvas: Canvas?, time: Time) {
        val hour12 = if (time.hour > 11) time.hour - 12 else time.hour
        val text = (hour12 % 10).toString() + if (time.minute > 9)
            time.minute.toString()
        else
            "0" + time.minute.toString()

        canvas?.drawRect(mRoundBlockWidth * 2f + mLineOffset,
                mLineOffset,
                mRoundBlockWidth * 4f + mLineOffset,
                mRoundBlockWidth * 1f + mLineOffset,
                mFillPaint
        )
        canvas?.drawText("T", mCenterTextOffset.x, mCenterTextOffset.y, mCenterTextPaint)
        if (time.hour > 11)
            canvas?.drawText("P", mCenterTextOffset.x,
                    mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                    mCenterTextPaint
            )
        else
            canvas?.drawText("A", mCenterTextOffset.x,
                    mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                    mCenterTextPaint
            )
        canvas?.drawText(text, mRightTextOffset.x, mRightTextOffset.y, mRightTextPaint)
    }

    fun drawDate(canvas: Canvas?, time: Time) {
        // Months are zero-indexed, so we have to add one in order to correct for it.
        val text = ((time.month % 10) + 1).toString() + (if (time.monthDay > 9)
            time.monthDay.toString()
        else
            "0" + time.monthDay.toString())

        canvas?.drawRect(mRoundBlockWidth * 2f + mLineOffset, mLineOffset,
                mRoundBlockWidth * 4f + mLineOffset,
                mRoundBlockWidth * 1f + mLineOffset,
                mFillPaint
        )
        canvas?.drawText("T", mCenterTextOffset.x, mCenterTextOffset.y, mCenterTextPaint)
        canvas?.drawText("S", mCenterTextOffset.x,
                mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                mCenterTextPaint
        )
        canvas?.drawText(text, mRightTextOffset.x, mRightTextOffset.y, mRightTextPaint)
    }

    fun drawWeekDay(canvas: Canvas?, time: Time) {
        canvas?.drawText(getWeekDayKanji(time.weekDay),
                mLeftTextOffset.x, mLeftTextOffset.y, mLeftTextPaint)
    }

    private fun getWeekDayKanji(num: Int): String {
        var c = ""

        when (num) {
            0 -> c = "曰"
            1 -> c = "月"
            2 -> c = "火"
            3 -> c = "水"
            4 -> c = "木"
            5 -> c = "金"
            6 -> c = "土"
        }
        return c
    }
}
