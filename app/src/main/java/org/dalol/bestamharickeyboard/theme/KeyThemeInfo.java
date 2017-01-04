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

package org.dalol.bestamharickeyboard.theme;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/10/2016
 */
public class KeyThemeInfo {

    private String Name;
    private int ColorA;
    private int ColorB;
    private int ColorC;
    private int ColorD;
    private int ColorE;
    private int TextColor;

    public KeyThemeInfo(String name, int colorA, int colorB, int colorC, int colorD, int colorE, int textColor) {
        Name = name;
        ColorA = colorA;
        ColorB = colorB;
        ColorC = colorC;
        ColorD = colorD;
        ColorE = colorE;
        setTextColor(textColor);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getColorA() {
        return ColorA;
    }

    public void setColorA(int colorA) {
        ColorA = colorA;
    }

    public int getColorB() {
        return ColorB;
    }

    public void setColorB(int colorB) {
        ColorB = colorB;
    }

    public int getColorC() {
        return ColorC;
    }

    public void setColorC(int colorC) {
        ColorC = colorC;
    }

    public int getColorD() {
        return ColorD;
    }

    public void setColorD(int colorD) {
        ColorD = colorD;
    }

    public int getColorE() {
        return ColorE;
    }

    public void setColorE(int colorE) {
        ColorE = colorE;
    }

    public int getTextColor() {
        return TextColor;
    }

    public void setTextColor(int textColor) {
        TextColor = textColor;
    }
}
