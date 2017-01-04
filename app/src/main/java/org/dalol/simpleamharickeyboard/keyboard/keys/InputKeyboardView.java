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
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.dalol.simpleamharickeyboard.R;
import org.dalol.simpleamharickeyboard.keyboard.keyinfo.InputKeysInfo;

import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/18/2016
 */
public class InputKeyboardView extends LinearLayout {

    public static final int DEFAULT_NORMAL_CALLBACK_INTERVAL_DELAY = 50;
    private OnInputKeyListener onInputKeyListener;
    private InputKeysInfo mInputKeysInfo;
    private int mKeyHeight;

    private final static int INITIAL_INTERVAL = 400;
    private View mPressedKeyView;
    private int mNormalInterval = DEFAULT_NORMAL_CALLBACK_INTERVAL_DELAY;
    private Handler mHandler = new Handler();
    private LinearLayout modifiersContainer;

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
        modifiersContainer = new LinearLayout(context);
        modifiersContainer.setOrientation(LinearLayout.HORIZONTAL);
        modifiersContainer.setWillNotDraw(true);
        modifiersContainer.setBackgroundColor(0xFF364751);
        modifiersContainer.setGravity(Gravity.CENTER);
        modifiersContainer.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.modifier_key_height)));
        modifiersContainer.setPadding(5, 5, 5, 5);
    }

    private void verifyEditMode() {
        if (isInEditMode()) {
            return;
        }
    }

    public void setOnInputKeyListener(OnInputKeyListener onInputKeyListener) {
        this.onInputKeyListener = onInputKeyListener;
    }

    public void setInputKeyboard(InputKeysInfo inputKeysInfo) {
        modifiersContainer.setVisibility(inputKeysInfo.isModifiersEnabled() ? VISIBLE : GONE);
        mInputKeysInfo = inputKeysInfo;
        removeAllViews();
        setMeasuredDimension(ViewGroup.LayoutParams.MATCH_PARENT, inputKeysInfo.getKeysRowList().size() * mKeyHeight);
        addView(modifiersContainer);
        Context context = getContext();
        setBackgroundColor(Color.parseColor("#45ffcc"));

        List<InputKeysRow> keysRowList = mInputKeysInfo.getKeysRowList();

        int size = keysRowList.size();
        for (int i = 0; i < size; i++) {

            LinearLayout keyRow = new LinearLayout(context);
            keyRow.setOrientation(LinearLayout.HORIZONTAL);
            keyRow.setGravity(Gravity.CENTER);

            List<KeyInfo> keyInfoList = keysRowList.get(i).getKeyInfoList();

            for (int j = 0; j < keyInfoList.size(); j++) {

                KeyInfo keyInfo = keyInfoList.get(j);
                String keyLabel = keyInfo.getKeyLabel();

                float keyWeight = keyInfo.getKeyWeight();
                if (keyLabel != null) {
                    TextView keyText = new TextView(context);
//                    key.setTypeface(FontType.NYALA.getTypeface(context), Typeface.BOLD);
//                        AmharicTextView key = new AmharicTextView(context);
                    keyText.setGravity(Gravity.CENTER);
                    keyText.setText(keyLabel);
                    keyText.setTextSize(18f);
                    keyText.setIncludeFontPadding(false);
                    keyText.setTextColor(Color.WHITE);
                    keyText.setTag(keyInfo);
                    keyText.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.theme_blue_marble_bg));
                    configureKey(keyText, keyWeight, keyRow);
                } else {
                    ImageView keyImage = new ImageView(context);
                    keyImage.setImageResource(keyInfo.getKeyIcon());
                    int padding = getCustomSize(keyWeight * 5);
                    keyImage.setPadding(padding, padding, padding, padding);
                    keyImage.setTag(keyInfo);
                    if(keyInfo.isSelected()) {
                        keyImage.setBackgroundColor(0xFFff0000);
                    } else {
                        keyImage.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.theme_blue_marble_bg));
                    }
                    configureKey(keyImage, keyWeight, keyRow);
                }
            }
            addView(keyRow);
        }
    }

    private void configureKey(View child, float columnCount, LinearLayout keyContainer) {
        child.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mPressedKeyView = v;
                        mHandler.removeCallbacks(mKeyPressRunnable);
                        mHandler.postAtTime(mKeyPressRunnable, mPressedKeyView, SystemClock.uptimeMillis() + INITIAL_INTERVAL);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_OUTSIDE:
                        mHandler.removeCallbacksAndMessages(mPressedKeyView);
                        mPressedKeyView = null;
                        mNormalInterval = DEFAULT_NORMAL_CALLBACK_INTERVAL_DELAY;
                        break;
                }
                return false;
            }
        });
        child.setOnClickListener(mKeyClickListener);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, mKeyHeight, columnCount);
        int margin = getCustomSize(3f);
        //params.setMargins(margin, margin, margin, margin);
        keyContainer.setBaselineAligned(false);
        keyContainer.addView(child, params);
    }

    private int getCustomSize(float size) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, getResources().getDisplayMetrics()));
    }

    public void resetModifiersRow() {
        if (modifiersContainer != null) {
            modifiersContainer.removeAllViews();
        }
    }

    private Runnable mKeyPressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPressedKeyView == null) {
                return;
            }
            mHandler.removeCallbacksAndMessages(mPressedKeyView);
            mHandler.postAtTime(this, mPressedKeyView, SystemClock.uptimeMillis() + mNormalInterval);
            mKeyClickListener.onClick(mPressedKeyView);
        }
    };

    private final View.OnClickListener mKeyClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onInputKeyListener != null) {
                KeyInfo info = (KeyInfo) v.getTag();
                switch (info.getKeyEventType()) {
                    case KeyInfo.KEY_EVENT_NORMAL:
                        String label = info.getKeyLabel();
                        String[] keyModifiers = mInputKeysInfo.getModifiers(label);
                        onInputKeyListener.onClick(label);
                        if (keyModifiers != null) {
                            resetModifiersRow();
                            for (int i = 0; i < keyModifiers.length; i++) {
                                String keyModifier = keyModifiers[i];
                                TextView modifierKey = new TextView(getContext());
                                // AmharicButtonView modifierKey = new AmharicButtonView(getContext());
                                //modifierKey.setTypeface(FontType.NYALA.getTypeface(getContext()), Typeface.BOLD);
                                modifierKey.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.theme_modifiers_bg));
                                modifierKey.setTextSize(21f);
                                modifierKey.setText(keyModifier);
                                modifierKey.setTextColor(Color.WHITE);
                                modifierKey.setGravity(Gravity.CENTER);
                                modifierKey.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView modifierKeyButton = (TextView) v;
                                        String label = modifierKeyButton.getText().toString();
                                        if (onInputKeyListener != null) {
                                            onInputKeyListener.onModifierClick(label);
                                        }
                                    }
                                });
                                modifiersContainer.addView(modifierKey, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
                            }
                        }
                        break;
                    case KeyInfo.KEY_EVENT_SPACE:
                        onInputKeyListener.onSpace();
                        break;
                    case KeyInfo.KEY_EVENT_BACKSPACE:
                        onInputKeyListener.onBackSpace();
                        break;
                    case KeyInfo.KEY_EVENT_ENTER:
                        onInputKeyListener.onEnter();
                        break;
                    case KeyInfo.KEY_EVENT_SETTINGS:
                        onInputKeyListener.onSettingClicked();
                        break;
                    case KeyInfo.KEY_EVENT_HAHU:
                        onInputKeyListener.onSetAmharicKeyboard();
                        break;
                    case KeyInfo.KEY_EVENT_SYMBOLS_ONE:
                        onInputKeyListener.onSetSymbolsOneKeyboard();
                        break;
                    case KeyInfo.KEY_EVENT_SYMBOLS_TWO:
                        onInputKeyListener.onSetSymbolsTwoKeyboard();
                        break;
                    case KeyInfo.KEY_EVENT_ENGLISH:
                        onInputKeyListener.onSetEnglishKeyboard();
                        break;
                    case KeyInfo.KEY_EVENT_SHIFT:
                        onInputKeyListener.onChangeEnglishCharactersCase();
                        break;
                }
            }
        }
    };
}
