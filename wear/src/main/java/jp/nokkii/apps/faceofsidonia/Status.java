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
    Paint mLeftTextPaint;
    Paint mRightTextPaint;
    Paint mCenterTextPaint;
    Paint mFillPaint;

    float mLeftTextXOffset ,mRightTextXOffset ,mCenterTextXOffset;
    float mLeftTextYOffset ,mRightTextYOffset ,mCenterTextYOffset;
    float mRoundBlockWidth;

    public Status(FaceOfSidonia watch) {
        Resources resources = watch.getResources();

        float leftTextSize = resources.getDimension(R.dimen.status_left_text_size);
        float textSize = resources.getDimension(R.dimen.status_right_text_size);
        float centerTextSize = resources.getDimension(R.dimen.status_right_text_size) - 33;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "mplus-1m-regular.ttf");

        // 塗りつぶし
        mFillPaint = watch.createTextPaint(resources.getColor(R.color.round));
        mRoundBlockWidth = resources.getDimension(R.dimen.round_block_width);

        // 左側
        mLeftTextPaint = new Paint();
        mLeftTextXOffset = resources.getDimension(R.dimen.status_left_text_x_offset);
        mLeftTextYOffset = resources.getDimension(R.dimen.status_left_text_y_offset);

        mLeftTextPaint.setTypeface(typeface);
        mLeftTextPaint.setColor(resources.getColor(R.color.round));
        mLeftTextPaint.setTextSize(leftTextSize);
        mLeftTextPaint.setAntiAlias(true);

        // 右側
        typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");
        mRightTextPaint = new Paint();
        mRightTextXOffset = resources.getDimension(R.dimen.status_right_text_x_offset);
        mRightTextYOffset = resources.getDimension(R.dimen.status_right_text_y_offset);

        mRightTextPaint.setTypeface(typeface);
        mRightTextPaint.setColor(resources.getColor(R.color.digital_background));
        mRightTextPaint.setTextSize(textSize);
        mRightTextPaint.setAntiAlias(true);

        // 中央
        mCenterTextPaint = new Paint();
        mCenterTextXOffset = resources.getDimension(R.dimen.status_center_text_x_offset);
        mCenterTextYOffset = resources.getDimension(R.dimen.status_center_text_y_offset);

        mCenterTextPaint.setTypeface(typeface);
        mCenterTextPaint.setColor(resources.getColor(R.color.digital_background));
        mCenterTextPaint.setTextSize(centerTextSize);
        mCenterTextPaint.setAntiAlias(true);
    }

    public void setAntiAlias(Boolean mode){
        mFillPaint.setAntiAlias(mode);
        mLeftTextPaint.setAntiAlias(mode);
        // こっちはめちゃ汚くなる
        // mCenterTextPaint.setAntiAlias(mode);
        // mRightTextPaint.setAntiAlias(mode);
    }

    public void drawTime(Canvas canvas, Time time) {
        int hour12 = time.hour > 11 ? time.hour - 12 : time.hour;
        String text = String.valueOf(hour12%10) +
                (time.minute > 9 ? String.valueOf(time.minute) :
                        "0" + String.valueOf(time.minute));

        canvas.drawRect(mRoundBlockWidth * 2 +3, 3, mRoundBlockWidth * 4 + 3 + 1.0f, mRoundBlockWidth * 1 + 3 + 1.0f, mFillPaint);
        canvas.drawText("T", mCenterTextXOffset, mCenterTextYOffset, mCenterTextPaint);
        if (time.hour > 11)
            canvas.drawText("P", mCenterTextXOffset, mCenterTextYOffset + 27, mCenterTextPaint);
        else
            canvas.drawText("A", mCenterTextXOffset, mCenterTextYOffset + 27, mCenterTextPaint);
        canvas.drawText(text, mRightTextXOffset, mRightTextYOffset, mRightTextPaint);
    }

    public void drawDate(Canvas canvas, Time time) {
        String text = String.valueOf(time.month%10) +
                (time.monthDay > 9 ? String.valueOf(time.monthDay) :
                        "0" + String.valueOf(time.monthDay));

        canvas.drawRect(mRoundBlockWidth * 2 +3, 3, mRoundBlockWidth * 4 + 3 + 1.0f, mRoundBlockWidth * 1 + 3 + 1.0f, mFillPaint);
        canvas.drawText("T", mCenterTextXOffset, mCenterTextYOffset, mCenterTextPaint);
        canvas.drawText("S", mCenterTextXOffset, mCenterTextYOffset + 27, mCenterTextPaint);
        canvas.drawText(text, mRightTextXOffset, mRightTextYOffset, mRightTextPaint);
    }

    public void drawWeekDay(Canvas canvas, Time time) {
        canvas.drawText(changeWeekDayToKanji(time.weekDay),
                mLeftTextXOffset, mLeftTextYOffset, mLeftTextPaint);
    }

    private static String changeWeekDayToKanji(int num) {
        String c = "";

        switch (num) {
            case 0:
                c = "曰";
                break;
            case 1:
                c = "月";
                break;
            case 2:
                c = "火";
                break;
            case 3:
                c = "水";
                break;
            case 4:
                c = "木";
                break;
            case 5:
                c = "金";
                break;
            case 6:
                c = "土";
                break;
        }
        return c;
    }
}
