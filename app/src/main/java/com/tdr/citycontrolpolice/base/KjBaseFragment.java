package com.tdr.citycontrolpolice.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/23 13:10
 * 修改备注：
 */
public abstract class KjBaseFragment extends Fragment {
    protected String TAG = getClass().getSimpleName();
    protected String mHouseId;
    protected View rootView;
    protected Context mContext;
    protected Activity mActivity;
    private ProgressDialog mProgressDialog;
    private Unbinder bind;

    @Override
    public void onAttach(Context context) {
        this.mContext = context;
        mActivity = getActivity();
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = onFragmentCreateView(inflater, container, savedInstanceState);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public abstract View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initConmonView();
        initFragmentVariables();
        initFragmentView();
        initFragmentNet();
        initFragmentData();
        setFragmentData();
    }
    private void initConmonView() {
        mProgressDialog = new ProgressDialog(getActivity());
    }
    protected void setProgressDialog(boolean show) {
        if (show) {
            mProgressDialog.show();
        } else {
            mProgressDialog.dismiss();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
       bind.unbind();
    }

    protected abstract void initFragmentVariables();

    protected abstract void initFragmentView();

    protected abstract void initFragmentNet();

    protected abstract void initFragmentData();

    protected abstract void setFragmentData();
}
