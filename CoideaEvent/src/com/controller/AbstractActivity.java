package com.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.annotation.FindViewById;
import com.annotation.SetOnClick;
import com.application.MyApplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity基类
 * 
 * 通过annotation自动初始化View
 * 
 * 屏蔽onBackPressed(子类可通过重写重新实现)
 * 
 * @author dingxiaoyu
 */
public abstract class AbstractActivity extends Activity implements OnClickListener {

	/** 此方法在注入View之后调用 */
	protected abstract void afterInjectViews();

	/** 此方法在注入View之前调用 用于当前Activity独立功能的初始化 */
	protected abstract void beforInjectViews();

	/** 返回当前Activity实例 */
	protected abstract AbstractActivity getThis();

	/** 返回当前Activity对应的XML资源 */
	protected abstract int getResId();

	private AppManager appManager;

	public MyApplication eApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initial();

		beforInjectViews();

		getThis().setContentView(getResId());

		injectViews();

		injectOnClick();

		afterInjectViews();

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onBackPressed() {
		//
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity关闭，从栈中移除
		appManager.finishActivity(getThis());
	}

	/**
	 * 捕抓返回按钮,定义退出应用方法并销毁所有Activity
	 */
	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setTitle("真的要退出应用？").setMessage("你确定要退出本应用?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					appManager.AppExit(getApplicationContext());
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
		}
		return true;
	}

	protected String tag() {
		if (getThis() == null) {
			throw new RuntimeException("no implements method \"getThis()\"");
		}
		return getThis().getClass().getSimpleName().toString();
	};

	/** Activity公用初始化 */
	private void initial() {
		// 初始化应用管理器
		appManager = AppManager.getAppManager();
		// 把当前activity加入栈中
		appManager.addActivity(getThis());
		// 获取Application实例
		eApp = (MyApplication) this.getApplication();
//		// 去掉标题栏
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// 去掉信息栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		// 设置页面横向
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		// 设置软键盘
//		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/** View注入 */
	private void injectViews() {
		Class<?> instanceClass = getThis().getClass();
		Class<?> idClass = null;
		try {
			idClass = Class.forName(getPackageName() + ".R$id");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Field[] allFields = instanceClass.getDeclaredFields();
		for (Field field : allFields) {
			// findViewById
			if (field.isAnnotationPresent(FindViewById.class)) {
				FindViewById annotation = field.getAnnotation(FindViewById.class);
				int aValue = annotation.value();
				if (aValue == -1) {
					try {
						aValue = idClass.getField(field.getName()).getInt(getThis());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				field.setAccessible(true);
				try {
					field.set(getThis(), getThis().findViewById(aValue));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** OnClick注入 */
	private void injectOnClick() {
		Class<?> instanceClass = getThis().getClass();
		Field[] allFields = instanceClass.getDeclaredFields();
		for (Field field : allFields) {
			// setOnClickListener
			if (field.isAnnotationPresent(SetOnClick.class)) {
				SetOnClick annotation = field.getAnnotation(SetOnClick.class);
				boolean need = annotation.value();
				if (need) {
					try {
						Method method = View.class.getMethod("setOnClickListener", OnClickListener.class);
						field.setAccessible(true);
						method.invoke(field.get(getThis()), getThis());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/** HolderView 注入 */
	protected void injectHolderViews(View convertView, Object viewHolder) {
		Class<?> instanceClass = viewHolder.getClass();
		Class<?> idClass = null;
		try {
			idClass = Class.forName(getPackageName() + ".R$id");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Field[] allFields = instanceClass.getDeclaredFields();
		for (Field field : allFields) {
			// findViewById
			if (field.isAnnotationPresent(FindViewById.class)) {
				FindViewById annotation = field.getAnnotation(FindViewById.class);
				int aValue = annotation.value();
				if (aValue == -1) {
					try {
						aValue = idClass.getField(field.getName()).getInt(getThis());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				field.setAccessible(true);
				try {
					field.set(viewHolder, convertView.findViewById(aValue));
					System.out.println("pause");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** HodlerOnClick注入 */
	protected void injectHolderOnClick(Object viewHolder, final int position, final ViewHolder.OnClickListener listener) {
		Class<?> instanceClass = viewHolder.getClass();
		Field[] allFields = instanceClass.getDeclaredFields();
		for (Field field : allFields) {
			if (field.isAnnotationPresent(SetOnClick.class)) {
				SetOnClick annotation = field.getAnnotation(SetOnClick.class);
				boolean need = annotation.value();
				if (need) {
					try {
						Method method = View.class.getMethod("setOnClickListener", View.OnClickListener.class);
						field.setAccessible(true);
						method.invoke(field.get(viewHolder), new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								listener.onClick(v, position);
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
