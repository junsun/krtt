package com.passionpeople.krtt;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.passionpeople.krtt.handlers.KrttAuthActivityHandler;
import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class KrttAuthActivity extends Activity implements OnClickListener{
	
	private Button krttAuthId;
	private EditText krttAuthTxt;
	private HttpGetThread httpGetThread;
	private HashMap<String, Object> httpParam;
	private HashMap<String, String> userInfo;
	private FileManager fileManager;
	private KrttAuthActivityHandler krttAuthActivityHandler;
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krtt_auth);
        
        krttAuthId = (Button)findViewById(R.id.krtt_auth_id);
        krttAuthTxt = (EditText)findViewById(R.id.krtt_auth_id_txt);
        
        krttAuthActivityHandler = KrttAuthActivityHandler.getInstance();
        krttAuthActivityHandler.setContext(this);
        
        krttAuthId.setOnClickListener(this);
        fileManager = FileManager.getInstance();
	}

	
	/**
	 * Function : onClick 리스너
	 */
	@Override
	public void onClick(View v) {
		httpParam = new HashMap<String, Object>();
		fileManager = FileManager.getInstance();
		userInfo = fileManager.readUserAuth();

		if(v.getId() == R.id.krtt_auth_id){
			httpParam.put("email", userInfo.get("email"));
			httpParam.put("authId", krttAuthTxt.getText());
			
    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_KRTT_AUTH, httpParam, krttAuthActivityHandler);
    		httpGetThread.start();
		} else if (v.getId() == R.id.user_auth_id){
    			
		}
		
	}


}
