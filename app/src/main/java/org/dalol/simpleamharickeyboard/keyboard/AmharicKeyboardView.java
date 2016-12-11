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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import org.dalol.simpleamharickeyboard.theme.KeyThemeInfo;
import org.dalol.simpleamharickeyboard.theme.ThemesInfo;

import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public class AmharicKeyboardView extends KeyboardView {

    private AmharicKeyboardService keyboardService;

    public AmharicKeyboardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AmharicKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/goffer.ttf");
//        TextView view = (TextView) getKeyboard().getKeys().get(0).label;
//        view.setTypeface(font);
    }
    public void registerKeyboardService(AmharicKeyboardService kbService) {
        keyboardService = kbService;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        ThemesInfo themesInfo = new ThemesInfo();
//        List<KeyThemeInfo> themes = themesInfo.getThemes();
//        GradientDrawable drawable = themesInfo.getGradient(themes.get(11));
//
//
//        Paint mPaint = new Paint();
//        mPaint.setTextAlign(Paint.Align.CENTER);
//        mPaint.setTextSize(80);
//        mPaint.setColor(Color.WHITE);
//
//        Typeface font = Typeface.createFromAsset(getContext().getAssets(),"fonts/goffer.ttf");
//        mPaint.setTypeface(font);
//
//        List<Key> keys = getKeyboard().getKeys();
//        for (Key key : keys) {
//            drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//            drawable.draw(canvas);
//
//
//           if (key.label != null) {
//
//                String keyLabel = key.label.toString();
//                //if (caps) {
//                    //keyLabel = keyLabel.toUpperCase();
//                //}
//                canvas.drawText(keyLabel, key.x + (key.width / 2),
//                        key.y + (key.height / 2), mPaint);
//            } else if (key.icon != null) {
//                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//                key.icon.draw(canvas);
//            }
//
//            key.label = "";
//        }


//

//        for (Key key : getKeyboard().getKeys()) {
//            if (key.label != null) {
//
//                String keyLabel = key.label.toString();
//                //if (caps) {
//                    //keyLabel = keyLabel.toUpperCase();
//                //}
//                canvas.drawText(keyLabel, key.x + (key.width / 2),
//                        key.y + (key.height / 2), mPaint);
//            } else if (key.icon != null) {
//                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
//                key.icon.draw(canvas);
//            }
//
//            key.label = "";
//        }
    }

    protected boolean onLongPress(Key key) {
        if (keyboardService.ProcessLongPress(key)) {
            return true;
        }
        return super.onLongPress(key);
    }
}
