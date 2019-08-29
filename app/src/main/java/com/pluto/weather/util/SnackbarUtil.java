package com.pluto.weather.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.View.OnClickListener;

public class SnackbarUtil
{
    private static Snackbar snackbar;
    
    public static void showSnackbarOnlyText(View v,String content){
        if(snackbar==null){
            snackbar=Snackbar.make(v,content,Snackbar.LENGTH_SHORT);
        }else{
            snackbar.setText(content);
        }
        
        snackbar.show();
    }
    
    public static void showSnackbarAddAction(View v,String content,String actionTip,View.OnClickListener action){
        if(snackbar==null){
            snackbar=Snackbar.make(v,content,Snackbar.LENGTH_SHORT);
        }else{
            snackbar.setText(content);
        }

        snackbar.setAction(actionTip,action);
        snackbar.show();
    }
}
