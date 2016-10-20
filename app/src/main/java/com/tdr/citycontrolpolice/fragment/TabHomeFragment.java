package com.tdr.citycontrolpolice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.BoxActivity;
import com.tdr.citycontrolpolice.activity.AttentionQueryActivity;
import com.tdr.citycontrolpolice.activity.CzfInfoActivity;
import com.tdr.citycontrolpolice.activity.CzfQueryActivity;
import com.tdr.citycontrolpolice.activity.KjLoginActivity;
import com.tdr.citycontrolpolice.NfcActivity;
import com.tdr.citycontrolpolice.activity.PersonCheckActivity;
import com.tdr.citycontrolpolice.adapter.HomeAdapter;
import com.tdr.citycontrolpolice.base.BaseFragment;
import com.tdr.citycontrolpolice.czfinit.CzfInitActivity;
import com.tdr.citycontrolpolice.net.DownloadDbManager;
import com.tdr.citycontrolpolice.net.NfcUtil;
import com.tdr.citycontrolpolice.util.GoUtil;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.QRCodeUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;
import com.tdr.citycontrolpolice.view.MyViewPager;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogNFC;
import com.tdr.citycontrolpolice.view.FixedGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/19.
 */
public class TabHomeFragment extends BaseFragment implements DialogNFC.OnClickListener, View.OnClickListener {
    private static final String TAG = "TabHomeFragment";
    private String[] page1Titles = {"出租房绑定", "出租房信息", "出租房查询", "身份认证", "货品箱开启", "登记牌变更", "出租房关注", "更新字典"};
    private String[] page2Titles = {"工作统计", "", "", "", ""};
    private int[] page1Imgs = {R.drawable.bg_czfbd, R.drawable.bg_saoyisao, R.drawable.bg_czfcx, R.drawable.bg_ryhc, R.drawable.bg_box_on, R.drawable.bg_fdbg, R.drawable.bg_czfgz, R.drawable.bg_gxzd};
    private int[] page2Imgs = {R.drawable.bg_gztj, R.drawable.t, R.drawable.t, R.drawable.t, R.drawable.t};


    private final static int SCANNIN_GREQUEST_CODE = 2002;
    private final static int SCANNIN_CZF_CODE = 2003;
    private ZProgressHUD progressHUD;
    private final static int ERROR = 4001;
    private DialogNFC dialogNFC;
    private DialogDouble dialogDouble;
    private HomeAdapter homeAdapter;
    private RelativeLayout rl_guide;
    private String deviceCode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent = new Intent();
            switch (msg.what) {
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
                case DownloadDbManager.LASTEST_VERSION:
                    ToastUtil.showMyToast("已经是最新版本");

                    break;
            }
        }
    };
    private HomeAdapter home2Adapter;


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
        rl_guide = (RelativeLayout) v.findViewById(R.id.rl_guide);
        rl_guide.setOnClickListener(this);
        dialogDouble = new DialogDouble(getActivity(), "是否要进行数据升级?", "确定", "取消");
        dialogNFC = new DialogNFC(getActivity());
        dialogNFC.setOnClickListener(this);
        progressHUD = new ZProgressHUD(mActivity);
        setupViewPager(v);
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                new DownloadDbManager(mHandler).startDownloadDb();
                ToastUtil.showMyToast("数据库后台更新");
            }

            @Override
            public void onRight() {

            }
        });
    }

    private void setupViewPager(View v) {
        MyViewPager vp_home = (MyViewPager) v.findViewById(R.id.vp_home);
        RelativeLayout rl_vp = (RelativeLayout) v.findViewById(R.id.rl_vp);
        List<View> viewList = new ArrayList<>();
        View pageView1 = View.inflate(getActivity(), R.layout.include_gv1, null);
        View pageView2 = View.inflate(getActivity(), R.layout.include_gv2, null);
        FixedGridView gv_page1 = (FixedGridView) pageView1.findViewById(R.id.gv_page1);
        FixedGridView gv_page2 = (FixedGridView) pageView2.findViewById(R.id.gv_page2);
        homeAdapter = new HomeAdapter(getActivity(), page1Titles, page1Imgs);
        home2Adapter = new HomeAdapter(getActivity(), page2Titles, page2Imgs);
        gv_page1.setAdapter(homeAdapter);
        gv_page2.setAdapter(home2Adapter);
        gv_page1.setOnItemClickListener(page1OnItemClickListener);
        gv_page2.setOnItemClickListener(page2OnItemClickListener);
        viewList.add(pageView1);
//        viewList.add(pageView2);
        vp_home.setContent(viewList, rl_vp);
    }


    @Override
    public void onNfcClick(int position) {
        switch (position) {
            case 0:
                if (NfcUtil.isEnable()) {
                    GoUtil.goActivity(getActivity(), NfcActivity.class);
                }
                break;
            case 1:
                GoUtil.goActivity(getActivity(), PersonCheckActivity.class);
                break;
            default:
                break;

        }
    }

    private AdapterView.OnItemClickListener page1OnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    GoUtil.goActivity(getActivity(), CzfInitActivity.class);
                    break;
                case 1:
                    Intent intent = new Intent();
                    intent.setClass(mActivity, zbar.CaptureActivity.class);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                    break;
                case 2:
                    GoUtil.goActivity(mActivity, CzfQueryActivity.class);
                    break;
                case 3:
                    dialogNFC.show();
                    break;
                case 4:
                    GoUtil.goActivity(mActivity, BoxActivity.class);
                    break;
                case 5:
                    ToastUtil.showMyToast("请进入出租房信息模块进行变更操作...");
                    break;
                case 6:
                    GoUtil.goActivity(getActivity(), AttentionQueryActivity.class);
                    break;
                case 7:
                    dialogDouble.show();
                    break;
            }
        }
    };
    private AdapterView.OnItemClickListener page2OnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    ToastUtil.showMyToast("亲爱的用户，工作统计更换正在开发中...");
                    break;
            }
        }
    };


    /**
     * 设备查询
     */
    private void inquireDevice(Intent data, final int requestCode) {

        deviceCode = QRCodeUtil.inquireCzf(data);
        if (TextUtils.isEmpty(deviceCode)) {
            progressHUD.dismiss();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("DEVICETYPE", "2");
                    object.put("DEVICECODE", deviceCode);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_guide:
                ToastUtil.showMyToast("亲爱的用户，该功能正在开发中...");
                break;
            default:
                break;
        }
    }
}
