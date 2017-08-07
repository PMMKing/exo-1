package com.wuye.wuye.framework.view.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.wuye.wuye.R;
import com.wuye.wuye.utils.graphics.Dimen;


/**
 */
public class LoadingProgressView extends View {
    int color = getResources().getColor(R.color.pub_color_white);
    /**
     * 动画
     */
    private ValueAnimator mRotateAnimation;
    /**
     * 打钩
     */
    private ValueAnimator mTickAnimation;
    /**
     * 绘制感叹号
     */
    private ValueAnimator mshockAnimation;
    /**
     * 绘制感叹号震动
     */
    private ValueAnimator mCommaAnimation;
    private int mWidth, mHeight;
    private RectF mRectF = new RectF();
    private Paint circlePaint = new Paint();
    private Paint commaPaint = new Paint();
    private Paint tickPaint = new Paint();
    /**
     * 画笔宽度
     */
    private int strokeWidth = Dimen.dpToPx(3);
    /**
     *
     */
    private float radius = 0;
    //0画圆1,勾,2叹号出现，3叹号震动
    private int status = 0;
    /**
     * 测量打钩
     */
    private PathMeasure tickPathMeasure;
    /**
     * 感叹号
     */
    private PathMeasure commaPathMeasure1;
    private PathMeasure commaPathMeasure2;
    /**
     * 打钩百分比
     *
     * @param context
     */
    float tickPrecent = 0;
    /**
     * 感叹号百分比
     *
     * @param context
     */
    float commaPrecent = 0;
    /**
     * 震动百分比
     *
     * @param context
     */
    int shockPrecent = 0;
    /**
     * 扫过角度
     */
    private float curSweepAngle = 0;
    /**
     * 震动角度
     */
    private int shockDir = 20;
    private AnimationListener animationListener;

    public LoadingProgressView(Context context) {
        this(context, null);
    }

    public LoadingProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2 - strokeWidth;
        mRectF.set(new RectF(w / 2 - radius - strokeWidth / 2, h / 2 - radius - strokeWidth / 2, w / 2 + radius + strokeWidth / 2, h / 2 + radius + strokeWidth / 2));
        Path tickPath = new Path();
        tickPath.moveTo(0.5f * radius + strokeWidth, radius + strokeWidth);
        tickPath.lineTo(0.5f * radius + 0.3f * radius + strokeWidth, radius + 0.3f * radius + strokeWidth);
        tickPath.lineTo(radius + 0.5f * radius + strokeWidth, radius - 0.3f * radius + strokeWidth);
        tickPathMeasure = new PathMeasure(tickPath, false);
        //感叹号路径
        Path commaPath1 = new Path();
        Path commaPath2 = new Path();
        commaPath1.moveTo(radius + strokeWidth, 0.25f * radius + strokeWidth);
        commaPath1.lineTo(radius + strokeWidth, 1.25f * radius + strokeWidth);
        commaPath2.moveTo(radius + strokeWidth, 1.75f * radius + strokeWidth);
        commaPath2.lineTo(radius + strokeWidth, 1.5f * radius + strokeWidth);
        commaPathMeasure1 = new PathMeasure(commaPath1, false);
        commaPathMeasure2 = new PathMeasure(commaPath2, false);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(color);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);

        commaPaint.setAntiAlias(true);
        commaPaint.setColor(color);
        commaPaint.setStrokeWidth(strokeWidth);
        commaPaint.setStyle(Paint.Style.STROKE);

        tickPaint.setColor(color);
        tickPaint.setAntiAlias(true);
        tickPaint.setStrokeWidth(strokeWidth);
        tickPaint.setStyle(Paint.Style.STROKE);

        //进度条动画
        mRotateAnimation = ValueAnimator.ofFloat(0f, 360f);
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatMode(ValueAnimator.RESTART);
        mRotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curSweepAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mRotateAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                curSweepAngle = 0;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                status = 0;
            }
        });

        //打钩动画
        mTickAnimation = ValueAnimator.ofFloat(0f, 1f);
        mTickAnimation.setDuration(800);
        mTickAnimation.setInterpolator(new AccelerateInterpolator());
        mTickAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tickPrecent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mTickAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                status = 1;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (animationListener != null) {
                    animationListener.animationEnd();
                }
            }
        });

        //感叹号动画
        mCommaAnimation = ValueAnimator.ofFloat(0f, 1f);
        mCommaAnimation.setDuration(1000);
        mCommaAnimation.setInterpolator(new LinearInterpolator());
        mCommaAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                commaPrecent = (float) animation.getAnimatedValue();
