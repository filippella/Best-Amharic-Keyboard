package org.dalol.bestamharickeyboard.core.ui;

import org.dalol.bestamharickeyboard.core.internal.VirtualKeyboardManager;
import org.dalol.bestamharickeyboard.core.models.VirtualKeyboard;
import org.dalol.bestamharickeyboard.core.models.callback.OnVirtualKeyboardActionListener;

import androidx.annotation.NonNull;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 26/05/2019 at 14:09.
 */
public interface IVirtualKeyboardView {

    void setVirtualKeyboard(@NonNull VirtualKeyboard keyboard);

    void setOnVirtualKeyboardActionListener(OnVirtualKeyboardActionListener actionListener);

    void setKeyboardManager(@NonNull VirtualKeyboardManager keyboardManager);

    boolean requiresModifierBar();
}
