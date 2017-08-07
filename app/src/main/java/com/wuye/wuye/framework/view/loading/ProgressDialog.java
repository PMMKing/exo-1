package com.wuye.wuye.framework.view.loading;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuye.wuye.R;
import com.wuye.wuye.base.BaseActivity;
import com.wuye.wuye.utils.viewutils.ViewUtils;


/**
 * Created by shucheng.qu on 2017/5/31.
 */

public class ProgressDialog extends Dialog implements LoadingProgressView.AnimationListener {

    private final Context mContext;
    private LoadingProgressView ivLoading;
    private TextView tvHint;

    public ProgressDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setWindowAnimations(R.style.atom_PopupAnimation);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0;
        getWindow().setAttributes(attributes);
        setContentView(R.layout.pub_dialog_progress_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ivLoading = (LoadingProgressView) findViewById(R.id.loading);
        ivLoading.setAnimationListener(this);
        tvHint = (TextView) findViewById(R.id.hint);
    }

    @Override
    public void show() {
        if (!isShowing() && mContext instanceof BaseActivity && !((BaseActivity) mContext).isFinishing()) {
            super.show();
            ivLoading.start();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing() && mContext instanceof BaseActivity && !((BaseActivity) mContext).isFinishing()) {
            super.dismiss();
        }
    }

    public void setLoadingHint(String hint) {
        ViewUtils.setOrGone(tvHint, hint);
    }

    public void onStart(String hint) {
        ivLoading.start();
        setLoadingHint(hint);
    }

    public void onSuccess() {
        ivLoading.finishSuccess();
    }

    public void onSuccess(String hint) {
        ivLoading.finishSuccess();
        setLoadingHint(hint);
    }

    public void onFail() {
        ivLoading.finishFail();
    }

    public void onFail(String hint) {
        ivLoading.finishFail();
        setLoadingHint(hint);
    }




    @Override
    public void animationEnd() {
        this.dismiss();
    }
}
