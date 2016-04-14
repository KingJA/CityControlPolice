package com.tdr.citycontrolpolice.czffragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdr.citycontrolpolice.R;

/**
 * Created by Administrator on 2016/3/19.
 */
public class SystemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_czf_system, container, false);//关联布局文件
        return rootView;

    }
}
