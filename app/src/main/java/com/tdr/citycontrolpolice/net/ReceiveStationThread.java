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
public class ReceiveStationThread extends ReceiveBaseThread {
    private static final String TAG = "ReceiveStationThread";

    public ReceiveStationThread(BluetoothSocket stock, Handler handler) {
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
                        Log.i(TAG, "基站HexString: " + NoHexString);
                        String deviceNO = String.valueOf(Long.valueOf(NoHexString, 16));
                        Log.i(TAG, "基站编号: " + deviceNO);
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

}
