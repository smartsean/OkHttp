package com.css.okhttp3.example;

import android.app.Application;

import com.smartsean.download.file.FileStorageManager;
import com.smartsean.download.http.HttpManager;

/**
 * @author SmartSean
 * @date 17/12/6 23:07
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
    }
}
