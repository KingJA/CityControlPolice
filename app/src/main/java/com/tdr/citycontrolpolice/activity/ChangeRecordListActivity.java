package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.ChangeRecordAdapter;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_XingZhengQuHua_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ChangeMenPaiList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：登记牌变更记录
 * 创建人：KingJA
 * 创建时间：2016/6/30 15:08
 * 修改备注：
 */
public class ChangeRecordListActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String HOUSE_ID = "HOUSE_ID";
    private String houseId;
    private List<ChuZuWu_ChangeMenPaiList.ContentBean> chagngeRecordList = new ArrayList<>();
    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private LinearLayout ll_empty;
    private ChangeRecordAdapter changeRecordAdapter;
    private Map<String, String> cityMap;
    private List<Basic_XingZhengQuHua_Kj> cityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.single_lv_divider, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
        initCitys();
    }

    private void initCitys() {
        cityMap = new HashMap<>();
        cityList = DbDaoXutils3.getInstance().selectAll(Basic_XingZhengQuHua_Kj.class);
        for (Basic_XingZhengQuHua_Kj bean : cityList) {
            if ("开发区".equals(bean.getDMMC())) {
                cityMap.put(bean.getDMZM(), "经开");
            } else {
                cityMap.put(bean.getDMZM(), bean.getDMMC().substring(0, 2));
            }
        }
    }

    @Override
    protected void initView() {
        ll_empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        mSingleSrl = (SwipeRefreshLayout) view.findViewById(R.id.single_srl);
        mSingleLv = (ListView) view.findViewById(R.id.single_lv);

        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        mSingleSrl.setOnRefreshListener(this);

        changeRecordAdapter = new ChangeRecordAdapter(this, chagngeRecordList,cityMap);
        mSingleLv.setAdapter(changeRecordAdapter);
    }

    @Override
    public void initNet() {
        mSingleSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_CHANGEMENPAILIST, param)
                .setBeanType(ChuZuWu_ChangeMenPaiList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ChangeMenPaiList>() {
                    @Override
                    public void onSuccess(ChuZuWu_ChangeMenPaiList bean) {
                        mSingleSrl.setRefreshing(false);
                        chagngeRecordList = bean.getContent();
                        ll_empty.setVisibility(chagngeRecordList.size() == 0 ? View.VISIBLE : View.GONE);
                        changeRecordAdapter.setData(chagngeRecordList);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSingleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        setOnRightClickListener(this);

    }


    @Override
    public void setData() {
        setTitle("变更记录");
        setRightTextVisibility("变更");

    }

    @Override
    public void onRightClick() {
        ChangeCodeActivity.goActivity(this, houseId);
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, ChangeRecordListActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        initNet();
    }

    @Subscribe
    public void onEventMainThread(Object obj) {
        initNet();
    }
}
