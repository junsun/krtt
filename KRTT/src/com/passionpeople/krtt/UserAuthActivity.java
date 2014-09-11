package com.passionpeople.krtt;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
	private UserAuthActivityHandler userAuthActivityHandler;
	
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);
        
        sendEmail = (Button)findViewById(R.id.user_auth_email);
        sendAuthId = (Button)findViewById(R.id.user_auth_id);
        emailTxt = (EditText)findViewById(R.id.user_auth_email_txt);
        authIdTxt = (EditText)findViewById(R.id.user_auth_id_txt);
        
        userAuthActivityHandler = UserAuthActivityHandler.getInstance();
        userAuthActivityHandler.setContext(this);
        
        sendEmail.setOnClickListener(this);
        sendAuthId.setOnClickListener(this);
        fileManager = FileManager.getInstance();
	}

	
	/**
	 * Function : onClick 리스너
	 */
	@Override
	public void onClick(View v) {
		httpParam = new HashMap<String, Object>();

		if(v.getId() == R.id.user_auth_email){
			httpParam.put("email", emailTxt.getText());
    		
	    	httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_SEND_MAIL, httpParam, userAuthActivityHandler);
			httpGetThread.start();
			
			Toast.makeText(getApplicationContext(), Constants.SEND_AUTH_ID, Toast.LENGTH_LONG).show();
		} else if (v.getId() == R.id.user_auth_id){
    		httpParam.put("email", emailTxt.getText());
    		httpParam.put("authId", authIdTxt.getText());
			fileManager.writeUserAuth(emailTxt.getText().toString(), authIdTxt.getText().toString());
			
    		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_CHECK_AUTH, httpParam, userAuthActivityHandler);
    		httpGetThread.start();	
		}
		
	}
}
