package com.framework.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.qfant.wuye.R;

import java.util.ArrayList;
import java.util.List;

public class DatePickerDialog {

    private Context mContext;
    private AlertDialog.Builder mAlertDialog;
    private DatePickerDialogInterface datePickerDialogInterface;
    private DatePicker mDatePicker;
    private int mYear, mDay, mMonth;

    public DatePickerDialog(Context context) {
        super();
        mContext = context;
        datePickerDialogInterface = (DatePickerDialogInterface) context;
    }

    /**
     * 初始化DatePicker
     *
     * @return
     */
    private View initDatePicker() {
        View inflate = LayoutInflater.from(mContext).inflate(
                R.layout.pub_dialog_datepicker_layout, null);
        mDatePicker = (DatePicker) inflate
                .findViewById(R.id.datePicker);
        return inflate;
    }

    /**
     * 创建dialog
     *
     * @param view
     */
    private void initDialog(View view) {
        mAlertDialog.setPositiveButton("确定",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        getDatePickerValue();
                        datePickerDialogInterface.positiveListener();

                    }
                });
        mAlertDialog.setNegativeButton("取消",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        datePickerDialogInterface.negativeListener();
                        dialog.dismiss();
                    }
                });
        mAlertDialog.setView(view);
    }

    /**
     * 显示日期选择器
     */
    public void showDatePickerDialog() {
        View view = initDatePicker();
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();
    }

    public int getYear() {
        return mYear;
    }

    public int getDay() {
        return mDay;
    }

    public int getMonth() {
        //返回的时间是0-11
        return mMonth + 1;
    }

    /**
     * 获取日期选择的值
     */
    private void getDatePickerValue() {
        mYear = mDatePicker.getYear();
        mMonth = mDatePicker.getMonth();
        mDay = mDatePicker.getDayOfMonth();
    }

    public interface DatePickerDialogInterface {
        public void positiveListener();

        public void negativeListener();
    }

}
