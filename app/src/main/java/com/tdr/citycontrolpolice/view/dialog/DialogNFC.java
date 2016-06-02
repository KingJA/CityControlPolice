package com.tdr.citycontrolpolice.view.dialog;


import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;


public class DialogNFC extends DialogBaseAlert {
    private LinearLayout ll_nfc;
    private LinearLayout ll_card;
    private OnClickListener onClickListener;

    public DialogNFC(Context context) {
        super(context);
    }


    @Override
    public void initView() {
        setContentView(R.layout.dialog_nfc);
        ll_nfc = (LinearLayout) findViewById(R.id.ll_nfc);
        ll_card = (LinearLayout) findViewById(R.id.ll_card);
        setFullWidth();

    }

    private void setFullWidth() {
        Window win = this.getWindow();
        this.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initEvent() {
        ll_nfc.setOnClickListener(this);
        ll_card.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }


    @Override
    public void childClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nfc:
                if (onClickListener != null) {
                    onClickListener.onNfcClick(0);
                }
                break;
            case R.id.ll_card:
                if (onClickListener != null) {
                    onClickListener.onNfcClick(1);
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
        void onNfcClick(int position);
    }

}
