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

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.dalol.simpleamharickeyboard.R;
import org.dalol.simpleamharickeyboard.keyboard.AmharicKeyboardService;
import org.dalol.simpleamharickeyboard.theme.KeyThemeInfo;
import org.dalol.simpleamharickeyboard.theme.ThemesInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity {

    @BindView(R.id.navigationDrawerKeyboardSetting) protected DrawerLayout drawerLayout;
    @BindView(R.id.navigationViewKeyboardSetting) protected NavigationView navigationView;
    @BindView(R.id.imageViewKeyboardEnabled) protected ImageView keyboardEnabledImageView;
    @BindView(R.id.imageViewKeyboardSelected) protected ImageView keyboardSelectedImageView;

    private Unbinder bind;
    private ActionBarDrawerToggle drawerToggle;
    private boolean shown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind = ButterKnife.bind(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        DisplayMetrics metrics = displayMetrics;
        float screenWidth = metrics.widthPixels / metrics.density;

        if (screenWidth <= 360) {
            navigationView.getLayoutParams().width = displayMetrics.widthPixels - Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, displayMetrics));
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
        //navigationView.setItemIconTintList(null);

        ThemesInfo themesInfo = new ThemesInfo();
        List<KeyThemeInfo> themes = themesInfo.getThemes();
        GradientDrawable drawable = themesInfo.getGradient(themes.get(3));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setBackgroundDrawable(drawable);
        //toolbar.setTitle("Amharic Keyboard");
        setSupportActionBar(toolbar);
        showBack();


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                supportInvalidateOptionsMenu();
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED);
        registerReceiver(mReceiver, filter);
        keyboardEnabledImageView.setImageDrawable(getKeyboardStatusDrawable(isBestAmharicKeyboardEnabled()));
        showDialog("Getting configurations...");
        configureStatus();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    private Drawable getKeyboardStatusDrawable(boolean enabled) {
        Drawable drawable;
        if(enabled) {
            drawable = ContextCompat.getDrawable(this, R.mipmap.ic_done_all_white_24dp);
            tintDrawable(drawable, R.color.green);
        } else {
            drawable = ContextCompat.getDrawable(this, R.mipmap.ic_clear_white_24dp);
            tintDrawable(drawable, R.color.red);
        }
        return drawable;
    }

    @Override
    protected void onDestroy() {
        if (bind != null) {
            bind.unbind();
            bind = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    private boolean isBestAmharicKeyboardEnabled() {
        String packageLocal = getPackageName();
        boolean isInputDeviceEnabled = false;
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        List<InputMethodInfo> list = inputMethodManager.getEnabledInputMethodList();
        for (InputMethodInfo inputMethod : list) {
            String packageName = inputMethod.getPackageName();
            if (packageName.equals(packageLocal)) {
                isInputDeviceEnabled = true;
            }
        }
        return isInputDeviceEnabled;
    }

    @OnClick(R.id.optionEnableKeyboardView)
    void onEnableKeyboardOptionClicked() {
        Intent intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
        startActivityForResult(intent, 0);
    }

    @OnClick(R.id.optionSelectKeyboardView)
    void onSelectKeyboardOptionClicked() {
        InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
    }

    @OnClick(R.id.optionOpenEditor)
    void onOpenEditorOptionClicked() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setView(R.layout.editor_layout)
                .setCancelable(true)
                .create();
//        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialog) {
//                AlertDialog d = (AlertDialog) dialog;
//                EditText editorView = (EditText) d.findViewById(R.id.editorEditText);
//                editorView.requestFocus();
//            }
//        });

        dialog.show();
        Window window = dialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    InputMethodChangeReceiver mReceiver = new InputMethodChangeReceiver();
    public class InputMethodChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_INPUT_METHOD_CHANGED)) {
                showDialog("Configuring Keyboard...");
                configureStatus();
            }
        }
    }

    private void configureStatus() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                keyboardSelectedImageView.setImageDrawable(getKeyboardStatusDrawable(isMyServiceRunning(AmharicKeyboardService.class)));
                hideDialog();
            }
        }, 250L);
    }
}
