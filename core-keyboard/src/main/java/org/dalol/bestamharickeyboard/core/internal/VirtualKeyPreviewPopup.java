package org.dalol.bestamharickeyboard.core.internal;

import android.view.Gravity;
import android.view.View;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;

import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.MeasureSpec.makeMeasureSpec;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 05/05/2019 at 16:34.
 */
public class VirtualKeyPreviewPopup extends PopupWindow {

    //    private final Context mContext;
    private final TextView mPreviewText;
    private final int mOffset;

    public VirtualKeyPreviewPopup(TextView contentView) {
        super(contentView);
        mOffset = CoreUtils.toPx(12f);
        setBackgroundDrawable(null);
        setTouchable(false);
        setFocusable(true);
        setClippingEnabled(false);

        setWidth(ListPopupWindow.WRAP_CONTENT);
        setHeight(ListPopupWindow.WRAP_CONTENT);

//        setAnimationStyle(0);

//        setBackgroundDrawable(new BitmapDrawable());
//        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        setAnimationStyle(android.R.anim.fade_in);

        setOutsideTouchable(true);
        setInputMethodMode(INPUT_METHOD_NOT_NEEDED);
        mPreviewText = contentView;
    }


//    public VirtualKeyPreviewPopup(Context context) {
//        super(context);
//        setBackgroundDrawable(null);
//        setTouchable(false);
//        setClippingEnabled(false);
//        setAnimationStyle(0);
//
////        mPreviewText = new VirtualKeyTextPreviewView(context);
////
//        mContext = context;
////        setTouchable(true);
////        setFocusable(true);
////        setOutsideTouchable(true);
////        setInputMethodMode(INPUT_METHOD_NOT_NEEDED);
//
////        setBackgroundDrawable(new BitmapDrawable());
////        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
////        LayoutInflater inflater = (LayoutInflater) context
////                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        mPreviewText = (TextView) inflater.inflate(contentResId, null);
//
////        setContentView(mPreviewText);
//
//        //setAnimationStyle(android.R.anim.fade_in);
//    }

    public void showKeyPreview(View parent, VirtualKeyInfo keyInfo, int keyMargin) {

        mPreviewText.setText(keyInfo.key.getKeyLabel());

        mPreviewText.measure(
                makeMeasureSpec(0, UNSPECIFIED),
                makeMeasureSpec(0, UNSPECIFIED)
        );

        int popupWidth = mPreviewText.getMeasuredWidth();
        int popupHeight = mPreviewText.getMeasuredHeight();

//        ViewGroup.LayoutParams lp = mPreviewText.getLayoutParams();
//        if (lp != null) {
//            lp.width = popupWidth;
//            lp.height = popupHeight;
//        }

//        int measureSpec = makeMeasureSpec(0, UNSPECIFIED);
//        mPreviewText.measure(measureSpec, measureSpec);


        int[] xy = new int[2];

        parent.getLocationInWindow(xy);

        int parentX = xy[0];
        int parentY = xy[1];

//        int dx = Math.round(keyInfo.xPos + (keyInfo.width * 1.0f / 2) - (popupWidth * 1.0f / 2));
        int dy = keyInfo.yPos - popupHeight;

//        final int x = Math.round(parentX + dx);
//        final int y = Math.round(parentY + (dy - CoreUtils.toPx(10f)));


        float keyX = (keyInfo.xPos) + (keyInfo.width * 1.0f / 2);
        float halfPopupWidth = popupWidth * 1.0f / 2;

        int parentWidth = parent.getWidth();

        float dx;
        if (keyX + halfPopupWidth > parentWidth) {
            dx = parentWidth - popupWidth - keyMargin;
        } else {
            dx = Math.max(keyX - halfPopupWidth, keyMargin);
        }

        final int x = Math.round(parentX + dx + xy[0]);
        final int y = Math.round(parentY + (dy - mOffset));

        if (isShowing()) {
            update(x, y, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT);
        } else {
            showAtLocation(parent, Gravity.NO_GRAVITY, x, y);
        }

        //mPreviewText.setVisibility(View.VISIBLE);
    }

    public void hide() {
        //mPreviewText.setVisibility(View.INVISIBLE);
    }
}
