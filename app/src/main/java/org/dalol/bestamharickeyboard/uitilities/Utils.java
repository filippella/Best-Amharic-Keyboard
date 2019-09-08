package org.dalol.bestamharickeyboard.uitilities;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 16:54.
 */
public final class Utils {

    public static int toPx(float value) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics()));
    }
}
