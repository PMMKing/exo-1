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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatePickerDialog {

    private Context mContext;
    private AlertDialog.Builder mAlertDialog;
    private DatePickerDialogInterface datePickerDialogInterface;
    private DatePicker mDatePicker;

    public DatePickerDialog(Context context) {
        super();
        mContext = context;
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
                        if (datePickerDialogInterface != null) {
                            datePickerDialogInterface.sure(mDatePicker.getYear(), mDatePicker.getMonth() + 1, mDatePicker.getDayOfMonth());
                        }

                    }
                });
        mAlertDialog.setNegativeButton("取消",
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (datePickerDialogInterface != null) {
                            datePickerDialogInterface.cancle();
                        }
                        dialog.dismiss();
                    }
                });
        mAlertDialog.setView(view);
    }

    /**
     * 显示日期选择器
     */
    public void showDatePickerDialog(DatePickerDialogInterface datePickerDialogInterface) {
        showDatePickerDialog(0, datePickerDialogInterface);
    }

    public void showDatePickerDialog(long timeInMillis, DatePickerDialogInterface datePickerDialogInterface) {
        this.datePickerDialogInterface = datePickerDialogInterface;
        Calendar calendar = Calendar.getInstance();
        View view = initDatePicker();
        mDatePicker.setMinDate(timeInMillis > 0 ? timeInMillis : calendar.getTimeInMillis());
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();
    }


    /**
     * 显示日期选择器
     */
    public void showDatePickerDialogNo(DatePickerDialogInterface datePickerDialogInterface) {
        this.datePickerDialogInterface = datePickerDialogInterface;
        View view = initDatePicker();
        Calendar calendar = Calendar.getInstance();
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();
    }


    public interface DatePickerDialogInterface {
        void sure(int year, int month, int day);

        void cancle();
    }

}
