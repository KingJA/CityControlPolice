package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.ApplyEditActivity;
import com.tdr.citycontrolpolice.activity.DeviceCertActivity;
import com.tdr.citycontrolpolice.adapter.CzfDeviceAdapter;
import com.tdr.citycontrolpolice.adapter.CzfInAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.CHUZUWU_ROOMLKSELFREPORTINGLIST;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeleteReportInfo;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LkSelfReportingMacList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.InfoDeviceFragmentEvent;
import com.tdr.citycontrolpolice.event.InfoInFragmetnEvent;
import com.tdr.citycontrolpolice.event.InfoLeftFragmetnEvent;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：设备认证
 * 创建人：KingJA
 * 创建时间：2016/12/15 16:12
 * 修改备注：
 */
public class InfoDeviceFragment extends KjBaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    @Bind(R.id.single_lv)
    ListView singleLv;
    @Bind(R.id.single_srl)
    SwipeRefreshLayout singleSrl;

    private HashMap<String, Object> mParam = new HashMap<>();
    private CzfDeviceAdapter deviceAdapter;
    private  List<ChuZuWu_LkSelfReportingMacList.ContentBean> deviceList = new ArrayList<>();

    public static InfoDeviceFragment newInstance(String houseId) {
        InfoDeviceFragment applyFragment = new InfoDeviceFragment();
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
        mParam.put("PageSize", 500);
        mParam.put("PageIndex", 0);
    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.single_lv_sl, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        EventBus.getDefault().register(this);
        deviceAdapter = new CzfDeviceAdapter(mActivity, deviceList);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(deviceAdapter);
    }

    @Override
    protected void initFragmentNet() {
        singleSrl.setRefreshing(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_LkSelfReportingMacList", mParam)
                .setBeanType(ChuZuWu_LkSelfReportingMacList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LkSelfReportingMacList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LkSelfReportingMacList bean) {
                        singleSrl.setRefreshing(false);
                        deviceList = bean.getContent();
                        llEmpty.setVisibility(deviceList.size() == 0 ? View.VISIBLE : View.GONE);
                        deviceAdapter.setData(deviceList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        singleSrl.setRefreshing(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }


    @Override
    protected void initFragmentData() {
        singleSrl.setOnRefreshListener(this);
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
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        initFragmentNet();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onMessageEvent(InfoDeviceFragmentEvent messageEvent) {
        initFragmentNet();
    }



}
