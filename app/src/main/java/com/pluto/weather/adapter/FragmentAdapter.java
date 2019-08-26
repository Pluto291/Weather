package com.pluto.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment>fragments;
    
    public FragmentAdapter(FragmentManager fm,List<Fragment>fragments){
        super(fm);
        this.fragments=fragments;
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
