package org.dalol.bestamharickeyboard.core.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 18:01.
 */
public class VirtualKeyboard {

    private final static int DEFAULT_TOTAL_KEYS_ROW = 10;
    private final static float DEFAULT_KEY_MARGIN = 8f;

    private final List<VirtualKeysRow> keysRows = new ArrayList<>();

    private int rowKeyMaxCount;
    private float keyMargin;
    private float mKeyboardHeight;

    public static VirtualKeyboard withRowKeyMaxCount(int rowKeyMaxCount) {
        return new VirtualKeyboard(rowKeyMaxCount, DEFAULT_KEY_MARGIN);
    }

    public static VirtualKeyboard withKeysMargin(float keysMargin) {
        return new VirtualKeyboard(DEFAULT_TOTAL_KEYS_ROW, keysMargin);
    }

    public VirtualKeyboard() {
        this(DEFAULT_TOTAL_KEYS_ROW, DEFAULT_KEY_MARGIN);
    }

    public VirtualKeyboard(int rowKeyMaxCount, float keyMargin) {
        this.rowKeyMaxCount = rowKeyMaxCount;
        this.keyMargin = keyMargin;
    }

    public void addVirtualKeysRow(VirtualKeysRow keysRow) {
        mKeyboardHeight += keysRow.getKeysRowHeight() + keysRow.getKeysMarginTop() + keysRow.getKeysMarginBottom();
        keysRows.add(keysRow);
    }

    public List<VirtualKeysRow> getKeysRows() {
        return keysRows;
    }

    public int getRowKeyMaxCount() {
        return rowKeyMaxCount;
    }

    public float getKeyMargin() {
        return keyMargin;
    }

    public float getKeyboardHeight() {
        return mKeyboardHeight;
    }

    public int getKeysRowCount() {
        return keysRows.size();
    }
}
