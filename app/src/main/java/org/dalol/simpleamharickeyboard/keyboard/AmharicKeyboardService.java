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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import org.dalol.simpleamharickeyboard.keyboard.keyinfo.EnglishInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.GeezInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.InputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.SymbolsOneInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.SymbolsTwoInputKeysInfo;
import org.dalol.simpleamharickeyboard.keyboard.keys.InputKeyboardView;
import org.dalol.simpleamharickeyboard.keyboard.keys.OnInputKeyListener;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicKeyboardService extends InputMethodService implements OnInputKeyListener {

    private InputKeysInfo geezKeyInfo, englishKeyInfo, symbolsOneKeyInfo, symbolsTwoKeyInfo;
    private InputKeyboardView inputKeyboardView;
    private boolean modifierReady;
    private int selectionEnd, selectionStart;

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

    @Override
    public void onClick(String keyLabel) {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.commitText(keyLabel, 1);
        modifierReady = true;

        ExtractedText et = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
        selectionStart = et.selectionStart;
        selectionEnd = et.selectionEnd;
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

    @Override
    public void onModifierClick(String keyLabel) {
        InputConnection inputConnection = getCurrentInputConnection();

        ExtractedText et = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
        int ss = et.selectionStart;
        int se = et.selectionEnd;

        if(modifierReady && se == ss && ss == selectionStart) inputConnection.deleteSurroundingText(1, 0);
        inputConnection.commitText(keyLabel, 1);
        modifierReady = false;
    }
}
