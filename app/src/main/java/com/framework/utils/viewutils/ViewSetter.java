//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.framework.utils.viewutils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewSetter {
    private static final boolean DEBUG = true;

    public ViewSetter() {
    }

    public static ViewSetter.ViewSetting getSetting(View view) {
        if(view == null) {
            throw new IllegalArgumentException("view must be not null...");
        } else {
            for(Class clazz = view.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
                Object setting = null;
                if(clazz == View.class) {
                    setting = new ViewSetter.ViewProxy();
                } else if(clazz == TextView.class) {
                    setting = new ViewSetter.TextViewProxy();
                } else if(clazz == ImageView.class) {
                    setting = new ViewSetter.ImageViewProxy();
                }

                if(setting != null) {
                    ((ViewSetter.CommonViewProxy)setting).init(view);
                    return (ViewSetter.ViewSetting)setting;
                }
            }

            throw new IllegalArgumentException("can\'t get view setting");
        }
    }

    public static class ImageViewProxy extends ViewSetter.ViewProxy<ImageView> {
        public ImageViewProxy() {
        }

        protected boolean set(ViewSetter.Method method, int state, Object... params) {
            if(method == ViewSetter.Method.Src) {
                this.setViewImage((ImageView)this.getView(), params[0]);
                return this.visible();
            } else {
                return super.set(method, state, params);
            }
        }

        protected void get(List<Method> methods) {
            super.get(methods);
            methods.add(ViewSetter.Method.Src);
        }

        private void setViewImage(ImageView view, Object obj) {
            if(obj instanceof Bitmap) {
                view.setImageBitmap((Bitmap)obj);
            } else if(obj instanceof Drawable) {
                view.setImageDrawable((Drawable)obj);
            } else {
                if(!(obj instanceof Integer)) {
                    throw this.ex(view, obj);
                }

                view.setImageResource(((Integer)obj).intValue());
            }

        }
    }

    public static class TextViewProxy extends ViewSetter.ViewProxy<TextView> {
        public TextViewProxy() {
        }

        protected boolean set(ViewSetter.Method method, int state, Object... params) {
            if(method == ViewSetter.Method.Text) {
                this.setViewText((TextView)this.getView(), params);
                return this.visible();
            } else {
                return super.set(method, state, params);
            }
        }

        protected void get(List<Method> methods) {
            methods.add(ViewSetter.Method.Text);
        }

        private void setViewText(TextView view, Object... obj) {
            Object data;
            if(obj.length != 1) {
                data = ViewUtils.joinNotAllowedNull(obj);
            } else {
                data = obj[0];
            }

            if(data instanceof CharSequence) {
                view.setText((CharSequence)data);
            } else {
                if(!(data instanceof Integer)) {
                    throw this.ex(this.getView(), obj);
                }

                view.setText(((Integer)data).intValue());
            }

        }
    }

    public static class ViewProxy<T extends View> extends ViewSetter.CommonViewProxy<View> {
        public ViewProxy() {
        }

        protected T getView() {
            return (T) this.mView;
        }

        protected boolean set(ViewSetter.Method method, int state, Object... params) {
            if(method == ViewSetter.Method.Background) {
                this.setBackground(this.mView, params[0]);
                return this.visible();
            } else {
                throw this.ex(this.mView, params[0]);
            }
        }

        private void setBackground(View v, Object param) {
            if(param instanceof Drawable) {
                v.setBackgroundDrawable((Drawable)param);
            } else {
                if(!(param instanceof Integer)) {
                    throw this.ex(v, param);
                }

                v.setBackgroundResource(((Integer)param).intValue());
            }

        }

        protected void get(List<Method> methods) {
            methods.add(ViewSetter.Method.Background);
        }
    }

    public static class FakeProxy implements ViewSetter.ViewSetting {
        public FakeProxy() {
        }

        public boolean setOr(ViewSetter.Method method, boolean condition, int state, Object... params) {
            return false;
        }
    }

    public abstract static class CommonViewProxy<T extends View> implements ViewSetter.ViewSetting {
        protected T mView;

        public CommonViewProxy() {
        }

        public void init(T view) {
            this.mView = view;
        }

        public final boolean setOr(ViewSetter.Method method, boolean condition, int state, Object... params) {
            if(!condition) {
                return this.gone(state);
            } else if(method == ViewSetter.Method.NotCare) {
                return this.visible();
            } else if(CheckUtils.isContainsEmpty(params)) {
                return this.gone(state);
            } else {
                ArrayList methods = new ArrayList();
                this.get(methods);
                Iterator var6 = methods.iterator();

                ViewSetter.Method ori;
                do {
                    if(!var6.hasNext()) {
                        throw this.ex(this.mView, params[0]);
                    }

                    ori = (ViewSetter.Method)var6.next();
                } while(ori != method);

                return this.set(method, state, params);
            }
        }

        protected abstract boolean set(ViewSetter.Method var1, int var2, Object... var3);

        protected abstract void get(List<Method> var1);

        protected boolean gone(int state) {
            this.mView.setVisibility(state);
            return false;
        }

        protected boolean visible() {
            this.mView.setVisibility(0);
            return true;
        }

        protected RuntimeException ex(View v, Object param) {
            return new RuntimeException("can\'t handle... view " + v.getClass().getSimpleName() + " data :" + param.toString());
        }
    }

    public static enum Method {
        Text,
        Src,
        Background,
        NotCare;

        private Method() {
        }
    }

    public interface ViewSetting {
        boolean setOr(ViewSetter.Method var1, boolean var2, int var3, Object... var4);
    }
}
