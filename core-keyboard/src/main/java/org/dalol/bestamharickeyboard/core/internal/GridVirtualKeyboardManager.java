package org.dalol.bestamharickeyboard.core.internal;

import android.content.Context;
import android.util.Log;

import org.dalol.bestamharickeyboard.core.R;
import org.dalol.bestamharickeyboard.core.models.VirtualKey;
import org.dalol.bestamharickeyboard.core.models.VirtualKeyboard;
import org.dalol.bestamharickeyboard.core.models.VirtualKeysRow;
import org.dalol.bestamharickeyboard.core.utilities.CoreUtils;

import java.util.List;

import androidx.core.content.ContextCompat;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 20:44.
 */
public class GridVirtualKeyboardManager extends VirtualKeyboardManager {

    private static final String TAG = GridVirtualKeyboardManager.class.getSimpleName();

    public GridVirtualKeyboardManager(Context context) {
        super(context);
    }

    @Override
    public void populateKeys(VirtualKeyboard keyboard, int width, int paddingLeft, int paddingTop, List<VirtualKeyInfo> keyInfos) {

        if (keyboard != null) {

            keyInfos.clear();

            Context context = getContext();

            int keyMargin = CoreUtils.toPx(keyboard.getKeyMargin());
            int defaultKeyMaxCount = keyboard.getRowKeyMaxCount();

            int left = paddingLeft + keyMargin;
            int top = paddingTop + keyMargin;

            List<VirtualKeysRow> keysRows = keyboard.getKeysRows();

            for (int row = 0, rowsCount = keysRows.size(); row < rowsCount; row++) {

                VirtualKeysRow keysRow = keysRows.get(row);

                List<VirtualKey> keys = keysRow.getKeys();

                int keyCount = keys.size();
                int rowHeight = CoreUtils.toPx(keysRow.getKeysRowHeight());

                int keysGravityMode = keysRow.getKeysGravity();

                if (keyCount > defaultKeyMaxCount) {
                    keysGravityMode = VirtualKeysRow.GRAVITY_SPREAD_STRETCHED;
                }

                int horizontalGap = (keyCount + 1) * keyMargin;

                int defaultKeyWidth = Math.round((width - horizontalGap) * 1.0f / defaultKeyMaxCount);
                float keysRowWidth = keysRow.getTotalKeysWeight() * defaultKeyWidth;

                int startX = left;
                top += CoreUtils.toPx(keysRow.getKeysMarginTop());

                float keysGap = keyMargin;

                switch (keysGravityMode) {
                    case VirtualKeysRow.GRAVITY_START:
                        break;
                    case VirtualKeysRow.GRAVITY_CENTER:
                        startX += (width - (keysRowWidth + horizontalGap)) * 1.0f / 2;
                        break;
                    case VirtualKeysRow.GRAVITY_END:
                        startX += Math.round(width - (keysRowWidth + (horizontalGap)));
                        break;
                    case VirtualKeysRow.GRAVITY_SPREAD:
                        keysGap = (width - keysRowWidth) * 1.0f / (keyCount + 1);
                        startX += keysGap;
                        break;
                }

                for (int cols = 0; cols < keyCount; cols++) {

                    VirtualKey key = keys.get(cols);

                    int keyWidth = keysGravityMode != VirtualKeysRow.GRAVITY_SPREAD_STRETCHED ?
                            Math.round(defaultKeyWidth * key.getKeyWeight()) : defaultKeyWidth;

//                    key.keyBackground = ContextCompat.getDrawable(context, R.drawable.default_key_background_light);
                    key.keyBackground = ContextCompat.getDrawable(context, R.drawable.default_key_background_dark);

                    VirtualKeyInfo keyInfo = new VirtualKeyInfo(context, key);
                    keyInfo.keyId = (cols * row) + cols;
                    keyInfo.xPos = startX;
                    keyInfo.yPos = top;
                    keyInfo.width = keyWidth;
                    keyInfo.height = rowHeight;

                    keyInfos.add(keyInfo);//adding key to key list
                    startX += keysGap + keyWidth;
                }
                top += keyMargin + rowHeight + CoreUtils.toPx(keysRow.getKeysMarginBottom());
                showLog("TOP_KEY -> " + top + " KEY_HEIGHT -> " + rowHeight);
            }
        }
    }

    private void showLog(String message) {
        Log.d(TAG, message);
    }
}
