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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseActivity;

import androidx.appcompat.app.ActionBar;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/7/2017
 */
public class VideoHelpActivity extends BaseActivity {

    private VideoView mVideoPlayer;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        hideSystemUI();
        super.onViewReady(savedInstanceState, intent);
        mVideoPlayer = findViewById(R.id.videoPlayer);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(mVideoPlayer);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + getMovie());
        mVideoPlayer.setMediaController(mediaController);
        mVideoPlayer.setVideoURI(video);
        mVideoPlayer.start();
    }

    @Override
    protected int getContentView() {
        return 0;
    }

    private void hideSystemUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    public int getMovie() {
        return -1;
    }
}
