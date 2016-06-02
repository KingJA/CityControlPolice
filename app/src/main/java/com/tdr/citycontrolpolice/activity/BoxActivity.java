package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Common_OpenBox_Param;
import com.tdr.citycontrolpolice.entity.Common_OpenBox_Result;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.TendencyEncrypt;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.TypeConvert;
import com.tdr.citycontrolpolice.util.UserService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/5/7 17:04
 * 修改备注：
 */
public class BoxActivity extends BackTitleActivity implements View.OnClickListener {
    private static final int REQ_SCAN = 1;
    private static final int CAMERA = 2;
    private static final String TAG = "BoxActivity";
    private TextView mTvBoxName;
    private TextView mTvBoxPolice;
    private ImageView mIvBox;
    private TextView tv_submit;
    private String userName;
    private File boxFile;
    private String base64Box;
    private long deviceType;
    private long boxId;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_box, null);
        return view;

    }

    @Override
    public void initVariables() {
        userName = (String) SharedPreferencesUtils.get("login_name", "");
    }

    @Override
    protected void initView() {
        mTvBoxName = (TextView) findViewById(R.id.tv_box_name);
        mTvBoxPolice = (TextView) findViewById(R.id.tv_box_police);
        mIvBox = (ImageView) findViewById(R.id.iv_box);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
    }


    @Override
    public void initNet() {
    }

    /**
     * 上传货品箱
     */
    private void upload() {
        String totelId = String.format("%04d", deviceType) + "-" + String.format("%010d", boxId);
        Log.i(TAG, "totelId: " + totelId);
        setProgressDialog(true);
        Common_OpenBox_Param param = new Common_OpenBox_Param();
        param.setTaskID("1");
        param.setBOXID(totelId);
        param.setDEVICETYPE((int) deviceType);
        param.setPHOTOCOUNT(1);
        List<Common_OpenBox_Param.PHOTOLISTBean> photolist = new ArrayList<>();
        Common_OpenBox_Param.PHOTOLISTBean photo = new Common_OpenBox_Param.PHOTOLISTBean();
        photo.setLISTID(MyUtil.getUUID());
        photo.setIMAGE(base64Box);
        photo.setTAG("货品箱");
        photolist.add(photo);
        param.setPHOTOLIST(photolist);
        ThreadPoolTask.Builder<Common_OpenBox_Result> builder = new ThreadPoolTask.Builder<Common_OpenBox_Result>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_OpenBox", param)
                .setBeanType(Common_OpenBox_Result.class)
                .setActivity(BoxActivity.this)
                .setCallBack(new WebServiceCallBack<Common_OpenBox_Result>() {
                    @Override
                    public void onSuccess(Common_OpenBox_Result bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("箱子启用成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        tv_submit.setOnClickListener(this);
        mIvBox.setOnClickListener(this);
        boxFile = ImageUtil.createImageFile();
    }

    private void scanBox() {
        ActivityUtil.goActivityForResult(this, zbar.CaptureActivity.class, REQ_SCAN);
    }

    @Override
    public void setData() {
        mTvBoxName.setText(userName);
        setTitle("货品箱开启");
    }

    private void setImagSize(int size) {
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(size, size);
        mIvBox.setLayoutParams(imgParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_SCAN:
                if (resultCode == RESULT_OK) {
                    inquireDevice(data);
                }
                break;
            case CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = ImageUtil.compressScaleFromF2B(boxFile.getAbsolutePath());
                    base64Box = ImageUtil.bitmapToBase64(bitmap);
                    mIvBox.setImageBitmap(ImageUtil.base64ToBitmap(base64Box));
                    Log.i(TAG, "base64Box: " + base64Box.length());
//                    ImageUtil.saveBitmapFile(bitmap);
                    break;
                }
            default:
                break;
        }
    }

    private void inquireDevice(Intent data) {
        String result = data.getExtras().getString("result");
        Log.i(TAG, "onActivityResult: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("AE".equals(type)) {
            result = result.substring(2);
            byte[] s = TendencyEncrypt.decode(result.getBytes());
            result = TendencyEncrypt.bytesToHexString(s);
            //FIXME
//            result = VerifyCode.checkDeviceCode(result);
//            if (TextUtils.isEmpty(result)) {
//                ToastUtil.showMyToast("可疑数据！");
//                return;
//            }
            Log.i(TAG, "解码: " + result);
            deviceType = Long.valueOf(result.substring(0, 4), 16);
            boxId = Long.valueOf(result.substring(4, 12), 16);
            Long boxCount = Long.valueOf(result.substring(12, 16), 16);
            String date = TypeConvert.hexString2String(result.substring(16, 28));
            upload();
            Log.i(TAG, "设备类型: " + deviceType);
            Log.i(TAG, "箱体序号: " + boxId);
            Log.i(TAG, "boxCount: " + boxCount);
            Log.i(TAG, "date: " + date);
        } else {
            ToastUtil.showMyToast("不是要求的二维码对象");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_box:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(boxFile));
                startActivityForResult(intent, CAMERA);
                break;
            case R.id.tv_submit:
                if (CheckUtil.checkEmpty(base64Box, "请拍摄货品箱图片")) {
                    scanBox();
                }
                break;

        }
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (boxFile.exists()) {
            boxFile.delete();
        }
    }
}
