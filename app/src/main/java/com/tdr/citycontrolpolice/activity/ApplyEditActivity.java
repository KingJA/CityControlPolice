package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_EditReportInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.event.InfoInFragmetnEvent;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.popupwindow.RoomSelectPopDirect;
import com.yunmai.android.engine.OcrEngine;
import com.yunmai.android.idcard.ACamera;
import com.yunmai.android.vo.IDCard;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：申报编辑
 * 创建人：KingJA
 * 创建时间：2016/3/25 13:30
 * 修改备注：
 */
public class ApplyEditActivity extends BackTitleActivity implements View.OnClickListener{
    public static final String ROOMID = "ROOMID";
    public static final String LISTID = "LISTID";
    public static final String HOUSE_ID = "HOUSE_ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String CARDID = "CARDID";
    private String listId;
    private RoomSelectPopDirect roomSelectPop;
    private String houseId;
    private String roomId;
    private String name;
    private String phone;
    private String cardId;
    private String selectRoomId;
    private RelativeLayout mRlSelect;
    private TextView mTvRoom;
    private ImageView mIvArrow;
    private TextView mEvApplyEditName;
    private TextView mTvApplyEditPhone;
    private EditText mEtApplyEditCardId;
    private TextView mTvApplyEditConfirm;
    private Map<String, Integer> roomMap;
    private boolean doNetSuccess;
    private ImageView mIvCamera;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_apply_edit, null);
        return view;
    }


    @Override
    public void initVariables() {
        listId = getIntent().getStringExtra(LISTID);
        houseId = getIntent().getStringExtra(HOUSE_ID);
        name = getIntent().getStringExtra(NAME);
        phone = getIntent().getStringExtra(PHONE);
        cardId = getIntent().getStringExtra(CARDID);
        roomId = getIntent().getStringExtra(ROOMID);
    }

    @Override
    protected void initView() {
        mIvCamera = (ImageView) findViewById(R.id.iv_camera);
        mRlSelect = (RelativeLayout) findViewById(R.id.rl_select);
        mTvRoom = (TextView) findViewById(R.id.tv_room);
        mIvArrow = (ImageView) findViewById(R.id.iv_arrow);
        mEvApplyEditName = (TextView) findViewById(R.id.et_apply_edit_name);
        mTvApplyEditPhone = (TextView) findViewById(R.id.tv_apply_edit_phone);
        mEtApplyEditCardId = (EditText) findViewById(R.id.et_apply_edit_cardId);
        mTvApplyEditConfirm = (TextView) findViewById(R.id.tv_apply_edit_confirm);
    }

    @Override
    public void initNet() {
        Map<String, String> mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("HouseID", houseId);
        setProgressDialog(true);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        doNetSuccess = true;
                        initRoomNo(bean);
                        roomSelectPop = new RoomSelectPopDirect(mRlSelect, ApplyEditActivity.this, bean.getContent().getRoomList());
                        roomSelectPop.setOnRoomSelectListener(new RoomSelectPopDirect.OnRoomSelectListener() {
                            @Override
                            public void onSelect(int position, KjChuZuWuInfo.ContentBean.RoomListBean bean) {

                                mTvRoom.setText(bean.getROOMNO() + "");
                                selectRoomId = bean.getROOMID();
                                Log.e(TAG, "selectRoomId: " + selectRoomId);
                            }
                        });
                        setProgressDialog(false);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void initRoomNo(KjChuZuWuInfo bean) {
        roomMap = new HashMap<>();
        List<KjChuZuWuInfo.ContentBean.RoomListBean> roomList = bean.getContent().getRoomList();
        for (KjChuZuWuInfo.ContentBean.RoomListBean roomBean : roomList) {
            roomMap.put(roomBean.getROOMID(),roomBean.getROOMNO());
        }

        if (roomMap.get(roomId) != null) {
            selectRoomId=roomId;
            mTvRoom.setText(roomMap.get(roomId) + "");
        }
    }


    @Override
    public void initData() {
        mIvCamera.setOnClickListener(this);
        mRlSelect.setOnClickListener(this);
        mTvApplyEditConfirm.setOnClickListener(this);
    }

    private void upload(String name,String cardId) {
        Map<String, String> mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("LISTID", listId);
        mParam.put("ROOMID", selectRoomId);
        mParam.put("NAME", name);
        mParam.put("IDENTITYCARD", cardId);
        setProgressDialog(true);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_EditReportInfo", mParam)
                .setBeanType(ChuZuWu_EditReportInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_EditReportInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_EditReportInfo bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("申报信息确认成功");
                        finish();
                        EventBus.getDefault().post(new InfoInFragmetnEvent());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();

    }

    @Override
    public void setData() {
        setTitle("申报信息确认");
        mEvApplyEditName.setText(name);
        mTvApplyEditPhone.setText(phone);
        mEtApplyEditCardId.setText(cardId);
    }

    public static void goActivity(Context context, String listId, String houseId, String roomId, String name, String phone, String cardId) {
        Intent intent = new Intent(context, ApplyEditActivity.class);
        intent.putExtra(LISTID, listId);
        intent.putExtra(HOUSE_ID, houseId);
        intent.putExtra(NAME, name);
        intent.putExtra(PHONE, phone);
        intent.putExtra(CARDID, cardId);
        intent.putExtra(ROOMID, roomId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_camera:
                ActivityUtil.goActivityForResult(ApplyEditActivity.this, ACamera.class, 100);
                break;
            case R.id.rl_select:
                if (doNetSuccess) {
                    roomSelectPop.showPopupWindowDown();
                }else{
                    ToastUtil.showMyToast("数据未获取");
                }
                break;
            case R.id.tv_apply_edit_confirm:
                String name = mEvApplyEditName.getText().toString().trim();
                String cardId = mEtApplyEditCardId.getText().toString().trim();
                if (CheckUtil.checkIdCard(cardId, "身份证号码格式有误") && CheckUtil.checkEmpty(name,"姓名不能为空")&&CheckUtil.checkEmpty(selectRoomId,"请选择房间")) {
                    upload(name,cardId);
                }
                break;
            default:
                break;

        }
    }
    private IDCard idCard;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case 100:
                setProgressDialog(true);
                final Intent result = data;

                new Thread() {
                    @Override
                    public void run() {
                        OcrEngine ocrEngine = new OcrEngine();
                        try {
                            byte[] bytes = result.getByteArrayExtra("idcardA");

                            String headPath = "";
                            idCard = ocrEngine.recognize(ApplyEditActivity.this, bytes, null, headPath);
                            if (idCard.getRecogStatus() == OcrEngine.RECOG_OK) {
                                setProgressDialog(false);
                                mOcrHandler.sendMessage(mOcrHandler.obtainMessage(OcrEngine.RECOG_OK, headPath));
                            } else {
                                setProgressDialog(false);
                                mOcrHandler.sendEmptyMessage(idCard.getRecogStatus());
                            }
                        } catch (Exception e) {
                            setProgressDialog(false);
                            mOcrHandler.sendEmptyMessage(OcrEngine.RECOG_FAIL);
                        }
                    }
                }.start();
                break;
        }
    }
    public static final int REQUEST_CODE_RECOG = 113;  //	识别
    public static final int RESULT_RECOG_SUCCESS = 103;//识别成功
    public static final int RESULT_RECOG_FAILED = 104;//识别失败
    private Handler mOcrHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OcrEngine.RECOG_FAIL:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_BLUR:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_OK:
                    mEtApplyEditCardId.setText(idCard.getCardNo());
                    break;
                case OcrEngine.RECOG_IMEI_ERROR:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_imei, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_FAIL_CDMA:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_cdma, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_LICENSE_ERROR:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_licens, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_TIME_OUT:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_time_out, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_ENGINE_INIT_ERROR:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_engine_init, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_COPY:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_fail_copy, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(ApplyEditActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };
}
