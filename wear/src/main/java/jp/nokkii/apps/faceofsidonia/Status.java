package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.Time;

/**
 * Created by nokkii on 2015/05/20.
 */
public class Status {
    Paint mTextPaint;
    Paint mLeftTextPaint;

    float mTextXOffset ,mLeftTextXOffset;
    float mTextYOffset ,mLeftTextYOffset;

    public Status(FaceOfSidonia watch) {
        Resources resources = watch.getResources();

        float textSize = resources.getDimension(R.dimen.status_text_size);
        float leftTextSize = resources.getDimension(R.dimen.status_text_size) - 33;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");

        // 右側
        mTextPaint = new Paint();
        mTextXOffset = resources.getDimension(R.dimen.status_text_x_offset);
        mTextYOffset = resources.getDimension(R.dimen.status_text_y_offset);

        mTextPaint.setTypeface(typeface);
        mTextPaint.setColor(resources.getColor(R.color.digital_background));
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);

        // 左側
        mLeftTextPaint = new Paint();
        mLeftTextXOffset = resources.getDimension(R.dimen.status_text_x_offset) - 21;
        mLeftTextYOffset = resources.getDimension(R.dimen.status_left_text_y_offset);

        mLeftTextPaint.setTypeface(typeface);
        mLeftTextPaint.setColor(resources.getColor(R.color.digital_background));
        mLeftTextPaint.setTextSize(leftTextSize);
        mLeftTextPaint.setAntiAlias(true);
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
    }

    public void drawTime(Canvas canvas, Time time) {
        int hour12 = time.hour > 11 ? time.hour - 12 : time.hour;
        String text = String.valueOf(hour12%10) +
                (time.minute > 9 ? String.valueOf(time.minute) :
                String.valueOf(0) + String.valueOf(time.minute));

        canvas.drawText("T", mLeftTextXOffset, mLeftTextYOffset, mLeftTextPaint);
        if (time.hour > 11)
            canvas.drawText("P", mLeftTextXOffset, mLeftTextYOffset + 27, mLeftTextPaint);
        else
            canvas.drawText("A", mLeftTextXOffset, mLeftTextYOffset + 27, mLeftTextPaint);
        canvas.drawText(text, mTextXOffset, mTextYOffset, mTextPaint);
    }
}
