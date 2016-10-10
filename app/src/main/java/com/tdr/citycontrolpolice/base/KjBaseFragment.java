package com.tdr.citycontrolpolice.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/23 13:10
 * 修改备注：
 */
public abstract class KjBaseFragment extends Fragment {

    protected String mHouseId;
    protected View rootView;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        mActivity = getActivity();
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = onFragmentCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    public abstract View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initFragmentVariables();
        initFragmentView();
        initFragmentNet();
        initFragmentData();
        setFragmentData();
    }

    protected abstract void initFragmentVariables();

    protected abstract void initFragmentView();

    protected abstract void initFragmentNet();

    protected abstract void initFragmentData();

    protected abstract void setFragmentData();
}
