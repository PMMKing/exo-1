package com.page.uc;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.haolb.client.R;
import com.framework.activity.BaseActivity;
import com.framework.adapter.utils.QSimpleAdapter;
import com.page.uc.bean.Address;

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
        AddressAdapter addressAdapter = new AddressAdapter(getContext());
        listview.setAdapter(addressAdapter);
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(tvAdd)) {
            showToast("tianja");
        }
    }

    public static class AddressAdapter extends QSimpleAdapter<Address> {


        public AddressAdapter(Context context) {
            super(context);
        }

        @Override
        protected View newView(Context context, ViewGroup parent) {
            return null;
        }

        @Override
        protected void bindView(View view, Context context, Address item, int position) {

        }
    }
}
