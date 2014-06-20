package com.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter {

	List<View> views;

	public ViewPagerAdapter(List<View> view) {
		this.views = view;
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {


		((ViewPager) arg0).removeView(views.get(arg1));

	}

	public void finishUpdate(View arg0) {


	}

	public int getCount() {


		return views.size();

	}

	public Object instantiateItem(View arg0, int arg1) {


		((ViewPager) arg0).addView(views.get(arg1), 0);

		return views.get(arg1);

	}

	public boolean isViewFromObject(View arg0, Object arg1) {


		return arg0 == (arg1);

	}

	public void restoreState(Parcelable arg0, ClassLoader arg1) {


	}

	public Parcelable saveState() {

		return null;
	}

	@Override
	public void startUpdate(View arg0) {


	}

}
