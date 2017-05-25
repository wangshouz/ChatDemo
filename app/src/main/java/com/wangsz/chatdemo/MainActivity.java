package com.wangsz.chatdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatui.activity.ChatActivity;
import com.chatui.model.User;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button btnToChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToChat = (Button) findViewById(R.id.btnToChat);
        editText = (EditText) findViewById(R.id.etNickname);

        btnToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = editText.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)){
                    Snackbar.make(btnToChat,"请输入昵称",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                ChatActivity.startChat(new User(nickName,""),MainActivity.this);
            }
        });

    }
}
