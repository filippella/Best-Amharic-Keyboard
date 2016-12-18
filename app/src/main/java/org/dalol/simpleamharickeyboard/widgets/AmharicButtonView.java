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

package org.dalol.simpleamharickeyboard.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

import org.dalol.simpleamharickeyboard.R;
import org.dalol.simpleamharickeyboard.uitilities.FontType;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicButtonView extends Button {

    private static final String FONTS_NYALA_TTF = "fonts/nyala.ttf";
    private int mTextFontType;

    public AmharicButtonView(Context context) {
        super(context);
        initialize(context, null);
    }

    public AmharicButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public AmharicButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AmharicButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AmharicButtonView, 0, 0);
        try {
            mTextFontType = typedArray.getInteger(R.styleable.AmharicButtonView_amharic_font_type, 0);
        } finally {
            typedArray.recycle();
        }
        applyTypeface(mTextFontType);
    }

    public void setCustomTypeFace(int typeFaceType) {
        applyTypeface(typeFaceType);
    }

    private void applyTypeface(int typefaceType) {
        setTypeface(FontType.getTypeFaceByType(getContext(), typefaceType));
    }
}
