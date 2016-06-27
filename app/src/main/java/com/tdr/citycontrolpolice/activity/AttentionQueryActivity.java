package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.AttentionQueryAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_InquireFavorites;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
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
 * 创建时间：2016/6/23 10:20
 * 修改备注：
 */
public class AttentionQueryActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener,TextWatcher, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private List<ChuZuWu_InquireFavorites.ContentBean> attentionQueryList = new ArrayList<>();
    private AttentionQueryAdapter attentionQueryAdapter;
    private EditText mEtQuery;
    private ImageView mIvClear;
    private ImageView mIvSearch;
    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private LinearLayout mLlEmpty;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_attention_query, null);
        return view;
    }

    @Override
    public void initVariables() {
    }

    @Override
    protected void initView() {
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mEtQuery = (EditText) findViewById(R.id.et_query);
        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mSingleSrl = (SwipeRefreshLayout) findViewById(R.id.single_srl);
        mSingleLv = (ListView) findViewById(R.id.single_lv);
        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        attentionQueryAdapter = new AttentionQueryAdapter(this, attentionQueryList);
        mSingleLv.setAdapter(attentionQueryAdapter);
    }

    @Override
    public void initNet() {
        queryAttention("");
    }


    @Override
    public void initData() {
        mIvClear.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
        mSingleLv.setOnItemClickListener(this);
        mSingleSrl.setOnRefreshListener(this);
        setOnRightClickListener(this);

    }

    @Override
    public void setData() {
        setTitle("出租房关注");
        setRightImageVisibility(R.drawable.bg_attention_list);

    }

    @Override
    public void onRightClick() {
        ActivityUtil.goActivity(this, RemindActivity.class);
    }


    private void queryAttention(String address) {
        mSingleSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        param.put("Address", address);
        param.put("XQCODE", "");
        param.put("PCSCODE", "");
        param.put("JWHCODE", "");
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_INQUIREFAVORITES, param)
                .setBeanType(ChuZuWu_InquireFavorites.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_InquireFavorites>() {
                    @Override
                    public void onSuccess(ChuZuWu_InquireFavorites bean) {
                        mSingleSrl.setRefreshing(false);
                        attentionQueryList = bean.getContent();
                        attentionQueryAdapter.setData(attentionQueryList);
                        mLlEmpty.setVisibility(attentionQueryList.size() > 0 ? View.GONE : View.VISIBLE);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSingleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void onRefresh() {
        mSingleSrl.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChuZuWu_InquireFavorites.ContentBean bean = (ChuZuWu_InquireFavorites.ContentBean) parent.getItemAtPosition(position);
        CzfInfoActivity.goActivity(this, bean.getHOUSEID());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                mEtQuery.setText("");
                break;
            case R.id.iv_search:
                String address = mEtQuery.getText().toString().trim();
                if (CheckUtil.checkEmpty(address, "请输入查询地址")) {
                    queryAttention(address);
                }
                break;

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mIvClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
    }
}
