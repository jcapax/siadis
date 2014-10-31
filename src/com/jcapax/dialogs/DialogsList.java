package com.jcapax.dialogs;

import com.jcapax.siadis.NewClientActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;


public class DialogsList extends DialogFragment{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
 
        builder.setMessage("¿Desea Registrar un Nuevo Cliente?")
        	.setTitle("Confirmacion")
        	.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
               public void onClick(DialogInterface dialog, int id) {
	            	   Intent intent = new Intent(getActivity().getBaseContext(), NewClientActivity.class);
	   				   startActivity(intent);
           	   
	                   dialog.cancel();
                   }
               })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmacion Cancelada.");
                        dialog.cancel();
                   }
               });
 
        return builder.create();
		
		//return super.onCreateDialog(savedInstanceState);
	}
	
	
}
