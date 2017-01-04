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

package org.dalol.bestamharickeyboard.uitilities;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/4/2016
 */
public enum FontType {

    NYALA(0, "fonts/nyala.ttf"),
    JIRET(1, "fonts/jiret.ttf"),
    FANTUWUA(2, "fonts/fantuwua.ttf"),
    GOFFER(3, "fonts/goffer.ttf"),
    HIWUA(4, "fonts/hiwua.ttf"),
    SABAEAN(5, "fonts/sabaean.ttf"),
    TINT(6, "fonts/tint.ttf"),
    WASHRAB(7, "fonts/washrab.ttf"),
    WASHRASB(8, "fonts/washrasb.ttf"),
    WOOKIANOS(9, "fonts/wookianos.ttf"),
    YEBSE(10, "fonts/yebse.ttf"),
    YIGEZUBISRAT(11, "fonts/yigezubisratgothic.ttf"),
    ZELAN(12, "fonts/zelan.ttf"),;

    private int fontType;
    private String fontPath;

    FontType(int fontType, String fontPath) {
        this.fontType = fontType;
        this.fontPath = fontPath;
    }

    public String getFontPath() {
        return fontPath;
    }

    public int getFontType() {
        return fontType;
    }

    public Typeface getTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), getFontPath());
    }

    public static Typeface getTypeFaceByType(Context context, int type) {
        FontType[] values = values();
        FontType selectedFontType = NYALA;
        for (int i = 0; i < values.length; i++) {
            FontType value = values[i];
            if (value.getFontType() == type) {
                selectedFontType = value;
                break;
            }
        }
        return Typeface.createFromAsset(context.getAssets(), selectedFontType.getFontPath());
    }
}
