package com.passionpeople.krtt.utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
 
public class JsonUtil {
	
	public String map2Json(Map<String, String> paramMap) {
 
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		
		try {
			json = mapper.writeValueAsString(paramMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
 
		return json;
	}
 
	public Map<String, String> Json2Map(String jsonStr) {
		 
		Map<String, String> mapObj = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
 
		try {
 
			// convert JSON string to Map
			mapObj = mapper.readValue(jsonStr, new TypeReference<HashMap<String, String>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return mapObj;
	}
	
	public Map<String, Object> Json2OMap(String jsonStr) {
		 
		Map<String, Object> mapObj = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
 
		try {
 
			// convert JSON string to Map
			mapObj = mapper.readValue(jsonStr, new TypeReference<HashMap<String, Object>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return mapObj;
	}
	
	public ArrayList<HashMap<String, Object>> Json2QList(String jsonStr) {
		 
		ArrayList<HashMap<String, Object>> listObj = new ArrayList<HashMap<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();
 
		try {
			// convert JSON string to Array
			listObj = mapper.readValue(jsonStr, new TypeReference<ArrayList<HashMap<String, Object>>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return listObj;
	}
	
	public ArrayList<String> Json2StrList(String jsonStr) {
		 
		ArrayList<String> listObj = new ArrayList<String>();
		ObjectMapper mapper = new ObjectMapper();
 
		try {
			// convert JSON string to Array
			listObj = mapper.readValue(jsonStr, new TypeReference<String>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return listObj;
	}
}
