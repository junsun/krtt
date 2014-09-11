package com.passionpeople.krtt.threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.passionpeople.krtt.R;
import com.passionpeople.krtt.handlers.MainActivityHandler;
import com.passionpeople.krtt.handlers.SplashActivityHandler;
import com.passionpeople.krtt.handlers.UserAuthActivityHandler;
import com.passionpeople.krtt.utils.JsonUtil;
import com.passionpeople.krtt.vo.Company;
import com.passoinpeople.krtt.Constants.Constants;

public class HttpGetThread extends Thread {

	private String url;
	private JsonUtil jsonUtil;
	private Handler handler;
	Message msg;
	
	public HttpGetThread(int urlType, HashMap<String, Object> param, Handler handler){
		msg = new Message();
		msg.what = urlType;
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_CHECK_UPDATE:
				this.url = Constants.HTTPGET_URL_CHECK_UPDATE;	
				break;
	
			case Constants.HTTPGET_GET_COMPANYLIST:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_COMPANYLIST;	
				break;

			case Constants.HTTPGET_GET_CHECK_AUTH:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_CHECK_AUTH
				+ "?" + Constants.HTTPGET_PARAM_MAIL_TO + "=" + param.get("email").toString()
				+ "&" + Constants.HTTPGET_PARAM_AUTH_ID + "=" + param.get("authId").toString();	
				break;
				
			case Constants.HTTPGET_GET_SEND_MAIL:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_SEND_MAIL
				+ "?" + Constants.HTTPGET_PARAM_MAIL_TO + "=" + param.get("email").toString();	
				break;	
				
			case Constants.HTTPGET_GET_LIKE_COMPANY_LIST:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_COMPANYLIST_LIKED
				+ "?" + Constants.HTTPGET_PARAM_EMAIL + "=" + param.get("email").toString();	
				break;	
				
			case Constants.HTTPGET_GET_ADD_LIKE_COMPANY:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_ADD_LIKE
				+ "?" + Constants.HTTPGET_PARAM_EMAIL + "=" + param.get("email").toString()
				+ "&" + Constants.HTTPGET_PARAM_CP_ID + "=" + param.get("cpId").toString();	
				break;	
				
			case Constants.HTTPGET_GET_RMV_LIKE_COMPANY:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_RMV_LIKE
				+ "?" + Constants.HTTPGET_PARAM_EMAIL + "=" + param.get("email").toString()
				+ "&" + Constants.HTTPGET_PARAM_CP_ID + "=" + param.get("cpId").toString();	
				break;	
		}
		
		jsonUtil = new JsonUtil();
		this.handler = handler;
	}
	
	public void run() {
		super.run();
		
		switch (msg.what) {
			
			case Constants.HTTPGET_GET_CHECK_UPDATE:
				try {
					String res = EntityUtils.toString(getHttp(url));
					Map<String, Object> resultMap = jsonUtil.Json2OMap(res);
					msg.obj = resultMap;
					handler.sendMessage(msg);	
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		
			case Constants.HTTPGET_GET_COMPANYLIST:
				try {
					String res = EntityUtils.toString(getHttp(url));
					ArrayList<HashMap<String, Object>> resultList = jsonUtil.Json2QList(res);
					msg.obj = resultList;
					handler.sendMessage(msg);	
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case Constants.HTTPGET_GET_CHECK_AUTH:
				try {
					String res = EntityUtils.toString(getHttp(url));
					Map<String, String> resultMap = jsonUtil.Json2Map(res);
					msg.obj = resultMap;
					handler.sendMessage(msg);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;
			
			case Constants.HTTPGET_GET_SEND_MAIL:
				try {
					getHttp(url);
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;

			case Constants.HTTPGET_GET_LIKE_COMPANY_LIST:
			case Constants.HTTPGET_GET_ADD_LIKE_COMPANY:
			case Constants.HTTPGET_GET_RMV_LIKE_COMPANY:
				try {
					String res = EntityUtils.toString(getHttp(url));
					ArrayList<HashMap<String, Object>> resultList = jsonUtil.Json2QList(res);
					ArrayList<String> resultStrList = new ArrayList<String>();
					
					for(HashMap<String, Object> iterator : resultList){
						resultStrList.add(iterator.get("CP_ID").toString());
					}
					
					msg.obj = resultStrList;
					handler.sendMessage(msg);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			break;
		}
	}
	
	public HttpEntity getHttp(String url){
		HttpEntity resEntity = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			resEntity = response.getEntity();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resEntity;
	}
	
}
