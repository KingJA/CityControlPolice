package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BaseFragmentPagerTitleAdapter;
import com.tdr.citycontrolpolice.fragment.PersonAccreditFragment;
import com.tdr.citycontrolpolice.fragment.PersonHistoryFragment;
import com.tdr.citycontrolpolice.fragment.PersonInOutFragment;
import com.tdr.citycontrolpolice.fragment.PersonInfoFragment;
import com.tdr.citycontrolpolice.fragment.RoomInfoFragment;
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
    private List<String> mTitleList = Arrays.asList("房间信息", "综合信息", "门戒授权", "进出记录", "历史人数");
    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabLayout mTlPerson;
    private String mRoomNo;

    @Override
    public void initVariables() {
        Bundle bundle = getIntent().getExtras();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");
        mRoomNo = bundle.getString("ROOM_NO");
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czfinfo, null);
        return view;
    }

    @Override
    protected void initView() {
//        mSilPersonInfo = (SimpleIndicatorLayout) view.findViewById(R.id.sil_person_info);
        mVpPersonInfo = (ViewPager) view.findViewById(R.id.vp_person_info);
        mTlPerson = (TabLayout) view.findViewById(R.id.tl_person);
    }

    @Override
    public void initNet() {
    }

    @Override
    public void initData() {
        mFragmentList.add(RoomInfoFragment.newInstance(mRoomId,mRoomNo));
        mFragmentList.add(PersonInfoFragment.newInstance(mHouseId,mRoomId));
        mFragmentList.add(PersonAccreditFragment.newInstance(mHouseId,mRoomId));
        mFragmentList.add(PersonInOutFragment.newInstance(mHouseId,mRoomId));
        mFragmentList.add(PersonHistoryFragment.newInstance(mHouseId,mRoomId));

        mTlPerson.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < mTitleList.size(); i++) {
            mTlPerson.addTab(mTlPerson.newTab().setText(mTitleList.get(i)));
        }
        mVpPersonInfo.setAdapter(new BaseFragmentPagerTitleAdapter(getSupportFragmentManager(), mFragmentList,mTitleList));
        mVpPersonInfo.setOffscreenPageLimit(3);
        mTlPerson.setupWithViewPager(mVpPersonInfo);
    }

    @Override
    public void setData() {
        setTitle("人员信息");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
