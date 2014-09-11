package com.passionpeople.krtt;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.passionpeople.krtt.handlers.MainActivityHandler;
import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.utils.SoundSearcher;
import com.passionpeople.krtt.vo.AnimParams;
import com.passionpeople.krtt.vo.Company;
import com.passionpeople.krtt.vo.CompanyAdapter;
import com.passoinpeople.krtt.Constants.Constants;

public class MainActivity extends Activity implements AnimationListener, OnClickListener, OnTouchListener, OnItemClickListener {

	private ListView listview;
	private View listHeader;
	private View listFooter;
	private CompanyAdapter adapter;
	private ArrayList<Company> companyList;
	private View aniFixView; 
	private View aniMovView;
	private TextView sortLike;
	private TextView sortAll;
	private LinearLayout sortletters;
	
	private boolean menuOut = false;
	private AnimParams animParams;
	private HttpGetThread httpGetThread;
	private HashMap<String, Object> httpParam;
	private MainActivityHandler mainActivityHandler;

	private String email;
	private String authId;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mainActivityHandler = MainActivityHandler.getInstance();
		
		Intent intent = getIntent();
		email = intent.getExtras().getString("email");
		authId = intent.getExtras().getString("authId");
		
		aniFixView = findViewById(R.id.menu);
		aniMovView = findViewById(R.id.app);
		sortLike = (TextView)findViewById(R.id.user_auth_sort_like);
		sortAll = (TextView)findViewById(R.id.user_auth_sort_name);
		listview = (ListView) findViewById(R.id.listView1);
		listview.setOnItemClickListener(this);
		listHeader = getLayoutInflater().inflate(R.layout.listview_header_company, null, false);
		listFooter = getLayoutInflater().inflate(R.layout.listview_footer_company, null, false);
		
		sortletters = (LinearLayout)findViewById(R.id.sortletter_area);
		sortletters.setOnTouchListener(this); 

		companyList = new ArrayList<Company>();
		adapter = new CompanyAdapter(this, companyList);
		adapter.setEmail(email);
		adapter.setAuthId(authId);
		mainActivityHandler.setContext(this);
		mainActivityHandler.setCompanyAdapter(adapter);
		mainActivityHandler.setListView(listview);
		
		ViewUtils.printView("aniFixView", aniFixView);
		ViewUtils.printView("app", aniMovView);
		aniMovView.findViewById(R.id.BtnSlide).setOnClickListener(this);
		sortLike.setOnClickListener(this);
		sortAll.setOnClickListener(this);
		
