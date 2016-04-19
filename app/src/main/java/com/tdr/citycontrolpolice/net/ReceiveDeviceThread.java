package com.tdr.citycontrolpolice.net;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
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
public class ReceiveDeviceThread extends ReceiveBaseThread {
    private static final String TAG = "ReceiveDeviceThread";

    public ReceiveDeviceThread(BluetoothSocket stock, Handler handler) {
        super(stock, handler);
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

                    if (totleByte.length == 19) {
                        byte[] order = Arrays.copyOfRange(totleByte, 5, 6);
                        if (order[0] != (byte) (0xb7)) {
                            Log.i(TAG, "不是基站信息HexString: " + BluetoothUtil.bytesToHexString(totleByte));
                            continue;
                        }
                        String HexString = BluetoothUtil.bytesToHexString(totleByte);
                        Log.i(TAG, "HexString: " + HexString);
                        byte[] type = Arrays.copyOfRange(totleByte, 9, 11);
                        byte[] number = Arrays.copyOfRange(totleByte, 11, 15);
                        String typeHexString = BluetoothUtil.bytesToHexString(type);
                        Log.i(TAG, "设备类型HexString: " + typeHexString);
                        String NoHexString = BluetoothUtil.bytesToHexString(number);
                        Log.i(TAG, "设备编号HexString: " + NoHexString);

                        String deviceType = String.valueOf(Long.valueOf(typeHexString, 16));
                        String deviceNO = String.valueOf(Long.valueOf(NoHexString, 16));
                        Log.i(TAG, "设备类型: " + deviceType);
                        Log.i(TAG, "设备编号: " + deviceNO);

                        Message msg = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("TYPE", deviceType);
                        bundle.putString("NO", deviceNO);
                        msg.obj = bundle;
                        msg.what = 200;
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

}
