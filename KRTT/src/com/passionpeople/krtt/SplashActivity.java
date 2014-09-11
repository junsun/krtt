package com.passionpeople.krtt;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;
import com.passionpeople.krtt.handlers.SplashActivityHandler;
import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class SplashActivity extends Activity {
	
	 // Splash screen timer
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
        splashActivityHandler = SplashActivityHandler.getInstance();
        splashActivityHandler.setContext(this);
        
        httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_UPDATE, null, SplashActivityHandler.getInstance());
		httpGetThread.start();	
    }
    
    
}
