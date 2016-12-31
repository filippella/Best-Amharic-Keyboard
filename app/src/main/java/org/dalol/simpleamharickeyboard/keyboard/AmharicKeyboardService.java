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
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.dalol.simpleamharickeyboard.keyboard.keyinfo.EnglishInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.GeezInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.InputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.SymbolsOneInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.SymbolsTwoInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keys.InputKeyboardView;
import org.dalol.simpleamharickeyboard.keyboard.keys.OnInputKeyListener;
import org.dalol.simpleamharickeyboard.widgets.AmharicButtonView;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicKeyboardService extends InputMethodService implements OnKeyboardActionListener, OnInputKeyListener {

//    private AmharicKeyboardView mKeyboardView;

//    private LinearLayout modifiersContainer;

    private String am[] = {"ሁ", "ሂ", "ሃ", "ሄ", "ህ", "ሆ", "ሇ"};
    private InputKeysInfo geezKeyInfo, englishKeyInfo, symbolsOneKeyInfo, symbolsTwoKeyInfo;
    private InputKeyboardView inputKeyboardView;

    public void onCreate() {
        super.onCreate();

        inputKeyboardView = new InputKeyboardView(getApplicationContext());
        inputKeyboardView.setOnInputKeyListener(this);
    }

    public void onInitializeInterface() {
        geezKeyInfo = new GeezInputKeysInfo();
        englishKeyInfo = new EnglishInputKeysInfo();
        symbolsOneKeyInfo = new SymbolsOneInputKeysInfo();
        symbolsTwoKeyInfo = new SymbolsTwoInputKeysInfo();
        inputKeyboardView.setInputKeyboard(geezKeyInfo);
//        mKeyboard = new AmharicKeyboard(this, R.xml.mainkeyboard);
//        mSecondKeyboard = new AmharicKeyboard(this, R.xml.symbols);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public View onCreateInputView() {


        //Dinamically change themes

//        SharedPreferences pre = getSharedPreferences("test", 1);
//        int theme = pre.getInt("theme", 1);
//
//        if(theme == 1)
//        {
//            this.mInputView = (AmharicKeyboardView) this.getLayoutInflater().inflate(R.layout.input, null);
//        }else
//        {
//            this.mInputView = (AmharicKeyboardView) this.getLayoutInflater().inflate(R.layout.input_2, null);
//
//        }
//        this.mInputView.setOnKeyboardActionListener(this);
//        this.mInputView.setKeyboard(this.mQwertyKeyboard);



//        mKeyboardView = (AmharicKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
//        mKeyboardView.setOnKeyboardActionListener(this);
//        mKeyboardView.setKeyboard(mKeyboard);
//        mKeyboardView.registerKeyboardService(this);
        FrameLayout parent = (FrameLayout) inputKeyboardView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        return inputKeyboardView;
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

//    public View onCreateCandidatesView() {
//        FrameLayout candidateView = new FrameLayout(getApplicationContext());
//        candidateView.removeAllViews();
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//        FrameLayout parent = (FrameLayout) modifiersContainer.getParent();
//        if (parent != null) {
//            parent.removeAllViews();
//        }
//        candidateView.addView(modifiersContainer);
//        setCandidatesViewShown(true);
//        candidateView.setLayoutParams(params);
//        return candidateView;
//    }

    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);

        setInputView(onCreateInputView());
//
//        mKeyboardView.setKeyboard(mKeyboard);
//        mKeyboardView.closing();
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
//        mKeyboardView.setKeyboard(mKeyboard);
//        mKeyboardView.registerKeyboardService(this);
    }

    private void switchToSecondTamilKeyboard() {
//        mKeyboardView.setKeyboard(mSecondKeyboard);
//        mKeyboardView.registerKeyboardService(this);
    }


    private void handleAmharicKeyPress() {
//        if (key1Char >= '\u0b95' && key1Char <= '\u0bba') {
//            switch (key0Char) {
//                case '\u0b83':
//                    key0Char = '\u0bcd';
//                    break;
//                case '\u0b86':
//                    key0Char = '\u0bbe';
//                    break;
//                case '\u0b87':
//                    key0Char = '\u0bbf';
//                    break;
//                case '\u0b88':
//                    key0Char = '\u0bc0';
//                    break;
//                case '\u0b89':
//                    key0Char = '\u0bc1';
//                    break;
//                case '\u0b8a':
//                    key0Char = '\u0bc2';
//                    break;
//                case '\u0b8e':
//                    key0Char = '\u0bc6';
//                    break;
//                case '\u0b8f':
//                    key0Char = '\u0bc7';
//                    break;
//                case '\u0b90':
//                    key0Char = '\u0bc8';
//                    break;
//                case '\u0b92':
//                    key0Char = '\u0bca';
//                    break;
//                case '\u0b93':
//                    key0Char = '\u0bcb';
//                    break;
//                case '\u0b94':
//                    key0Char = '\u0bcc';
//                    break;
//            }
//        }
//        getCurrentInputConnection().commitText(String.valueOf(key0Char), 1);
    }

    public void onKey(int keyCode, int[] otherKeyCodes) {
        InputConnection currentInputConnection = getCurrentInputConnection();

//        InputConnection ic = getCurrentInputConnection();
//        ic.deleteSurroundingText(4, 0);
//        ic.commitText("Hello", 1);
//        ic.commitText("!", 1);


//        List<Key> keyList = mKeyboardView.getKeyboard().getKeys();
//        for (int i = 0; i < 8; i++) {
//            Key key = keyList.get(i);
//            key.label = Integer.toString(new Random().nextInt(10));
//            mKeyboardView.invalidateKey(i);
//        }

        CharSequence textBeforeCursor = currentInputConnection.getTextBeforeCursor(9999, 0);
        CharSequence textAfterCursor = currentInputConnection.getTextAfterCursor(9999, 0);

        int tempLength = textBeforeCursor.toString().length();
        if (textAfterCursor != null) {
            tempLength += textAfterCursor.toString().length();
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
                    tempLength--;

                    return;
                case Keyboard.KEYCODE_CANCEL:
                    InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    if (imeManager == null) {
                        return;
                    }
                    imeManager.showInputMethodPicker();
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
                case 10:
                    keyDownUp(66);
                    break;
                default:
                    String text = String.valueOf((char) keyCode);
                    tempLength++;
                    currentInputConnection.commitText(text, 1);
                    break;
            }
            //modifiersContainer.removeAllViews();

            if (tempLength > 0) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {


                        //Random random = new Random();
                        //int count = random.nextInt(9) + 1;
                        for (int i = 0; i < 10; i++) {
                            AmharicButtonView child = new AmharicButtonView(getApplicationContext());
                            //child.setCustomTypeFace(FontType.NYALA.getFontType());
                            child.setTextSize(21f);
                            child.setText(am[0]);
                            child.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Button button = (Button) v;
                                    InputConnection inputConnection = getCurrentInputConnection();
                                    CharSequence beforeCursor = inputConnection.getTextBeforeCursor(9999, 0);
                                    //beforeCursor = new String("Filippo");

                                    inputConnection.commitText(button.getText().toString(), 1);
                                    Toast.makeText(AmharicKeyboardService.this, "Hey " + button.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            //child.setPadding(10, 10, 10, 10);
                            //modifiersContainer.addView(child, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                        }
                    }

                });
            }


            return;
        }
