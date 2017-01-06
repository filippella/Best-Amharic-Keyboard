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

package org.dalol.bestamharickeyboard.delegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import org.dalol.bestamharickeyboard.modules.dialog.AboutDialog;
import org.dalol.bestamharickeyboard.uitilities.Constant;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/6/2017
 */
public class MenusDelegate {

    public void rate(Context context) {
        String url;
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            url = "market://details?id=" + Constant.PACKAGE_NAME;
        } catch (final Exception e) {
            url = Constant.LINK_TO_APP;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        context.startActivity(intent);
    }

    public void share(Context context) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey check out Best Amharic/Geez Keyboard at: https://play.google.com/store/apps/details?id=" + Constant.PACKAGE_NAME;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Best Amharic Keyboard");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(sharingIntent);
    }

    public void aboutApp(Activity activity) {
        AboutDialog dialog = new AboutDialog(activity);
        dialog.show();
    }
}