//                LogUtils.d("感叹号进度 ： " + animation.getAnimatedValue());
                invalidate();
            }
        });

        mCommaAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                status = 3;
//                mshockAnimation.start();
                if (animationListener != null) {
                    animationListener.animationEnd();
                }
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                status = 2;
            }
        });

        //震动动画
        mshockAnimation = ValueAnimator.ofInt(-1, 0, 1, 0, -1, 0, 1, 0);
        mshockAnimation.setDuration(500);

        mshockAnimation.setInterpolator(new LinearInterpolator());
        mshockAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                shockPrecent = (int) animation.getAnimatedValue();
                invalidate();
            }

        });
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (status) {
            case 0:
                canvas.drawArc(mRectF, curSweepAngle, 270, false, circlePaint);
                break;
            case 1:
                drawTick(canvas);
                break;
            case 2:
                drawComma(canvas);
                break;
            case 3:
                drawShockComma(canvas);
                break;
        }
    }


    /**
     * 绘制打钩
     *
     * @param canvas
     */
    private void drawTick(Canvas canvas) {
        Path path = new Path();
        /*
         * On KITKAT and earlier releases, the resulting path may not display on a hardware-accelerated Canvas. 
         * A simple workaround is to add a single operation to this path, such as dst.rLineTo(0, 0).
         */
        tickPathMeasure.getSegment(0, tickPrecent * tickPathMeasure.getLength(), path, true);
        path.rLineTo(0, 0);
        canvas.drawPath(path, tickPaint);
        canvas.drawArc(mRectF, 0, 360, false, tickPaint);
    }

    /**
     * 绘制感叹号
     */
    private void drawComma(Canvas canvas) {
        Path path1 = new Path();
        commaPathMeasure1.getSegment(0, commaPrecent * commaPathMeasure1.getLength(), path1, true);
        path1.rLineTo(0, 0);
        Path path2 = new Path();
        commaPathMeasure2.getSegment(0, commaPrecent * commaPathMeasure2.getLength(), path2, true);
        path2.rLineTo(0, 0);
        canvas.drawPath(path1, commaPaint);
        canvas.drawPath(path2, commaPaint);
        canvas.drawArc(mRectF, 0, 360, false, commaPaint);
    }

    /**
     * 绘制震动效果
     *
     * @param canvas
     */
    private void drawShockComma(Canvas canvas) {
        Path path1 = new Path();
        commaPathMeasure1.getSegment(0, commaPathMeasure1.getLength(), path1, true);
        path1.rLineTo(0, 0);
        Path path2 = new Path();
        commaPathMeasure2.getSegment(0, commaPathMeasure2.getLength(), path2, true);
        path2.rLineTo(0, 0);

        if (shockPrecent != 0) {
            canvas.save();
            if (shockPrecent == 1)
                canvas.rotate(shockDir, radius, radius);
            else if (shockPrecent == -1)
                canvas.rotate(-shockDir, radius, radius);
        }
        canvas.drawPath(path1, commaPaint);
        canvas.drawPath(path2, commaPaint);
        canvas.drawArc(mRectF, 0, 360, false, commaPaint);
        if (shockPrecent != 0) {
            canvas.restore();
        }
    }

    /**
     * 开始动画
     */
    public void start() {
        mRotateAnimation.start();
    }

    /**
     * loading成功后调用
     */
    public void finishSuccess() {
        mRotateAnimation.cancel();
        mTickAnimation.start();
    }

    /**
     * loading失败后调用
     */
    public void finishFail() {
        mRotateAnimation.cancel();
        mCommaAnimation.start();
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.animationListener = animationListener;
    }

    public interface AnimationListener {
        void animationEnd();
    }
}
