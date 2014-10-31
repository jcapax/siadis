package com.jcapax.fragments;


import com.jcapax.siadis.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CriterioCliente extends Fragment {
	
	Button btnSearch;
	EditText editSearch;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
				
		View view;
		view = inflater.inflate(R.layout.fragment_criterio_client, container);
		
		btnSearch = (Button)view.findViewById(R.id.btnSearch);
		editSearch = (EditText)view.findViewById(R.id.editSearch);
		
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

}
