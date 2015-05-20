package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by nokkii on 2015/05/20.
 */
public class Status {
    Paint mTextPaint;

    float mTextXOffset;
    float mTextYOffset;

    public Status(FaceOfSidonia watch) {
        Resources resources = watch.getResources();

        float statusTextSize = resources.getDimension(R.dimen.status_text_size);
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");

        mTextPaint = new Paint();
        mTextXOffset = resources.getDimension(R.dimen.status_text_x_offset);
        mTextYOffset = resources.getDimension(R.dimen.status_text_y_offset);

        mTextPaint.setTypeface(typeface);
        mTextPaint.setColor(resources.getColor(R.color.digital_background));
        mTextPaint.setTextSize(statusTextSize);
        mTextPaint.setAntiAlias(true);
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
    }

    public void drawText(Canvas canvas, String text) {
        canvas.drawText(text, mTextXOffset , mTextYOffset, mTextPaint);
    }
}
