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

package com.lwh.filechooser.bean;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.lwh.filechooser.fs.AbsFile;
import com.lwh.filechooser.util.MimeUtils;

import java.io.File;

public class MyFile extends AbsFile {

    public MyFile(File file) {
        super(file);
    }

    @Override
    public void open(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String type = MimeUtils.getMimetype(getFile());
        intent.setDataAndType(Uri.fromFile(getFile()), type);
        context.startActivity(intent);
    }

    @Override
    public boolean isFolder() {
        return false;
    }
}
