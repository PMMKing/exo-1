package com.page.community.quickpain.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.framework.activity.BaseActivity;
import com.framework.net.NetworkParam;
import com.framework.net.Request;
import com.framework.net.ServiceMap;
import com.framework.rvadapter.adapter.MultiAdapter;
import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.rvadapter.manage.ITypeView;
import com.framework.utils.ArrayUtils;
import com.framework.view.LineDecoration;
import com.framework.view.pull.SwipRefreshLayout;
import com.page.community.quickpain.holder.ContentHolder;
import com.page.community.quickpain.holder.HeaderHolder;
import com.page.community.quickpain.model.DelEvaParam;
import com.page.community.quickpain.model.QpDetailParam;
import com.page.community.quickpain.model.QpDetailResult;
import com.page.community.quickpain.model.ScommentParam;
import com.page.community.quickpain.model.ScommentsParam;
import com.page.community.quickpain.model.ScommentsReault;
import com.page.community.quickpain.model.ScommentsReault.Data.Datas;
import com.qfant.wuye.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/10.
 */

public class QuickPaiNActivity extends BaseActivity implements SwipRefreshLayout.OnRefreshListener {

    public static String ID = "id";

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.spl_refresh)
    SwipRefreshLayout splRefresh;
    @BindView(R.id.et_scomment)
    EditText etScomment;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String id;
    private MultiAdapter adapter;
    private ArrayList<Datas> datases = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_quickpain_layout);
        ButterKnife.bind(this);
        if (myBundle == null) {
            finish();
        }
        setTitleBar("随手拍详情", true);
        datases.add(new Datas());//占位
        id = myBundle.getString(ID);
        setListView();
        startRequestDetail();
        startRequestScomments(1);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        myBundle.putString(ID, id);
    }

    private void startRequestDetail() {
        QpDetailParam param = new QpDetailParam();
        param.id = id;
        Request.startRequest(param, ServiceMap.getSnapshot, mHandler, Request.RequestFeature.BLOCK);
    }

    private void startRequestScomments(int pager) {
        ScommentsParam param = new ScommentsParam();
        param.pageNo = pager;
        param.pageSize = 8;
        param.snapshotid = id;
        Request.startRequest(param, pager, ServiceMap.scomments, mHandler);
    }

    private void startRequestScomment() {
        ScommentParam param = new ScommentParam();
        String content = etScomment.getText().toString();
        param.snapshotid = id;
        param.content = content;
        Request.startRequest(param, ServiceMap.scomment, mHandler, Request.RequestFeature.BLOCK);
    }

    public void deletEvaluate(String id, int position) {
        DelEvaParam param = new DelEvaParam();
        param.id = id;
        Request.startRequest(param, position, ServiceMap.deleteScomment, mHandler, Request.RequestFeature.BLOCK);
    }


    private void setListView() {
        adapter = new MultiAdapter<Datas>(this, datases).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position == 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new HeaderHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_quick_headerview_layout, parent, false));
            }
        }).addTypeView(new ITypeView() {
            @Override
            public boolean isForViewType(Object item, int position) {
                return position > 0;
            }

            @Override
            public BaseViewHolder createViewHolder(Context mContext, ViewGroup parent) {
                return new ContentHolder(mContext, LayoutInflater.from(mContext).inflate(R.layout.pub_activity_quickpain_item_layout, parent, false));
            }
        });
        rvList.addItemDecoration(new LineDecoration(this));
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(adapter);
        splRefresh.setOnRefreshListener(this);
    }

    @Override
    public boolean onMsgSearchComplete(NetworkParam param) {
        if (param.key.equals(ServiceMap.getSnapshot)) {
            QpDetailResult result = (QpDetailResult) param.result;
            if (param.result.bstatus.code == 0) {
                if (result != null && result.data != null) {
                    Datas item = new Datas();
                    item.hearderData = result.data;
                    datases.remove(0);
                    datases.add(0, item);
                    adapter.notifyItemChanged(0);
                }
            }
        } else if (param.key == ServiceMap.scomments) {
            ScommentsReault result = (ScommentsReault) param.result;
            if (result != null && result.data != null && !ArrayUtils.isEmpty(result.data.datas)) {
                if ((int) param.ext == 1) {
                    Datas temp = datases.get(0);
                    datases.clear();
                    datases.add(temp);
                    datases.addAll(1, result.data.datas);
                    adapter.notifyDataSetChanged();

                } else {
                    adapter.addData(result.data.datas);
                }
            } else {
                if ((int) param.ext == 1) {
                    showToast("还没有添加评论~");
                } else {
                    showToast("没有更多了");
                }
            }
            splRefresh.setRefreshing(false);
        } else if (param.key == ServiceMap.scomment) {
            if (param.result.bstatus.code == 0) {
                etScomment.setText("");
                startRequestScomments(1);
            } else {
                showToast(param.result.bstatus.des);
            }
        } else if (param.key == ServiceMap.deleteScomment) {
            if (param.result.bstatus.code == 0) {
                int position = (int) param.ext;
                adapter.removeData(position);
                adapter.notifyDataSetChanged();
            } else {
                showToast(param.result.bstatus.des);
            }
        }
        return false;
    }

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        startRequestScomment();
    }

    @Override
    public void onRefresh(int index) {
        startRequestScomments(1);
    }

    @Override
    public void onLoad(int index) {
        startRequestScomments(++index);
    }
}
