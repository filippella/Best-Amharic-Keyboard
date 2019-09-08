package org.dalol.bestamharickeyboard.core.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.dalol.bestamharickeyboard.core.R;
import org.dalol.bestamharickeyboard.core.models.VirtualKey;
import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;

import static android.view.View.MeasureSpec;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 20:24.
 */
public class VirtualKeyInfo {

    private static final int MSG_SHOW_PREVIEW = 0;
    private static final int MSG_REMOVE_PREVIEW = 1;

    private final static int[] KEY_STATE_NORMAL = {
    };

    private final static int[] KEY_STATE_PRESSED = {
            android.R.attr.state_pressed
    };
//    private final View mParent;

    public final VirtualKey key;
    private final Context mContext;
    private PopupWindow mPreviewPopup;

    public int width, height;
    public int keyId = -1;
    public int xPos, yPos;
//    public String label;
    public int icon;//if icon then no label on the key
    public boolean locked;//caps lock or shift
    private boolean pressed;//key state
    public boolean reTypeOnHold;
    private TextView mPreviewText;

//    private Handler mHandler;
    private int mPopupPreviewX, mPopupPreviewY;

    public VirtualKeyInfo(Context context, VirtualKey vk) {
//        mParent = parent;
        mContext = context;
        key = vk;
    }

    public boolean isEventInBounds(float x, float y) {
        return (x > xPos && x < (xPos + width)) && (y > yPos && y < (yPos + height));
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
        int[] drawableState = pressed ? KEY_STATE_PRESSED : KEY_STATE_NORMAL;
        key.keyBackground.setState(drawableState);
//        if(pressed) {
//            showKeyPreview();
//        } else {
//            dismissKeyPreview();
//        }
    }

//    private void showKeyPreview() {
//
//        if (mPreviewPopup == null) {
//            mPreviewPopup = new PopupWindow(mContext);
//            mPreviewPopup.setBackgroundDrawable(null);
//            mPreviewPopup.setWidth(ListPopupWindow.WRAP_CONTENT);
//            mPreviewPopup.setHeight(ListPopupWindow.WRAP_CONTENT);
////            mPreviewPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            mPreviewPopup.setTouchable(false);
//            mPreviewPopup.setClippingEnabled(false);
////            mPreviewPopup.setAnimationStyle(android.R.anim.fade_in);
//
//            mPreviewText = (TextView) LayoutInflater.from(mContext).inflate(R.layout.default_key_preview_layout, null);
//            mPreviewPopup.setContentView(mPreviewText);
//
//            mPreviewText.setText(key.getKeyLabel());
//
//            int measureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
//
//            mPreviewText.measure(measureSpec, measureSpec);
//
//            int popupWidth = mPreviewText.getMeasuredWidth();
//            int popupHeight = mPreviewText.getMeasuredHeight();
//
//            int[] xy = new int[2];
//            mParent.getLocationInWindow(xy);
//
//            int parentX = xy[0];
//            int parentY = xy[1];
//
//            int dx = Math.round(xPos + (width * 1.0f / 2) - (popupWidth * 1.0f / 2));
//            int dy = (yPos) - popupHeight;
//
//            mPopupPreviewX = Math.round(parentX + dx);
//            mPopupPreviewY = Math.round(parentY + (dy - CoreUtils.toPx(10f)));
//        }
//
////        if (mPreviewPopup.isShowing()) {
////            mPreviewPopup.update(x, y, popupWidth, popupHeight);
////        } else {
////            mPreviewPopup.setWidth(popupWidth);
////            mPreviewPopup.setHeight(popupHeight);
////
////        }
//
//        mPreviewPopup.showAtLocation(mParent, Gravity.NO_GRAVITY, mPopupPreviewX, mPopupPreviewY);
//    }

    private void dismissKeyPreview() {
        if (mPreviewPopup.isShowing()) {
            mPreviewPopup.dismiss();
        }
    }

    public boolean isPressed() {
        return pressed;
    }

    public void onDestroy() {
//        mHandler = null;
    }
}
