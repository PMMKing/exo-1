package com.wuye.wuye.utils.simpleutils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.wuye.wuye.utils.LogUtils;

/**
 * Created by shucheng.qu on 2016/12/2 002.
 */

public class SoftKeyboardUtils {


    /**
     * @param root         最外层布局，需要调整的布局
     * @param scrollToView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public static void controlKeyboardLayout(final View root, final View scrollToView) {
        // 注册一个回调函数，当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时调用这个回调函数。
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 当前视图最外层的高度减去现在所看到的视图的最底部的y坐标
                        int rootInvisibleHeight = root.getRootView()
                                .getHeight() - rect.bottom;
                        LogUtils.d("最外层的高度" + root.getRootView().getHeight());
                        // 若rootInvisibleHeight高度大于100，则说明当前视图上移了，说明软键盘弹出了
                        if (rootInvisibleHeight > 100) {
                            //软键盘弹出来的时候
                            int[] location = new int[2];
                            // 获取scrollToView在窗体的坐标
                            scrollToView.getLocationInWindow(location);
                            // 计算root滚动高度，使scrollToView在可见区域的底部
//                            int srollHeight = (location[1] + scrollToView
//                                    .getHeight()) - rect.bottom;
//                            root.scrollTo(0, srollHeight);
                            int srollHeight = (location[1] + scrollToView.getHeight()) - root.getRootView().getHeight();
                            int height = rootInvisibleHeight - (Math.abs(srollHeight));
                            root.scrollBy(0, height);
                        } else {
                            // 软键盘没有弹出来的时候
                            root.scrollTo(0, 0);
                        }

                    }
                });
    }

}
