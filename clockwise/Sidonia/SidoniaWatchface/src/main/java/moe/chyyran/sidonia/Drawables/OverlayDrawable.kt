package moe.chyyran.sidonia.Drawables
import android.app.WallpaperManager
import android.graphics.*
import com.ustwo.clockwise.WatchFace
import moe.chyyran.sidonia.R


class OverlayDrawable(watch: WatchFace) {
    internal val mWallpaper: Bitmap = BitmapFactory.decodeResource(watch.resources, R.mipmap.sidonia)
    internal val watch: WatchFace = watch
    internal val wallpaperBounds: Rect = Rect(0, 0, mWallpaper.width, mWallpaper.height)

    fun drawOverlay(canvas: Canvas?) {
        canvas?.save()
        canvas?.drawColor(Color.BLACK)
        canvas?.drawBitmap(this.mWallpaper, wallpaperBounds, canvas.clipBounds, createAntiAliasedPaint())
        canvas?.restore()
    }
}
