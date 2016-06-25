package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kingja.ui.wheelview.DeadlineSelector;
import com.kingja.ui.wheelview.TimeSelector;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.AttentionAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomListOfFavorites;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SetRoomInfoOfFavorites;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_SetRoomInfoOfFavorites;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 15:46
 * 修改备注：
 */
public class AttentionEditActivity extends BackTitleActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, BackTitleActivity.OnRightClickListener, CompoundButton.OnCheckedChangeListener {
    private CheckBox mCbTogether;
    private ListView mLvAttentionRoom;
    private RadioGroup mRgAttention;
    private RadioButton mRbOnly;
    private RadioButton mRbOnce;
    private RadioButton mRbMany;
    private EditText mEtAttentionPhone;
    private EditText mEtDateFrom;
    private EditText mEtDateTo;
    private EditText mEtTimeFrom;
    private EditText mEtTimeTo;
    private TimeSelector timeFromSelector;
    private TimeSelector timeToSelector;
    private LinearLayout ll_only;
    private LinearLayout ll_once;

    private static final String HOUSE_ID = "HOUSE_ID";
    private String houseId;
    private List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> favortyRoomList = new ArrayList<>();
    private AttentionAdapter attentionAdapter;
    private LinearLayout ll_attention;

    private int currentAttentionType = 0;
    private int currentPosition;
    private boolean isTogether;
    private TextView tv_setting;
    private Param_ChuZuWu_SetRoomInfoOfFavorites.ContentBean contentBean;
    private Param_ChuZuWu_SetRoomInfoOfFavorites param;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_attention_edti, null);
        return view;
    }


    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
    }

    @Override
    protected void initView() {
        tv_setting = (TextView) view.findViewById(R.id.tv_setting);
        ll_attention = (LinearLayout) view.findViewById(R.id.ll_attention);
        ll_only = (LinearLayout) view.findViewById(R.id.ll_only);
        ll_once = (LinearLayout) view.findViewById(R.id.ll_once);
        mCbTogether = (CheckBox) view.findViewById(R.id.cb_together);
        mLvAttentionRoom = (ListView) view.findViewById(R.id.lv_attention_room);
        mRgAttention = (RadioGroup) view.findViewById(R.id.rg_attention);
        mRbOnly = (RadioButton) view.findViewById(R.id.rb_only);
        mRbOnce = (RadioButton) view.findViewById(R.id.rb_once);
        mRbMany = (RadioButton) view.findViewById(R.id.rb_many);
        mEtAttentionPhone = (EditText) view.findViewById(R.id.et_attention_phone);
        mEtDateFrom = (EditText) view.findViewById(R.id.et_date_from);
        mEtDateTo = (EditText) view.findViewById(R.id.et_date_to);
        mEtTimeFrom = (EditText) view.findViewById(R.id.et_time_from);
        mEtTimeTo = (EditText) view.findViewById(R.id.et_time_to);
        attentionAdapter = new AttentionAdapter(this, favortyRoomList);
        mLvAttentionRoom.setAdapter(attentionAdapter);


    }

    @Override
    public void initNet() {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_ROOMLISTOFFAVORITES, param)
                .setBeanType(ChuZuWu_RoomListOfFavorites.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_RoomListOfFavorites>() {
                    @Override
                    public void onSuccess(ChuZuWu_RoomListOfFavorites bean) {
                        favortyRoomList = bean.getContent().getMonitorRoomList();
                        attentionAdapter.setData(favortyRoomList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        mCbTogether.setOnCheckedChangeListener(this);
        mLvAttentionRoom.setOnItemClickListener(this);
        tv_setting.setOnClickListener(this);
        mEtTimeFrom.setOnClickListener(this);
        mEtTimeTo.setOnClickListener(this);
        mEtDateFrom.setOnClickListener(this);
        mEtDateTo.setOnClickListener(this);
        mRgAttention.setOnCheckedChangeListener(this);
        setOnRightClickListener(this);
    }

    @Override
    public void setData() {
        setTitle("提醒设置");
        setRightTextVisibility("提交");

    }

    private TimeSelector.OnTimeSelectListener onTimeFromListener = new TimeSelector.OnTimeSelectListener() {

        @Override
        public void onTimeSelect(String hour, String second) {
            mEtTimeFrom.setText(hour + ":" + second);

        }
    };
    private TimeSelector.OnTimeSelectListener onTimeToListener = new TimeSelector.OnTimeSelectListener() {

        @Override
        public void onTimeSelect(String hour, String second) {
            mEtTimeTo.setText(hour + ":" + second);
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        ll_only.setVisibility(View.VISIBLE);
        ll_once.setVisibility(View.VISIBLE);
        switch (checkedId) {
            case R.id.rb_only:
                currentAttentionType = 0;
                ll_only.setVisibility(View.GONE);
                break;
            case R.id.rb_once:
                currentAttentionType = 1;
                ToastUtil.showMyToast("提醒一次");
                ll_once.setVisibility(View.GONE);
                break;
            case R.id.rb_many:
                currentAttentionType = 2;
                ToastUtil.showMyToast("提醒多次");
                break;


        }
    }

    private DeadlineSelector.OnDateSelectListener onDateFromListener = new DeadlineSelector.OnDateSelectListener() {
        @Override
        public void onClick(String year, String month, String day) {
            mEtDateFrom.setText(year + "-" + month + "-" + day);


        }
    };
    private DeadlineSelector.OnDateSelectListener onDateToListener = new DeadlineSelector.OnDateSelectListener() {
        @Override
        public void onClick(String year, String month, String day) {
            mEtDateTo.setText(year + "-" + month + "-" + day);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_date_from:
                DeadlineSelector dateFromSelector = new DeadlineSelector(this);
                dateFromSelector.setOnDateSelectListener(onDateFromListener);
                dateFromSelector.show();
                break;
            case R.id.et_date_to:
                DeadlineSelector dateToSelector = new DeadlineSelector(this);
                dateToSelector.setOnDateSelectListener(onDateToListener);
                dateToSelector.show();
                break;
            case R.id.et_time_from:
                timeFromSelector = new TimeSelector(this);
                timeFromSelector.setOnTimeSelectListener(onTimeFromListener);
                timeFromSelector.show();
                break;
            case R.id.et_time_to:
                timeToSelector = new TimeSelector(this);
                timeToSelector.setOnTimeSelectListener(onTimeToListener);
                timeToSelector.show();
                break;
            case R.id.tv_setting:
                setAttention();
                break;


        }
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AttentionEditActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition = position;
        attentionAdapter.selectItem(position);
        ll_attention.setVisibility(View.VISIBLE);

        ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean attentionRoom = (ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean) parent.getItemAtPosition(position);
        showAttentionByType(attentionRoom);
    }

    public void showAttentionByType(ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean attentionRoom) {
        mEtAttentionPhone.setText(attentionRoom.getTARGET());
        mEtDateFrom.setText(attentionRoom.getSTARTDATE());
        mEtDateTo.setText(attentionRoom.getENDDATE());
        mEtTimeFrom.setText(attentionRoom.getSTARTTIME());
        mEtTimeTo.setText(attentionRoom.getENDTIME());
        switch (attentionRoom.getREMIND_TYPE()) {
            case 0:
                mRgAttention.check(R.id.rb_only);
                break;
            case 1:
                mRgAttention.check(R.id.rb_once);
                break;
            case 2:
                mRgAttention.check(R.id.rb_many);
                break;

        }
    }

    public void setAttention() {
        String mAttentionPhone = mEtAttentionPhone.getText().toString().trim();
        String mDateFrom = mEtDateFrom.getText().toString().trim();
        String mDateTo = mEtDateTo.getText().toString().trim();
        String mTimeFrom = mEtTimeFrom.getText().toString().trim();
        String mTimeTo = mEtTimeTo.getText().toString().trim();
        switch (currentAttentionType) {
            case 0:
                if (isTogether) {
                    attentionAdapter.setAttentionByPosition(currentAttentionType, "", "", "", "", "", currentPosition);
                } else {
                    attentionAdapter.setAttentionByPosition(currentAttentionType, "", "", "", "", "", currentPosition);
                }
                break;
            case 1:
                if (CheckUtil.checkPhoneFormat(mAttentionPhone)
                        && CheckUtil.checkEmpty(mTimeFrom, "请设置开始时间")
                        && CheckUtil.checkEmpty(mTimeTo, "请设置结束时间")) {
                    if (isTogether) {
                        attentionAdapter.setAttentionByList(currentAttentionType, mAttentionPhone, "", "", mTimeFrom, mTimeTo);
                    } else {
                        attentionAdapter.setAttentionByPosition(currentAttentionType, mAttentionPhone, "", "", mTimeFrom, mTimeTo, currentPosition);
                    }
                }
                break;
            case 2:
                if (CheckUtil.checkPhoneFormat(mAttentionPhone)
                        && CheckUtil.checkEmpty(mDateFrom, "请设置开始日期")
                        && CheckUtil.checkEmpty(mDateFrom, "请设置结束日期")
                        && CheckUtil.checkEmpty(mDateFrom, "请设置开始时间")
                        && CheckUtil.checkEmpty(mDateFrom, "请设置结束时间")) {
                    if (isTogether) {
                        attentionAdapter.setAttentionByList(currentAttentionType, mAttentionPhone, mDateFrom, mDateTo, mTimeFrom, mTimeTo);
                    } else {
                        attentionAdapter.setAttentionByPosition(currentAttentionType, mAttentionPhone, mDateFrom, mDateTo, mTimeFrom, mTimeTo, currentPosition);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRightClick() {
        List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> attentionList = attentionAdapter.getAttentionList();
        if (attentionList.size() > 0) {
            upload(attentionList);
        } else {
            ToastUtil.showMyToast("请勾选要关注的房间");
        }
    }

    /**
     * 上传配置信息
     * @param attentionList
     */
    private void upload(List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> attentionList) {
        setProgressDialog(true);
        Log.e("attentionList", attentionList.toString());
        param = new Param_ChuZuWu_SetRoomInfoOfFavorites();
        param.setTaskID("1");
        param.setHOUSEID(houseId);
        List<Param_ChuZuWu_SetRoomInfoOfFavorites.ContentBean> contentList = new ArrayList<>();
        for (ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean bean:attentionList) {
            contentBean = new Param_ChuZuWu_SetRoomInfoOfFavorites.ContentBean();
            contentBean.setROOMID(bean.getROOMID());
            contentBean.setREMIND_TYPE(bean.getREMIND_TYPE());
            contentBean.setTARGET(bean.getTARGET());
            contentBean.setSTARTDATE(bean.getSTARTDATE());
            contentBean.setENDDATE(bean.getENDDATE());
            contentBean.setSTARTTIME(bean.getSTARTTIME());
            contentBean.setENDTIME(bean.getENDTIME());
            contentList.add(contentBean);
        }
        param.setContent(contentList);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_SETROOMINFOOFFAVORITES, param)
                .setBeanType(ChuZuWu_SetRoomInfoOfFavorites.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_SetRoomInfoOfFavorites>() {
                    @Override
                    public void onSuccess(ChuZuWu_SetRoomInfoOfFavorites bean) {
                        setProgressDialog(false);
                        DialogConfirm dialogConfirm = new DialogConfirm(AttentionEditActivity.this, "提醒配置完成", "确定");
                        dialogConfirm.setOnConfirmClickListener(new DialogConfirm.OnConfirmClickListener() {
                            @Override
                            public void onConfirm() {
                                finish();
                            }
                        });
                        dialogConfirm.show();

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isTogether = isChecked;
        mRgAttention.check(R.id.rb_only);
        ll_attention.setVisibility(isChecked?View.VISIBLE:View.GONE);
        if (isChecked) {
            attentionAdapter.reset();
        }
    }
}
