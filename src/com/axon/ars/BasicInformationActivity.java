package com.axon.ars;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BasicInformationActivity extends Activity implements
		OnClickListener {

	Button submitBtn;
	EditText nameEt, nicEt;
	static String filename = "nicPrefs";
	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_information);
		initializer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.basic_information, menu);
		return true;
	}

	private void initializer() {
		submitBtn = (Button) findViewById(R.id.submitBtn);
		nameEt = (EditText) findViewById(R.id.username);
		nicEt = (EditText) findViewById(R.id.nicNumber);
		submitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.submitBtn:

			String nicData = nicEt.getText().toString();
			String nameData = nameEt.getText().toString();
			if (nicData == null || nameData == null) {
				Toast.makeText(this, "No fields can be empty",
						Toast.LENGTH_SHORT).show();
			} else {

				myPrefs = PreferenceManager.getDefaultSharedPreferences(this);

				Editor editor = myPrefs.edit();
				//SharedPreferences.Editor editor = myPrefs.edit();
				editor.putString("number", nicData);
				editor.putString("name", nameData);
				editor.commit();
				Intent i = new Intent(BasicInformationActivity.this,
						TabViewActivity.class);
				BasicInformationActivity.this.startActivity(i);
				finish();
				break;
			}

		}

	}

}
