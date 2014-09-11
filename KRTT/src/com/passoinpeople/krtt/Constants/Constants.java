package com.passoinpeople.krtt.Constants;

public class Constants {

	
	public final static String HTTPGET_URL_BASE = "http://54.191.253.51/";
	public final static String HTTPGET_URL_COMPANYLIST = "CAMPANY_LIST";
	public final static String HTTPGET_URL_IMAGE = "IMG_DOWN?IMG_NM=";
	public final static String HTTPGET_URL_CHECK_AUTH = "CHECK_AUTH";
	public final static String HTTPGET_URL_SEND_MAIL = "SEND_MAIL";
	public final static String HTTPGET_URL_COMPANYLIST_LIKED = "COMPANY_LIKED_LIST";
	public final static String HTTPGET_URL_ADD_LIKE = "COMPANY_LIKED_ADD";
	public final static String HTTPGET_URL_RMV_LIKE = "COMPANY_LIKED_RMV";
	
	public final static String HTTPGET_PARAM_MAIL_TO = "MAIL_TO";
	public final static String HTTPGET_PARAM_AUTH_ID = "AUTH_ID";
	public final static String HTTPGET_PARAM_CP_ID = "CP_ID";
	public final static String HTTPGET_PARAM_EMAIL = "EMAIL";
	
	
//	54.191.253.51/CHECK_AUTH?MAIL_TO=junsun2005@naver.com&AUTH_ID=7485
	
	public final static int HTTPGET_GET_COMPANYLIST = 1;
	public final static int HTTPGET_GET_CHECK_AUTH = 2;
	public final static int HTTPGET_GET_IMAGE = 3;
	public final static int HTTPGET_GET_SEND_MAIL = 4;
	public final static int HTTPGET_GET_ADD_LIKE_COMPANY = 5;
	public final static int HTTPGET_GET_RMV_LIKE_COMPANY = 6;
	public final static int HTTPGET_GET_LIKE_COMPANY_LIST = 7;
	
	
	public final static String SEND_AUTH_ID = "인증번호가 발송되었습니다.";
	public final static String AUTH_SUCCESS = "성공적으로 인증되었습니다.";
	public final static String AUTH_FAILED = "인증번호 혹은 메일주소가 잘못되었습니다.";
	
	
	
	public final static char[] SORT_LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
													'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
													'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
													'Y', 'Z', 'ㄱ', 'ㄴ', 'ㄷ', 'ㄹ', 'ㅁ', 'ㅂ',
													'ㅅ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
	
}
