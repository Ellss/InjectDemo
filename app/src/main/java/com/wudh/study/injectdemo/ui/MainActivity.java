package com.wudh.study.injectdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wudh.study.injectdemo.R;
import com.wudh.study.injectdemo.base.BaseActivity;
import com.wudh.study.injectmanager.annotation.BindView;
import com.wudh.study.injectmanager.annotation.OnClick;
import com.wudh.study.injectmanager.annotation.SetContentView;

@SetContentView(R.layout.activity_main)

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_b1)
    private Button btn;

    @BindView(R.id.tv_t1)
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @OnClick({R.id.btn_b1,R.id.tv_t1})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_b1:
                Toast.makeText(MainActivity.this,"点击了按钮",Toast.LENGTH_SHORT).show();
                break;

            case R.id.tv_t1:
                Toast.makeText(MainActivity.this,"点击了tv",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
