package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by nokkii on 2015/05/20.
 */
public class CenterPoint {
    Paint mTextPaint;

    float mTextXOffset;
    float mTextYOffset;

    public CenterPoint(FaceOfSidonia watch) {
        Resources resources = watch.getResources();
        float textSize = resources.getDimension(R.dimen.digital_text_size);

        mTextPaint = new Paint();
        mTextXOffset = resources.getDimension(R.dimen.digital_x_offset);
        mTextYOffset = resources.getDimension(R.dimen.digital_y_offset);
        mTextPaint = watch.createTextPaint(resources.getColor(R.color.digital_text));

        mTextPaint.setTextSize(textSize);
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
    }

    public void drawText(Canvas canvas, String text, String text2, String text3, String text4) {
        canvas.drawText(text , mTextXOffset   , mTextYOffset   , mTextPaint);
        canvas.drawText(text2, mTextXOffset+91, mTextYOffset   , mTextPaint);
        canvas.drawText(text3, mTextXOffset   , mTextYOffset+91, mTextPaint);
        canvas.drawText(text4, mTextXOffset+91, mTextYOffset+91, mTextPaint);
    }
}
