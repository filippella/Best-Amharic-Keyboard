package org.dalol.bestamharickeyboard.core.internal;

import android.content.Context;

import org.dalol.bestamharickeyboard.core.models.VirtualKeyboard;

import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 20:43.
 */
public abstract class VirtualKeyboardManager {

    private final Context mContext;

    protected VirtualKeyboardManager(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void populateKeys(VirtualKeyboard keyboard, int width, int paddingLeft, int paddingTop, List<VirtualKeyInfo> keyInfos);
}
