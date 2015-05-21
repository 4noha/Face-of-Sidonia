package jp.nokkii.apps.faceofsidonia;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.format.Time;

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

    public void drawTime(Canvas canvas,Time time) {
        canvas.drawText(changeKanji(time.hour/10)  , mTextXOffset   , mTextYOffset   , mTextPaint);
        canvas.drawText(changeKanji(time.hour%10)  , mTextXOffset+91, mTextYOffset   , mTextPaint);
        canvas.drawText(changeKanji(time.minute/10), mTextXOffset   , mTextYOffset+91, mTextPaint);
        canvas.drawText(changeKanji(time.minute%10), mTextXOffset+91, mTextYOffset+91, mTextPaint);
    }

    private String changeKanji(int num) {
        String c = "";

        switch (num) {
            case 0:
                c = "零";
                break;
            case 1:
                c = "壱";
                break;
            case 2:
                c = "弐";
                break;
            case 3:
                c = "参";
                break;
            case 4:
                c = "肆";
                break;
            case 5:
                c = "伍";
                break;
            case 6:
                c = "陸";
                break;
            case 7:
                c = "漆";
                break;
            case 8:
                c = "捌";
                break;
            case 9:
                c = "玖";
                break;
        }
        return c;
    }
}
