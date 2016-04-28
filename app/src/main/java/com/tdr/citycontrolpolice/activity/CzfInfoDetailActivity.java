package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/13 16:41
 * 修改备注：
 */
public class CzfInfoDetailActivity extends BackTitleActivity {
    public static final String CZF_INFO_DETAIL = "HOUSE_ID";
    private TextView mTvOwnerName;
    private TextView mTvOwnerPhone;
    private TextView mTvCzfName;
    private TextView mTvCzfAddress;
    private TextView mTvRoomCount;
    private TextView mTvPersonCount;
    private List<KjChuZuWuInfo.ContentBean.RoomListBean> mRoomList;
    private KjChuZuWuInfo.ContentBean mContent;
    private int mRoomCount;
    private int mPersonCount;
    private String houseId;
    private HashMap<String, Object> mParam = new HashMap<>();


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czf_tongji, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(CZF_INFO_DETAIL);
        mParam.put("TaskID", "1");
        mParam.put("HouseID", houseId);

    }

    @Override
    protected void initView() {
        mTvOwnerName = (TextView) view.findViewById(R.id.tv_ownerName);
        mTvOwnerPhone = (TextView) view.findViewById(R.id.tv_ownerPhone);
        mTvCzfName = (TextView) view.findViewById(R.id.tv_czfName);
        mTvCzfAddress = (TextView) view.findViewById(R.id.tv_czfAddress);
        mTvRoomCount = (TextView) view.findViewById(R.id.tv_roomCount);
        mTvPersonCount = (TextView) view.findViewById(R.id.tv_personCount);
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        ThreadPoolTask.Builder<KjChuZuWuInfo> builder = new ThreadPoolTask.Builder<KjChuZuWuInfo>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(CzfInfoDetailActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        setProgressDialog(false);
                        mContent = bean.getContent();
                        setData(mContent);
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

    }

    @Override
    public void setData() {
        setTitle("出租屋信息统计");
    }

    private void setData(KjChuZuWuInfo.ContentBean mContent) {
        mRoomList = mContent.getRoomList();
        mRoomCount = mContent.getRoomList().size();
        for (KjChuZuWuInfo.ContentBean.RoomListBean bean : this.mRoomList) {
            if (bean.getHEADCOUNT() > 0) {
                mPersonCount += bean.getHEADCOUNT();
            }
        }
        mTvOwnerName.setText(mContent.getOWNERNAME());
        mTvOwnerPhone.setText(mContent.getPHONE());
        mTvCzfName.setText(mContent.getHOUSENAME());
        mTvCzfAddress.setText(mContent.getADDRESS());
        mTvRoomCount.setText(mRoomCount + "");
        mTvPersonCount.setText(mPersonCount + "");
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, CzfInfoDetailActivity.class);
        intent.putExtra(CZF_INFO_DETAIL, houseId);
        context.startActivity(intent);
    }
}
