package org.dalol.bestamharickeyboard.core.models.common;

/**
 * @author Filippo Engidashet
 * @version 1.0.0
 * @since Sat, 18/05/2019 at 21:19.
 */
public abstract class AbstractRunnable<S> implements Runnable {

    private final S subject;

    protected AbstractRunnable(S subject) {
        this.subject = subject;
    }

    @Override
    public final void run() {
        run(this.subject);
    }

    protected abstract void run(S subject);
}
