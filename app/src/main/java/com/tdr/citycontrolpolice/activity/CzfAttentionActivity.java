package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_InquireFavorites;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomListOfFavorites;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/23 10:20
 * 修改备注：
 */
public class CzfAttentionActivity extends BackTitleActivity implements BackTitleActivity.OnRightClickListener{
    private static final String HOUSE_ID="HOUSE_ID";
    private String houseId;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_attention, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void initNet() {
        queryAttention("");
    }

    private void queryAttention(String address) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", 200);
        param.put("PageIndex", 0);
        param.put("Address", address);
        param.put("XQCODE", "");
        param.put("PCSCODE", "");
        param.put("JWHCODE", "");
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_INQUIREFAVORITES, param)
                .setBeanType(ChuZuWu_InquireFavorites.class)
                .setCallBack(new WebServiceCallBack< ChuZuWu_InquireFavorites>() {
                    @Override
                    public void onSuccess( ChuZuWu_InquireFavorites bean) {
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        setOnRightClickListener(this);

    }

    @Override
    public void setData() {
        setTitle("出租房关注");
        setRightImageVisibility(R.drawable.bg_attention);

    }

    @Override
    public void onRightClick() {
        ActivityUtil.goActivity(this,RemindActivity.class);
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, CzfAttentionActivity.class);
        intent.putExtra(HOUSE_ID,houseId);
        context.startActivity(intent);
    }
}
