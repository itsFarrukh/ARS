package com.axon.ars;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.R.string;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AccidentActivity extends Activity {
	String msg;

	protected static final double Baldia_Latitude = 24.9244;
	protected static final double Baldia_Longitude = 66.9736;
	protected static final double Bin_Qasim_Latitude = 24.8348034;
	protected static final double Bin_Qasim_Longitude = 67.35629740000002;
	private static final float DEFAULT_ZOOM = 15;
	protected static final double Gadap_Town_Latitude = 25.0508;
	protected static final double Gadap_Town_Longitude = 67.0226;
	protected static final double Gulberg_Town_Latitude = 24.9369;
	protected static final double Gulberg_Town_Longitude = 67.0759;
	protected static final double Jamshad_Town_Latitude = 24.857782800000000000;
	protected static final double Jamshad_Town_Longitude = 67.057144500000050000;
	protected static final double Kemari_Town_Latitude = 24.8183;
	protected static final double Kemari_Town_Longitude = 66.9836;
	protected static final double Korangi_Town_Latitude = 24.832234300000000000;
	protected static final double Korangi_Town_Longitude = 67.129866999999990000;
	protected static final double Liaquatabad_Town_Latitude = 24.9002;
	protected static final double Liaquatabad_Town_Longitude = 67.0446;
	protected static final double Lyari_Town_Latitude = 24.8722;
	protected static final double Longitude_Town_Longitude = 66.9922;
	protected static final double Malir_Town_Latitude = 24.8931;
	protected static final double Malir_Town_Longitude = 67.1953;
	protected static final double New_Karachi_Town_Latitude = 24.9936;
	protected static final double New_Karachi_Town_Longitude = 67.0653;
	protected static final double North_Nazimabad_Town_Latitude = 24.9426;
	protected static final double North_Nazimabad_Town_Longitude = 67.0474;
	protected static final double Orangi_Town_Latitude = 24.9500;
	protected static final double Orangi_Town_Longitude = 66.9667;
	protected static final double Saddar_Town_Latitude = 24.8575;
	protected static final double Saddar_Town_Longitude = 67.0028;
	protected static final double Shah_Faisal_Town_Latitude = 24.8806;
	protected static final double Shah_Faisal_Town_Longitude = 67.1625;
	protected static final double SITE_Town_Latitude = 24.9053;
	protected static final double SITE_Town_Longitude = 66.9928;
	private static final double Karachi_Latitude = 24.8600;
	private static final double Karachi_Longitude = 67.0100;
	private GoogleMap accidentMap;
	private double lat[] = { 24.857625, 24.86192738, 24.85715737 };
	private double lng[] = { 67.002869, 67.00216055, 67.0059371 };
	Spinner townSpinner;
	private String towns[] = { "Select One..", "Baldia Town", "Bin Qasim Town",
			"Gadap Town", "Gulberg Town", "Jamshed Town", "Kemari Town",
			"Korangi Town", "Landhi Town", "Liaquatabad Town", "Lyari Town",
			"Malir Town", "New Karachi Town", "North Nazimabad Town",
			"Orangi Town", "Saddar Town", "Shah Faisal Town", "SITE Town" };

	webServices services = new webServices();
	ArrayList<IncidentInfo> IncidentsInfo = new ArrayList<IncidentInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accident);
		initializer();
		setFocusOnMap(Karachi_Latitude, Karachi_Longitude);
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, towns);
		townSpinner.setAdapter(ad);
		townSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				int index = arg0.getSelectedItemPosition();
				switch (index) {
				case 0:

					Toast.makeText(AccidentActivity.this,
							"You must select something", Toast.LENGTH_LONG)
							.show();
					break;

				case 1:
					setFocusOnMap(Baldia_Latitude, Baldia_Longitude);

					break;
				case 3:
					setFocusOnMap(Bin_Qasim_Latitude, Bin_Qasim_Longitude);

					break;
				case 4:
					setFocusOnMap(Gadap_Town_Latitude, Gadap_Town_Longitude);
					break;
				case 5:
					setFocusOnMap(Gulberg_Town_Latitude, Gulberg_Town_Longitude);
					break;
				case 6:
					setFocusOnMap(Jamshad_Town_Latitude, Jamshad_Town_Longitude);
					break;
				case 7:
					setFocusOnMap(Kemari_Town_Latitude, Kemari_Town_Longitude);
					break;
				case 8:
					setFocusOnMap(Korangi_Town_Latitude, Korangi_Town_Longitude);
					break;
				case 9:
					setFocusOnMap(Liaquatabad_Town_Latitude,
							Liaquatabad_Town_Longitude);
					break;
				case 10:
					setFocusOnMap(Lyari_Town_Latitude, Longitude_Town_Longitude);
					break;
				case 11:
					setFocusOnMap(Malir_Town_Latitude, Malir_Town_Longitude);

					break;
				case 12:
					setFocusOnMap(New_Karachi_Town_Latitude,
							New_Karachi_Town_Longitude);
					break;
				case 13:
					setFocusOnMap(North_Nazimabad_Town_Latitude,
							North_Nazimabad_Town_Longitude);
					break;

				case 14:
					setFocusOnMap(Orangi_Town_Latitude, Orangi_Town_Longitude);
					break;
				case 15:

					setFocusOnMap(Saddar_Town_Latitude, Saddar_Town_Longitude);

					break;
				case 16:
					setFocusOnMap(Shah_Faisal_Town_Latitude,
							Shah_Faisal_Town_Longitude);
					break;

				case 17:
					setFocusOnMap(SITE_Town_Latitude, SITE_Town_Longitude);
					break;
				default:
					Toast.makeText(AccidentActivity.this, "Not workin",
							Toast.LENGTH_SHORT).show();
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		if (isNetworkAvailable()) {
			AsyncCallWS task = new AsyncCallWS();
			task.execute();
		} else {
			Toast.makeText(this, "Please connect to internet",
					Toast.LENGTH_SHORT).show();
		}

		/*
		 * for (int i = 0; i < lat.length; i++) { addMarker(lat[i], lng[i]); }
		 */
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private void addMarker(IncidentInfo incidentInfo) {
		// TODO Auto-generated method stub
		MarkerOptions option = new MarkerOptions()
				.title(incidentInfo.Title)
				.position(
						new LatLng(Double.parseDouble(incidentInfo.Latitude),
								Double.parseDouble(incidentInfo.Longitude)))
				.snippet(incidentInfo.Info);
		accidentMap.addMarker(option);
		accidentMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				AlertDialog alert = new AlertDialog.Builder(
						AccidentActivity.this).create();
				alert.setTitle("Location Co-ordinates");
				StringBuilder bd = new StringBuilder();
				/*
				 * String lat =LocationDetail.Latitude; String
				 * lng=LocationDetail.Longitude; String
				 * name=LocationDetail.Name;
				 */
				LatLng latlng = arg0.getPosition();
				String name = arg0.getTitle();
				Geocoder gc = new Geocoder(AccidentActivity.this,
						Locale.ENGLISH);

				try {
					List<Address> address = gc.getFromLocation(latlng.latitude,
							latlng.longitude, 1);
					if (address != null) {

						Address fetchedAddress = address.get(0);
						String locality = fetchedAddress.getLocality();

						String postal = fetchedAddress.getPostalCode();
						StringBuilder strAddress = new StringBuilder();

						for (int i = 0; i < fetchedAddress
								.getMaxAddressLineIndex(); i++) {
							strAddress.append(fetchedAddress.getAddressLine(i))
									.append("\n");
						}

						bd.append("Name:" + name + "\n" + "Latitude:"
								+ latlng.latitude + "\n" + "Longitude:"
								+ latlng.longitude + "\n" + "Address:"
								+ strAddress);

					}

				} catch (Exception ex) {
					bd.append("Name:" + name + "\n" + "Latitude:"
							+ latlng.latitude + "\n" + "Longitude:"
							+ latlng.longitude);

				}

				// StringBuilder bs=new StringBuilder();
				// Toast.makeText(AccidentActivity.this, bd,
				// Toast.LENGTH_SHORT).show();

				alert.setMessage(bd);
				alert.show();

				return false;
			}

		});
	}

	/*
	 * private void addMarker(double latitude, double longitude) { // TODO
	 * Auto-generated method stub MarkerOptions option = new
	 * MarkerOptions().title("Some thing is here") .position(new
	 * LatLng(latitude, longitude)) .snippet("Same for this line");
	 * accidentMap.addMarker(option); }
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accident, menu);
		return true;
	}

	private void setFocusOnMap(double latitude, double longitude) {
		LatLng LL = new LatLng(latitude, longitude);
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LL,
				DEFAULT_ZOOM);
		accidentMap.animateCamera(cameraUpdate);

	}

	private void initializer() {
		accidentMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.accidentMap)).getMap();
		townSpinner = (Spinner) findViewById(R.id.townSorter);
	/*	LatLng point=new LatLng(Jamshad_Town_Latitude,Jamshad_Town_Longitude);
		CircleOptions circleOptions = new CircleOptions()
		  .center(point)   //set center
		  .radius(1000)   //set radius in meters
		  .fillColor(Color.RED)
		  .strokeColor(Color.BLACK)
		  .strokeWidth(5);
		 
		accidentMap.addCircle(circleOptions);*/
		
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			// / Log.i(TAG, "doInBackground");
			IncidentsInfo = services.getData(-1);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			for (IncidentInfo incidentInfo : IncidentsInfo)
				addMarker(incidentInfo);
			/*
			 * Log.i(TAG, "onPostExecute"); // response.setText(ans);
			 * Toast.makeText(FormActivity.this, ans,
			 * Toast.LENGTH_SHORT).show();
			 */
		}

		@Override
		protected void onPreExecute() {
			IncidentsInfo.clear();
			/*
			 * Log.i(TAG, "onPreExecute"); Toast.makeText(FormActivity.this,
			 * "Sending Report..", Toast.LENGTH_SHORT).show();
			 */
			Toast.makeText(AccidentActivity.this, msg, Toast.LENGTH_SHORT)
					.show();
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
