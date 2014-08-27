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

import android.os.Message;

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
	private MainActivityHandler mainActivityHandler;
	private UserAuthActivityHandler userAuthActivityHandler;
	private SplashActivityHandler splashActivityHandler;
	Message msg;
	
	public HttpGetThread(int urlType, HashMap<String, Object> param){
		msg = new Message();
		msg.what = urlType;
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_COMPANYLIST:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_COMPANYLIST;	
				break;
	
			case Constants.HTTPGET_GET_AUTH:
				this.url = Constants.HTTPGET_URL_BASE + Constants.HTTPGET_URL_CHECK_AUTH
				+ "?" + Constants.HTTPGET_PARAM_MAIL_TO + "=" + param.get("email").toString()
				+ "&" + Constants.HTTPGET_PARAM_AUTH_ID + "=" + param.get("authId").toString();	
				break;
		}
		
		jsonUtil = new JsonUtil();
		mainActivityHandler = MainActivityHandler.getInstance();
		userAuthActivityHandler = UserAuthActivityHandler.getInstance();
		splashActivityHandler = SplashActivityHandler.getInstance();
	}
	
	public void run() {
		super.run();
		
		switch (msg.what) {
		
		case Constants.HTTPGET_GET_COMPANYLIST:
			try {
				String res = EntityUtils.toString(getHttp(url));
				ArrayList<HashMap<String, Object>> resultList = jsonUtil.Json2QList(res);
				
//				byte[] img;
//				for (HashMap<String, Object> iterator : resultList){
//					img = EntityUtils.toByteArray(getHttp(Constants.HTTPGET_URL_BASE + Constants.HTTPGET_GET_IMAGE + iterator.get("imgUrl")));
//				}

//				for (int i=0; i< resultList.size(); i++){
//					System.out.println(i);
//					img = EntityUtils.toByteArray(getHttp(Constants.HTTPGET_URL_BASE + Constants.HTTPGET_GET_IMAGE + resultList.get(i).get("imgUrl")));
//
//				}
				msg.obj = resultList;
				mainActivityHandler.sendMessage(msg);	
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			case Constants.HTTPGET_GET_AUTH:
				try {
					String res = EntityUtils.toString(getHttp(url));
					Map<String, String> resultMap = jsonUtil.Json2Map(res);
					msg.obj = resultMap;
					splashActivityHandler.sendMessage(msg);	
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
