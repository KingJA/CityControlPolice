package com.tdr.citycontrolpolice.activity;

import android.bluetooth.BluetoothDevice;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BluetoothAdapter;
import com.tdr.citycontrolpolice.entity.BluetoothBean;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/1 15:13
 * 修改备注：
 */
public class BluetoothChangeActivity extends BackTitleActivity implements AdapterView.OnItemClickListener {

    private ListView lv_bluetooth;
    private BluetoothAdapter bluetoothAdapter;
    private android.bluetooth.BluetoothAdapter defaultAdapter;
    private List<BluetoothBean> boundDevices = new ArrayList<>();
    private String saveAddress;
    private TextView tv_submit;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_bluetooth_change, null);
        return view;
    }

    @Override
    public void initVariables() {
        saveAddress = (String) SharedPreferencesUtils.get("BLUETOOTH", "");
        initBluetooth();
    }

    @Override
    protected void initView() {
        tv_submit = (TextView) view.findViewById(R.id.tv_submit);
        lv_bluetooth = (ListView) view.findViewById(R.id.lv_bluetooth);
        lv_bluetooth = (ListView) findViewById(R.id.lv_bluetooth);
        bluetoothAdapter = new BluetoothAdapter(this, boundDevices);
        lv_bluetooth.setAdapter(bluetoothAdapter);
        lv_bluetooth.setOnItemClickListener(this);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(saveAddress)) {
                    ToastUtil.showMyToast("更换蓝牙设备成功");
                    SharedPreferencesUtils.put("BLUETOOTH", saveAddress);
                    finish();
                } else {
                    ToastUtil.showMyToast("未选择蓝牙设备");
                }


            }
        });
    }

    @Override
    public void setData() {
        setTitle("更换蓝牙设备");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bluetoothAdapter.checkPosition(position);
        BluetoothBean bean = (BluetoothBean) parent.getItemAtPosition(position);
        saveAddress = bean.getAddress();

    }

    /**
     * 初始化蓝牙
     */
    private void initBluetooth() {
        /**
         * 获取并打开蓝牙设备
         */
        defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            ToastUtil.showMyToast("没有蓝牙设备");
        } else if (!defaultAdapter.enable()) {
            defaultAdapter.enable();
        }
        /**
         * 将已经配对的设备存入列表
         */
        boundDevices = getBoundDevices();
        for (BluetoothBean bean : boundDevices) {
            if (bean.getAddress().equals(saveAddress)) {
                bean.setChecked(true);
            }
        }
    }

    /**
     * 获取已经绑定的蓝牙设备
     *
     * @return
     */
    private List<BluetoothBean> getBoundDevices() {
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
        List<BluetoothBean> boundDevices = new ArrayList<>();
        if (bondedDevices.size() > 0) {
            for (BluetoothDevice device : bondedDevices) {
                BluetoothBean bluetoothBean = new BluetoothBean();
                bluetoothBean.setAddress(device.getAddress());
                bluetoothBean.setName(device.getName());
                boundDevices.add(bluetoothBean);
            }
            return boundDevices;
        }
        return boundDevices;
    }
}
