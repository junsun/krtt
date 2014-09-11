package com.passionpeople.krtt;

import java.util.HashMap;

import com.passionpeople.krtt.handlers.MainActivityHandler;
import com.passionpeople.krtt.handlers.SplashActivityHandler;
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
	private SplashActivityHandler splashActivityHandler;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        fileManager = FileManager.getInstance();
        userInfo = fileManager.readUserAuth();
        
        splashActivityHandler = SplashActivityHandler.getInstance();
        splashActivityHandler.setContext(this);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            	
	        if(userInfo.size() > 1){
	        	httpParam = new HashMap<String, Object>();
	    		httpParam.put("email", userInfo.get("email"));
	    		httpParam.put("authId", userInfo.get("authId"));
	    		
	    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_AUTH, httpParam, SplashActivityHandler.getInstance());
	    		httpGetThread.start();	
	        } else {
	                Intent newIntent = new Intent(SplashActivity.this, UserAuthActivity.class);
	                newIntent.putExtra("email", userInfo.get("email"));
	                newIntent.putExtra("authId", userInfo.get("authId"));
	                startActivity(newIntent);
	                finish();
	            }
	           
	        }
            
        }, SPLASH_TIME_OUT);
    }
}
