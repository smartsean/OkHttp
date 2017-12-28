package com.smartsean.download;

import com.smartsean.download.http.DownloadCallback;
import com.smartsean.download.http.HttpManager;

import java.io.IOException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.smartsean.download.http.HttpManager.ERROR_CODE;

/**
 * @author SmartSean
 * @date 17/12/7 10:58
 */
public class DownloadManager {

    private final static int MAX_THREAD = 2;

    private static final ThreadPoolExecutor mThreadPool = new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 60, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {

        private AtomicInteger integer = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "Download Thread # " + integer.getAndIncrement());
            return thread;
        }
    });


    /**
     * 下载开始命令
     *
     * @param url
     * @param callback
     */
    public void download(final String url, final DownloadCallback callback) {
        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful() && callback != null) {
                    callback.fail(ERROR_CODE, "网络请求失败，请稍后再试");
                    return;
                }
                long length = response.body().contentLength();
                if (length == -1) {
                    callback.fail(2, "content length 为空");
                    return;
                }
                processDownload(url, length, callback);
            }
        });
    }

    /**
     * 多线程下载
     *
     * @param url
     * @param length
     * @param callback
     */
    private void processDownload(String url, long length, DownloadCallback callback) {
        long threadDownloadSize = length / MAX_THREAD;
        for (int i = 0; i < MAX_THREAD; i++) {
            long startSize = i * threadDownloadSize;
            long endSize = (i + 1) * threadDownloadSize - 1;
            mThreadPool.execute(new DownloadRunnable(startSize, endSize, url, callback));
        }
    }

    private DownloadManager() {
    }

    private static class DownloadManagerHolder {
        private static final DownloadManager INSTANCE = new DownloadManager();
    }

    public static DownloadManager getInstance() {
        return DownloadManagerHolder.INSTANCE;
    }
}
