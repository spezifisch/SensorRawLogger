package com.github.spezifisch.sensorrawlogger.Input;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.github.spezifisch.sensorrawlogger.Logger;

public class GpsInput extends Service implements LocationListener, GpsStatus.Listener {
    private static GpsInput ourInstance = new GpsInput();
    public static GpsInput getInstance() {
        return ourInstance;
    }

    private GpsInput() {
    }

    private static final String TAG = "GpsInput";
    protected Context context;
    protected boolean success = false;
    protected LocationManager locationManager;
    protected GpsStatus gpsStatus;

    public void init(Context c) {
        context = c;

        Log.d(TAG, "GpsInput init");

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        gpsStatus = locationManager.getGpsStatus(null);

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            locationManager.addGpsStatusListener(this);

            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            success = true;

            Log.d(TAG, "Last Location: " + loc);
        } catch (SecurityException e) {
            success = false;
            Log.e(TAG, "No GPS permission");
            e.printStackTrace();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Logger.loc(System.currentTimeMillis(), location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Logger.event(System.currentTimeMillis(), "onProviderDisabled/" + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Logger.event(System.currentTimeMillis(), "onProviderEnabled/" + provider);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Logger.event(System.currentTimeMillis(), "onStatusChanged/" + provider + "/" + status);
    }

    @Override
    public void onGpsStatusChanged(int event) {
        locationManager.getGpsStatus(gpsStatus);
        long now = System.currentTimeMillis();

        switch (event) {
            case GpsStatus.GPS_EVENT_STARTED:
                Logger.event(now, "GPS_EVENT_STARTED");
                break;

            case GpsStatus.GPS_EVENT_STOPPED:
                Logger.event(now, "GPS_EVENT_STOPPED");
                break;

            case GpsStatus.GPS_EVENT_FIRST_FIX:
                int ttf = gpsStatus.getTimeToFirstFix();
                Logger.event(now, "GPS_EVENT_FIRST_FIX/" + ttf);
                break;

            case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                Logger.event(now, "GPS_EVENT_SATELLITE_STATUS");

                int satsTotal = 0, satsInFix = 0, gpsTotal = 0, gpsInFix = 0;
                for (GpsSatellite gs: gpsStatus.getSatellites()) {
                    Logger.sat(now, gs);

                    satsTotal++;
                    if (gs.usedInFix()) {
                        satsInFix++;
                    }
                    if (gs.getPrn() < 50) {
                        gpsTotal++;
                        if (gs.usedInFix()) {
                            gpsInFix++;
                        }
                    }
                }

                Logger.fix(now, satsTotal, satsInFix, gpsTotal, gpsInFix);
                break;

        }
    }
}
