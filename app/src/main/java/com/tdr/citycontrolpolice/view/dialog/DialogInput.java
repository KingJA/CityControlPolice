package com.tdr.citycontrolpolice.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.CheckUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/19 17:15
 * 修改备注：
 */
public class DialogInput extends Dialog {
    private static final String TAG = "DialogInput";
    private Activity activity;
    private Context context;
    private EditText mEtStationInput;
    private Button mBtnStationInput;
    private OnInputListener onInputListener;
    private String stationNo;


    public DialogInput(Context context) {

        super(context, R.style.CustomDialog);
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        initView();
        initEvent();
    }


    public void initView() {
        mEtStationInput = (EditText) findViewById(R.id.et_station_input);
        mBtnStationInput = (Button) findViewById(R.id.btn_station_input);
    }


    public void initEvent() {
        mBtnStationInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onInputListener != null) {
                    stationNo = mEtStationInput.getText().toString().trim();
                    if (CheckUtil.checkEmpty(stationNo,"请输入基站编号")){
                        dismiss();
                        onInputListener.onInput(stationNo);
                    }
                }
            }
        });
    }

    public interface OnInputListener {
        void onInput(String statioNo);
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }
}
