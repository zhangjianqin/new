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
 * Activity����
 * 
 * ͨ��annotation�Զ���ʼ��View
 * 
 * ����onBackPressed(�����ͨ����д����ʵ��)
 * 
 * @author dingxiaoyu
 */
public abstract class AbstractActivity extends Activity implements OnClickListener {

	/** �˷�����ע��View֮����� */
	protected abstract void afterInjectViews();

	/** �˷�����ע��View֮ǰ���� ���ڵ�ǰActivity�������ܵĳ�ʼ�� */
	protected abstract void beforInjectViews();

	/** ���ص�ǰActivityʵ�� */
	protected abstract AbstractActivity getThis();

	/** ���ص�ǰActivity��Ӧ��XML��Դ */
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
		// activity�رգ���ջ���Ƴ�
		appManager.finishActivity(getThis());
	}

	/**
	 * ��ץ���ذ�ť,�����˳�Ӧ�÷�������������Activity
	 */
	public boolean onKeyDown(int keycode, KeyEvent keyEvent) {
		if (keycode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(this).setTitle("���Ҫ�˳�Ӧ�ã�").setMessage("��ȷ��Ҫ�˳���Ӧ��?").setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					appManager.AppExit(getApplicationContext());
				}
			}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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

	/** Activity���ó�ʼ�� */
	private void initial() {
		// ��ʼ��Ӧ�ù�����
		appManager = AppManager.getAppManager();
		// �ѵ�ǰactivity����ջ��
		appManager.addActivity(getThis());
		// ��ȡApplicationʵ��
		eApp = (MyApplication) this.getApplication();
//		// ȥ��������
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		// ȥ����Ϣ��
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		// ����ҳ�����
//		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		// ���������
//		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/** Viewע�� */
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

	/** OnClickע�� */
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

	/** HolderView ע�� */
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

	/** HodlerOnClickע�� */
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
