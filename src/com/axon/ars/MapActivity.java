package com.axon.ars;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.R.string;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {
	private static final int REQUEST_CODE = 0;
	private GoogleMap map;
	private static double KARACHI_LAT = 24.9512, KARACHI_LNG = 67.038145;
	private static float CAMERA_ZOOM = 15;
	LocationClient locationClient;
	// private double Longitude;
	// private double Latitude;
	// private Location location;
	

	String excep;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		//myPrefs = getSharedPreferences(filename, 0);

		if (servicesAvailabe()) {
			Toast.makeText(this, "Getting  maps ready!", Toast.LENGTH_SHORT)
					.show();
			setContentView(R.layout.activity_map);
			locationClient = new LocationClient(this, this, this);
			locationClient.connect();

			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		}

		else {
			Toast.makeText(this, "Sorry! maps cannot be loaded",
					Toast.LENGTH_LONG).show();

		}

		// getLocation(Latitude,Longitude,map);

		/*
		 * map.setMapType(GoogleMap.MAP_TYPE_TERRAIN); Latitude = 24.8600;
		 * Longitude = 67.0100; map.addMarker( new MarkerOptions().position(new
		 * LatLng(Latitude, Longitude)) .title("Karachi").draggable(true)
		 * .rotation((float) 360.0)).showInfoWindow();
		 * map.setMyLocationEnabled(true);
		 */
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.mapTypeNormal:
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			Toast.makeText(this, "Normal map mode is selected",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.mapTypeHybird:
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			Toast.makeText(this, "Hybird map mode is selected",
					Toast.LENGTH_SHORT).show();

			break;
		case R.id.mapTypeSatellite:
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			Toast.makeText(this, "Satellite map mode is selected",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.mapTypeTerrain:
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			Toast.makeText(this, "Terrain map mode is selected",
					Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	public boolean servicesAvailabe() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Dialog window = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, REQUEST_CODE);
			window.show();

		} else {
			Toast.makeText(this, "Google Maps are not available",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	// public void getLocation(double lat, double lng, GoogleMap mp) {
	// latitude=location.getLatitude();
	// longitude=location.getLongitude();
	// LatLng positionCoordinates = new LatLng(lat, lng);
	// mp.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	// mp.addMarker(
	// new MarkerOptions().position(positionCoordinates).title(
	// "Current Location")).showInfoWindow();
	// }
	private void currentLocation(float zoom) {
		Location location = locationClient.getLastLocation();
		if (location == null) {
			Toast.makeText(this, "Current location is not available",
					Toast.LENGTH_SHORT).show();
			/*
			 * LatLng latitudeLongitude = new LatLng(location.getLatitude(),
			 * location.getLongitude()); CameraUpdate updateCamera =
			 * CameraUpdateFactory.newLatLngZoom( latitudeLongitude, zoom);
			 * map.animateCamera(updateCamera);
			 */
		} else {
			LatLng latitudeLongitude = new LatLng(location.getLatitude(),
					location.getLongitude());
			CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(
					latitudeLongitude, zoom);
			map.animateCamera(updateCamera);
			Geocoder gc = new Geocoder(this);
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			List<Address> lt = null;
			try {
				lt = gc.getFromLocation(latitude, longitude, 1);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				Toast.makeText(this, "Karachi", Toast.LENGTH_SHORT).show();
			}
			String address = lt.get(0).getLocality().toString();

			MarkerOptions option = new MarkerOptions().title(address)
					.position(new LatLng(latitude, longitude)).snippet(address);
			map.addMarker(option);
		}

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "App is connected with location service",
				Toast.LENGTH_SHORT).show();
		currentLocation(15.0f);
		LocationRequest locationRequest = LocationRequest.create();
		locationRequest.setNumUpdates(10);
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		locationRequest.setInterval(5000);
		locationRequest.setFastestInterval(1000);
		locationClient.requestLocationUpdates(locationRequest, this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		locationClient.removeLocationUpdates(this);
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		// locationClient.removeLocationUpdates(this);
		super.onStop();
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		String msg = "Location " + loc.getLatitude() + "," + loc.getLongitude();
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

	}
}
