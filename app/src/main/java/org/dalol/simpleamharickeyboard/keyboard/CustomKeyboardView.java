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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/17/2016
 */
public class CustomKeyboardView extends LinearLayout {

    public CustomKeyboardView(Context context) {
        this(context, null, 0);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeKeyboardView(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeKeyboardView(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Round up a little
//        if (mKeyboard == null) {
//            setMeasuredDimension(getPaddingLeft() + mPaddingRight, mPaddingTop + mPaddingBottom);
//        } else {
//            int width = mKeyboard.getMinWidth() + mPaddingLeft + mPaddingRight;
//            if (MeasureSpec.getSize(widthMeasureSpec) < width + 10) {
//                width = MeasureSpec.getSize(widthMeasureSpec);
//            }
//            setMeasuredDimension(width, mKeyboard.getHeight() + mPaddingTop + mPaddingBottom);
//        }
    }

    private void initializeKeyboardView(Context context, AttributeSet attrs) {
        verifyInEditMode();
        setOrientation(VERTICAL);
        setWillNotDraw(true);
        setClipToPadding(false);
    }

    private void verifyInEditMode() {
        if(isInEditMode()) {
            return;
        }
    }
}
