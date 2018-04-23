package com.axon.ars;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;

public class SplashScreenActivity extends Activity {

	static String filename = "NICPrefs";
	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		Thread splashThread = new Thread() {
			public void run() {
				try {

					sleep(2000);
				} catch (Exception ex) {
					ex.printStackTrace();

				} finally {
					if (checkPreferences() == true) {

						Intent i = new Intent(SplashScreenActivity.this,
								BasicInformationActivity.class);
						SplashScreenActivity.this.startActivity(i);
						finish();
					} else {
						Intent i = new Intent(SplashScreenActivity.this,
								TabViewActivity.class);
						SplashScreenActivity.this.startActivity(i);
						finish();
					}

				}
			}
		};
		splashThread.start();
	}

	private boolean checkPreferences() {

		myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		String getValue = myPrefs.getString("number", null);

		if (getValue == null) {
			return true;
		} else
			return false;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

}
