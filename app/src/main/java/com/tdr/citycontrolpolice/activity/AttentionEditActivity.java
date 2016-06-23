package com.tdr.citycontrolpolice.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kingja.ui.wheelview.DialogTimeWheel;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.view.FixedListView;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 15:46
 * 修改备注：
 */
public class AttentionEditActivity extends BackTitleActivity {
    private CheckBox mCbTogether;
    private FixedListView mLvAttentionRoom;
    private RadioGroup mRgAttention;
    private RadioButton mRbCancel;
    private RadioButton mRbAttention;
    private RadioButton mRbOnce;
    private RadioButton mRbMany;
    private EditText mEtAttentionPhone;
    private EditText mEtDateFrom;
    private EditText mEtDateTo;
    private EditText mEtTimeFrom;
    private EditText mEtTimeTo;
    private DialogTimeWheel timeFromDialog;
    private DialogTimeWheel timeToDialog;

    @Override
    public View setContentView() {
        view=View.inflate(this, R.layout.activity_attention_edti,null);
        return view;
    }


    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        mCbTogether = (CheckBox) view.findViewById(R.id.cb_together);
        mLvAttentionRoom = (FixedListView) view.findViewById(R.id.lv_attention_room);
        mRgAttention = (RadioGroup) view.findViewById(R.id.rg_attention);
        mRbCancel = (RadioButton) view.findViewById(R.id.rb_cancel);
        mRbAttention = (RadioButton) view.findViewById(R.id.rb_attention);
        mRbOnce = (RadioButton) view.findViewById(R.id.rb_once);
        mRbMany = (RadioButton) view.findViewById(R.id.rb_many);
        mEtAttentionPhone = (EditText) view.findViewById(R.id.et_attention_phone);
        mEtDateFrom = (EditText) view.findViewById(R.id.et_date_from);
        mEtDateTo = (EditText) view.findViewById(R.id.et_date_to);
        mEtTimeFrom = (EditText) view.findViewById(R.id.et_time_from);
        mEtTimeTo = (EditText) view.findViewById(R.id.et_time_to);

        timeFromDialog = new DialogTimeWheel(this);
        timeToDialog = new DialogTimeWheel(this);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

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
    private DialogTimeWheel.OnTimeSelectListener onTimeSelectListener=new DialogTimeWheel.OnTimeSelectListener(){

        @Override
        public void onTimeSelect(String hour, String second) {

        }
    };


}
