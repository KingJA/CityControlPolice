package com.tdr.citycontrolpolice.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.tdr.citycontrolpolice.activity.HomeActivity;
import com.tdr.citycontrolpolice.activity.KjLoginActivity;
import com.tdr.citycontrolpolice.activity.PersonCheckActivity;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Common_IdentityCardAuthentication;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.OCR_Kj;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.NetUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

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
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build().execute();
    }

}
