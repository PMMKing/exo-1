package com.wuye.wuye.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wuye.wuye.R;
import com.wuye.wuye.framework.view.TitleBarCenterItem;
import com.wuye.wuye.framework.view.TitleBarItem;
import com.wuye.wuye.framework.view.TitleBarNew;
import com.wuye.wuye.framework.view.loading.ProgressDialog;
import com.wuye.wuye.net.link.netlistener.NetWorkListener;
import com.wuye.wuye.net.link.param.NetworkParam;
import com.wuye.wuye.utils.ToastUtils;


/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class BaseActivity extends AppCompatActivity implements NetWorkListener {

    protected Handler mHandler;
    protected Bundle myBundle;
    public CloseActivityAnim animType = CloseActivityAnim.FLIP;
    public TitleBarNew mTitleBar;
    public ProgressDialog dialog = null;
//    private JProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler = new Handler(Looper.getMainLooper());
        myBundle = savedInstanceState == null ? getIntent().getExtras() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks((Runnable) null);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(animType.getEnterAnim(), animType.getExitAnim());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (myBundle != null) {
            outState.putAll(myBundle);
        }
        super.onSaveInstanceState(outState);

    }


    public void onProgressShow() {
        onProgressShow("加载中……");
    }

    public void onProgressShow(String hint) {
        if (dialog == null) {
            dialog = new ProgressDialog(getContext(), R.style.loading_dialog);
        }
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setLoadingHint(hint);
    }

    public void onProgressSuccess(String hint) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.onSuccess(hint);

    }

    public void onProgressFail(String hint) {
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.onFail(hint);
    }

    public void onDismiss(){
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        dialog.dismiss();
    }

    public void setTitleBar(boolean hasBackBtn, TitleBarItem... barItems) {
        this.setTitleBar("", hasBackBtn, barItems);
    }

    public void setTitleBar(String title, boolean hasBackBtn, TitleBarItem... barItems) {
        this.setTitleBar("", title, hasBackBtn, barItems);
    }


    public void setTitleBar(String backText, String title, boolean hasBackBtn, TitleBarItem... barItems) {
        this.setTitleBar(backText, (String) title, hasBackBtn, (View.OnClickListener) null, barItems);
    }

    public void setTitleBar(String backText, String title, boolean hasBackBtn, View.OnClickListener listener, TitleBarItem... rightBarItems) {
        TitleBarCenterItem barCenterItem = new TitleBarCenterItem(this);
        barCenterItem.setContent(title);
        barCenterItem.requestRelayout();
        this.setTitleBar(backText, barCenterItem, (TitleBarItem[]) null, hasBackBtn, listener, rightBarItems);
    }

    public void setTitleBar(String backText, TitleBarCenterItem barCenterItem, TitleBarItem[] leftBarItems, boolean hasBackBtn, View.OnClickListener listener, TitleBarItem... rightBarItems) {
        this.mTitleBar = (TitleBarNew) this.findViewById(R.id.title_bar);
        if (this.mTitleBar != null) {
            this.mTitleBar.setTitleBar(backText, hasBackBtn, leftBarItems, barCenterItem, rightBarItems);
            if (listener != null) {
                this.mTitleBar.setBackButtonClickListener(listener);
            }
            this.mTitleBar.setVisibility(View.VISIBLE);
        }
    }

    public void qStartActivity(Intent intent) {
        startActivity(intent);
    }

    public void qStartActivity(Class<? extends Activity> cls) {
        qStartActivity(cls, null);
    }

    /* 打开新的Activity */
    public void qStartActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, cls);
        qStartActivity(intent);

    }

    public void qStartShareActivity(String title, String shareContent) {
//		Intent intent = new Intent(Intent.ACTION_SEND);
//		intent.setType("text/plain");
//		if (title != null) {
//			intent.putExtra(Intent.EXTRA_SUBJECT, title);
//		}
//		intent.putExtra(Intent.EXTRA_TEXT, shareContent);
//		startActivity(Intent.createChooser(intent,
//				getString(R.string.share_message)));
    }

    public void qStartImageShare(String shareContent, Uri shareUri) {
//		Intent intent = new Intent(Intent.ACTION_SEND);
//		intent.setType("image/*");
//		if (shareUri == null) {
//			qStartShareActivity(null, shareContent);
//			return;
//		}
//		intent.putExtra(Intent.EXTRA_STREAM, shareUri);
//		intent.putExtra(Intent.EXTRA_TEXT, shareContent);
//		intent.putExtra("sms_body", shareContent);
//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		startActivity(Intent.createChooser(intent,
//				getString(R.string.share_message)));
    }

    /* 打开新的Activity for result */
    public void qStartActivityForResult(Class<? extends Activity> cls,
                                        Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }

    /* 带结果返回上一个activity， 配合qStartActivityForResult使用 */
    public void qBackForResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(resultCode, intent);
        finish();
    }

    /* 回到之前的Activity */
    public void qBackToActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
    }


    public void openSoftinput(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 20);
    }

    /**
     * 关掉软键盘
     */
    public void hideSoftInput() {
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    public void showToast(String message) {
        ToastUtils.toastSth(getApplicationContext(), message);
    }

    public void qShowAlertMessage(String title, String message) {
        try {
            new AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setNegativeButton("确定", null).show();
        } catch (IllegalStateException e) {
        }
    }

    public void qShowAlertMessage(String message) {
        qShowAlertMessage("温馨提示", message);
    }

    public void qShowAlertMessage(int titleResId, String message) {
        qShowAlertMessage(getString(titleResId), message);
    }

    public void qShowAlertMessage(int titleResId, int msgResId) {
        qShowAlertMessage(getString(titleResId), getString(msgResId));
    }

    /**
     * 输入错误提示
     *
     * @param editText
     * @param message
     */
    public void showErrorTip(final EditText editText, String message) {

        new AlertDialog.Builder(this)
                .setMessage(message).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (editText != null) {
                    editText.requestFocus(View.FOCUS_RIGHT);
                    String txt = editText.getText().toString().trim();
                    editText.setText(txt);
                    editText.setSelection(txt.length());
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(editText, 0);
                }
            }
        }).show();
    }

    public BaseActivity getContext() {
        return this;
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onNetStart(NetworkParam param) {

    }

    @Override
    public void onNetEnd(NetworkParam param) {

    }

    @Override
    public void onNetError(NetworkParam param) {
        onProgressFail("网络异常，请检查网络设置，或稍后重试~~");
    }

    @Override
    public void onNetFinish(NetworkParam param) {

    }

    @Override
    public void onMsgSearchComplete(NetworkParam param) {

    }

    @Override
    public void onNetCancel(NetworkParam param) {

    }

    @Override
    public void onCacheHit(NetworkParam param) {

    }

    public enum CloseActivityAnim {
        DOWN(0, R.anim.down),
        FADE(0, R.anim.fade_out),
        FLIP(R.anim.back_left_in_show, R.anim.back_right_out_dismiss),
        CUSTOM(0, 0);

        private int enterAnim, exitAnim;

        CloseActivityAnim(int enterAnim, int exitAnim) {
            this.enterAnim = enterAnim;
            this.exitAnim = exitAnim;
        }

        public int getEnterAnim() {
            return enterAnim;
        }

        public CloseActivityAnim setEnterAnim(int enterAnim) {
            this.enterAnim = enterAnim;
            return this;
        }

        public int getExitAnim() {
            return exitAnim;
        }

        public CloseActivityAnim setExitAnim(int exitAnim) {
            this.exitAnim = exitAnim;
            return this;
        }
    }

}
