package com.tdr.citycontrolpolice.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BaseFragmentPagerTitleAdapter;
import com.tdr.citycontrolpolice.fragment.CzfQueryFragment;
import com.tdr.citycontrolpolice.fragment.StandardAddressQueryFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/3/31 8:50
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class QueryActivity extends BackTitleActivity {

    private TabLayout mTabQuery;
    private ViewPager mVpQuery;
    private List<String> mTabTitles = Arrays.asList("标准地址查询", "出租房查询");
    private List<Fragment> mFragmentList = new ArrayList<>();
    @Override
    public void initVariables() {
        mFragmentList.clear();
        mFragmentList.add(new StandardAddressQueryFragment());
        mFragmentList.add(new CzfQueryFragment());
    }

    @Override
    protected void initView() {
        mTabQuery = (TabLayout) findViewById(R.id.tab_query);
        mVpQuery = (ViewPager) findViewById(R.id.vp_query);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        for (int i = 0; i < mTabTitles.size(); i++) {
            mTabQuery.addTab(mTabQuery.newTab().setText(mTabTitles.get(i)));
        }
        mVpQuery.setAdapter(new BaseFragmentPagerTitleAdapter(getSupportFragmentManager(), mFragmentList,mTabTitles));
        mTabQuery.setupWithViewPager(mVpQuery);
    }

    @Override
    public void setData() {
        setTitle("查询");
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_query, null);
        return view;
    }
}
