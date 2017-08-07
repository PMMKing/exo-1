package com.wuye.wuye.page.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.wuye.wuye.R;
import com.wuye.wuye.base.BaseActivity;
import com.wuye.wuye.framework.view.PasswordInputView;
import com.wuye.wuye.framework.view.SafeEditText;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by shucheng.qu on 2017/6/13.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.passwordInputView)
    PasswordInputView passwordInputView;
    @BindView(R.id.set)
    SafeEditText set;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_test_layout);
        ButterKnife.bind(this);
        set.setType(SafeEditText.TYPE_ID);
        passwordInputView.setType(SafeEditText.TYPE_ID);
    }

    private void DDDD(File[] folders) {


        Observable<String> just = Observable.just("", "", "");
        just.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        final CompositeDisposable disposable = new CompositeDisposable();
        disposable.add(null);

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d("1", "Observable thread is : " + Thread.currentThread().getName());
                Log.d("1", "emit 1");
                emitter.onNext(1);
            }
        });

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable.add(d);
            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


        disposable.clear();
    }


}
