package com.chatui.module;

import android.app.Activity;

import com.chatui.model.User;

/**
 */
public class Container {
    public final Activity activity;
    public final User user;
    public final ModuleProxy proxy;

    public Container(Activity activity, User user, ModuleProxy proxy) {
        this.activity = activity;
        this.user = user;
        this.proxy = proxy;
    }
}
