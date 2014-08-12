package com.passionpeople.krtt;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.passionpeople.krtt.threads.HttpGetThread;
import com.passionpeople.krtt.vo.AnimParams;
import com.passionpeople.krtt.vo.Business;
import com.passionpeople.krtt.vo.BusinessAdapter;

public class MainActivity extends Activity implements AnimationListener, OnClickListener {

	private ListView listview;
	private View listHeader;
	private View listFooter;
	private BusinessAdapter adapter;
	private ArrayList<Business> alist;
	private View aniFixView; 
	private View aniMovView;
	private boolean menuOut = false;
	private AnimParams animParams;
	private HttpGetThread httpGetThread;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		aniFixView = findViewById(R.id.menu);
		aniMovView = findViewById(R.id.app);
		listview = (ListView) findViewById(R.id.listView1);
		listHeader = getLayoutInflater().inflate(R.layout.listview_header_business, null, false);
		listFooter = getLayoutInflater().inflate(R.layout.listview_footer_business, null, false);
		
		alist = new ArrayList<Business>();
		adapter = new BusinessAdapter(this, alist);
		
		ViewUtils.printView("aniFixView", aniFixView);
		ViewUtils.printView("app", aniMovView);
		aniMovView.findViewById(R.id.BtnSlide).setOnClickListener(this);
		
		listview.setAdapter(adapter);
		listview.addHeaderView(listHeader);
		listview.addFooterView(listFooter);

		adapter.add(new Business(getApplicationContext(), "업체1", "www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "업체1", "www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "업체13", "www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "업체14", "www.google.com"));
		adapter.add(new Business(getApplicationContext(), "업체11", "www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "업체1", "www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "업체1", "www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "업체13", "www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "업체14", "www.google.com"));
		adapter.add(new Business(getApplicationContext(), "업체11", "www.areanashop.com"));
	}

	
	/**
	 * Function : Click 리스너
	 */
	@Override
	public void onClick(View v) {
		Animation anim;
		animParams = new AnimParams();
		
		int w = aniMovView.getMeasuredWidth();
		int h = aniMovView.getMeasuredHeight();
		int left = (int) (aniMovView.getMeasuredWidth() * 0.3);

		if (!menuOut) {
			anim = new TranslateAnimation(0, left, 0, 0);
			aniFixView.setVisibility(View.VISIBLE);
			animParams.setParams(left, 0, left + w, h);
		} else {
			anim = new TranslateAnimation(0, -left, 0, 0);
			animParams.setParams(0, 0, w, h);
		}

		anim.setDuration(400);
		anim.setAnimationListener(this);
		anim.setFillAfter(true);
		aniMovView.startAnimation(anim);
		
		httpGetThread = new HttpGetThread("http://54.191.253.51/");
		httpGetThread.start();
		
//		http://54.191.253.51/CHECK_AUTH?MAIL_TO=junsun2005@naver.com&AUTH_ID=7485
//		http://54.191.253.51/SEND_MAIL?MAIL_TO=junsun2005@naver.com
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
		
//		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//		startActivity(intent);
		
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

}
