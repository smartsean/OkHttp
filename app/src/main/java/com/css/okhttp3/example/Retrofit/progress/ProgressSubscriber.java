package com.css.okhttp3.example.Retrofit.progress;

import android.content.Context;
import android.widget.Toast;

import com.css.okhttp3.example.Retrofit.SubscriberOnNextListener;
import com.css.okhttp3.example.Retrofit.progress.ProgressCancelListener;
import com.css.okhttp3.example.Retrofit.progress.ProgressDialogHandler;

import rx.Subscriber;

/**
 * Created by sean on 2017/3/1.
 */

public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private Context context;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.context = context;
        this.mProgressDialogHandler = new ProgressDialogHandler(context,true,this);
    }

    /**
     * 显示dialog
     */
    private void showProgressDialog(){
        if (mProgressDialogHandler != null){
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null){
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T o) {
        mSubscriberOnNextListener.onNext(o);
    }

    /**
     * 取消本次请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
