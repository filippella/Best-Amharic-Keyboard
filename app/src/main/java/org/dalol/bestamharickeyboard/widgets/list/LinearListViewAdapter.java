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

import android.view.View;
import android.view.ViewGroup;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/5/2017
 */
public interface LinearListViewAdapter<T extends LinearListViewAdapter.ViewHolder> {

    T onCreateViewHolder(ViewGroup parent);

    int getItemCount();

    void onBindViewHolder(T viewHolder, int position);

    abstract class ViewHolder {
        private View itemView;
        private int position;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public View getItemView() {
            return itemView;
        }

        public int getItemPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}