package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.PersonInfoActivity;
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
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.KingJA_AddNextLine;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;

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
public class InfoManagerFragment extends KjBaseFragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "InfoManagerFragment";
    @Bind(R.id.lv_init)
    ListView lvInit;
    @Bind(R.id.ll_initRoom)
    LinearLayout llInitRoom;
    @Bind(R.id.srl_czf_manager)
    SwipeRefreshLayout srlCzfManager;
    @Bind(R.id.ll_init_root)
    LinearLayout llInitRoot;
    @Bind(R.id.iv_init_add)
    ImageView ivInitAdd;
    @Bind(R.id.iv_init_delete)
    ImageView ivInitDelete;
    @Bind(R.id.btn_init_submit)
    Button btnInitSubmit;
    @Bind(R.id.btn_add)
    ImageView btnAdd;
    @Bind(R.id.tv_back)
    TextView tvBack;
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
    private Param_ChuZuWu_AddRoomList param;
    private LinearLayout.LayoutParams layoutParams;
    private DialogProgress dialogProgress;
    private DialogDouble addDialogDouble;
    private List<String> currentRoomList = new ArrayList<>();

    public static InfoManagerFragment newInstance(String houseId) {
        InfoManagerFragment managerFragment = new InfoManagerFragment();
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
        addDialogDouble = new DialogDouble(getActivity(), "确定要删除该房间？", "确定", "取消");
        czfManagerAdapter = new CzfManagerAdapter(mActivity, roomList);
        lvInit.setAdapter(czfManagerAdapter);
        lvInit.setOnItemClickListener(this);
        lvInit.setOnItemLongClickListener(this);
        srlCzfManager.setOnRefreshListener(this);
        srlCzfManager.setColorSchemeResources(R.color.bg_blue_light);
        srlCzfManager.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogProgress = new DialogProgress(mActivity);

    }

    @Override
    protected void initFragmentNet() {
        dialogProgress.show();
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        kjChuZuWuInfo = bean;
                        roomList = bean.getContent().getRoomList();
                        Log.i("roomList", "InfoManagerFragment: " + roomList.size());
                        llInitRoom.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
                        btnAdd.setVisibility(roomList.size() == 0 ? View.GONE : View.VISIBLE);
                        tvBack.setVisibility(roomList.size() == 0 ? View.GONE : View.VISIBLE);
                        czfManagerAdapter.setData(roomList);
                        dialogProgress.dismiss();
                        currentRoomList.clear();
                        for (int i = 0; i < roomList.size(); i++) {
                            currentRoomList.add(String.valueOf(roomList.get(i).getROOMNO()));
                        }
                        Log.i(TAG, "currentRoomList: " + currentRoomList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        dialogProgress.dismiss();
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }


    @Override
    protected void initFragmentData() {
        addDialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                ToastUtil.showMyToast("亲爱的用户，该用户正在发开发中...");
            }

            @Override
            public void onRight() {

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        roomBean = (KjChuZuWuInfo.ContentBean.RoomListBean) parent.getItemAtPosition(position);
        roomid = roomBean.getROOMID();
        roomno = roomBean.getROOMNO();
        bundle = new Bundle();
        bundle.putString("HOUSE_ID", mHouseId);
        bundle.putString("ROOM_ID", roomid);
        bundle.putString("ROOM_NO", roomno + "");
        ActivityUtil.goActivityWithBundle(mActivity, PersonInfoActivity.class, bundle);

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


    private List<KingJA_AddNextLine> roomStringList = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private List<String> floors = new ArrayList<>();

    @OnClick(R.id.btn_add)
    void addFloat() {
        ToastUtil.showMyToast("添加");
        llInitRoot.removeAllViews();
        roomStringList.clear();
        llInitRoom.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_init_delete)
    void delete() {
        if (roomStringList.size() > 0) {
            llInitRoot.removeView(roomStringList.remove(roomStringList.size() - 1));
        }
    }

    @OnClick(R.id.iv_init_add)
    void add() {
        KingJA_AddNextLine kingJA_addNextLine = new KingJA_AddNextLine(mActivity);
        llInitRoot.addView(kingJA_addNextLine, layoutParams);
        roomStringList.add(kingJA_addNextLine);
    }

    @OnClick(R.id.tv_back)
    void back() {
        llInitRoom.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
        btnAdd.setVisibility(roomList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.btn_init_submit)
    void init() {
        rooms.clear();
        floors.clear();
        Log.i(TAG, "KingJA_AddNextLine: " + roomStringList.size());
        for (KingJA_AddNextLine line : roomStringList) {
            List<String> room = line.getRoom();
            if (room != null) {
                String floor = line.getFloor();
                if (floors.contains(floor)) {
                    ToastUtil.showMyToast("楼层重复");
                    rooms.clear();
                    return;
                } else {
                    floors.add(floor);
                }
                rooms.addAll(room);
            } else {
                return;
            }
        }
        if (!rooms.isEmpty()) {
            initRoom();
            Log.i(TAG, "rooms: " + rooms.toString());
        } else {
            ToastUtil.showMyToast("请添加房间");
        }

    }

    private void initRoom() {
//        for (int i = 0; i < rooms.size(); i++) {
//            for (int j = 0; j < currentRoomList.size(); j++) {
//                if (rooms.get(i).equals(currentRoomList.get(j))) {
//                    Log.i(TAG, rooms.get(i)+"&&"+currentRoomList.get(j));
//                    ToastUtil.showMyToast(rooms.get(i) + "号房间已经存在");
//                    return;
//                }
//            }
//        }
        dialogProgress.show();
        param = new Param_ChuZuWu_AddRoomList();
        param.setTaskID("1");
        param.setHOUSEID(mHouseId);
        param.setROOMCOUNT(rooms.size());
        List<Param_ChuZuWu_AddRoomList.ROOMLISTBean> roomlist = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Param_ChuZuWu_AddRoomList.ROOMLISTBean roomlistBean = new Param_ChuZuWu_AddRoomList.ROOMLISTBean();
            roomlistBean.setROOMID(MyUtil.getUUID());
            roomlistBean.setROOMNO(Integer.valueOf(rooms.get(i)));
            roomlist.add(roomlistBean);
        }
        param.setROOMLIST(roomlist);
        ThreadPoolTask.Builder<ChuZuWu_AddRoomList> builder = new ThreadPoolTask.Builder<ChuZuWu_AddRoomList>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0, "ChuZuWu_AddRoomList", param)
                .setBeanType(ChuZuWu_AddRoomList.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<ChuZuWu_AddRoomList>() {
                    @Override
                    public void onSuccess(ChuZuWu_AddRoomList bean) {
                        dialogProgress.dismiss();
                        if (bean.getResultCode() == 0) {
                            ToastUtil.showMyToast("添加房间成功！");
                            initFragmentNet();
                        } else {
                            ToastUtil.showMyToast("添加房间失败！");
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        dialogProgress.dismiss();
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        addDialogDouble.show();
        return true;
    }
}
