package com.tdr.citycontrolpolice.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdr.citycontrolpolice.R;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：工具预警Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/26 10:20
 * 修改备注：
 */
public class TabWorkFragment extends Fragment implements View.OnClickListener {


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
