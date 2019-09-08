package org.dalol.bestamharickeyboard.core.utilities;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 18:07.
 */
public final class CoreUtils {

    public static int toPx(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value,
                Resources.getSystem().getDisplayMetrics())
        );
    }

    public static int toSp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, value,
                Resources.getSystem().getDisplayMetrics())
        );
    }

    public static boolean isInBound(View view, float x, float y) {

        int xy[] = new int[2];
        view.getLocationOnScreen(xy);

        int xPos = xy[0];
        int yPos = xy[1];

        return (x > xPos && x < (xPos + view.getWidth())) && (y > yPos && y < (yPos + view.getHeight()));
    }

    public static void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
}
