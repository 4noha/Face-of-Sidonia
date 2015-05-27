package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.format.Time;

/**
 * Created by nokkii on 2015/05/20.
 */
public class Background {
    Paint mLinePaint;
    Paint mBoldLinePaint;

    float mRoundBlockWidth;
    float mLineOffset;
    float mBackGroundLines[];
    float mBackGroundBoldLines[];

    public Background(FaceOfSidonia watch, int desiredMinimumWidth) {
        Resources resources = watch.getResources();

        // Line
        mBoldLinePaint = new Paint();
        mBoldLinePaint = watch.createTextPaint(resources.getColor(R.color.round));
        mBoldLinePaint.setStrokeWidth(2.0f);

        mLinePaint = watch.createTextPaint(resources.getColor(R.color.round));
        mLinePaint.setStrokeWidth(1.1f);

        mLineOffset = desiredMinimumWidth / 80f;
        mRoundBlockWidth = (desiredMinimumWidth - mLineOffset * 2f) / 5f;

        // コンパイラでListから静的なArrayにならないので直書き
        mBackGroundLines = new float[] {
                // 縦線
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4,
                // 横線
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 4,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 4,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 4,
        };

        mBackGroundBoldLines = new float[] {
                // 縦棒
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0 + 8,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 8,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 3, // 内側
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 3, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 8,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 8,
                // mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2 + 8,
                // mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 8,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 8,
                // mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3 + 8,
                // mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 8,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 8,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 4 + 8, // 内側
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 4 + 8, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 8,
                mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5 + 8,
                mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 8,
                mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 8,
                mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 8,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 8,
                mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5 + 8,
                // 横棒
                mRoundBlockWidth * 0, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 0, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 0, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 2 + 4,
                mRoundBlockWidth * 0, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 0, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 0, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 0 + 8, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 1, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 1, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 1, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 1 + 3, mRoundBlockWidth * 2 + 4, // 内側
                mRoundBlockWidth * 1, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 1 + 3, mRoundBlockWidth * 3 + 4, // 内側
                mRoundBlockWidth * 1, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 1, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 1 + 8, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 2, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 2, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 1 + 4,
                // mRoundBlockWidth * 2, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 2 + 4,
                // mRoundBlockWidth * 2, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 2, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 2, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 2 + 8, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 3, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 3, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 1 + 4,
                // mRoundBlockWidth * 3, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 2 + 4,
                // mRoundBlockWidth * 3, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 3, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 3, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 3 + 8, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 4, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 4, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 2 + 5, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 2 + 4, // 内側
                mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 3 + 5, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 3 + 4, // 内側
                mRoundBlockWidth * 4, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 4, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 4 + 8, mRoundBlockWidth * 5 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 0 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 0 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 1 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 1 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 2 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 2 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 3 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 3 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 4 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 4 + 4,
                mRoundBlockWidth * 5, mRoundBlockWidth * 5 + 4, mRoundBlockWidth * 5 + 8, mRoundBlockWidth * 5 + 4,
        };
    }

    public void setAntiAlias(Boolean mode){
        mLinePaint.setAntiAlias(mode);
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawLines(mBackGroundLines, mLinePaint);
        canvas.drawLines(mBackGroundBoldLines, mBoldLinePaint);
    }
}
