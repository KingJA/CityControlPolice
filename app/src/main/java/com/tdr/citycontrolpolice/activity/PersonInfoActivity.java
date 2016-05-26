package com.tdr.citycontrolpolice.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.PersonInfoAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;
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
 * 类描述：人员信息
 * 创建人：KingJA
 * 创建时间：2016/3/25 16:52
 * 修改备注：
 */
public class PersonInfoActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener, CzfListDetailPop.OnPopClickListener {

    private static final String TAG = "PersonInfoActivity";
    private String mHouseId;
    private String mRoomId;
    private ListView lv;
    private String mToken;
    private HashMap<String, Object> mParam = new HashMap<>();
    private List<ChuZuWu_ComprehensiveInfo.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = new ArrayList<>();
    private PersonInfoAdapter personInfoAdapter;
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
        mParam.put("HOUSEID", mHouseId);
        mParam.put("ROOMID", mRoomId);
        mParam.put("PageSize", 100);
        mParam.put("PageIndex", 0);
    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.fragment_lv, null);
        return view;
    }

    @Override
    protected void initView() {

        setRightVisibility(View.VISIBLE);
        lv = (ListView) findViewById(R.id.lv_exist);
        ll_empty = (LinearLayout) findViewById(R.id.ll_empty);
        personInfoAdapter = new PersonInfoAdapter(this, personnelinfolist);
        lv.setAdapter(personInfoAdapter);
        czfListDetailPop = new CzfListDetailPop(rlParent, PersonInfoActivity.this);
    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        ThreadPoolTask.Builder<ChuZuWu_ComprehensiveInfo> builder = new ThreadPoolTask.Builder<ChuZuWu_ComprehensiveInfo>();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_ComprehensiveInfo", mParam)
                .setBeanType(ChuZuWu_ComprehensiveInfo.class)
                .setActivity(PersonInfoActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ComprehensiveInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_ComprehensiveInfo bean) {
                        setProgressDialog(false);
                       personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        Log.i(TAG, "personnelinfolist: " + PersonInfoActivity.this.personnelinfolist.size());
                        personInfoAdapter.setData(PersonInfoActivity.this.personnelinfolist);
                        ll_empty.setVisibility(PersonInfoActivity.this.personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);
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
        setTitle("人员信息");
    }

    @Override
    public void onRightClick() {
        czfListDetailPop.showPopupWindowDown();
    }

    @Override
    public void onCzfInfoPop(int position) {
        switch (position) {
            case 0:
                ModifyRoomActivity.goActivity(this, mHouseId, mRoomId, mRoomNo);
                break;
            default:
                break;
        }
    }
}
