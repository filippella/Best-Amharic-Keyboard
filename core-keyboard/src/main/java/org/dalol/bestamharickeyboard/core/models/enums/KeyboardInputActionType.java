package org.dalol.bestamharickeyboard.keyboard.model;

import android.view.inputmethod.EditorInfo;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sat, 16/02/2019 at 17:23.
 */
public enum KeyboardInputActionType {

    IME_ACTION_NONE,
    IME_ACTION_GO,
    IME_ACTION_SEARCH,
    IME_ACTION_SEND,
    IME_ACTION_NEXT,
    IME_ACTION_DONE,
    IME_ACTION_PREVIOUS;

    public static KeyboardInputActionType fromeImeOptions(int imeOptions) {

        KeyboardInputActionType actionType;

        switch (imeOptions & EditorInfo.IME_MASK_ACTION) {
            case EditorInfo.IME_ACTION_GO:
                actionType = KeyboardInputActionType.IME_ACTION_GO;
                break;
            case EditorInfo.IME_ACTION_SEARCH:
                actionType = KeyboardInputActionType.IME_ACTION_SEARCH;
                break;
            case EditorInfo.IME_ACTION_SEND:
                actionType = KeyboardInputActionType.IME_ACTION_SEND;
                break;
            case EditorInfo.IME_ACTION_NEXT:
                actionType = KeyboardInputActionType.IME_ACTION_NEXT;
                break;
            case EditorInfo.IME_ACTION_DONE:
                actionType = KeyboardInputActionType.IME_ACTION_DONE;
                break;
            case EditorInfo.IME_ACTION_PREVIOUS:
                actionType = KeyboardInputActionType.IME_ACTION_PREVIOUS;
                break;
            default:
                actionType = KeyboardInputActionType.IME_ACTION_NONE;
        }

        return actionType;
    }
}
