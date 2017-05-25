package com.chatui.model;

/**
 * Created by admin on 2017/5/24.
 */

public class User {

    private String nickName; // 昵称

    private String avatar; // 头像

    public User(String nickName, String avatar) {
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
