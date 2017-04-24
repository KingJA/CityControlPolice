package com.tdr.citycontrolpolice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：FragmentPagerAdapter基类
 * 创建人：KingJA
 * 创建时间：2016/3/24 9:09
 * 修改备注：
 */
public class BaseFragmentPagerTitleAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<String> titleList;

    public BaseFragmentPagerTitleAdapter(FragmentManager fm, List<Fragment> list, List<String>titleList) {
        super(fm);
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
