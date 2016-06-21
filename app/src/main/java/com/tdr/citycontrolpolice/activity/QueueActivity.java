package com.tdr.citycontrolpolice.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.QueueAdapter;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.OCR_Kj;
import com.tdr.citycontrolpolice.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/20 15:24
 * 修改备注：
 */
public class QueueActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private List<OCR_Kj> queue=new ArrayList<>();
    private QueueAdapter queueAdapter;

    @Override
    public View setContentView() {
        view=View.inflate(this, R.layout.single_lv,null);
        return view;
    }

    @Override
    public void initVariables() {
        queue = DbDaoXutils3.getInstance().selectAll(OCR_Kj.class);
        Log.e("queue", queue.toString());
    }

    @Override
    protected void initView() {
        mSingleSrl = (SwipeRefreshLayout) view.findViewById(R.id.single_srl);
        mSingleLv = (ListView) view.findViewById(R.id.single_lv);
        queueAdapter = new QueueAdapter(this, queue);
        mSingleLv.setAdapter(queueAdapter);

    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        mSingleSrl.setOnRefreshListener(this);
        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    public void setData() {
        setTitle("上传队列");

    }

    @Override
    public void onRefresh() {
        mSingleSrl.setRefreshing(false);
        queue = DbDaoXutils3.getInstance().selectAll(OCR_Kj.class);
        queueAdapter.setData(queue);
    }
}
