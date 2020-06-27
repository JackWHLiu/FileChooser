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

import java.util.List;

/**
 * 文件夹接口
 */
public interface IFolder extends F {

    /**
     * 进入
     */
    List<F> enter();

    /**
     * 添加子节点
     * @param f
     */
    void addChild(F f);

    /**
     * 得到所有的子节点
     * @return
     */
    List<F> getAllChild();

    /**
     * 得到指定位置的子节点
     * @param position
     * @return
     */
    F getChildAt(int position);
}
