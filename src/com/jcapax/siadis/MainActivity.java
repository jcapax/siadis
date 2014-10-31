package com.jcapax.siadis;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jcapax.dataConnect.HttpHandler;
import com.jcapax.dialogs.DialogsClientMenu;
import com.jcapax.dialogs.DialogsList;
import com.jcapax.list.Clients;

public class MainActivity extends ActionBarActivity {

	public boolean intentNewClient = false;
	
	private static final String LOGTAG = "LogsAndroid";
	
	
	static ProgressDialog dialog;
	
	TabHost tabHost;
	//*********************************
	// components sale   TAB 1
	Spinner spinListProductsSale;
	Spinner spinTipoVenta;
	String productSale = "";
	String tipeSale = "0";
	ListView listProductsSale;
	Button btnSearchClientSale;
	Button btnAddProductList;
	Button btnGetProductsSale;
	EditText editSearchSale;
	EditText editCantSale;
	TextView textClientDataSale;
	int contProducts = 0;
	
	int bottle = 0;	
	boolean boxBottle = false;
	
	//*********************************
	//components container   TAB 2
	
	String productRecup = "";
	Button btnSearchClientRecup;	
	Button btnGetProductsRecup;
	EditText editSearchRecup;
	EditText editBotellas;
	EditText editCajas;
	TextView textClientDataRecup;
	int contEnva = 0;
	
	//***********************************
	// history clients
	EditText editSearchHistory;
	Button btnSearchHistory;
	ListView listViewClientesHistory;
	
	
	String serviceSale = "scripts/savesale.php";
	String serviceDetailSale = "scripts/savedetailsale.php";
	
	
	HttpHandler http = new HttpHandler();
	String nameClientSale, nameClientRecup;
		
	@SuppressWarnings("unused")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		btnSearchClientSale = (Button)findViewById(R.id.btnSearchSale);
		editSearchSale = (EditText)findViewById(R.id.editSearchSale);
		textClientDataSale = (TextView)findViewById(R.id.textClientDataSale);
		
		
		final String service = "scripts/getDataClient.php";
				
		btnSearchClientSale.setOnClickListener(new OnClickListener() {		
			
			@Override
			public void onClick(View v) {
			    if(editSearchSale.length() > 0){
			    	nameClientSale = http.postIdcliente(service, editSearchSale.getText().toString());
			    	
			    	if(nameClientSale == "0"){
			    		registerNewClient();
			    	}
			    	else{
			    		textClientDataSale.setText(nameClientSale);
			    		
			    	}
			    }
			    else{
			    	registerNewClient();
			    }
				
				
				
			}
		});
		

		
		//oculta el action bar
		getSupportActionBar().hide();
		
		tabHost = (TabHost)findViewById(android.R.id.tabhost);

		Resources res = getResources();
		
		//  primer tab	
		
		tabHost.setup();
		
		TabHost.TabSpec ts1 = tabHost.newTabSpec("tab1");		
		ts1.setIndicator("", res.getDrawable(R.drawable.registro));
		ts1.setContent(R.id.tab1);
		tabHost.addTab(ts1);
		
		//*********************************************************************************************************
		// spiner tipo venta
		//*********************************************************************************************************
		spinTipoVenta = (Spinner)findViewById(R.id.spinListTipoVenta);
		final ArrayAdapter<?> adapterTipoVenta = ArrayAdapter.createFromResource(this, 
				R.array.listTipoVenta, android.R.layout.simple_spinner_dropdown_item);		
		adapterTipoVenta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
		spinTipoVenta.setAdapter(adapterTipoVenta);
		
		spinTipoVenta.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				tipeSale = Integer.toString(position);				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
				
		//*********************************************************************************************************
		// spiner lista productos venta
		//*********************************************************************************************************
		
