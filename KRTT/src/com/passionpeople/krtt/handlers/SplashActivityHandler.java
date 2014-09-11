package com.passionpeople.krtt.handlers;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.passionpeople.krtt.MainActivity;
import com.passionpeople.krtt.UserAuthActivity;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class SplashActivityHandler extends Handler {
	private volatile static SplashActivityHandler uniqueInstance;
	private Context context;
	private FileManager fileManager;
	private HashMap<String, String> userInfo;
	private SplashActivityHandler(){}
	
	public static SplashActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (SplashActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new SplashActivityHandler();
				}
			}
		}
		
		return uniqueInstance;
	}
	
	public void setContext(Context context){
		this.context = context;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_CHECK_AUTH:

				Map<String, String> resultMap = (Map<String, String>)msg.obj;
				Log.d("###DEBUG####","httpresult : "+resultMap);
				
				fileManager = FileManager.getInstance();
				userInfo = fileManager.readUserAuth();
				
				if(resultMap.get("RESULT").equals("true")){
					Log.d("###DEBUG####","TRUE!!!");
					Intent newIntent = new Intent(context, MainActivity.class);
	                newIntent.putExtra("email", userInfo.get("email"));
	                newIntent.putExtra("authId", userInfo.get("authId"));
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				} else {
	                Intent newIntent = new Intent(context, UserAuthActivity.class);
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				}
			break;
		}
		
	}

	

}
