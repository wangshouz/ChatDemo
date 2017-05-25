package com.chatui.actions;

import android.widget.Toast;

import com.chatui.R;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class VideoAction extends BaseAction {

    public VideoAction() {
        super(R.drawable.message_plus_video_selector, R.string.input_panel_video);

    }

    @Override
    public void onClick() {
        Toast.makeText(getActivity().getApplicationContext(),R.string.input_panel_video,Toast.LENGTH_SHORT).show();
    }

}
