package com.passionpeople.krtt.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.passionpeople.krtt.utils.FileManager;
import com.passionpeople.krtt.vo.Company;
import com.passionpeople.krtt.vo.CompanyAdapter;
import com.passoinpeople.krtt.Constants.Constants;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class UserAuthActivityHandler extends Handler {
	
	private volatile static UserAuthActivityHandler uniqueInstance;
	private FileManager fileManager;
	private UserAuthActivityHandler(){}
	
	public static UserAuthActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (MainActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new UserAuthActivityHandler();
				}
			}
		}
		
		return uniqueInstance;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_AUTH:

				Map<String, String> resultMap = (Map<String, String>)msg.obj;
				Log.d("###DEBUG####","httpresult : "+resultMap);
				
				if(resultMap.get("RESULT").equals("true")){
					Log.d("###DEBUG####","TRUE!!!");
					fileManager = FileManager.getInstance();
					fileManager.writeUserAuth("junsun2005@naver.com", "7485");
				}
			break;
		}
		
	}

}
