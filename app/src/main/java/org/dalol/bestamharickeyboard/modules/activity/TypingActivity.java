/*
 * Copyright (c) 2017 Filippo Engidashet
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

package org.dalol.bestamharickeyboard.modules.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.gms.ads.AdView;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseActivity;
import org.dalol.bestamharickeyboard.delegate.AdsDelegate;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/6/2017
 */
public class TypingActivity extends BaseActivity {

    private EditText mEditorView;
    private AdView mAdView;
    private AdsDelegate adsDelegate;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        showHome();
        changeStatusBarColor(R.color.colorPrimaryDark);

        mEditorView = findViewById(R.id.editorEditText);
        mAdView = findViewById(R.id.adView);

        adsDelegate = new AdsDelegate(mAdView);
        adsDelegate.handleAdBanner();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_typing;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_typing_test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionClear:
                mEditorView.setText("");
                showToast("Text Cleared!");
                return true;
            case R.id.actionCopy:
                String writtenValue = mEditorView.getText().toString();
                if(!writtenValue.isEmpty()) {
                    copyText(writtenValue);
                    showToast("Text Copied on the Clipboard!");
                } else {
                    showToast("Text cannot be copied! TextField is empty.");
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void copyText(String text) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Written Text Copied!", text);
            clipboard.setPrimaryClip(clip);
        }
    }
}
