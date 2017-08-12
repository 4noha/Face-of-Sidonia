package moe.chyyran.sidonia.Drawables

import android.graphics.*
import android.text.format.Time

import com.ustwo.clockwise.WatchFace
import com.ustwo.clockwise.WatchFaceTime

private class TextOffsets(val leftTextOffset: PointF, val rightTextOffset: PointF, val halfHeightTextOffset: PointF)

class StatusDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private var mLeftTextPaint: Paint = createWeekDayPaint()
    private var mRightTextPaint: Paint = createDatePaint()
    private var mHalfHeightTextPaint: Paint = createHalfHeightDisplayPaint()

    private fun getTextOffsets(kanji: String, time: String): TextOffsets {
        val leftTextMetrics = mLeftTextPaint.fontMetrics

        // This text is to be drawn in cell 0, 1.
        val leftCellOffset = this.getCellOffset(0, 1)

        // Calculate the difference on each side between the cell walls and the
        // width of the text, in order to properly center the text horizontally.
        val leftTextWidthDiff = (hudCellWidth - mLeftTextPaint.measureText(kanji)) / 2f

        // Calculate the difference on each side between the cell walls and the height of
        // the text, in order to properly center the text vertically.
        val leftTextHeightDiff = (hudCellWidth - (leftTextMetrics.ascent + leftTextMetrics.descent)) / 2f

        // The offset is simply the cell offset + the difference between the cell walls
        // and the dimensions of the text.
        val leftOffset = PointF(leftCellOffset.x + leftTextWidthDiff, leftCellOffset.y + leftTextHeightDiff)


        val rightCellOffset = this.getCellOffset(0, 2) // actually 2.5 ish, but we will ignore the x.
        val rightTextMetrics = mRightTextPaint.fontMetrics
        val rightTextWidth = mRightTextPaint.measureText(time) / 2f
        val rightTextHeightDiff = (hudCellWidth - (rightTextMetrics.ascent + rightTextMetrics.descent)) / 2f
        val rightLeftOffset = edgeOffset + desiredMinimumWidth / 1.60f - rightTextWidth
        // Here the x offset has been manually specified, but the top offset is the same principle
        val rightOffset = PointF(rightLeftOffset, rightCellOffset.y + rightTextHeightDiff)


        // Half height text is it's own special case.
        val halfHeightTextWidth = mHalfHeightTextPaint.measureText("T") / 2f
        val halfHeightTextHeight = (rightTextMetrics.ascent + rightTextMetrics.descent) / 2f
        val halfHeightTopOffset = edgeOffset + hudCellWidth / 9f
        val halfHeightLeftOffset = edgeOffset + desiredMinimumWidth / 2.25f - halfHeightTextWidth
        val halfHeightOffset = PointF(halfHeightLeftOffset, halfHeightTopOffset - halfHeightTextHeight)

        return TextOffsets(leftOffset, rightOffset, halfHeightOffset)
    }

    private fun createHalfHeightDisplayPaint(): Paint {
        val centerTextSize = desiredMinimumWidth / 12.0f
        val halfHeightPaint = Paint()
        halfHeightPaint.typeface = this.latinFont
        halfHeightPaint.color = this.backgroundColor
        halfHeightPaint.textSize = centerTextSize
        halfHeightPaint.isAntiAlias = true
        return halfHeightPaint
    }

    private fun createWeekDayPaint() : Paint {
        val textSize = desiredMinimumWidth / 5.5f
        val paint = Paint(this.hudPaint)
        paint.typeface = this.kanjiFont
        paint.textSize = textSize
        return paint
    }

    private fun createDatePaint() : Paint {
        val textSize = desiredMinimumWidth / 5.5f
        val paint = Paint()
        paint.typeface = this.latinFont
        paint.color = this.backgroundColor
        paint.textSize = textSize
        paint.isAntiAlias = true
        return paint
    }

    fun drawTime(canvas: Canvas?, time: WatchFaceTime) {
        val hour12 = if (time.hour > 11) time.hour - 12 else time.hour
        val text = (hour12 % 10).toString() + if (time.minute > 9)
            time.minute.toString()
        else
            "0" + time.minute.toString()

        val textOffsets = getTextOffsets(getWeekDayKanji(time.weekDay), text)
        canvas?.drawRect(hudCellWidth * 2f + edgeOffset,
                edgeOffset,
                hudCellWidth * 4f + edgeOffset,
                hudCellWidth * 1f + edgeOffset,
                this.hudPaint
        )
        canvas?.drawText("T", textOffsets.halfHeightTextOffset.x, textOffsets.halfHeightTextOffset.y, mHalfHeightTextPaint)
        if (time.hour > 11)
            canvas?.drawText("P", textOffsets.halfHeightTextOffset.x,
                    textOffsets.halfHeightTextOffset.y + (edgeOffset + hudCellWidth / 3f),
                    mHalfHeightTextPaint
            )
        else
            canvas?.drawText("A", textOffsets.halfHeightTextOffset.x,
                    textOffsets.halfHeightTextOffset.y + (edgeOffset + hudCellWidth / 3f),
                    mHalfHeightTextPaint
            )
        canvas?.drawText(text, textOffsets.rightTextOffset.x, textOffsets.rightTextOffset.y, mRightTextPaint)
    }

    fun drawDate(canvas: Canvas?, time: WatchFaceTime) {
        // Months are zero-indexed, so we have to add one in order to correct for it.
        val text = ((time.month % 10) + 1).toString() + (if (time.monthDay > 9)
            time.monthDay.toString()
        else
            "0" + time.monthDay.toString())
        val textOffsets = getTextOffsets(getWeekDayKanji(time.weekDay), text)

        val startOffset = this.getCellOffset(0, 2)
        val endOffset = this.getCellOffset(1, 4)
        canvas?.drawRect(startOffset.x, startOffset.y,
                endOffset.x,
                endOffset.y,
                this.hudPaint
        )
        canvas?.drawText("T", textOffsets.halfHeightTextOffset.x, textOffsets.halfHeightTextOffset.y,
                mHalfHeightTextPaint)
        canvas?.drawText("S", textOffsets.halfHeightTextOffset.x,
                textOffsets.halfHeightTextOffset.y + (edgeOffset + hudCellWidth / 3f),
                mHalfHeightTextPaint
        )
        canvas?.drawText(text, textOffsets.rightTextOffset.x, textOffsets.rightTextOffset.y, mRightTextPaint)
    }

    fun drawWeekDay(canvas: Canvas?, time: WatchFaceTime) {
        val text = ((time.month % 10) + 1).toString() + (if (time.monthDay > 9)
            time.monthDay.toString()
        else
            "0" + time.monthDay.toString())
        val textOffsets = getTextOffsets(getWeekDayKanji(time.weekDay), text)

        canvas?.drawText(getWeekDayKanji(time.weekDay),
                textOffsets.leftTextOffset.x, textOffsets.leftTextOffset.y, mLeftTextPaint)
    }

    private fun getWeekDayKanji(num: Int): String {
        var c: String = ""

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
