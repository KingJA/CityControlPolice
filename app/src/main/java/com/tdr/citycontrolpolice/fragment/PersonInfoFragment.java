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
import com.tdr.citycontrolpolice.activity.HomeActivity;
import com.tdr.citycontrolpolice.activity.KjLoginActivity;
import com.tdr.citycontrolpolice.activity.PersonInfoActivity;
import com.tdr.citycontrolpolice.adapter.PersonInfoAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.User_LoginByPolice;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/21 10:04
 * 修改备注：
 */
public class PersonInfoFragment extends KjBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    @Bind(R.id.single_lv)
    ListView singleLv;
    @Bind(R.id.single_srl)
    SwipeRefreshLayout singleSrl;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<ChuZuWu_ComprehensiveInfo.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = new ArrayList<>();
    private String mRoomId;
    private PersonInfoAdapter personInfoAdapter;

    public static PersonInfoFragment newInstance(String houseId,String roomId) {
        PersonInfoFragment personInfoFragment = new PersonInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("HOUSE_ID", houseId);
        bundle.putString("ROOM_ID", roomId);
        personInfoFragment.setArguments(bundle);
        return personInfoFragment;

    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.single_lv, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {
        Bundle bundle = getArguments();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");

        mParam.put("TaskID", "1");
        mParam.put("HOUSEID", mHouseId);
        mParam.put("ROOMID", mRoomId);
        mParam.put("PageSize", 200);
        mParam.put("PageIndex", 0);
    }

    @Override
    protected void initFragmentView() {
        personInfoAdapter = new PersonInfoAdapter(getActivity(), personnelinfolist);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(personInfoAdapter);
    }

    @Override
    protected void initFragmentNet() {
        singleSrl.setRefreshing(true);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0,"ChuZuWu_ComprehensiveInfo" , mParam)
                .setBeanType(ChuZuWu_ComprehensiveInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ComprehensiveInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_ComprehensiveInfo bean) {
                        singleSrl.setRefreshing(false);
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        personInfoAdapter.setData(personnelinfolist);
                        llEmpty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        singleSrl.setRefreshing(false);
                    }
                }).build().execute();
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
        singleSrl.setRefreshing(false);
    }
}
