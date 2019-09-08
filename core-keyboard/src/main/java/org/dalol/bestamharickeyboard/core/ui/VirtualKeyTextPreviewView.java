package org.dalol.bestamharickeyboard.core.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;

import org.dalol.bestamharickeyboard.core.R;
import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 26/05/2019 at 14:01.
 */
public class VirtualKeyTextPreviewView extends AppCompatTextView {

    private Paint mPaint;
    private Path mPath;
    private RectF mBounds;
    private int mPadding, mCenterX, mArrowHeight, mBoundsCorner;

    public VirtualKeyTextPreviewView(Context context) {
        super(context);
        doInit(context);
    }

    public VirtualKeyTextPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        doInit(context);
    }

    public VirtualKeyTextPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doInit(context);
    }

    private void doInit(Context context) {

        mArrowHeight = CoreUtils.toPx(8f);
        mBoundsCorner = CoreUtils.toPx(5f);
        mPadding = CoreUtils.toPx(3f);

        mPath = new Path();
        mBounds = new RectF();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#3f3e50"));
        mPaint.setStyle(Paint.Style.FILL);
        //mPaint.setColor(Color.parseColor("#ffffff"));
        // mPaint.setShadowLayer(15.0f, 0.0f, 0.0f, Color.parseColor("#000000"));

        //setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        setGravity(Gravity.CENTER);
        setTextSize(30f);

        setTextColor(Color.WHITE);
        setTypeface(null, Typeface.BOLD);
        setBackgroundResource(R.drawable.default_key_preview_background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mBounds = new RectF(0, 0, w, h - mArrowHeight);

        //setTextSize(TypedValue.COMPLEX_UNIT_DIP, w);


        //setPadding(mPadding, mPadding, mPadding, mPadding);
        //setPadding(mPadding, mPadding, mPadding, mPadding + mArrowHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        if (!TextUtils.isEmpty(getText())) {
//            mPath.reset();
//            mPath.addRoundRect(mBounds, mBoundsCorner, mBoundsCorner, Path.Direction.CW);
//            mPath.moveTo(mCenterX - mArrowHeight, mBounds.bottom);
//            mPath.lineTo(mCenterX, getHeight());
//            mPath.lineTo(mCenterX + mArrowHeight, mBounds.bottom);
//            mPath.close();
//            canvas.drawPath(mPath, mPaint);
//        }

        super.onDraw(canvas);
    }
}
