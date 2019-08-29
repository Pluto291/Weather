package com.pluto.weather.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.pluto.weather.R;
import com.pluto.weather.adapter.FragmentAdapter;
import com.pluto.weather.ui.fragment.WeatherFragment;
import com.pluto.weather.util.LocationUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private final String TAG="MainActivity";
    private final String[]PERMISSION={Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_PHONE_STATE};

    private Set<String>location;;
    private List<Fragment>fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        checkPermission ();
        initView ();
    }

    private void initView() {
        if(Build.VERSION.SDK_INT >= 21) {
            View decroView=getWindow ().getDecorView ();
            int options=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
            decroView.setSystemUiVisibility (options);

            getWindow ().setStatusBarColor (Color.TRANSPARENT);
            getWindow ().setNavigationBarColor (Color.TRANSPARENT);
        }

        ImageView bg_main=findViewById (R.id.bg_main);
        Glide.with (this).load (R.drawable.pic_bg).into (bg_main);
    }

    private void initViewPager() {
        ViewPager viewPager=findViewById (R.id.view_pager_main);

        location = LocationUtil.readLocationSet (this, "location");
        fragments = new ArrayList<> ();

        if(location == null) {
            location = new HashSet<> ();
            location.add (LocationUtil.getLocalCoordinate (this));
        }
        
        LocationUtil.saveLocationSet(this,"location",location);
        
        for(String location:location) {
            if(location != null) {
                fragments.add (new WeatherFragment (location));
            }
        }

        FragmentAdapter adapter=new FragmentAdapter (getSupportFragmentManager (), fragments);
        viewPager.setAdapter (adapter);
    }

    private void checkPermission() {
        for(String permission:PERMISSION) {
            if(ActivityCompat.checkSelfPermission (this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions (PERMISSION, 0);
                return;
            }
        }

        initViewPager ();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 0:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initViewPager ();
                } else {
                    Toast.makeText (MainActivity.this, "缺少权限，请前往授予", Toast.LENGTH_SHORT).show ();
                }
                break;
        }
    }
}
