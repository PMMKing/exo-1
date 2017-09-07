package com.page.store.orderaffirm.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.framework.utils.BusinessUtils;
import com.framework.utils.imageload.ImageLoad;
import com.page.store.orderaffirm.activity.OrderAffirmActivity;
import com.page.store.orderaffirm.model.CommitOrderParam;
import com.qfant.wuye.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by shucheng.qu on 2017/9/7.
 */

public class ProItemView extends LinearLayout {
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_sub)
    TextView tvSub;
    @BindView(R.id.tv_number2)
    TextView tvNumber2;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private CommitOrderParam.Product data;
    private OrderAffirmActivity activity;

    public ProItemView(Context context) {
        this(context, null);
    }

    public ProItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        LinearLayout.inflate(getContext(), R.layout.pub_activity_orderaffirm_item_layout, this);
        ButterKnife.bind(this);
        activity = (OrderAffirmActivity) getContext();
    }

    public void updataView(CommitOrderParam.Product data) {
        this.data = data;
        ImageLoad.loadPlaceholder(getContext(), data.pic, ivImage);
        tvName.setText(data.name);
        tvPrice.setText("¥ " + BusinessUtils.formatDouble2String(data.price));
        tvNumber.setText("x" + data.num);
        tvNumber2.setText(data.num + "");
    }

    @OnClick({R.id.tv_sub, R.id.tv_add})
    public void onViewClicked(View view) {

        int number = Integer.parseInt(tvNumber2.getText().toString().trim());

        switch (view.getId()) {
            case R.id.tv_sub:
                if (number > 1) {
                    tvNumber.setText("x" + --number);
                    tvNumber2.setText(number + "");
                    data.num = number;
                    activity.refreshPrice();
                } else {
                    Toast.makeText(getContext(), "不能再少了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_add:
                if (number > 100) {
                    Toast.makeText(getContext(), "库存不足", Toast.LENGTH_SHORT).show();
                } else {
                    tvNumber.setText("x" + ++number);
                    tvNumber2.setText(number + "");
                    data.num = number;
                    activity.refreshPrice();
                }
                break;
        }
    }
}
