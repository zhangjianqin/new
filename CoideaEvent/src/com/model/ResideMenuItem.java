package com.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coideaevent.R;

public class ResideMenuItem extends LinearLayout {

	/** menu item icon */
	private ImageView iv_icon;
	/** menu item title */
	private TextView tv_title;
	private Handler handler;
	private Message msg;
	private String count;
	public ResideMenuItem(Context context) {
		super(context);
		initViews(context);
	}

	public ResideMenuItem(Context context, Handler handl,String counts) {

		super(context);
		initViews(context);
		handler = handl;
		count = counts;

	}

	public ResideMenuItem(Context context, int icon, int title) {
		super(context);
		initViews(context);
		iv_icon.setImageResource(icon);
		tv_title.setText(title);
	}

	public ResideMenuItem(Context context, int icon, String title) {
		super(context);
		initViews(context);
		iv_icon.setImageResource(icon);
		tv_title.setText(title);
	}

	private void initViews(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.residemenu_item, this);
		Button btn_usercenter = (Button) findViewById(R.id.btn_usercenter);
		Button btn_message = (Button) findViewById(R.id.btn_message);
		Button btn_add = (Button) findViewById(R.id.btn_add);
		Button btn_creative = (Button) findViewById(R.id.btn_creative);
		Button btn_project = (Button) findViewById(R.id.btn_project);
		Button btn_event = (Button) findViewById(R.id.btn_event);
		Button btn_group = (Button) findViewById(R.id.btn_group);
		Button btn_invite = (Button) findViewById(R.id.btn_invite);
		Button btn_setting = (Button) findViewById(R.id.btn_setting);
		Button btn_quit = (Button) findViewById(R.id.btn_quit);
		setBtnListener(btn_usercenter);
		setBtnListener(btn_message);
		setBtnListener(btn_add);
		setBtnListener(btn_creative);
		setBtnListener(btn_project);
		setBtnListener(btn_event);
		setBtnListener(btn_group);
		setBtnListener(btn_invite);
		setBtnListener(btn_setting);
		setBtnListener(btn_quit);
	}

	private void setBtnListener(Button button) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btn_usercenter:
					msg = handler.obtainMessage(1, 1);
					break;
				case R.id.btn_message:
					msg = handler.obtainMessage(2, 2);
					break;
				case R.id.btn_add:
					msg = handler.obtainMessage(3, 3);
					break;
				case R.id.btn_creative:
					msg = handler.obtainMessage(4, 4);
					break;
				case R.id.btn_project:
					msg = handler.obtainMessage(5, 5);
					break;
				case R.id.btn_event:
					msg = handler.obtainMessage(6, 6);
					break;
				case R.id.btn_group:
					msg = handler.obtainMessage(7, 7);
					break;
				case R.id.btn_invite:
					msg = handler.obtainMessage(8, 8);
					break;
				case R.id.btn_setting:
					msg = handler.obtainMessage(9, 9);
					break;
				case R.id.btn_quit:
					msg = handler.obtainMessage(10, 10);
					break;
				}
				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * set the icon color;
	 * 
	 * @param icon
	 */
	public void setIcon(int icon) {
		iv_icon.setImageResource(icon);
	}

	/**
	 * set the title with resource ;
	 * 
	 * @param title
	 */
	public void setTitle(int title) {
		tv_title.setText(title);
	}

	/**
	 * set the title with string;
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tv_title.setText(title);
	}
}
