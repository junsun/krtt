package com.passionpeople.krtt.handlers;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.passionpeople.krtt.MainActivity;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class KrttAuthActivityHandler extends Handler {
	
	private volatile static KrttAuthActivityHandler uniqueInstance;
	private Context context;
	private FileManager fileManager;
	private HashMap<String, String> userInfo;
	private KrttAuthActivityHandler(){}
	
	public static KrttAuthActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (KrttAuthActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new KrttAuthActivityHandler();
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
			case Constants.HTTPGET_GET_CHECK_KRTT_AUTH:

				Map<String, String> resultMap = (Map<String, String>)msg.obj;
				Log.d("###DEBUG####","httpresult : "+resultMap);
				
				if(resultMap.get("RESULT").equals("true")){
					Log.d("###DEBUG####","TRUE!!!");
					
					fileManager = FileManager.getInstance();
					userInfo = fileManager.readUserAuth();
					
					Toast.makeText(context, Constants.AUTH_SUCCESS, Toast.LENGTH_SHORT).show();
					Intent newIntent = new Intent(context, MainActivity.class);
	                newIntent.putExtra("email", userInfo.get("email"));
	                newIntent.putExtra("authId", userInfo.get("authId"));
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				} else {
					Toast.makeText(context, Constants.KRTT_AUTH_FAILED, Toast.LENGTH_LONG).show();
				}
				
			break;
		}
		
	}

}
