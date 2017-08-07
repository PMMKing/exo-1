package com.wuye.wuye.framework.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wuye.wuye.R;
import com.wuye.wuye.utils.graphics.Dimen;

import java.util.Random;


/**
 * Created by jun.xu flight_on 2014/4/25.
 */
public class NumberKeyboardView extends Dialog implements View.OnClickListener, View.OnFocusChangeListener {

    public static final int MODE_NORMAL = 0;
    public static final int MODE_RANDOM = 1;
    public static NumberKeyboardView INSTANCE_SHOW;
    private final View view;

    private Context mContext;
    private int[] mNumberBase;
    private int mMode = MODE_NORMAL;
    private boolean mDisplayX;
    private EditText editText;
    private LinearLayout contentView;

    public NumberKeyboardView(Context context) {
        this(context, null);
    }

    public NumberKeyboardView(Context context, View view) {
        super(context, R.style.atom_dialog_fullscreen);
        this.mContext = context;
        this.view = view;
    }

    public void init(int mode, boolean displayX, EditText editText) {
        this.mMode = mode;
        this.mDisplayX = displayX;
        this.editText = editText;
        contentView = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        contentView.setLayoutParams(layoutParams);
        contentView.setOrientation(LinearLayout.VERTICAL);
        contentView.setOnFocusChangeListener(this);
        setContentView(contentView);
        generateOk();
        generateNumberKeyboard();
        initWindow();
        setCanceledOnTouchOutside(true);
    }

