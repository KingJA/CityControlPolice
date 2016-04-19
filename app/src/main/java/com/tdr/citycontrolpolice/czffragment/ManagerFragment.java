package com.tdr.citycontrolpolice.czffragment;

import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.DetailCzfInfoActivity;
import com.tdr.citycontrolpolice.adapter.CzfManagerAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AddRoomList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_AddRoomList;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：出租房管理Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/13 9:58
 * 修改备注：
 */
public class ManagerFragment extends KjBaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "ManagerFragment";
    @Bind(R.id.lv_init)
    ListView lvInit;
    @Bind(R.id.et_init_floor)
    EditText etInitFloor;
    @Bind(R.id.et_init_room)
    EditText etInitRoom;
    @Bind(R.id.btn_init_house)
    Button btnInitHouse;
    @Bind(R.id.ll_initRoom)
    LinearLayout llInitRoom;
    @Bind(R.id.srl_czf_manager)
    SwipeRefreshLayout srlCzfManager;
    @Bind(R.id.ll_empty)
    LinearLayout llEmpty;
    private ChuZuWuInfo chuZuWuInfo;
    private View rootView;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> roomList = new ArrayList<>();
    private CzfManagerAdapter czfManagerAdapter;
    private KjChuZuWuInfo kjChuZuWuInfo = new KjChuZuWuInfo();
    private String roomid;
    private KjChuZuWuInfo.ContentBean.RoomListBean roomBean;
    private Bundle bundle;
    private int roomno;
    private String floor;
    private String room;
    private Param_ChuZuWu_AddRoomList param;

    public static ManagerFragment newInstance(String houseId) {
        ManagerFragment managerFragment = new ManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mHouseId", houseId);
        managerFragment.setArguments(bundle);
        return managerFragment;

    }


    @Override
    protected void initFragmentVariables() {
        mHouseId = getArguments().getString("mHouseId");
        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);
    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_czf_manager, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentView() {
        czfManagerAdapter = new CzfManagerAdapter(mActivity, roomList);
        lvInit.setAdapter(czfManagerAdapter);
        lvInit.setOnItemClickListener(this);
        srlCzfManager.setOnRefreshListener(this);
        srlCzfManager.setColorSchemeResources(R.color.blue_light_kj);
        srlCzfManager.setProgressViewOffset(false, 0, AppUtil.dip2px(24));

    }

    @Override
    protected void initFragmentNet() {
        srlCzfManager.setRefreshing(true);
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        kjChuZuWuInfo = bean;
                        roomList = bean.getContent().getRoomList();
                        Log.i("roomList", "ManagerFragment: " + roomList.size());
                        llInitRoom.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
                        czfManagerAdapter.setData(roomList);
                        srlCzfManager.setRefreshing(false);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }


    @Override
    protected void initFragmentData() {
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
        roomBean = (KjChuZuWuInfo.ContentBean.RoomListBean) parent.getItemAtPosition(position);
        roomid = roomBean.getROOMID();
        roomno = roomBean.getROOMNO();
        bundle = new Bundle();
        bundle.putString("HOUSE_ID", mHouseId);
        bundle.putString("ROOM_ID", roomid);
        bundle.putString("ROOM_NO", roomno + "");
        ActivityUtil.goActivityWithBundle(mActivity, DetailCzfInfoActivity.class, bundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onRefresh() {
        srlCzfManager.setRefreshing(false);
    }

    @OnClick(R.id.btn_init_house)
    void init() {
        floor = etInitFloor.getText().toString().trim();
        room = etInitRoom.getText().toString().trim();
        if (CheckUtil.checkEmpty(floor, "请输入楼层数") && CheckUtil.checkEmpty(room, "请输入房间数数")) {
            param = new Param_ChuZuWu_AddRoomList();
            param.setTaskID("1");
            param.setHOUSEID(mHouseId);
            param.setROOMCOUNT(Integer.valueOf(floor) * Integer.valueOf(room));
            List<Param_ChuZuWu_AddRoomList.ROOMLISTBean> roomlist = new ArrayList<>();

            for (int i = 1; i <= Integer.valueOf(floor); i++) {
                for (int j = 1; j <= Integer.valueOf(room); j++) {
                    int roomNO = Integer.valueOf(i + String.format("%02d", j));
                    Log.i(TAG, i + "0" + j);
                    Param_ChuZuWu_AddRoomList.ROOMLISTBean roomlistBean = new Param_ChuZuWu_AddRoomList.ROOMLISTBean();
                    roomlistBean.setROOMID(MyUtil.getUUID());
                    roomlistBean.setROOMNO(roomNO);
                    roomlist.add(roomlistBean);
                }
            }
            param.setROOMLIST(roomlist);
        }
        ThreadPoolTask.Builder<ChuZuWu_AddRoomList> builder = new ThreadPoolTask.Builder<ChuZuWu_AddRoomList>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_AddRoomList", param)
                .setBeanType(ChuZuWu_AddRoomList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_AddRoomList>() {
                    @Override
                    public void onSuccess(ChuZuWu_AddRoomList bean) {
                        if (bean.getResultCode() == 0) {
                            ToastUtil.showMyToast("初始化房间成功！");
                            initFragmentNet();
                        } else {
                            ToastUtil.showMyToast("初始化房间失败！");
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);

    }
}
