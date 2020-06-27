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

package com.lwh.filechooser.fs;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 抽象的文件
 */
public abstract class AbsFile implements IFile {

    private File file;
    protected String sortLetter = "";

    public AbsFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return file.getName();
    }

    public String getPath() {
        return file.getPath();
    }

    public long lastModified() {
        return file.lastModified();
    }

    public String getParent() {
        return file.getParent();
    }

    public File getParentFile() {
        return file.getParentFile();
    }

    public void copy(AbsFolder f) throws IOException {
        File folder = f.getFile();//目标文件夹
        InputStream is = new FileInputStream(file);
        OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(folder, file.getName())));
        int len;
        byte[] buff = new byte[1024];
        while ((len = is.read(buff)) != -1) {
            os.write(buff, 0, len);
        }
        os.flush();
        os.close();
        is.close();
    }

    public void clear() {
        if (file != null) {
            file.delete();
        }
    }

    public abstract void open(Context context);

    @Override
    public String getSortLetter() {
        return sortLetter;
    }

    @Override
    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }
}
