package com.page.uc;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.adapter.utils.QSimpleAdapter;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.utils.IBaseActFrag;
import com.haolb.client.R;
import com.page.uc.bean.Address;
import com.page.uc.bean.AddressResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/13.
 */

public class AddressActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_address_layout);
        ButterKnife.bind(this);
        setTitleBar("收货地址", true, "管理", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("管理");
            }
        });
        addressAdapter = new AddressAdapter(getContext());
        listview.setAdapter(addressAdapter);
        tvAdd.setOnClickListener(this);
        Request.startRequest(new BaseParam(), ServiceMap.getAddresses, mHandler, Request.RequestFeature.BLOCK);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(tvAdd)) {
            showToast("tianja");
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
            }
        }
        return super.onMsgSearchComplete(param);
    }

    public static class AddressAdapter extends QSimpleAdapter<Address> {


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
            TextView textDefault = (TextView) view.findViewById(R.id.text_default);
            TextView textDetail = (TextView) view.findViewById(R.id.text_detail);
            TextView textEdit = (TextView) view.findViewById(R.id.text_edit);
            TextView textDelete = (TextView) view.findViewById(R.id.text_delete);
            textName.setText(item.name);
            textDetail.setText(item.detail);
            if (item.isdefault == 1) {
                textDefault.setTextColor(getContext().getResources().getColor(R.color.pub_color_blue));
            }else {
                textDefault.setTextColor(getContext().getResources().getColor(R.color.pub_color_gray_666));
            }
            textDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            textEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("address", item);
                    ((IBaseActFrag) context).qStartActivity(AddressEditActivity.class, bundle);
                }
            });
            textDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.isdefault = item.isdefault == 1 ? 0 : 1;
                    notifyDataSetChanged();
   //                 Request.startRequest();
                }
            });
        }

    }
}
