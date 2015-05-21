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
    Paint mRightTextPaint;
    Paint mCenterTextPaint;

    float mRightTextXOffset ,mCenterTextXOffset;
    float mRightTextYOffset ,mCenterTextYOffset;

    public Status(FaceOfSidonia watch) {
        Resources resources = watch.getResources();

        float textSize = resources.getDimension(R.dimen.status_right_text_size);
        float leftTextSize = resources.getDimension(R.dimen.status_right_text_size) - 33;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");


        // 右側
        mRightTextPaint = new Paint();
        mRightTextXOffset = resources.getDimension(R.dimen.status_right_text_x_offset);
        mRightTextYOffset = resources.getDimension(R.dimen.status_right_text_y_offset);

        mRightTextPaint.setTypeface(typeface);
        mRightTextPaint.setColor(resources.getColor(R.color.digital_background));
        mRightTextPaint.setTextSize(textSize);
        mRightTextPaint.setAntiAlias(true);

        // 左側
        mCenterTextPaint = new Paint();
        mCenterTextXOffset = resources.getDimension(R.dimen.status_text_x_offset) - 21;
        mCenterTextYOffset = resources.getDimension(R.dimen.status_left_text_y_offset);

        mCenterTextPaint.setTypeface(typeface);
        mCenterTextPaint.setColor(resources.getColor(R.color.digital_background));
        mCenterTextPaint.setTextSize(leftTextSize);
        mCenterTextPaint.setAntiAlias(true);
    }

    public void setAntiAlias(Boolean mode){
        mRightTextPaint.setAntiAlias(mode);
    }

    public void drawTime(Canvas canvas, Time time) {
        int hour12 = time.hour > 11 ? time.hour - 12 : time.hour;
        String text = String.valueOf(hour12%10) +
                (time.minute > 9 ? String.valueOf(time.minute) :
                String.valueOf(0) + String.valueOf(time.minute));

        canvas.drawText("T", mCenterTextXOffset, mCenterTextYOffset, mCenterTextPaint);
        if (time.hour > 11)
            canvas.drawText("P", mCenterTextXOffset, mCenterTextYOffset + 27, mCenterTextPaint);
        else
            canvas.drawText("A", mCenterTextXOffset, mCenterTextYOffset + 27, mCenterTextPaint);
        canvas.drawText(text, mRightTextXOffset, mRightTextYOffset, mRightTextPaint);
    }
    }
}
