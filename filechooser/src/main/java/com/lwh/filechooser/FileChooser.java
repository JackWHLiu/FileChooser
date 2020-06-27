/*
 * Copyright (C) 2019 The JackKnife Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lwh.filechooser;

import android.app.Activity;
import android.content.Intent;

import com.lwh.filechooser.ui.FileExplorerActivity;

public class FileChooser {

    public static final int REQUEST_CODE_CHOOSE_FILE = 1;
    public static final int REQUEST_CODE_CHOOSE_FOLDER = 2;
    public static final int REQUEST_CODE_CHOOSE_BOTH_FILE_AND_FOLDER = 3;

    public static void chooseFile(Activity activity) {
        Intent intent = new Intent(activity, FileExplorerActivity.class);
        intent.setAction(FileExplorerActivity.ACTION_CHOOSE_FILE);
        activity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_FILE);
    }

    public static void chooseFolder(Activity activity) {
        Intent intent = new Intent(activity, FileExplorerActivity.class);
        intent.setAction(FileExplorerActivity.ACTION_CHOOSE_FOLDER);
        activity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_FOLDER);
    }

    public static void chooseFileAndFolder(Activity activity) {
        Intent intent = new Intent(activity, FileExplorerActivity.class);
        intent.setAction(FileExplorerActivity.ACTION_CHOOSE_BOTH_FILE_AND_FOLDER);
        activity.startActivityForResult(intent, REQUEST_CODE_CHOOSE_BOTH_FILE_AND_FOLDER);
    }
}
