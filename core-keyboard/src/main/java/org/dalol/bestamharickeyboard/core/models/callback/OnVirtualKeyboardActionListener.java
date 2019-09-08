package org.dalol.bestamharickeyboard.core.models.callback;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 18:17.
 */
public interface OnVirtualKeyboardActionListener {

    void onPress(String key);

    void onKey(int primaryCode, String key);

    void onRelease(String key);
}
