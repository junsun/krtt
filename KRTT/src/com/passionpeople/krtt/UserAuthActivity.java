package com.passionpeople.krtt;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.passionpeople.krtt.handlers.UserAuthActivityHandler;
import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.FileManager;
import com.passoinpeople.krtt.Constants.Constants;

public class UserAuthActivity extends Activity implements OnClickListener{
	
	private Button sendEmail;
	private Button sendAuthId;
	private EditText emailTxt;
	private EditText authIdTxt;
	private HttpGetThread httpGetThread;
	private HashMap<String, Object> httpParam;
	private FileManager fileManager;
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);
        
        sendEmail = (Button)findViewById(R.id.user_auth_email);
        sendAuthId = (Button)findViewById(R.id.user_auth_id);
        emailTxt = (EditText)findViewById(R.id.user_auth_email_txt);
        authIdTxt = (EditText)findViewById(R.id.user_auth_id_txt);
        
        sendEmail.setOnClickListener(this);
        sendAuthId.setOnClickListener(this);
        fileManager = new FileManager();
	}

	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.user_auth_email){
			Log.d("###DEBUG####","auth email clicked");

	    	httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_SEND_MAIL, null, UserAuthActivityHandler.getInstance());
			httpGetThread.start();
			
		} else if (v.getId() == R.id.user_auth_id){
			Log.d("###DEBUG####","auth id clicked");

    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_AUTH, httpParam, UserAuthActivityHandler.getInstance());
    		httpGetThread.start();	
			fileManager.writeUserAuth("junsun2005@naver.com", "7485");
		}
		
	}
}
