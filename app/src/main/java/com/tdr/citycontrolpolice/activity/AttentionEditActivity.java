package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kingja.ui.wheelview.DeadlineSelector;
import com.kingja.ui.wheelview.TimeSelector;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.AttentionAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomListOfFavorites;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.FixedListView;

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
public class AttentionEditActivity extends BackTitleActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener,AdapterView.OnItemClickListener {
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

    private static final String HOUSE_ID="HOUSE_ID";
    private String houseId;
    private List<ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean> favortyRoomList=new ArrayList<>();
    private AttentionAdapter attentionAdapter;
    private LinearLayout ll_attention;
    private int radioGroupByType;

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
                .setBeanType( ChuZuWu_RoomListOfFavorites.class)
                .setCallBack(new WebServiceCallBack< ChuZuWu_RoomListOfFavorites>() {
                    @Override
                    public void onSuccess( ChuZuWu_RoomListOfFavorites bean) {
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
        mLvAttentionRoom.setOnItemClickListener(this);
        mEtTimeFrom.setOnClickListener(this);
        mEtTimeTo.setOnClickListener(this);
        mEtDateFrom.setOnClickListener(this);
        mEtDateTo.setOnClickListener(this);
        mRgAttention.setOnCheckedChangeListener(this);
        setOnRightClickListener(new OnRightClickListener() {
            @Override
            public void onRightClick() {
                ToastUtil.showMyToast("取消关注");
            }
        });

    }

    @Override
    public void setData() {
        setTitle("提醒设置");
        setRightTextVisibility("取消关注");

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
                ToastUtil.showMyToast("仅关注");
                ll_only.setVisibility(View.GONE);
                break;
            case R.id.rb_once:
                ToastUtil.showMyToast("提醒一次");
                ll_once.setVisibility(View.GONE);
                break;
            case R.id.rb_many:
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

        }
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AttentionEditActivity.class);
        intent.putExtra(HOUSE_ID,houseId);
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ll_attention.setVisibility(View.VISIBLE);
        attentionAdapter.selectItem(position);
        ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean attentionRoom = (ChuZuWu_RoomListOfFavorites.ContentBean.MonitorRoomListBean) parent.getItemAtPosition(position);
        int remindType = attentionRoom.getREMIND_TYPE();
        setRadioGroupByType(remindType);
    }

    public void setRadioGroupByType(int type) {
        switch (type) {
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
}
