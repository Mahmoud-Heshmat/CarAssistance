package com.h2m.carassistance.Utitls;

import android.support.design.widget.Snackbar;
import android.view.View;

public class Utility {
    public static void snackBar(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
