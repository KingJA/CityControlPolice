package com.tdr.citycontrolpolice.activity;

import android.content.Context;
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
import com.tdr.citycontrolpolice.entity.ChuZuWu_BoundMenjinYiTiJiByQRCode;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Favorites;
import com.tdr.citycontrolpolice.entity.ChuZuWu_InstallStatus;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.fragment.InfoInFragment;
import com.tdr.citycontrolpolice.fragment.InfoLeftFragment;
import com.tdr.citycontrolpolice.fragment.InfoManagerFragment;
import com.tdr.citycontrolpolice.fragment.InfoPopulationFragment;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.TendencyEncrypt;
import com.tdr.citycontrolpolice.util.TimeUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.SimpleIndicatorLayout;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.tdr.citycontrolpolice.view.popupwindow.CzfInfoPopKj;

import org.greenrobot.eventbus.EventBus;

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

public class CzfInfoActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, CzfInfoPopKj.OnCzfInfoPopClickListener {
    private static final String TAG = "CzfInfoActivity";
    private static final int SCANNIN_ACCESS = 0x001;
    private TextView tv_czf_info_name;
    private TextView tv_czf_info_phone;
    private TextView tv_czf_info_address;
    private String mToken;
    private String mHouseId;
    private ViewPager vp_czf_info;
    private SimpleIndicatorLayout sil_czf_info;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<String> mTitleList = Arrays.asList("出租房管理", "入住申报", "离开申报", "流动系统");
    private List<Fragment> mFragmentList = new ArrayList<>();
    private CzfInfoPopKj mCzfInfoPop;
    private KjChuZuWuInfo mCzfInfo = new KjChuZuWuInfo();
    private int mIsregister;
    private DialogConfirm mConfirmDialog;
    private DialogProgress mDialogProgress;
    private int mIsfavorite;
    private TextView tv_czf_info_detail;
    private ImageView iv_attention;
    private String method;
    private DialogDouble mDoubleDialog;


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

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, CzfInfoActivity.class);
        intent.putExtra("HouseID", houseId);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        iv_attention = (ImageView) view.findViewById(R.id.tv_attention);
        tv_czf_info_detail = (TextView) view.findViewById(R.id.tv_czf_info_detail);
        tv_czf_info_name = (TextView) view.findViewById(R.id.tv_czf_info_name);
        tv_czf_info_phone = (TextView) view.findViewById(R.id.tv_czf_info_phone);
        tv_czf_info_address = (TextView) view.findViewById(R.id.tv_czf_info_address);
        sil_czf_info = (SimpleIndicatorLayout) view.findViewById(R.id.sil_czf_info);
        vp_czf_info = (ViewPager) view.findViewById(R.id.vp_czf_info);
        mDoubleDialog = new DialogDouble(CzfInfoActivity.this, "确定进行设备申报？", "确定", "取消");
        mConfirmDialog = new DialogConfirm(CzfInfoActivity.this, "设备已申报成功！", "确定");
        mCzfInfoPop = new CzfInfoPopKj(rlParent, CzfInfoActivity.this);
        mDialogProgress = new DialogProgress(this);
    }


    @Override
    public void initNet() {
        mDialogProgress.dismiss();
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(CzfInfoActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        isFinished = true;
                        mDialogProgress.dismiss();
                        mCzfInfo = bean;
                        mIsregister = bean.getContent().getISREGISTER();
                        mIsfavorite = bean.getContent().getISFAVORITE();
                        iv_attention.setBackgroundResource(mIsfavorite == 1 ? R.drawable.bg_unattention : R.drawable.bg_attention);
                        Log.i(TAG, "mIsregister: " + mIsregister);
                        mCzfInfoPop.setAppleVisibility(mIsregister);
                        mCzfInfoPop.setAccess(bean.getContent().getHAS(),"1023");
                        tv_czf_info_name.setText(bean.getContent().getOWNERNAME());
                        tv_czf_info_phone.setText(bean.getContent().getPHONE());
                        tv_czf_info_address.setText(bean.getContent().getADDRESS());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mDialogProgress.dismiss();
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        mFragmentList.add(InfoManagerFragment.newInstance(mHouseId));
        mFragmentList.add(InfoInFragment.newInstance(mHouseId));
        mFragmentList.add(InfoLeftFragment.newInstance(mHouseId));
        mFragmentList.add(InfoPopulationFragment.newInstance(mHouseId));
        mCzfInfoPop.setOnCzfInfoPopClickListener(CzfInfoActivity.this);
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
        tv_czf_info_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CzfInfoDetailActivity.goActivity(CzfInfoActivity.this, mHouseId);
            }
        });
        iv_attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsfavorite == 1) {
                    method = CustomConstants.CHUZUWU_REMOVEFAVORITES;
                    //取消关注
                } else {
                    method = CustomConstants.CHUZUWU_FAVORITES;
                    //添加关注
                }
                attentionCzf(method);
            }
        });
    }

    @Override
    public void setData() {
        setRightImageVisibility(R.drawable.bg_menu);
        setTitle("出租房管理");

    }

    @Override
    public void onRightClick() {
        if (isFinished) {
            mCzfInfoPop.showPopupWindowDownOffset();
        }

    }

    @Override
    public void onCzfInfoPop(int position) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, ModifyCzfActivity.class);
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
                CzfCardActivity.goActivity(this, mHouseId, mCzfInfo);
                break;
            case 3:
                CzfOutInActivity.goActivity(this, mHouseId, mCzfInfo);
                break;
            case 4:
                if (mIsregister != 1) {
                    ToastUtil.showMyToast("请先进行设备安装申报");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("HOUSE_ID", mHouseId);
                ActivityUtil.goActivityWithBundle(this, DeviceBindingActivity.class, bundle);
                break;
            case 5:
                Intent deviceIntent = new Intent(this, DeviceManagerActivity.class);
                deviceIntent.putExtra("HOUSE_ID", mHouseId);
                startActivity(deviceIntent);
                break;

            case 6:
                ChangeRecordActivity.goActivity(this, mHouseId);
                break;
            case 7:
                intent = new Intent();
                intent.setClass(this, zbar.CaptureActivity.class);
                startActivityForResult(intent, SCANNIN_ACCESS);
                break;
            case 8:
                if (mIsfavorite == 1) {
                    AttentionEditActivity.goActivity(this, mHouseId);
                } else {
                    ToastUtil.showMyToast("请先关注出租屋");
                }
                break;

            default:
                break;
        }
        mCzfInfoPop.dismiss();
    }

    /**
     * 关注/取消关注出租房
     *
     * @param method 方法名
     */
    private void attentionCzf(String method) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", mHouseId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, method, param)
                .setBeanType(ChuZuWu_Favorites.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_Favorites>() {
                    @Override
                    public void onSuccess(ChuZuWu_Favorites bean) {
                        setProgressDialog(false);
                        EventBus.getDefault().post(new Object());
                        if (mIsfavorite == 1) {
                            mIsfavorite = 0;
                        } else {
                            mIsfavorite = 1;
                        }
                        iv_attention.setBackgroundResource(mIsfavorite == 1 ? R.drawable.bg_unattention : R.drawable.bg_attention);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    /**
     * 设备申报
     */
    private void applyDevice() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", mHouseId);
        param.put("ISREGISTER", "1");
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_InstallStatus", param)
                .setBeanType(ChuZuWu_InstallStatus.class)
                .setActivity(CzfInfoActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_InstallStatus>() {
                    @Override
                    public void onSuccess(ChuZuWu_InstallStatus bean) {
                        if (bean.getContent().getInstallStatus() == 1) {
                            mIsregister = 1;
                            mCzfInfoPop.setAppleVisibility(mIsregister);
                            setProgressDialog(false);
                            mConfirmDialog.show();
                        }
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
        decodeDevice(data);
    }

    /**
     * 解析门禁二维码
     * @param data
     */
    private void decodeDevice(Intent data) {
        String result = data.getExtras().getString("result");
        result = result.substring(result.indexOf("?") + 1);
        String type = result.substring(0, 2);
        if ("M1".equals(type)) {
            result = result.substring(2);
            byte[] b = TendencyEncrypt.decode(result.getBytes());
            result = TendencyEncrypt.bytesToHexString(b);
            Log.e(TAG, result);
            final long deviceType = Long.valueOf(result.substring(0, 4), 16);
            final long deviceNO = Long.valueOf(result.substring(4,12), 16);
            final long ownerNO = Long.valueOf(result.substring(12,14), 16);
            final long ownerType = Long.valueOf(result.substring(14,16), 16);
            final long cardType = Long.valueOf(result.substring(16,18), 16);
            final String cardNO = result.substring(18,34);
            final long date = Long.valueOf(result.substring(34,40), 16);
            Long checkCode = Long.valueOf(result.substring(40,44), 16);
            Log.i(TAG, "设备类型: " + deviceType);
            Log.i(TAG, "设备编号: " + deviceNO);
            Log.i(TAG, "ownerNO: " + ownerNO);
            Log.i(TAG, "ownerType: " + ownerType);
            Log.i(TAG, "cardType: " + cardType);
            Log.i(TAG, "cardNO: " + cardNO);
            Log.i(TAG, "date: " + date);
            DialogDouble dialogDouble = new DialogDouble(this, "确定要绑定编号"+deviceNO+"的门禁？", "确定", "取消");
            dialogDouble.show();
            dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
                @Override
                public void onLeft() {
                    bindAccess(deviceType,deviceNO,ownerNO,ownerType,cardType,cardNO, TimeUtil.get2015Date(date));
                }
                @Override
                public void onRight() {

                }
            });
        } else {
            ToastUtil.showMyToast("不是要求的二维码对象");
        }
    }

    /**
     * 绑定门禁
     * @param deviceType
     * @param deviceNO
     * @param ownerNO
     * @param ownerType
     * @param cardType
     * @param cardNO
     * @param date
     */
    private void bindAccess(long deviceType, long deviceNO, long ownerNO, long ownerType, long cardType, String cardNO, String date) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", mHouseId);
        param.put("DEVICETYPE", deviceType);
        param.put("DEVICECODE", deviceNO);
        param.put("HOUSENO", ownerNO);
        param.put("LANDLORDLEVEL", ownerType);
        param.put("CARDTYPE", cardType);
        param.put("CARDID", cardNO);
        param.put("CREATEDTIME", date);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_BoundMenjinYiTiJiByQRCode", param)
                .setBeanType(ChuZuWu_BoundMenjinYiTiJiByQRCode.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_BoundMenjinYiTiJiByQRCode>() {
                    @Override
                    public void onSuccess(ChuZuWu_BoundMenjinYiTiJiByQRCode bean) {
                        setProgressDialog(false);
                        ToastUtil.showMyToast("门禁绑定成功");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }
}