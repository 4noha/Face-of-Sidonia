package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;

/**
 * Created by nokkii on 2015/05/20.
 */
public class LeftSide {
    Paint mFillPaint;
    Paint mNumTextPaint;
    Paint mTextPaint;

    int mBackgroundColor, mRoundColor, mChargingColor;
    float mRoundBlockWidth, mLineOffset;
    PointF mTopBlockOffset, mTopTextPoint, mTopNumPoint;

    public LeftSide(FaceOfSidonia watch, int desiredMinimumWidth) {
        Resources resources = watch.getResources();

        float numTextSize = desiredMinimumWidth / 5.5f;
        float textSize    = desiredMinimumWidth / 5.5f;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");

        // 塗りつぶし用
        mFillPaint       = new Paint();
        mLineOffset      = desiredMinimumWidth / 80f;
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f;
        mTopBlockOffset  = new PointF(mLineOffset, mRoundBlockWidth + mLineOffset);

        mRoundColor = resources.getColor(R.color.round);
        mFillPaint.setColor(mRoundColor);
        mFillPaint.setAntiAlias(true);

        // 数字用
        mNumTextPaint    = new Paint();
        mBackgroundColor = resources.getColor(R.color.digital_background);

        mNumTextPaint.setTypeface(typeface);
        mNumTextPaint.setColor(mBackgroundColor);
        mNumTextPaint.setTextSize(numTextSize);
        mNumTextPaint.setAntiAlias(true);

        // 文字用
        mTextPaint = new Paint();
        typeface = Typeface.createFromAsset(watch.getAssets(), "mplus-1m-regular.ttf");
        mChargingColor = resources.getColor(R.color.battery_charge);

        mTextPaint.setTypeface(typeface);
        mTextPaint.setColor(mRoundColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setAntiAlias(true);

        // 上
        // mplus-1m-regular 漢字一字
        float halfTextWidth = mTextPaint.measureText("電") / 2f;
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f;
        mTopTextPoint = new PointF(mLineOffset + mRoundBlockWidth / 2 - halfTextWidth,
                mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2 - halfTextHeight);
        // Browing 数字2字
        halfTextWidth = mNumTextPaint.measureText("00") / 2f;
        fontMetrics = mTextPaint.getFontMetrics();
        halfTextHeight = (fontMetrics.ascent + fontMetrics.descent) / 2f;
        mTopNumPoint = new PointF(mLineOffset + mRoundBlockWidth / 2f - halfTextWidth,
                mLineOffset + mRoundBlockWidth + mRoundBlockWidth / 2f - halfTextHeight);
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
        // mNumTextPaint.setAntiAlias(mode);
    }

    public void drawBatteryPct(Canvas canvas, int batteryPct, boolean ambient, boolean isCharging) {
        if ( ambient ) {
            canvas.drawRect(mTopBlockOffset.x, mTopBlockOffset.y,
                    mTopBlockOffset.x + mRoundBlockWidth,
                    mTopBlockOffset.y + mRoundBlockWidth, mFillPaint);
            canvas.drawText(String.format("%02d", batteryPct != 100 ? batteryPct : 0), mTopNumPoint.x, mTopNumPoint.y, mNumTextPaint);
        } else {
            if ( isCharging ) {
                mTextPaint.setColor(mChargingColor);
                canvas.drawText("電", mTopTextPoint.x - 2f, mTopTextPoint.y, mTextPaint);
                mTextPaint.setColor(mRoundColor);
            } else {
                canvas.drawText("電", mTopTextPoint.x - 2f, mTopTextPoint.y, mTextPaint);
            }
        }
    }
}
