package com.passionpeople.krtt;

import java.util.HashMap;

import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {
	
	 // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    private FileManager fileManager;
	private HttpGetThread httpGetThread;
	private HashMap<String, Object> httpParam;
	private boolean authrizedUser = false;
	private HashMap<String, String> userInfo;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fileManager = new FileManager();
        userInfo = fileManager.readUserAuth();
        
        if(userInfo.size() > 1){
        	httpParam = new HashMap<String, Object>();
    		httpParam.put("email", userInfo.get("email"));
    		httpParam.put("authId", userInfo.get("authId"));
    		
    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_AUTH, httpParam);
    		httpGetThread.start();	
    		Log.d("###DEBUG####",fileManager.readUserAuth().toString());
        } else {
            new Handler().postDelayed(new Runnable() {
            	 
                @Override
                public void run() {
                    Intent newIntent = new Intent(SplashActivity.this, UserAuthActivity.class);
                    startActivity(newIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        
        
    }
}
