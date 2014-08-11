package com.passionpeople.krtt.vo;

import android.content.Context;

public class Business {
	private String m_szLabel;
	private String m_szData;
	private int m_szData2;

	public Business(Context context, String p_szLabel, String p_szDataFile) {
		m_szLabel = p_szLabel;
		m_szData = p_szDataFile;
		m_szData2 = 1;
	}

	public String getLabel() {
		return m_szLabel;
	}

	public String getData() {
		return m_szData;
	}

	public int getData2() {
		return m_szData2;
	}
}
