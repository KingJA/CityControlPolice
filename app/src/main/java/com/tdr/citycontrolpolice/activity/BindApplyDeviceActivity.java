package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.Common_AddDevice;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Param_Common_AddDevice;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：设备绑定界面
 * 创建人：KingJA
 * 创建时间：2016/4/7 16:42
 * 修改备注：
 */
public class BindApplyDeviceActivity extends BackTitleActivity {

    private static final String TAG = "BindDeviceActivity";
    private static final int Camara = 100;
    private int deviceType;
    private long deviceNO;
    private String houseId;
    private String roomId;
    private int roomNo;
    private EditText et_device_name;
    private TextView tv_device_room;
    private TextView tv_device_type;
    private TextView tv_device_submit;
    private ImageView iv_device_icon;
    private File boxFile;
    private String base64Box;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_bind_apply_device, null);
        return view;
    }

    @Override
    public void initVariables() {
        deviceType = getIntent().getIntExtra("DEVICE_TYPE", 888);
        deviceNO = getIntent().getLongExtra("DEVICE_NO", 888);
        houseId = getIntent().getStringExtra("HOUSE_ID");
    }

    @Override
    protected void initView() {
        et_device_name = (EditText) view.findViewById(R.id.et_device_name);
        tv_device_room = (TextView) view.findViewById(R.id.tv_device_room);
        tv_device_type = (TextView) view.findViewById(R.id.tv_device_type);
        tv_device_submit = (TextView) view.findViewById(R.id.tv_device_submit);
        iv_device_icon = (ImageView) view.findViewById(R.id.iv_device_icon);
    }

    @Override
    public void initNet() {


    }

    private void bindDevice() {
        String deviceName = et_device_name.getText().toString().trim();
        if (CheckUtil.checkEmpty(deviceName, "请输入设备名称") && CheckUtil.checkEmpty(base64Box, "请拍摄设备照片")) {
            setProgressDialog(true);
            Param_Common_AddDevice param = new Param_Common_AddDevice();
            param.setTaskID("1");
            param.setDEVICEID(MyUtil.getUUID());
            param.setDEVICETYPE(deviceType);
            param.setDEVICECODE(deviceNO);
            param.setDEVICENAME(deviceName);
            param.setOTHERTYPE(2);
            param.setOTHERID(houseId);
            param.setROOMID("");
            param.setPHOTOCOUNT(1);
            List<Param_Common_AddDevice.PHOTOLISTBean> photolist = new ArrayList<>();
            Param_Common_AddDevice.PHOTOLISTBean photolistBean = new Param_Common_AddDevice.PHOTOLISTBean();
            photolistBean.setIMAGE(base64Box);
            photolistBean.setLISTID(MyUtil.getUUID());
            photolistBean.setTAG("设备");
            photolist.add(photolistBean);
            param.setPHOTOLIST(photolist);
            ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_AddDevice", param)
                    .setBeanType(Common_AddDevice.class)
                    .setActivity(BindApplyDeviceActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_AddDevice>() {
                        @Override
                        public void onSuccess(Common_AddDevice bean) {
                            setProgressDialog(false);
                            ToastUtil.showMyToast("绑定自主申报一体机成功");
                            finish();
                        }

                        @Override
                        public void onErrorResult(ErrorResult errorResult) {
                            setProgressDialog(false);
                        }
                    }).build();
            PoolManager.getInstance().execute(task);
        }

    }

    @Override
    public void initData() {
        boxFile = ImageUtil.createImageFile();
        iv_device_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();

            }
        });
        tv_device_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindDevice();
            }
        });
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(boxFile));
        startActivityForResult(intent, Camara);
    }

    @Override
    public void setData() {
        setTitle("自主申报一体机绑定");
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE", "COLUMNVALUE", deviceType + "");
        if (bean == null) {
            ToastUtil.showMyToast("未找到匹配的设备类型");
            finish();
        }else{
            tv_device_type.setText(bean.getCOLUMNCOMMENT());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Camara:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = ImageUtil.compressScaleFromF2B(boxFile.getAbsolutePath());
                    base64Box = new String(ImageUtil.bitmapToBase64(bitmap));
                    iv_device_icon.setImageBitmap(ImageUtil.base64ToBitmap(base64Box));
                    Log.i(TAG, "base64Box: " + base64Box.length());
                }
                break;
            default:

                break;
        }
    }

    /**
     * 删除拍摄图片
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
        outState.putString("boxFile", boxFile.getAbsolutePath());
        outState.putString("base64Box", base64Box);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
        boxFile = new File(savedInstanceState.getString("boxFile"));
        base64Box = savedInstanceState.getString("base64Box");
    }
    public static void goActivity(Context context, String houseId, int deviceType,long deviceNO) {
        Intent intent = new Intent(context, BindApplyDeviceActivity.class);
        intent.putExtra("HOUSE_ID",houseId);
        intent.putExtra("DEVICE_TYPE", deviceType);
        intent.putExtra("DEVICE_NO", deviceNO);
        context.startActivity(intent);
    }
}
