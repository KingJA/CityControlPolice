package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.DeviceBindingAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SetRoomStationNo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.Equipment;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.zbar.lib.CaptureActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/6 10:27
 * 修改备注：
 */
public class DeviceBindingListActivity extends BackTitleActivity implements DeviceBindingAdapter.OnBindingStationListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "BindingListActivity";
    private ListView lv;
    private SwipeRefreshLayout srl;
    private String mToken;
    private Map<String, Object> mParam;
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> roomList = new ArrayList<>();
    private DeviceBindingAdapter deviceBindingAdapter;
    private LinearLayout ll_empty;
    private String mHouseId;
    private String currentRoomId;
    private int currentRoomNo;
    private int stationNO;
    private DialogDouble dialogDouble;
    private int deviceNO;
    private int deviceType;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_binding_list, null);
        return view;
    }

    @Override
    public void initVariables() {
        mToken = UserService.getInstance(this).getToken();
        mHouseId = getIntent().getStringExtra("HOUSE_ID");
        mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);
    }

    @Override
    protected void initView() {

        deviceBindingAdapter = new DeviceBindingAdapter(this, roomList);
        deviceBindingAdapter.setOnBindingStationListener(this);
        lv = (ListView) view.findViewById(R.id.lv_exist);
        lv.setAdapter(deviceBindingAdapter);
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        srl.setColorSchemeResources(R.color.blue_light_kj);
        srl.setProgressViewOffset(false, 0, AppUtil.dip2px(24));
        srl.setOnRefreshListener(this);
        ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
    }

    @Override
    public void initNet() {
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(DeviceBindingListActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        roomList = bean.getContent().getRoomList();
                        Log.i(TAG, "roomList: " + roomList.size());
                        deviceBindingAdapter.setData(roomList);
                        ll_empty.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("防控设备绑定");
    }

    @Override
    public void onBindingStation(String roomId, int roomNo) {
        currentRoomId = roomId;
        currentRoomNo = roomNo;
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onBindingDevice(String roomId, int roomNo) {
        currentRoomId = roomId;
        currentRoomNo = roomNo;
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 0:
                decodeStation(data);
                break;
            case 1:
                decodeDevice(data);
                break;
            default:
                break;
        }
    }

    private void decodeDevice(Intent data) {
        String result = data.getExtras().getString("result");
        Log.i(TAG, "onActivityResult: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("AB".equals(type)) {
            result = result.substring(2);
            result = Equipment.decode(result);
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showMyToast("可疑数据！");
                return;
            }
            deviceType = Integer.parseInt(result.substring(0, 4), 16);
            deviceNO = Integer.parseInt(result.substring(4), 16);
            Log.i(TAG, "设备类型: " + deviceType);
            Log.i(TAG, "设备编号: " + deviceNO);

            goBindDevice(deviceType, deviceNO, mHouseId, currentRoomId, currentRoomNo);
        }
    }

    /**
     * 跳转设备绑定页面
     *
     * @param deviceType
     * @param deviceNO
     * @param houseId
     * @param roomId
     * @param roomNo
     */
    private void goBindDevice(int deviceType, int deviceNO, String houseId, String roomId, int roomNo) {
        Intent intent = new Intent(this, BindingDeviceActivity.class);
        intent.putExtra("DEVICE_TYPE", deviceType);
        intent.putExtra("DEVICE_NO", deviceNO);
        intent.putExtra("HOUSE_ID", houseId);
        intent.putExtra("ROOM_ID", roomId);
        intent.putExtra("ROOM_NO", roomNo);

        Log.i(TAG, "deviceType: " + deviceType);
        Log.i(TAG, "deviceNO: " + deviceNO);
        Log.i(TAG, "houseId: " + houseId);
        Log.i(TAG, "roomId: " + roomId);
        Log.i(TAG, "roomNo: " + roomNo);
        startActivity(intent);
    }

    private void decodeStation(Intent data) {
        String result = data.getExtras().getString("result");
        Log.i(TAG, "onActivityResult: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("AB".equals(type)) {
            result = result.substring(2);
            result = Equipment.decode(result);
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showMyToast("可疑数据！");
                return;
            }
            stationNO = Integer.parseInt(result.substring(4), 16);

            Log.i(TAG, "解码: " + result);
            Log.i(TAG, "基站编号: " + stationNO);
            dialogDouble = new DialogDouble(this, "是否将" + currentRoomNo + "房间绑定到" + stationNO + "基站", "确定", "取消");
            dialogDouble.show();
            dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
                @Override
                public void onLeft() {
                    ToastUtil.showMyToast("绑定基站");
                    BindingStation(mHouseId, currentRoomId, stationNO + "");
                }

                @Override
                public void onRight() {

                }
            });
        }
    }

    /**
     * 绑定到基站
     *
     * @param houseId
     * @param roomId
     * @param stationNO
     */
    private void BindingStation(String houseId, String roomId, String stationNO) {
        Map<String, Object> paramBinding = new HashMap<>();
        paramBinding.put("TaskID", "1");
        paramBinding.put("HOUSEID", houseId);
        paramBinding.put("ROOMID", roomId);
        paramBinding.put("STATIONNO", stationNO);
        ThreadPoolTask.Builder<ChuZuWu_SetRoomStationNo> builder = new ThreadPoolTask.Builder<ChuZuWu_SetRoomStationNo>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_SetRoomStationNo", paramBinding)
                .setBeanType(ChuZuWu_SetRoomStationNo.class)
                .setActivity(DeviceBindingListActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_SetRoomStationNo>() {
                    @Override
                    public void onSuccess(ChuZuWu_SetRoomStationNo bean) {
                        if (bean.getResultCode() == 0) {
                            initNet();
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
    }
}

