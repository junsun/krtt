package com.passionpeople.krtt.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.vo.Company;
import com.passionpeople.krtt.vo.CompanyAdapter;
import com.passoinpeople.krtt.Constants.Constants;

public class MainActivityHandler extends Handler {
	
	private volatile static MainActivityHandler uniqueInstance;
	private Context context;
	private CompanyAdapter adapter;
	private ListView listview;
	
	private MainActivityHandler(){}
	
	public static MainActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (MainActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new MainActivityHandler();
				}
			}
		}
		
		return uniqueInstance;
	}
	
	public void setContext(Context context){
		this.context = context;
	}

	public void setCompanyAdapter(CompanyAdapter adapter){
		this.adapter = adapter;
	}

	public void setListView(ListView listview){
		this.listview = listview;
	}
	
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_COMPANYLIST:
				
				ArrayList<HashMap<String, Object>> resultMap = (ArrayList<HashMap<String, Object>>)msg.obj;
				int imgId;
				for (HashMap<String, Object> iterator : resultMap){
					imgId = context.getResources().getIdentifier("cpn_" + iterator.get("cpId").toString(), "drawable", context.getPackageName());
					adapter.add(new Company(context, iterator.get("cpId").toString(), iterator.get("cpNm").toString(), iterator.get("url").toString(), imgId));
				}
				
				break;
		}
		
		listview.setVisibility(View.VISIBLE);
	}

}
