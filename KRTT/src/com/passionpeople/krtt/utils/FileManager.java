package com.passionpeople.krtt.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.passionpeople.krtt.handlers.MainActivityHandler;
import com.passionpeople.krtt.handlers.UserAuthActivityHandler;

import android.os.Environment;
import android.util.Log;

public class FileManager {

	private final static String rootDirectory = "/krtt";
	private final static String userPropDirStr = "user_prop";
	private final static String userPropStr = "user_prop.dat";

	private volatile static FileManager uniqueInstance;
	public static FileManager getInstance(){
		if (uniqueInstance == null){
			synchronized (MainActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new FileManager();
				}
			}
		}
		
		return uniqueInstance;
	}
	
	
	public FileManager() {
		File rootDir = new File(Environment.getExternalStorageDirectory(), userPropDirStr);
		File userPropDir = new File(Environment.getExternalStorageDirectory() + rootDirectory, userPropDirStr);

		if (!rootDir.exists()) {
			if (!rootDir.mkdirs()) {
				Log.d("[FileManager getDirectory]", "failed to create directory");
			}
		}

		if (!userPropDir.exists()) {
			if (!userPropDir.mkdirs()) {
				Log.d("[FileManager getDirectory]", "failed to create directory");
			}
		}
	}

	public void writeUserAuth(String email, String authId) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(rootDirectory + "/" + userPropDirStr + "/" + userPropStr));
			out.write("email:" + email);
			out.newLine();
			out.write("authId:" + authId);
			out.newLine();
			out.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public HashMap<String, String> readUserAuth() {
		HashMap<String, String> resultMap = new HashMap<String, String>();

		try {
			BufferedReader in = new BufferedReader(new FileReader(rootDirectory + "/" + userPropDirStr + "/" + userPropStr));
			String strLine;
			String[] eachLineStr;
			while ((strLine = in.readLine()) != null) {
				eachLineStr = strLine.split(":");
				resultMap.put(eachLineStr[0], eachLineStr[1]);
			}
			in.close();
		} catch (IOException e) {
			System.err.println(e);
		}
		return resultMap;
	}

}