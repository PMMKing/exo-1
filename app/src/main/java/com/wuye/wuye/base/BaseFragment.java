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
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.wuye.wuye.R;
import com.wuye.wuye.framework.view.TitleBarCenterItem;
import com.wuye.wuye.framework.view.TitleBarItem;
import com.wuye.wuye.framework.view.TitleBarNew;
import com.wuye.wuye.net.link.netlistener.NetWorkListener;
import com.wuye.wuye.net.link.param.NetworkParam;
import com.wuye.wuye.utils.BusinessUtils;

/**
 * Created by shucheng.qu on 2017/5/27.
 */

public class BaseFragment extends Fragment implements NetWorkListener, FragmentOnBackListener {


    protected Handler mHandler;
    protected Bundle myBundle;
    public TitleBarNew mTitleBar;
    private boolean mIsFirstResume = true;
    //    private JProgressDialog dialog;
    private Activity mActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler(Looper.getMainLooper());
        myBundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (myBundle == null) {
            myBundle = new Bundle();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (myBundle != null) {
            outState.putAll(myBundle);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            this.onHide();
        } else {
            this.onShow();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.mIsFirstResume) {
            mIsFirstResume = false;
            this.onFirstResume();
        } else {
            this.onRegularResume();
        }
    }

    protected void onRegularResume() {
    }

    protected void onFirstResume() {
    }

    protected void onHide() {
    }

    protected void onShow() {
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
        intent.setClass(getActivity(), cls);
        qStartActivity(intent);
    }

    public void qStartShareActivity(String title, String shareContent) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (title != null) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        qStartActivity(Intent.createChooser(intent, ""));
    }

    public void qStartImageShare(String shareContent, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        if (uri == null) {
            qStartShareActivity(null, shareContent);
            return;
        }
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.putExtra(Intent.EXTRA_TEXT, shareContent);
        intent.putExtra("sms_body", shareContent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        qStartActivity(Intent.createChooser(intent, ""));
    }

    /* 打开新的Activity for result */
    public void qStartActivityForResult(Class<? extends Activity> cls,
                                        Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /* 带结果返回上一个activity， 配合qStartActivityForResult使用 */
    public void qBackForResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        getActivity().setResult(resultCode, intent);
        getActivity().finish();
    }

    /* 回到之前的Activity */
    public void qBackToActivity(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setClass(getActivity(), cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
    }

    /* 根据url跳转Activity */
    public void qStartActivity(String url, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        qStartActivity(intent);
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
        TitleBarCenterItem barCenterItem = new TitleBarCenterItem(getContext());
        barCenterItem.setContent(title);
        barCenterItem.requestRelayout();
        this.setTitleBar(backText, barCenterItem, (TitleBarItem[]) null, hasBackBtn, listener, rightBarItems);
    }

    public void setTitleBar(String backText, TitleBarCenterItem barCenterItem, TitleBarItem[] leftBarItems, boolean hasBackBtn, View.OnClickListener listener, TitleBarItem... rightBarItems) {
        if (this.mTitleBar == null) {
            this.mTitleBar = (TitleBarNew) getView().findViewById(R.id.title_bar);
        }
        if (this.mTitleBar != null) {
            this.mTitleBar.setTitleBar(backText, hasBackBtn, leftBarItems, barCenterItem, rightBarItems);
            if (listener != null) {
                this.mTitleBar.setBackButtonClickListener(listener);
            }
            this.mTitleBar.setVisibility(View.VISIBLE);
        }
    }

    public void setTitleText(String text) {
        if (getView() != null) {
            this.mTitleBar = (TitleBarNew) getView().findViewById(R.id.title_bar);
            if (this.mTitleBar != null) {
                this.mTitleBar.setTitle(text);
            }
        }
    }


    public void qShowAlertMessage(String message) {
        qShowAlertMessage("温馨提示", message);
    }

    public void qShowAlertMessage(int titleResId, String message) {
        qShowAlertMessage(getString(titleResId), message);
    }

    public void qShowAlertMessage(String title, String message) {

        try {
            new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(message).setNegativeButton("确定", null)
                    .show();
        } catch (Exception e) {
        }
    }

    public void processAgentPhoneCall(String phoneNum) {
        try {
            startActivity(new Intent(Intent.ACTION_CALL,
                    Uri.parse(BusinessUtils.formatPhoneNumber(phoneNum))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public BaseActivity getContext() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity == null) {
            return (BaseActivity) mActivity;
        }
        return activity;
    }

    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mHandler.removeCallbacks((Runnable) null);
    }

//    public void onShowProgress(final NetworkParam networkParam) {
//        if (dialog == null) {
//            dialog = new JProgressDialog(getContext());
//        }
//        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                ChiefGuard.getInstance().cancelTaskByCallback(taskCallback, forceCancelOnDestory());
//            }
//        });
//        dialog.show();
//    }

//    public void onCloseProgress(NetworkParam networkParam) {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//    }

    /**
     * 输入错误提示
     *
     * @param editText
     */
    protected void showErrorTip(final EditText editText, int msgResId) {
        showErrorTip(editText, getString(msgResId));
    }

    /**
     * 输入错误提示
     *
     * @param editText
     * @param message
     */
    public void showErrorTip(final EditText editText, String message) {
        new AlertDialog.Builder(getActivity()).setTitle("温馨提示")
                .setMessage(message).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (editText != null) {
                    editText.requestFocus(View.FOCUS_RIGHT);
                    String txt = editText.getText().toString().trim();
                    editText.setText(txt);
                    editText.setSelection(txt.length());
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                            .getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(editText, 0);
                }
            }
        }).show();
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
        }, 498);
    }

    /**
     * 关掉软键盘
     */
    public void hideSoftInput() {
        try {
            InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getContext().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }


    @Override
    public void onNetStart(NetworkParam param) {

    }

    @Override
    public void onNetEnd(NetworkParam param) {

    }

    @Override
    public void onNetError(NetworkParam param) {

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
}
