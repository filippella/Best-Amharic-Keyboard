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

package org.dalol.bestamharickeyboard.modules.activity;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
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

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseActivity;
import org.dalol.bestamharickeyboard.keyboard.AmharicKeyboardService;
import org.dalol.bestamharickeyboard.modules.dialog.ThemeSelectorDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static org.dalol.bestamharickeyboard.uitilities.Constant.ENABLE_KEYBOARD_REQUEST_CODE;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.navigationDrawerKeyboardSetting) protected DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationViewKeyboardSetting) protected NavigationView mNavigationView;
    @BindView(R.id.imageViewKeyboardEnabled) protected ImageView keyboardEnabledImageView;
    @BindView(R.id.imageViewKeyboardSelected) protected ImageView keyboardSelectedImageView;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        DisplayMetrics metrics = displayMetrics;
        float screenWidth = metrics.widthPixels / metrics.density;

        if (screenWidth <= 360) {
            mNavigationView.getLayoutParams().width = displayMetrics.widthPixels - Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, displayMetrics));
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(MainActivity.this, "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });

        showHome();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDrawer, R.string.closeDrawer) {
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
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        showDialog("Getting configurations...");
        configureStatus();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED);
        registerReceiver(mReceiver, filter);
        keyboardEnabledImageView.setImageDrawable(getKeyboardStatusDrawable(isBestAmharicKeyboardEnabled()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ENABLE_KEYBOARD_REQUEST_CODE) {
            showDialog("Configuring Keyboard...");
            configureStatus();
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        startActivityForResult(intent, ENABLE_KEYBOARD_REQUEST_CODE);
    }

    @OnClick(R.id.optionSelectKeyboardView)
    void onSelectKeyboardOptionClicked() {
        InputMethodManager imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imeManager.showInputMethodPicker();
    }

    @OnClick(R.id.optionChangeTheme)
    void onChangeThemeOptionClicked() {
        startActivity(new Intent(this, ThemeSelectionActivity.class));
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

//        EditText editorView = (EditText) dialog.findViewById(R.id.editorEditText);
//        editorView.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d(TAG, "Input is -> " + s);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
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
