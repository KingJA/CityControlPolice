package com.tdr.citycontrolpolice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.DetailInfoAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_MenPaiAuthorizationList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.popupwindow.CzfListDetailPop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:52
 * 修改备注：
 */
public class DetailCzfInfoActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, CzfListDetailPop.OnPopClickListener {

    private static final String TAG = "DetailCzfInfoActivity";
    private String mHouseId;
    private String mRoomId;
    private ListView lv;
    private String mToken;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<ChuZuWu_MenPaiAuthorizationList.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = new ArrayList<>();
    private DetailInfoAdapter detailInfoAdapter;
    private LinearLayout ll_empty;
    private CzfListDetailPop czfListDetailPop;
    private String mRoomNo;
    private String mRoomId1;

    @Override
    public void initVariables() {
        Bundle bundle = getIntent().getExtras();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");
        mRoomNo = bundle.getString("ROOM_NO");
        mToken = UserService.getInstance(this).getToken();

        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);
        mParam.put("ROOMID", mRoomId);
        mParam.put("PageSize", 50);
        mParam.put("PageIndex", 0);
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.fragment_lv, null);
        return view;
    }

    @Override
    protected void initView() {
        setTitle("人员信息");
        setRightVisibility(View.VISIBLE);
        lv = (ListView) findViewById(R.id.lv_exist);
        ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
        detailInfoAdapter = new DetailInfoAdapter(this, personnelinfolist);
        lv.setAdapter(detailInfoAdapter);
        czfListDetailPop = new CzfListDetailPop(rlParent, DetailCzfInfoActivity.this);
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        ThreadPoolTask.Builder<ChuZuWu_MenPaiAuthorizationList> builder = new ThreadPoolTask.Builder<ChuZuWu_MenPaiAuthorizationList>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_MenPaiAuthorizationList", mParam)
                .setBeanType(ChuZuWu_MenPaiAuthorizationList.class)
                .setActivity(DetailCzfInfoActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_MenPaiAuthorizationList>() {
                    @Override
                    public void onSuccess(ChuZuWu_MenPaiAuthorizationList bean) {
                        setProgressDialog(false);
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        Log.i(TAG, "personnelinfolist: " + personnelinfolist.size());
                        detailInfoAdapter.setData(personnelinfolist);
                        ll_empty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);
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
        setOnRightClickListener(this);
        czfListDetailPop.setOnPopClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public void onRightClick() {
        czfListDetailPop.showPopupWindowDown();
    }

    @Override
    public void onCzfInfoPop(int position) {
        switch (position) {
            case 0:
//                Intent intent = new Intent(this, KjRoomModifyActivity.class);
//                intent.putExtra("HOUSEID", mHouseId);
//                intent.putExtra("ROOMID", mRoomId);
//                intent.putExtra("ROOMNO", mRoomNo);
//                startActivity(intent);
                KjRoomModifyActivity.goActivity(this, mHouseId, mRoomId, mRoomNo);
                break;
            default:
                break;
        }
    }
}
