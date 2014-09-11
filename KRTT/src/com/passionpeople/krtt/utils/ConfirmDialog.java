package com.passionpeople.krtt.utils;

import java.util.HashMap;

import com.passoinpeople.krtt.Constants.Constants;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.R;

public class ConfirmDialog {
	
	private Context context;
	private Handler handler;
	
	public ConfirmDialog(Context context, Handler handler){
		this.context = context;
		this.handler = handler;
	}

	public void showDialog(String title, String content){
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(content);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	Message msg;
        		msg = new Message();
        		msg.what = Constants.DIALOG_UPDATE_APP;
        		handler.sendMessage(msg);
	        }
		});

		alertDialog.show();
	}
}
