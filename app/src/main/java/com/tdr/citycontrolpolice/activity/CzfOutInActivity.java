package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfOutInAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ActivecardChangeList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：出入历史记录
 * 创建人：KingJA
 * 创建时间：2016/5/10 15:35
 * 修改备注：
 */
public class CzfOutInActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String HOUSE_ID = "HOUSE_ID";
    private static final String TAG = "CzfCardActivity";
    private String houseId;
    private LinearLayout ll_empty;
    private ListView single_lv;
    private SwipeRefreshLayout single_srl;
    private List<ChuZuWu_ActivecardChangeList.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = new ArrayList<>();
    private CzfOutInAdapter czfOutInAdapter;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.single_lv, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);

    }

    @Override
    protected void initView() {
        ll_empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        single_lv = (ListView) view.findViewById(R.id.single_lv);
        single_srl = (SwipeRefreshLayout) view.findViewById(R.id.single_srl);
        single_srl.setColorSchemeResources(R.color.bg_blue_solid);
        single_srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        single_srl.setOnRefreshListener(this);
        czfOutInAdapter = new CzfOutInAdapter(this, personnelinfolist);
        single_lv.setAdapter(czfOutInAdapter);
    }

    @Override
    public void initNet() {
        single_srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("ROOMID", "");
        param.put("PageSize", 500);
        param.put("PageIndex", 0);
        ThreadPoolTask.Builder<ChuZuWu_ActivecardChangeList> builder = new ThreadPoolTask.Builder<ChuZuWu_ActivecardChangeList>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_ActivecardChangeList", param)
                .setBeanType(ChuZuWu_ActivecardChangeList.class)
                .setActivity(CzfOutInActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ActivecardChangeList>() {
                    @Override
                    public void onSuccess(ChuZuWu_ActivecardChangeList bean) {
                        single_srl.setRefreshing(false);
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        ll_empty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);
                        Log.i(TAG, "onSuccess: " + personnelinfolist.size());
                        czfOutInAdapter.setData(personnelinfolist);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        single_srl.setRefreshing(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("出入历史列表");
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, CzfOutInActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        single_srl.setRefreshing(false);
    }
}