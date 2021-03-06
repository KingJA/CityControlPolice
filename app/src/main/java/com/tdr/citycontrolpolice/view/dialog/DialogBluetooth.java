package com.tdr.citycontrolpolice.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BluetoothAdapter;
import com.tdr.citycontrolpolice.entity.BluetoothBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 9:51
 * 修改备注：
 */
public class DialogBluetooth extends DialogBaseAlert implements AdapterView.OnItemClickListener {
    //    @Bind(R.id.lv_bluetooth)
//    ListView lvBluetooth;
    /*    @Bind(R.id.tv_scan)
        TextView tvScan;*/
    private List<BluetoothBean> list = new ArrayList<>();
    private OnBuletoothListener onBuletoothListener;
    private BluetoothAdapter bluetoothAdapter;
    private ListView lv_bluetooth;

    public DialogBluetooth(Context context, List<BluetoothBean> list) {
        super(context);
        this.list = list;
    }

//    @OnItemClick(R.id.lv_bluetooth)
//    void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (onBuletoothListener != null) {
//            onBuletoothListener.onBluetoothItemClick(parent, view, position, id);
//            dismiss();
//        }
//    }

/*    @OnClick(R.id.tv_scan)
    void onScan() {
        if (onBuletoothListener != null) {
            onBuletoothListener.onScan();
        }
    }*/

    @Override
    public void initView() {
        setContentView(R.layout.dialog_bluetooth);
        lv_bluetooth = (ListView) findViewById(R.id.lv_bluetooth);
//        ButterKnife.bind(this);
        bluetoothAdapter = new BluetoothAdapter(context, list);
        lv_bluetooth.setAdapter(bluetoothAdapter);
        lv_bluetooth.setOnItemClickListener(this);

    }

    @Override
    public void initNet() {
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void childClick(View v) {

    }

    public void refresh(List<BluetoothBean> list) {
        bluetoothAdapter.setData(list);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onBuletoothListener != null) {
            onBuletoothListener.onBluetoothItemClick(parent, view, position, id);
            dismiss();
        }
    }

    public interface OnBuletoothListener {
        void onBluetoothItemClick(AdapterView<?> parent, View view, int position, long id);

        void onScan();
    }

    public void setOnBuletoothListener(OnBuletoothListener onBuletoothListener) {
        this.onBuletoothListener = onBuletoothListener;
    }
}
