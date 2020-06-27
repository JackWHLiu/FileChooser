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

package com.lwh.filechooser.util;

import com.lwh.filechooser.bean.Sort;

import java.util.Comparator;

/**
 * 通过首字母排序的比较器。
 */
public class PinyinComparator implements Comparator<Sort> {

    private static PinyinComparator sComparator = new PinyinComparator();

    @Override
    public int compare(Sort lhs, Sort rhs) {
        if (lhs.getSortLetter().equals("@")
                || rhs.getSortLetter().equals("#")) {
            return -1;
        } else if (lhs.getSortLetter().equals("#")
                || rhs.getSortLetter().equals("@")) {
            return 1;
        } else {
            return lhs.getSortLetter().compareTo(rhs.getSortLetter());
        }
    }

    public static PinyinComparator get() {
        return sComparator;
    }
}