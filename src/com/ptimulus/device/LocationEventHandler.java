package com.ptimulus.device;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.ptimulus.PtimulusActivity;
import com.ptimulus.log.IPtimulusLogger;
import com.ptimulus.log.LogEntryType;

/**
 * Handles location events and log them.
 * @author nicolas
 *
 */
public class LocationEventHandler implements LocationListener {
	
	private static PtimulusActivity ptimulusActivity;
	
	List<IPtimulusLogger> loggers;

	private LocationManager gps;
	private String locationProvider;
	
	public LocationEventHandler(Context ctx, List<IPtimulusLogger> loggers) {
		this.loggers = loggers;

		// set up gps
		gps = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_FINE);
		c.setAltitudeRequired(true);
		c.setSpeedRequired(true);
		locationProvider = gps.getBestProvider(c, true);

		Toast.makeText(ctx, "LocationEventHandler just created", Toast.LENGTH_LONG).show();
	}

	public void start() {
		gps.requestLocationUpdates(locationProvider, 0, 0, this);
	}

	public void onLocationChanged(Location l) {
		
		String text = l.getLatitude() + "," + l.getLongitude() + " " + l.getAltitude();
		long ts = l.getTime();
		
		for (IPtimulusLogger listener : loggers)
			listener.logDataEvent(LogEntryType.GPS, text, ts);
		
		if(ptimulusActivity != null) {
			ptimulusActivity.updateLocation(text, ts);
		}
	}

	public void stop() {
		gps.removeUpdates(this);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub	
	}
	
	public static void registerActivity(PtimulusActivity activity) {
		ptimulusActivity = activity;
	}
}