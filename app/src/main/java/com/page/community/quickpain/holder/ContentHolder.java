package com.page.community.quickpain.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.framework.rvadapter.holder.BaseViewHolder;
import com.framework.utils.DateFormatUtils;
import com.framework.utils.viewutils.ViewUtils;
import com.page.community.quickpain.activity.QuickPaiNActivity;
import com.page.community.quickpain.model.ScommentsReault.Data.Datas;
import com.page.uc.UCUtils;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/8/11.
 */

public class ContentHolder extends BaseViewHolder<Datas> {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_del)
    TextView tvDel;
    private int position;
    private Datas data;

    public ContentHolder(Context context, View itemView) {
        super(context, itemView);
//        R.layout.pub_activity_quickpain_item_layout;
        ButterKnife.bind(this, itemView);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, Datas data, int position) {
        this.position = position;
        this.data = data;
        ViewUtils.setOrGone(tvDel, data != null && TextUtils.equals(data.customerid, UCUtils.getInstance().getUserInfo().userId));
        if (data == null) return;
        tvName.setText(data.nickname);
        tvContent.setText(data.content);
        tvTime.setText(DateFormatUtils.format(data.createtime, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));

    }

    @OnClick(R.id.tv_del)
    public void onViewClicked() {
        ((QuickPaiNActivity)mContext).deletEvaluate(data.id,position);
    }
}
