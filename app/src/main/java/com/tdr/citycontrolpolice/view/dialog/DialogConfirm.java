package com.tdr.citycontrolpolice.view.dialog;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;


public class DialogConfirm extends DialogBaseAlert {
    private Context context;
    private String title;
    private TextView tv_message;
    private TextView tv_confirm;
    private LinearLayout ll_confirm;
    private String message;
    private String confirmString;
    private OnConfirmClickListener onConfirmClickListener;

    public DialogConfirm(Context context, String message, String confirmString) {
        super(context);
        this.context = context;
        this.message = message;
        this.confirmString = confirmString;
    }

    @Override
    public void initView() {
        setContentView(R.layout.dialog_confirm);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);
        ll_confirm = (LinearLayout) findViewById(R.id.ll_confirm);

    }

    @Override
    public void initNet() {

    }

    @Override
    public void initEvent() {
        ll_confirm.setOnClickListener(this);

    }

    @Override
    public void initData() {
        tv_message.setText(message);
        tv_confirm.setText(confirmString);

    }


    @Override
    public void childClick(View v) {
        dismiss();
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onConfirm();
        }

    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void onConfirm();
    }

}
