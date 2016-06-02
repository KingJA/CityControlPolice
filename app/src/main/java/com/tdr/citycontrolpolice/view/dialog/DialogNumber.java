package com.tdr.citycontrolpolice.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.ToastUtil;


public class DialogNumber extends AlertDialog implements DialogInterface.OnDismissListener {

    private static final String TAG = "DialogNumber";
    private TextView tv_countDown;
    private Handler handler;

    /**
     * @param context
     */
    public DialogNumber(Context context, Handler handler) {
        super(context, R.style.dialog_progress);
        this.handler = handler;
        this.setCancelable(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_number);
        tv_countDown = (TextView) findViewById(R.id.tv_countDown);
        this.setOnDismissListener(this);
    }

    @Override
    public void show() {
        super.show();
        handler.post(runnable);
    }

    public void setTimeDown(int time) {
        Log.i(TAG, "time: " + time);
        tv_countDown.setText(time + "");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        close();
        Log.i(TAG, "关闭: ");
    }

    private int count = 16;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count > -1) {
                setTimeDown(count);
                count--;
                handler.postDelayed(this, 1000);
            } else {
                ToastUtil.showMyToast("未搜索到基站设备");
                close();
            }
        }
    };

    public void close() {
        handler.removeCallbacks(runnable);
        handler.sendEmptyMessage(300);
        count = 16;
        dismiss();
    }

    public void finish() {
        handler.removeCallbacks(runnable);
        count = 16;
        dismiss();
    }

}
