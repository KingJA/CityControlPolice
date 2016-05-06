package com.tdr.citycontrolpolice.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.DeviceListAdapter;
import com.tdr.citycontrolpolice.adapter.DeviceManagerAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DeviceLists;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.ZhuFang_DeviceLists;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
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
        srl.setColorSchemeResources(R.color.bg_blue_light);
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
    public void onChange(final ChuZuWu_DeviceLists.ContentBean bean, final String roomId) {


        DialogDouble dialogDouble = new DialogDouble(this, "您确定要更换该设备？", "确定", "取消");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                ToastUtil.showMyToast("更换设备" + bean.getDEVICENAME());
//                changeDevice(bean,roomId);
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
    private void changeDevice(ZhuFang_DeviceLists.ContentEntity bean, String roomId) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("DEVICEID", MyUtil.getUUID());
        param.put("DEVICETYPE", 20);
        param.put("NEWDEVICECODE", 0);
        param.put("OLDDEVICECODE", bean.getDEVICECODE());
        param.put("OTHERTYPE", "2");
        param.put("OTHERID", roomId);
        ThreadPoolTask.Builder<ZhuFang_DeviceLists> builder = new ThreadPoolTask.Builder<ZhuFang_DeviceLists>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ZhuFang_DeviceLists", param)
                .setBeanType(ZhuFang_DeviceLists.class)
                .setActivity(DeviceManagerActivity.this)
                .setCallBack(new WebServiceCallBack<ZhuFang_DeviceLists>() {
                    @Override
                    public void onSuccess(ZhuFang_DeviceLists bean) {
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
                        deviceListAdapter = new DeviceListAdapter(roomid, DeviceManagerActivity.this, deviceList);
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

}
