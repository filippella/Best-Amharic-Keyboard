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

package org.dalol.simpleamharickeyboard.theme;

import android.graphics.Color;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/13/2016
 */
public class ThemesInfoTest {


    @Before
    public void setUp() throws Exception {
        ThemesInfo info = new ThemesInfo();
        List<KeyThemeInfo> themes = info.getThemes();
        for (int i = 0; i < themes.size(); i++) {
            KeyThemeInfo themeInfo = themes.get(i);
            System.out.println("theme_"+themeInfo.getName()+"_bg");

//            String hexValue = Integer.toHexString(themeInfo.getColorE());
//            System.out.println(hexValue);

            System.out.println(String.format(bgInfo, Integer.toHexString(themeInfo.getColorE()),
                    Integer.toHexString(themeInfo.getColorD()), Integer.toHexString(themeInfo.getColorC())
                    , Integer.toHexString(themeInfo.getColorA()), Integer.toHexString(themeInfo.getColorB()),
                    Integer.toHexString(themeInfo.getColorC())));
        }
    }

    @Test
    public void getThemes() throws Exception {

    }

    @Test
    public void getGradient() throws Exception {

    }

    private String bgInfo = "<selector xmlns:android=\"http://schemas.android.com/apk/res/android\">\n" +
            "    <item android:state_focused=\"false\" android:state_pressed=\"false\" android:state_selected=\"false\">\n" +
            "        <shape android:shape=\"rectangle\">\n" +
            "            <gradient\n" +
            "                android:angle=\"90\"\n" +
            "                android:startColor=\"#%s\"\n" +
            "                android:centerColor=\"#%s\"\n" +
            "                android:endColor=\"#%s\"\n" +
            "                android:type=\"linear\" />\n" +
            "            <corners android:radius=\"0dp\" />\n" +
            "            <stroke android:color=\"@color/black\" android:width=\"0.5dp\" />\n" +
            "        </shape>\n" +
            "    </item>\n" +
            "\n" +
            "    <item android:state_pressed=\"true\">\n" +
            "        <shape android:shape=\"rectangle\">\n" +
            "            <gradient\n" +
            "                android:angle=\"90\"\n" +
            "                android:startColor=\"#%s\"\n" +
            "                android:centerColor=\"#%s\"\n" +
            "                android:endColor=\"#%s\"\n" +
            "                android:type=\"linear\" />\n" +
            "            <corners android:radius=\"0dp\" />\n" +
            "            <stroke android:color=\"@color/black\" android:width=\"0.5dp\" />\n" +
            "        </shape>\n" +
            "    </item>\n" +
            "</selector>";
}