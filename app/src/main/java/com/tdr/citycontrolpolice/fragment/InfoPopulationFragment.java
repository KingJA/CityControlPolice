package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.PopulationPersonActivity;
import com.tdr.citycontrolpolice.adapter.CzfPopulationAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKJBInfoList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:26
 * 修改备注：
 */
public class InfoPopulationFragment extends KjBaseFragment implements CzfPopulationAdapter.OnClickDetailListener , SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.single_lv)
    ListView singleLv;
    @BindView(R.id.single_srl)
    SwipeRefreshLayout singleSrl;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<ChuZuWu_LKJBInfoList.ContentEntity.PERSONNELINFOLISTEntity> personnelinfolist = new ArrayList<>();
    private String mToken;
    private CzfPopulationAdapter czfPersonAdapter;
    private Unbinder bind;

    public static InfoPopulationFragment newInstance(String houseId) {
        InfoPopulationFragment applyFragment = new InfoPopulationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mHouseId", houseId);
        applyFragment.setArguments(bundle);
        return applyFragment;

    }

    @Override
    protected void initFragmentVariables() {
        mToken = UserService.getInstance(mActivity).getToken();
        mHouseId = getArguments().getString("mHouseId");
        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);
        mParam.put("PageSize", 20);
        mParam.put("PageIndex", 0);
    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.single_lv_sl, container, false);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        czfPersonAdapter = new CzfPopulationAdapter(mActivity, personnelinfolist);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(czfPersonAdapter);
    }

    @Override
    protected void initFragmentNet() {
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_LKJBInfoList", mParam)
                .setBeanType(ChuZuWu_LKJBInfoList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKJBInfoList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKJBInfoList bean) {
                        singleSrl.setRefreshing(false);
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        Log.i("ApplyFragment", "流动人口列表: " + personnelinfolist.size());
                        czfPersonAdapter.setData(personnelinfolist);
                        llEmpty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        if (errorResult.getResultCode() != 30) {
                            singleSrl.setRefreshing(false);
                        }

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    protected void initFragmentData() {
        czfPersonAdapter.setOnClickDetailListener(this);
        singleSrl.setOnRefreshListener(this);
    }

    @Override
    protected void setFragmentData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }


    @Override
    public void onClickDetail(String identityCard) {
        PopulationPersonActivity.goActivity(mActivity, identityCard);
    }

    @Override
    public void onRefresh() {
        initFragmentNet();
    }
}
