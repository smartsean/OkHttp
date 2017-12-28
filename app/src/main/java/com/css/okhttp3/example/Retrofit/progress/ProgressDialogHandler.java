package com.css.okhttp3.example.Retrofit.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by sean on 2017/3/1.
 */

public class ProgressDialogHandler extends Handler {

    /**
     * 显示dialog
     */
    public static final int SHOW_PROGRESS_DIALOG = 1;
    /**
     * 隐藏dialog
     */
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    /**
     * 上下文对象
     */
    private Context context;

    /**
     * 控制能够取消dialog
     */
    private boolean cancelable;

    /**
     * 定义取消Progress的监听
     */
    private ProgressCancelListener mProgressCancelListener;

    /**
     * 定义一个Progress用于显示加载中
     */
    private ProgressDialog progressDialog;

    /**
     * 构造方法
     *
     * @param context                 用来显示Toast
     * @param cancelable              设置是否可以取消Progress
     * @param mProgressCancelListener 设置取消监听
     */
    public ProgressDialogHandler(Context context, boolean cancelable, ProgressCancelListener mProgressCancelListener) {
        super();
        this.context = context;
        this.cancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    /**
     * 初始化progress，并显示
     */
    private void initProgressDialog() {
        if (progressDialog == null) {
            //新建一个ProgressDialog
            progressDialog = new ProgressDialog(context);
            //设置是否可以取消
            progressDialog.setCancelable(cancelable);
            if (cancelable) {
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    /**
     * 隐藏progress
     */
    protected void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * 处理Handler信息
     *
     * @param msg
     */
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
