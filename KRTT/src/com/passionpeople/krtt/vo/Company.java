package com.passionpeople.krtt.vo;

import android.content.Context;

public class Company {
	private String cpId;
	private String cpNm;
	private String url;
	private int imgId;

	public Company(Context context, String cpId, String cpNm, String url, int imgId) {
		this.cpId = cpId;
		this.cpNm = cpNm;
		this.url = url;
		this.imgId = imgId;
	}

	public String getCpId(){
		return cpId;
	}
	
	public String getCpNm() {
		return cpNm;
	}

	public String getUrl() {
		return url;
	}

	public int getImgId() {
		return imgId;
	}
}
