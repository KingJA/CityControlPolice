package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.tdr.citycontrolpolice.util.ToastUtil;
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
    private Button mBtnSubmit;
    private String userName;
    private File boxFile;
    private String base64Box;


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
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);

    }


    @Override
    public void initNet() {
    }

    /**
     * 上传货品箱
     */
    private void upload() {
        Common_OpenBox_Param param = new Common_OpenBox_Param();
        param.setTaskID("1");
        param.setBOXID("xxxxxxxxxxxxx");
        param.setDEVICETYPE(00000000000);
        param.setPHOTOCOUNT(1);
        List<Common_OpenBox_Param.PHOTOLISTBean> photolist = new ArrayList<>();
        Common_OpenBox_Param.PHOTOLISTBean photo = new Common_OpenBox_Param.PHOTOLISTBean();
        photo.setLISTID(MyUtil.getUUID());
        photo.setIMAGE("");
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
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        mBtnSubmit.setOnClickListener(this);
        mIvBox.setOnClickListener(this);
        boxFile = ImageUtil.createImageFile();
    }

    private void scanBox() {
        ToastUtil.showMyToast("扫描");
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
//                    inquireDevice(data);
                }
                break;
            case CAMERA:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = ImageUtil.compressScaleFromF2B(boxFile.getAbsolutePath());
                    base64Box = new String(ImageUtil.bitmapToBase64(bitmap));
                    mIvBox.setImageBitmap(ImageUtil.base64ToBitmap(base64Box));
                    break;
                }
            default:
                break;
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
            case R.id.btn_submit:
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

}