//        key0Char = (char) keyCode;
        if (keyCode == 3002) {
            currentInputConnection.commitText("\u0b95\u0bcd\u0bb7", 1);
        } else if (keyCode == 3071) {
            currentInputConnection.commitText("\u0bb8\u0bcd\u0bb0\u0bc0", 1);
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

    @Override
    public void onClick(String keyLabel) {
        getCurrentInputConnection().commitText(keyLabel, 1);
       // handler.post(new PopulateModifiers(keyModifiers));
    }

    @Override
    public void onBackSpace() {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 67));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, 67));
    }

    @Override
    public void onSpace() {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 62));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, 62));
    }

    @Override
    public void onEnter() {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 66));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(1, 66));
    }

    @Override
    public void onSettingClicked() {
        InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager == null) {
            return;
        }
        imeManager.showInputMethodPicker();
    }

    @Override
    public void onSetAmharicKeyboard() {
        inputKeyboardView.setInputKeyboard(geezKeyInfo);
    }

    @Override
    public void onSetSymbolsOneKeyboard() {
        inputKeyboardView.setInputKeyboard(symbolsOneKeyInfo);
    }

    @Override
    public void onSetSymbolsTwoKeyboard() {
        inputKeyboardView.setInputKeyboard(symbolsTwoKeyInfo);
    }

    @Override
    public void onSetEnglishKeyboard() {
        inputKeyboardView.setInputKeyboard(englishKeyInfo);
    }

    //Handler handler = new Handler(Looper.getMainLooper());


}
