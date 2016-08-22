package com.github.spezifisch.sensorrawlogger;

import android.location.GpsSatellite;
import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

public class Logger {
    public static void loc(long now, Location loc) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "loc");
            object.put("time", now);
            object.put("loctime", loc.getTime());
            object.put("lat", loc.getLatitude());
            object.put("lon", loc.getLongitude());
            object.put("alt", loc.getAltitude());
            object.put("bea", loc.getBearing());
            object.put("spd", loc.getSpeed());
            object.put("acc", loc.getAccuracy());
            object.put("prv", loc.getProvider());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public static void sat(long now, GpsSatellite gs) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "sat");
            object.put("time", now);
            object.put("prn", gs.getPrn());
            object.put("azi", gs.getAzimuth());
            object.put("ele", gs.getElevation());
            object.put("snr", gs.getSnr());
            object.put("alm", gs.hasAlmanac());
            object.put("eph", gs.hasEphemeris());
            object.put("fix", gs.usedInFix());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public static void fix(long now,
                           int satsTotal, int satsInFix,
                           int gpsTotal, int gpsInFix) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "fix");
            object.put("time", now);
            object.put("satsTotal", satsTotal);
            object.put("satsInFix", satsInFix);
            object.put("gpsTotal", gpsTotal);
            object.put("gpsInFix", gpsInFix);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }

    public static void event(long now, String evt) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "event");
            object.put("time", now);
            object.put("event", evt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(object);
    }
}
