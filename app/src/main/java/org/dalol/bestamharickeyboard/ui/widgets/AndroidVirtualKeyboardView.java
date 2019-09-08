package org.dalol.bestamharickeyboard.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.uitilities.Utils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 15:11.
 */
public class AndroidVirtualKeyboardView extends KeyboardView {

    private static final String TAG = AndroidVirtualKeyboardView.class.getSimpleName();

    private Context mContext;
    private PopupWindow popup;

    public AndroidVirtualKeyboardView(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        doInitialize(context, attrs);
    }

    public AndroidVirtualKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        doInitialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AndroidVirtualKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        doInitialize(context, attrs);
    }

    private void doInitialize(@NonNull Context context, @Nullable AttributeSet attrs) {
        mContext = context;


    }

    @Override
    public void setKeyboard(Keyboard keyboard) {
        super.setKeyboard(keyboard);

        List<Keyboard.Key> keys = keyboard.getKeys();
        for (Keyboard.Key key : keys) {

            key.getCurrentDrawableState();
        }


        setOnKeyboardActionListener(new OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                showLog("KEY_ACTION onPress() -> " + primaryCode);
            }

            @Override
            public void onRelease(int primaryCode) {
                showLog("KEY_ACTION onRelease() -> " + primaryCode);
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                showLog("KEY_ACTION onKey() -> " + primaryCode);
            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
    }

    @Override
    protected boolean onLongPress(Keyboard.Key popupKey) {

        int[] xy = new int[2];
        getLocationInWindow(xy);

        final View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.popup_layout, new FrameLayout(mContext));
        popup = new PopupWindow(mContext);
        popup.setContentView(contentView);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        contentView.measure(
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
        );

        int measuredWidth = contentView.getMeasuredWidth();
        int measuredHeight = contentView.getMeasuredHeight();

        float keyX = (popupKey.x) + (popupKey.width * 1.0f / 2);
        float halfPopupWidth = measuredWidth * 1.0f / 2;

        float dx;
        int width = getWidth();
        if (keyX + halfPopupWidth > width) {
            dx = width - measuredWidth - Utils.toPx(8f);
        } else {
            dx = Math.max(keyX - halfPopupWidth, Utils.toPx(8f));
        }

        float keyY = (popupKey.y) - measuredHeight;

//        final int x = Math.round(keyX + contentView.getPaddingRight() + xy[0]);
//        final int x = Math.round(((getWidth() - measuredWidth) * 1.0f / 2) + xy[0]);
        final int x = Math.round(dx + xy[0]);
        final int y = Math.round(keyY + xy[1]) - (Utils.toPx(8) * 1);

        popup.showAtLocation(this, Gravity.NO_GRAVITY, x, y);
        return true;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        long start = System.currentTimeMillis();
        int ae = me.getAction() & MotionEvent.ACTION_MASK;
        switch (ae) {
            case MotionEvent.ACTION_DOWN:
                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_POINTER_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_MOVE");
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                showLog("KEY_ACTION onTouchEvent() -> MotionEvent.ACTION_UP");
                if (popup != null && popup.isShowing()) {
                    popup.dismiss();
                }
                break;
        }

        boolean onTouchEvent = super.onTouchEvent(me);

        long elapsed = System.currentTimeMillis() - start;
        showLog("KEY_ACTION onTouchEvent() -> elapsed second: " + (elapsed * 1.0f / 1000L));

        return onTouchEvent;
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }
}
