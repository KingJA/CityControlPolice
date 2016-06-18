package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfCardAdapter;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SwipeCardList;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.popupwindow.RoomSelectPop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：刷卡记录列表
 * 创建人：KingJA
 * 创建时间：2016/3/25 13:30
 * 修改备注：
 */
public class CzfCardActivity extends BackTitleActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String HOUSE_ID = "HOUSE_ID";
    public static final String CZFINFO = "CZFINFO";
    private static final String TAG = "CzfCardActivity";
    private String houseId;
    private LinearLayout ll_empty;
    private ListView single_lv;
    private SwipeRefreshLayout single_srl;
    private List<ChuZuWu_SwipeCardList.ContentBean.PERSONNELINFOLISTBean> personnelinfolist = new ArrayList<>();
    private CzfCardAdapter czfCardAdapter;
    private KjChuZuWuInfo czfinfo;
    private RelativeLayout rl_select;
    private RoomSelectPop roomSelectPop;
    private TextView tv_room;
    private String currentSelectType="";

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.template_select, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
        czfinfo = (KjChuZuWuInfo) getIntent().getSerializableExtra(CZFINFO);
    }

    @Override
    protected void initView() {
        tv_room = (TextView) view.findViewById(R.id.tv_room);
        rl_select = (RelativeLayout) view.findViewById(R.id.rl_select);
        ll_empty = (LinearLayout) view.findViewById(R.id.ll_empty);
        single_lv = (ListView) view.findViewById(R.id.single_lv);
        single_srl = (SwipeRefreshLayout) view.findViewById(R.id.single_srl);
        single_srl.setColorSchemeResources(R.color.bg_blue_solid);
        single_srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        single_srl.setOnRefreshListener(this);
        czfCardAdapter = new CzfCardAdapter(this, personnelinfolist);
        single_lv.setAdapter(czfCardAdapter);
        roomSelectPop = new RoomSelectPop(rl_select, this, czfinfo.getContent().getRoomList());
    }

    @Override
    public void initNet() {
        loadNet(currentSelectType);
    }

    private void loadNet(String roomId) {
        single_srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("ROOMID", roomId);
        param.put("PageSize", 500);
        param.put("PageIndex", 0);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_SwipeCardList", param)
                .setBeanType(ChuZuWu_SwipeCardList.class)
                .setActivity(CzfCardActivity.this)
                .setCallBack(new WebServiceCallBack<ChuZuWu_SwipeCardList>() {
                    @Override
                    public void onSuccess(ChuZuWu_SwipeCardList bean) {
                        single_srl.setRefreshing(false);
                        personnelinfolist = bean.getContent().getPERSONNELINFOLIST();
                        ll_empty.setVisibility(personnelinfolist.size() == 0 ? View.VISIBLE : View.GONE);
                        Log.i(TAG, "onSuccess: " + personnelinfolist.size());
                        czfCardAdapter.setData(personnelinfolist);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        single_srl.setRefreshing(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        rl_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomSelectPop.showPopupWindowDown();
            }
        });
        roomSelectPop.setOnRoomSelectListener(new RoomSelectPop.OnRoomSelectListener() {
            @Override
            public void onSelect(int position, KjChuZuWuInfo.ContentBean.RoomListBean bean) {
                tv_room.setText(position==0?"全部房间":bean.getROOMNO()+"");
                CzfCardActivity.this.currentSelectType=position==0?"":bean.getROOMID();
                loadNet(currentSelectType);
            }
        });
    }

    @Override
    public void setData() {
        setTitle("刷卡记录列表");
    }

    public static void goActivity(Context context, String houseId, KjChuZuWuInfo czfinfo) {
        Intent intent = new Intent(context, CzfCardActivity.class);
        intent.putExtra(CZFINFO,czfinfo);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        loadNet(currentSelectType);
    }
}
