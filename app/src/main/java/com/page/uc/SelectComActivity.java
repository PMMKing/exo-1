package com.page.uc;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.domain.param.BaseParam;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.view.ListDialog;
import com.qfant.wuye.R;
import com.page.uc.bean.BuidingsResult;
import com.page.uc.bean.ComBean;
import com.page.uc.bean.ComParam;
import com.page.uc.bean.DistrictsResult;
import com.page.uc.bean.RoomsResult;
import com.page.uc.bean.UnitsResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenxi.cui on 2017/8/18.
 */

public class SelectComActivity extends BaseActivity {
    @BindView(R.id.text_0)
    TextView text0;
    @BindView(R.id.ll_0)
    LinearLayout ll0;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private ListDialog<ComBean> listDialig;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_select_com_layout);
        ButterKnife.bind(this);
        setTitleBar("选择小区", true);
        listDialig = new ListDialog(getContext(), R.style.list_dialog_style);
        listDialig.create();
        listDialig.setOnCellClick(new ListDialog.OnCellClick() {
            @Override
            public void onCellClick(ComBean baseParam, int pos) {
                int type = listDialig.getType();
                if (type == 0) {
                    _districtItem = baseParam;
                    _buildingItem = null;
                    _unitItem = null;
                    _roomImte = null;
                    text0.setText(_districtItem.name);
                    text1.setText("");
                    text2.setText("");
                    text3.setText("");
                } else if (type == 1) {
                    _buildingItem = baseParam;
                    _unitItem = null;
                    _roomImte = null;
                    text1.setText(_buildingItem.name);
                    text2.setText("");
                    text3.setText("");
                } else if (type == 2) {
                    _unitItem = baseParam;
                    _roomImte = null;
                    text2.setText(_unitItem.name);
                    text3.setText("");
                }else {
                    _roomImte = baseParam;
                    text3.setText(_roomImte.name);
                }
            }
        });
    }


    ComBean _districtItem, _buildingItem, _unitItem,_roomImte;

    @OnClick({R.id.ll_0, R.id.ll_1, R.id.ll_2,R.id.ll_3,R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_0:
                Request.startRequest(new BaseParam(), ServiceMap.getDistricts, mHandler, Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_1:
                if (_districtItem == null) {
                    showToast("先选择小区");
                    return;
                }
                ComParam comParam = new ComParam();
                comParam.pid = _districtItem.id;
                Request.startRequest(comParam, ServiceMap.getBuildings, mHandler, Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_2:
                if (_buildingItem == null) {
                    showToast("先选择楼号");
                    return;
                }

                ComParam comParam1 = new ComParam();
                comParam1.pid = _buildingItem.id;
                Request.startRequest(comParam1, ServiceMap.getUnits, mHandler, Request.RequestFeature.BLOCK);
                break;
            case R.id.ll_3:
                if (_unitItem == null) {
                    showToast("先选择单元");
                    return;
                }
                ComParam comParam2 = new ComParam();
                comParam2.pid = _unitItem.id;
                Request.startRequest(comParam2, ServiceMap.getRooms, mHandler, Request.RequestFeature.BLOCK);
                break;
            case R.id.tv_next:
                if (_buildingItem == null || _districtItem == null || _unitItem == null || _roomImte == null) {
                    showToast("信息不完整");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("key1", _districtItem);
                bundle.putSerializable("key2", _buildingItem);
                bundle.putSerializable("key3", _unitItem);
                bundle.putSerializable("key4", _roomImte);
                qStartActivity(RegisterActivity.class, bundle);
                break;
        }
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key == ServiceMap.getDistricts) {
            if (param.result.bstatus.code == 0) {
                DistrictsResult result = (DistrictsResult) param.result;
                listDialig.setListView(result.data.districts);
                listDialig.setType(0);
                listDialig.show();
            }
        } else if (param.key == ServiceMap.getBuildings) {
            if (param.result.bstatus.code == 0) {
                BuidingsResult result = (BuidingsResult) param.result;
                listDialig.setListView(result.data.datas);
                listDialig.setType(1);
                listDialig.show();
            }
        } else if (param.key == ServiceMap.getUnits) {
            if (param.result.bstatus.code == 0) {
                UnitsResult result = (UnitsResult) param.result;
                listDialig.setListView(result.data.datas);
                listDialig.setType(2);
                listDialig.show();
            }
        }else if (param.key == ServiceMap.getRooms) {
            if (param.result.bstatus.code == 0) {
                RoomsResult result = (RoomsResult) param.result;
                listDialig.setListView(result.data.datas);
                listDialig.setType(3);
                listDialig.show();
            }
        }
        return false;
    }
}
