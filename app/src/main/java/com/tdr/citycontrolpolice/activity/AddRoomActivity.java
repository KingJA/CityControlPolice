package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AddRoom;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Modify;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_ModifyRoom;
import com.tdr.citycontrolpolice.entity.Photo;
import com.tdr.citycontrolpolice.event.RefreshInfoManagerFragment;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ImageUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.popupwindow.BottomListPop;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/3/30 14:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddRoomActivity extends BackTitleActivity implements View.OnClickListener, BottomListPop
        .OnBottemPopSelectListener {
    private LinearLayout mLlEditRoot;
    private LinearLayout mLlRoomNo;
    private EditText mEtRoomNumber;
    private EditText mEtRoomShi;
    private EditText mEtRoomTing;
    private EditText mEtRoomWei;
    private EditText mEtRoomYangtai;
    private EditText mEtRoomArea;
    private EditText mEtRoomPrice;
    private EditText mEtRoomPerson;
    private TextView mTvFixture;
    private TextView mTvDeposit;
    private SwitchCompat mSbAutoPublish;
    private EditText mEtPublishTitle;
    private ImageView mIvRoom;
    private List<Basic_Dictionary_Kj> depositList;
    private List<Basic_Dictionary_Kj> fixtureList;
    private int selectType;
    private BottomListPop depositPop;
    private BottomListPop fixturePop;
    private String fixture;
    private String deposit;
    private String houseId;
    private String mShi;
    private String mTing;
    private String mWei;
    private String mYangTai;
    private String mArea;
    private String mPerson;
    private String mPrice;
    private String mRoomNumber;
    private boolean autoPulish;
    private String mTitle;
    private ChuZuWu_AddRoom chuZuWu_addRoom;
    private List<ChuZuWu_AddRoom.PHOTOBean> photos = new ArrayList<>();


    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra("HOUSE_ID");
        Log.e(TAG, "houseId: " + houseId);

        depositList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj
                .class, "COLUMNCODE", "DEPOSIT");
        fixtureList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj
                .class, "COLUMNCODE", "FIXTURE");
    }

    @Override
    protected void initView() {
        mLlEditRoot = (LinearLayout) view.findViewById(R.id.ll_edit_root);
        mLlRoomNo = (LinearLayout) view.findViewById(R.id.ll_room_no);
        mEtRoomNumber = (EditText) view.findViewById(R.id.et_room_number);
        mEtRoomShi = (EditText) view.findViewById(R.id.et_room__shi);
        mEtRoomTing = (EditText) view.findViewById(R.id.et_room_ting);
        mEtRoomWei = (EditText) view.findViewById(R.id.et_room_wei);
        mEtRoomYangtai = (EditText) view.findViewById(R.id.et_room_yangtai);
        mEtRoomArea = (EditText) view.findViewById(R.id.et_room_area);
        mEtRoomPrice = (EditText) view.findViewById(R.id.et_room_price);
        mEtRoomPerson = (EditText) view.findViewById(R.id.et_room_person);
        mTvFixture = (TextView) view.findViewById(R.id.tv_fixture);
        mTvDeposit = (TextView) view.findViewById(R.id.tv_deposit);
        mSbAutoPublish = (SwitchCompat) view.findViewById(R.id.sb_autoPublish);
        mEtPublishTitle = (EditText) view.findViewById(R.id.et_publish_title);
        mIvRoom = (ImageView) view.findViewById(R.id.iv_room);

        depositPop = new BottomListPop(mLlEditRoot, AddRoomActivity.this, depositList);
        fixturePop = new BottomListPop(mLlEditRoot, AddRoomActivity.this, fixtureList);
    }

    private void hideInput() {
        InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_fixture:
                hideInput();
                //装修
                selectType = 0;
                fixturePop.showPopupWindow();
                break;
            case R.id.tv_deposit:
                hideInput();
                selectType = 1;
                depositPop.showPopupWindow();
                break;
            case R.id.iv_room:
                takePhoto();
                break;
            default:
                break;
        }
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        depositPop.setOnBottemPopSelectListener(this);
        fixturePop.setOnBottemPopSelectListener(this);
        mTvFixture.setOnClickListener(this);
        mTvDeposit.setOnClickListener(this);
        mIvRoom.setOnClickListener(this);
        mSbAutoPublish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                autoPulish = isChecked;
            }
        });
    }

    @Override
    public void setData() {
        setTitle("房间信息");
        setRightTextVisibility("保存");
        setOnRightClickListener(new OnRightClickListener() {
            @Override
            public void onRightClick() {
                checkRoomInfo();
            }
        });
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_add_room, null);
        return view;
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AddRoomActivity.class);
        intent.putExtra("HOUSE_ID", houseId);
        context.startActivity(intent);
    }

    @Override
    public void onSelect(int position, Basic_Dictionary_Kj bean) {
        String type = bean.getCOLUMNVALUE();
        String name = bean.getCOLUMNCOMMENT();
        switch (selectType) {
            case 0:
                fixture = type;
                mTvFixture.setText(name);
                break;
            case 1:
                deposit = type;
                mTvDeposit.setText(name);
                break;
            default:
                break;
        }
    }

    public int string2Int(String num) {
        return "".equals(num) ? 0 : Integer.valueOf(num);
    }

    private void checkRoomInfo() {
        mRoomNumber = mEtRoomNumber.getText().toString().trim();
        mShi = mEtRoomShi.getText().toString().trim();
        mTing = mEtRoomTing.getText().toString().trim();
        mWei = mEtRoomWei.getText().toString().trim();
        mYangTai = mEtRoomYangtai.getText().toString().trim();
        mArea = mEtRoomArea.getText().toString().trim();
        mPerson = mEtRoomPerson.getText().toString().trim();
        mPrice = mEtRoomPrice.getText().toString().trim();
        mTitle = mEtPublishTitle.getText().toString().trim();
        if (CheckUtil.checkEmpty(mRoomNumber, "请输入房间号码")
                && CheckUtil.checkEmpty(fixture, "请选择装修程度")
                && CheckUtil.checkEmpty(deposit, "请选择支付方式")
                ) {
            load();
        }

    }

    private void load() {
        setProgressDialog(true);
        chuZuWu_addRoom = new ChuZuWu_AddRoom();
        chuZuWu_addRoom.setDEPOSIT(string2Int(deposit));
        chuZuWu_addRoom.setFIXTURE(string2Int(fixture));
        chuZuWu_addRoom.setTaskID("1");
        chuZuWu_addRoom.setPRICE(TextUtils.isEmpty(mPrice) ? 0 : string2Int(mPrice));
        chuZuWu_addRoom.setHOUSEID(houseId);
        chuZuWu_addRoom.setROOMID(MyUtil.getUUID());
        chuZuWu_addRoom.setSHI(TextUtils.isEmpty(mShi) ? 0 : string2Int(mShi));
        chuZuWu_addRoom.setTING(TextUtils.isEmpty(mTing) ? 0 : string2Int(mTing));
        chuZuWu_addRoom.setWEI(TextUtils.isEmpty(mWei) ? 0 : string2Int(mWei));
        chuZuWu_addRoom.setYANGTAI(TextUtils.isEmpty(mYangTai) ? 0 : string2Int(mYangTai));
        chuZuWu_addRoom.setSQUARE(TextUtils.isEmpty(mArea) ? 0 : string2Int(mArea));
        chuZuWu_addRoom.setGALLERYFUL(TextUtils.isEmpty(mPerson) ? 0 : string2Int(mPerson));
        chuZuWu_addRoom.setROOMNO(string2Int(mRoomNumber));
        chuZuWu_addRoom.setISAUTOPUBLISH(autoPulish ? 1 : 0);
        chuZuWu_addRoom.setTITLE(mTitle);

        if (photos.size() > 0) {
            chuZuWu_addRoom.setPHOTOCOUNT(1);
            chuZuWu_addRoom.setPHOTO(photos);
        }


        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_AddRoom",
                chuZuWu_addRoom)
                .setBeanType(ChuZuWu_AddRoom.class)
                .setActivity(AddRoomActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AddRoom>() {
                    @Override
                    public void onSuccess(ChuZuWu_AddRoom bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("添加房间成功");
                        EventBus.getDefault().post(new RefreshInfoManagerFragment());
                        finish();

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    private final static int CAMERA = 0;

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA && data != null) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mIvRoom.setImageBitmap(bitmap);
                photos.clear();
                ChuZuWu_AddRoom.PHOTOBean photo = new ChuZuWu_AddRoom.PHOTOBean();
                photo.setIMAGE(new String(ImageUtil.bitmapToBase64(bitmap)));
                photo.setLISTID(MyUtil.getUUID());
                photo.setTAG("房间照片");
                photos.add(photo);
            }
        }
    }
}
