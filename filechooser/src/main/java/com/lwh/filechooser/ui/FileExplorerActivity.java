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

package com.lwh.filechooser.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lwh.filechooser.FileAdapter;
import com.lwh.filechooser.bean.MyFile;
import com.lwh.filechooser.bean.MyFolder;
import com.lwh.filechooser.fs.F;
import com.lwh.fileexplorer.R;
import com.lwh.jackknife.permission.Action;
import com.lwh.jackknife.permission.XPermission;
import com.lwh.jackknife.permission.runtime.Permission;
import com.lwh.jackknife.util.IoUtils;
import com.lwh.jackknife.util.ToastUtils;
import com.lwh.jackknife.widget.LetterView;

import java.io.File;
import java.util.List;

public class FileExplorerActivity extends Activity {

    private TextView tv_main_curr_path;
    private TextView tv_main_total_rom;
    private TextView tv_main_available_rom;
    private TextView tv_titlebar_left;
    private TextView tv_titlebar_right;
    private ListView mFileListView;
    private LetterView mLetterView;
    private TextView mTextDialog;
    private List<F> mFileTree;
    private FileAdapter mAdapter;
    public static final String ACTION_CHOOSE_FILE = "com.lwh.filechooser.action.CHOOSE_FILE";
    public static final String ACTION_CHOOSE_FOLDER = "com.lwh.filechooser.action.CHOOSE_FOLDER";
    public static final String ACTION_CHOOSE_BOTH_FILE_AND_FOLDER = "com.lwh.filechooser.action.CHOOSE_BOTH_FILE_AND_FOLDER";
    public static final String EXTRA_PATH = "path";
    private String mAction;

    private final int REQUEST_CODE_SETTING = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);
        Intent intent = getIntent();
        mAction = intent.getAction();
        if (mAction == null) {
            finish();
        }
        initViews();
        XPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        initData();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        if (XPermission.hasAlwaysDeniedPermission(FileExplorerActivity.this, permissions)) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XPermission.with(FileExplorerActivity.this).runtime().setting().start(REQUEST_CODE_SETTING);
                        }
                    }
                }).start();
    }

    private void initViews() {
        tv_main_curr_path = (TextView) findViewById(R.id.tv_main_curr_path);
        tv_main_total_rom = (TextView) findViewById(R.id.tv_main_total_rom);
        tv_main_available_rom = (TextView) findViewById(R.id.tv_main_available_rom);
        tv_titlebar_left = (TextView) findViewById(R.id.tv_titlebar_left);
        tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
        mFileListView = (ListView) findViewById(R.id.mFileListView);
        mLetterView = (LetterView) findViewById(R.id.mLetterView);
        mTextDialog = (TextView) findViewById(R.id.mTextDialog);
    }


    private void initData() {
        tv_main_curr_path.setText(IoUtils.getSdRoot());
        tv_main_total_rom.setText("总共 " + IoUtils.getRomTotalSize(this));
        tv_main_available_rom.setText("剩余 " + IoUtils.getRomAvailableSize(this));
        final MyFolder myFolder = initFolder(new MyFolder(new File(IoUtils.getSdRoot())));
        mFileTree = myFolder.getAllChild();
        mAdapter = new FileAdapter(this);
        mAdapter.addItems(mFileTree);
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                F fileable = mAdapter.getItem(position);
                if (fileable instanceof MyFile) {
                    if (mAction.equals(ACTION_CHOOSE_FILE) || mAction.equals(ACTION_CHOOSE_BOTH_FILE_AND_FOLDER)) {
                        Intent intent = new Intent();
                        intent.putExtra(EXTRA_PATH, fileable.getPath());
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        ToastUtils.showShort(FileExplorerActivity.this, "请选择目录");
                    }
                } else if (fileable instanceof MyFolder) {
                    List<F> subFiles = ((MyFolder) fileable).enter();//拿到子目录所有文件
                    mAdapter.clear();
                    mAdapter.addItems(subFiles);
                    tv_main_curr_path.setText(fileable.getPath());
                }
            }
        });
        mLetterView.setOnLetterChangeListener(new LetterView.OnLetterChangeListener() {
            @Override
            public void onChanged(String letter) {
                mTextDialog.setText(letter);
                int positionForSection = mAdapter.getPositionForSection(letter.toUpperCase().charAt(0));
                mFileListView.setSelection(positionForSection);
            }
        });
        mLetterView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTextDialog.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_UP:
                        mTextDialog.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        tv_titlebar_left.setText("上一级");
        if (mAction.equals(ACTION_CHOOSE_FOLDER) || mAction.equals(ACTION_CHOOSE_BOTH_FILE_AND_FOLDER)) {
            tv_titlebar_right.setText("选择");
        }
        tv_titlebar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFolder parentFolder = new MyFolder(new File(tv_main_curr_path.getText().toString().trim()).getParentFile());
                MyFolder myFolder = initFolder(parentFolder);
                if (myFolder != null) {
                    List<F> allChild = myFolder.getAllChild();
                    mAdapter.clear();
                    if (allChild != null && allChild.size() > 0) {
                        mAdapter.addItems(allChild);
                    }
                    tv_main_curr_path.setText(parentFolder.getPath());
                } else {
                    ToastUtils.showShort(FileExplorerActivity.this, "不能再返回上一级了");
                }
            }
        });
        tv_titlebar_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_PATH, tv_main_curr_path.getText().toString().trim());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private MyFolder initFolder(MyFolder rootFolder) {
        if (rootFolder != null) {
            File file = rootFolder.getFile();
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    F fileable;
                    if (f.isDirectory()) {
                        fileable = new MyFolder(f);
                    } else {
                        fileable = new MyFile(f);
                    }
                    rootFolder.addChild(fileable);
                }
                rootFolder.sort();
            } else {
                return null;
            }
        }
        return rootFolder;
    }
}
