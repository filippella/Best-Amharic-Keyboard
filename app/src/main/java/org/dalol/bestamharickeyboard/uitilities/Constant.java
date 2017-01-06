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

package org.dalol.bestamharickeyboard.uitilities;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 1/4/2017
 */
public class Constant {

    public static final int SHARE_APP_INDEX = 0;
    public static final int RATE_APP_INDEX = 1;
    public static final int ABOUT_APP_INDEX = 2;

    public static int ENABLE_KEYBOARD_REQUEST_CODE = 0;

    public final static String PACKAGE_NAME = "org.dalol.bestamharickeyboard";
    public static final String LINK_TO_APP = "https://play.google.com/store/apps/details?id=" + PACKAGE_NAME;

    public static final String PREFERENCE_NAME = PACKAGE_NAME + ":storage_preference";
    public static final String SELECTED_THEME_ID = PACKAGE_NAME + ":selected_theme";
    public static final String UNSELECTED_THEME_ID = PACKAGE_NAME + ":unselected_theme";
    public static final String THEME_CHANGED_ID = PACKAGE_NAME + ":theme_changed";
}
