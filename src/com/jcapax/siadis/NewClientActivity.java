package com.jcapax.siadis;

import com.jcapax.dataConnect.HttpHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class NewClientActivity extends Activity {
	
	private static final String LOGTAG = "LogsAndroid";
	
	Spinner spinTipoLocal;
	Spinner spinCanal; 
	
	Button btnSaveNewClient;
	
	EditText editClientName, editDenominationClient, editDireccion, editNro, editZona,
		editTelefono, editRuta;
	
	String canal, tipoLocal;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_client);
		

		//*********************************************************************************************************
		// spiner tipo local
		//*********************************************************************************************************
		
		spinTipoLocal = (Spinner)findViewById(R.id.spinTipoLocal);
		final ArrayAdapter<?> adapterTipoLocal = ArrayAdapter.createFromResource(this, 
				R.array.listTipoLocal, android.R.layout.simple_spinner_dropdown_item);		
		adapterTipoLocal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spinTipoLocal.setAdapter(adapterTipoLocal);
			
		spinTipoLocal.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				tipoLocal = arg0.getItemAtPosition(arg2).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//*********************************************************************************************************
		// spiner canal
		//*********************************************************************************************************
		
		spinCanal = (Spinner)findViewById(R.id.spinCanal);
		final ArrayAdapter<?> adapterCanal = ArrayAdapter.createFromResource(this, 
				R.array.listCanal, android.R.layout.simple_spinner_dropdown_item);		
		adapterCanal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spinCanal.setAdapter(adapterCanal);
			
		spinCanal.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				canal = arg0.getItemAtPosition(arg2).toString();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

		btnSaveNewClient = (Button)findViewById(R.id.btnSaveNewClient);
		btnSaveNewClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editClientName = (EditText)findViewById(R.id.editClientName);
				editDenominationClient = (EditText)findViewById(R.id.editDenominationClient); 
				editDireccion  = (EditText)findViewById(R.id.editDirecction);
				editNro        = (EditText)findViewById(R.id.editNro);
				editZona       = (EditText)findViewById(R.id.editZone);
				editTelefono   = (EditText)findViewById(R.id.editTelephone);
				editRuta       = (EditText)findViewById(R.id.editRuta);
				
				if(validar()){
					
					String service = "scripts/saveclient.php";
					
					
					
					String idClient = "error";
					String error    = "error";
					
					
					
					HttpHandler httpCliSave = new HttpHandler();	
					
					
	/*				
					Log.e(LOGTAG, editClientName.getText().toString());
					Log.e(LOGTAG, editDireccion.getText().toString());
					Log.e(LOGTAG, editNro.getText().toString());
					Log.e(LOGTAG, editZona.getText().toString());
					Log.e(LOGTAG, canal.toString());
					Log.e(LOGTAG, tipoLocal.toString());
					Log.e(LOGTAG, editTelefono.getText().toString());
					Log.e(LOGTAG, editRuta.getText().toString());
	*/				
					
					idClient = httpCliSave.saveClient(service, editClientName.getText().toString(),
									editDenominationClient.getText().toString(),
									editDireccion.getText().toString(), 
									editNro.getText().toString(), 
									editRuta.getText().toString(), 
									editZona.getText().toString(), 
									canal.toString(),
									tipoLocal.toString(),
									editTelefono.getText().toString());
					
					if(!idClient.equals(error)){
						Toast toast1 = Toast.makeText(getApplicationContext(),
			                    "Cliente "+idClient+ " Registrado con Éxito", Toast.LENGTH_LONG);				
						toast1.show();
						finish();
					}
					else{
						Toast toast1 = Toast.makeText(getApplicationContext(),
			                    "Error en el Registro, Llamar al Administrador!!!", Toast.LENGTH_LONG);				
						toast1.show();
						
					}
				}
				else{	// else validacion
					Toast toast1 = Toast.makeText(getApplicationContext(),
		                    "Error en el Registro, Verificar !!!", Toast.LENGTH_LONG);				
					toast1.show();
				}
				
				
			}

			private boolean validar() {
				// TODO Auto-generated method stub
				Boolean aux = false;
				
				String cadenaCanal    = "CANAL";
				String cadenaTipoLocal = "TIPO-LOCAL";
				
				if(!(cadenaCanal.equals(canal)) && (!cadenaTipoLocal.equals(tipoLocal))){
					aux = true;
				}
				else{
					aux = false;
				}
				
				
				// VALIDAR NOMBRE CLIENTE
				if(aux){
					if(editClientName.getText().toString() == ""){
						aux = false;
					}
				}

				// VALIDAR DIRECCION
				if(aux){
					if(editDireccion.getText().toString() == ""){
						aux = false;
					}
				}
				
								
				return aux;
			}
		});
		
	}
}
