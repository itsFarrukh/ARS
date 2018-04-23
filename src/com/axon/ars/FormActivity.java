package com.axon.ars;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends Activity implements OnClickListener {
	public String TAG = "MUZAMMIL";
	public static String ans;

	public String strBase64;
	public String strBase642;
	public sendDataService services = new sendDataService();
	String[] accidentType = { "Robbery", "Accident", "Fire", "Crime Scene" };
	Spinner accidentSpinner;
	static final int REQUEST_IMAGE_CAPTURE1 = 1;
	static final int REQUEST_IMAGE_CAPTURE2 = 2;
	private static final float DEFAULT_ZOOM = 15;
	ImageView imView, imView2;
	private EditText desc;
	static String name, nic, streetAddress, accidentDescription,
			accidentCategory;
	static double getLat, getLng;
	static String lat, len;

	SharedPreferences myPrefs;
	Button submit;
	GoogleMap formMap;
	String[] accidentTypesSpinner = { "Rescue", "Accident", "Fire",
			"Crime Scene", "Protest" };
	Integer[] spinnerImages = { R.drawable.crimescene, R.drawable.crimescene,
			R.drawable.crimescene, R.drawable.crimescene,
			R.drawable.crimescene, R.drawable.crimescene, R.drawable.crimescene };

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_IMAGE_CAPTURE1:
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");

				imView.setImageBitmap(imageBitmap);
				BitmapDrawable drawable = (BitmapDrawable) imView.getDrawable();
				Bitmap bitmap = drawable.getBitmap();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
				byte[] datas = baos.toByteArray();
				strBase64 = Base64.encodeToString(datas, Base64.DEFAULT);

				break;
			case REQUEST_IMAGE_CAPTURE2:

				Bundle extras1 = data.getExtras();
				Bitmap imageBitmap1 = (Bitmap) extras1.get("data");
				imView2.setImageBitmap(imageBitmap1);
				BitmapDrawable drawable2 = (BitmapDrawable) imView2
						.getDrawable();
				Bitmap bitmap2 = drawable2.getBitmap();

				ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
				bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
				byte[] datas2 = baos2.toByteArray();

				strBase642 = Base64.encodeToString(datas2, Base64.DEFAULT);

			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ars_form_revised);
		initializers();
		accidentSpinner.setAdapter(new MyAdaptor(this, R.layout.custom_spinner,
				R.id.spinnerTv, accidentTypesSpinner));
		getValues();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// traceLocation(getLat, getLng);

		desc.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub

				Button cancelbutton = (Button) findViewById(R.id.calc_clear_txt_Prise);
				cancelbutton.setVisibility(0);
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.form, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.image1:
			if (checkCameraAvailable()) {
				CaptureImages();

			}
			break;
		case R.id.image2:
			if (checkCameraAvailable()) {
				CaptureSecondSnap();
			}
			break;
		case R.id.reportBtn:

			getValues();
			AsyncCallWS task = new AsyncCallWS();
			// Call execute
			task.execute();
		}

	}

	private void traceLocation(double Lat, double Lng) {

		LatLng latlng = new LatLng(Lat, Lng);
		CameraUpdate updateCamera = CameraUpdateFactory.newLatLngZoom(latlng,
				DEFAULT_ZOOM);
		formMap.animateCamera(updateCamera);
		addMarkers(Lat, Lng);
	}

	private void addMarkers(double lat, double lng) {
		LatLng currentLocationLatLng = new LatLng(lat, lng);
		MarkerOptions option = new MarkerOptions()
				.position(currentLocationLatLng).title("You are here")
				.snippet("Accident Location")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
		formMap.addMarker(option);
	}

	public void initializers() {

		/*
		 * formMap = ((MapFragment) getFragmentManager().findFragmentById(
		 * R.id.form_Map)).getMap();
		 */
		accidentSpinner = (Spinner) findViewById(R.id.accidentSpinner);
		desc = (EditText) findViewById(R.id.descriptionEt);
		imView = (ImageView) findViewById(R.id.image1);
		imView2 = (ImageView) findViewById(R.id.image2);
		imView.setOnClickListener(this);
		imView2.setOnClickListener(this);
		submit = (Button) findViewById(R.id.reportBtn);
		submit.setOnClickListener(this);

		try {
			ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, accidentType);
			adapter_state
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			accidentSpinner.setAdapter(adapter_state);
		} catch (Exception ex) {
			Toast.makeText(this, "Something happen", Toast.LENGTH_SHORT).show();
		}

	}

	private boolean checkCameraAvailable() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;

		} else {
			Toast.makeText(this, "Camera app isn't available",
					Toast.LENGTH_SHORT).show();
			return false;
		}

	}

	private void CaptureImages() {
		Intent takeSnaps = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (takeSnaps.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takeSnaps, REQUEST_IMAGE_CAPTURE1);

		}
	}

	private void CaptureSecondSnap() {

		Intent takeSnaps = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (takeSnaps.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takeSnaps, REQUEST_IMAGE_CAPTURE2);

		}

	}

	private void getValues() {

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		nic = myPrefs.getString("number", null);
		name = myPrefs.getString("name", null);
		DataStorage retrieveData = new DataStorage();
		streetAddress = retrieveData.getAddressData();
		getLat = retrieveData.getLatData();
		getLng = retrieveData.getLngData();
		accidentDescription = desc.getText().toString();
		accidentCategory = accidentSpinner.getSelectedItem().toString();
		lat = Double.toString(getLat);
		len = Double.toString(getLng);

	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			ans = services.sendData(nic, name, accidentCategory,
					accidentDescription, len, lat, streetAddress, strBase64,
					strBase642);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			// response.setText(ans);
			Toast.makeText(FormActivity.this, ans, Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			Toast.makeText(FormActivity.this, "Sending Report..",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
			Toast.makeText(FormActivity.this, "wait..", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public class MyAdaptor extends ArrayAdapter<String> {

		public MyAdaptor(Context context, int resource, int textViewResourceId,
				String[] objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			return super.getDropDownView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return super.getView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView,
				ViewGroup parent) {

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.custom_spinner, parent, false);
			ImageView icon = (ImageView) row.findViewById(R.id.spinnerImage);
			icon.setImageResource(spinnerImages[position]);

			TextView label = (TextView) row.findViewById(R.id.spinnerTv);
			label.setText(accidentTypesSpinner[position]);

			return row;
		}

	}

}
