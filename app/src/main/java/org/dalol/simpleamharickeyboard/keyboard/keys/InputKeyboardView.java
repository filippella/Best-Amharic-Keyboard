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

package org.dalol.simpleamharickeyboard.keyboard.keys;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.dalol.simpleamharickeyboard.R;
import org.dalol.simpleamharickeyboard.uitilities.FontType;

import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/18/2016
 */
public class InputKeyboardView extends LinearLayout {

    private OnInputKeyListener onInputKeyListener;
    private InputKeysInfo mInputKeysInfo;
    private int mKeyHeight;

    public InputKeyboardView(Context context) {
        this(context, null, 0);
    }

    public InputKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeKeyboardView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public InputKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeKeyboardView(context, attrs);
    }

    private void initializeKeyboardView(Context context, AttributeSet attrs) {
        verifyEditMode();
        setWillNotDraw(true);
        setOrientation(VERTICAL);
        mKeyHeight = getResources().getDimensionPixelOffset(R.dimen.key_height);
    }

    private void verifyEditMode() {
        if(isInEditMode()) {
            return;
        }
    }

    public void setOnInputKeyListener(OnInputKeyListener onInputKeyListener) {
        this.onInputKeyListener = onInputKeyListener;
    }

    public void setInputKeyboard(InputKeysInfo inputKeysInfo) {
        mInputKeysInfo = inputKeysInfo;
        //setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, mKeyHeight * inputKeysInfo.getKeysRowList().size()));
        handler.post(new CreateKeyboard());
    }

    Handler handler = new Handler(Looper.getMainLooper());

    public class CreateKeyboard implements Runnable {

        @Override
        public void run() {
            removeAllViews();
            Context context = getContext();
            //setBackgroundColor(Color.parseColor("#45ffcc"));
            setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));


//        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);

            // Random random = new Random();

            List<InputKeysRow> keysRowList = mInputKeysInfo.getKeysRowList();

            int size = keysRowList.size();
            for(int i = 0; i < size; i++) {

                LinearLayout keyRow = new LinearLayout(context);
                keyRow.setOrientation(LinearLayout.HORIZONTAL);

                List<KeyInfo> keyInfoList = keysRowList.get(i).getKeyInfoList();
                for (int j = 0; j < keyInfoList.size(); j++) {

                    KeyInfo keyInfo = keyInfoList.get(j);

                    TextView key = new TextView(context);
                    key.setGravity(Gravity.CENTER);
                    key.setTypeface(FontType.NYALA.getTypeface(context), Typeface.BOLD);
                    key.setText(keyInfo.getKeyLabel());
                    key.setTextSize(16f);
                    key.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TextView labelView = (TextView) v;
                            //KeyInfo info = (KeyInfo) v.getTag();

                            if(onInputKeyListener != null) {
                                String label = labelView.getText().toString();
                                onInputKeyListener.onClick(label, mInputKeysInfo.getModifiers(label));
                            }

//                        InputConnection inputConnection = getCurrentInputConnection();
//
//                        ExtractedText text = inputConnection.getExtractedText(new ExtractedTextRequest(), 0);
//                        CharSequence sequence = text.text;
//
//
//                        inputConnection.commitText(tt.getText().toString(), 1);
//                        EditorInfo editorInfo = getCurrentInputEditorInfo();
//                        int inputType = editorInfo.inputType;
//
//
//
//                        Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //key.setTextColor(ContextCompat.getColorStateList(context, R.color.amharic_key_text_color_selector));
                    //key.setTag(keyInfo);
                    key.setIncludeFontPadding(false);
                    key.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.theme_violet_bg));
//                    key.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
                    key.setTextColor(Color.WHITE);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, mKeyHeight, keyInfo.getKeyWeight());
                    //params.setMargins(margin, margin, margin, margin);
                    keyRow.setBaselineAligned(false);
                    keyRow.addView(key, params);
                }
                addView(keyRow);
            }

        }
    }
}
