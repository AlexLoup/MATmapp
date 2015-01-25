package com.alexander.wolf.matmapp;

import android.location.*;
import android.os.Bundle;

public class locListener implements LocationListener{

	double latitude;
	double longitude;
	
	@Override
	public void onLocationChanged(Location location){
		if (location != null){
			latitude = (float) location.getLatitude();
			longitude = (float) location.getLongitude();
		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
}
