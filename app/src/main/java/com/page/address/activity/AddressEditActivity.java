package com.page.address.activity;

import android.os.Bundle;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;
import com.page.address.Address;

/**
 * Created by chenxi.cui on 2017/8/19.
 */

public class AddressEditActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_address_edit_layout);
        Address address = (Address) myBundle.getSerializable("address");

    }
}
