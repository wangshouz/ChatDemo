package com.chatui.actions;


import android.widget.Toast;

import com.chatui.R;

/**
 * Created by hzxuwen on 2015/6/12.
 */
public class LocationAction extends BaseAction {

    public LocationAction() {
        super(R.drawable.message_plus_location_selector, R.string.input_panel_location);
    }

    @Override
    public void onClick() {
        Toast.makeText(getActivity().getApplicationContext(),R.string.input_panel_location,Toast.LENGTH_SHORT).show();
    }
}
