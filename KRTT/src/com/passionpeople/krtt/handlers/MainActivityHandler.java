package com.passionpeople.krtt.handlers;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.util.EntityUtils;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.SoundSearcher;
import com.passionpeople.krtt.vo.Company;
import com.passionpeople.krtt.vo.CompanyAdapter;
import com.passoinpeople.krtt.Constants.Constants;

public class MainActivityHandler extends Handler {
	
	private volatile static MainActivityHandler uniqueInstance;
	private Context context;
	private CompanyAdapter adapter;
	private ListView listview;
	private static ArrayList<Company> companyList;
	private static HashMap<Character, Integer> sortlettersIndex;
	private static SoundSearcher soundSearcher;
	
	private MainActivityHandler(){}
	
	public static MainActivityHandler getInstance(){
		if (uniqueInstance == null){
			synchronized (MainActivityHandler.class) {
				if (uniqueInstance == null){
					uniqueInstance = new MainActivityHandler();
					companyList = new ArrayList<Company>();
					sortlettersIndex = new HashMap<Character, Integer>();
					soundSearcher = new SoundSearcher();
				}
			}
		}
		
		return uniqueInstance;
	}
	
	public void setContext(Context context){
		this.context = context;
	}

	public void setCompanyAdapter(CompanyAdapter adapter){
		this.adapter = adapter;
	}

	public void setListView(ListView listview){
		this.listview = listview;
	}
	
	public ArrayList<Company> getCompanyList(){
		return companyList;
	}
	
	
	public void setSortLettersIndex(){
		char soundChar=' ';
		sortlettersIndex.clear();
		for(int i=0; i<companyList.size(); i++){
			
			if(soundSearcher.isHangul(companyList.get(i).getCpNm().charAt(0))){
				soundChar = soundSearcher.getInitialSound(companyList.get(i).getCpNm().charAt(0));
			} else {
				soundChar = companyList.get(i).getCpNm().toUpperCase().charAt(0);
			}

			if(!sortlettersIndex.containsKey(soundChar)){
				sortlettersIndex.put(soundChar, i);
			}
		}
	}
	
	
	public int getSortLettersIndex(Character letter){
//		Log.d("getSortLettersIndex", sortlettersIndex.toString());
		char sortLetter;
		int startIndex = 0;
		
		for(int i=0; i<Constants.SORT_LETTERS.length; i++){
			if(Constants.SORT_LETTERS[i] == letter)
				startIndex = i;
		}
		
		for(int i=startIndex; i<Constants.SORT_LETTERS.length; i++){
			sortLetter = Constants.SORT_LETTERS[i];
					
			if(sortlettersIndex.containsKey(sortLetter)){
				return sortlettersIndex.get(sortLetter) <= companyList.size() ? sortlettersIndex.get(sortLetter)+1 : sortlettersIndex.get(sortLetter);				
			}
		}
		
		return 0;
	}
	
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		switch (msg.what) {
			case Constants.HTTPGET_GET_COMPANYLIST:
				companyList.clear();
				
				ArrayList<HashMap<String, Object>> resultMap = (ArrayList<HashMap<String, Object>>)msg.obj;
				int imgId;
				for (HashMap<String, Object> iterator : resultMap){
					imgId = context.getResources().getIdentifier("cpn_" + iterator.get("cpId").toString(), "drawable", context.getPackageName());
					adapter.add(new Company(context, iterator.get("cpId").toString(), iterator.get("cpNm").toString(), iterator.get("url").toString(), imgId));
					companyList.add(new Company(context, iterator.get("cpId").toString(), iterator.get("cpNm").toString(), iterator.get("url").toString(), imgId));
				}
				
				setSortLettersIndex();
				break;
				
			case Constants.HTTPGET_GET_LIKE_COMPANY_LIST:
			case Constants.HTTPGET_GET_ADD_LIKE_COMPANY:
			case Constants.HTTPGET_GET_RMV_LIKE_COMPANY:
				adapter.setLikeCompanyList((ArrayList<String>)msg.obj);			
				break;
		}
		
		listview.setVisibility(View.VISIBLE);
	}

}
