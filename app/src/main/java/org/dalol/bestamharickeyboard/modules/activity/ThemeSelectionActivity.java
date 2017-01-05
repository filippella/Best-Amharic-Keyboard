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

package org.dalol.bestamharickeyboard.modules.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseActivity;
import org.dalol.bestamharickeyboard.modules.dialog.ThemeSelectorDialog;
import org.dalol.bestamharickeyboard.modules.theme.KeyThemeInfo;
import org.dalol.bestamharickeyboard.modules.theme.ThemesInfo;
import org.dalol.bestamharickeyboard.uitilities.Constant;
import org.dalol.bestamharickeyboard.uitilities.Storage;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static org.dalol.bestamharickeyboard.uitilities.Constant.SELECTED_THEME_ID;
import static org.dalol.bestamharickeyboard.uitilities.Constant.UNSELECTED_THEME_ID;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/4/2017
 */
public class ThemeSelectionActivity extends BaseActivity {

    @BindView(R.id.previewPressedKeyBG) protected ImageView pressedKeyBG;
    @BindView(R.id.previewUnpressedKeyBG) protected ImageView unpressedKeyBG;

    private Storage mStorage;
    private ThemesInfo themesInfo;
    private List<KeyThemeInfo> themes;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showHome();
        changeStatusBarColor(R.color.colorPrimaryDark);
        mStorage = new Storage(getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE));

        int selectedThemeId = mStorage.getInt(SELECTED_THEME_ID, 25);
        int unselectedThemeId = mStorage.getInt(UNSELECTED_THEME_ID, 48);

        themesInfo = new ThemesInfo();
        themes = themesInfo.getThemes();

        pressedKeyBG.setBackgroundDrawable(themesInfo.getGradient(themes.get(selectedThemeId)));
        unpressedKeyBG.setBackgroundDrawable(themesInfo.getGradient(themes.get(unselectedThemeId)));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_theme_selection;
    }

    @OnClick(R.id.selectKeyUnpressedState)
    void onSelectUnpressedKeyStateBg() {
        ThemeSelectorDialog dialog = new ThemeSelectorDialog(ThemeSelectionActivity.this);
        dialog.setOnThemeSelectListener(new ThemeSelectorDialog.OnThemeSelectListener() {
            @Override
            public void onThemeSelect(int position) {
                unpressedKeyBG.setBackgroundDrawable(themesInfo.getGradient(themes.get(position)));
                mStorage.putInt(UNSELECTED_THEME_ID, position);
            }
        });
        dialog.show();
    }

    @OnClick(R.id.selectKeyPressedState)
    void onSelectPressedKeyStateBg() {
        ThemeSelectorDialog dialog = new ThemeSelectorDialog(ThemeSelectionActivity.this);
        dialog.setOnThemeSelectListener(new ThemeSelectorDialog.OnThemeSelectListener() {
            @Override
            public void onThemeSelect(int position) {
                pressedKeyBG.setBackgroundDrawable(themesInfo.getGradient(themes.get(position)));
                mStorage.putInt(SELECTED_THEME_ID, position);
            }
        });
        dialog.show();
    }
}
