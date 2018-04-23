package com.axon.ars;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class NewMapActivity extends FragmentActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, View.OnClickListener,
		LocationListener {

	private GoogleMap Mmap;
	private static final int REQUEST_CODE = 1;
	private static final float DEFAULT_ZOOM = 15;
	private static final double KARACHI_LATITUDE = 24.8600;
	private static final double KARACHI_LONGITUDE = 67.0100;
	boolean isGPSEnabled;
	/*
	 * private Button locationBtn; private EditText locationEt;
	 */
	private Button nextBtn;
	LocationClient lcClient;
	static String filename = "NICPrefs";
	SharedPreferences myPrefs;
	Button submit;
	EditText nic;
	double getLat;
	double getLng;
	String getAddress;
	Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (isServicesAvailable()) {

			setContentView(R.layout.activity_map);
			Toast.makeText(this, "Getting maps ready!", Toast.LENGTH_LONG)
					.show();
			// locationBtn = (Button) findViewById(R.id.locBtn);
			nextBtn = (Button) findViewById(R.id.button1);
			nextBtn.setOnClickListener(this);
			// locationBtn.setOnClickListener(this);
			Mmap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.newMap)).getMap();
			traceLocation(KARACHI_LATITUDE, KARACHI_LONGITUDE);
			lcClient = new LocationClient(this, this, this);
			lcClient.connect();

			// Mmap.setMyLocationEnabled(true);

		} else {
			setContentView(R.layout.ars_form);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflator = getMenuInflater();
		inflator.inflate(R.menu.activity_action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean isServicesAvailable() {
		int isAvailable = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (isAvailable == ConnectionResult.SUCCESS) {
			return true;

		} else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {

			Dialog window = GooglePlayServicesUtil.getErrorDialog(isAvailable,
					this, REQUEST_CODE);
			window.show();

		} else {
			Toast.makeText(this, "Can't connect to Googlen Play services	",
					Toast.LENGTH_SHORT).show();
		}
		return false;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Location services available", Toast.LENGTH_SHORT)
				.show();
		currentLocation();
		LocationRequest cLocationRequest = LocationRequest.create();
		cLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		cLocationRequest.setInterval(3000);
		cLocationRequest.setFastestInterval(1000);
		lcClient.requestLocationUpdates(cLocationRequest, this);

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	private void checkingGPSStatus() {
		LocationManager lm = (LocationManager) this
				.getSystemService(this.LOCATION_SERVICE);
		isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!isGPSEnabled) {
			Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
			intent.putExtra("enabled", true);
			sendBroadcast(intent);
		}

	}

	private void traceLocation(double Lat, double Lng) {

		LatLng latlng = new LatLng(Lat, Lng);
		CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(latlng,
				DEFAULT_ZOOM);
		Mmap.animateCamera(updateCamera);
	}

	private void addMarkers(double lat, double lng, String Add) {
		LatLng currentLocationLatLng = new LatLng(lat, lng);
		MarkerOptions option = new MarkerOptions()
				.position(currentLocationLatLng).title("You are here")
				.snippet(Add)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
		Mmap.addMarker(option);
	}

	public void currentLocation() {
		Location currentLocation = lcClient.getLastLocation();
		if (currentLocation == null) {
			Toast.makeText(this, "current location isn't available",
					Toast.LENGTH_SHORT).show();
		} else {
			LatLng newLatLng = new LatLng(currentLocation.getLatitude(),
					currentLocation.getLongitude());
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					newLatLng, DEFAULT_ZOOM);
			Mmap.animateCamera(cameraUpdate);
			Geocoder gc = new Geocoder(this, Locale.ENGLISH);
			try {
				List<Address> address = gc.getFromLocation(
						currentLocation.getLatitude(),
						currentLocation.getLongitude(), 1);
				if (address != null) {

					Address fetchedAddress = address.get(0);
					String locality = fetchedAddress.getLocality();

					String postal = fetchedAddress.getPostalCode();
					StringBuilder strAddress = new StringBuilder();

					for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
						strAddress.append(fetchedAddress.getAddressLine(i))
								.append("\n");
					}

					Toast.makeText(this, strAddress.toString(),
							Toast.LENGTH_LONG).show();

					getLat = currentLocation.getLatitude();
					getLng = currentLocation.getLongitude();
					getAddress = strAddress.toString();
					DataStorage dataSet = new DataStorage();
					dataSet.setData(getLat, getLng, getAddress);
					addMarkers(getLat, getLng, getAddress);

				} else {
					Toast.makeText(this, "address not found",
							Toast.LENGTH_SHORT).show();
				}

			} catch (Exception ex) {

			}
		}
	}

	private void passingData(double lat, double lng, String address) {
		Bundle dataBundle = new Bundle();
		dataBundle.putString("locationAddress", address);
		dataBundle.putDouble("Latitude", lat);
		dataBundle.putDouble("Longitude", lng);
		Intent i = new Intent(NewMapActivity.this, FormActivity.class);
		i.putExtras(dataBundle);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.button1:
			Intent i = new Intent(NewMapActivity.this, FormActivity.class);
			NewMapActivity.this.startActivity(i);
			break;

		}

	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		String location = "Location:" + loc.getLatitude() + ","
				+ loc.getLongitude();
		traceLocation(loc.getLatitude(), loc.getLongitude());

	}
}
