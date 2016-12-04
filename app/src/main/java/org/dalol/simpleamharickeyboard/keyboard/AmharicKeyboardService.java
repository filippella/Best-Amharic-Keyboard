/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dalol.simpleamharickeyboard.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.dalol.simpleamharickeyboard.R;

import java.util.List;
import java.util.Random;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicKeyboardService extends InputMethodService implements OnKeyboardActionListener {

    private char key0Char;
    private char key1Char;
    private char key2Char;
    private AmharicKeyboard mKeyboard;
    private AmharicKeyboardView mKeyboardView;
    private AmharicKeyboard mSecondKeyboard;

    public void onCreate() {
        super.onCreate();
    }

    public void onInitializeInterface() {
        mKeyboard = new AmharicKeyboard(this, R.xml.mainkeyboard);
        mSecondKeyboard = new AmharicKeyboard(this, R.xml.symbols);
    }

    public View onCreateInputView() {
        mKeyboardView = (AmharicKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
        mKeyboardView.setOnKeyboardActionListener(this);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.registerKeyboardService(this);
        return mKeyboardView;
    }

    public View onCreateCandidatesView() {
        return null;
    }

    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.closing();
    }

    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
    }

    public void onFinishInput() {
        super.onFinishInput();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    public boolean ProcessLongPress(Key key) {
        if (key.codes[0] == 32) {
            InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imeManager == null) {
                return true;
            }
            imeManager.showInputMethodPicker();
            return true;
        } else if (key.codes[0] != -3) {
            return false;
        } else {
            Toast.makeText(getApplicationContext(), "\u0bb5\u0bbf\u0b9a\u0bc8 \u0b85\u0ba4\u0bbf\u0bb0\u0bcd\u0bb5\u0bc1: \u0b85\u0ba9\u0bc1\u0bae\u0ba4\u0bbf", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, keyEventCode));
    }

    private void switchToTamilKeyboard() {
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.registerKeyboardService(this);
    }

    private void switchToSecondTamilKeyboard() {
        mKeyboardView.setKeyboard(mSecondKeyboard);
        mKeyboardView.registerKeyboardService(this);
    }

    private void sendBackspaceKey() {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 67));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, 67));
    }

    private void handleAmharicKeyPress() {
        if (key1Char >= '\u0b95' && key1Char <= '\u0bba') {
            switch (key0Char) {
                case '\u0b83':
                    key0Char = '\u0bcd';
                    break;
                case '\u0b86':
                    key0Char = '\u0bbe';
                    break;
                case '\u0b87':
                    key0Char = '\u0bbf';
                    break;
                case '\u0b88':
                    key0Char = '\u0bc0';
                    break;
                case '\u0b89':
                    key0Char = '\u0bc1';
                    break;
                case '\u0b8a':
                    key0Char = '\u0bc2';
                    break;
                case '\u0b8e':
                    key0Char = '\u0bc6';
                    break;
                case '\u0b8f':
                    key0Char = '\u0bc7';
                    break;
                case '\u0b90':
                    key0Char = '\u0bc8';
                    break;
                case '\u0b92':
                    key0Char = '\u0bca';
                    break;
                case '\u0b93':
                    key0Char = '\u0bcb';
                    break;
                case '\u0b94':
                    key0Char = '\u0bcc';
                    break;
            }
        }
        getCurrentInputConnection().commitText(String.valueOf(key0Char), 1);
    }

    public void onKey(int keyCode, int[] otherKeyCodes) {

        List<Key> keyList = mKeyboardView.getKeyboard().getKeys();
        for(int i = 0; i < 8; i++) {
            Key key = keyList.get(i);
            key.label = Integer.toString(new Random().nextInt(10));
            mKeyboardView.invalidateKey(i);
        }

        if (keyCode < 2944 || keyCode > 3071) {
            switch (keyCode) {
                case -33:
                    switchToTamilKeyboard();
                    break;
                case -11:
                    switchToSecondTamilKeyboard();
                    break;
                case Keyboard.KEYCODE_DELETE:
                    sendBackspaceKey();
                    break;
                case Keyboard.KEYCODE_CANCEL:
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
                case 10:
                    keyDownUp(66);
                    break;
                default:
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                    break;
            }
            return;
        }
        key0Char = (char) keyCode;
        if (keyCode == 3002) {
            getCurrentInputConnection().commitText("\u0b95\u0bcd\u0bb7", 1);
        } else if (keyCode == 3071) {
            getCurrentInputConnection().commitText("\u0bb8\u0bcd\u0bb0\u0bc0", 1);
        } else {
            handleAmharicKeyPress();
        }
    }

    public void onText(CharSequence text) {
    }

    public void swipeRight() {
    }

    public void swipeLeft() {
    }

    public void swipeDown() {
    }

    public void swipeUp() {
    }

    public void onPress(int primaryCode) {
    }

    public void onRelease(int primaryCode) {
    }
}
