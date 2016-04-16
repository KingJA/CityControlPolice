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
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.ZhuFang_DeviceLists;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/6 8:40
 * 修改备注：
 */
public class DeviceManagerActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, DeviceListAdapter.OnDeviceDeleteListener, DeviceManagerAdapter.OnExplandListener {

    private static final String TAG = "DeviceManagerActivity";
    private SwipeRefreshLayout srl;
    private ListView lv;
    private LinearLayout ll_empty;
    private String house_id;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> roomList = new ArrayList<>();
    private DeviceManagerAdapter deviceManagerAdapter;
    private List<ZhuFang_DeviceLists.ContentEntity> deviceList = new ArrayList<>();
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
        srl.setColorSchemeResources(R.color.blue_light_kj);
        srl.setProgressViewOffset(false, 0, AppUtil.dip2px(24));
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
    public void onDelete(final ZhuFang_DeviceLists.ContentEntity bean) {
        DialogDouble dialogDouble = new DialogDouble(this, "您确定要解绑该设备？", "确定", "取消");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                ToastUtil.showMyToast("解绑设备" + bean.getDEVICENAME());
            }

            @Override
            public void onRight() {

            }
        });
        ToastUtil.showMyToast("解绑" + bean.getDEVICENAME());

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
    public void onExpland(String roomid, final String roomno, final ListView lv, final ImageView iv, final int position, final boolean expland) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("RoomID", roomid);
        param.put("PageSize", 20);
        param.put("PageIndex", 0);
        ThreadPoolTask.Builder<ZhuFang_DeviceLists> builder = new ThreadPoolTask.Builder<ZhuFang_DeviceLists>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ZhuFang_DeviceLists", param)
                .setBeanType(ZhuFang_DeviceLists.class)
                .setActivity(DeviceManagerActivity.this)
                .setCallBack(new WebServiceCallBack<ZhuFang_DeviceLists>() {
                    @Override
                    public void onSuccess(ZhuFang_DeviceLists bean) {
                        setProgressDialog(false);
                        deviceList = bean.getContent();
                        Log.i(TAG, "deviceList: " + deviceList.size());
                        if (deviceList.size() == 0) {
                            ToastUtil.showMyToast(roomno + "房间没有设备");
                            return;
                        }
                        deviceListAdapter = new DeviceListAdapter(DeviceManagerActivity.this, deviceList);
                        deviceListAdapter.setOnDeviceDeleteListener(DeviceManagerActivity.this);
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
