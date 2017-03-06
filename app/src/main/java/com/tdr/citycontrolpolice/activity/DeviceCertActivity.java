package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfLeftAdapter;
import com.tdr.citycontrolpolice.adapter.DeviceCertAdapter;
import com.tdr.citycontrolpolice.entity.CHUZUWU_ROOMLKSELFREPORTINGLIST;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LkSelfReportingMacAuth;
import com.tdr.citycontrolpolice.entity.ChuZuWu_LkSelfReportingMacList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.InfoDeviceFragmentEvent;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.event.InfoInFragmetnEvent;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：设备认证
 * Create Time：2016/12/16 14:12
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceCertActivity extends  BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private LinearLayout mLlEmpty;
    private String houseId;
    private List<CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean> personList=new ArrayList<>();
    private DeviceCertAdapter deviceCertAdapter;
    private ChuZuWu_LkSelfReportingMacList.ContentBean macBean;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.single_lv_sl, null);
        return view;
    }

    @Override
    public void initVariables() {
        EventBus.getDefault().register(this);
        houseId = getIntent().getStringExtra("houseId");
        macBean = (ChuZuWu_LkSelfReportingMacList.ContentBean) getIntent().getSerializableExtra("macBean");
    }

    @Override
    protected void initView() {
        mSingleSrl = (SwipeRefreshLayout) findViewById(R.id.single_srl);
        mSingleLv = (ListView) findViewById(R.id.single_lv);
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        deviceCertAdapter = new DeviceCertAdapter(this, personList);
        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        mSingleLv.setAdapter(deviceCertAdapter);
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HouseID", houseId);
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        setProgressDialog(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "CHUZUWU_ROOMLKSELFREPORTINGLIST", param)
                .setBeanType(CHUZUWU_ROOMLKSELFREPORTINGLIST.class)
                .setActivity(DeviceCertActivity.this)
                .setCallBack(new WebServiceCallBack<CHUZUWU_ROOMLKSELFREPORTINGLIST>() {
                    @Override
                    public void onSuccess(CHUZUWU_ROOMLKSELFREPORTINGLIST bean) {
                        setProgressDialog(false);
                        personList = bean.getContent();
                        mLlEmpty.setVisibility(personList.size() == 0 ? View.VISIBLE : View.GONE);
                        deviceCertAdapter.setData(personList);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        mSingleSrl.setOnRefreshListener(this);
    }

    @Override
    public void setData() {
        setTitle("设备认证");

    }

    public static void goActivity(Context context, String houseId, ChuZuWu_LkSelfReportingMacList.ContentBean macBean) {
        Intent intent = new Intent(context, DeviceCertActivity.class);
        intent.putExtra("houseId", houseId);
        intent.putExtra("macBean", macBean);
        context.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN )
    public void macBind(CHUZUWU_ROOMLKSELFREPORTINGLIST.ContentBean.PERSONNELINFOLISTBean bean) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", macBean.getHOUSEID());
        param.put("MAC", macBean.getMAC());
        param.put("ROOMID", bean.getROOMID());
        param.put("IDENTITYCARD",bean.getIDENTITYCARD());
        param.put("NAME", bean.getNAME());
        param.put("STATUS", 2);
        param.put("PHONE", bean.getPHONENUM());
        setProgressDialog(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_LkSelfReportingMacAuth", param)
                .setBeanType(ChuZuWu_LkSelfReportingMacAuth.class)
                .setActivity(DeviceCertActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_LkSelfReportingMacAuth>() {
                    @Override
                    public void onSuccess(ChuZuWu_LkSelfReportingMacAuth bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("认证成功");
                        EventBus.getDefault().post(new InfoInFragmetnEvent());
                        EventBus.getDefault().post(new InfoDeviceFragmentEvent());
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {
        mSingleSrl.setRefreshing(false);
    }
}
