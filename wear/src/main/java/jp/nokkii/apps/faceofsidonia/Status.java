package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
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
    Paint.FontMetrics mCenterFontMetrics;
    PointF mLeftTextOffset ,mRightTextOffset ,mCenterTextOffset;

    float mRoundBlockWidth;
    float mLineOffset;

    public Status(FaceOfSidonia watch, int desiredMinimumWidth) {
        Resources resources = watch.getResources();

        float leftTextSize   = desiredMinimumWidth /  5.5f;
        float centerTextSize = desiredMinimumWidth / 12.0f;
        float textSize       = desiredMinimumWidth /  5.5f;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "mplus-1m-regular.ttf");

        // 塗りつぶし
        mFillPaint = watch.createTextPaint(resources.getColor(R.color.round));
        mLineOffset = desiredMinimumWidth / 80f;
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f;

        // 左側
        mLeftTextPaint = new Paint();

        mLeftTextPaint.setTypeface(typeface);
        mLeftTextPaint.setColor(resources.getColor(R.color.round));
        mLeftTextPaint.setTextSize(leftTextSize);
        mLeftTextPaint.setAntiAlias(true);

        // 右側
        typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");
        mRightTextPaint = new Paint();

        mRightTextPaint.setTypeface(typeface);
        mRightTextPaint.setColor(resources.getColor(R.color.digital_background));
        mRightTextPaint.setTextSize(textSize);
        mRightTextPaint.setAntiAlias(true);

        // 中央
        mCenterTextPaint = new Paint();

        mCenterTextPaint.setTypeface(typeface);
        mCenterTextPaint.setColor(resources.getColor(R.color.digital_background));
        mCenterTextPaint.setTextSize(centerTextSize);
        mCenterTextPaint.setAntiAlias(true);

        Paint.FontMetrics mFontMetrics = mLeftTextPaint.getFontMetrics();
        float halfTextWidth  = mLeftTextPaint.measureText("木") / 2f;
        float halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f;
        float top            = mLineOffset + mRoundBlockWidth / 2f;
        float left           = mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2f;
        mLeftTextOffset      = new PointF(left - halfTextWidth, top - halfTextHeight);

        mFontMetrics      = mRightTextPaint.getFontMetrics();
        halfTextWidth     = mRightTextPaint.measureText("000") / 2f;
        halfTextHeight    = (mFontMetrics.ascent + mFontMetrics.descent) / 2f;
        left              = mLineOffset + desiredMinimumWidth / 1.60f - halfTextWidth;
        mRightTextOffset  = new PointF(left, top - halfTextHeight);

        mCenterFontMetrics= mCenterTextPaint.getFontMetrics();
        halfTextWidth     = mCenterTextPaint.measureText("T") / 2f;
        halfTextHeight    = (mFontMetrics.ascent + mFontMetrics.descent) / 2f;
        top               = mLineOffset + mRoundBlockWidth / 9f;
        left              = mLineOffset + desiredMinimumWidth / 2.25f - halfTextWidth;
        mCenterTextOffset = new PointF(left, top - halfTextHeight);
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

        canvas.drawRect(mRoundBlockWidth * 2f + mLineOffset,
                mLineOffset,
                mRoundBlockWidth * 4f + mLineOffset,
                mRoundBlockWidth * 1f + mLineOffset,
                mFillPaint
        );
        canvas.drawText("T", mCenterTextOffset.x, mCenterTextOffset.y, mCenterTextPaint);
        if (time.hour > 11)
            canvas.drawText("P", mCenterTextOffset.x,
                    mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                    mCenterTextPaint
            );
        else
            canvas.drawText("A", mCenterTextOffset.x,
                    mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                    mCenterTextPaint
            );
        canvas.drawText(text, mRightTextOffset.x, mRightTextOffset.y, mRightTextPaint);
    }

    public void drawDate(Canvas canvas, Time time) {
        String text = String.valueOf(time.month%10) +
                (time.monthDay > 9 ? String.valueOf(time.monthDay) :
                        "0" + String.valueOf(time.monthDay));

        canvas.drawRect(mRoundBlockWidth * 2f + mLineOffset, mLineOffset,
                mRoundBlockWidth * 4f + mLineOffset,
                mRoundBlockWidth * 1f + mLineOffset,
                mFillPaint
        );
        canvas.drawText("T", mCenterTextOffset.x, mCenterTextOffset.y, mCenterTextPaint);
        canvas.drawText("S", mCenterTextOffset.x,
                mCenterTextOffset.y + (mLineOffset + mRoundBlockWidth / 3f),
                mCenterTextPaint
        );
        canvas.drawText(text, mRightTextOffset.x, mRightTextOffset.y, mRightTextPaint);
    }

    public void drawWeekDay(Canvas canvas, Time time) {
        canvas.drawText(changeWeekDayToKanji(time.weekDay),
                mLeftTextOffset.x, mLeftTextOffset.y, mLeftTextPaint);
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
