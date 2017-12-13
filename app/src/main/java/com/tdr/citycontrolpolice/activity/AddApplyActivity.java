package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.RoomListAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingIn;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingInParam;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.event.InfoInFragmetnEvent;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.DialogUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.OCRUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.StringUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：添加申报人员
 * Create Time：2016/12/16 9:18
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddApplyActivity extends BackTitleActivity implements View.OnClickListener, OnOperItemClickL {
    private static final int REQUEST_CAMARA = 1009;
    private LinearLayout mLlSelectRoom;
    private TextView mTvApplyRoomNum;
    private LinearLayout mLlOcrCamera;
    private EditText mEtApplyName;
    private ImageView mIvApplyCamera;
    private EditText mEtApplyCardId;
    private EditText mEtApplyPhone;
    private ImageView mIvIdcard;
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> mRoomList = new ArrayList<>();
    private NormalListDialog mNormalListDialog;
    private TextView mTvConfirm;
    private String mRoomId;
    private String name;
    private String cardId;
    private String phone;
    private String imgBase64;
    private String houseId;
    private KjChuZuWuInfo.ContentBean czfInfo;
    private EditText mEtApplyHeight;
    private String height;
    private LinearLayout mLlApplyAvatar;
    private ImageView mIvApplyAvatar;
    private String base64Avatar;
    private ImageView mIvBigAvatar;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_add_apply, null);
        return view;
    }


    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra("houseId");
    }

    @Override
    protected void initView() {
        mLlSelectRoom = (LinearLayout) view.findViewById(R.id.ll_selectRoom);
        mTvApplyRoomNum = (TextView) view.findViewById(R.id.tv_apply_roomNum);
        mTvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        mLlOcrCamera = (LinearLayout) view.findViewById(R.id.ll_ocr_camera);
        mEtApplyName = (EditText) view.findViewById(R.id.et_apply_name);
        mIvApplyCamera = (ImageView) view.findViewById(R.id.iv_apply_camera);
        mEtApplyCardId = (EditText) view.findViewById(R.id.et_apply_cardId);
        mEtApplyPhone = (EditText) view.findViewById(R.id.et_apply_phone);
        mEtApplyHeight = (EditText) view.findViewById(R.id.et_apply_height);
        mIvIdcard = (ImageView) view.findViewById(R.id.iv_idcard);
        mLlApplyAvatar = (LinearLayout) view.findViewById(R.id.ll_apply_avatar);
        mIvApplyAvatar = (ImageView) view.findViewById(R.id.iv_apply_avatar);
        mIvBigAvatar = (ImageView) view.findViewById(R.id.iv_big_avatar);
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HouseID", houseId);
        setProgressDialog(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_Info",
                param)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(AddApplyActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        czfInfo = bean.getContent();
                        mRoomList = czfInfo.getRoomList();
                        setProgressDialog(false);
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
        mLlOcrCamera.setOnClickListener(this);
        mLlSelectRoom.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
        mLlApplyAvatar.setOnClickListener(this);
        mIvApplyAvatar.setOnClickListener(this);
        mIvBigAvatar.setOnClickListener(this);
    }

    @Override
    public void setData() {
        setTitle("添加申报人员");
    }


    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AddApplyActivity.class);
        intent.putExtra("houseId", houseId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_selectRoom:
                if (mRoomList.size() > 0) {
                    mNormalListDialog = DialogUtil.getListDialog(this, "房间号", new RoomListAdapter(this, mRoomList));
                    mNormalListDialog.setOnOperItemClickL(this);
                    mNormalListDialog.show();
                } else {
                    ToastUtil.showMyToast("该出租屋暂时没有房间");
                }
                break;
            case R.id.ll_apply_avatar:
                takePhoto();
                break;
            case R.id.iv_big_avatar:
                mIvBigAvatar.setVisibility(View.GONE);
                break;
            case R.id.iv_apply_avatar:
                mIvBigAvatar.setVisibility(View.VISIBLE);
                mIvBigAvatar.setImageBitmap(ImageUtil.base64ToBitmap(base64Avatar));
                break;
            case R.id.ll_ocr_camera:
                KCamera.GoCamera(this);
                break;
            case R.id.tv_confirm:
                name = mEtApplyName.getText().toString().trim();
                cardId = mEtApplyCardId.getText().toString().trim().toUpperCase();
                phone = mEtApplyPhone.getText().toString().trim();
                height = mEtApplyHeight.getText().toString().trim();
                if (CheckUtil.checkEmpty(mTvApplyRoomNum.getText().toString(), "请选择房间号")
                        && CheckUtil.checkEmpty(name, "请通过相机获取姓名")
                        && CheckUtil.checkIdCard(cardId, "身份证号格式错误")
                        && CheckUtil.checkHeight(height, 80, 210)
                        && CheckUtil.checkPhoneFormat(phone)) {
                    onApply();
                }
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    private int photoCount;

    private void onApply() {
        setProgressDialog(true);
        ChuZuWu_LKSelfReportingInParam bean = new ChuZuWu_LKSelfReportingInParam();
        bean.setTaskID("1");
        bean.setHOUSEID(czfInfo.getHOUSEID());
        bean.setROOMID(mRoomId);
        bean.setLISTID(StringUtil.getUUID());
        bean.setNAME(name);
        bean.setIDENTITYCARD(cardId);
        bean.setPHONE(phone);
        bean.setREPORTERROLE(3);
        bean.setOPERATOR((String) SharedPreferencesUtils.get("uid", ""));
        bean.setSTANDARDADDRCODE(czfInfo.getSTANDARDADDRCODE());
        bean.setTERMINAL(3);
        bean.setXQCODE(czfInfo.getXQCODE());
        bean.setPCSCODE(czfInfo.getPCSCODE());
        bean.setJWHCODE(czfInfo.getJWHCODE());
        bean.setHEIGHT(Integer.valueOf(height));
        bean.setOPERATORPHONE(phone);
        List<ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean> photolist = new ArrayList<>();
        if (!TextUtils.isEmpty(imgBase64)) {
            bean.setPHOTOCOUNT(++photoCount);
            ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean idcard = new ChuZuWu_LKSelfReportingInParam
                    .PHOTOLISTBean();
            idcard.setLISTID(StringUtil.getUUID());
            idcard.setTAG("身份证");
            idcard.setIMAGE(imgBase64);
            photolist.add(idcard);
        }
        if (!TextUtils.isEmpty(base64Avatar)) {
            bean.setPHOTOCOUNT(++photoCount);
            ChuZuWu_LKSelfReportingInParam.PHOTOLISTBean avatar = new ChuZuWu_LKSelfReportingInParam
                    .PHOTOLISTBean();
            avatar.setLISTID(StringUtil.getUUID());
            avatar.setTAG("人像");
            avatar.setIMAGE(base64Avatar);
            photolist.add(avatar);
        }
        bean.setPHOTOLIST(photolist);
        setProgressDialog(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0,
                "ChuZuWu_LKSelfReportingIn", bean)
                .setBeanType(ChuZuWu_LKSelfReportingIn.class)
                .setActivity(AddApplyActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingIn>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingIn bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("添加申报人员成功");
                        EventBus.getDefault().post(new InfoInFragmetnEvent());
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
    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
        KjChuZuWuInfo.ContentBean.RoomListBean bean = (KjChuZuWuInfo.ContentBean.RoomListBean) parent
                .getItemAtPosition(position);
        mRoomId = bean.getROOMID();
        mTvApplyRoomNum.setText(bean.getROOMNO() + "");
        mNormalListDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case KCamera.REQUEST_CODE_KCAMERA:
                if (Activity.RESULT_OK == resultCode) {
                    String card = data.getStringExtra("card");
                    String name = data.getStringExtra("name");
                    //身份证base64字符串
                    imgBase64 = data.getStringExtra("img");
                    if (card != null) {
                        mEtApplyCardId.setText(card);
                    }
                    if (name != null) {
                        mEtApplyName.setText(name);
                    }
                    Bitmap bitmap = OCRUtil.base64ToBitmap(imgBase64);
                    mIvIdcard.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CAMARA:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mIvApplyAvatar.setVisibility(View.VISIBLE);
                    mIvApplyAvatar.setImageBitmap(bitmap);
                    mIvApplyAvatar.setEnabled(true);
                    base64Avatar = new String(ImageUtil.bitmapToBase64(bitmap));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mIvBigAvatar.getVisibility() == View.VISIBLE) {
            mIvBigAvatar.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    void onBack() {
        onBackPressed();
    }
}
