package com.smartsean.download.http;

import java.io.File;

/**
 * @author SmartSean
 * @date 17/12/6 21:22
 */
public interface DownloadCallback {
    void success(File file);

    void fail(int errorCode, String errorMessage);

    void progress(int progress);
}
