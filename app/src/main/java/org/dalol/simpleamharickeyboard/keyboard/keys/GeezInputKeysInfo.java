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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/18/2016
 */
public class GeezInputKeysInfo implements InputKeysInfo {

    private List<InputKeysRow> inputKeysRows = new ArrayList<>();
    private GeezKeyModifier keyModifier;

    @Override
    public List<InputKeysRow> getKeysRowList() {

        if(inputKeysRows == null || inputKeysRows.isEmpty()) {
            InputKeysRow keysRow1 = new InputKeysRow();

            keysRow1.addKeyInfo(new KeyInfo("\u1200", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1208", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1210", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1218", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1220", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1228", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1230", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1238", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1240", 1, true));
            keysRow1.addKeyInfo(new KeyInfo("\u1260", 1, true));

            InputKeysRow keysRow2 = new InputKeysRow();
            keysRow2.addKeyInfo(new KeyInfo("\u1268", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u1270", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u1278", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u1280", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u1290", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u1298", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u12A0", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u12A8", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u12B8", 1, true));
            keysRow2.addKeyInfo(new KeyInfo("\u12C8", 1, true));

            InputKeysRow keysRow3 = new InputKeysRow();
            keysRow3.addKeyInfo(new KeyInfo("\u12D0", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u12D8", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u12E0", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u12E8", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u12F0", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u1300", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u1308", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u1320", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u1328", 1, true));
            keysRow3.addKeyInfo(new KeyInfo("\u1330", 1, true));

            InputKeysRow keysRow4 = new InputKeysRow();
            keysRow4.addKeyInfo(new KeyInfo("\u1338", 1, true));
            keysRow4.addKeyInfo(new KeyInfo("\u1340", 1, true));
            keysRow4.addKeyInfo(new KeyInfo("\u1348", 1, true));
            keysRow4.addKeyInfo(new KeyInfo("\u1350", 1, true));

            inputKeysRows.add(keysRow1);
            inputKeysRows.add(keysRow2);
            inputKeysRows.add(keysRow3);
            inputKeysRows.add(keysRow4);
        }
        return inputKeysRows;
    }

    @Override
    public String[] getModifiers(String keyLabel) {
        if(keyModifier == null) {
            keyModifier = new GeezKeyModifier();
        }
        return keyModifier.findModifiers(keyLabel);
    }
}
