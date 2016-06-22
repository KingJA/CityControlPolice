package com.tdr.citycontrolpolice.czfinit;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.BackTitleActivity;
import com.tdr.citycontrolpolice.activity.CzfInfoActivity;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Administrator;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.Basic_JuWeiHui_Kj;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo_Kj;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Add;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Add_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_GetSSYByStandAddressCode;
import com.tdr.citycontrolpolice.entity.Common_InquireDevice;
import com.tdr.citycontrolpolice.entity.Deivce;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Photo;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.TendencyEncrypt;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogAddress;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.popupwindow.BottomListPop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：出租房绑定
 * 创建人：KingJA
 * 创建时间：2016/4/19 16:30
 * 修改备注：
 */
public class CzfInitActivity extends BackTitleActivity implements View.OnClickListener, DialogAddress.OnSearchListener, CompoundButton.OnCheckedChangeListener, BottomListPop.OnBottemPopSelectListener {
    private static final String TAG = "CzfInitActivity";
    private TextView mTvAddress;
    private ImageView mIvSearch;
    private EditText mEtCzfName;
    private EditText mTvCzfType;
    private EditText mEtAreaName;
    private EditText mTvPolice;
    private EditText mTvOwnerName;
    private EditText mTvOwnerCard;
    private EditText mEtOwnerPhone;
    private CheckBox mCbIsOwern;
    private LinearLayout mLlAdmin;
    private EditText mEtAdminName;
    private EditText mEtAdminCard;
    private EditText mEtAdminPhone;
    private ImageView mIvNumber;
    private ImageView mIvRoom;
    private TextView mTvSubmit;
    private DialogAddress dialogAddress;
    private HashMap<String, Object> mParam;
    private Basic_JuWeiHui_Kj juWeiHui;
    private Basic_PaiChuSuo_Kj paiChuSuo;
    private boolean isChecked;
    private String ownerName;
    private String ownerCard;
    private String ownerPhone;
    private List<Basic_Dictionary_Kj> roomTypeList;
    private BottomListPop houseTypePop;
    private String houseType;
    private String houseTypeString;
    private DialogDouble dialogDouble;
    private int photoType;
    private static final int Camara = 100;
    private File numberFile;
    private File roomFile;
    private File imageFile;
    private String base64Number;
    private String base64Room;
    private String mAddress;
    private String mCzfName;
    private String mCzfType;
    private String mOwnerName;
    private String mPolice;
    private String mAreaName;
    private String mOwnerCard;
    private String mOwnerPhone;
    private String mAdminName;
    private String mAdminCard;
    private String mAdminPhone;
    private String addressCode;
    private Basic_StandardAddressCodeByKey_Kj.ContentBean standardAddressCodeByKey;
    private ChuZuWu_GetSSYByStandAddressCode.ContentBean content;
    private LinearLayout mLlSearch;
    private TextView mTvAddAdmin;
    private boolean addAdmin;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czf_init, null);
        return view;
    }

    @Override
    public void initVariables() {
    }

    private void initDevice(String deviceCode) {
        deivceList = new ArrayList<Deivce>();
        Deivce deivce = new Deivce();
        Intent intent = getIntent();
        deivce.setDEVICENAME("二维码门牌");
        deivce.setDEVICEID(MyUtil.getUUID());
        deivce.setDEVICECODE(deviceCode);
        deivce.setDEVICETYPE("0002");
        Log.i(TAG, "DEVICECODE: " + intent.getStringExtra("DEVICECODE"));
        Log.i(TAG, "DEVICETYPE: " + intent.getStringExtra("DEVICETYPE"));
        deivceList.add(deivce);
    }

    @Override
    protected void initView() {
        mLlSearch = (LinearLayout) view.findViewById(R.id.ll_search);
        mTvAddress = (EditText) view.findViewById(R.id.tv_address);
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search);
        mEtCzfName = (EditText) view.findViewById(R.id.et_czfName);
        mTvCzfType = (EditText) view.findViewById(R.id.tv_czfType);
        mEtAreaName = (EditText) view.findViewById(R.id.et_areaName);
        mTvPolice = (EditText) view.findViewById(R.id.tv_police);
        mTvOwnerName = (EditText) view.findViewById(R.id.tv_ownerName);
        mTvOwnerCard = (EditText) view.findViewById(R.id.tv_ownerCard);
        mEtOwnerPhone = (EditText) view.findViewById(R.id.et_ownerPhone);
        mCbIsOwern = (CheckBox) view.findViewById(R.id.cb_isOwern);
        mLlAdmin = (LinearLayout) view.findViewById(R.id.ll_admin);
        mEtAdminName = (EditText) view.findViewById(R.id.et_adminName);
        mEtAdminCard = (EditText) view.findViewById(R.id.et_adminCard);
        mEtAdminPhone = (EditText) view.findViewById(R.id.et_adminPhone);
        mIvNumber = (ImageView) view.findViewById(R.id.iv_number);
        mIvRoom = (ImageView) view.findViewById(R.id.iv_room);
        mTvSubmit = (TextView) view.findViewById(R.id.tv_submit);
        mTvAddAdmin = (TextView) view.findViewById(R.id.tv_addAdmin);
        dialogAddress = new DialogAddress(this);
        roomTypeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "HOUSETYPE");
        houseTypePop = new BottomListPop(mTvAddress, CzfInitActivity.this, roomTypeList);
        dialogDouble = new DialogDouble(this, "确定要退出出租房登记页面？", "确定", "取消");
        dialogAddress = new DialogAddress(this);
        numberFile = ImageUtil.createImageFile();
        roomFile = ImageUtil.createImageFile();
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        mTvAddAdmin.setOnClickListener(this);
        mLlSearch.setOnClickListener(this);
        dialogAddress.setOnSearchListener(this);
        mCbIsOwern.setOnCheckedChangeListener(this);
        mTvCzfType.setOnClickListener(this);
        mIvNumber.setOnClickListener(this);
        mIvRoom.setOnClickListener(this);
        mTvSubmit.setOnClickListener(this);
        houseTypePop.setOnBottemPopSelectListener(this);
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {

            }
        });

    }

    @Override
    public void setData() {
        setTitle("出租房绑定");
    }

    private final static int SCANNIN_CZF_CODE = 2003;


    private String newcode;

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

            Map<String, Object> param = new HashMap<>();
            param.put("TaskID", "1");
            param.put("DEVICETYPE", "2");
            param.put("DEVICECODE", newcode);
            ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_InquireDevice", param)
                    .setBeanType(Common_InquireDevice.class)
                    .setActivity(CzfInitActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_InquireDevice>() {
                        @Override
                        public void onSuccess(Common_InquireDevice bean) {
                            setProgressDialog(false);
                            initDevice(newcode);
                            upload();
                        }

                        @Override
                        public void onErrorResult(ErrorResult errorResult) {
                            setProgressDialog(false);
                        }
                    }).build();
            PoolManager.getInstance().execute(task);
        } else {
            setProgressDialog(false);
            ToastUtil.showMyToast("非指定设备");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
//                二维码

                checkData();
                break;
            case R.id.ll_search:

                dialogAddress.showAndReset();
                break;
            case R.id.tv_czfType:
                houseTypePop.showPopupWindow();
                break;
            case R.id.iv_number:
                photoType = 0;
                takePhoto();
                break;
            case R.id.iv_room:
                photoType = 1;
                takePhoto();
                break;
            case R.id.tv_addAdmin:
                addAdmin =!addAdmin;
                mLlAdmin.setVisibility(addAdmin ?View.VISIBLE:View.GONE);
                mTvAddAdmin.setText(addAdmin ?"取消":"添加");
                break;
        }
    }

    private void checkData() {
        mAddress = mTvAddress.getText().toString().trim();
        mCzfName = mEtCzfName.getText().toString().trim();
        mCzfType = mTvCzfType.getText().toString().trim();
        mAreaName = mEtAreaName.getText().toString().trim();
        mPolice = mTvPolice.getText().toString().trim();
        mOwnerName = mTvOwnerName.getText().toString().trim();
        mOwnerCard = mTvOwnerCard.getText().toString().trim();
        mOwnerPhone = mEtOwnerPhone.getText().toString().trim();
        mAdminName = mEtAdminName.getText().toString().trim();
        mAdminCard = mEtAdminCard.getText().toString().trim();
        mAdminPhone = mEtAdminPhone.getText().toString().trim();

        if (CheckUtil.checkEmpty(mAddress, "请选择地址") && CheckUtil.checkEmpty(mCzfName, "请输入出租房名称") &&
                CheckUtil.checkEmpty(mCzfType, "请选择房屋类型") && CheckUtil.checkEmpty(mOwnerName, "房东姓名空缺") &&
                CheckUtil.checkEmpty(mOwnerCard, "房东身份证空缺") && CheckUtil.checkPhoneFormat(mOwnerPhone) &&
                CheckUtil.checkEmpty(mAdminName, "请输管理员姓名") && CheckUtil.checkEmpty(mAdminCard, "请输入管理员身份证号码") &&
                CheckUtil.checkPhoneFormat(mAdminPhone) && CheckUtil.checkEmpty(base64Number, "请拍摄号牌")
                ) {
            Intent intent = new Intent();
            intent.setClass(CzfInitActivity.this, zbar.CaptureActivity.class);
            startActivityForResult(intent, SCANNIN_CZF_CODE);
        }
    }

    private Photo dz_photo, fw_photo;
    private ArrayList<Deivce> deivceList;

    private void upload() {
        setProgressDialog(true);
        ChuZuWu_Add chuZuWuAdd = new ChuZuWu_Add();
        chuZuWuAdd.setTaskID("1");//自动恢复
        chuZuWuAdd.setHOUSENAME(mCzfName);//自动恢复
        chuZuWuAdd.setHOUSEID(MyUtil.getUUID());//自动恢复
        chuZuWuAdd.setADDRESS(mAddress);//自动恢复
        chuZuWuAdd.setSTANDARDADDRCODE(addressCode);//自动恢复
        chuZuWuAdd.setHOUSINGESTATE(mAreaName);//自动恢复
        chuZuWuAdd.setIDENTITYCARD(mOwnerCard);//自动恢复
        chuZuWuAdd.setOWNERNAME(mOwnerName);//自动恢复
        chuZuWuAdd.setOWNERNEWUSERID(MyUtil.getUUID());//自动恢复
        chuZuWuAdd.setHOUSETYPE(houseType);//自动恢复
        chuZuWuAdd.setPHONE(mOwnerPhone);//自动恢复
        chuZuWuAdd.setPCSCO(paiChuSuo.getDMZM());//自动恢复
        chuZuWuAdd.setJWHCODE(juWeiHui.getDMZM());//自动恢复
        chuZuWuAdd.setXQCODE(content.getJWHCODE().substring(0, 6));//自动恢复
        chuZuWuAdd.setLNG(standardAddressCodeByKey.getX());//自动恢复
        chuZuWuAdd.setLAT(standardAddressCodeByKey.getY());//自动恢复

        chuZuWuAdd.setADMINISTRATORCOUNT("1");//自动恢复
        Administrator administrator = new Administrator();
        administrator.setNEWUSERID(MyUtil.getUUID());//自动恢复
        administrator.setPHONE(mAdminPhone);//自动恢复
        administrator.setNAME(mAdminName);//自动恢复
        administrator.setIDENTITYCARD(mAdminCard);//自动恢复
        List<Administrator> administratorList = new ArrayList<Administrator>();
        administratorList.add(administrator);
        chuZuWuAdd.setADMINISTRATOR(administratorList);
        List<Photo> photoList = new ArrayList<Photo>();
        if (dz_photo != null) {
            photoList.add(dz_photo);//自动恢复
        }
        if (fw_photo != null) {
            photoList.add(fw_photo);//自动恢复
        }
        chuZuWuAdd.setPHOTOCOUNT(photoList.size() + "");
        chuZuWuAdd.setPHOTOLIST(photoList);
        chuZuWuAdd.setDEVICECOUNT(deivceList.size() + "");
        chuZuWuAdd.setDEVICELIST(deivceList);


        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_Add", chuZuWuAdd)
                .setBeanType(ChuZuWu_Add_Kj.class)
                .setActivity(CzfInitActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_Add_Kj>() {
                    @Override
                    public void onSuccess(ChuZuWu_Add_Kj bean) {
                        setProgressDialog(false);
                        goActivity(bean);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    private void goActivity(final ChuZuWu_Add_Kj bean) {
        DialogDouble dialogDouble = new DialogDouble(this, "是否开始初始化房间？", "确定", "取消");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                Intent intent = new Intent(CzfInitActivity.this,
                        CzfInfoActivity.class);
                intent.putExtra("HouseID", bean.getContent().getHouseID());
                Log.i(TAG, "onSuccess: " + bean.getContent().getHouseID());
                startActivity(intent);
                finish();
            }

            @Override
            public void onRight() {
                finish();
            }
        });

    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (photoType == 0) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(numberFile));
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(roomFile));
        }

        startActivityForResult(intent, Camara);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_CZF_CODE:
                if (resultCode == RESULT_OK) {
                    setProgressDialog(true);
                    inquireDevice(data, requestCode);
                }
                break;
            case Camara:
                if (resultCode == RESULT_OK) {
                    if (photoType == 0) {
                        Bitmap bitmap = ImageUtil.compressScaleFromF2B(numberFile.getAbsolutePath());
                        base64Number = new String(ImageUtil.bitmapToBase64(bitmap));
                        mIvNumber.setImageBitmap(ImageUtil.base64ToBitmap(base64Number));
                        Log.i(TAG, "base64String: " + base64Number.length());
                        dz_photo = new Photo();
                        dz_photo.setLISTID(MyUtil.getUUID());
                        dz_photo.setTAG("电子门牌");
                        dz_photo.setIMAGE(base64Number);
                    } else {
                        Bitmap bitmap = ImageUtil.compressScaleFromF2B(roomFile.getAbsolutePath());
                        base64Room = new String(ImageUtil.bitmapToBase64(bitmap));
                        mIvRoom.setImageBitmap(ImageUtil.base64ToBitmap(base64Room));
                        Log.i(TAG, "base64String: " + base64Room.length());
                        fw_photo = new Photo();
                        fw_photo.setLISTID(MyUtil.getUUID());
                        fw_photo.setTAG("房屋外径");
                        fw_photo.setIMAGE(base64Room);
                    }
                }
                break;
        }
    }

    @Override
    public void onConfirm(Basic_StandardAddressCodeByKey_Kj.ContentBean standardAddressCodeByKey) {
        this.standardAddressCodeByKey = standardAddressCodeByKey;
        this.addressCode = standardAddressCodeByKey.getGeocode();

        setProgressDialog(true);
        mTvAddress.setText(standardAddressCodeByKey.getAddress().substring(6));
        String czfName = MyUtil.getCzfName(standardAddressCodeByKey.getAddressPath());
        mEtCzfName.setText(czfName);
        mTvOwnerName.setText("");
        mTvOwnerCard.setText("");
        mEtOwnerPhone.setText("");
        mTvPolice.setText("");
        mCbIsOwern.setChecked(false);
        mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("STANDARDADDRCODE", addressCode);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_GetSSYByStandAddressCode", mParam)
                .setBeanType(ChuZuWu_GetSSYByStandAddressCode.class)
                .setActivity(CzfInitActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_GetSSYByStandAddressCode>() {
                    @Override
                    public void onSuccess(ChuZuWu_GetSSYByStandAddressCode bean) {
                        setProgressDialog(false);
                        content = bean.getContent();
                        juWeiHui = (Basic_JuWeiHui_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_JuWeiHui_Kj.class, "DMZM", content.getJWHCODE());
                        if (juWeiHui == null) {
                            ToastUtil.showMyToast("请更新字典获取最新数据");
                            return;
                        }

                        mTvOwnerName.setText(content.getOWNERNAME());
                        mTvOwnerCard.setText(content.getIDENTITYCARD());
                        mEtOwnerPhone.setText(content.getPHONE());
                        paiChuSuo = (Basic_PaiChuSuo_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_PaiChuSuo_Kj.class, "DMZM", juWeiHui.getFDMZM());
                        mTvPolice.setText(paiChuSuo.getDMMC());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            CzfInitActivity.this.isChecked = isChecked;
            ownerName = mTvOwnerName.getText().toString().trim();
            ownerCard = mTvOwnerCard.getText().toString().trim();
            ownerPhone = mEtOwnerPhone.getText().toString().trim();
            if (CheckUtil.checkEmpty(ownerName, "房东姓名为空") && CheckUtil.checkEmpty(ownerCard, "房东身份证号码为空") && CheckUtil.checkPhoneFormat(ownerPhone)) {
                mEtAdminName.setText(ownerName);
                mEtAdminCard.setText(ownerCard);
                mEtAdminPhone.setText(ownerPhone);
                mLlAdmin.setVisibility(View.GONE);
            } else {
                buttonView.setChecked(false);
            }


        } else {
            mLlAdmin.setVisibility(View.VISIBLE);
            mEtAdminName.setText("");
            mEtAdminCard.setText("");
            mEtAdminPhone.setText("");
        }
    }

    @Override
    public void onSelect(int position, Basic_Dictionary_Kj bean) {
        houseType = bean.getCOLUMNVALUE();
        houseTypeString = bean.getCOLUMNCOMMENT();
        mTvCzfType.setText(houseTypeString);
    }

    @Override
    public void onBackPressed() {
        dialogDouble.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
        if (numberFile.exists()) {
            numberFile.delete();
        }
        if (roomFile.exists()) {
            roomFile.delete();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("numberFile", numberFile.getAbsolutePath());
        outState.putString("roomFile", roomFile.getAbsolutePath());
        outState.putString("base64Number", base64Number);
        outState.putString("base64Room", base64Room);
        outState.putString("addressCode", addressCode);
        outState.putString("houseType", houseType);
        outState.putInt("photoType", photoType);
        outState.putSerializable("deivceList", deivceList);
        outState.putSerializable("standardAddressCodeByKey", standardAddressCodeByKey);
        outState.putSerializable("paiChuSuo", paiChuSuo);
        outState.putSerializable("juWeiHui", juWeiHui);
        outState.putSerializable("content", content);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        numberFile = new File(savedInstanceState.getString("numberFile"));
        roomFile = new File(savedInstanceState.getString("roomFile"));
        base64Number = savedInstanceState.getString("base64Number");
        base64Room = savedInstanceState.getString("base64Room");
        addressCode = savedInstanceState.getString("addressCode");
        houseType = savedInstanceState.getString("houseType");
        photoType = savedInstanceState.getInt("photoType");
        deivceList = (ArrayList<Deivce>) savedInstanceState.getSerializable("deivceList");
        standardAddressCodeByKey = (Basic_StandardAddressCodeByKey_Kj.ContentBean) savedInstanceState.getSerializable("standardAddressCodeByKey");
        paiChuSuo = (Basic_PaiChuSuo_Kj) savedInstanceState.getSerializable("paiChuSuo");
        juWeiHui = (Basic_JuWeiHui_Kj) savedInstanceState.getSerializable("juWeiHui");
        content = (ChuZuWu_GetSSYByStandAddressCode.ContentBean) savedInstanceState.getSerializable("content");
        Log.i(TAG, "onRestoreInstanceState: ");


        if (!TextUtils.isEmpty(base64Number)) {
            mIvNumber.setImageBitmap(ImageUtil.base64ToBitmap(base64Number));
        }
        if (!TextUtils.isEmpty(base64Room)) {
            mIvRoom.setImageBitmap(ImageUtil.base64ToBitmap(base64Room));
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
