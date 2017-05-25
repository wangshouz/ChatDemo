package com.chatui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chatui.module.input.InputPanel;

/**
 * Created by admin on 2017/5/24.
 * ChatActivity---绑定ChatFragment
 */

public abstract class ChatBaseActivity extends AppCompatActivity {

    private static String TAG = "ChatBaseActivity";

    public abstract int setLayoutId();
    /**解析Intent参数，如会话id，会话双方信息（头像、昵称等）*/
    public abstract void parseIntent();
    /**初始化控件*/
    public abstract void initView();

    protected InputPanel mInputPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        parseIntent();
        initView();
    }


    /**
     * ***************************** life cycle *******************************
     */

    @Override
    public void onPause() {
        super.onPause();
        if (mInputPanel != null){
            mInputPanel.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mInputPanel != null){
            mInputPanel.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected <T extends View> T findView(int resId) {
        return (T) (findViewById(resId));
    }

}
