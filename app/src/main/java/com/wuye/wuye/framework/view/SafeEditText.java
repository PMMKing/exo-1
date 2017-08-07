package com.wuye.wuye.framework.view;


import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;


/**
 * Created by steven.shen flight_on 15/7/23.
 */
public class SafeEditText extends ClearableEditText {

    private NumberKeyboardView numberKeyboardView;
    private int inputMode = NumberKeyboardView.MODE_NORMAL;

    public static final int TYPE_ID = 0x1;
    public static final int TYPE_OTHER = 0x2;

    private int type;

    private Method mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);
    private InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

    public SafeEditText(Context context) {
        super(context);
        init();
    }

    public SafeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setType(int type) {
        this.type = type;
        if (numberKeyboardView != null) {
            numberKeyboardView.dismiss();
        }

    }

    public int getType() {
        return type;
    }

    private void init() {

        numberKeyboardView = new NumberKeyboardView(this.getContext(), this);
        numberKeyboardView.init(inputMode, true, this);

        mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);

        setInputType(getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setFocusableInTouchMode(true);
                SafeEditText.this.setFocusable(true);
                SafeEditText.this.requestFocus();
                if (type == TYPE_ID) {
                    setCursorVisible(true);
                } else {
                    showSoftInput();
                }
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (type == TYPE_ID) {
                    setCursorVisible(true);
                } else {
                    showSoftInput();
                }
                return false;
            }
        });

        reflexSetShowSoftInputOnFocus(false);

        setSelection(getText().length());
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (type == TYPE_ID) {
            if (focused) {
                hideKeyboard();
                showKeyboard();
            } else {
                dismissKeyboard();
            }
        } else {
            if (focused) {
                showSoftInput();
            } else {
                hideSoftInput();
            }
        }
    }

    private void showSoftInput() {
        if (imm == null) {
            return;
        }
        imm.showSoftInput(SafeEditText.this, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftInput() {
        setFocusableInTouchMode(false);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (type == TYPE_ID) {
            final boolean ret = super.onTouchEvent(event);
            if (!hasFocus() && requestFocus()) {
                hideKeyboard();
                showKeyboard();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                hideKeyboard();
                showKeyboard();
            }
            return ret;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void reflexSetShowSoftInputOnFocus(boolean show) {
        if (mShowSoftInputOnFocus != null) {
            invokeMethod(mShowSoftInputOnFocus, this, show);
        } else {
            hideKeyboard();
        }
    }

    private void hideKeyboard() {
        final InputMethodManager imm = ((InputMethodManager) getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE));
        if (imm != null && imm.isActive(this)) {
            imm.hideSoftInputFromWindow(getApplicationWindowToken(), 0);
        }
    }

    public boolean isKeyboardShown() {
        return numberKeyboardView.isShowing();
    }

    public void dismissKeyboard() {
        if (numberKeyboardView != null) {
            numberKeyboardView.dismiss();
        }
    }

    public void showKeyboard() {
        if (numberKeyboardView != null) {
            numberKeyboardView.show();
        }
    }

    public Method getMethod(Class<?> cls, String methodName, Class<?>... parametersType) {
        Class<?> sCls = cls.getSuperclass();
        while (sCls != Object.class) {
            try {
                return sCls.getDeclaredMethod(methodName, parametersType);
            } catch (NoSuchMethodException e) {
            }
            sCls = sCls.getSuperclass();
        }
        return null;
    }

    public Object invokeMethod(Method method, Object receiver, Object... args) {
        try {
            return method.invoke(receiver, args);
        } catch (Exception e) {
        }
        return null;
    }
}