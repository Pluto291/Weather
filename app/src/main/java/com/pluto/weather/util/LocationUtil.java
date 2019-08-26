package com.pluto.weather.util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import java.util.ArrayList;
import java.util.List;

public class LocationUtil {
    public static List<String>getSavedAdCodes(Context context) {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences (context);
        String adCodes=pref.getString ("adCodes", null);

        if(adCodes != null) {
            String[]separatedAdCodes=adCodes.split (";");
            List<String>adCodesList=new ArrayList<> ();

            for(String adCode:separatedAdCodes) {
                adCodesList.add (adCode);
            }

            return adCodesList;
        } else {
            return null;
        }
    }

    public static void saveCoordinates(Context context, List<String>adCodes) {
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
        StringBuilder builder=new StringBuilder ();

        for(int i=0;i < adCodes.size ();i++) {
            if(i != adCodes.size () - 1) {
                builder.append (adCodes.get (i) + ";");
            } else {
                builder.append (adCodes.get (i));
            }
        }

        editor.putString ("adCodes", builder.toString ());
        editor.apply ();
    }

    public static String getLocalCoordinate(Context context) {
        Location location=null;
        LocationManager manager=(LocationManager)context.getSystemService (Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        if(manager.isProviderEnabled (LocationManager.NETWORK_PROVIDER)) {
            location = manager.getLastKnownLocation (LocationManager.NETWORK_PROVIDER);

            return location.getLatitude () + "," + location.getLongitude ();
        } else {
            return null;
        }
    }
}
