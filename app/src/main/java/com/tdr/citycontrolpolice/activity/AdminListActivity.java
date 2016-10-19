package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.AdminsAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AdminList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Favorites;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RemoveAdminByPolice;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.event.AdminListRefreshEvent;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description：TODO
 * Create Time：2016/10/17 10:25
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AdminListActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener, AdminsAdapter.OnAdminDeleteListener {
    private SwipeRefreshLayout mSingleSrl;
    private ListView mSingleLv;
    private LinearLayout mLlEmpty;
    private String houseId;
    private List<ChuZuWu_AdminList.ContentBean.AdminListBean> mAdminList = new ArrayList<>();
    private AdminsAdapter mAdminsAdapter;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.single_lv_sl, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mLlEmpty = (LinearLayout) findViewById(R.id.ll_empty);
        mSingleSrl = (SwipeRefreshLayout) findViewById(R.id.single_srl);
        mSingleLv = (ListView) findViewById(R.id.single_lv);
        mAdminsAdapter = new AdminsAdapter(this, mAdminList);
        mSingleLv.setAdapter(mAdminsAdapter);

        mSingleSrl.setColorSchemeResources(R.color.bg_blue_solid);
        mSingleSrl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        mSingleSrl.setOnRefreshListener(this);
    }

    @Override
    public void initNet() {
        mSingleSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("PageIndex", "0");
        param.put("PageSize", "200");
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_AdminList", param)
                .setBeanType(ChuZuWu_AdminList.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_AdminList>() {
                    @Override
                    public void onSuccess(ChuZuWu_AdminList bean) {
                        mSingleSrl.setRefreshing(false);
                        mAdminList = bean.getContent().getAdminList();
                        mLlEmpty.setVisibility(mAdminList.size() == 0 ? View.VISIBLE : View.GONE);
                        mAdminsAdapter.setData(mAdminList);

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSingleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        mAdminsAdapter.setOnAdminDeleteListener(this);
    }

    private void uploadDelete(String cardId, final int position) {
        mSingleSrl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("IDENTITYCARD", cardId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_RemoveAdminByPolice", param)
                .setBeanType(ChuZuWu_RemoveAdminByPolice.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_RemoveAdminByPolice>() {
                    @Override
                    public void onSuccess(ChuZuWu_RemoveAdminByPolice bean) {
                        mAdminsAdapter.deleteItem(position);
                        mSingleSrl.setRefreshing(false);
                        ToastUtil.showMyToast("管理员删除成功");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mSingleSrl.setRefreshing(false);
                    }
                }).build().execute();
    }

    @Override
    public void setData() {
        setTitle("管理员列表");
        setRightTextVisibility("添加");
        setOnRightClickListener(new OnRightClickListener() {
            @Override
            public void onRightClick() {
                AddAdminActivity.goActivity(AdminListActivity.this, houseId);
            }
        });
    }

    @Override
    public void onRefresh() {
        mSingleSrl.setRefreshing(false);
    }

    private static final String HOUSE_ID = "HOUSE_ID";

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, AdminListActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AdminListRefreshEvent messageEvent) {
        initNet();
    }

    @Override
    public void onDelete(final String cardId, final int position) {
        DialogDouble dialogDouble = new DialogDouble(this, "确定要删除该管理员?", "取消", "确定");
        dialogDouble.show();
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
            }

            @Override
            public void onRight() {
                uploadDelete(cardId, position);
            }
        });
    }
}
