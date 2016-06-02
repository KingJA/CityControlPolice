package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.DeviceListAdapter;
import com.tdr.citycontrolpolice.adapter.DeviceManagerAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeviceLists;
import com.tdr.citycontrolpolice.entity.Common_ReplaceDevice;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.VerifyCode;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：设备管理页面
 * 创建人：KingJA
 * 创建时间：2016/4/6 8:40
 * 修改备注：
 */
public class DeviceManagerActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, DeviceListAdapter.OnDeviceChangeListener, DeviceManagerAdapter.OnExplandListener {

    private static final String TAG = "DeviceManagerActivity";
    private SwipeRefreshLayout srl;
    private ListView lv;
    private LinearLayout ll_empty;
    private String house_id;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> roomList = new ArrayList<>();
    private DeviceManagerAdapter deviceManagerAdapter;
    private List<ChuZuWu_DeviceLists.ContentBean> deviceList = new ArrayList<>();
    private DeviceListAdapter deviceListAdapter;
    private ChuZuWu_DeviceLists.ContentBean bean;
    private String roomId;
    private String roomNo;
    private int position;
    private int outPosition;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_lv, null);
        return view;
    }

    @Override
    public void initVariables() {
        house_id = getIntent().getStringExtra("HOUSE_ID");
        mParam.put("TaskID", "1");
        mParam.put("HouseID", house_id);
    }

    @Override
    protected void initView() {
        srl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        lv = (ListView) view.findViewById(R.id.lv_exist);
        ll_empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        deviceManagerAdapter = new DeviceManagerAdapter(this, roomList);
        deviceManagerAdapter.setOnExplandListener(this);
        lv.setAdapter(deviceManagerAdapter);
        srl.setOnRefreshListener(this);
        srl.setColorSchemeResources(R.color.bg_blue_solid);
        srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    public void initNet() {
        srl.setRefreshing(true);
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(DeviceManagerActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        roomList = bean.getContent().getRoomList();
                        ll_empty.setVisibility(roomList.size() == 0 ? View.VISIBLE : View.GONE);
                        deviceManagerAdapter.setData(roomList);
                        srl.setRefreshing(false);
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
        setTitle("防控设备管理");
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
    }

    @Override
    public void onChange(final ChuZuWu_DeviceLists.ContentBean bean, final String roomId, final String roomNo, final int position, final int outPosition) {
        DialogDouble dialogDouble = new DialogDouble(this, "您确定要更换该设备？", "确定", "取消");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                changeDevice(bean, roomId, roomNo, position, outPosition);
            }

            @Override
            public void onRight() {

            }
        });

    }

    /**
     * 更换设备
     * @param bean
     * @param roomId
     */
    private void changeDevice(ChuZuWu_DeviceLists.ContentBean bean, String roomId, String roomNo, int position, int outPosition) {
        this.bean = bean;
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.position = position;
        this.outPosition = outPosition;
//        setProgressDialog(true);
//        uploadDevice(bean, roomId);
//TODO
        Intent intent = new Intent(DeviceManagerActivity.this, zbar.CaptureActivity.class);
        startActivityForResult(intent, 1);

    }

    private void uploadDevice(ChuZuWu_DeviceLists.ContentBean bean, final String deviceType, final String deviceCode, final String roomId) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("DEVICEID", bean.getDEVICEID());
        param.put("DEVICETYPE", deviceType);
        param.put("NEWDEVICECODE", deviceCode);
        param.put("OLDDEVICECODE", bean.getDEVICECODE());
        param.put("OTHERTYPE", "2");
        param.put("OTHERID", roomId);
        ThreadPoolTask.Builder<Common_ReplaceDevice> builder = new ThreadPoolTask.Builder<Common_ReplaceDevice>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_ReplaceDevice", param)
                .setBeanType(Common_ReplaceDevice.class)
                .setActivity(DeviceManagerActivity.this)
                .setCallBack(new WebServiceCallBack<Common_ReplaceDevice>() {
                    @Override
                    public void onSuccess(Common_ReplaceDevice bean) {
                        deviceManagerAdapter.getAdapter(outPosition).changeDevice(position, deviceType, deviceCode);
                        ToastUtil.showMyToast("设备更换成功");
                        setProgressDialog(false);
                    }
                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);

                    }
                }).build();
        PoolManager.getInstance().execute(task);

    }

    /**
     * 展开加载网络
     *
     * @param roomid
     * @param roomno
     * @param lv
     * @param iv
     * @param position
     * @param expland
     */
    @Override
    public void onExpland(final String roomid, final String roomno, final ListView lv, final ImageView iv, final int position, final boolean expland) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("RoomID", roomid);
        param.put("PageSize", 20);
        param.put("PageIndex", 0);
        ThreadPoolTask.Builder<ChuZuWu_DeviceLists> builder = new ThreadPoolTask.Builder<ChuZuWu_DeviceLists>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_DeviceLists", param)
                .setBeanType(ChuZuWu_DeviceLists.class)
                .setActivity(DeviceManagerActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DeviceLists>() {
                    @Override
                    public void onSuccess(ChuZuWu_DeviceLists bean) {
                        setProgressDialog(false);
                        deviceList = bean.getContent();
                        Log.i(TAG, "deviceList: " + deviceList.size());
                        if (deviceList.size() == 0) {
                            ToastUtil.showMyToast(roomno + "房间没有设备");
                            return;
                        }
                        deviceListAdapter = new DeviceListAdapter(position, roomid, roomno, DeviceManagerActivity.this, DeviceManagerActivity.this, deviceList);
                        deviceListAdapter.setOnDeviceChangeListener(DeviceManagerActivity.this);
                        deviceManagerAdapter.saveAdapter(position, deviceListAdapter);
                        lv.setAdapter(deviceListAdapter);
                        deviceManagerAdapter.setVisibility(!expland, position);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1:
//                setProgressDialog(false);
                decodeDevice(data);
                break;
            default:
                break;
        }
    }

    private long deviceNO;
    private long deviceType;

    private void decodeDevice(Intent data) {
        String result = data.getExtras().getString("result");
        Log.i(TAG, "onActivityResult: " + result);
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("AB".equals(type)) {
            result = result.substring(2);
            result = VerifyCode.checkDeviceCode(result);
            if (TextUtils.isEmpty(result)) {
                ToastUtil.showMyToast("可疑数据！");
                return;
            }
            deviceType = Long.valueOf(result.substring(0, 4), 16);
            deviceNO = Long.valueOf(result.substring(4), 16);
            if (deviceType == 1040) {
                ToastUtil.showMyToast("未识别设备类型");
                Log.i(TAG, "类型1040，不是设备类型:,");
                return;
            }
            Log.i(TAG, deviceType + "是设备类型:,");
            Log.i(TAG, "设备类型: " + deviceType);
            Log.i(TAG, "设备编号: " + deviceNO);
            DialogDouble dialogDouble = new DialogDouble(this, "是否将" + deviceNO + "设备绑定到" + roomNo + "房间", "确定", "取消");
            dialogDouble.show();
            dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
                @Override
                public void onLeft() {
                    uploadDevice(bean, String.valueOf(deviceType), String.valueOf(deviceNO), roomId);
                }

                @Override
                public void onRight() {

                }
            });
        } else {
            ToastUtil.showMyToast("不是要求的二维码对象");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: ");
    }
}
