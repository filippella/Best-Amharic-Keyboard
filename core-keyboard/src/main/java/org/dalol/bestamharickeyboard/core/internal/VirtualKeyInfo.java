package org.dalol.bestamharickeyboard.core.internal;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sun, 28/04/2019 at 20:24.
 */
public class Key {

    private final int width, height;
    public int x, y;
    public String label;
    public int icon;//if icon then no label on the key
    public boolean locked;//caps lock or shift
    public boolean pressed;//key state
    public boolean reTypeOnHold;

    public Key(int width, int height) {
        this.width = width;
        this.height = height;

    }
}
