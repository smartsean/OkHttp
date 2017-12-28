package com.smartsean.download;

import com.smartsean.download.file.FileStorageManager;
import com.smartsean.download.http.DownloadCallback;
import com.smartsean.download.http.HttpManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

import static com.smartsean.download.http.HttpManager.ERROR_CODE;

/**
 * @author SmartSean
 * @date 17/12/7 11:04
 */
public class DownloadRunnable implements Runnable {

    private long mStart;
    private long mEnd;

    private String mUrl;
    private DownloadCallback mDownloadCallback;


    public DownloadRunnable(long mStart, long mEnd, String mUrl, DownloadCallback mDownloadCallback) {
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mUrl = mUrl;
        this.mDownloadCallback = mDownloadCallback;
    }

    @Override
    public void run() {
        Response response = HttpManager.getInstance().syncRequestByRange(mUrl, mStart, mEnd);
        if (null == response && mDownloadCallback != null) {
            mDownloadCallback.fail(ERROR_CODE, "网络出问题了");
            return;
        }
        File file = FileStorageManager.getInstance().getFileByName(mUrl);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(mStart);
            byte[] buffer = new byte[1024 * 500];
            int len;
            InputStream inputStream = response.body().byteStream();
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
            }
            mDownloadCallback.success(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
