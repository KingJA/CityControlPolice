package com.tdr.citycontrolpolice.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.tdr.citycontrolpolice.R;


public class DialogProgressX extends AlertDialog {

    /**
     * @param context
     */
    public DialogProgressX(Context context) {
        super(context, R.style.dialog_progress);
        this.setCancelable(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_x);
    }

}
