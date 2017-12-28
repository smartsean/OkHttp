package com.smartsean.download.file;

import android.content.Context;
import android.os.Environment;

import com.smartsean.download.utils.Md5Utils;

import java.io.File;
import java.io.IOException;

/**
 * @author SmartSean
 * @date 17/12/6 21:06
 */
public class FileStorageManager {

    private Context mContext;

    public void init(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 根据url获取File
     *
     * @param url
     * @return
     */
    public File getFileByName(String url) {
        File parent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            parent = mContext.getExternalCacheDir();
        } else {
            parent = mContext.getCacheDir();
        }

        String fileName = Md5Utils.generateCode(url);

        File file = new File(parent, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    private FileStorageManager() {
    }

    private static class FileStorageManagerHolder {
        private static final FileStorageManager INSTANCE = new FileStorageManager();
    }

    public static FileStorageManager getInstance() {
        return FileStorageManagerHolder.INSTANCE;
    }
}
