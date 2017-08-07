package com.wuye.wuye.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v4.view.MotionEventCompat;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;

import com.wuye.wuye.R;


/**
 * 可清空的EditText
 *
 * @author steven.shen
 */
public class ClearableEditText extends android.support.v7.widget.AppCompatEditText implements OnFocusChangeListener, TextWatcher {

    Drawable clearableDrawable;
    private Drawable[] mCompoundDrawables;
    private boolean isClearableHit;
    public boolean isFoucs;
    private boolean isVisible;
    private boolean isToUpperCase;

    /**
     * hint text style
     **/
    private final int textStyleHint;

    private final int textStyle;

    private Drawable clearIconDrawable;
    private RotateDrawable progressDrawable;
    private ProgressThread progressThread;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.atom_ClearableEditText);
        textStyleHint = a.getInt(R.styleable.atom_ClearableEditText_atom_textStyleHint, Typeface.NORMAL);
        textStyle = a.getInt(R.styleable.atom_ClearableEditText_android_textStyle, Typeface.NORMAL);
        clearIconDrawable = a.getDrawable(R.styleable.atom_ClearableEditText_atom_clearableIcon);
        a.recycle();
        // drawable right
        clearableDrawable = getCompoundDrawables()[2];
        if (!isInEditMode()) {
            if (clearableDrawable == null) {
                if (clearIconDrawable == null) {
                    clearIconDrawable = getResources().getDrawable(R.drawable.pub_delete_icon);
                }
                clearableDrawable = clearIconDrawable;
            }
            clearableDrawable.setBounds(0, 0, clearableDrawable.getIntrinsicWidth(),
                    clearableDrawable.getIntrinsicHeight());
        }
        addTextChangedListener(this);
        setOnFocusChangeListener(this);
    }

    /**
     * We expose this method as public because calling setError(null) flight_on Gingerbread devices will hide the cancel (and
     * other) drawables. You can call showOrHideCancel() after you call setError(null) to reset the drawables.
     */
    public void showOrHideCancel() {
        setCancelVisible(getText().length() > 0);
    }

    public void showProgress() {
        if (progressDrawable == null) {
            progressDrawable = (RotateDrawable) getResources().getDrawable(R.drawable.pub_gropress_circle);
            progressDrawable.setBounds(0, 0, progressDrawable.getIntrinsicWidth(), progressDrawable.getIntrinsicHeight());
        }

        if (mCompoundDrawables == null) {
            mCompoundDrawables = getCompoundDrawables();
        }
        setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1], progressDrawable,
                mCompoundDrawables[3]);

        if (progressThread != null) {
            progressThread.progress = false;
        }
        progressThread = new ProgressThread();
        progressThread.start();
    }

    public void hideProgress() {
        if (mCompoundDrawables == null) {
            mCompoundDrawables = getCompoundDrawables();
        }
        setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1],
                mCompoundDrawables[2], mCompoundDrawables[3]);
        if (progressThread != null) {
            progressThread.progress = false;
        }
        showOrHideCancel();
    }


    public void setCancelVisible(boolean visible) {
        if (mCompoundDrawables == null) {
            mCompoundDrawables = getCompoundDrawables();
        }
        isVisible = visible;
        if (visible) {
            setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1], clearableDrawable,
                    mCompoundDrawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(mCompoundDrawables[0], mCompoundDrawables[1],
                    mCompoundDrawables[2], mCompoundDrawables[3]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isClearableHit = clearableDrawable != null && isVisible
                        && event.getX() > getWidth() - getPaddingRight() - clearableDrawable.getIntrinsicWidth();
                break;
            case MotionEvent.ACTION_UP:
                if (isClearableHit) {
                    setText("");
                    setCancelVisible(false);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setToUpperCase(boolean isToUpperCase) {
        this.isToUpperCase = isToUpperCase;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isFoucs = hasFocus;
        if (hasFocus) {
            showOrHideCancel();
        } else {
            setCancelVisible(false);
        }

        if (isToUpperCase && v instanceof EditText) {
            final EditText ed = (EditText) v;
            Editable s = ed.getText();
            if (s != null) {
                if (!s.toString().equals(s.toString().toUpperCase())) {
                    ed.postDelayed(new Runnable() {
                        public void run() {
                            int postion = Math.min(ed.getSelectionStart(), ed.getText().toString().length());
                            ed.setText(ed.getText().toString().toUpperCase());
                            Selection.setSelection(ed.getText(), postion);
                        }
                    }, 200);
                }
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (isFoucs) {
            showOrHideCancel();
        }
        setTypeface(null, getText().length() > 0 ? textStyle : textStyleHint);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private class ProgressThread extends Thread {

        boolean progress = progressDrawable != null;
        int level = 0;

        @Override
        public void run() {
            super.run();
            while (progress) {
                if (level >= 10000) {
                    level = 0;
                }
                post(new Runnable() {
                    @Override
                    public void run() {
                        progressDrawable.setLevel(level += 100);
                    }
                });
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