		listview.setAdapter(adapter);
		listview.addHeaderView(listHeader);
		listview.addFooterView(listFooter);
		
		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_COMPANYLIST, null, MainActivityHandler.getInstance());
		httpGetThread.start();
		
		
		httpParam = new HashMap<String, Object>();
		httpParam.put("email", email);
		httpGetThread = new HttpGetThread(Constants.HTTPGET_GET_LIKE_COMPANY_LIST, httpParam, MainActivityHandler.getInstance());
		httpGetThread.start();
	}

	
	/**
	 * Function : onClick 리스너
	 */
	@Override
	public void onClick(View v) {

		/**
		 * Animation 이동 부분, 현재 기능 disabled
		 */
//		Animation anim;
//		animParams = new AnimParams();
//		 
//		int w = aniMovView.getMeasuredWidth();
//		int h = aniMovView.getMeasuredHeight();
//		int left = (int) (aniMovView.getMeasuredWidth() * 0.3);
//
//		if (!menuOut) {
//			anim = new TranslateAnimation(0, left, 0, 0);
//			aniFixView.setVisibility(View.VISIBLE);
//			animParams.setParams(left, 0, left + w, h);
//		} else {
//			anim = new TranslateAnimation(0, -left, 0, 0);
//			animParams.setParams(0, 0, w, h);
//		}
//
//		anim.setDuration(400);
//		anim.setAnimationListener(this);
//		anim.setFillAfter(true);
//		aniMovView.startAnimation(anim);
		
		if(v.getId() == R.id.user_auth_sort_like){
			sortLike.setTypeface(null, Typeface.BOLD);
			sortLike.setTextColor(getResources().getColor(R.color.click_dark_gray));
			sortAll.setTypeface(null, Typeface.NORMAL);
			sortAll.setTextColor(getResources().getColor(R.color.click_medium_gray));
			companyList.clear();
			for(Company iterator: mainActivityHandler.getCompanyList()){
				if(adapter.getLikeCompanyList().contains(iterator.getCpId())){
					companyList.add(iterator);
				}
			}
			mainActivityHandler.setSortLettersIndex();
			adapter.notifyDataSetChanged();
			
		} else if(v.getId() == R.id.user_auth_sort_name){
			sortLike.setTypeface(null, Typeface.NORMAL);
			sortLike.setTextColor(getResources().getColor(R.color.click_medium_gray));
			sortAll.setTypeface(null, Typeface.BOLD);
			sortAll.setTextColor(getResources().getColor(R.color.click_dark_gray));
			companyList.clear();
			for(Company iterator: mainActivityHandler.getCompanyList()){
				companyList.add(iterator);
			}
			mainActivityHandler.setSortLettersIndex();
			adapter.notifyDataSetChanged();
		}  
	}
	
	
	
	
	
	/**
	 * Function : List Click 리스너
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Company clickedCp = (Company)parent.getAdapter().getItem(position);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+clickedCp.getUrl()));
		startActivity(intent);
		
	}
    
	/**
	 * Function : 메뉴 핸들러 Click 리스너
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		System.out.println("test");
		return true;
	}

	
	/**
	 * Function : 에니메이션 삭제
	 */
	void layoutApp(boolean menuOut) {
//		System.out.println("layout [" + animParams.left + "," + animParams.top + "," + animParams.right + "," + animParams.bottom + "]");
		aniMovView.layout(animParams.getLeft(), animParams.getTop(), animParams.getRight(), animParams.getBottom());
		aniMovView.clearAnimation();
	}
	

	/**
	 * Function : 에니메이션 마침
	 */
	@Override
	public void onAnimationEnd(Animation animation) {
		System.out.println("onAnimationEnd");
		ViewUtils.printView("menu", aniFixView);
		ViewUtils.printView("app", aniMovView);
		menuOut = !menuOut;
		if (!menuOut) {
			aniFixView.setVisibility(View.INVISIBLE);
		}
		layoutApp(menuOut);
	}

	
	/**
	 * Function : 
	 */
	@Override
	public void onAnimationRepeat(Animation animation) {
		System.out.println("onAnimationRepeat");
	}

	
	/**
	 * Function : 에니메이션 시작 콜백함수
	 */
	@Override
	public void onAnimationStart(Animation animation) {
		System.out.println("onAnimationStart");
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
//		Log.d("ontouch", ""+(event.getY()/v.getHeight()*100));

		int id = v.getId();
		if(id == R.id.sortletter_area){
			
			if((event.getY()/v.getHeight()*100) < 5){
				listview.setSelection(0);
				
			} else if((event.getY()/v.getHeight()*100) < 10){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('A'));
				
			} else if((event.getY()/v.getHeight()*100) < 15){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('G'));
				
			} else if((event.getY()/v.getHeight()*100) < 20){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('N'));
				
			} else if((event.getY()/v.getHeight()*100) < 25){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('T'));
				
			} else if((event.getY()/v.getHeight()*100) < 30){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('Z'));
				
			} else if((event.getY()/v.getHeight()*100) < 35){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㄱ'));
				
			} else if((event.getY()/v.getHeight()*100) < 40){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㄴ'));
				
			} else if((event.getY()/v.getHeight()*100) < 45){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㄷ'));
				
			} else if((event.getY()/v.getHeight()*100) < 50){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㄹ'));
				
			} else if((event.getY()/v.getHeight()*100) < 55){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅁ'));
				
			} else if((event.getY()/v.getHeight()*100) < 60){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅂ'));
				
			} else if((event.getY()/v.getHeight()*100) < 65){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅅ'));
				
			} else if((event.getY()/v.getHeight()*100) < 70){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅇ'));
				
			} else if((event.getY()/v.getHeight()*100) < 75){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅈ'));
				
			} else if((event.getY()/v.getHeight()*100) < 80){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅊ'));
				
			} else if((event.getY()/v.getHeight()*100) < 85){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅋ'));
				
			} else if((event.getY()/v.getHeight()*100) < 90){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅌ'));
				
			} else if((event.getY()/v.getHeight()*100) < 95){
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅍ'));
				
			} else {
				listview.setSelection(mainActivityHandler.getSortLettersIndex('ㅎ'));
				
			} 
		}
		
		return true;
	}
	
}
