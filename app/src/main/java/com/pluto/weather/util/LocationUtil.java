package com.pluto.weather.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import java.util.Set;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LocationUtil {
    public static String getLocalCoordinate(Context context) {
        Location location=null;
        LocationManager manager=(LocationManager)context.getSystemService (Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        if(manager.isProviderEnabled (LocationManager.NETWORK_PROVIDER)) {
            location = manager.getLastKnownLocation (LocationManager.NETWORK_PROVIDER);

            return location != null ? location.getLatitude () + "," + location.getLongitude (): null;
        } else {
            return null;
        }
    }

    public static void saveLocationSet(Context context, String key, Set<String>location) {
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
        editor.putStringSet (key, location);
        editor.apply ();
    }

    public static Set<String> readLocationSet(Context context, String key) {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences (context);
        Set<String>location=pref.getStringSet (key, null);

        return location != null ?location: null;
    }
}
