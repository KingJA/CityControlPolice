package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.tdr.citycontrolpolice.entity.Param_Common_AddDevice;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 16:42
 * 修改备注：
 */
public class BindingDeviceActivity extends BackTitleActivity {

    private static final String TAG = "BindingDeviceActivity";
    private static final int Camara = 100;
    private static final int SCALE = 200;
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
    private ImageView iv_device_camera;
    private File imageFile;
    private String base64String;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_binding_device, null);
        return view;
    }

    @Override
    public void initVariables() {
        deviceType = getIntent().getIntExtra("DEVICE_TYPE", 888);
        Log.i(TAG, "initVariables: " + deviceType);
        deviceNO = getIntent().getLongExtra("DEVICE_NO", 888);
        houseId = getIntent().getStringExtra("HOUSE_ID");
        roomId = getIntent().getStringExtra("ROOM_ID");
        roomNo = getIntent().getIntExtra("ROOM_NO", 888);
    }

    @Override
    protected void initView() {
        et_device_name = (EditText) view.findViewById(R.id.et_device_name);
        tv_device_room = (TextView) view.findViewById(R.id.tv_device_room);
        tv_device_type = (TextView) view.findViewById(R.id.tv_device_type);
        tv_device_submit = (TextView) view.findViewById(R.id.tv_device_submit);
        iv_device_icon = (ImageView) view.findViewById(R.id.iv_device_icon);
        iv_device_camera = (ImageView) view.findViewById(R.id.iv_device_camera);
    }

    @Override
    public void initNet() {


    }

    private void bindDevice() {
        String deviceName = et_device_name.getText().toString().trim();
        if (CheckUtil.checkEmpty(deviceName, "请输入设备名称") && CheckUtil.checkEmpty(base64String, "请拍摄设备照片")) {
            setProgressDialog(true);
            Param_Common_AddDevice param = new Param_Common_AddDevice();
            param.setTaskID("1");
            param.setDEVICEID(MyUtil.getUUID());
            param.setDEVICETYPE(deviceType);
            param.setDEVICECODE(deviceNO);
            param.setDEVICENAME(deviceName);
            param.setOTHERTYPE(2);
            param.setOTHERID(houseId);
            param.setROOMID(roomId);
            param.setPHOTOCOUNT(1);
            List<Param_Common_AddDevice.PHOTOLISTBean> photolist = new ArrayList<>();
            Param_Common_AddDevice.PHOTOLISTBean photolistBean = new Param_Common_AddDevice.PHOTOLISTBean();
            photolistBean.setIMAGE(base64String);
            photolistBean.setLISTID(MyUtil.getUUID());
            photolistBean.setTAG("设备");
            photolist.add(photolistBean);
            param.setPHOTOLIST(photolist);
            ThreadPoolTask.Builder<Common_AddDevice> builder = new ThreadPoolTask.Builder<Common_AddDevice>();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_AddDevice", param)
                    .setBeanType(Common_AddDevice.class)
                    .setActivity(BindingDeviceActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_AddDevice>() {
                        @Override
                        public void onSuccess(Common_AddDevice bean) {
                            setProgressDialog(false);
                            ToastUtil.showMyToast("绑定设备成功！");
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
        imageFile = ImageUtil.createImageFile();
        Log.i(TAG, "openCamera: " + imageFile.getAbsolutePath());
        iv_device_camera.setOnClickListener(new View.OnClickListener() {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent, Camara);
    }

    @Override
    public void setData() {
        setTitle("设备绑定");
        tv_device_room.setText(roomNo + "");
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE", "COLUMNVALUE", deviceType + "");
        tv_device_type.setText(bean.getCOLUMNCOMMENT());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Camara:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
                    intent.putExtra("crop", true);
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("outputX", 150);
                    intent.putExtra("outputY", 150);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, SCALE);
                }
                break;
            case SCALE:
                if (resultCode == RESULT_OK) {
                    if (data.getExtras() != null) {
                        Bitmap bitmap = data.getExtras().getParcelable("data");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                        byte[] bytes = stream.toByteArray();
                        base64String = new String(ImageUtil.bitmapToBase64(bitmap));
                        Log.i(TAG, "base64String: " + base64String.length());
                        iv_device_icon.setImageBitmap(bitmap);
                    }
                }

            default:

                break;
        }
    }

}
