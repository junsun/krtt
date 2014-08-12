package com.passionpeople.krtt.vo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.passionpeople.krtt.R;

public class BusinessAdapter  extends ArrayAdapter<Business> {
	private LayoutInflater mInflater;

	public BusinessAdapter(Context context, ArrayList<Business> object) {
		super(context, 0, object);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		View view = null;
		if (v == null) {
			view = mInflater.inflate(R.layout.listview_item_business, null);
		} else {
			view = v;
		}
		
		final Business data = this.getItem(position);
		if (data != null) {
			TextView tv = (TextView) view.findViewById(R.id.textView1);
			TextView tv2 = (TextView) view.findViewById(R.id.textView2);
			tv.setText(data.getLabel());
			tv2.setText(data.getData());
			ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
			iv.setImageResource(data.getData2());
		}
		return view;
	}
}
