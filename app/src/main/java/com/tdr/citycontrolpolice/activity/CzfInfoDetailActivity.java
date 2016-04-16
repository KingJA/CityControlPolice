package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/13 16:41
 * 修改备注：
 */
public class CzfInfoDetailActivity extends BackTitleActivity {
    private static final String CZF_INFO_DETAIL = "CZF_INFO_DETAIL";
    private TextView mTvOwnerName;
    private TextView mTvOwnerPhone;
    private TextView mTvCzfName;
    private TextView mTvCzfAddress;
    private TextView mTvRoomCount;
    private TextView mTvPersonCount;
    private KjChuZuWuInfo mCzfInfo;
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> mRoomList;
    private KjChuZuWuInfo.ContentBean mContent;
    private int mRoomCount;
    private int mPersonCount;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czf_tongji, null);
        return view;
    }

    @Override
    public void initVariables() {
        mCzfInfo = (KjChuZuWuInfo) getIntent().getSerializableExtra(CZF_INFO_DETAIL);
        mContent = mCzfInfo.getContent();
    }

    @Override
    protected void initView() {
        mTvOwnerName = (TextView) view.findViewById(R.id.tv_ownerName);
        mTvOwnerPhone = (TextView) view.findViewById(R.id.tv_ownerPhone);
        mTvCzfName = (TextView) view.findViewById(R.id.tv_czfName);
        mTvCzfAddress = (TextView) view.findViewById(R.id.tv_czfAddress);
        mTvRoomCount = (TextView) view.findViewById(R.id.tv_roomCount);
        mTvPersonCount = (TextView) view.findViewById(R.id.tv_personCount);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        mRoomList = mCzfInfo.getContent().getRoomList();
        mRoomCount = mCzfInfo.getContent().getRoomList().size();
        for (KjChuZuWuInfo.ContentBean.RoomListBean bean : this.mRoomList) {
            if (bean.getHEADCOUNT() > 0) {
                mPersonCount += bean.getHEADCOUNT();
            }
        }
    }

    @Override
    public void setData() {

        setTitle("出租屋信息统计");
        mTvOwnerName.setText(mContent.getOWNERNAME());
        mTvOwnerPhone.setText(mContent.getPHONE());
        mTvCzfName.setText(mContent.getHOUSENAME());
        mTvCzfAddress.setText(mContent.getADDRESS());
        mTvRoomCount.setText(mRoomCount + "");
        mTvPersonCount.setText(mPersonCount + "");

    }

    public static void goActivity(Context context, Serializable serializable) {
        Intent intent = new Intent(context, CzfInfoDetailActivity.class);
        intent.putExtra(CZF_INFO_DETAIL, serializable);
        context.startActivity(intent);
    }
}
