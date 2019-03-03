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
import android.view.View;

import org.dalol.bestamharickeyboard.R;
import org.dalol.bestamharickeyboard.base.BaseDialog;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/6/2017
 */
public class AboutDialog extends BaseDialog {


    public AboutDialog(Context context) {
        super(context);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_about_app;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    @Override
    protected void onDialogReady(Bundle savedInstanceState) {
        super.onDialogReady(savedInstanceState);
        findViewById(R.id.closeDialog)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
    }
}
