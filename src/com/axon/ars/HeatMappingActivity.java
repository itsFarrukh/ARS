package com.axon.ars;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class HeatMappingActivity extends Activity {
	private static final float DEFAULT_ZOOM = 15;
	private GoogleMap heatmap;
	webServices services = new webServices();
	int redColor = 1;
	private static final double Karachi_Latitude = 24.8600;
	private static final double Karachi_Longitude = 67.0100;
	ArrayList<IncidentInfo> IncidentsInfo = new ArrayList<IncidentInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heat_mapping);
		Intializers();
		if (isNetworkAvailable()) {
			AsyncHeatMaps task = new AsyncHeatMaps();
			task.execute();
			setFocusOnMap(Karachi_Latitude, Karachi_Longitude, 10f);

		} else
			Toast.makeText(this, "Connect to the internet", Toast.LENGTH_SHORT)
					.show();

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private double getDistance(LatLng LatLng1, LatLng LatLng2) {
		double distance = 0;
		Location locationA = new Location("A");
		locationA.setLatitude(LatLng1.latitude);
		locationA.setLongitude(LatLng1.longitude);
		Location locationB = new Location("B");
		locationB.setLatitude(LatLng2.latitude);
		locationB.setLongitude(LatLng2.longitude);
		distance = locationA.distanceTo(locationB);
		return distance;

	}

	public void Intializers() {
		heatmap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.heatMap)).getMap();

	}

	private void setFocusOnMap(double latitude, double longitude) {
		LatLng LL = new LatLng(latitude, longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LL,
				DEFAULT_ZOOM);
		heatmap.animateCamera(cameraUpdate);

	}

	private void setFocusOnMap(double latitude, double longitude, float zoom) {
		LatLng LL = new LatLng(latitude, longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LL, zoom);
		heatmap.animateCamera(cameraUpdate);

	}

	private void drawPolygon(double lat, double lng) {
		LatLng point = new LatLng(lat, lng);
		CircleOptions circleOptions = new CircleOptions().center(point) // set
																		// center
				.radius(5000) // set radius in meters
				.fillColor(Color.RED).strokeColor(Color.RED);

		heatmap.addCircle(circleOptions);
	}

	private class AsyncHeatMaps extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// / Log.i(TAG, "doInBackground");
			IncidentsInfo = services.getData(-1);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			for (int i = 0; i < IncidentsInfo.size(); i++) {
				for (int j = i + 1; j < IncidentsInfo.size(); j++) {
					LatLng l = new LatLng(Double.parseDouble(IncidentsInfo
							.get(i).Latitude), Double.parseDouble(IncidentsInfo
							.get(i).Longitude));
					LatLng ll = new LatLng(Double.parseDouble(IncidentsInfo
							.get(j).Latitude), Double.parseDouble(IncidentsInfo
							.get(j).Longitude));
					double distance = getDistance(l, ll);
					if (distance < (5 * 1000)) {

						drawPolygon(
								Double.parseDouble(IncidentsInfo.get(j).Latitude),
								Double.parseDouble(IncidentsInfo.get(j).Longitude));

					} else {

						drawPolygon(
								Double.parseDouble(IncidentsInfo.get(j).Latitude),
								Double.parseDouble(IncidentsInfo.get(j).Longitude));
					}
				}
			}

		}

		@Override
		protected void onPreExecute() {
			IncidentsInfo.clear();

			/*
			 * Toast.makeText(AccidentActivity.this, msg, Toast.LENGTH_SHORT)
			 * .show();
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
