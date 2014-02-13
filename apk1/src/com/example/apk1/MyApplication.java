package com.example.apk1;

import android.app.Application;

/**
 * Created by ttpod on 14-2-13.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication instance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application has not been created");
        }

        return mInstance;
    }

    public MyApplication() {
        mInstance = this;
    }
}
