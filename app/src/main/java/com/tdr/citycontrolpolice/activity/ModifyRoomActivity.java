package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Modify;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_ModifyRoom;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;
import com.tdr.citycontrolpolice.view.popupwindow.BottomListPop;

import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/30 21:14
 * 修改备注：
 */
public class ModifyRoomActivity extends BackTitleActivity implements BottomListPop.OnBottemPopSelectListener, View.OnClickListener, DialogConfirm.OnConfirmClickListener, WebServiceCallBack<ChuZuWu_RoomInfo> {
    private HashMap<String, Object> mParam = new HashMap<>();
    private static final String TAG = "ModifyRoomActivity";
    private TextView tv_room_number;
    private TextView tv_room_renovation;
    private TextView tv_room_payment;
    private EditText et_room__shi;
    private EditText et_room_ting;
    private EditText et_room_wei;
    private EditText et_room_yangtai;
    private EditText et_room_area;
    private EditText et_room_person;
    private Button bt_room_submit;
    private LinearLayout ll_root;
    private String mToken;
    private String mHouseId;
    private String mRoomId;
    private String mRoomNo;
    private DialogConfirm mDialogConfirm;
    private Param_ChuZuWu_ModifyRoom mParam_chuZuWu_modifyRoom;
    private String mNumber;
    private String mRenovation;
    private String mPayment;
    private String mShi;
    private String mTing;
    private String mWei;
    private String mYangTai;
    private String mArea;
    private String mPerson;
    private int selectType;
    private String renovation;
    private String payment;
    private List<Basic_Dictionary_Kj> paymentList;
    private List<Basic_Dictionary_Kj> renovationList;
    private BottomListPop renovationPop;
    private BottomListPop paymentPop;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_room_info_kj, null);
        return view;
    }

    @Override
    public void initVariables() {
        mToken = UserService.getInstance(this).getToken();
        mHouseId = getIntent().getStringExtra("HOUSEID");
        mRoomId = getIntent().getStringExtra("ROOMID");
        mRoomNo = getIntent().getStringExtra("ROOMNO");
        mParam.put("TaskID", "1");
        mParam.put("ROOMID", mRoomId);
    }

    @Override
    protected void initView() {
        ll_root = (LinearLayout) view.findViewById(R.id.ll_root);
        tv_room_number = (TextView) view.findViewById(R.id.tv_room_number);
        tv_room_renovation = (TextView) view.findViewById(R.id.tv_room_renovation);
        tv_room_payment = (TextView) view.findViewById(R.id.tv_room_payment);
        et_room__shi = (EditText) view.findViewById(R.id.et_room__shi);
        et_room_ting = (EditText) view.findViewById(R.id.et_room_ting);
        et_room_wei = (EditText) view.findViewById(R.id.et_room_wei);
        et_room_yangtai = (EditText) view.findViewById(R.id.et_room_yangtai);
        et_room_area = (EditText) view.findViewById(R.id.et_room_area);
        et_room_person = (EditText) view.findViewById(R.id.et_room_person);
        bt_room_submit = (Button) view.findViewById(R.id.bt_room_submit);
        mDialogConfirm = new DialogConfirm(this, "成功修改房间信息!", "确定");
        paymentList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().sleectAll(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEPOSIT");
        renovationList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().sleectAll(Basic_Dictionary_Kj.class, "COLUMNCODE", "FIXTURE");
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        ThreadPoolTask.Builder<ChuZuWu_RoomInfo> builder = new ThreadPoolTask.Builder<ChuZuWu_RoomInfo>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_RoomInfo", mParam)
                .setBeanType(ChuZuWu_RoomInfo.class)
                .setActivity(ModifyRoomActivity.this)
                .setCallBack(this).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        renovationPop = new BottomListPop(ll_root, ModifyRoomActivity.this, renovationList);
        paymentPop = new BottomListPop(ll_root, ModifyRoomActivity.this, paymentList);
        renovationPop.setOnBottemPopSelectListener(this);
        paymentPop.setOnBottemPopSelectListener(this);
        mDialogConfirm.setOnConfirmClickListener(this);
        bt_room_submit.setOnClickListener(this);
        tv_room_payment.setOnClickListener(this);
        tv_room_renovation.setOnClickListener(this);
    }

    @Override
    public void setData() {
        setTitle("修改房间信息");
        tv_room_number.setText(mRoomNo);
    }

    /**
     * 设置出租房信息
     *
     * @param bean
     */
    private void setInfoFromNet(ChuZuWu_RoomInfo bean) {
        ChuZuWu_RoomInfo.ContentBean content = bean.getContent();
        Basic_Dictionary_Kj paymentBean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEPOSIT", "COLUMNVALUE", content.getDEPOSIT() + "");
        Basic_Dictionary_Kj renovationBean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "FIXTURE", "COLUMNVALUE", content.getFIXTURE() + "");
        tv_room_payment.setText(paymentBean.getCOLUMNCOMMENT());
        tv_room_renovation.setText(renovationBean.getCOLUMNCOMMENT());
        payment = content.getDEPOSIT() + "";
        renovation = content.getFIXTURE() + "";
        et_room__shi.setText(content.getSHI() + "");
        et_room_ting.setText(content.getTING() + "");
        et_room_wei.setText(content.getWEI() + "");
        et_room_yangtai.setText(content.getYANGTAI() + "");
        et_room_area.setText(content.getSQUARE() + "");
        et_room_person.setText(content.getGALLERYFUL() + "");
        setProgressDialog(false);
        bt_room_submit.setEnabled(true);
        bt_room_submit.setClickable(true);
    }

    /**
     * 修改逻辑 检查，上传
     */
    private void mododifyRoomInfo() {
        mNumber = tv_room_number.getText().toString().trim();
        mRenovation = tv_room_renovation.getText().toString().trim();
        mPayment = tv_room_payment.getText().toString().trim();
        mShi = et_room__shi.getText().toString().trim();
        mTing = et_room_ting.getText().toString().trim();
        mWei = et_room_wei.getText().toString().trim();
        mYangTai = et_room_yangtai.getText().toString().trim();
        mArea = et_room_area.getText().toString().trim();
        mPerson = et_room_person.getText().toString().trim();
        load();
    }

    /**
     * 上传修改数据到服务器
     */
    private void load() {
        if (!isFinished){
            return;
        }
        setProgressDialog(true);
        mParam_chuZuWu_modifyRoom = new Param_ChuZuWu_ModifyRoom();
        mParam_chuZuWu_modifyRoom.setDEPOSIT(Integer.valueOf(payment));
        mParam_chuZuWu_modifyRoom.setFIXTURE(Integer.valueOf(renovation));
        mParam_chuZuWu_modifyRoom.setTaskID("1");
        mParam_chuZuWu_modifyRoom.setHOUSEID(mHouseId);
        mParam_chuZuWu_modifyRoom.setROOMID(mRoomId);
        mParam_chuZuWu_modifyRoom.setSHI(Integer.valueOf(mShi));
        mParam_chuZuWu_modifyRoom.setTING(Integer.valueOf(mTing));
        mParam_chuZuWu_modifyRoom.setWEI(Integer.valueOf(mWei));
        mParam_chuZuWu_modifyRoom.setYANGTAI(Integer.valueOf(mYangTai));
        mParam_chuZuWu_modifyRoom.setSQUARE(Integer.valueOf(mArea));
        mParam_chuZuWu_modifyRoom.setGALLERYFUL(Integer.valueOf(mPerson));
        ThreadPoolTask.Builder<ChuZuWu_Modify> builder = new ThreadPoolTask.Builder<ChuZuWu_Modify>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_ModifyRoom", mParam_chuZuWu_modifyRoom)
                .setBeanType(ChuZuWu_Modify.class)
                .setActivity(ModifyRoomActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_Modify>() {
                    @Override
                    public void onSuccess(ChuZuWu_Modify bean) {

                        mDialogConfirm.show();
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
    public void onSelect(int position, Basic_Dictionary_Kj bean) {
        String type = bean.getCOLUMNVALUE();
        String name = bean.getCOLUMNCOMMENT();
        switch (selectType) {
            case 0:
                renovation = type;
                tv_room_renovation.setText(name);
                break;
            case 1:
                payment = type;
                tv_room_payment.setText(name);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_room_submit:
                mododifyRoomInfo();
                break;
            case R.id.tv_room_renovation:
                selectType = 0;//装修
                renovationPop.showPopupWindow();
                break;
            case R.id.tv_room_payment:
                selectType = 1;//支付
                paymentPop.showPopupWindow();
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfirm() {
        finish();
    }

    @Override
    public void onSuccess(ChuZuWu_RoomInfo bean) {
        isFinished = true;
        setInfoFromNet(bean);
    }

    @Override
    public void onErrorResult(ErrorResult errorResult) {

    }

    public static void goActivity(Context context, String houseId, String roomId, String roomNo) {
        Intent intent = new Intent(context, ModifyRoomActivity.class);
        intent.putExtra("HOUSEID", houseId);
        intent.putExtra("ROOMID", roomId);
        intent.putExtra("ROOMNO", roomNo);
        context.startActivity(intent);
    }
}
