package com.leagueofworkout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class SpinnerLista implements OnItemSelectedListener {
	private String x;
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		x=parent.getItemAtPosition(pos).toString();
	}
	public String getSpinnerName(){
		return x;
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}
