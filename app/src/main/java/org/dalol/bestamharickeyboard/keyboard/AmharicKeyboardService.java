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

package org.dalol.bestamharickeyboard.keyboard;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import org.dalol.bestamharickeyboard.keyboard.keyinfo.EnglishInputKeysInfo;
import org.dalol.bestamharickeyboard.keyboard.keyinfo.GeezInputKeysInfo;
import org.dalol.bestamharickeyboard.keyboard.keyinfo.InputKeysInfo;
import org.dalol.bestamharickeyboard.keyboard.keyinfo.SymbolsOneInputKeysInfo;
import org.dalol.bestamharickeyboard.keyboard.keyinfo.SymbolsTwoInputKeysInfo;
import org.dalol.bestamharickeyboard.keyboard.keys.InputKeyboardView;
import org.dalol.bestamharickeyboard.keyboard.keys.OnInputKeyListener;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicKeyboardService extends InputMethodService implements OnInputKeyListener {

    private InputKeysInfo geezKeyInfo, englishLowerCaseKeyInfo, englishUpperCaseKeyInfo, symbolsOneKeyInfo, symbolsTwoKeyInfo;
    private InputKeyboardView inputKeyboardView;
    private boolean modifierReady;
    private int selectionStart;
    private boolean uppercase;

    @Override
    public void onCreate() {
        super.onCreate();
        inputKeyboardView = new InputKeyboardView(getApplicationContext());
        inputKeyboardView.setOnInputKeyListener(this);
    }

    @Override
    public void onInitializeInterface() {
        geezKeyInfo = new GeezInputKeysInfo();
        englishLowerCaseKeyInfo = new EnglishInputKeysInfo(true);
        englishUpperCaseKeyInfo = new EnglishInputKeysInfo(false);
        symbolsOneKeyInfo = new SymbolsOneInputKeysInfo();
        symbolsTwoKeyInfo = new SymbolsTwoInputKeysInfo();
        inputKeyboardView.setInputKeyboard(geezKeyInfo);
    }

    @Override
    public void onDestroy() {
        if (geezKeyInfo != null) {
            geezKeyInfo.clean();
        }
        if (englishLowerCaseKeyInfo != null) {
            englishLowerCaseKeyInfo.clean();
        }
        if (englishUpperCaseKeyInfo != null) {
            englishUpperCaseKeyInfo.clean();
        }
        if (symbolsOneKeyInfo != null) {
            symbolsOneKeyInfo.clean();
        }
        if (symbolsTwoKeyInfo != null) {
            symbolsTwoKeyInfo.clean();
        }
        super.onDestroy();
    }

    public View onCreateInputView() {
        FrameLayout parent = (FrameLayout) inputKeyboardView.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }
        inputKeyboardView.applyKeyboardChanges();
        return inputKeyboardView;
    }

    @Override
    public void onComputeInsets(InputMethodService.Insets outInsets) {
        super.onComputeInsets(outInsets);
        if (!isFullscreenMode()) {
            outInsets.contentTopInsets = outInsets.visibleTopInsets;
        }
    }

    @Override
    public void onStartInputView(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        setInputView(onCreateInputView());
    }

    @Override
    public void onClick(String keyLabel) {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.commitText(keyLabel, 1);
        modifierReady = true;

        ExtractedText et = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
        selectionStart = et.selectionStart;
    }

    @Override
    public void onBackSpace() {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.sendKeyEvent(new KeyEvent(0, 67));
        inputConnection.sendKeyEvent(new KeyEvent(1, 67));
    }

    @Override
    public void onSpace() {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.sendKeyEvent(new KeyEvent(0, 62));
        inputConnection.sendKeyEvent(new KeyEvent(1, 62));
    }

    @Override
    public void onEnter() {
        InputConnection inputConnection = getCurrentInputConnection();
        inputConnection.sendKeyEvent(new KeyEvent(0, 66));
        inputConnection.sendKeyEvent(new KeyEvent(1, 66));
    }

    @Override
    public void onSettingClicked() {
        InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imeManager == null) {
            return;
        }
        imeManager.showInputMethodPicker();
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
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
        inputKeyboardView.setInputKeyboard(englishLowerCaseKeyInfo);
        uppercase = false;
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

    @Override
    public void onChangeEnglishCharactersCase() {
        if(!uppercase) {uppercase = true;inputKeyboardView.setInputKeyboard(englishUpperCaseKeyInfo);}
        else {uppercase = false;inputKeyboardView.setInputKeyboard(englishLowerCaseKeyInfo);}
    }
}
