package com.tdr.citycontrolpolice.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.RemindAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_PushInfoOfMonitorRoom;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 13:19
 * 修改备注：
 */
public class RemindActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private LinearLayout mLlEmpty;
    private List<ChuZuWu_PushInfoOfMonitorRoom.ContentBean> attentionList=new ArrayList<>();
    private RemindAdapter remindAdapter;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.single_lv_divider, null);
        return view;
    }


    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSingleSrl = (SwipeRefreshLayout) findViewById(R.id.single_srl);
        mSingleLv = (ListView) findViewById(R.id.single_lv);
        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        remindAdapter = new RemindAdapter(this, attentionList);
        mSingleLv.setAdapter(remindAdapter);
    }

    @Override
    public void initNet() {
        mSingleSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", "");
        param.put("ROOMID", "");
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_PUSHINFOOFMONITORROOM, param)
                .setBeanType(ChuZuWu_PushInfoOfMonitorRoom.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_PushInfoOfMonitorRoom>() {
                    @Override
                    public void onSuccess( ChuZuWu_PushInfoOfMonitorRoom bean) {
                        mSingleSrl.setRefreshing(false);
                        attentionList = bean.getContent();
                        mLlEmpty.setVisibility(attentionList.size()>0?View.GONE:View.VISIBLE);
                        remindAdapter.setData(attentionList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSingleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        mSingleSrl.setOnRefreshListener(this);
    }

    @Override
    public void setData() {
        setTitle("提醒记录");
    }

    @Override
    public void onRefresh() {
        initNet();
    }
}
