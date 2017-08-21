package com.page.address.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.haolb.client.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by shucheng.qu on 2017/8/17.
 */

public class AddAddressActivity extends BaseActivity {

    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address1)
    TextView tvAddress1;
    @BindView(R.id.edit_address2)
    EditText editAddress2;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    private boolean isEidt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_addaddress_layout);
        ButterKnife.bind(this);
        isEidt = true;
        String title = isEidt ? "编辑收货地址" : "添加收货地址";

        setTitleBar(title, true, "保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
