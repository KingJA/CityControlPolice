package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BaseFragmentPagerAdapter;
import com.tdr.citycontrolpolice.czffragment.ManagerFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_InstallStatus;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.fragment.KjApplyFragment;
import com.tdr.citycontrolpolice.fragment.KjPersonFragment;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.SimpleIndicatorLayout;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.tdr.citycontrolpolice.view.popupwindow.CzfInfoPopKj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：出租房管理主页面
 * 创建人：KingJA
 * 创建时间：2016/3/24 10:02
 * 修改备注：
 */

public class KjCzfInfoActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, CzfInfoPopKj.OnCzfInfoPopClickListener {
    private static final String TAG = "KjCzfInfoActivity";
    private ImageView iv_czf_info_detail;
    private TextView tv_czf_info_name;
    private TextView tv_czf_info_phone;
    private TextView tv_czf_info_address;
    private String mToken;
    private String mHouseId;
    private ViewPager vp_czf_info;
    private SimpleIndicatorLayout sil_czf_info;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<String> mTitleList = Arrays.asList("出租房管理", "自助申报", "流动人口");
    private List<Fragment> mFragmentList = new ArrayList<>();
    private CzfInfoPopKj mCzfInfoPop;
    private DialogProgress mProgressDialog;
    private KjChuZuWuInfo mCzfInfo = new KjChuZuWuInfo();
    private int mIsregister;
    private DialogConfirm mConfirmDialog;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_kj_czf_info, null);
        return view;
    }

    @Override
    public void initVariables() {
        mToken = UserService.getInstance(this).getToken();
        mHouseId = getIntent().getStringExtra("HouseID");
        Log.i(TAG, "HouseID: " + mHouseId);
        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);
    }

    @Override
    protected void initView() {
        iv_czf_info_detail = (ImageView) view.findViewById(R.id.iv_czf_info_detail);
        tv_czf_info_name = (TextView) view.findViewById(R.id.tv_czf_info_name);
        tv_czf_info_phone = (TextView) view.findViewById(R.id.tv_czf_info_phone);
        tv_czf_info_address = (TextView) view.findViewById(R.id.tv_czf_info_address);
        sil_czf_info = (SimpleIndicatorLayout) view.findViewById(R.id.sil_czf_info);
        vp_czf_info = (ViewPager) view.findViewById(R.id.vp_czf_info);
        mDoubleDialog = new DialogDouble(KjCzfInfoActivity.this, "确定进行设备申报？", "确定", "取消");
        mConfirmDialog = new DialogConfirm(KjCzfInfoActivity.this, "设备已申报成功！", "确定");
        mProgressDialog = new DialogProgress(KjCzfInfoActivity.this);
        mCzfInfoPop = new CzfInfoPopKj(rlParent, KjCzfInfoActivity.this);

    }


    @Override
    public void initNet() {
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(KjCzfInfoActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        mCzfInfo = bean;
                        mIsregister = bean.getContent().getISREGISTER();
                        Log.i(TAG, "mIsregister: " + mIsregister);
                        mCzfInfoPop.setAppleVisibility(mIsregister);
                        tv_czf_info_name.setText(bean.getContent().getOWNERNAME());
                        tv_czf_info_phone.setText(bean.getContent().getPHONE());
                        tv_czf_info_address.setText(bean.getContent().getADDRESS());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        mFragmentList.add(ManagerFragment.newInstance(mHouseId));
        mFragmentList.add(KjApplyFragment.newInstance(mHouseId));
        mFragmentList.add(KjPersonFragment.newInstance(mHouseId));
        mCzfInfoPop.setOnCzfInfoPopClickListener(KjCzfInfoActivity.this);
        setOnRightClickListener(this);
        vp_czf_info.setOffscreenPageLimit(mTitleList.size() - 1);
        vp_czf_info.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        sil_czf_info.setTitles(mTitleList);
        sil_czf_info.setUpWithViewPager(vp_czf_info, 0);
        mConfirmDialog.setOnConfirmClickListener(new DialogConfirm.OnConfirmClickListener() {
            @Override
            public void onConfirm() {

            }
        });
        mDoubleDialog.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                applyDevice();
            }

            @Override
            public void onRight() {
            }
        });
        iv_czf_info_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CzfInfoDetailActivity.goActivity(KjCzfInfoActivity.this, mCzfInfo);
                ToastUtil.showMyToast("房东详情");
            }
        });
    }

    @Override
    public void setData() {
        setRightVisibility(View.VISIBLE);
        setTitle("出租房管理");

    }

    @Override
    public void onRightClick() {

        mCzfInfoPop.showPopupWindowDown();
    }

    @Override
    public void onCzfInfoPop(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(this, CzfModifyActivity.class);
                intent.putExtra("CZF_INFO", mCzfInfo);
                startActivity(intent);
                break;
            case 1:
                if (mIsregister == 1) {
                    mConfirmDialog.show();
                    break;
                }
                mDoubleDialog.show();

                break;
            case 2:
                ActivityUtil.goActivityWithBundle(this, CzfFloatActivity.class, new Bundle());
                break;
            case 3:
                Bundle bundle = new Bundle();
                bundle.putString("HOUSE_ID", mHouseId);
                ActivityUtil.goActivityWithBundle(this, DeviceBindingListActivity.class, bundle);
                break;
            case 4:
                Intent deviceIntent = new Intent(this, DeviceManagerActivity.class);
                deviceIntent.putExtra("HOUSE_ID", mHouseId);
                startActivity(deviceIntent);
//                ActivityUtil.goActivity(this,DeviceManagerActivity.class);
                break;
            default:
                break;
        }
        mCzfInfoPop.dismiss();
    }

    /**
     * 设备申报
     */
    private void applyDevice() {
        mProgressDialog.show();
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", mHouseId);
        param.put("ISREGISTER", "1");

        ThreadPoolTask.Builder<ChuZuWu_InstallStatus> builder = new ThreadPoolTask.Builder<ChuZuWu_InstallStatus>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_InstallStatus", param)
                .setBeanType(ChuZuWu_InstallStatus.class)
                .setActivity(KjCzfInfoActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_InstallStatus>() {
                    @Override
                    public void onSuccess(ChuZuWu_InstallStatus bean) {
                        if (bean.getContent().getInstallStatus() == 1) {
                            mIsregister = 1;
                            mProgressDialog.dismiss();
                            mConfirmDialog.show();
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    private DialogDouble mDoubleDialog;

}
