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

package org.dalol.simpleamharickeyboard.activity;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/10/2016
 */
public class BaseActivity extends AppCompatActivity {


    protected void changeStatusBarColor(int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && statusBarColor > 0) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, statusBarColor));
        }
    }

    protected void showBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    protected void showHome(int resId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            showBack();
            actionBar.setHomeAsUpIndicator(resId);
        }
    }

    protected void showHome(int resId, int colorTint) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            showBack();
            Drawable drawable = ContextCompat.getDrawable(this, resId);
            drawable.setColorFilter(new
                    PorterDuffColorFilter(ContextCompat.getColor(this, colorTint), PorterDuff.Mode.SRC_ATOP));
            actionBar.setHomeAsUpIndicator(drawable);
        }
    }

    protected void showHome(Drawable drawable) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            showBack();
            actionBar.setHomeAsUpIndicator(drawable);
        }
    }
}
