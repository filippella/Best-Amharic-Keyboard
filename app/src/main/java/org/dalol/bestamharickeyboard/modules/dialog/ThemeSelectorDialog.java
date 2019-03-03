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

package org.dalol.bestamharickeyboard.modules.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseDialog;
import org.dalol.bestamharickeyboard.modules.theme.KeyThemeInfo;
import org.dalol.bestamharickeyboard.modules.theme.ThemesInfo;

import java.util.List;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/5/2017
 */
public class ThemeSelectorDialog extends BaseDialog implements View.OnClickListener {

    private OnThemeSelectListener listener;
    private int keyBGPosition;

    public ThemeSelectorDialog(Context context, int keyBGPosition) {
        super(context);
        this.keyBGPosition = keyBGPosition;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_theme_selection;
    }

    @Override
    public int getMaxHeight() {
        return Math.round(getContext().getResources().getDimensionPixelSize(R.dimen.max_dialog_height));
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    @Override
    protected void onDialogReady(Bundle savedInstanceState) {
        super.onDialogReady(savedInstanceState);
        RecyclerView themes = findViewById(R.id.themesList);
        themes.setLayoutManager(new LinearLayoutManager(getContext()));
        themes.setHasFixedSize(true);
        themes.setAdapter(new ThemeListAdapter(new ThemesInfo()));
        themes.getLayoutManager().scrollToPosition(keyBGPosition);

        findViewById(R.id.donetOption).setOnClickListener(this);
        findViewById(R.id.resetDefaultOption).setOnClickListener(this);
    }

    void onDoneClicked() {
        if (listener != null) {
            listener.onThemeSelect(keyBGPosition);
        }
        dismiss();
    }

    void onResetToDefaultOptionClicked() {
        this.listener.onResetToDefault();
        dismiss();
    }

    public void setOnThemeSelectListener(OnThemeSelectListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.donetOption:
                onDoneClicked();
                break;
            case R.id.resetDefaultOption:
                onResetToDefaultOptionClicked();
                break;
        }
    }

    public class ThemeListAdapter extends RecyclerView.Adapter<ThemeListAdapter.Holder> {

        private final ThemesInfo themesInfo;
        private final List<KeyThemeInfo> themes;

        public ThemeListAdapter(ThemesInfo themesInfo) {
            this.themesInfo = themesInfo;
            this.themes = themesInfo.getThemes();
            this.themes.get(keyBGPosition).setSelected(true);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(getLayoutInflater().inflate(R.layout.item_theme_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            View itemView = holder.itemView;
            KeyThemeInfo themeInfo = themes.get(position);
            holder.themeSelected.setVisibility(themeInfo.isSelected() ? View.VISIBLE: View.GONE);
            itemView.setBackgroundDrawable(themesInfo.getGradient(themeInfo));
            TextView themeName = holder.themeName;
            themeName.setText(themeInfo.getColorName());
            themeName.setTextColor(themeInfo.getTextColor());
            holder.themeBG.setText(Integer.toString(position + 1));
        }

        @Override
        public int getItemCount() {
            return themes.size();
        }

        public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView themeBG;
            private TextView themeName;
            private ImageView themeSelected;

            public Holder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                themeBG = (TextView) itemView.findViewById(R.id.theme_bg);
                themeName = (TextView) itemView.findViewById(R.id.theme_name);
                themeSelected = (ImageView) itemView.findViewById(R.id.theme_selected);
            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                KeyThemeInfo info = themes.get(position);
                if(!info.isSelected()) {
                    themes.get(keyBGPosition).setSelected(false);
                    info.setSelected(true);
                    notifyDataSetChanged();
                    keyBGPosition = position;
                    if(listener != null) {
                        listener.onThemeSelect(position);
                    }
                }
            }
        }
    }

    public interface OnThemeSelectListener {

        void onThemeSelect(int position);

        void onResetToDefault();
    }
}
