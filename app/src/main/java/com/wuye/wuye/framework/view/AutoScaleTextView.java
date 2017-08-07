package com.wuye.wuye.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wuye.wuye.R;


/**
 */
public class AutoScaleTextView extends TextView {

    protected float maxTextSize;
    protected float minTextSize;
    protected static final float DEFAULT_MAX_TEXTSIZE = 16F;
    protected static final float DEFAULT_MIN_TEXTSIZE = 10F;

    public AutoScaleTextView(Context context) {
        this(context, null);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.atom_autoScaleTextViewStyle);

        // Use this constructor, if you do not want use the default style
        // super(context, attrs);
    }

    public AutoScaleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.atom_AutoScaleTextView, defStyle, 0);
        this.minTextSize = a.getDimension(R.styleable.atom_AutoScaleTextView_atom_minTextSize, DEFAULT_MIN_TEXTSIZE);
        this.maxTextSize = a.getDimension(R.styleable.atom_AutoScaleTextView_atom_maxTextSize, DEFAULT_MAX_TEXTSIZE);
        a.recycle();
    }

    /**
     * Set the minimum text size for this view
     * @param minTextSize
     *            The minimum text size
     */
    public void setMinTextSize(float minTextSize) {
        this.minTextSize = minTextSize;
    }

    /**
     * Set the setMaxTextSize text size for this view
     * @param maxTextSize
     *            The setMaxTextSize text size
     */
    public void setMaxTextSize(float maxTextSize) {
        this.maxTextSize = maxTextSize;
    }

    /**
     * Resize the text so that it fits
     * @param text
     *            The text. Neither <code>null</code> nor empty.
     * @param textWidth
     *            The width of the TextView. > 0
     */
    private void refitText(String text, int textWidth) {

        if (textWidth <= 0 || text == null || text.length() == 0) {
            return;
        }

        // the width
        int targetWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();

        float maxSize = maxTextSize;
        getPaint().setTextSize(maxTextSize);
        while(getPaint().measureText(text) >= targetWidth && maxSize > minTextSize){
            maxSize--;
            getPaint().setTextSize(maxSize);
        }
    }

    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        this.refitText(text.toString(), this.getWidth());
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldwidth, int oldheight) {
        if (width != oldwidth) {
            this.refitText(this.getText().toString(), width);
        }
    }

    @Override
    public boolean isSelected() {
        return true;
    }

}

