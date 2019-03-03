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

package org.dalol.bestamharickeyboard.widgets.list;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/5/2017
 */
public class LinearListView extends LinearLayout {

    public LinearListView(Context context) {
        super(context);
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinearListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public <VH extends LinearListViewAdapter.ViewHolder> void setAdapter(LinearListViewAdapter<VH> adapter) {
        clear();
        int itemCount = adapter.getItemCount();
        for(int position = 0; position < itemCount; position++) {
            VH viewHolder = adapter.onCreateViewHolder(this, adapter.getViewType(position));
            addView(viewHolder.itemView);
            viewHolder.position = position;
            adapter.onBindViewHolder(viewHolder, position);
        }
    }

    public void clear() {
        removeAllViews();
    }
}
