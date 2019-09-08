package org.dalol.bestamharickeyboard.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ViewUtils;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 17:54.
 */
public class VirtualKeyboardView extends View implements ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = VirtualKeyboardView.class.getSimpleName();

    private static final long DELAY_LONG_PRESS = ViewConfiguration.getLongPressTimeout();
    private static final long DELAY_TAP = ViewConfiguration.getTapTimeout();
    private static final int MESSAGE_REMOVE_PREVIEW = 2;
    private static final int MESSAGE_LONG_PRESS = 4;
    private static final int DELAY_AFTER_PREVIEW = 70;

    private final RectF mDirtyRect = new RectF();

    private AccessibilityManager mAccessibilityManager;
    private Resources mResources;
    private Context mContext;

    private VirtualKeyboard mKeyboard;
    private boolean mPreDrawRegistered;

    public VirtualKeyboardView(@NonNull Context context) {
        super(context);
        doInitialize(context, null);
    }

    public VirtualKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        doInitialize(context, attrs);
    }

    public VirtualKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doInitialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VirtualKeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        doInitialize(context, attrs);
    }

    private void doInitialize(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;
        mResources = getResources();
        mAccessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    public void setVirtualKeyboard(VirtualKeyboard keyboard) {
        mKeyboard = keyboard;

        requestLayout();

        mDirtyRect.union(0, 0, getWidth(), getHeight());
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showLog("onAttachedToWindow()");
        registerForPreDraw();
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        if (mAccessibilityManager.isTouchExplorationEnabled() && event.getPointerCount() == 1) {
            final int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_HOVER_ENTER: {
                    event.setAction(MotionEvent.ACTION_DOWN);
                }
                break;
                case MotionEvent.ACTION_HOVER_MOVE: {
                    event.setAction(MotionEvent.ACTION_MOVE);
                }
                break;
                case MotionEvent.ACTION_HOVER_EXIT: {
                    event.setAction(MotionEvent.ACTION_UP);
                }
                break;
            }
            return onTouchEvent(event);
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int width = w - (getPaddingLeft() + getPaddingRight());
        int height = h - (getPaddingTop() + getPaddingBottom());

        if (mKeyboard != null) {
            mKeyboardLayoutManager.measureKeyboard(mKeyboard, width, height);
        }


        showLog("onSizeChanged() -> " + width + " - " + h);
//        if (mKeyboard != null) {
//            mKeyboard.resize(w, h);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        showLog("onMeasure() -> " + widthSize + " - " + heightSize);

        int height = measureDimension(ViewUtils.toPx(100f), heightMeasureSpec);

        if (mKeyboard == null) {
            setMeasuredDimension(
                    getPaddingLeft() + getPaddingRight(),
                    getPaddingTop() + getPaddingBottom() + height
            );
        } else {

            int measuredWidth = widthSize - (getPaddingLeft() + getPaddingRight());
            int measuredHeight = getPaddingTop() + getPaddingBottom();
        }
    }

    private void registerForPreDraw() {
        if (!mPreDrawRegistered) {
            getViewTreeObserver().addOnPreDrawListener(this);
            mPreDrawRegistered = true;
        }
    }

    private void unregisterForPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);
        mPreDrawRegistered = false;
        //mPreDrawListenerDetached = false;
    }

    private int measureDimension(int desiredSize, int measureSpec) {
        int viewSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            viewSize = specSize;
        } else {
            viewSize = desiredSize;
            if (specMode == MeasureSpec.AT_MOST) {
                viewSize = Math.min(viewSize, specSize);
            }
        }

        if (viewSize < desiredSize){
            showLog("The view size is too small");
        }
        return viewSize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        showLog("onDraw()");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        showLog("dispatchDraw()");

        int size = ViewUtils.toPx(0.5f);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        paint.setStrokeWidth(size);

        int width = getWidth();
        int height = getHeight();

        canvas.drawRect(new RectF(size, size, width - size, height - size), paint);

        canvas.drawRect(new RectF(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom()), paint);


        int centerX = width / 2;
        int centerY = height / 2;

        paint.setColor(Color.BLUE);
        canvas.drawLine(centerX, 0, centerX, height, paint);
        canvas.drawLine(0, centerY, width, centerY, paint);
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }

    @Override
    public boolean onPreDraw() {

        unregisterForPreDraw();
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        unregisterForPreDraw();
        super.onDetachedFromWindow();

        showLog("onDetachedFromWindow()");
    }
}
