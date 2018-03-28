package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.PersonAccreditAdapter;
import com.tdr.citycontrolpolice.adapter.PersonInoutAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DZDeviceInOutList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeviceInOutList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：进出记录
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/21 10:04
 * 修改备注：
 */
public class PersonInOutFragment extends KjBaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.single_lv)
    ListView singleLv;
    @BindView(R.id.single_srl)
    SwipeRefreshLayout singleSrl;
    private int loadIndex = 0;
    private boolean hasMore;
    private String mRoomId;
    private PersonInoutAdapter mPersonInoutAdapter;
    private int LOADSIZE = 20;
    private List<ChuZuWu_DZDeviceInOutList.ContentBean.PERSONNELINFOLISTBean> personnelInfoList = new ArrayList<>();
    private Unbinder bind;

    public static PersonInOutFragment newInstance(String houseId, String roomId) {
        PersonInOutFragment personAccreditFragment = new PersonInOutFragment();
        Bundle bundle = new Bundle();
        bundle.putString("HOUSE_ID", houseId);
        bundle.putString("ROOM_ID", roomId);
        personAccreditFragment.setArguments(bundle);
        return personAccreditFragment;

    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frag_person_inout, container, false);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {
        Bundle bundle = getArguments();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");


    }

    @Override
    protected void initFragmentView() {
        mPersonInoutAdapter = new PersonInoutAdapter(getActivity(), personnelInfoList);
        singleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        singleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        singleLv.setAdapter(mPersonInoutAdapter);
        singleSrl.setOnRefreshListener(this);
        singleLv.setOnScrollListener(onScrollListener);
    }

    @Override
    protected void initFragmentNet() {
        doNet(loadIndex);
    }

    private void doNet(final int index) {
        singleSrl.setRefreshing(true);
        Map<String, Object> mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("HOUSEID", mHouseId);
        mParam.put("ROOMID", mRoomId);
        mParam.put("PageSize", LOADSIZE);
        mParam.put("PageIndex", index);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_DZDeviceInOutList",
                        mParam)
                .setBeanType(ChuZuWu_DZDeviceInOutList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DZDeviceInOutList>() {
                    @Override
                    public void onSuccess(ChuZuWu_DZDeviceInOutList bean) {
                        singleSrl.setRefreshing(false);
                        personnelInfoList = bean.getContent().getPERSONNELINFOLIST();
                        String devicecode = bean.getContent().getDEVICECODE();
                        mPersonInoutAdapter.setDeviceCode(devicecode);
                        if (index == 0) {
                            mPersonInoutAdapter.reset();
                        }
                        hasMore = personnelInfoList.size() == LOADSIZE;
                        Log.e(TAG, "hasMore" + hasMore);
                        Log.e(TAG, "加载数据条数" + personnelInfoList.size());
                        llEmpty.setVisibility(personnelInfoList.size() > 0 ? View.GONE : View.VISIBLE);
                        mPersonInoutAdapter.addData(personnelInfoList);
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
        bind.unbind();
    }


    @Override
    public void onRefresh() {
        loadIndex = 0;
        doNet(loadIndex);
    }

    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    if (singleLv.getLastVisiblePosition() == (singleLv.getCount() - 1)) {
                        Log.e("log", "滑到底部");
                        if (singleSrl.isRefreshing()) {
                            return;
                        }
                        if (hasMore) {
                            doNet(++loadIndex);
                        } else {
                            ToastUtil.showMyToast("已经没有更多数据");
                        }
                    }
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    };
}
