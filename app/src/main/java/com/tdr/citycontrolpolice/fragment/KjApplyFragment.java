package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfApplyAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.KingJA_SwtichButton_Kj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:12
 * 修改备注：
 */
public class KjApplyFragment extends KjBaseFragment implements AdapterView.OnItemClickListener, KingJA_SwtichButton_Kj.OnSwitchListener {
    @Bind(R.id.lv_exist)
    ListView lv_exist;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    @Bind(R.id.kj_switchbutton)
    KingJA_SwtichButton_Kj kjSwitchbutton;
    private HashMap<String, Object> mParam = new HashMap<>();
    private boolean isInList = true;
    private CzfApplyAdapter applyAdapter;
    private List<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> applyList = new ArrayList<>();
    private Map<Boolean, CzfApplyAdapter> adapterMap = new HashMap<>();

    public static KjApplyFragment newInstance(String houseId) {
        KjApplyFragment applyFragment = new KjApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mHouseId", houseId);
        applyFragment.setArguments(bundle);
        return applyFragment;

    }

    @Override
    protected void initFragmentVariables() {
        mHouseId = getArguments().getString("mHouseId");
        mParam.put("TaskID", "1");
        mParam.put("HOUSEID", mHouseId);
        mParam.put("ROOMID", "");
        mParam.put("PageSize", 100);
        mParam.put("PageIndex", 0);
    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_lv_apply, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        applyAdapter = new CzfApplyAdapter(mActivity, applyList);
        lv_exist.setAdapter(applyAdapter);
        lv_exist.setOnItemClickListener(this);
    }

    @Override
    protected void initFragmentNet() {
        getAppleList(isInList);
    }


    private void getAppleList(final boolean isInList) {
        String methodName = isInList ? "ChuZuWu_LKSelfReportingList" : "ChuZuWu_LKSelfReportingOutList";
        ThreadPoolTask.Builder<ChuZuWu_LKSelfReportingList> builder = new ThreadPoolTask.Builder<ChuZuWu_LKSelfReportingList>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, methodName, mParam)
                .setBeanType(ChuZuWu_LKSelfReportingList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingList bean) {
                        applyList = bean.getContent().getPERSONNELINFOLIST();
                        Log.i("ApplyFragment", "applyList: " + applyList.size());
//                        llEmpty.setVisibility(applyList.size() == 0 ? View.VISIBLE : View.GONE);
                        applyAdapter = new CzfApplyAdapter(mActivity, applyList);
                        lv_exist.setAdapter(applyAdapter);
                        adapterMap.put(Boolean.valueOf(isInList), applyAdapter);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    protected void initFragmentData() {
        kjSwitchbutton.setOnSwitchListener(this);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onSwitch(boolean isLeft) {
        this.isInList = isLeft;
        if (adapterMap.get(isInList) != null) {
            lv_exist.setAdapter(adapterMap.get(isInList));
        } else {
            getAppleList(isInList);
        }


    }
}
