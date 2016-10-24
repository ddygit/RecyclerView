package com.example.administrator.recylerviewdemo.app;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by Administrator on 2016/10/24.
 */

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.

    }
}
