package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_EditReportInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.event.InfoInFragmetnEvent;
import com.tdr.citycontrolpolice.fragment.InfoInFragment;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.popupwindow.RoomSelectPopDirect;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：申报编辑
 * 创建人：KingJA
 * 创建时间：2016/3/25 13:30
 * 修改备注：
 */
public class ApplyEditActivity extends BackTitleActivity {
    public static final String LISTID = "LISTID";
    public static final String HOUSE_ID = "HOUSE_ID";
    public static final String NAME = "NAME";
    public static final String PHONE = "PHONE";
    public static final String CARDID = "CARDID";
    private String listId;
    private RoomSelectPopDirect roomSelectPop;
    private String houseId;
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
    }

    @Override
    protected void initView() {
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
                        setProgressDialog(false);
                        roomSelectPop = new RoomSelectPopDirect(mRlSelect, ApplyEditActivity.this, bean.getContent().getRoomList());
                        roomSelectPop.setOnRoomSelectListener(new RoomSelectPopDirect.OnRoomSelectListener() {
                            @Override
                            public void onSelect(int position, KjChuZuWuInfo.ContentBean.RoomListBean bean) {
                                mTvRoom.setText(bean.getROOMNO() + "");
                                selectRoomId = bean.getROOMID();
                                Log.e(TAG, "selectRoomId: " + selectRoomId);
                            }
                        });
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }


    @Override
    public void initData() {
        mRlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomSelectPop.showPopupWindowDown();
            }
        });
        mTvApplyEditConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEvApplyEditName.getText().toString().trim();
                String cardId = mEtApplyEditCardId.getText().toString().trim();
                if (CheckUtil.checkIdCard(cardId, "身份证号码格式有误") && CheckUtil.checkEmpty(name,"姓名不能为空")&&CheckUtil.checkEmpty(selectRoomId,"请选择房间")) {
                    upload(name,cardId);
                }
            }
        });
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
                        ToastUtil.showMyToast("申报信息修改成功");
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
        setTitle("申报信息修改");
        mEvApplyEditName.setText(name);
        mTvApplyEditPhone.setText(phone);
        mEtApplyEditCardId.setText(cardId);
    }

    public static void goActivity(Context context, String listId, String houseId, String name, String phone, String cardId) {
        Intent intent = new Intent(context, ApplyEditActivity.class);
        intent.putExtra(LISTID, listId);
        intent.putExtra(HOUSE_ID, houseId);
        intent.putExtra(NAME, name);
        intent.putExtra(PHONE, phone);
        intent.putExtra(CARDID, cardId);
        context.startActivity(intent);
    }
}
