package com.passionpeople.krtt.handlers;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.passionpeople.krtt.KrttAuthActivity;
import com.passionpeople.krtt.MainActivity;
import com.passionpeople.krtt.SplashActivity;
import com.passionpeople.krtt.UserAuthActivity;
import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.ConfirmDialog;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class SplashActivityHandler extends Handler {
	private volatile static SplashActivityHandler uniqueInstance;
	
	private Context context;
	private FileManager fileManager;
	private HashMap<String, String> userInfo;
	private HttpGetThread httpGetThread;
	private HashMap<String, Object> httpParam;
	private ConfirmDialog confirmDialog;
	
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
			case Constants.HTTPGET_GET_CHECK_UPDATE:
		        fileManager = FileManager.getInstance();
		        userInfo = fileManager.readUserAuth();
		        
		        Map<String, String> httpMap = (Map<String, String>)msg.obj;
		        // application version  
		        String versionName = "";  
		        try {  
		            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
		            versionName = info.versionName;  
		        } catch (NameNotFoundException e) {  
		            Log.d("handleMessage()", "", e);  
		        }  
		        
		        if(httpMap.get("version").equals(versionName)){
		        	new Handler().postDelayed(new Runnable() {
			            @Override
			            public void run() {
			            	
				        if(userInfo.size() > 1){
				        	httpParam = new HashMap<String, Object>();
				    		httpParam.put("email", userInfo.get("email"));
				    		httpParam.put("authId", userInfo.get("authId"));
				    		
				    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_USER_AUTH, httpParam, SplashActivityHandler.getInstance());
				    		httpGetThread.start();	
				        } else {
				                Intent newIntent = new Intent(context, UserAuthActivity.class);
				                newIntent.putExtra("email", userInfo.get("email"));
				                newIntent.putExtra("authId", userInfo.get("authId"));
				                context.startActivity(newIntent);
				                ((Activity)context).finish();
				            }
				           
				        }
			            
			        }, Constants.SPLASH_TIME_OUT);
		        } else {
		        	confirmDialog = new ConfirmDialog(context, this);
		        	confirmDialog.showDialog(Constants.DIALOG_UPDATE_APP_TITLE, Constants.DIALOG_UPDATE_APP_CONTENT);
		        }
				break;
				
			 
			case Constants.DIALOG_UPDATE_APP:
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.passionpeople.krtt"));
				context.startActivity(intent);
				((Activity)context).finish();
				break;
				
		
			case Constants.HTTPGET_GET_CHECK_USER_AUTH:
				Map<String, String> resultUserAuthMap = (Map<String, String>)msg.obj;
				Log.d("###DEBUG####","httpresult : "+resultUserAuthMap);
				
				if(resultUserAuthMap.get("RESULT").equals("true")){
					Log.d("###DEBUG####","TRUE!!!");

					fileManager = FileManager.getInstance();
					userInfo = fileManager.readUserAuth();
					
					httpParam = new HashMap<String, Object>();
		    		httpParam.put("email", userInfo.get("email"));
		    		httpParam.put("authId", "");
		    		
		    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_KRTT_AUTH, httpParam, SplashActivityHandler.getInstance());
		    		httpGetThread.start();	
				
				} else {
	                Intent newIntent = new Intent(context, UserAuthActivity.class);
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				}
				break;
			
			
			case Constants.HTTPGET_GET_CHECK_KRTT_AUTH:
				Map<String, String> resultKrttAuthMap = (Map<String, String>)msg.obj;
				Log.d("###DEBUG####","httpresult : "+resultKrttAuthMap);
				
				fileManager = FileManager.getInstance();
				userInfo = fileManager.readUserAuth();
				
				if(resultKrttAuthMap.get("RESULT").equals("true")){
					Log.d("###DEBUG####","TRUE!!!");
					Intent newIntent = new Intent(context, MainActivity.class);
	                newIntent.putExtra("email", userInfo.get("email"));
	                newIntent.putExtra("authId", userInfo.get("authId"));
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				} else {
	                Intent newIntent = new Intent(context, KrttAuthActivity.class);
	                context.startActivity(newIntent);
	                ((Activity)context).finish();
				}
				break;
		}
		
	}

	

}
