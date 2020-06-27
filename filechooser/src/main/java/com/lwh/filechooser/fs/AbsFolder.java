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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsFolder implements IFolder {

    private File folder;
    protected String sortLetter = "";

    /**
     * 保存子节点的集合
     */
    protected List<F> mChildFileable;

    public AbsFolder(File folder) {
        this.folder = folder;
    }

    public String getName() {
        return folder.getName();
    }

    public String getPath() {
        return folder.getPath();
    }

    public String getParent() {
        return folder.getParent();
    }

    public long lastModified() {
        return folder.lastModified();
    }

    public int getChildCount() {
        return folder.list().length;
    }

    public File getFile() {
        return folder;
    }

    public File getParentFile() {
        return folder.getParentFile();
    }

    @Override
    public void addChild(F f) {
        if (mChildFileable == null) {
            mChildFileable = new ArrayList<F>();
        }
        mChildFileable.add(f);
    }

    @Override
    public List<F> getAllChild() {
        return mChildFileable;
    }

    @Override
    public F getChildAt(int position) {
        if (mChildFileable == null) {
            return null;
        }
        return mChildFileable.get(position);
    }

    @Override
    public String getSortLetter() {
        return sortLetter;
    }

    @Override
    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }
}
