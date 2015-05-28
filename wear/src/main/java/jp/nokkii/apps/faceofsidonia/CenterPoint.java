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
public class CenterPoint {
    Paint mLinePaint;
    Paint mBoldLinePaint;
    Paint mTextPaint;
    Paint.FontMetrics mFontMetrics;
    PointF mTLTextPoint, mTRTextPoint, mBLTextPoint, mBRTextPoint;

    float mRoundBlockWidth, mCenterBlockWidth, mLineOffset, mWatchWidth;
    float mCenterBoldLines[];
    int mAlertColor;
    int mRoundColor;
    int mBackgroundColor;

    public CenterPoint(FaceOfSidonia watch, int desiredMinimumWidth) {
        Resources resources = watch.getResources();
        float textSize = desiredMinimumWidth / 5f;
        Typeface typeface = Typeface.createFromAsset(watch.getAssets(), "mplus-1m-regular.ttf");
        mAlertColor = resources.getColor(R.color.alert);
        mRoundColor = resources.getColor(R.color.round);
        mBackgroundColor = resources.getColor(R.color.digital_background);

        mLineOffset = desiredMinimumWidth / 80f;
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f;
        mWatchWidth = desiredMinimumWidth;
        mCenterBlockWidth = ( desiredMinimumWidth - (mLineOffset * 2f + mRoundBlockWidth * 2f) ) / 2f;

        mTextPaint = new Paint();

        mTextPaint.setTypeface(typeface);
        mTextPaint.setTextSize(textSize);
        mFontMetrics = mTextPaint.getFontMetrics();
        mTextPaint.setColor(mAlertColor);

        float halfTextWidth = mTextPaint.measureText("０") / 2f;
        float halfTextHeight = (mFontMetrics.ascent + mFontMetrics.descent) / 2f;
        float top = mLineOffset + mRoundBlockWidth + mCenterBlockWidth / 2f;
        mTLTextPoint = new PointF(top - halfTextWidth, top - halfTextHeight);
        mTRTextPoint = new PointF(top + mCenterBlockWidth - halfTextWidth, top - halfTextHeight);
        mBLTextPoint = new PointF(top - halfTextWidth, top + mCenterBlockWidth - halfTextHeight);
        mBRTextPoint = new PointF(top + mCenterBlockWidth - halfTextWidth, top + mCenterBlockWidth - halfTextHeight);

        // Line
        mBoldLinePaint = new Paint();
        mBoldLinePaint.setStrokeWidth(mLineOffset / 2f);

        mLinePaint = watch.createTextPaint(resources.getColor(R.color.round));
        mLinePaint.setStrokeWidth(mLineOffset / 4f);

        mFillPaint = new Paint();
        mFillPaint.setColor(mBackgroundColor);
        mFillPaint.setAntiAlias(true);

        mCenterBoldLines = new float[] {
                // 縦線
                mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset,
                mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset,
                // 横線
                mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 1f + mLineOffset,
                mRoundBlockWidth * 1f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset, mRoundBlockWidth * 4f + mLineOffset,
        };
    }

    public void setAntiAlias(Boolean mode){
        mTextPaint.setAntiAlias(mode);
    }

    public void drawTime(Canvas canvas, Time time) {
        mBoldLinePaint.setColor(mAlertColor);
        canvas.drawLine(mRoundBlockWidth * 1f + mLineOffset, mWatchWidth / 2f, mRoundBlockWidth * 4f + mLineOffset, mWatchWidth / 2f, mBoldLinePaint);
        canvas.drawLine(mWatchWidth / 2f, mRoundBlockWidth * 1f + mLineOffset, mWatchWidth / 2f, mRoundBlockWidth * 4f + mLineOffset, mBoldLinePaint);
        canvas.drawLines(mCenterBoldLines, mBoldLinePaint);
        canvas.drawText(changeKanji(time.hour / 10)  , mTLTextPoint.x, mTLTextPoint.y, mTextPaint);
        canvas.drawText(changeKanji(time.hour % 10)  , mTRTextPoint.x, mTRTextPoint.y, mTextPaint);
        canvas.drawText(changeKanji(time.minute / 10), mBLTextPoint.x, mBLTextPoint.y, mTextPaint);
        canvas.drawText(changeKanji(time.minute % 10), mBRTextPoint.x, mBRTextPoint.y, mTextPaint);
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
