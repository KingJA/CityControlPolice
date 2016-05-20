package com.tdr.citycontrolpolice.fragment;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.BoxActivity;
import com.tdr.citycontrolpolice.activity.CzfInfoActivity;
import com.tdr.citycontrolpolice.activity.CzfQueryActivity;
import com.tdr.citycontrolpolice.activity.KjLoginActivity;
import com.tdr.citycontrolpolice.activity.NfcActivity;
import com.tdr.citycontrolpolice.activity.PersonCheckActivity;
import com.tdr.citycontrolpolice.adapter.HomeAdapter;
import com.tdr.citycontrolpolice.base.BaseFragment;
import com.tdr.citycontrolpolice.czfinit.CzfInitActivity;
import com.tdr.citycontrolpolice.net.DownloadDbManager;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.TendencyEncrypt;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;
import com.tdr.citycontrolpolice.view.NoScrollGridView;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogNFC;
import com.tdr.citycontrolpolice.view.popupwindow.FixedGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
public class TabHomeFragment extends BaseFragment implements DialogNFC.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "TabHomeFragment";
    private String[] titles = {"出租房绑定", "出租房信息", "出租房查询", "身份认证", "货品箱开启", "房东变更", "工作统计", "更新字典"};
    private int[] imgs = {R.drawable.bg_czfbd, R.drawable.bg_saoyisao, R.drawable.bg_czfcx, R.drawable.bg_ryhc, R.drawable.bg_box_on, R.drawable.bg_fdbg, R.drawable.bg_gztj, R.drawable.bg_gxzd};
    private final static int SCANNIN_GREQUEST_CODE = 2002;
    private final static int SCANNIN_CZF_CODE = 2003;
    private final static int UPDATA = 1001;
    private ZProgressHUD progressHUD;
    private String newcode;
    private Gson gson = new Gson();
    private final static int ERROR = 4001;
    private int dataspage = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            switch (msg.what) {
                /**
                 * 设备登记
                 */
                case SCANNIN_CZF_CODE:
                    if (msg.getData().getInt("error") == 0) {
                        progressHUD.dismiss();
                        //TODO
                        intent.setClass(mActivity, CzfInitActivity.class);
                        intent.putExtra("DEVICECODE", newcode);
                        intent.putExtra("DEVICETYPE", "0002");
                        startActivity(intent);
                    }
                    if (msg.getData().getInt("error") == 1) {
                        progressHUD.dismiss();
                        Toast.makeText(mActivity, "已登记", Toast.LENGTH_LONG).show();
                    } else {
                        progressHUD.dismiss();
                    }
                    break;
                /**
                 * 查询设备
                 */
                case SCANNIN_GREQUEST_CODE:
                    if (msg.getData().getInt("error") == 1) {
                        progressHUD.dismiss();
                        String content = msg.getData().getString("content");
                        try {
                            JSONObject rootObject = new JSONObject(content);
                            intent.setClass(mActivity, CzfInfoActivity.class);
                            intent.putExtra("HouseID", rootObject.getString("OTHERID"));
                            startActivity(intent);
                        } catch (JSONException e) {
                            Toast.makeText(mActivity, "JSON解析出错", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    } else if (msg.getData().getInt("error") == 0) {
                        progressHUD.dismiss();
                        Toast.makeText(mActivity, "号牌未登记", Toast.LENGTH_LONG).show();
                    } else if (msg.getData().getInt("error") == 2) {
                        progressHUD.dismiss();

                        ToastUtil.showMyToast("登录失效，请重新登录！");
                        Intent reLoginIntent = new Intent(mActivity, KjLoginActivity.class);
                        mActivity.startActivity(reLoginIntent);
                        mActivity.finish();
                    } else {
                        progressHUD.dismiss();
                        Toast.makeText(mActivity, msg.getData().getString("resultText"), Toast.LENGTH_LONG).show();
                    }
                    break;
                case ERROR:
                    Toast.makeText(mActivity, "网络异常", Toast.LENGTH_LONG).show();
                    break;
                case DownloadDbManager.Done_Basic_JuWeiHui:
                    ToastUtil.showMyToast("数据库更新成功");
                    break;
            }
        }
    };
    private DialogNFC dialogNFC;
    private DialogDouble dialogDouble;
    private HomeAdapter homeAdapter;

    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.fragment_home, null);
        init_view(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == mActivity.RESULT_OK) {
                    // Bundle bundle = data.getExtras();
                    // mTextView.setText(bundle.getString("result"));
                    progressHUD.setMessage("数据请求中");
                    progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);
                    progressHUD.show();
                    inquireDevice(data, requestCode);

                }
                break;
            case SCANNIN_CZF_CODE:
                if (resultCode == mActivity.RESULT_OK) {
                    progressHUD.setMessage("数据请求中");
                    progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);
                    progressHUD.show();
                    inquireDevice(data, requestCode);
                }
                break;
        }
    }

    /**
     * 初始化控件
     */
    private void init_view(View v) {
        dialogDouble = new DialogDouble(getActivity(), "是否要进行数据升级?", "确定", "取消");
        dialogNFC = new DialogNFC(getActivity());
        dialogNFC.setOnClickListener(this);
        progressHUD = new ZProgressHUD(mActivity);
        FixedGridView gv_top = (FixedGridView) v.findViewById(R.id.gv_home_top);
        homeAdapter = new HomeAdapter(getActivity(), titles, imgs);
        gv_top.setAdapter(homeAdapter);
        gv_top.setOnItemClickListener(this);
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                DownloadDbManager.getInstance(getActivity(), mHandler).startDownloadDb();
                ToastUtil.showMyToast("数据库进入后台更新");
            }

            @Override
            public void onRight() {

            }
        });
    }

    public static boolean nfcAble(Context context) {
        NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
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

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
//                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                if (nfcAble(getActivity())) {
                    ActivityUtil.goActivity(getActivity(), NfcActivity.class);
                }
                break;
            case 1:

                ActivityUtil.goActivity(getActivity(), PersonCheckActivity.class);

                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        switch (position) {
            case 0:
//                    intent.setClass(mActivity, zbar.CaptureActivity.class);
//                    startActivityForResult(intent, SCANNIN_CZF_CODE);
                ActivityUtil.goActivity(getActivity(), CzfInitActivity.class);
                break;
            case 1:
                intent.setClass(mActivity, zbar.CaptureActivity.class);
//                    intent.setClass(mActivity, com.zbar.lib.CaptureActivity.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case 2:
                ActivityUtil.goActivity(mActivity, CzfQueryActivity.class);
                break;

            case 3:
                dialogNFC.show();
                break;
            case 4:
//                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                ActivityUtil.goActivity(mActivity, BoxActivity.class);
                break;
            case 5:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case 6:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            case 7:
                dialogDouble.show();
                break;
        }
    }

    /**
     * 设备查询
     */
    private void inquireDevice(Intent data, final int requestCode) {
        Bundle bundle = data.getExtras();
        String result = bundle.getString("result");
        Log.i(TAG, "Camera result: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if (type.equals("AD")) {
            String base = result.substring(2);
            byte[] s = TendencyEncrypt.decode(base.getBytes());
            String code = TendencyEncrypt.bytesToHexString(s);
            Log.i(TAG, "TendencyEncrypt code: " + code);
            newcode = code.substring(0, 6) + code.substring(9);
            int i = newcode.length();
            newcode = newcode.substring(0, i - 4);
            Log.e("i", newcode);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final JSONObject object = new JSONObject();
                    try {
                        object.put("TaskID", "1");
                        object.put("DEVICETYPE", "2");
                        object.put("DEVICECODE", newcode);
                        Log.e("code", object.toString());
                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put("token", UserService.getInstance(mActivity).getToken());
                        param.put("encryption", 0);
                        param.put("dataTypeCode", "Common_InquireDevice");
                        param.put("content", object.toString());
                        String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                        Log.e("code", result);
                        JSONObject rootObject = new JSONObject(result);
                        int error = rootObject.getInt("ResultCode");
                        Message msg = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("error", error);
                        bundle.putString("content", rootObject.getString("Content"));
                        bundle.putString("resultText", rootObject.getString("ResultText"));
                        msg.setData(bundle);
                        msg.what = requestCode;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("code", e.toString());
                    }
                }
            }).start();
        } else {
            Toast.makeText(mActivity, "非指定设备", Toast.LENGTH_LONG).show();
            progressHUD.dismiss();
        }
    }

}