			spinListProductsSale = (Spinner)findViewById(R.id.spinListProductsSale);
			final ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, 
					R.array.listProducts, android.R.layout.simple_spinner_dropdown_item);		
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
			spinListProductsSale.setAdapter(adapter);
				
			spinListProductsSale.setOnItemSelectedListener(new OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					
					productSale = arg0.getItemAtPosition(arg2).toString();
				}
	
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		//************************	
				
		// listview list products sale
		//************************

			editCantSale = (EditText)findViewById(R.id.editCantSale);
			btnGetProductsSale = (Button)findViewById(R.id.btnGetProductsSale);
			btnGetProductsSale.setVisibility(View.INVISIBLE);
			
			btnAddProductList = (Button)findViewById(R.id.btnAddProductList);
			listProductsSale = (ListView)findViewById(R.id.listProductsSale);
				
			final ArrayList<String> arrayListProductsSale = new ArrayList<String>();
			
			final ArrayAdapter<String> arrayAdpaterProductsSale = new 
							ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListProductsSale);
			
			listProductsSale.setAdapter(arrayAdpaterProductsSale);
			
			btnAddProductList.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					float totalPriceProduct = 0;
					
					String bottleText    = "Botella";
					String boxBottleText = "Envase Canastillo";
					
					String getProduct = "PRODUCTO";
					
					if(!getProduct.equals(productSale)){
						
						Boolean aux = false;
										
						Iterator<String> i = arrayListProductsSale.iterator();
						while (i.hasNext()){
							String elemento = i.next();
							String soloProducto[] = elemento.split("-");
							
							if(soloProducto[0].trim().equals(productSale)){
								aux = true;
							}					
							else{
								totalPriceProduct = totalPriceProduct + Float.valueOf(soloProducto[2].trim());
							}
						}
						
						if (!aux){
							
							String priceUnit = "0";
							HttpHandler httpPrice = new HttpHandler();
							
							String tipePrice = getTipePrice(textClientDataSale.getText().toString());
							
							priceUnit = httpPrice.getPriceProduct("scripts/getpriceproduct.php", tipePrice, productSale);
							
							// verificar que el producto tenga valor de venta
							Boolean auxValor = prudcotSinValor(priceUnit);
							
							if(boxBottleText.equals(productSale)){
								auxValor = true;
							}
							
							if(auxValor){ 
								
								String totalProductSelected = "";
								
								// verificar la cantidad de producto
								if(editCantSale.getText().length() != 0){
									totalProductSelected = calcularTotalProducto(editCantSale.getText().toString(), priceUnit);
									
									arrayListProductsSale.add(productSale+ " - "+ editCantSale.getText().toString() + " - "+ totalProductSelected);
									
									totalPriceProduct = totalPriceProduct + Float.valueOf(totalProductSelected);
									
									btnGetProductsSale.setText("Enviar el total: "+String.valueOf(totalPriceProduct));
									
									String indi[] = productSale.split("-"); 
									 
									if(bottleText.equals(indi[0].substring(0, 7))){
										bottle = bottle + 1;
									}
									
									if(boxBottleText.equals(indi[0])){
										boxBottle = true;
									}
									
									
									//Log.e(LOGTAG, String.valueOf(bottle));
									
								}
								else{
									Toast toast1 = Toast.makeText(getApplicationContext(),
											"Introducir la Cantidad del Producto a Vender", Toast.LENGTH_LONG);	
									toast1.show();
								}
							}	
							else{
								Toast toast1 = Toast.makeText(getApplicationContext(),
										"El producto seleccionado no tiene precio de venta", Toast.LENGTH_LONG);	
								toast1.show();
							}
							
						}
						else{
							Toast.makeText(getApplicationContext(), "El producto ya ha sido seleccionado",Toast.LENGTH_SHORT).show();
						}
											
						editCantSale.setText("");
					}				
					
					arrayAdpaterProductsSale.notifyDataSetChanged();
					
					spinListProductsSale.setAdapter(adapter);
					
					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			        inputMethodManager.hideSoftInputFromWindow(editCantSale.getWindowToken(), 0);
			        
			        contProducts = contProducts + 1;
			        btnGetProductsSale.setVisibility(View.VISIBLE);
					
				}

				private Boolean prudcotSinValor(String priceUnit) {
					Boolean aux = true;
					
					if(Double.valueOf(priceUnit) == 0){
						aux = false;
					}					
					
					return aux;
				}

				private String getTipePrice(String string) {
					String tipePrice = "0";
					
					if(string.trim().length()!=0){					
						String separated[] = string.split("//");
						tipePrice = separated[1].trim();
					}
					
					return tipePrice;
				}

				private String calcularTotalProducto(String cant,
						String priceUnit) {
					
					float total, aux;
					
					aux = Integer.valueOf(cant) * Float.valueOf(priceUnit);
					
					total = (float) (Math.rint(aux*100)/100); 
					
					return String.valueOf(total);
				}
			});
			
			listProductsSale.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					
					contProducts =  contProducts - 1;
					/*
					*******************************************************************************************************************
					*********************************************************************************
					*********************************************************************************
					*/
					
					String bottleText = "Botella";
					String boxBottleText = "Envase Canastillo";
					
					String textList = listProductsSale.getItemAtPosition(position).toString();
					String indi[] = textList.split("-"); 
					
					if(bottleText.equals(indi[0].substring(0, 7))){
						bottle = bottle - 1;
					}
					
					if(boxBottleText.equals(indi[0])){
						boxBottle = false;
					}
					
					if (contProducts == 0){
						btnGetProductsSale.setVisibility(View.INVISIBLE);
					}
						
					
					arrayListProductsSale.remove(position);
					arrayAdpaterProductsSale.notifyDataSetChanged();
					
					return false;
				}
				
			});
			
		
		btnGetProductsSale.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				
				//Verificar que listview tenga registros
				if(arrayListProductsSale.size() > 0){ 
									
					String idDistributor = "1";
					String idClient = "0"; 
						   idClient = getIdClient(textClientDataSale.getText().toString());
					String idPrice = "1";				
					
					
					String longitude, latitude;
					
					String movement = "1";
					
					//comprobar si son devoluciones de consignaciones
					if(tipeSale.equals("4")){
						movement = "-1";
					}
										
					longitude = "1";
					latitude  = "1";
					
					// comprobar registro de cajas
					
					if(bottle == 0){ // si no existen botellas
					
						insertSale(serviceSale, idDistributor, idClient, tipeSale, idPrice, movement, latitude, longitude);
						finish();
					}
					else{
						if(boxBottle){ // si las cajas han sido registradas
							insertSale(serviceSale, idDistributor, idClient, tipeSale, idPrice, movement, latitude, longitude);
							finish();
						}
						else{ // si faltan cajas por registrar
							Toast toast1 = Toast.makeText(getApplicationContext(),
									"No se tomaron en cuenta Envases Canastillos para la Venta", Toast.LENGTH_LONG);	
							toast1.show();	
						}
					}						
					
				}
				else{ // cuando listview este vacio
					Toast toast1 = Toast.makeText(getApplicationContext(),
							"Seleccionar Productos para la Venta", Toast.LENGTH_LONG);	
					toast1.show();
				}
				
			}

			private void insertSale(String service, String idDistributor, String idClient, String tipeSale, String idPrice, 
					String movement, String latitude, String longitude) {
				// TODO Auto-generated method stub
				
				String idSale = null;
				String aux = null;
				
				HttpHandler httpSale = new HttpHandler();
				idSale = httpSale.saveSale(serviceSale, idDistributor, idClient, tipeSale, idPrice, movement, latitude, longitude);
				
				for (int j = 0; j < arrayListProductsSale.size(); j++) {
					HttpHandler httpDetailSale = new HttpHandler();
					
					String separated[] = arrayListProductsSale.get(j).toString().split("-");
					
					String productName = "";
					productName = separated[0];
					
					String cant = "0";
					cant = separated[1];
					
					String priceUnit = "0";
					priceUnit = separated[2];
					
					aux = httpDetailSale.saveDetailSale(serviceDetailSale, idSale, productName, cant, priceUnit);
					
					
				}	
				
				arrayListProductsSale.clear();
				arrayAdpaterProductsSale.notifyDataSetChanged();
				
				Toast toast1 = Toast.makeText(getApplicationContext(),
						"VENTA REALIZADA CON EXITO", Toast.LENGTH_LONG);	
				toast1.show();


				
			}

		});	
			
		
		
		
		
		
		
		
		
		
		
		
		
		
			

		//*********************************************************************************************************		
		//*********************************************************************************************************
		
	
		//  second tab		
		tabHost.setup();
		TabHost.TabSpec ts2 = tabHost.newTabSpec("tab2");
		ts2.setIndicator("", res.getDrawable(R.drawable.venta));
		ts2.setContent(R.id.tab2);
		tabHost.addTab(ts2);		
		
		btnSearchClientRecup = (Button)findViewById(R.id.btnSearchrecup);
		editSearchRecup = (EditText)findViewById(R.id.editSearchRecup);
		textClientDataRecup = (TextView)findViewById(R.id.textClientDataRecup);		
		
		editBotellas = (EditText)findViewById(R.id.editBotellas);
		editCajas    = (EditText)findViewById(R.id.editCajas);
		btnGetProductsRecup = (Button)findViewById(R.id.btnGetProductsRecup);
				
		btnSearchClientRecup.setOnClickListener(new OnClickListener() {		
			
			@Override
			public void onClick(View v) {
			    if(editSearchRecup.length() > 0){
			    	HttpHandler httpRecup = new HttpHandler();
			    	nameClientRecup = httpRecup.postIdcliente("scripts/getDataClient.php", editSearchRecup.getText().toString());
			    	
			    	textClientDataRecup.setText(nameClientRecup);
			    }
			}			
			
		});
		
		btnGetProductsRecup.setOnClickListener(new OnClickListener() {			
			
			@Override
			public void onClick(View v) {
				HttpHandler httpRecup = new HttpHandler();
				
				String movement = "-1"; // recuperacion de envases
				
				String idDistributor = "1";
				String idClient = "0"; 
					idClient = getIdClient(textClientDataRecup.getText().toString());
				String idPrice = "0"; 
				
				String idRecup = "";
				String tipeSale = "4";
				
				String longitude, latitude;
				
				longitude = "1";
				latitude  = "1";
				
				idRecup = httpRecup.saveSale(serviceSale, idDistributor, idClient, tipeSale, idPrice, movement, latitude, longitude);
				
				httpRecup.saveDetailSale(serviceDetailSale, idRecup, "Envase Botella", editBotellas.getText().toString(), "0");
				httpRecup.saveDetailSale(serviceDetailSale, idRecup, "Envase Canastillo", editCajas.getText().toString(), "0");
				
				
				Toast toast1 = Toast.makeText(getApplicationContext(),
						"RECUPERACION DE ENVASES REALIZADA CON EXITO", Toast.LENGTH_LONG);	
				toast1.show();
				
				
				finish();
			}
		});
		
		
			
		



		//*********************************************************************************************************		
		//*********************************************************************************************************

			
		//  tercer tab		
		tabHost.setup();
		TabHost.TabSpec ts3 = tabHost.newTabSpec("tab3");
		ts3.setIndicator("", res.getDrawable(R.drawable.seguimiento));
		ts3.setContent(R.id.tab3);
		tabHost.addTab(ts3);
		//**********************************************
		// historial cliente
		//**********************************************

			btnSearchHistory  = (Button)findViewById(R.id.btnSearchHistory);
			editSearchHistory = (EditText)findViewById(R.id.editSearchHistory);
			listViewClientesHistory = (ListView)findViewById(R.id.listViewClientsHistory);
			
			btnSearchHistory.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					dialog = new ProgressDialog(MainActivity.this);
					dialog.setMessage("Un momento porfavor");
					dialog.show();				
					
					Editable criterio = editSearchHistory.getText();
					
					AsyncListClients asyncListClient = new AsyncListClients();
					asyncListClient.cargarContenido(getApplicationContext(), criterio.toString().trim());
					asyncListClient.execute(listViewClientesHistory);
					
					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			        inputMethodManager.hideSoftInputFromWindow(editSearchHistory.getWindowToken(), 0);
					
					
				}
			});
			
			listViewClientesHistory.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
				
					String cadena = null;
					cadena = listViewClientesHistory.getItemAtPosition(position).toString();
					
					android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
			        DialogsClientMenu dialog = new DialogsClientMenu();
			        
			        String separate[] = cadena.trim().split("-");
			        
			        Log.e(LOGTAG, separate[0]);
			        
			        dialog.setIdClient(separate[0]);
			        dialog.setClientName(separate[1]);
			        
			        dialog.show(fragmentManager, "tagAlerta");				
					
					return false;
					
				}
				
			});
		
				
