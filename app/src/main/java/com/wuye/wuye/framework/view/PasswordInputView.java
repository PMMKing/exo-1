package com.wuye.wuye.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wuye.wuye.R;

import java.lang.reflect.Method;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class PasswordInputView extends EditText {

    private NumberKeyboardView numberKeyboardView;
    private int inputMode = NumberKeyboardView.MODE_NORMAL;
    public static final int TYPE_ID = 0x1;
    public static final int TYPE_OTHER = 0x2;

    private int type;

    private static final int defaultContMargin = 5;
    private static final int defaultSplitLineWidth = 3;

    private int borderColor = 0xFFCCCCCC;
    private float borderWidth = 5;
    private float borderRadius = 3;

    private int passwordLength = 6;
    private int passwordColor = 0xFFCCCCCC;
    private float passwordWidth = 8;
    private float passwordRadius = 3;

    private Paint passwordPaint = new Paint(ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);
    private int textLength;

    private InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    private Method mShowSoftInputOnFocus = getMethod(EditText.class, "setShowSoftInputOnFocus", boolean.class);
    private OnInputFinish mOnInputFinish;

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderWidth, dm);
        borderRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderRadius, dm);
        passwordLength = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordLength, dm);
        passwordWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordWidth, dm);
        passwordRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordRadius, dm);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        borderColor = a.getColor(R.styleable.PasswordInputView_pivBorderColor, borderColor);
        borderWidth = a.getDimension(R.styleable.PasswordInputView_pivBorderWidth, borderWidth);
        borderRadius = a.getDimension(R.styleable.PasswordInputView_pivBorderRadius, borderRadius);
        passwordLength = a.getInt(R.styleable.PasswordInputView_pivPasswordLength, passwordLength);
        passwordColor = a.getColor(R.styleable.PasswordInputView_pivPasswordColor, passwordColor);
        passwordWidth = a.getDimension(R.styleable.PasswordInputView_pivPasswordWidth, passwordWidth);
        passwordRadius = a.getDimension(R.styleable.PasswordInputView_pivPasswordRadius, passwordRadius);
        a.recycle();

        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        passwordPaint.setStrokeWidth(passwordWidth);
        passwordPaint.setStyle(Paint.Style.FILL);
        passwordPaint.setColor(passwordColor);

        init();
    }

    private void init() {

        numberKeyboardView = new NumberKeyboardView(this.getContext(), this);
        numberKeyboardView.init(inputMode, true, this);
        reflexSetShowSoftInputOnFocus(false);

        setSelection(getText().length());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // 外边框
        RectF rect = new RectF(0, 0, width, height);
        borderPaint.setColor(borderColor);
        canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);

        // 内容区
        RectF rectIn = new RectF(rect.left + defaultContMargin, rect.top + defaultContMargin,
                rect.right - defaultContMargin, rect.bottom - defaultContMargin);
        borderPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);

        // 分割线
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(defaultSplitLineWidth);
        for (int i = 1; i < passwordLength; i++) {
            float x = width * i / passwordLength;
            canvas.drawLine(x, 0, x, height, borderPaint);
        }

        // 密码
        float cx, cy = height / 2;
        float half = width / passwordLength / 2;
        for (int i = 0; i < textLength; i++) {
            cx = width * i / passwordLength + half;
            canvas.drawCircle(cx, cy, height / 4, passwordPaint);//改为高度的四分之一
        }
    }

    public void setType(int type) {
        this.type = type;
        if (numberKeyboardView != null) {
            numberKeyboardView.dismiss();
        }

    }

    private void reflexSetShowSoftInputOnFocus(boolean show) {
        if (mShowSoftInputOnFocus != null) {
            invokeMethod(mShowSoftInputOnFocus, this, show);
        } else {
            hideKeyboard();
        }
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
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftInput() {
        setFocusableInTouchMode(false);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.textLength = text.toString().length();
        invalidate();
        if (textLength >= 6 && mOnInputFinish != null) {
            mOnInputFinish.inputFinish(text.toString());
        }
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
        invalidate();
    }

    public int getPasswordLength() {
        return passwordLength;
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        invalidate();
    }

    public int getPasswordColor() {
        return passwordColor;
    }

    public void setPasswordColor(int passwordColor) {
        this.passwordColor = passwordColor;
        passwordPaint.setColor(passwordColor);
        invalidate();
    }

    public float getPasswordWidth() {
        return passwordWidth;
    }

    public void setPasswordWidth(float passwordWidth) {
        this.passwordWidth = passwordWidth;
        passwordPaint.setStrokeWidth(passwordWidth);
        invalidate();
    }

    public float getPasswordRadius() {
        return passwordRadius;
    }

    public void setPasswordRadius(float passwordRadius) {
        this.passwordRadius = passwordRadius;
        invalidate();
    }

    public void setOnInputFinish(OnInputFinish onInputFinish) {
        this.mOnInputFinish = onInputFinish;
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

    public interface OnInputFinish {
        void inputFinish(String s);
    }
}