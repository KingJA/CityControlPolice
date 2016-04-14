package com.tdr.citycontrolpolice.util;


import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {


    public static void myToastNoImg(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
