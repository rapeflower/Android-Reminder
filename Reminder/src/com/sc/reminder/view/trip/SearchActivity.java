package com.sc.reminder.view.trip;

import java.util.ArrayList;
import java.util.List;

import com.sc.reminder.R;
import com.sc.reminder.database.dao.TripDao;
import com.sc.reminder.model.trip.Trip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity{
	
	private EditText et_search;
	private ListView lv_searchresult;
	private ProgressBar loadingProgressBar;
	private ImageButton btn_titlebar_left;
	private List<Trip> listTrips;
	private SearchListAdapter searchListAdapter;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			listTrips = (List<Trip>) msg.obj;
			//Log.i("listTrips", listTrips.get(0).getTypes());
			updateUI();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.detail_search);
		
		listTrips = new ArrayList<Trip>();
		searchListAdapter = new SearchListAdapter();
		
		btn_titlebar_left = (ImageButton) findViewById(R.id.btn_titlebar_left);		
		et_search = (EditText) findViewById(R.id.et_search);
		et_search.addTextChangedListener(new SearchTextWatcher());
		lv_searchresult =(ListView) findViewById(R.id.lv_searchresult);
		lv_searchresult.setAdapter(searchListAdapter);
		loadingProgressBar = (ProgressBar) findViewById(R.id.loadingprogressbar);
		lv_searchresult.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent(SearchActivity.this, TripDetail.class);
				intent.putExtra("mid", listTrips.get(arg2).getMid());
				startActivity(intent);
				
			}
		});
		
		btn_titlebar_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	
	private void updateUI(){
		loadingProgressBar.setVisibility(View.INVISIBLE);
		searchListAdapter.notifyDataSetChanged();
		if(listTrips.size() == 0){
			Toast.makeText(this, "差无相关事件", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	private class ViewCache{
		public ImageView imageView;
		public TextView tv_detailType;
		public TextView tv_startTime;
	}
	
	private class SearchListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listTrips.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return listTrips.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View view, ViewGroup arg2) {
			
			ViewCache viewCache = null;
			
			if(view == null){
				viewCache = new ViewCache();
				view = getLayoutInflater().inflate(R.layout.detail_search_list_item, null);
				viewCache.imageView = (ImageView) view.findViewById(R.id.detail_type_image);
				viewCache.tv_detailType = (TextView) view.findViewById(R.id.detail_type);
				viewCache.tv_startTime = (TextView) view.findViewById(R.id.detail_start_time);
				
				view.setTag(viewCache);
			}else{
				viewCache = (ViewCache) view.getTag();
			}
			
			
			switch (Integer.parseInt(listTrips.get(arg0).getTypes())) {
			case 0:
				viewCache.imageView.setImageResource(R.drawable.trip_type0);
				break;
			case 1:
				viewCache.imageView.setImageResource(R.drawable.trip_type1);
				break;
			case 2:
				viewCache.imageView.setImageResource(R.drawable.trip_type2);
				break;
			case 3:
				viewCache.imageView.setImageResource(R.drawable.trip_type3);
				break;
			case 4:
				viewCache.imageView.setImageResource(R.drawable.trip_type4);
				break;
			case 5:
				viewCache.imageView.setImageResource(R.drawable.trip_type5);
				break;
			case 6:
				viewCache.imageView.setImageResource(R.drawable.trip_type6);
				break;
			case 7:
				viewCache.imageView.setImageResource(R.drawable.trip_type7);
				break;
			}
			
			//viewCache.imageView.setImageResource(R.drawable.trip_type0);
			viewCache.tv_detailType.setText(listTrips.get(arg0).getTitle());
			viewCache.tv_startTime.setText(listTrips.get(arg0).getStarttime());
			

			return view;
		}
		
	}
	
	private class SearchTextWatcher implements TextWatcher{

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			String string = arg0.toString();
			//search(string);
			loadingProgressBar.setVisibility(View.VISIBLE);
			new SearchThread(string).start();
			Log.i("afterTextChanged", string);
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			Log.i("beforeTextChanged", arg0.toString());
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			Log.i("onTextChanged", arg0.toString());
		}
		
	}
	
	private class SearchThread extends Thread{
		private String string;
		private ArrayList<Trip> trips = new ArrayList<Trip>();
		
		public SearchThread(String s) {
			this.string = s;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			TripDao t = new TripDao(SearchActivity.this);
			trips = t.queryByTitle(string);
			Message message = new Message();
			message.obj = trips;
			handler.sendMessage(message);
			Log.i("fgdsgdf", string);
		}
		
	}
	
	
}
