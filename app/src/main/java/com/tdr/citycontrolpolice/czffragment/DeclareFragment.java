package com.tdr.citycontrolpolice.czffragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.DeclareAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/19.
 */
public class DeclareFragment extends Fragment {

    private ListView lv_czf_declare;
    private DeclareAdapter declareAdapter;
    private ArrayList<ChuZuWuInfo> listCZF;
    private ImageView img_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_czf_declare, container, false);//关联布局文件

        //ListView
        lv_czf_declare = (ListView) rootView.findViewById(R.id.lv_czf_declare);
        getListCZF();
        declareAdapter = new DeclareAdapter(listCZF, getActivity());
        lv_czf_declare.setAdapter(declareAdapter);
        lv_czf_declare.setOnItemClickListener(new LvDeclareListener());


        return rootView;

    }

    private class LvDeclareListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }

    private void getListCZF() {
        listCZF = new ArrayList<ChuZuWuInfo>();
        for (int i = 0; i < 10; i++) {
            ChuZuWuInfo czf = new ChuZuWuInfo();
            czf.setHOUSENAME("申报房间" + i);
            listCZF.add(czf);
        }

    }

}
