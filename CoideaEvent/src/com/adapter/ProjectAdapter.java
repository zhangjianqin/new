package com.adapter;

import java.util.List;
import java.util.Map;

import com.coideaevent.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ProjectAdapter extends SimpleAdapter {
	private List<? extends Map<String, ?>> list;
	private int resource;
	private Handler handler;
	private ViewHolder holder;
	private Context context;

	public ProjectAdapter(Context context, List<? extends Map<String, ?>> data,
			Handler handler, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.list = data;
		this.resource = resource;
		this.context = context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		return caretView(position, convertView, parent, resource);
	}
	public View caretView(int position, View convertView, ViewGroup parent,int vresource) {
		// TODO Auto-generated method stub
//		if (convertView == null) {
			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(resource, null);
			holder = new ViewHolder();
			holder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
			holder.tv_date = (TextView)convertView.findViewById(R.id.tv_date);
			holder.image_detail = (ImageView)convertView.findViewById(R.id.image_detail);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		holder.tv_title.setText(list.get(position).get("title").toString());
		holder.tv_date.setText(list.get(position).get("date").toString());
		holder.image_detail.setBackgroundResource(R.drawable.read);
		return convertView;
	}

	class ViewHolder {

		TextView tv_title;// 标题
		TextView tv_date;// 时间
		ImageView image_detail;// 图片

	}
}