//*********************************************************************************************************		
//*********************************************************************************************************
		

		//  cuarto tab		
		tabHost.setup();
		TabHost.TabSpec ts4 = tabHost.newTabSpec("tab");
		ts4.setIndicator("", res.getDrawable(R.drawable.reportes));
		ts4.setContent(R.id.tab4);
		tabHost.addTab(ts4);
		
//*********************************************************************************************************
// list historial distribuidor
//*********************************************************************************************************

		
		
//*********************************************************************************************************		
//*********************************************************************************************************
		
	
	}
	

	protected void registerNewClient() {
		// TODO Auto-generated method stub
		android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        DialogsList dialog = new DialogsList();
        dialog.show(fragmentManager, "tagAlerta");
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	static class AsyncListClients extends AsyncTask<ListView, Void, ArrayAdapter<Clients>>{
		Context contexto;
		ListView list;
		InputStream is;
		ArrayList<Clients> listaClientes = new ArrayList<Clients>();
		
		String criterio;
		
		public void cargarContenido(Context contexto, String criterio){
			this.contexto = contexto;
			this.criterio = criterio.replaceFirst(" ", "%20");			
		}
		
		@Override
		protected void onPostExecute(android.widget.ArrayAdapter<Clients> result) {
			dialog.dismiss();
			list.setAdapter(result);
		};
		
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
		protected ArrayAdapter<Clients> doInBackground(ListView... params) {
			// TODO Auto-generated method stub
			
			HttpHandler httpHandler = new HttpHandler();			
			
			list = params[0];
			String resultado = "fallo";
			Clients cli;
						
			String url = null;
			url = httpHandler.globalURL;	
			
			//Log.e(LOGTAG, criterio);
						
			String param = "scripts/json_clientes.php?criterio="+criterio;
				
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url+param);
			
			try{
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity contenido = response.getEntity();
				is = contenido.getContent();
				
			}catch(ClientProtocolException e){
				e.printStackTrace();
			}catch (IOException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			//Log.e(LOGTAG, "************** llegamos ******************");
			
			BufferedReader buferLector = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String linea = null;
			try{
				while((linea = buferLector.readLine()) != null){
					sb.append(linea);
				}
			}catch (IOException e){
				e.printStackTrace();
			}
			
			try{
				is.close();
			}catch (IOException e){
				e.printStackTrace();
			}
			
			resultado = sb.toString();
			
			
									
			try{
				JSONArray arrayJson = new JSONArray(resultado);
				for(int i = 0; i < arrayJson.length(); i++){
					JSONObject objetoJson = arrayJson.getJSONObject(i);
					cli = new Clients(objetoJson.getInt("id"), objetoJson.getString("nombreCliente"), 
										objetoJson.getString("direccion"), objetoJson.getString("nro"), 
										objetoJson.getString("ruta"),      objetoJson.getString("zona"), 
										objetoJson.getString("canal"),     objetoJson.getString("tipoLocal"));
					
					listaClientes.add(cli);
					
					//Log.e(LOGTAG, cli.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayAdapter<Clients> adaptador = new ArrayAdapter<Clients>(contexto, android.R.layout.simple_list_item_1, listaClientes);
			
			return adaptador;
		}
		
	}

	
	private String getIdClient(String string) {
		String idClient = "0";
		
		if(string.trim().length()!=0){					
			String separated[] = string.split("//");
			idClient = separated[0].trim();
		}
		
		return idClient;
	}

	

}
