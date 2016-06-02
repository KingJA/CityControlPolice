package com.tdr.citycontrolpolice.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.tdr.citycontrolpolice.NfcConstants;


public class MessageReceiver extends BroadcastReceiver implements Callback {

	private static final String TAG = "MessageReceiver";
	private Handler mHandler;

	private Intent i;

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("11111111111111111111111");
		String action = intent.getAction();
		if (action.equals("com.tdr.ecard.readcard")) {// E居卡
			Bundle bundle = intent.getBundleExtra("data");
			String e_tagId = bundle.getString(NfcConstants.TAGID);
			String e_idCard = bundle.getString(NfcConstants.ID_CARD);
			String e_phoneNum = bundle.getString(NfcConstants.PHONE_NUM);
			String e_name = bundle.getString(NfcConstants.E_NAME);
			String e_childId = bundle.getString(NfcConstants.CHILD_ID);
			String e_childName = bundle.getString(NfcConstants.CHILD_NAME);
			String e_currentAddress = bundle
					.getString(NfcConstants.CURRENT_ADDRESS);
			String e_primaryAddress = bundle
					.getString(NfcConstants.PRIMARY_ADDRESS);

			Log.i(TAG, "E居卡ID：" + e_tagId + "\r\n" + "身份证号码："
					+ e_idCard + "\r\n" + "手机号码：" + e_phoneNum + "\r\n" + "姓名："
					+ e_name + "\r\n" + "儿童身份证号码：" + e_childId + "\r\n"
					+ "儿童姓名：" + e_childName + "\r\n" + "现住址："
					+ e_currentAddress + "\r\n" + "户籍住址：" + e_primaryAddress);
		}

	}

	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

}
