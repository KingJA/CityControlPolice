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
import com.tdr.citycontrolpolice.adapter.CzfInAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeleteReportInfo;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LKSelfReportingList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
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
 * 类描述：申报入住
 * 创建人：KingJA
 * 创建时间：2016/3/24 16:12
 * 修改备注：
 */
public class InfoInFragment extends KjBaseFragment implements SwipeRefreshLayout.OnRefreshListener ,CzfInAdapter.OnItemDeleteListener{

    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    @Bind(R.id.single_lv)
    ListView singleLv;
    @Bind(R.id.single_srl)
    SwipeRefreshLayout singleSrl;
    private HashMap<String, Object> mParam = new HashMap<>();
    private CzfInAdapter inAdapter;
    private List<ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean> inLef = new ArrayList<>();

    public static InfoInFragment newInstance(String houseId) {
        InfoInFragment applyFragment = new InfoInFragment();
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
        rootView = inflater.inflate(R.layout.single_lv, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        EventBus.getDefault().register(this);
        inAdapter = new CzfInAdapter(mActivity, inLef);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(inAdapter);
    }

    @Override
    protected void initFragmentNet() {
        singleSrl.setRefreshing(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_LKSelfReportingList", mParam)
                .setBeanType(ChuZuWu_LKSelfReportingList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_LKSelfReportingList>() {
                    @Override
                    public void onSuccess(ChuZuWu_LKSelfReportingList bean) {
                        singleSrl.setRefreshing(false);
                        inLef = bean.getContent().getPERSONNELINFOLIST();
                        Log.i("入住申报", "列表: " + inLef.size());
                        llEmpty.setVisibility(inLef.size() == 0 ? View.VISIBLE : View.GONE);
                        inAdapter.setData(inLef);
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
        inAdapter.setonItemDeleteListener(this);
        singleLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean bean = (ChuZuWu_LKSelfReportingList.ContentBean.PERSONNELINFOLISTBean) parent.getItemAtPosition(position);
                ApplyEditActivity.goActivity(getActivity(),bean.getLISTID(),mHouseId,bean.getNAME(),bean.getPHONENUM(),bean.getIDENTITYCARD());
            }
        });
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
    public void onDelete(final int position, final String LISTID) {

        DialogDouble dialogDouble = new DialogDouble(getActivity(), "确定要删除这条申报信息?", "取消", "确定");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {

            }

            @Override
            public void onRight() {
                loadDelete(position, LISTID);
            }
        });

    }

    private void loadDelete(final int position, String LISTID) {
        Map<String, String> mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("LISTID", LISTID);
        mParam.put("OUTREPORTERROLE", "1");
        mParam.put("OUTOPERATOR", UserService.getInstance(mActivity).getUid());
        mParam.put("OUTOPERATORPHONE", "");

        singleSrl.setRefreshing(true);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_DeleteReportInfo", mParam)
                .setBeanType(ChuZuWu_DeleteReportInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DeleteReportInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_DeleteReportInfo bean) {
                        EventBus.getDefault().post(new InfoLeftFragmetnEvent());
                        inAdapter.deleteItem(position);
                        singleSrl.setRefreshing(false);
                        ToastUtil.showMyToast("删除成功");

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        singleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN )
    public void onMessageEvent(InfoInFragmetnEvent messageEvent) {
        initFragmentNet();
    }
}
