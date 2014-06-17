package com.example.pruebawebservice;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Opcion_1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opcion_1);
		TextView txt = (TextView)findViewById(R.id.text1);
		Bundle data = getIntent().getExtras();
		txt.setText(data.getStringArrayList("envio").get(0)+" "+data.getStringArrayList("envio").get(1));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.opcion_1, menu);
		return true;
	}

}
