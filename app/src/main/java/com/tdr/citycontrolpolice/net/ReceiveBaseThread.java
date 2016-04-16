package com.tdr.citycontrolpolice.net;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/16 15:36
 * 修改备注：
 */
public class ReceiveBaseThread extends Thread {
    protected static final String TAG = "ReceiveBaseThread";
    protected BluetoothSocket stock;
    protected Handler handler;
    protected boolean running = true;

    public ReceiveBaseThread(BluetoothSocket stock, Handler handler) {

        this.stock = stock;
        this.handler = handler;
    }

    public void cancel() {
        try {
            stock.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect " + stock + " socket failed", e);
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
