package com.jcapax.siadis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AutenticateActivity extends Activity {
	
	Button btnAutenticate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_autenticate);
		
		btnAutenticate = (Button)findViewById(R.id.btnAutenticar);
		
		btnAutenticate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(AutenticateActivity.this, MainActivity.class);
				//intent.putExtra("codigoUsuario",  "1");
				startActivity(intent);	
											
			}
		});
	}
}
