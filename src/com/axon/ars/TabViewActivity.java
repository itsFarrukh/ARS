package com.axon.ars;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TabViewActivity exte
ds TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_view);
		TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec tab1 = tab.newTabSpec("First Tab");
		TabSpec tab2 = tab.newTabSpec("Second Tab");
		TabSpec tab3 = tab.newTabSpec("Third Tab");
		TabSpec tab4 = tab.newTabSpec("Fourth Tab");

		tab1.setIndicator("Report");
		tab1.setContent(new Intent(this, NewMapActivity.class));
		tab2.setIndicator("View Incidents");
		tab2.setContent(new Intent(this, AccidentActivity.class));
		tab3.setIndicator("Analyz");
		tab3.setContent(new Intent(this, HeatMappingActivity.class));
		tab4.setIndicator("RSS");
		tab4.setContent(new Intent(this, RSSActivity.class));
		tab.setup();
		tab.addTab(tab1);
		tab.addTab(tab2);
		tab.addTab(tab3);
		tab.addTab(tab4);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tab_view, menu);
		return true;
	}

}
