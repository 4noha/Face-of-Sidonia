package moe.chyyran.sidonia.Drawables
import android.app.WallpaperManager
import android.graphics.*
import com.ustwo.clockwise.WatchFace
import moe.chyyran.sidonia.R


class OverlayDrawable(watch: WatchFace) : SidoniaDrawable(watch) {
    private val mTextPaint: Paint = Paint(this.hudPaint)
    private val overlayCharacterWidthDiff : Float
    private val overlayCharacterHeightDiff : Float

    init {
        mTextPaint.typeface = this.kanjiFont
        mTextPaint.color = this.hudColor
        mTextPaint.textSize = desiredMinimumWidth / 5.5f
        val fontMetrics: Paint.FontMetrics = mTextPaint.fontMetrics
        overlayCharacterWidthDiff = (hudCellWidth - mTextPaint.measureText("圧")) / 2f
        overlayCharacterHeightDiff = (hudCellWidth - (fontMetrics.ascent + fontMetrics.descent)) / 2f

    }

    fun drawOverlay(canvas: Canvas?) {
        canvas?.save()
        canvas?.drawColor(Color.BLACK)
        drawCharacter(canvas, "圧", 2, 0, 2f)
        drawCharacter(canvas, "温", 3, 0, 2f)
        drawCharacter(canvas, "能", 4, 1)
        drawCharacter(canvas, "析", 4, 2)
        drawCharacter(canvas, "設", 4, 3)
        drawCharacter(canvas, "通", 1, 4)
        drawCharacter(canvas, "令", 2, 4)

        canvas?.restore()
    }

    private fun drawCharacter(canvas: Canvas?, character: String, row: Int, column: Int, offsetX: Float = 0f, offsetY: Float = 0f) {
        canvas?.save()
        val cellOffset = this.getCellOffset(row, column)
        val characterOffset = PointF(cellOffset.x + overlayCharacterWidthDiff,
                cellOffset.y + overlayCharacterHeightDiff)
        canvas?.drawText(character, characterOffset.x - offsetX, characterOffset.y - offsetY, mTextPaint)
        canvas?.restore()
    }
    //

}
