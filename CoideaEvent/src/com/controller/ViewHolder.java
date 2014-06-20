package com.controller;

import android.view.View;

public abstract class ViewHolder {
	public interface OnClickListener {
		void onClick(View v, int position);
	}
}
