package moe.chyyran.sidonia.Drawables


import android.app.WallpaperManager
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat

import com.ustwo.clockwise.WatchFace

import moe.chyyran.sidonia.R

class GridDrawable(watch: WatchFace) {
    internal var mLinePaint: Paint
    internal var mBoldLinePaint: Paint

    internal var mRoundBlockWidth: Float = 0.toFloat()
    internal var mLineOffset: Float = 0.toFloat()
    internal var mBackGroundLines: FloatArray
    internal var mBackGroundBoldLines: FloatArray

    init {
        val backgroundColor = ContextCompat.getColor(watch.applicationContext, R.color.round)
        val desiredMinimumWidth = WallpaperManager.getInstance(watch).desiredMinimumWidth
        // Line
        mBoldLinePaint = createTextPaint(backgroundColor)
        mBoldLinePaint.strokeWidth = 2.0f

        mLinePaint = createTextPaint(backgroundColor)
        mLinePaint.strokeWidth = 1.1f

        mLineOffset = desiredMinimumWidth / 80f
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f

        // コンパイラでListから静的なArrayにならないので直書き
        mBackGroundLines = floatArrayOf(
                // 縦線
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4,
                // 横線
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4)

        mBackGroundBoldLines = floatArrayOf(
                // 縦棒
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 3, // 内側
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 3, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 8,
                // mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2 + 8,
                // mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 8,
                // mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3 + 8,
                // mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 8, // 内側
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 8, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5 + 8,
                // 横棒
                mRoundBlockWidth * 0, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 3, mRoundBlockWidth * 2 + 4, // 内側
                mRoundBlockWidth * 1, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 3, mRoundBlockWidth * 3 + 4, // 内側
                mRoundBlockWidth * 1, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 1 + 4,
                // mRoundBlockWidth * 2, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 2 + 4,
                // mRoundBlockWidth * 2, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 2, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 1 + 4,
                // mRoundBlockWidth * 3, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 2 + 4,
                // mRoundBlockWidth * 3, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 3, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 5, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 2 + 4, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 5, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 3 + 4, // 内側
                mRoundBlockWidth * 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 5 + 4)
    }

    fun setAntiAlias(mode: Boolean?) {
        mLinePaint.isAntiAlias = mode!!
    }

    fun drawBackground(canvas: Canvas?) {
        canvas?.save()
        canvas?.drawLines(mBackGroundLines, mLinePaint)
        canvas?.drawLines(mBackGroundBoldLines, mBoldLinePaint)
        canvas?.restore()
    }
}