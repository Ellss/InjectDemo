package com.wudh.study.injectdemo.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wudh.study.injectmanager.InjectManager;

/**
 * Created by wudh on 2019/3/21.
 **/
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
    }
}
