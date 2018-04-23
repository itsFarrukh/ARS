package com.axon.ars;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class HeatMapsActivity extends Activity {

	private static final float DEFAULT_ZOOM = 15;
	private GoogleMap heatmap;

	private static final double Karachi_Latitude = 24.8600;
	private static final double Karachi_Longitude = 67.0100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_heatmaps);
		Intializers();
		setFocusOnMap(Karachi_Latitude, Karachi_Longitude);


	}

	public void Intializers() {
		heatmap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.accidentMap)).getMap();

	}

	private void setFocusOnMap(double latitude, double longitude) {
		LatLng LL = new LatLng(latitude, longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LL,
				DEFAULT_ZOOM);
		heatmap.animateCamera(cameraUpdate);

	}

}
