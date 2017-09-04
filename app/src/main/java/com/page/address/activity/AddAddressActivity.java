package com.page.address.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.BusinessUtils;
import com.qfant.wuye.R;
import com.page.address.Address;

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
    private Address address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_addaddress_layout);
        ButterKnife.bind(this);
        isEidt = true;
        address = (Address)myBundle.getSerializable("address");
        isEidt = address != null;
        String title = isEidt ? "编辑收货地址" : "添加收货地址";
        setTitleBar(title, true, "保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        setData();
    }

    private void setData() {
        if (address != null) {
            editName.setText(address.name);
            editPhone.setText(address.tel);
            editAddress2.setText(address.detail);
            cbSelect.setEnabled(address.isdefault == 1);
        }
    }

    private void saveData() {
        String name =editName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            showToast("请输入姓名");
            return;
        }
        String tel = editPhone.getText().toString();
        if (!BusinessUtils.checkPhoneNumber(tel)) {
            showToast("输入手机号有误");
            return;
        }
        String addres1 = tvAddress.getText().toString();
        String addres2 = tvAddress1.getText().toString();
        String address3 = editAddress2.getText().toString();
        String detail = address3;
        if (TextUtils.isEmpty(detail)) {
            showToast("请输入地址");
        }
        AddressParam addressParam = new AddressParam();
        addressParam.name = name;
        addressParam.tel = tel;
        addressParam.detail = detail;
        addressParam.isdefault = cbSelect.isChecked() ? 1 : 0;
        addressParam.id = this.address.id;
        if (isEidt){
            Request.startRequest(addressParam , ServiceMap.updateAddress ,mHandler, Request.RequestFeature.BLOCK);
        }else {
            Request.startRequest(addressParam , ServiceMap.submitAddress ,mHandler, Request.RequestFeature.BLOCK);
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.submitAddress) {
            if (param.result.bstatus.code == 0) {
                showToast(param.result.bstatus.des);
                finish();
            }
        }else if (param.key == ServiceMap.updateAddress) {
            if (param.result.bstatus.code == 0) {
                showToast(param.result.bstatus.des);
                finish();
            }
        }
        return super.onMsgSearchComplete(param);
    }
}
