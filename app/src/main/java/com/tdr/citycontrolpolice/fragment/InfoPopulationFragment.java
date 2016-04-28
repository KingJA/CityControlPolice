package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.DetailPersonActivity;
import com.tdr.citycontrolpolice.adapter.CzfPersonAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKJBInfoList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:26
 * 修改备注：
 */
public class InfoPopulationFragment extends KjBaseFragment implements CzfPersonAdapter.OnClickDetailListener {
    @Bind(R.id.lv_exist)
    ListView lv;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<ChuZuWu_LKJBInfoList.ContentEntity.PERSONNELINFOLISTEntity> personnelinfolist = new ArrayList<>();
    private String mToken;
    private CzfPersonAdapter czfPersonAdapter;

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
        rootView = inflater.inflate(R.layout.fragment_lv, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        czfPersonAdapter = new CzfPersonAdapter(mActivity, personnelinfolist);
        lv.setAdapter(czfPersonAdapter);
    }

    @Override
    protected void initFragmentNet() {
        ThreadPoolTask.Builder<ChuZuWu_LKJBInfoList> builder = new ThreadPoolTask.Builder<ChuZuWu_LKJBInfoList>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_LKJBInfoList", mParam)
                .setBeanType(ChuZuWu_LKJBInfoList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKJBInfoList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKJBInfoList bean) {
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        Log.i("ApplyFragment", "流动人口列表: " + personnelinfolist.size());
                        czfPersonAdapter.setData(personnelinfolist);
                        llEmpty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    protected void initFragmentData() {
        czfPersonAdapter.setOnClickDetailListener(this);
    }

    @Override
    protected void setFragmentData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onClickDetail(String identityCard) {
        DetailPersonActivity.goActivity(mActivity, identityCard);
    }
}
