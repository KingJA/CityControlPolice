package com.tdr.citycontrolpolice.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.fragment.TabHomeFragment;
import com.tdr.citycontrolpolice.fragment.TabMineFragment;
import com.tdr.citycontrolpolice.fragment.TabWorkFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：Fragment工具类
 * 创建人：KingJA
 * 创建时间：2016/6/5 16:35
 * 修改备注：
 */
public class FragmentUtil {
    private static Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public static Fragment switchFragment(FragmentActivity activity, Fragment currentFragment, Fragment newFragment) {
        FragmentTransaction mTransaction = activity.getSupportFragmentManager().beginTransaction();
        if (!newFragment.isAdded()) {
            mTransaction.hide(currentFragment).add(R.id.fl_main, newFragment).commit();
        } else {
            mTransaction.hide(currentFragment).show(newFragment).commit();
        }
        return newFragment;
    }


    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentMap.get(position);
        if (fragment != null) {
            return fragment;
        } else {
            switch (position) {
                case 0:
                    fragment = new TabHomeFragment();
                    break;
                case 1:
                    fragment = new TabWorkFragment();
                    break;
                case 2:
                    fragment = new TabMineFragment();
                    break;
            }
            fragmentMap.put(position, fragment);
            return fragment;
        }
    }
}
