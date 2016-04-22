package com.tdr.citycontrolpolice.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

import com.tdr.tendencynfc.util.Constants;

public class MessageReceiver extends BroadcastReceiver implements Callback {
	
	private Handler mHandler;

	private Intent i;

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("11111111111111111111111");
		String action = intent.getAction();
		if (action.equals("com.tdr.ecard.readcard")) {// E居卡
			Bundle bundle = intent.getBundleExtra("data");
			String e_tagId = bundle.getString(Constants.TAGID);
			String e_idCard = bundle.getString(Constants.ID_CARD);
			String e_phoneNum = bundle.getString(Constants.PHONE_NUM);
			String e_name = bundle.getString(Constants.E_NAME);
			String e_childId = bundle.getString(Constants.CHILD_ID);
			String e_childName = bundle.getString(Constants.CHILD_NAME);
			String e_currentAddress = bundle
					.getString(Constants.CURRENT_ADDRESS);
			String e_primaryAddress = bundle
					.getString(Constants.PRIMARY_ADDRESS);

			Constants.myToast(context,"E居卡ID：" + e_tagId + "\r\n" + "身份证号码："
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
