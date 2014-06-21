package com.application;

import android.app.Application;

import com.crash.CrashHandler;

/**
 * 继承application,重写application创建时所需要执行的方法
 *
 * @author zhangjianqin
 *
 */
public class MyApplication extends Application {

     @Override
     public void onCreate() {
          super.onCreate();
          // 应用全局异常捕抓
//          CrashHandler.getInstance().init(getApplicationContext());
     }
}