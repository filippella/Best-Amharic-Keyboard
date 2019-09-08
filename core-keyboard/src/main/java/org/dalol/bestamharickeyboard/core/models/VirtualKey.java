package org.dalol.bestamharickeyboard.core.models;

import android.graphics.drawable.Drawable;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 18:01.
 */
public class VirtualKey {

    private final static float DEFAULT_KEY_WEIGHT = 1f;

    public Drawable keyBackground;
    private String keyLabel;
    private int keyIcon;
    private float keyWeight;
    private String[] keyModifiers;
    private boolean repeatable;
    private boolean lockable;

    public VirtualKey(VirtualKeysRow keysRow) {
        this(keysRow, DEFAULT_KEY_WEIGHT);
    }

    public VirtualKey(VirtualKeysRow keysRow, float keyWeight) {
        this.keyWeight = keyWeight;
    }

    public float getKeyWeight() {
        return keyWeight;
    }

    public void setKeyWeight(float keyWeight) {
        this.keyWeight = keyWeight;
    }

    public String getKeyLabel() {
        return keyLabel;
    }

    public void setKeyLabel(String keyLabel) {
        this.keyLabel = keyLabel;
    }

    public int getKeyIcon() {
        return keyIcon;
    }

    public void setKeyIcon(int keyIcon) {
        this.keyIcon = keyIcon;
    }

    public String[] getKeyModifiers() {
        return keyModifiers;
    }

    public void setKeyModifiers(String[] keyModifiers) {
        this.keyModifiers = keyModifiers;
    }

    public boolean isLockable() {
        return lockable;
    }

    public boolean isRepeatable() {
        return repeatable;
    }
}