    private void generateOk() {
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Dimen.dpToPx(40));
        layout.setLayoutParams(layoutParams);
        layout.setBackgroundColor(Color.parseColor("#f2f2f2"));
        layout.setGravity(Gravity.RIGHT);
        TextView textView = new TextView(getContext());
        textView.setText("完成");
        textView.setTextColor(Color.parseColor("#ff333333"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (view != null) {
                    if (view instanceof EditText) {
                        EditText et = (EditText) view;
                        et.setFocusable(false);
                        et.setFocusableInTouchMode(false);
                    }
                }
            }
        });
        textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, Dimen.dpToPx(14), 0);
        layout.addView(textView, layoutParams);
        contentView.addView(layout);
    }

    private void initWindow() {
        Window localWindow = getWindow();
        WindowManager.LayoutParams lp = localWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        localWindow.setAttributes(lp);
        localWindow.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 根据模式获取键盘的数字数据
     */
    private void generateNumberBase() {
        int NUMBER_COUNT = 10;
        int[] data = new int[NUMBER_COUNT];
        data[0] = 1;
        data[1] = 2;
        data[2] = 3;
        data[3] = 4;
        data[4] = 5;
        data[5] = 6;
        data[6] = 7;
        data[7] = 8;
        data[8] = 9;
        data[9] = 0;
        if (mMode == MODE_RANDOM) {
            Random localRandom = new Random();
            for (int j = 0; j < NUMBER_COUNT; j++) {
                int k = Math.abs(localRandom.nextInt()) % NUMBER_COUNT;
                int i = data[k];
                data[k] = data[0];
                data[0] = i;
            }
        }
        mNumberBase = data;
    }

    private void generateNumberKeyboard() {
        generateNumberBase();
        for (int k = 0; k < 4; k++) {
            LinearLayout localLinearLayout = new LinearLayout(this.getContext());
            localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            localLinearLayout.setBackgroundColor(Color.parseColor("#8B8B8B"));
            for (int n = 0; n < 3; n++) {
                TextView keyButton = generateKeyButton();
                keyButton.setOnClickListener(this);
                if (k == 3) {
                    switch (n) {
                        case 0:
                            if (mDisplayX) {
                                keyButton.setText(".");
                            } else {
                                keyButton.setEnabled(false);
                            }
                            break;
                        case 1:
                            keyButton.setText(String.valueOf(this.mNumberBase[mNumberBase.length - 1]));
                            break;
                        case 2:
                            ImageView deleteButton = generateKeyDeleteButton();
                            deleteButton.setOnClickListener(this);
                            localLinearLayout.addView(deleteButton);
                            continue;
                    }
                } else {
                    keyButton.setText(String.valueOf(this.mNumberBase[(k * 3 + n)]));
                }
                localLinearLayout.addView(keyButton);
            }
            this.contentView.addView(localLinearLayout);
        }
    }

    private TextView generateKeyButton() {
        TextView keyboardBtn = new TextView(this.getContext());
        int internalWidth = (int) Dimen.dpToPx(0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(internalWidth, internalWidth, internalWidth, internalWidth);
        keyboardBtn.setLayoutParams(layoutParams);
        int padding = Dimen.dpToPx(10);
        keyboardBtn.setPadding(padding, padding, padding, padding);
        keyboardBtn.setGravity(Gravity.CENTER);
        keyboardBtn.setBackgroundResource(R.drawable.pub_keyboard_selector);
        keyboardBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.0f);
        keyboardBtn.setTextColor(Color.BLACK);
        return keyboardBtn;
    }

    private ImageView generateKeyDeleteButton() {
        ImageView keyboardBtn = new ImageView(this.getContext());
        int internalWidth = (int) Dimen.dpToPx(0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(internalWidth, internalWidth, internalWidth, internalWidth);
        keyboardBtn.setLayoutParams(layoutParams);
        //keyboardBtn.setBackgroundColor(Color.WHITE);
        keyboardBtn.setBackgroundResource(R.drawable.pub_keyboard_selector);
        keyboardBtn.setImageResource(R.drawable.pub_btn_input_delete);
        keyboardBtn.setScaleType(ImageView.ScaleType.CENTER);
        keyboardBtn.setTag("del");
        return keyboardBtn;
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        Editable editable = editText.getText();
        int oldLength = editable.length();
        int start = editText.getSelectionStart();
        try {
            if (v instanceof TextView) {
                String content = ((TextView) v).getText().toString();
                if (!TextUtils.isEmpty(content) && TextUtils.isDigitsOnly(content)) {
                    editable.insert(start, content);
                    if (start == 6 || start == 15) {
                        editText.setSelection(start + 2);
                    } else {
                        editText.setSelection(start + 1);
                    }
                } else if (".".equalsIgnoreCase(content)) {
                    editable.insert(start, ".");
                    if (start == 6 || start == 15) {
                        editText.setSelection(start + 2);
                    } else {
                        editText.setSelection(start + 1);
                    }
                } else if ("X".equalsIgnoreCase(content)) {
                    editable.insert(start, "X");
                    if (start == 6 || start == 15) {
                        editText.setSelection(start + 2);
                    } else {
                        editText.setSelection(start + 1);
                    }
                } else if (v instanceof Button) {
                    if ("done".equals(tag)) {
                        this.dismiss();
                        InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
                    }
                }
            } else if (v instanceof ImageView) {
                if ("del".equalsIgnoreCase(tag)) {
                    if (editable != null && editable.length() > 0 && start > 0) {
                        editable.delete(start - 1, start);
                        editText.setSelection(start - 1);
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            editText.setSelection(editText.getText().toString().length());
        }
//        editText.setSelection(editText.getText().toString().length());
    }

    @Override
    public void show() {
        InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Context context = this.editText.getContext();
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.getCurrentFocus() != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } else {
                inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
            }
        } else {
            inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        }
        super.show();
//        new AndroidBug5497Workaround(mContext).possiblyResizeChildOfContent(this.getWindow().getDecorView().getHeight() + BitmapHelper.dip2px(25));
        INSTANCE_SHOW = this;
    }

    @Override
    public void dismiss() {
        InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(), 0);
        super.dismiss();
//        new AndroidBug5497Workaround(mContext).possiblyResizeChildOfContent(BitmapHelper.dip2px(25));
        INSTANCE_SHOW = null;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

//		InputMethodManager inputManager = (InputMethodManager) this.getContext().getSystemService(
//				Context.INPUT_METHOD_SERVICE);
//		inputManager.hideSoftInputFromWindow(this.editText.getWindowToken(),0);
//		Window localWindow = this.getWindow();
//		localWindow.setGravity(Gravity.BOTTOM);
//		localWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//		localWindow.setAttributes(localWindow.getAttributes());
    }
}

