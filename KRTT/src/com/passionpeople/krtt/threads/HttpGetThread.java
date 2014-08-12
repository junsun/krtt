package com.passionpeople.krtt.threads;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpGetThread extends Thread {

	private String url;
	
	public HttpGetThread(String url){
		this.url = url;
	}
	
	public void run() {
		super.run();
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			HttpResponse response = client.execute(get);
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				String res = EntityUtils.toString(resEntity);
				System.out.println(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
