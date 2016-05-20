package com.tdr.citycontrolpolice.net;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：连接蓝牙的子线程
 * 创建人：KingJA
 * 创建时间：2016/4/2 15:23
 * 修改备注：
 */
public class ConnectBindThread extends Thread {
    private static final String TAG = "ConnectThread";
    private BluetoothDevice device;
    private BluetoothAdapter adapterr;
    private final String uuid;
    private final Handler handler;
    private boolean isStation;
    private BluetoothSocket stock;
    private ReceiveBaseThread receiveThread;

    public ConnectBindThread(BluetoothDevice device, BluetoothAdapter adapterr, String uuid, Handler handler, boolean isStation) {
        this.device = device;
        this.adapterr = adapterr;
        this.uuid = uuid;
        this.handler = handler;
        this.isStation = isStation;
        try {
            stock = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (adapterr.isDiscovering()) {
            adapterr.cancelDiscovery();
        }
        try {
            stock.connect();
            if (isStation) {
                receiveThread = new ReceiveStationThread(stock, handler);
            } else {
                receiveThread = new ReceiveDeviceThread(stock, handler);
            }

            receiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, this.hashCode() + "ConnectThread run: 结束 ");
    }

    public void cancel() {
        if (receiveThread != null) {
            receiveThread.setRunning(false);
            receiveThread.cancel();
            receiveThread = null;
        }
        try {

            stock.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect " + stock + " socket failed", e);
        }
    }
}
