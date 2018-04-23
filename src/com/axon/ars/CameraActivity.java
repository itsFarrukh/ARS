package com.axon.ars;

import android.R.bool;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraActivity extends Activity implements View.OnClickListener {

	ImageView imView, imView2;
	static final int REQUEST_IMAGE_CAPTURE1 = 1;
	static final int REQUEST_IMAGE_CAPTURE2 = 2;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_IMAGE_CAPTURE1:
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				// imView = (ImageView) findViewById(R.id.imageView1);

				imView.setImageBitmap(imageBitmap);

				break;
			case REQUEST_IMAGE_CAPTURE2:

				Bundle extras1 = data.getExtras();
				Bitmap imageBitmap1 = (Bitmap) extras1.get("data");
				// imView = (ImageView) findViewById(R.id.imageView1);

				imView2.setImageBitmap(imageBitmap1);

			}

		}

		/*
		 * if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
		 * { Bundle extras = data.getExtras(); Bitmap imageBitmap = (Bitmap)
		 * extras.get("data"); imView2 = (ImageView)
		 * findViewById(R.id.imageView2);
		 * 
		 * imView2.setImageBitmap(imageBitmap); }
		 */
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.camera_activity);
		imView = (ImageView) findViewById(R.id.imageView1);
		// imView2 = (ImageView) findViewById(R.id.imageView2);
		imView.setOnClickListener(this);
		imView2.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	/*
	 * View.OnClickListener imageClick = new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (checkCameraAvailable()) { CaptureImages(); }
	 * 
	 * } }; View.OnClickListener secondImageClick = new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (checkCameraAvailable()) { CaptureImages(); } } };
	 */
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView1:
			if (checkCameraAvailable()) {
				CaptureImages();
				/*
				 * Intent data=new Intent(); Bundle extras = data.getExtras();
				 * Bitmap imageBitmap = (Bitmap) extras.get("data");
				 * 
				 * imView.setImageBitmap(imageBitmap);
				 */
			}
			break;

		}
	}
}
