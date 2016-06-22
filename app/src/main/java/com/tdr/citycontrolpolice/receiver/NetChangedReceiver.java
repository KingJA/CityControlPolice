package com.tdr.citycontrolpolice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.OCR_Kj;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.NetUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：监听网络状态
 * 创建人：KingJA
 * 创建时间：2016/6/20 10:18
 * 修改备注：
 */

/**
 *
 * 程序第一次启动时会收到一个Broadcast
 从 GPRS 到 WIFI，程序至少会收到3个Broadcast
 第一个是连接到WIFI
 第二个是断开GPRS
 第三个是连接到WIFI
 从WIFI到GPRS，程序至少会收到2个Broadcast
 第一个是断开Wifi
 第二个是连接到GPRS
 */
public class NetChangedReceiver extends BroadcastReceiver {
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (NetUtil.netAvailable()) {
            ToastUtil.showMyToast("当前网络:" + NetUtil.getNetworkStringType());
            uploadData();
        } else {
            ToastUtil.showMyToast("没有网络");
            Log.e("onReceive", "没有网络");
        }
    }

    private void uploadData() {
        List<OCR_Kj> queue = DbDaoXutils3.getInstance().selectAll(OCR_Kj.class);
        Log.i("uploadData", queue.toString());
        if (queue.size() > 0) {
            for (OCR_Kj info : queue) {
                doNet(info);
            }
        }
    }


    private void doNet(final OCR_Kj info) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", info.getTaskID());
        param.put("NAME", info.getNAME());
        param.put("SEX", info.getSEX());
        param.put("NATION", info.getNATION());
        param.put("BIRTHDAY", info.getBIRTHDAY());
        param.put("ADDRESS", info.getADDRESS());
        param.put("IDENTITYCARD", info.getIDENTITYCARD());
        param.put("IDENTITYCARDID", info.getIDENTITYCARDID());
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(context).getToken(), 0, "Common_IdentityCardAuthentication", param)
                .setBeanType(User_LoginByPolice.class)
                .setCallBack(new WebServiceCallBack<User_LoginByPolice>() {
                    @Override
                    public void onSuccess(User_LoginByPolice bean) {
                        Log.e("断网上传", "onSuccess: ");
                        DbDaoXutils3.getInstance().deleteById(OCR_Kj.class,info.getIDENTITYCARD());
                        EventBus.getDefault().post(new Object());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build().execute();
    }

}
