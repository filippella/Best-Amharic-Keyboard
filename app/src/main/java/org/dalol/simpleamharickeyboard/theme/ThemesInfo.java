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


import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/10/2016
 */
public class ThemesInfo {

    public List<KeyThemeInfo> getThemes() {

        List<KeyThemeInfo> themeInfos = new ArrayList<>();

        themeInfos.add(new KeyThemeInfo("Ruby", 0xFFf5d2db, 0xFFeba4b8, 0xFFcc1c4d, 0xFF9e032e, 0xFF7a0222, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Fire engine", 0xFFfacdd0, 0xFFeda4a7, 0xFFce2029, 0xFF9e030b, 0xFF73040b, 0xFFffffff));

        themeInfos.add(new KeyThemeInfo("Burgundy", 0xFFead0d2, 0xFFd4a0a4, 0xFF94121c, 0xFF6e060d, 0xFF4a090e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Brick red", 0xFFf4ded6, 0xFFe3ad99, 0xFFa32a00, 0xFF891700, 0xFF611000, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Vermillion", 0xFFe6d1cf, 0xFFcca29f, 0xFFe34234, 0xFFad2416, 0xFF80170e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Red", 0xFFffd4cc, 0xFFffa899, 0xFFff2600, 0xFFbf1d00, 0xFF801300, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Carmine", 0xFFf2ccd4, 0xFFe599aa, 0xFFff0038, 0xFFbf002a, 0xFF80001c, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Orange red", 0xFFfadaca, 0xFFf0b092, 0xFFff4e00, 0xFFc72b00, 0xFF802700, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Dark orange", 0xFFfaded0, 0xFFf6bea0, 0xFFe85c12, 0xFFb53300, 0xFF802200, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Pumkin", 0xFFffe3d1, 0xFFffc8a3, 0xFFff7518, 0xFFaa3e00, 0xFFb13e1e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Orange", 0xFFffecd6, 0xFFffcf99, 0xFFff8f00, 0xFFf26c0d, 0xFFaa3e00, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Orange peel", 0xFFffeccc, 0xFFffd999, 0xFFff9f00, 0xFFf27500, 0xFFc75000, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Coral", 0xFFffe6db, 0xFFffcdb6, 0xFFff8249, 0xFFf06937, 0xFF993b17, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Terracota", 0xFFf7e7e0, 0xFFecc3b2, 0xFFd06a3e, 0xFFb55632, 0xFF823113, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Brown", 0xFFeadbcc, 0xFFd5b799, 0xFF964b00, 0xFF713a00, 0xFF553000, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Chocolate", 0xFFebdfd9, 0xFFccaba1, 0xFF703422, 0xFF521c13, 0xFF4a140b, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Sienna", 0xFFe8d7cf, 0xFFd1afa0, 0xFF8c3611, 0xFF69270d, 0xFF461a09, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Dark coffee", 0xFFe0d7d4, 0xFFc1afa8, 0xFF633826, 0xFF4f1a03, 0xFF361305, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Sepia", 0xFFe3d9cf, 0xFFc7b39f, 0xFF73420e, 0xFF593000, 0xFF402200, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Umber", 0xFFeae0d9, 0xFFd5c2b3, 0xFF956642, 0xFF704d32, 0xFF4b3321, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Tans", 0xFFf3e8df, 0xFFe6d2bf, 0xFFc18e60, 0xFF996a3d, 0xFF73502e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Bronze", 0xFFf6e5d4, 0xFFedcca9, 0xFFd27f29, 0xFFa65c11, 0xFF693906, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Amber", 0xFFfef3d7, 0xFFfce19b, 0xFFffb300, 0xFFed7710, 0xFF9b4200, 0xFF693906));
        themeInfos.add(new KeyThemeInfo("Gold", 0xFFfff9d6, 0xFFfff099, 0xFFffd900, 0xFFffa400, 0xFFe68e15, 0xFF693906));
        themeInfos.add(new KeyThemeInfo("Sunglow", 0xFFfffae9, 0xFFffeaa5, 0xFFffca1d, 0xFFf5a71c, 0xFFe68e15, 0xFF7a4608));
        themeInfos.add(new KeyThemeInfo("Lemon", 0xFFfffdcc, 0xFFfffa99, 0xFFfff300, 0xFFffd400, 0xFFe3ac00, 0xFFa75400));
        themeInfos.add(new KeyThemeInfo("Pear", 0xFFf6f8d1, 0xFFeef2a4, 0xFFd4de1b, 0xFFb5bf07, 0xFF8f9601, 0xFF828a16));
        themeInfos.add(new KeyThemeInfo("Lime", 0xFFeafdcf, 0xFFcbfa87, 0xFFc1f900, 0xFF9fde23, 0xFF7fb900, 0xFF156615));
        themeInfos.add(new KeyThemeInfo("Chlorophyle", 0xFFebf4d3, 0xFFd7e9a8, 0xFF9cc925, 0xFF7ba60d, 0xFF5d8005, 0xFF49811f));
        themeInfos.add(new KeyThemeInfo("Foliage", 0xFFe2e8d1, 0xFFc4d1a4, 0xFF6c8b1b, 0xFF57730e, 0xFF3e5404, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Olive", 0xFFe4eacb, 0xFFd6dfb2, 0xFF697800, 0xFF4a5200, 0xFF2f3600, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Army", 0xFFdbdcd2, 0xFFb7baa5, 0xFF515918, 0xFF343b04, 0xFF26290f, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Grass", 0xFFebfcdf, 0xFFc2f79e, 0xFF5ba825, 0xFF377d00, 0xFF00570a, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Kelly green", 0xFFdbf1cc, 0xFFb6e299, 0xFF49b700, 0xFF378900, 0xFF255c00, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Forest", 0xFFd2e7d2, 0xFFa4cfa4, 0xFF1c881c, 0xFF156615, 0xFF0e440e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Green", 0xFFcce5d8, 0xFF99cbb2, 0xFF007e3e, 0xFF005b2a, 0xFF004420, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Emerald", 0xFFcceae4, 0xFF99d6c8, 0xFF009876, 0xFF007259, 0xFF004c3b, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Turquoise", 0xFFd6f3f0, 0xFF99e1da, 0xFF00b3a2, 0xFF009488, 0xFF005b5a, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Teal", 0xFFdeebee, 0xFFadced4, 0xFF338594, 0xFF006779, 0xFF004d5b, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Cold blue", 0xFFcceaf1, 0xFF99d5e2, 0xFF0095b7, 0xFF007089, 0xFF004b5c, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Cyaan", 0xFFcceffc, 0xFF99dff9, 0xFF00aeef, 0xFF0090bf, 0xFF006d90, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Aquamarine", 0xFFd3f6fd, 0xFFa6edfc, 0xFF21d1f7, 0xFF00a2d9, 0xFF0079a8, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Ice", 0xFFf8fdff, 0xFFf1faff, 0xFFdbf3ff, 0xFFa8e3ff, 0xFF71c2eb, 0xFF25679d));
        themeInfos.add(new KeyThemeInfo("Peace", 0xFFd7eaf8, 0xFFafd4f1, 0xFF3794dd, 0xFF136db5, 0xFF0b5794, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Denim", 0xFFcce0f2, 0xFF99c1e5, 0xFF0064bf, 0xFF004f96, 0xFF003b71, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Steel blue", 0xFFd7e6f0, 0xFFb0cde2, 0xFF3983b6, 0xFF246594, 0xFF154b70, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Azure", 0xFFcce7ff, 0xFF99ceff, 0xFF0085ff, 0xFF0064bf, 0xFF004b8f, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Royal blue", 0xFFd4e2f9, 0xFFa9c6f4, 0xFF2870e3, 0xFF1e54aa, 0xFF143872, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Navy blue", 0xFFccd5e5, 0xFF99aacb, 0xFF003494, 0xFF00205d, 0xFF00163e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Indigo", 0xFFdad1e6, 0xFFb6a3ce, 0xFF481884, 0xFF33036e, 0xFF270059, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Violet", 0xFFe5d4eb, 0xFFcba9d7, 0xFF7c279b, 0xFF5d1d74, 0xFF3e144e, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Fuschia", 0xFFf5d8e9, 0xFFebb1d3, 0xFFce3c92, 0xFFab156d, 0xFF8f0758, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Carnation pink", 0xFFffeef4, 0xFFffdde9, 0xFFffaac9, 0xFFcc7695, 0xFF9c546f, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Salmon", 0xFFffeaed, 0xFFffd5da, 0xFFff95a3, 0xFFd9737f, 0xFFa65059, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("French rose", 0xFFfedde7, 0xFFfdbbd0, 0xFFfb5589, 0xFFc43b67, 0xFFa11d47, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Pink", 0xFFfee5ef, 0xFFffd3df, 0xFFf42376, 0xFFd02261, 0xFFa8025b, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Magenta", 0xFFffd6e9, 0xFFffacd2, 0xFFff308f, 0xFFbf246b, 0xFF801848, 0xFFffffff));
        themeInfos.add(new KeyThemeInfo("Cerise", 0xFFf9d8df, 0xFFf4b2c0, 0xFFe33e61, 0xFFb3213e, 0xFF86192f, 0xFFffffff));
        return themeInfos;
    }

    public GradientDrawable getGradient(KeyThemeInfo themeInfo) {
        GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{themeInfo.getColorE(), themeInfo.getColorC()});
        //gd.setCornerRadius(0f);
        gd.setStroke(1, themeInfo.getColorE());
        gd.setCornerRadius(5f);
        return gd;
    }
}
