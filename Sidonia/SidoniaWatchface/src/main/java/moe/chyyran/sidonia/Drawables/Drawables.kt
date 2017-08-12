package moe.chyyran.sidonia.Drawables

import android.graphics.Paint
import android.graphics.Typeface


private val NORMAL_TYPEFACE = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)

fun createTextPaint(textColor: Int): Paint {
    val paint = Paint()
    paint.color = textColor
    paint.typeface = NORMAL_TYPEFACE
    paint.isAntiAlias = true
    return paint
}

fun createAntiAliasedPaint(): Paint {
    val paint = Paint()
    paint.isAntiAlias = true
    return paint
}