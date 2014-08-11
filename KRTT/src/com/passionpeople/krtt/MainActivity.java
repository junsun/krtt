package com.passionpeople.krtt;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.passionpeople.krtt.vo.Business;

public class MainActivity extends Activity implements AnimationListener {

	private ListView listview;
	private View listHeader;
	private View listFooter;
	DataAdapter adapter;
	ArrayList<Business> alist;
	View menu;
	View app;
	boolean menuOut = false;
	AnimParams animParams = new AnimParams();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		menu = findViewById(R.id.menu);
		app = findViewById(R.id.app);

		ViewUtils.printView("menu", menu);
		ViewUtils.printView("app", app);
		app.findViewById(R.id.BtnSlide).setOnClickListener(new ClickListener());

		listview = (ListView) findViewById(R.id.listView1);
		alist = new ArrayList<Business>();
		adapter = new DataAdapter(this, alist);
		listview.setAdapter(adapter);

		// Header, Footer 积己 棺 殿废
		listHeader = getLayoutInflater().inflate(
				R.layout.listview_header_business, null, false);
		listFooter = getLayoutInflater().inflate(
				R.layout.listview_footer_business, null, false);

		listview.addHeaderView(listHeader);
		listview.addFooterView(listFooter);

		adapter.add(new Business(getApplicationContext(), "模备1",
				"www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "模备2",
				"www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "模备3",
				"www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "模备4",
				"www.google.com"));
		adapter.add(new Business(getApplicationContext(), "模备1",
				"www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "模备2",
				"www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "模备3",
				"www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "模备4",
				"www.google.com"));
		adapter.add(new Business(getApplicationContext(), "模备1",
				"www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "模备2",
				"www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "模备3",
				"www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "模备4",
				"www.google.com"));
		adapter.add(new Business(getApplicationContext(), "模备1",
				"www.areanashop.com"));
		adapter.add(new Business(getApplicationContext(), "模备2",
				"www.naver.com"));
		adapter.add(new Business(getApplicationContext(), "模备3",
				"www.peace-worker.com"));
		adapter.add(new Business(getApplicationContext(), "模备4",
				"www.google.com"));
	}

	private class DataAdapter extends ArrayAdapter<Business> {
		private LayoutInflater mInflater;

		public DataAdapter(Context context, ArrayList<Business> object) {
			super(context, 0, object);
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view = null;
			if (v == null) {
				view = mInflater.inflate(R.layout.listview_item_business, null);
			} else {
				view = v;
			}

			final Business data = this.getItem(position);
			if (data != null) {
				TextView tv = (TextView) view.findViewById(R.id.textView1);
				TextView tv2 = (TextView) view.findViewById(R.id.textView2);
				tv.setText(data.getLabel());
				tv2.setText(data.getData());
				ImageView iv = (ImageView) view.findViewById(R.id.imageView1);
				iv.setImageResource(data.getData2());
			}
			return view;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	void layoutApp(boolean menuOut) {
		System.out.println("layout [" + animParams.left + "," + animParams.top
				+ "," + animParams.right + "," + animParams.bottom + "]");
		app.layout(animParams.left, animParams.top, animParams.right,
				animParams.bottom);
		// Now that we've set the app.layout property we can clear the
		// animation, flicker avoided :)
		app.clearAnimation();

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		System.out.println("onAnimationEnd");
		ViewUtils.printView("menu", menu);
		ViewUtils.printView("app", app);
		menuOut = !menuOut;
		if (!menuOut) {
			menu.setVisibility(View.INVISIBLE);
		}
		layoutApp(menuOut);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		System.out.println("onAnimationRepeat");
	}

	@Override
	public void onAnimationStart(Animation animation) {
		System.out.println("onAnimationStart");
	}

	static class AnimParams {
		int left, right, top, bottom;

		void init(int left, int top, int right, int bottom) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}
	}

	class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			System.out.println("onClick " + new Date());
			MainActivity me = MainActivity.this;
			Context context = me;
			Animation anim;

			int w = app.getMeasuredWidth();
			int h = app.getMeasuredHeight();
			int left = (int) (app.getMeasuredWidth() * 0.3);

			if (!menuOut) {
				// anim = AnimationUtils.loadAnimation(context,
				// R.anim.push_right_out_80);
				anim = new TranslateAnimation(0, left, 0, 0);
				menu.setVisibility(View.VISIBLE);
				animParams.init(left, 0, left + w, h);
			} else {
				// anim = AnimationUtils.loadAnimation(context,
				// R.anim.push_left_in_80);
				anim = new TranslateAnimation(0, -left, 0, 0);
				animParams.init(0, 0, w, h);
			}

			anim.setDuration(400);
			anim.setAnimationListener(me);
			// Tell the animation to stay as it ended (we are going to set the
			// app.layout first than remove this property)
			anim.setFillAfter(true);

			// Only use fillEnabled and fillAfter if we don't call layout
			// ourselves.
			// We need to do the layout ourselves and not use fillEnabled and
			// fillAfter because when the anim is finished
			// although the View appears to have moved, it is actually just a
			// drawing effect and the View hasn't moved.
			// Therefore clicking on the screen where the button appears does
			// not work, but clicking where the View *was* does
			// work.
			// anim.setFillEnabled(true);
			// anim.setFillAfter(true);

			app.startAnimation(anim);
		}
	}

}
