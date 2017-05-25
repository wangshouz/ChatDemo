package com.chatui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.chatui.R;
import com.chatui.actions.BaseAction;
import com.chatui.actions.LocationAction;
import com.chatui.actions.PickImageAction;
import com.chatui.actions.VideoAction;
import com.chatui.constant.Extra;
import com.chatui.model.ChatMessage;
import com.chatui.model.User;
import com.chatui.module.Container;
import com.chatui.module.ModuleProxy;
import com.chatui.module.input.InputPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/24.
 * ChatActivity---绑定ChatFragment
 */

public class ChatActivity extends ChatBaseActivity implements ModuleProxy{

    private static String TAG = "ChatActivity";

    private Toolbar mToolbar; // title

    private User mUser; //聊天对象

    @Override
    public int setLayoutId() {
        return R.layout.message_activity;
    }

    @Override
    public void parseIntent() {
        Intent intent = getIntent();
        if (!intent.hasExtra(Extra.USER_NICKNAME)){
            Snackbar.make(mToolbar,"用户昵称不能为空",Snackbar.LENGTH_SHORT).show();
            finish();
            return;
        }

        String nickName = intent.getStringExtra(Extra.USER_NICKNAME);
        mUser = new User(nickName,"");

        Container container = new Container(this, mUser, this);

        if (mInputPanel == null) {
            mInputPanel = new InputPanel(container, this.getWindow().getDecorView(), getActionList());
        } else {
            mInputPanel.reload(container);
        }
    }

    // 操作面板集合
    protected List<BaseAction> getActionList() {
        List<BaseAction> actions = new ArrayList<>();
        actions.add(new PickImageAction());
        actions.add(new VideoAction());
        actions.add(new LocationAction());

        return actions;
    }

    @Override
    public void initView() {
        setToolBar();
    }

    private void setToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mUser.getNickName());
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
    }

    /**
     * 开启ChatActivity
     * @param user
     * @param context
     */
    public static void startChat(User user, Context context){
        Intent intent = new Intent(context,ChatActivity.class);
        intent.putExtra(Extra.USER_NICKNAME,user.getNickName());
        context.startActivity(intent);
    }

    /**
     * ***************************** ModuleProxy 底部编辑状态变化 *******************************
     */

    @Override
    public boolean sendMessage(ChatMessage msg) {
        Toast.makeText(getApplicationContext(),"发送msg",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onInputPanelExpand() {

    }

    @Override
    public void onStartAudioRecord() {
        // TODO: 2017/5/25  录音当前正在播放语音，需要停止播放
    }

}

