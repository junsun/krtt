package com.passionpeople.krtt.vo;

import java.util.ArrayList;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.passionpeople.krtt.R;
import com.passionpeople.krtt.handlers.MainActivityHandler;
import com.passoinpeople.krtt.Constants.Constants;

public class CompanyAdapter extends ArrayAdapter<Company> {
	private LayoutInflater mInflater;
	private Context context;
	private ArrayList<String> clickedCpId;

	public CompanyAdapter(Context context, ArrayList<Company> object) {
		super(context, 0, object);
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		clickedCpId = new ArrayList<String>();
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		
		View view = null;
		if (v == null) {
			view = mInflater.inflate(R.layout.listview_item, null);
		} else {
			view = v;
		}

		final Company data = this.getItem(position);
		if (data != null) {
			
			TextView tv = (TextView) view.findViewById(R.id.textView1);
			TextView tv2 = (TextView) view.findViewById(R.id.textView2);
			tv.setText(data.getCpNm());
			tv2.setText(data.getUrl());
			ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
			if(data.getImgId() != 0){
				iv.setImageResource(data.getImgId());
			} else {
				iv.setImageResource(R.drawable.ic_default);
			}
			RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.layout_like);
			relativeLayout.setTag(data.getCpId());
			
			if(clickedCpId.contains(data.getCpId())){
				relativeLayout.setBackgroundResource(R.layout.listview_item_circle_darkgray);
			} else {
				relativeLayout.setBackgroundResource(R.layout.listview_item_circle);
			}
			
			relativeLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(clickedCpId.contains(v.getTag().toString())){
						v.setBackgroundResource(R.layout.listview_item_circle);
						clickedCpId.remove(v.getTag().toString());
					} else {
						v.setBackgroundResource(R.layout.listview_item_circle_darkgray);
						clickedCpId.add(v.getTag().toString());
					}
				}
			});

		}
		return view;
	}

}

