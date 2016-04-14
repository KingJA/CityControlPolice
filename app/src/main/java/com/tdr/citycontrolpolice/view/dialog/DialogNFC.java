package com.tdr.citycontrolpolice.view.dialog;


import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;


public class DialogNFC extends DialogBaseAlert {
    private TextView tv_nfc;
    private TextView tv_card;
    private OnClickListener onClickListener;

    public DialogNFC(Context context) {
        super(context);
    }


    @Override
    public void initView() {
        setContentView(R.layout.dialog_nfc);
        tv_nfc = (TextView) findViewById(R.id.tv_nfc);
        tv_card = (TextView) findViewById(R.id.tv_card);


    }

    @Override
    public void initNet() {

    }

    @Override
    public void initEvent() {
        tv_nfc.setOnClickListener(this);
        tv_card.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }


    @Override
    public void childClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nfc:
                if (onClickListener != null) {
                    onClickListener.onClick(0);
                }
                break;
            case R.id.tv_card:
                if (onClickListener != null) {
                    onClickListener.onClick(1);
                }
                break;
            default:
                break;

        }
        dismiss();

    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position);
    }

}
