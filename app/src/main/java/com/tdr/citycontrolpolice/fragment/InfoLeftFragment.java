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
import com.tdr.citycontrolpolice.adapter.CzfLeftAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingOutList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.event.InfoLeftFragmetnEvent;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：离开申报
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:12
 * 修改备注：
 */
public class InfoLeftFragment extends KjBaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.single_lv)
    ListView singleLv;
    @BindView(R.id.single_srl)
    SwipeRefreshLayout singleSrl;
    private HashMap<String, Object> mParam = new HashMap<>();
    private CzfLeftAdapter leftAdapter;
    private List<ChuZuWu_LKSelfReportingOutList.ContentBean.PERSONNELINFOLISTBean> leftList = new ArrayList<>();
    private Unbinder bind;

    public static InfoLeftFragment newInstance(String houseId) {
        InfoLeftFragment applyFragment = new InfoLeftFragment();
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
        mParam.put("PageSize", 500);
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
        EventBus.getDefault().register(this);
        leftAdapter = new CzfLeftAdapter(mActivity, leftList);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(leftAdapter);
    }

    @Override
    protected void initFragmentNet() {
        singleSrl.setRefreshing(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_LKSelfReportingOutList", mParam)
                .setBeanType(ChuZuWu_LKSelfReportingOutList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingOutList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingOutList bean) {
                        singleSrl.setRefreshing(false);
                        leftList = bean.getContent().getPERSONNELINFOLIST();
                        Log.i("离开申报", "列表: " + leftList.size());
                        llEmpty.setVisibility(leftList.size() == 0 ? View.VISIBLE : View.GONE);
                        leftAdapter.setData(leftList);
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
    public void onRefresh() {
        initFragmentNet();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onMessageEvent(InfoLeftFragmetnEvent messageEvent) {
        initFragmentNet();
    }
}
