package com.tdr.citycontrolpolice.net;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;

import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.util.ToastUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/6 9:17
 * 修改备注：
 */
public class NfcUtil {
    public static boolean isEnable() {
        NfcManager manager = (NfcManager) App.getContext().getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();
        if (adapter == null) {
            ToastUtil.showMyToast("该设备不支持NFC");
            return false;
        }
        if (!adapter.isEnabled()) {
            ToastUtil.showMyToast("请在系统设置中开启NFC");
            return false;
        }
        return true;
    }
}
