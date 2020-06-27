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

import com.lwh.filechooser.bean.Sort;

import java.io.File;
import java.io.IOException;

/**
 * 抽象文件的接口，可以代表一个文件，也可以代表一个目录。
 */
public interface F extends Sort {

    /**
     * 复制到另一个目录下面。
     *
     * @param f
     */
    void copy(AbsFolder f) throws IOException;

    /**
     * 是否是目录。
     *
     * @return
     */
    boolean isFolder();

    /**
     * 从文件系统中清除掉。
     */
    void clear();

    /**
     * 获取文件对象。
     *
     * @return
     */
    File getFile();

    /**
     * 获取名字。
     *
     * @return
     */
    String getName();

    /**
     * 获取路径。
     *
     * @return
     */
    String getPath();

    /**
     * 获取上次修改的时间。
     *
     * @return
     */
    long lastModified();

    /**
     * 获取父目录的路径。
     *
     * @return
     */
    String getParent();

    /**
     * 获取父目录。
     *
     * @return
     */
    File getParentFile();
}
