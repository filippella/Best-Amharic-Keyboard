package org.dalol.bestamharickeyboard.core.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.dalol.bestamharickeyboard.core.R;
import org.dalol.bestamharickeyboard.core.internal.GridVirtualKeyboardManager;
import org.dalol.bestamharickeyboard.core.internal.VirtualKeyInfo;
import org.dalol.bestamharickeyboard.core.internal.VirtualKeyPreviewPopup;
import org.dalol.bestamharickeyboard.core.internal.VirtualKeyboardManager;
import org.dalol.bestamharickeyboard.core.models.VirtualKey;
import org.dalol.bestamharickeyboard.core.models.VirtualKeyboard;
import org.dalol.bestamharickeyboard.core.models.callback.OnVirtualKeyboardActionListener;
import org.dalol.bestamharickeyboard.core.models.common.AbstractRunnable;
import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 17:54.
 */
public class VirtualKeyboardView extends View implements Handler.Callback {

    private static final String TAG = VirtualKeyboardView.class.getSimpleName();

    private static final long DELAY_LONG_PRESS = ViewConfiguration.getLongPressTimeout();
    private static final long DELAY_TAP = ViewConfiguration.getTapTimeout();

    private static final int MSG_SEND_PRESSED_KEY = 0;

    private static final int MESSAGE_HANDLE_KEY_PRESS = 1;
    private static final int MESSAGE_SHOW_PREVIEW = 2;
    private static final int MESSAGE_REMOVE_PREVIEW = 3;
    private static final int MESSAGE_LONG_PRESS = 4;

    private static final int DELAY_AFTER_PREVIEW = 20;
    private static final int REPEAT_INTERVAL = 50; // ~20 keys per second
    private static final int REPEAT_START_DELAY = 400;

    private final List<VirtualKeyInfo> mKeys = new ArrayList<>();
    private final SparseArray<VirtualKeyInfo> mPressedKeys = new SparseArray<>();
    private final SparseArray<VirtualKeyPreviewPopup> mPreviewPopupCache = new SparseArray<>();
    private final Rect mDirtyRect = new Rect();
    private final PointF lastPoint = new PointF();

    private Context mContext;
    private AccessibilityManager mAccessibilityManager;
    private Vibrator mVibrator;
    private AudioManager mAudioManager;

    private VirtualKeyboard mKeyboard;
    private VirtualKeyboardManager mKeyboardManager;
    private OnVirtualKeyboardActionListener mActionListener;
    private int mKeyMargin;
    private int mKeyboardHeight;

    private boolean mVirtualKeyboardChanged;

