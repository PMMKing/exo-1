package com.wuye.wuye.page.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuye.wuye.R;
import com.wuye.wuye.ServiceID;
import com.wuye.wuye.base.BaseFragment;
import com.wuye.wuye.framework.view.TitleBarItem;
import com.wuye.wuye.net.link.Request;
import com.wuye.wuye.net.link.param.NetworkParam;
import com.wuye.wuye.page.home.model.AccountParam;
import com.wuye.wuye.page.home.model.AccountResult;
import com.wuye.wuye.utils.BusinessUtils;
import com.wuye.wuye.utils.UCUtils;
import com.wuye.wuye.utils.framwork.ArrayUtils;
import com.wuye.wuye.utils.storage.Store;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by shucheng.qu on 2017/6/1.
 */

public class AccountFragment extends BaseFragment {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TitleBarItem titleBarItem = new TitleBarItem(getContext());
//        titleBarItem.setTextTypeItem(getResources().getString(R.string.icon_font_setting), 26f);
//        setTitleBar("账户", false, titleBarItem);
//        mTitleBar.setRightOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        requestAccount();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onMsgSearchComplete(NetworkParam param) {
        super.onMsgSearchComplete(param);
        if (param.key == ServiceID.ACCOUNT) {

        }
    }
}
