package com.jcapax.siadis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jcapax.dataConnect.HttpHandler;
import com.jcapax.list.SalePerClient;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SaleActivity extends Activity {
	
	static ProgressDialog dialog;
	
	private static final String LOGTAG = "LogsAndroid";
	
	
	String idClient = null;
	String clientName = null;
	String tipo = null;
	
	ListView listSaleHistoryPerClient;
	TextView textClientNameHistorySale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale_per_client);
		
		Bundle extras = getIntent().getExtras();
		
		idClient   = extras.getString("idClient");
		clientName = extras.getString("clientName");
		tipo       = extras.getString("tipo");		

		if(tipo.equals("1")){
			setTitle("VENTAS - BONIF...- CONSIG...");
		}
		
		if(tipo.equals("4")){
			setTitle("RECUP. ENVASES");
		}
		
		textClientNameHistorySale = (TextView)findViewById(R.id.textClientNameHistorySale);
		textClientNameHistorySale.setText(idClient + " " +clientName);
		
		listSaleHistoryPerClient = (ListView)findViewById(R.id.listSaleHistoryPerClient);
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Recupernado Ventas, un momento porfavor");
		dialog.show();		
		
		
		AsyncListSales asyncListSales = new AsyncListSales();
		asyncListSales.cargarContenido(getApplicationContext(), idClient.trim(), tipo.trim());
		asyncListSales.execute(listSaleHistoryPerClient);
					
	}
	
	static class AsyncListSales extends AsyncTask<ListView, Void, ArrayAdapter<SalePerClient>>{
		Context contexto;
		String criterio;
		String tipo;
		
		ListView list;
		InputStream is;
		ArrayList<SalePerClient> listSales = new ArrayList<SalePerClient>();
		
		public void cargarContenido(Context contexto, String criterio, String tipo){
			this.contexto = contexto;
			this.criterio = criterio;
			this.tipo     = tipo;
		}
		
		@Override
		protected void onPostExecute(android.widget.ArrayAdapter<SalePerClient> result) {
			dialog.dismiss();
			list.setAdapter(result);
		};
		
		@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		@Override
		protected ArrayAdapter<SalePerClient> doInBackground(ListView... params) {
			// TODO Auto-generated method stub
			
			HttpHandler httpHandler = new HttpHandler();			
			
			list = params[0];
			String resultado = "fallo";
			SalePerClient cli;
						
			String url = null;
			url = httpHandler.globalURL;	
			
			Log.e(LOGTAG, criterio);
						
			String param = url+"scripts/json_listclientsale.php?tipo="+tipo+"&criterio="+criterio;
				
			Log.e(LOGTAG, param);
			
			
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(param);
			
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
					cli = new SalePerClient(objetoJson.getString("fecha"), objetoJson.getString("nombreProducto"), 
										 objetoJson.getString("cantidad"));
					
					listSales.add(cli);
					
					//Log.e(LOGTAG, cli.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			ArrayAdapter<SalePerClient> adaptador = new ArrayAdapter<SalePerClient>(contexto, android.R.layout.simple_list_item_1, listSales);
			
			return adaptador;
		}
		
	}
	
	
	
	
}
