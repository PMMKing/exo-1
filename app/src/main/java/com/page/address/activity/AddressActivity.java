package com.page.address.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.IBaseActFrag;
import com.qfant.wuye.R;
import com.page.address.Address;
import com.page.address.AddressResult;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class AddressActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private AddressAdapter addressAdapter;
    private boolean isSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_address_layout);
        ButterKnife.bind(this);
        setTitleBar("收货地址", true);
        isSelect = myBundle.getBoolean("isSelect", true);
        addressAdapter = new AddressAdapter(getContext());
        listview.setAdapter(addressAdapter);
        tvAdd.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (isSelect) {
            Address address = addressAdapter.getItem(i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("address", address);
            qBackForResult(RESULT_OK, bundle);
        }
        super.onItemClick(adapterView, view, i, l);
    }

    @Override
    protected void onResume() {
        updateAddres();
        super.onResume();
    }

    private void updateAddres() {
        Request.startRequest(new BaseParam(), ServiceMap.getAddresses, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(tvAdd)) {
            qStartActivity(AddAddressActivity.class);
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (super.onMsgSearchComplete(param)) {
            if (ServiceMap.getAddresses == param.key) {
                if (param.result.bstatus.code == 0) {
                    AddressResult addressResult = (AddressResult) param.result;
                    addressAdapter.setData(addressResult.data.addresses);
                }
            }else   if (ServiceMap.setDefaultAddress == param.key) {
                if (param.result.bstatus.code == 0) {
                    updateAddres();
                }
            }else   if (ServiceMap.deleteAddress == param.key) {
                if (param.result.bstatus.code == 0) {
                    updateAddres();
                }
            }
        }
        return super.onMsgSearchComplete(param);
    }
    public void setDefault(Address aDefault) {
        UpdateAddreeParam updateAddreeParam = new UpdateAddreeParam();
        updateAddreeParam.id = aDefault.id;
        Request.startRequest(updateAddreeParam, ServiceMap.setDefaultAddress, mHandler, Request.RequestFeature.BLOCK);
    }
    public class AddressAdapter extends QSimpleAdapter<Address> {

        public AddressAdapter(Context context) {
            super(context);
        }

        @Override
        protected View newView(Context context, ViewGroup parent) {
            View view = inflate(R.layout.pub_address_layout_item, null, false);
            return view;
        }

        @Override
        protected void bindView(View view, final Context context, final Address item, int position) {
            TextView textName = (TextView) view.findViewById(R.id.text_name);
            LinearLayout llBottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
            TextView textDefault = (TextView) view.findViewById(R.id.text_default);
            TextView textDetail = (TextView) view.findViewById(R.id.text_detail);
            TextView textEdit = (TextView) view.findViewById(R.id.text_edit);
            TextView textDelete = (TextView) view.findViewById(R.id.text_delete);
            textName.setText(item.name);
            textDetail.setText(item.detail);
//            if (isSelect) {
//                llBottom.setVisibility(View.GONE);
//            }else {
                llBottom.setVisibility(View.VISIBLE);
//            }
            if (item.isdefault == 1) {
                textDefault.setText("默认地址");
                textDefault.setTextColor(getContext().getResources().getColor(R.color.pub_color_blue));
            }else {
                textDefault.setText("设为默认");
                textDefault.setTextColor(getContext().getResources().getColor(R.color.pub_color_gray_666));
            }
            textDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UpdateAddreeParam p = new UpdateAddreeParam();
                    p.id = item.id;
                    Request.startRequest(p, ServiceMap.deleteAddress, mHandler, Request.RequestFeature.BLOCK);
                }
            });
            textEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", item);
                    ((IBaseActFrag) context).qStartActivity(AddAddressActivity.class, bundle);
                }
            });
            textDefault.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    setDefault(item);
                }
            });
        }


    }

    public static class UpdateAddreeParam extends BaseParam{
        public String id ;
    }
}
