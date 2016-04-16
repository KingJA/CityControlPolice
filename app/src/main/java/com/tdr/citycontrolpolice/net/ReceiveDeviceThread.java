package com.tdr.citycontrolpolice.net;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：处理蓝牙信息的子线程
 * 创建人：KingJA
 * 创建时间：2016/4/2 15:26
 * 修改备注：
 */
public class ReceiveDeviceThread extends Thread {
    private static final String TAG = "ReceiveThread";
    private BluetoothSocket stock;
    private Handler handler;
    private boolean running = true;

    public ReceiveDeviceThread(BluetoothSocket stock, Handler handler) {

        this.stock = stock;
        this.handler = handler;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = stock.getInputStream();
            int count = 0;
            while (running) {
                if (inputStream.available() != 0) {
                    count = inputStream.available();
                    Log.i(TAG, "count: " + count);
                    byte[] totleByte = new byte[count];
                    inputStream.read(totleByte);

                    if (totleByte.length == 22) {
                        byte[] order = Arrays.copyOfRange(totleByte, 5, 6);
                        if (order[0] != (byte) (0xb6)) {
                            Log.i(TAG, "不是基站信息HexString: " + BluetoothUtil.bytesToHexString(totleByte));
                            continue;
                        }
                        String HexString = BluetoothUtil.bytesToHexString(totleByte);
                        Log.i(TAG, "HexString: " + HexString);
                        byte[] number = Arrays.copyOfRange(totleByte, 11, 15);
                        String NoHexString = BluetoothUtil.bytesToHexString(number);
                        Log.i(TAG, "NoHexString: " + NoHexString);

                        String deviceNO = String.valueOf(Integer.parseInt(NoHexString, 16));
                        Log.i(TAG, "deviceNO: " + deviceNO);

                        Message msg = handler.obtainMessage();
                        msg.obj = deviceNO;
                        msg.what = 100;
                        handler.sendMessage(msg);
                        break;
                    }
                } else {
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG, this.hashCode() + "ReceiveThread run: 结束 ");
    }

    public void cancel() {
        try {
            stock.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect " + stock + " socket failed", e);
        }
    }
}
