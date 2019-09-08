package org.dalol.bestamharickeyboard.core.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 18:01.
 */
public class VirtualKeysRow {

    /**
     * Packed to the left
     * { [x][x][x]               }
     */
    public final static int GRAVITY_START = 0x0001;
    /**
     * Packed to center
     * {        [x][x][x]        }
     */
    public final static int GRAVITY_CENTER = 0x0002;
    /**
     * Packed to the right
     * {               [x][x][x] }
     */
    public final static int GRAVITY_END = 0x0003;
    /**
     * Spread equally
     * {     [x]   [x]   [x]     }
     */
    public final static int GRAVITY_SPREAD = 0x0004;
    /**
     * Spread equally edge to edge
     * { [x]       [x]       [x] }
     */
    public final static int GRAVITY_EDGE_SPREAD = 0x0005;
    /**
     * { [  x  ] [  x  ] [  x  ] }
     */
    public final static int GRAVITY_SPREAD_STRETCHED = 0x0006;

    public final static float DEFAULT_KEYS_ROW_HEIGHT = 48f;
    public final static float DEFAULT_KEYS_MARGIN = 0f;

    private final List<VirtualKey> keys = new ArrayList<>();
    private float keysRowHeight;
    private float totalKeysWeight;
    private int keysGravity;
    private int keysRowWidth;
    private float keysMarginTop;
    private float keysMarginBottom;

    public static VirtualKeysRow withRowGravity(int keysGravity) {
        return new VirtualKeysRow(DEFAULT_KEYS_ROW_HEIGHT, keysGravity, DEFAULT_KEYS_MARGIN, DEFAULT_KEYS_MARGIN);
    }

    public static VirtualKeysRow withRowHeight(float keysHeight) {
        return new VirtualKeysRow(keysHeight, GRAVITY_START, DEFAULT_KEYS_MARGIN, DEFAULT_KEYS_MARGIN);
    }

    public static VirtualKeysRow withRowMargins(float keysMarginTop, float keysMarginBottom) {
        return new VirtualKeysRow(DEFAULT_KEYS_ROW_HEIGHT, GRAVITY_START, keysMarginTop, keysMarginBottom);
    }

    public static VirtualKeysRow withRowMargins(float keysHeight, float keysMarginTop, float keysMarginBottom) {
        return new VirtualKeysRow(keysHeight, GRAVITY_START, keysMarginTop, keysMarginBottom);
    }

    public VirtualKeysRow() {
        this(DEFAULT_KEYS_ROW_HEIGHT, GRAVITY_START, DEFAULT_KEYS_MARGIN, DEFAULT_KEYS_MARGIN);
    }

    public VirtualKeysRow(float keysRowHeight, int keysGravity, float keysMarginTop, float keysMarginBottom) {
        this.keysRowHeight = keysRowHeight;
        this.keysGravity = keysGravity;
        this.keysMarginTop = keysMarginTop;
        this.keysMarginBottom = keysMarginBottom;
    }

    public void addVirtualKey(VirtualKey key) {
        totalKeysWeight += key.getKeyWeight();
        keys.add(key);
    }

    public float getKeysRowHeight() {
        return keysRowHeight;
    }

    public int getKeysGravity() {
        return keysGravity;
    }

    public List<VirtualKey> getKeys() {
        return keys;
    }

    public void setKeysRowWidth(int keysRowWidth) {
        this.keysRowWidth = keysRowWidth;
    }

    public int getKeysRowWidth() {
        return keysRowWidth;
    }

    public float getTotalKeysWeight() {
        return totalKeysWeight;
    }

    public float getKeysMarginTop() {
        return keysMarginTop;
    }

    public float getKeysMarginBottom() {
        return keysMarginBottom;
    }
}
