package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    float mTopTextXOffset, mTopTextYOffset;
    float mTopBlockXOffset, mTopBlockYOffset;

    public LeftSide(FaceOfSidonia watch, int desiredMinimumWidth) {
        Resources resources = watch.getResources();

        float numTextSize = resources.getDimension(R.dimen.status_left_text_size);
        float textSize = resources.getDimension(R.dimen.left_side_text_size);
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "Browning.ttf");

        // 塗りつぶし用
        mFillPaint = new Paint();
        mLineOffset = desiredMinimumWidth / 80f;
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f;
        mTopBlockXOffset = 3.0f;
        mTopBlockYOffset = mRoundBlockWidth + 3.0f;

        mRoundColor = resources.getColor(R.color.round);
        mFillPaint.setColor(mRoundColor);
        mFillPaint.setAntiAlias(true);

        // 数字用
        mNumTextPaint = new Paint();
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
        mTopTextXOffset = resources.getDimension(R.dimen.left_side_top_text_x_offset);
        mTopTextYOffset = resources.getDimension(R.dimen.left_side_top_text_y_offset);
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
        // mNumTextPaint.setAntiAlias(mode);
    }

    public void drawBatteryPct(Canvas canvas, int batteryPct, boolean ambient, boolean isCharging) {
        if ( ambient ) {
            canvas.drawRect(mTopBlockXOffset, mTopBlockYOffset, mTopBlockXOffset + mRoundBlockWidth + 1.0f, mTopBlockYOffset + mRoundBlockWidth + 1.0f, mFillPaint);
            canvas.drawText(String.format("%02d", batteryPct != 100 ? batteryPct : 0), mTopTextXOffset, mTopTextYOffset, mNumTextPaint);
        } else {
            if ( isCharging ) {
                mTextPaint.setColor(mChargingColor);
                canvas.drawText("電", mTopTextXOffset - 2, mTopTextYOffset, mTextPaint);
                mTextPaint.setColor(mRoundColor);
            } else {
                canvas.drawText("電", mTopTextXOffset - 2, mTopTextYOffset, mTextPaint);
            }
        }
    }
}
