package com.tdr.citycontrolpolice.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.view.dialog.DialogAddress;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/19 16:30
 * 修改备注：
 */
public class CzfInitActivity extends BackTitleActivity implements View.OnClickListener {
    private TextView mTvAddress;
    private ImageView mIvSearch;
    private EditText mEtCzfName;
    private TextView mTvCzfType;
    private EditText mEtAreaName;
    private TextView mTvPolice;
    private TextView mEtOwnerName;
    private TextView mEtOwnerCard;
    private EditText mEtOwnerPhone;
    private CheckBox mCbIsOwern;
    private LinearLayout mLlAdmin;
    private EditText mEtAdminName;
    private EditText mEtAdminCard;
    private EditText mEtAdminPhone;
    private ImageView mIvNumber;
    private ImageView mIvRoom;
    private Button mBtnSubmit;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czf_init, null);
        return view;
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search);
        mEtCzfName = (EditText) view.findViewById(R.id.et_czfName);
        mTvCzfType = (TextView) view.findViewById(R.id.tv_czfType);
        mEtAreaName = (EditText) view.findViewById(R.id.et_areaName);
        mTvPolice = (TextView) view.findViewById(R.id.tv_police);
        mEtOwnerName = (TextView) view.findViewById(R.id.et_ownerName);
        mEtOwnerCard = (TextView) view.findViewById(R.id.et_ownerCard);
        mEtOwnerPhone = (EditText) view.findViewById(R.id.et_ownerPhone);
        mCbIsOwern = (CheckBox) view.findViewById(R.id.cb_isOwern);
        mLlAdmin = (LinearLayout) view.findViewById(R.id.ll_admin);
        mEtAdminName = (EditText) view.findViewById(R.id.et_adminName);
        mEtAdminCard = (EditText) view.findViewById(R.id.et_adminCard);
        mEtAdminPhone = (EditText) view.findViewById(R.id.et_adminPhone);
        mIvNumber = (ImageView) view.findViewById(R.id.iv_number);
        mIvRoom = (ImageView) view.findViewById(R.id.iv_room);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
    }

    @Override
    public void initNet() {
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("出租房登记");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                DialogAddress dialogAddress = new DialogAddress(this);
                dialogAddress.show();
                break;
        }
    }
}
