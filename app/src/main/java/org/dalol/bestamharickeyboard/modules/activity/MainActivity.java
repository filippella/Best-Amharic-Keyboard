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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseActivity;
import org.dalol.bestamharickeyboard.delegate.AdsDelegate;
import org.dalol.bestamharickeyboard.delegate.MenusDelegate;
import org.dalol.bestamharickeyboard.keyboard.service.AmharicKeyboardService;
import org.dalol.bestamharickeyboard.uitilities.Constant;

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
    @BindView(R.id.adView) protected AdView mAdView;

    private ActionBarDrawerToggle mDrawerToggle;
    private AdsDelegate adsDelegate;
    private MenusDelegate menusDelegate;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;

        if (screenWidth <= 360) {
            mNavigationView.getLayoutParams().width = displayMetrics.widthPixels - Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, displayMetrics));
        }
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_share:
                        handler.sendEmptyMessageDelayed(Constant.SHARE_APP_INDEX, 150L);
                        break;
                    case R.id.nav_rate_app:
                        handler.sendEmptyMessageDelayed(Constant.RATE_APP_INDEX, 150L);
                        break;
                    case R.id.nav_about:
                        handler.sendEmptyMessageDelayed(Constant.ABOUT_APP_INDEX, 150L);
                        break;
                }
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

        menusDelegate = new MenusDelegate();

        adsDelegate = new AdsDelegate(mAdView);
        adsDelegate.handleAdBanner();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED);
        registerReceiver(mReceiver, filter);
        applyStatusDrawable(keyboardEnabledImageView, isBestAmharicKeyboardEnabled());
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

    private void applyStatusDrawable(ImageView imageView, boolean enabled) {
        Drawable drawable;
        int color;
        if(enabled) {
            drawable = ContextCompat.getDrawable(this, R.mipmap.ic_done_all_white_24dp);
            color = R.color.green;
        } else {
            drawable = ContextCompat.getDrawable(this, R.mipmap.ic_clear_white_24dp);
            color = R.color.red;
        }
        imageView.setImageDrawable(drawable);
        tintDrawable(drawable, color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if(item.getItemId() == R.id.actionHelp) {
            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Coming soon..")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
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
        showAd();
        startActivity(new Intent(this, ThemeSelectionActivity.class));
    }

    @OnClick(R.id.optionOpenEditor)
    void onOpenEditorOptionClicked() {
        showAd();
        startActivity(new Intent(this, TypingActivity.class));
    }

    private void showAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                applyStatusDrawable(keyboardSelectedImageView, isMyServiceRunning(AmharicKeyboardService.class));
                hideDialog();
            }
        }, 250L);
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.SHARE_APP_INDEX:
                    menusDelegate.share(getApplicationContext());
                    break;
                case Constant.RATE_APP_INDEX:
                    menusDelegate.rate(getApplicationContext());
                    break;
                case Constant.ABOUT_APP_INDEX:
                    menusDelegate.aboutApp(MainActivity.this);
                    break;
            }
        }
    };
}
