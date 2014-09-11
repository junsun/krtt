package com.passionpeople.krtt.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.passionpeople.krtt.MainActivity;
import com.passionpeople.krtt.UserAuthActivity;
import com.passionpeople.krtt.utils.FileManager;
import com.passionpeople.krtt.vo.Company;
import com.passionpeople.krtt.vo.CompanyAdapter;
import com.passoinpeople.krtt.Constants.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class UserAuthActivityHandler extends Handler {
	
	private volatile static UserAuthActivityHandler uniqueInstance;
	private Context context;
	private FileManager fileManager;
	private HashMap<String, String> userInfo;
	private UserAuthActivityHandler(){}
	
	public static UserAuthActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (UserAuthActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new UserAuthActivityHandler();
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
					Toast.makeText(context, Constants.AUTH_FAILED, Toast.LENGTH_LONG).show();
				}
				
			break;
		}
		
	}

}