    private Bitmap mBuffer;
    private Canvas mCanvas;
    private Paint mTextPaint;
    private VirtualKeyInfo mInvalidatedKey;
    private boolean mDrawPending;
    private Handler mHandler;

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
        mAccessibilityManager = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        mKeyboardManager = new GridVirtualKeyboardManager(context);

        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

//        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setTextSize(100f);
//        mPaint.setColor(Color.BLUE);
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(CoreUtils.toSp(20f));
//        mTextPaint.setTextSize(35f);
//        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setColor(Color.WHITE);

//        setBackgroundColor(Color.parseColor("#D0D5DB"));
        //setBackgroundColor(Color.parseColor("#1C1F27"));
    }

    public void setVirtualKeyboard(@NonNull VirtualKeyboard keyboard) {
        mKeyboard = keyboard;
        mKeys.clear();

        mKeyMargin = CoreUtils.toPx(mKeyboard.getKeyMargin());
        mKeyboardHeight = CoreUtils.toPx(mKeyboard.getKeyboardHeight());

        mVirtualKeyboardChanged = true;

        requestLayout();

        invalidate();
    }

    public void setOnVirtualKeyboardActionListener(OnVirtualKeyboardActionListener actionListener) {
        mActionListener = actionListener;
    }

    public void setKeyboardManager(@NonNull VirtualKeyboardManager keyboardManager) {
        if (mKeyboardManager != null) throw new IllegalArgumentException(
                "VirtualKeyboardManager shouldn't be null."
        );
        mKeyboardManager = keyboardManager;
    }

    public boolean requiresModifierBar() {
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        showLog("onAttachedToWindow()");
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper(),this);
        }
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

        if (mKeyboardManager != null) {
            mKeyboardManager.populateKeys(mKeyboard, width, getPaddingLeft(), getPaddingTop(), mKeys);
        }

        showLog("onSizeChanged() -> " + width + " - " + h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null == mKeyboard) {
            setMeasuredDimension(
                    getPaddingLeft() + getPaddingRight(),
                    getPaddingTop() + getPaddingBottom()
            );
        } else {

            final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

            showLog("onMeasure() -> " + widthSize + " - " + heightSize);

            int totalVerticalMargin = mKeyMargin * (mKeyboard.getKeysRowCount() + 1);
            int keyboardHeight = mKeyboardHeight + totalVerticalMargin +
                    getPaddingTop() + getPaddingBottom();

            setMeasuredDimension(widthSize, keyboardHeight);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int actionMasked = event.getActionMasked();

        long start = System.currentTimeMillis();

        boolean result = false;

        switch (actionMasked) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_DOWN");

                int downPointerIndex = event.getActionIndex();

                float x = event.getX(downPointerIndex);
                float y = event.getY(downPointerIndex);

                int downPointerId = event.getPointerId(downPointerIndex);

                releasePressedKey(downPointerId);

                VirtualKeyInfo downPressedKey = getPressedKey(x, y, downPointerId);

                if (downPressedKey != null) {
                    vibrate();
                    //SoundEffectConstants.CLICK
                    playStandardClick(AudioManager.FX_KEY_CLICK);
                    if (mActionListener != null) {
                        mActionListener.onPress("-");
                    }
                }

                result = true;
                break;
            case MotionEvent.ACTION_MOVE:

                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_MOVE");

                int pointerCount = event.getPointerCount();

//                mHandler.removeMessages(MESSAGE_REMOVE_PREVIEW);

                for (int movePointerIndex = 0; movePointerIndex < pointerCount; movePointerIndex++) {

                    int movePointerId = event.getPointerId(movePointerIndex);

                    float xPos = event.getX(movePointerIndex);
                    float yPos = event.getY(movePointerIndex);

                    VirtualKeyInfo keyInfo = mPressedKeys.get(movePointerId);

                    if (keyInfo != null) {
                        if (!keyInfo.isEventInBounds(xPos, yPos)) {

                            keyInfo.setPressed(false);
                            invalidateVirtualKey(keyInfo);
                            mPressedKeys.remove(movePointerId);

//                            mHandler.sendMessageDelayed(
//                                    mHandler.obtainMessage(MESSAGE_REMOVE_PREVIEW, movePointerId, -1),
//                                    DELAY_AFTER_PREVIEW
//                            );

                            VirtualKeyInfo movePressedKey = getPressedKey(xPos, yPos, movePointerId);

                            if (movePressedKey != null) {
                                playStandardClick(AudioManager.FX_KEYPRESS_STANDARD);
                            }
                        }
                    } else {

//                        mHandler.sendMessageDelayed(
//                                mHandler.obtainMessage(MESSAGE_REMOVE_PREVIEW, movePointerId, -1),
//                                DELAY_AFTER_PREVIEW
//                        );

                        //needs pressing
                        VirtualKeyInfo movePressedKey = getPressedKey(xPos, yPos, movePointerId);
                        if (movePressedKey != null) {
                            playStandardClick(AudioManager.FX_KEYPRESS_STANDARD);
                        }
                    }
                }

                result = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:

                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_UP");

                int upPointerIdIndex = event.getActionIndex();
                int upPointerId = event.getPointerId(upPointerIdIndex);

                post(new AbstractRunnable<Integer>(upPointerId) {

                    @Override
                    protected void run(Integer subject) {
                        releasePressedKey(subject);


                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(MESSAGE_REMOVE_PREVIEW, subject, -1),
                                DELAY_AFTER_PREVIEW
                        );
                    }
                });

                result = true;
                break;
        }

        long elapsed = System.currentTimeMillis() - start;

        showLog("KEY_ACTION onTouchEvent() -> elapsed second: " + (elapsed * 1.0f / 1000L));

        return result;
    }

    private void playStandardClick(int clickEffect) {
        mAudioManager.playSoundEffect(clickEffect);
    }

    private void removeMessages() {
        if (mHandler != null) {
            mHandler.removeMessages(MESSAGE_SHOW_PREVIEW);
        }
    }

    private void vibrate() {
        if (mVibrator.hasVibrator()) {
//            long[] pattern = {/* start delay */ 0, /* vibrate delay */ 1};
//            mVibrator.vibrate(pattern, -1);
            mVibrator.vibrate(1);
        }
    }

    @Nullable
    private VirtualKeyInfo getPressedKey(float x, float y, int pointerId) {
        VirtualKeyInfo pressedKeyInfo = null;
        for (int i = 0, keyCount = mKeys.size(); i < keyCount; i++) {
            VirtualKeyInfo keyInfo = mKeys.get(i);
            if (keyInfo.isEventInBounds(x, y)) {
                keyInfo.setPressed(true);
                pressedKeyInfo = keyInfo;
                mPressedKeys.put(pointerId, keyInfo);
                invalidateVirtualKey(keyInfo);
                mHandler.sendMessageDelayed(
                        mHandler.obtainMessage(MESSAGE_SHOW_PREVIEW, pointerId, 0),
                        0
                );
                break;
            }
        }
        return pressedKeyInfo;
    }

    private void releasePressedKey(int pointerId) {

        VirtualKeyInfo keyInfo = mPressedKeys.get(pointerId);

        if (keyInfo != null) {
            keyInfo.setPressed(false);
            invalidateVirtualKey(keyInfo);
            mPressedKeys.remove(pointerId);

            if (mActionListener != null) {
                mActionListener.onKey(0, keyInfo.key.getKeyLabel());
                mActionListener.onRelease(keyInfo.key.getKeyLabel());
            }

            showLog("releasePressedKey " + keyInfo.key.getKeyLabel());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBuffer == null || mVirtualKeyboardChanged || mDrawPending) {
            onDrawBuffer();
        }
        canvas.drawBitmap(mBuffer, 0, 0, null);
    }

    private void onDrawBuffer() {
        if (mBuffer == null || mVirtualKeyboardChanged) {
            final int width = Math.max(1, getWidth());
            final int height = Math.max(1, getHeight());
            mBuffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBuffer);
            invalidateVirtualKeys();
            mVirtualKeyboardChanged = false;
        }

        mCanvas.save();
        mCanvas.clipRect(mDirtyRect);

        mCanvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);

        final VirtualKeyInfo invalidKey = mInvalidatedKey;

        if (invalidKey == null) {
            for (VirtualKeyInfo keyInfo : mKeys) {
                drawKey(keyInfo);
            }
        } else {
            drawKey(invalidKey);
        }

        mInvalidatedKey = null;

        mCanvas.restore();
        mDrawPending = false;
        mDirtyRect.setEmpty();
    }

    private void drawKey(VirtualKeyInfo keyInfo) {
        int x = keyInfo.xPos;
        int y = keyInfo.yPos;
        int width = keyInfo.width;
        int height = keyInfo.height;

        VirtualKey key = keyInfo.key;

        Drawable drawable = key.keyBackground;

        if (drawable != null) {
            final Rect bounds = drawable.getBounds();
            if (width != bounds.right || height != bounds.bottom) {
                drawable.setBounds(x, y, x + width, y + height);
            }
            //mCanvas.translate(x, y);
            //mCanvas.translate(x + width, y + height);
            drawable.draw(mCanvas);

            //mCanvas.translate(-x, -y);
        }

        String label = key.getKeyLabel();

        //int textSize = Math.min(width, height);


        drawTextCentered(label, x + (width / 2), y + (height / 2), mCanvas);

    }

    public void invalidateVirtualKey(VirtualKeyInfo key) {
        if (key == null) return;

        int xPos = key.xPos;
        int yPos = key.yPos;

        int width = key.width;
        int height = key.height;

        mInvalidatedKey = key;
        mDirtyRect.union(xPos, yPos, xPos + width, yPos + height);
        onDrawBuffer();
        invalidate(xPos, yPos, xPos + width, yPos + key.height);
    }

    private void invalidateVirtualKeys() {
        mDirtyRect.union(0, 0, getWidth(), getHeight());
        mDrawPending = true;
        invalidate();
    }

    private void drawTextCentered(String text, int xPos, int yPos, Canvas canvas) {
//        int xPos = x - (int)(mTextPaint.measureText(text)/2);
//        int yPos = (int) (y - ((mTextPaint.descent() + mTextPaint.ascent()) / 2)) ;
//        canvas.drawText(text, xPos, yPos, mTextPaint);

        //int xPos = (canvas.getWidth() / 2);

        yPos = (int) (yPos - ((mTextPaint.descent() + mTextPaint.ascent()) / 2));
//        yPos = (int) (yPos - ((mTextPaint.getTextSize() + mTextPaint.descent()) / 2));
        //((textPaint.descent() + textPaint.ascent()) / 2) is the distance from the baseline to the center.

        canvas.drawText(text, xPos, yPos, mTextPaint);
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//
//        showLog("dispatchDraw()");
//
//        int size = CoreUtils.toPx(0.5f);
//
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.BLACK);
//
//        paint.setStrokeWidth(size);
//
//        int width = getWidth();
//        int height = getHeight();
//
//        canvas.drawRect(new RectF(size, size, width - size, height - size), paint);
//
//        canvas.drawRect(new RectF(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(), height - getPaddingBottom()), paint);
//
//
//        int centerX = width / 2;
//        int centerY = height / 2;
//
//        paint.setColor(Color.YELLOW);
//        canvas.drawLine(centerX, 0, centerX, height, paint);
//        canvas.drawLine(0, centerY, width, centerY, paint);
//    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        showLog("onDetachedFromWindow()");
        dismissKeyPreviewPopups();
        mPreviewPopupCache.clear();
        mHandler = null;
    }

    private void dismissKeyPreviewPopups() {
        for (int i = 0, size = mPreviewPopupCache.size(); i < size; i++) {
            VirtualKeyPreviewPopup popup = mPreviewPopupCache.get(i);
            if (popup != null && popup.isShowing()) {
                popup.dismiss();
            }
        }
    }

    private void dismissKeyPreviewPopup(int touchPointerId) {

        VirtualKeyPreviewPopup previewPopup = mPreviewPopupCache.get(touchPointerId);

        showLog("SHOWING_POPUP-8");
        if (previewPopup != null && previewPopup.isShowing()) {
            showLog("SHOWING_POPUP-9");
            //previewPopup.hide();
            previewPopup.dismiss();
            showLog("SHOWING_POPUP-10");
        }
        showLog("SHOWING_POPUP-11");
    }

    private void showKeyPreviewPopup(int touchPointerId) {

        showLog("SHOWING_POPUP-1");

        VirtualKeyInfo keyInfo = mPressedKeys.get(touchPointerId);

        if (keyInfo != null) {

            showLog("SHOWING_POPUP-2");

            VirtualKeyPreviewPopup popup = mPreviewPopupCache.get(touchPointerId);

            showLog("SHOWING_POPUP-3");

            if (popup == null) {

                showLog("SHOWING_POPUP-4");

                TextView textView = (TextView) inflate(mContext, R.layout.default_key_preview_layout, null);
                popup = new VirtualKeyPreviewPopup(textView);
                popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        showLog("SHOWING_POPUP-7");
                    }
                });
                mPreviewPopupCache.put(touchPointerId, popup);
            }

            showLog("SHOWING_POPUP-5");

            popup.showKeyPreview(this, keyInfo, mKeyMargin);

            showLog("SHOWING_POPUP-6");
        }
    }

    private void dismissAllPreviewPopups() {
        int size = mPreviewPopupCache.size();
        for (int i = 0; i < size; i++) {
            VirtualKeyPreviewPopup popup = mPreviewPopupCache.get(i);
            if (popup != null && popup.isShowing()) {
                popup.dismiss();
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MESSAGE_HANDLE_KEY_PRESS:
                VirtualKeyInfo key = mKeys.get(msg.arg1);
                if (key != null) {
                    invalidateVirtualKey(key);
                }
                break;
            case MESSAGE_SHOW_PREVIEW:
                showKeyPreviewPopup(msg.arg1);
                break;
            case MESSAGE_REMOVE_PREVIEW:
                releasePressedKey(msg.arg1);
                dismissKeyPreviewPopup(msg.arg1);
                break;
        }
        return false;
    }
}
