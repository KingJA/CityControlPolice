package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BaseFragmentPagerAdapter;
import com.tdr.citycontrolpolice.fragment.InfoInFragment;
import com.tdr.citycontrolpolice.fragment.InfoManagerFragment;
import com.tdr.citycontrolpolice.fragment.PersonAccreditFragment;
import com.tdr.citycontrolpolice.fragment.PersonInfoFragment;
import com.tdr.citycontrolpolice.view.SimpleIndicatorLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：人员信息
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:52
 * 修改备注：
 */
public class PersonInfoActivity extends BackTitleActivity {

    private static final String TAG = "PersonInfoActivity";
    private String mHouseId;
    private String mRoomId;
    private SimpleIndicatorLayout mSilPersonInfo;
    private ViewPager mVpPersonInfo;
    private List<String> mTitleList = Arrays.asList("综合人员信息", "门戒授权");
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    public void initVariables() {
        Bundle bundle = getIntent().getExtras();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czfinfo, null);
        return view;
    }

    @Override
    protected void initView() {
        mSilPersonInfo = (SimpleIndicatorLayout) view.findViewById(R.id.sil_person_info);
        mVpPersonInfo = (ViewPager) view.findViewById(R.id.vp_person_info);
    }

    @Override
    public void initNet() {
    }

    @Override
    public void initData() {
        mFragmentList.add(PersonInfoFragment.newInstance(mHouseId,mRoomId));
        mFragmentList.add(PersonAccreditFragment.newInstance(mHouseId,mRoomId));
        mVpPersonInfo.setOffscreenPageLimit(mTitleList.size() - 1);
        mVpPersonInfo.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mSilPersonInfo.setTitles(mTitleList);
        mSilPersonInfo.setUpWithViewPager(mVpPersonInfo, 0);
    }

    @Override
    public void setData() {
        setTitle("人员信息");
    }

}
