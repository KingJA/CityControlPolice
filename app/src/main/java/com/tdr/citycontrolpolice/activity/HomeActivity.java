package com.tdr.citycontrolpolice.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.fragment.TabHomeFragment;
import com.tdr.citycontrolpolice.receiver.NetChangedReceiver;
import com.tdr.citycontrolpolice.util.AppManager;
import com.tdr.citycontrolpolice.util.FragmentUtil;
import com.tdr.citycontrolpolice.util.ResourcesUtil;
import com.tdr.citycontrolpolice.util.StatusBarCompat;
import com.tdr.citycontrolpolice.util.ToastUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：主界面Activity
 * 创建人：KingJA
 * 创建时间：2016/6/5 10:56
 * 修改备注：
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private long firstTime;

    private LinearLayout mLlMainCzf;
    private ImageView mIvMainCzf;
    private LinearLayout mLlMainWork;
    private ImageView mIvMainWork;
    private LinearLayout mLlMainInfo;
    private LinearLayout mLlMainTongji;
    private ImageView mIvMainInfo;
    private Fragment mCurrentFragment;
    private int nCurrentPosition = -1;
    private int mSelectedPosition = -1;
    private ImageView mIvMainTongji;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initVariables() {

    }
    @Override
    public void initView() {
        mLlMainTongji = (LinearLayout) findViewById(R.id.ll_main_tongji);
        mLlMainCzf = (LinearLayout) findViewById(R.id.ll_main_czf);
        mLlMainWork = (LinearLayout) findViewById(R.id.ll_main_work);
        mLlMainInfo = (LinearLayout) findViewById(R.id.ll_main_info);
        mIvMainCzf = (ImageView) findViewById(R.id.iv_main_czf);
        mIvMainWork = (ImageView) findViewById(R.id.iv_main_work);
        mIvMainInfo = (ImageView) findViewById(R.id.iv_main_info);
        mIvMainTongji = (ImageView) findViewById(R.id.iv_main_tongji);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        mLlMainCzf.setOnClickListener(this);
        mLlMainWork.setOnClickListener(this);
        mLlMainInfo.setOnClickListener(this);
        mLlMainTongji.setOnClickListener(this);
        mCurrentFragment = new TabHomeFragment();
        mSupportFragmentManager.beginTransaction().add(R.id.fl_main, mCurrentFragment).commit();
    }

    @Override
    public void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_main_czf:
                selectTab(0);
                break;
            case R.id.ll_main_work:
                selectTab(1);
                break;
            case R.id.ll_main_tongji:
                selectTab(2);
                break;
            case R.id.ll_main_info:
                selectTab(3);
                break;
            default:
                break;
        }
    }

    private void selectTab(int position) {
        mSelectedPosition = position;
        if (mSelectedPosition == nCurrentPosition) {
            return;
        }
        setTabStatus();
        mCurrentFragment = FragmentUtil.switchFragment(this, mCurrentFragment, FragmentUtil.getFragment(position));
        nCurrentPosition = mSelectedPosition;
    }

    private void setTabStatus() {
        mIvMainCzf.setBackgroundResource(mSelectedPosition == 0 ? R.drawable.home_on : R.drawable.home_off);
        mIvMainWork.setBackgroundResource(mSelectedPosition == 1 ? R.drawable.police_on : R.drawable.police_off);
        mIvMainTongji.setBackgroundResource(mSelectedPosition == 2 ? R.drawable.tongji_on : R.drawable.tongji_off);
        mIvMainInfo.setBackgroundResource(mSelectedPosition == 3 ? R.drawable.personal_on : R.drawable.personal_off);
    }

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if ((secondTime - firstTime) > 2000) {
            ToastUtil.showMyToast(ResourcesUtil.getString(R.string.click_again_quit));
            firstTime = secondTime;
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }


}

