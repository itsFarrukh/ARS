package com.axon.ars;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rssPackage.LazyAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class RSSActivity extends Activity {

	ListView l;
	List headlines;
	List links;
	webServices service = new webServices();
	ArrayList<Feeds> feedList = new ArrayList<Feeds>();
	ArrayList<HashMap<String, String>> ifieldList = new ArrayList<HashMap<String, String>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss);

		l = (ListView) findViewById(R.id.list);
		/*
		 * // Binding data ArrayAdapter adapter = new ArrayAdapter(this,
		 * android.R.layout.simple_list_item_1, headlines);
		 */
		AsyncCallWS obj = new AsyncCallWS();
		obj.execute();

	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// / Log.i(TAG, "doInBackground");
			feedList = service.getRss();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			
			
			for(Feeds f:feedList)
			 {
					HashMap<String, String> map = new HashMap<String, String>();
					
					map.put("title", ""+f.Title);
					map.put("desc", f.Description);
					
//					map.put(KEY_THUMB_URL, "http://api.androidhive.info/music/images/adele.png");
					ifieldList.add(map);

			        
			 }
		//	list=(ListView)v.findViewById(R.id.clist);
			
			// Getting adapter by passing xml data ArrayList
	

			LazyAdapter  adapter=new LazyAdapter(RSSActivity.this, ifieldList);        
	       l.setAdapter(adapter);
			/*
			 * Log.i(TAG, "onPostExecute"); // response.setText(ans);
			 * Toast.makeText(FormActivity.this, ans,
			 * Toast.LENGTH_SHORT).show();
			 */
		}

		@Override
		protected void onPreExecute() {
			feedList.clear();
			/*
			 * Log.i(TAG, "onPreExecute"); Toast.makeText(FormActivity.this,
			 * "Sending Report..", Toast.LENGTH_SHORT).show();
			 */
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			/*
			 * Log.i(TAG, "onProgressUpdate"); Toast.makeText(FormActivity.this,
			 * "wait..", Toast.LENGTH_SHORT) .show();
			 */
		}

	}

}
