package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.AddRoomActivity;
import com.tdr.citycontrolpolice.activity.PersonInfoActivity;
import com.tdr.citycontrolpolice.adapter.CzfManagerAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AddRoomList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeleteRoom;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_AddRoomList;
import com.tdr.citycontrolpolice.event.RefreshInfoManagerFragment;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.GoUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.KingJA_AddNextLine;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.tdr.citycontrolpolice.view.popupwindow.KingJA_AddNextRoom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：出租房管理Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/13 9:58
 * 修改备注：
 */
public class InfoManagerFragment extends KjBaseFragment implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemLongClickListener {
    private static final String TAG = "InfoManagerFragment";
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.ll_init_root)
    LinearLayout llInitRoot;
    @BindView(R.id.iv_init_add)
    ImageView ivInitAdd;
    @BindView(R.id.iv_init_delete)
    ImageView ivInitDelete;
    @BindView(R.id.tv_init_submit)
    TextView tvInitSubmit;
    @BindView(R.id.ll_initRoom)
    LinearLayout llInitRoom;
    @BindView(R.id.lv_init)
    ListView lvInit;
    @BindView(R.id.srl_czf_manager)
    SwipeRefreshLayout srlCzfManager;
    @BindView(R.id.btn_add)
    ImageView btnAdd;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.tv_add_roomDetail)
    TextView tvAddRoomDetail;

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
    private InputMethodManager inputManager;
    private boolean hasInitRoom;
    private KingJA_AddNextLine kingJA_addNextLine;
    private KingJA_AddNextRoom kingJA_addNextroom;
    private Unbinder bind;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_czf_manager, container, false);
        bind = ButterKnife.bind(this, rootView);
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
        srlCzfManager.setColorSchemeResources(R.color.bg_blue_solid);
        srlCzfManager.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        dialogProgress = new DialogProgress(mActivity);

    }

    @Override
    protected void initFragmentNet() {
        srlCzfManager.setRefreshing(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0,
                "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(getActivity())
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        srlCzfManager.setRefreshing(false);
                        kjChuZuWuInfo = bean;
                        roomList = bean.getContent().getRoomList();
                        Log.i("roomList", "InfoManagerFragment: " + roomList.size());
                        llInitRoom.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
                        btnAdd.setVisibility(roomList.size() == 0 ? View.GONE : View.VISIBLE);
                        hasInitRoom = (roomList.size() == 0 ? false : true);
                        tvTip.setText(roomList.size() == 0 ? "请输入楼层号和房间数进行初始化" : "请输入房间号进行批量添加");
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
                        if (errorResult.getResultCode() != 30) {
                            srlCzfManager.setRefreshing(false);
                        }
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
        bind.unbind();
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
        GoUtil.goActivityWithBundle(mActivity, PersonInfoActivity.class, bundle);

    }


    @Override
    public void onRefresh() {
        initFragmentNet();
    }


    private List<KingJA_AddNextLine> initRoomsList = new ArrayList<>();
    private List<KingJA_AddNextRoom> addRoomsList = new ArrayList<>();
    private List<String> rooms = new ArrayList<>();
    private List<String> floors = new ArrayList<>();

    @OnClick(R.id.btn_add)
    void addFloat() {
        llInitRoot.removeAllViews();
        initRoomsList.clear();
        addRoomsList.clear();

        llInitRoom.setVisibility(View.VISIBLE);
        btnAdd.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_init_delete)
    void delete() {
        if (hasInitRoom) {
            if (addRoomsList.size() > 0) {
                llInitRoot.removeView(addRoomsList.remove(addRoomsList.size() - 1));
            }
        } else {
            if (initRoomsList.size() > 0) {
                llInitRoot.removeView(initRoomsList.remove(initRoomsList.size() - 1));
            }
        }

    }

    @OnClick(R.id.iv_init_add)
    void add() {
        if (hasInitRoom) {
            kingJA_addNextroom = new KingJA_AddNextRoom(mActivity);
            llInitRoot.addView(kingJA_addNextroom, layoutParams);
            addRoomsList.add(kingJA_addNextroom);
        } else {
            kingJA_addNextLine = new KingJA_AddNextLine(mActivity);
            llInitRoot.addView(kingJA_addNextLine, layoutParams);
            initRoomsList.add(kingJA_addNextLine);
        }

    }

    @OnClick(R.id.tv_back)
    void back() {
//        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager
// .HIDE_NOT_ALWAYS);
//        llInitRoom.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
//        btnAdd.setVisibility(roomList.size() == 0 ? View.GONE : View.VISIBLE);
        llInitRoom.setVisibility(View.GONE);
        btnAdd.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tv_add_roomDetail)
    void addRoomDetail() {
        AddRoomActivity.goActivity(getActivity(),mHouseId);
    }

    @OnClick(R.id.tv_init_submit)
    void init() {
        if (hasInitRoom) {
            //添加房间
            doAddRoom();
        } else {
            //初始化房间
            doInitRoom();
        }
    }

    /**
     * 初始化房间
     */
    private void doInitRoom() {
        rooms.clear();
        floors.clear();
        Log.i(TAG, "KingJA_AddNextLine: " + initRoomsList.size());
        for (KingJA_AddNextLine line : initRoomsList) {
            List<String> room = line.getInitRooms();
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

    /**
     * 添加房间
     */
    private void doAddRoom() {
        rooms.clear();
        floors.clear();
        Log.i(TAG, "KingJA_AddNextLine: " + initRoomsList.size());
        for (KingJA_AddNextRoom line : addRoomsList) {
            String room = line.getAddRoom();
            if (TextUtils.isEmpty(room)) {
                return;
            }
            if (rooms.contains(room)) {
                ToastUtil.showMyToast("房间重复");
                return;
            } else {
                rooms.add(room);
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
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(mActivity).getToken(), 0,
                "ChuZuWu_AddRoomList", param)
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final KjChuZuWuInfo.ContentBean.RoomListBean roomBean = (KjChuZuWuInfo.ContentBean.RoomListBean) parent
                .getItemAtPosition(position);
        addDialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                onDeleteRoom(roomBean.getROOMID(), position);
            }

            @Override
            public void onRight() {

            }
        });
        addDialogDouble.show();
        return true;
    }

    private void onDeleteRoom(String roomid, final int position) {
        srlCzfManager.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("ROOMID", roomid);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_DeleteRoom", param)
                .setBeanType(ChuZuWu_DeleteRoom.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DeleteRoom>() {
                    @Override
                    public void onSuccess(ChuZuWu_DeleteRoom bean) {
                        srlCzfManager.setRefreshing(false);
                        czfManagerAdapter.deleteItem(position);
                        ToastUtil.showMyToast("房间删除成功");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        srlCzfManager.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreh(RefreshInfoManagerFragment bean) {
        initFragmentNet();
    }

}
