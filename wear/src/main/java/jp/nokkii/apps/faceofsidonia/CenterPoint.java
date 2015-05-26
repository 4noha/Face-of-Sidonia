package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.Time;

/**
 * Created by nokkii on 2015/05/20.
 */
public class CenterPoint {
    Paint mLinePaint;
    Paint mBoldLinePaint;
    Paint mTextPaint;

    float mTextXOffset, mTextYOffset;
    float mLineXOffset, mLineYOffset, mWidth, mHeight;
    float mRoundBlockWidth, mWatchWidth;

    float mCenterBoldLines[];

    public CenterPoint(FaceOfSidonia watch) {
        Resources resources = watch.getResources();
        float textSize = resources.getDimension(R.dimen.digital_text_size);
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "mplus-1m-regular.ttf");

        mTextPaint = new Paint();
        mTextXOffset = resources.getDimension(R.dimen.digital_x_offset);
        mTextYOffset = resources.getDimension(R.dimen.digital_y_offset);
        mTextPaint = watch.createTextPaint(resources.getColor(R.color.alert));

        mTextPaint.setTypeface(typeface);
        mTextPaint.setTextSize(textSize);

        // Line
        mBoldLinePaint = new Paint();
        mRoundBlockWidth = resources.getDimension(R.dimen.round_block_width);
        mWatchWidth = resources.getDimension(R.dimen.watch_width);
        mWidth = resources.getDimension(R.dimen.center_width);
        mHeight = resources.getDimension(R.dimen.center_height);
        mBoldLinePaint = watch.createTextPaint(resources.getColor(R.color.alert));
        mBoldLinePaint.setStrokeWidth(2.0f);

        mLinePaint = watch.createTextPaint(resources.getColor(R.color.round));
        mLinePaint.setStrokeWidth(1.0f);

        mCenterBoldLines = new float[] {
                // 縦線
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 4,
                // 横線
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 4,
        };
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
    }

    public void drawTime(Canvas canvas, Time time) {
        canvas.drawLine(mRoundBlockWidth * 1 + 4, mWatchWidth / 2.0f, mRoundBlockWidth * 4 + 4, mWatchWidth / 2.0f, mBoldLinePaint);
        canvas.drawLine(mWatchWidth / 2.0f, mRoundBlockWidth * 1 + 4, mWatchWidth / 2.0f, mRoundBlockWidth * 4 + 4, mBoldLinePaint);
        canvas.drawLines(mCenterBoldLines, mBoldLinePaint);

        canvas.drawText(changeKanji(time.hour / 10), mTextXOffset, mTextYOffset, mTextPaint);
        canvas.drawText(changeKanji(time.hour % 10), mTextXOffset + 91, mTextYOffset, mTextPaint);
        canvas.drawText(changeKanji(time.minute / 10), mTextXOffset, mTextYOffset + 91, mTextPaint);
        canvas.drawText(changeKanji(time.minute % 10), mTextXOffset + 91, mTextYOffset + 91, mTextPaint);
    }

    }

    private static String changeKanji(int num) {
        String c = "";

        switch (num) {
            case 0:
                c = "０";
                break;
            case 1:
                c = "１";
                break;
            case 2:
                c = "２";
                break;
            case 3:
                c = "３";
                break;
            case 4:
                c = "４";
                break;
            case 5:
                c = "５";
                break;
            case 6:
                c = "６";
                break;
            case 7:
                c = "７";
                break;
            case 8:
                c = "８";
                break;
            case 9:
                c = "９";
                break;
        }
        return c;
    }
}
