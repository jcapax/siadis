package com.jcapax.dataConnect;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class HttpHandler {
	
private static final String LOGTAG = "LogsAndroid";
	
	public String globalURL = "http://192.168.123.205/sureguila/";
	//public String globalURL = "http://www.sidssucre.tk/";
	
	@SuppressLint("NewApi")
	public String autenticate(String _service, String _idDevice, String _patron){
		String _res = null;
		
		try{
	        
	        String _urlglobal = globalURL + _service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(_urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idDevice", _idDevice));
            params.add(new BasicNameValuePair("patron", _patron));
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			_res = EntityUtils.toString(ent);			
			
			return _res;
		}
		catch(Exception e){
			return "no ingresa";
		}
	} 	

	
	@SuppressLint("NewApi")
	public String getPriceProduct(String service, String tipePrice, String product){
		String priceProduct = "0";
		
		try{
			
			Log.e(LOGTAG, "param: "+service+" "+tipePrice+" "+product);
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String  url = globalURL + service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("product", product));
            params.add(new BasicNameValuePair("tipePrice", tipePrice));
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			priceProduct = EntityUtils.toString(ent);			
			
			return priceProduct.trim();
		}
		catch(Exception e){
			return "SIN SERVICIO";
		}
	} 
	
	
	@SuppressLint("NewApi")
	public String postIdcliente(String service, String idCliente){
		String nombreCliente = null;
		
		try{
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String  url = globalURL + service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idCliente", idCliente));
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			nombreCliente = EntityUtils.toString(ent);			
			
			return nombreCliente.trim();
		}
		catch(Exception e){
			return "SIN SERVICIO";
		}
	} 
	
	@SuppressLint("NewApi")
	public String saveClient(String service, String nameClient,
								String denominationClient,
								String direction, 
								String nro, String ruta, String zona, String canal, String tipoLocal,
								String telefono) {
		
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String urlglobal = globalURL + service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(urlglobal);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nombreCliente", nameClient));
            params.add(new BasicNameValuePair("denominacionCliente", denominationClient));
            params.add(new BasicNameValuePair("direccion", direction));            
            params.add(new BasicNameValuePair("nro", nro));
            params.add(new BasicNameValuePair("ruta", ruta));
            params.add(new BasicNameValuePair("zona", zona));
            params.add(new BasicNameValuePair("canal", canal));
            params.add(new BasicNameValuePair("tipoLocal", tipoLocal));
            params.add(new BasicNameValuePair("telefono", telefono));
            params.add(new BasicNameValuePair("fechaNacimiento", ""));

            Log.e(LOGTAG, nameClient);
            Log.e(LOGTAG, denominationClient);
            Log.e(LOGTAG, direction);
            Log.e(LOGTAG, nro);
            Log.e(LOGTAG, ruta);
            Log.e(LOGTAG, zona);
            Log.e(LOGTAG, canal);
            Log.e(LOGTAG, tipoLocal);
            Log.e(LOGTAG, telefono);
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			String idCliente = EntityUtils.toString(ent);
			
			Log.e(LOGTAG, "idCliente: "+idCliente);
			return idCliente;			
			
		}
		catch(Exception e){
			return "no ingresa";
		}
		
		
	}

	@SuppressLint("NewApi")
	public String saveSale(String service, String idDistributor, String idClient, String tipeSale, String idPrice, 
				String movement, String latitude, String longitude){
		String idSale = null;
		
		try{
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String  url = globalURL + service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idDistribuidor", idDistributor));
            params.add(new BasicNameValuePair("idCliente", idClient));
            params.add(new BasicNameValuePair("tipoVenta", tipeSale));
            params.add(new BasicNameValuePair("idPrecio", idPrice));
            params.add(new BasicNameValuePair("movimiento", movement));
            params.add(new BasicNameValuePair("longitud", longitude));
            params.add(new BasicNameValuePair("latitud", latitude));
           // params.add(new BasicNameValuePair("jsonList", jsonList));     
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	        
	        //Log.e(LOGTAG, "tipo venta: "+tipeSale);

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			idSale = EntityUtils.toString(ent);			
			
			return idSale.trim();
		}
		catch(Exception e){
			return "SIN SERVICIO";
		}
	} 

	@SuppressLint("NewApi")
	public String saveDetailSale(String service, String idSale, String productName, String cant, String totalPrice){
		
		String aux = null;
		
		try{
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String idProduct = "0";
	        idProduct = getIdProducto(productName);
	        
	        String  url = globalURL + service;
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("idVenta", idSale));
            params.add(new BasicNameValuePair("idProducto", idProduct));
            params.add(new BasicNameValuePair("cantidad", cant));
            params.add(new BasicNameValuePair("precioTotal", totalPrice));  
            
            //Log.e(LOGTAG, idSale +" - "+ idProduct+" - "+cant+" - "+totalPrice);
            
	        httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			aux = EntityUtils.toString(ent);			
			
			return aux.trim();
		}
		catch(Exception e){
			return "SIN SERVICIO";
		}
	}


	private String getIdProducto(String productName) {
		String idProduct = "0";
		
		try{
			
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        String  url = globalURL + "scripts/getidproduct.php";
	        
	        HttpClient httpclient = (HttpClient) new DefaultHttpClient();
			
			HttpPost httppost = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("producto", productName));

            httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse resp = httpclient.execute(httppost);			
			
			HttpEntity ent = resp.getEntity();
			
			idProduct = EntityUtils.toString(ent);			
			
			return idProduct.trim();
		}
		catch(Exception e){
			return "0";
		}	
		
	} 
	
	
	
	
}
