package com.tdr.citycontrolpolice.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BluetoothAdapter;
import com.tdr.citycontrolpolice.entity.BluetoothBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 9:51
 * 修改备注：
 */
public class DialogBluetooth extends DialogBaseAlert {
    @Bind(R.id.lv_bluetooth)
    ListView lvBluetooth;
    /*    @Bind(R.id.tv_scan)
        TextView tvScan;*/
    private List<BluetoothBean> list = new ArrayList<>();
    private OnBuletoothListener onBuletoothListener;
    private BluetoothAdapter bluetoothAdapter;

    public DialogBluetooth(Context context, List<BluetoothBean> list) {
        super(context);
        this.list = list;
    }

    @OnItemClick(R.id.lv_bluetooth)
    void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onBuletoothListener != null) {
            onBuletoothListener.onBluetoothItemClick(parent, view, position, id);
            dismiss();
        }
    }

/*    @OnClick(R.id.tv_scan)
    void onScan() {
        if (onBuletoothListener != null) {
            onBuletoothListener.onScan();
        }
    }*/

    @Override
    public void initView() {
        setContentView(R.layout.dialog_bluetooth);
        ButterKnife.bind(this);
        bluetoothAdapter = new BluetoothAdapter(context, list);
        lvBluetooth.setAdapter(bluetoothAdapter);

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

    public interface OnBuletoothListener {
        void onBluetoothItemClick(AdapterView<?> parent, View view, int position, long id);

        void onScan();
    }

    public void setOnBuletoothListener(OnBuletoothListener onBuletoothListener) {
        this.onBuletoothListener = onBuletoothListener;
    }
}
