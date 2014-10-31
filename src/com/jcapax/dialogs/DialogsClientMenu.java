package com.jcapax.dialogs;

import com.jcapax.siadis.SaleActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class DialogsClientMenu extends DialogFragment{
	
	int option = 0;
	private String clientName;
	private String idClient;
	private String tipo;
	
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getIdClient() {
		return idClient;
	}

	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		/*
		 * venta = 1
		 * recuperacion = 2
		 * otros = 3
		 * 
		 */
		final String[] menu = {"Venta", "Recuperacion", "Saldos"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		
		builder.setTitle(clientName)
        .setSingleChoiceItems(menu, -1,
                 new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int item) {
            	  
            	  switch (item){
            	  	case 0:
	            		  Intent intentSale = new Intent(getActivity().getBaseContext(), SaleActivity.class);
	            		  intentSale.putExtra("idClient", idClient);
	            		  intentSale.putExtra("clientName", clientName);
	            		  intentSale.putExtra("tipo", "1"); // VENTA DE PRODUCTO 
		   				  startActivity(intentSale);	
		   				  
	                break;
	                
            	  	case 1:
	            		  Intent intentRecup = new Intent(getActivity().getBaseContext(), SaleActivity.class);
	            		  intentRecup.putExtra("idClient", idClient);
	            		  intentRecup.putExtra("clientName", clientName);
	            		  intentRecup.putExtra("tipo", "4");  // RECUPERACION DE ENVASES 
		   				  startActivity(intentRecup);	
            	  		
            	  		
            	  	break;
            	  	
            	  	default:
            	  	break;
	               
            	  }
            	  
            	  
            		  
            	                   
              }
          });
		
		
		//return super.onCreateDialog(savedInstanceState);
		
		return builder.create();
		
		
	}

}
