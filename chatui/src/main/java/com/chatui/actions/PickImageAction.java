package com.chatui.actions;

import android.widget.Toast;

import com.chatui.R;

/**
 * Created by zhoujianghua on 2015/7/31.
 */
public class PickImageAction extends BaseAction {

    public PickImageAction() {
        super(R.drawable.message_plus_photo_selector, R.string.input_panel_photo);
    }

    @Override
    public void onClick() {
        Toast.makeText(getActivity().getApplicationContext(),R.string.input_panel_photo,Toast.LENGTH_SHORT).show();
    }
}
