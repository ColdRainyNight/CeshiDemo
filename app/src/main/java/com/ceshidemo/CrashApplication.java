package com.ceshidemo;

import android.app.Application;

/**
 * 类描述：
 * 创建人：捕获异常
 * 创建时间：
 */
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
