package com.smartsean.download.http;

import android.content.Context;

import com.smartsean.download.file.FileStorageManager;
import com.smartsean.download.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author SmartSean
 * @date 17/12/6 21:26
 */
public class HttpManager {

    public static final int ERROR_CODE = 1;

    private Context mContext;

    private OkHttpClient mClient;

    public void init(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 同步方式
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步方式下载
     *
     * @param url
     * @param callback
     * @return
     */
    public Response asyncRequest(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
        return null;
    }

    /**
     * 异步方式下载
     *
     * @param url
     * @param downloadCallback
     * @return
     */
    public Response asyncRequest(final String url, final DownloadCallback downloadCallback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && downloadCallback != null) {
                    downloadCallback.fail(ERROR_CODE, "网络请求失败，请稍后再试");
                }

                File file = FileStorageManager.getInstance().getFileByName(url);
                byte[] buffer = new byte[1024 * 500];
                int len;
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                InputStream inputStream = response.body().byteStream();
                while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    fileOutputStream.flush();
                }
                downloadCallback.success(file);

            }
        });
        return null;
    }


    /**
     * 同步方法多线程下载添加header的Range字段
     *
     * @param url
     * @param start
     * @param end
     * @return
     */
    public Response syncRequestByRange(String url, long start, long end) {
        Request request = new Request.Builder().url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        try {
            return mClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpManager() {
        mClient = new OkHttpClient();
    }

    private static class HttpManagerHolder {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    public static HttpManager getInstance() {
        return HttpManagerHolder.INSTANCE;
    }
}
