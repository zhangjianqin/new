package com.application;

import android.app.Application;

import com.crash.CrashHandler;

/**
 * �̳�application,��дapplication����ʱ����Ҫִ�еķ���
 * 
 * @author zhangjianqin
 * 
 */
public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// Ӧ��ȫ���쳣��ץ
//		CrashHandler.getInstance().init(getApplicationContext());
	}
}
