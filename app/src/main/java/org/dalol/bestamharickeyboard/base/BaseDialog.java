/*
 * Copyright (c) 2017 Filippo Engidashet
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

package org.dalol.bestamharickeyboard.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import org.dalol.bestamharickeyboard.R;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/5/2017
 */
public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        this(context, android.R.style.Theme_Holo_Light_Dialog);
    }

    public BaseDialog(Context context, int style) {
        super(new ContextThemeWrapper(context, style));
        initialize(context);
    }

    @CallSuper
    protected void initialize(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(isCancelable());
        setContentView(getContentView());
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Window window = getWindow();
                window.setGravity(Gravity.CENTER);
                int currentWidth = window.getDecorView().getWidth();
                int currentHeight = window.getDecorView().getHeight();
                WindowManager.LayoutParams params = window.getAttributes();
                int maxWidth = getMaxWidth();
                if (currentWidth > maxWidth) {
                    params.width = maxWidth;
                }
                int maxHeight = getMaxHeight();
                if (currentHeight > maxHeight) {
                    params.height = maxHeight;
                }
                window.setAttributes(params);
            }
        });
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                onDialogVisible();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = getHeight();
        params.width = getWidth();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        params.windowAnimations = R.style.AboutDialogAnimation;
        onDialogReady(savedInstanceState);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public boolean isCancelable() {
        return false;
    }

    public int getMaxHeight() {
        return getHeight();
    }

    public int getMaxWidth() {
        return getWidth();
    }

    public int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public int getWidth() {
        return Math.round(getContext().getResources().getDimensionPixelSize(R.dimen.max_dialog_width));
    }

    protected void onDialogReady(Bundle savedInstanceState) {}

    protected void onDialogVisible() {}

    protected abstract int getContentView();
}