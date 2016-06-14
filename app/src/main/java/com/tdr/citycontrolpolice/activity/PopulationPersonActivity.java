package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Common_LKRenYuanXinxi;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：流动人口详情
 * 创建人：KingJA
 * 创建时间：2016/4/13 15:37
 * 修改备注：
 */
public class PopulationPersonActivity extends BackTitleActivity {

    private TextView mTvZzzh;
    private TextView mTvXm;
    private TextView mTvLxdh;
    private TextView mTvSfzh;
    private TextView mTvHksx;
    private TextView mTvHkxz;
    private TextView mTvBm;
    private TextView mTvXb;
    private TextView mTvCsrq;
    private TextView mTvMz;
    private TextView mTvHyzk;
    private TextView mTvWhcd;
    private TextView mTvDqfl;
    private TextView mTvDjrq;
    private TextView mTvDqrq;
    private TextView mTvCszy;
    private TextView mTvGzcs;
    private TextView mTvGzdz;
    private String identityCard;
    private DialogProgress dialogProgress;


    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_detial_person, null);
        return view;
    }

    @Override
    public void initVariables() {
        identityCard = getIntent().getStringExtra("IDENTITYCARD");

    }

    @Override
    protected void initView() {
        mTvZzzh = (TextView) view.findViewById(R.id.tv_zzzh);
        mTvXm = (TextView) view.findViewById(R.id.tv_xm);
        mTvLxdh = (TextView) view.findViewById(R.id.tv_lxdh);
        mTvSfzh = (TextView) view.findViewById(R.id.tv_sfzh);
        mTvHksx = (TextView) view.findViewById(R.id.tv_hksx);
        mTvHkxz = (TextView) view.findViewById(R.id.tv_hkxz);
        mTvBm = (TextView) view.findViewById(R.id.tv_bm);
        mTvXb = (TextView) view.findViewById(R.id.tv_xb);
        mTvCsrq = (TextView) view.findViewById(R.id.tv_csrq);
        mTvMz = (TextView) view.findViewById(R.id.tv_mz);
        mTvHyzk = (TextView) view.findViewById(R.id.tv_hyzk);
        mTvWhcd = (TextView) view.findViewById(R.id.tv_whcd);
        mTvDqfl = (TextView) view.findViewById(R.id.tv_dqfl);
        mTvDjrq = (TextView) view.findViewById(R.id.tv_djrq);
        mTvDqrq = (TextView) view.findViewById(R.id.tv_dqrq);
        mTvCszy = (TextView) view.findViewById(R.id.tv_cszy);
        mTvGzcs = (TextView) view.findViewById(R.id.tv_gzcs);
        mTvGzdz = (TextView) view.findViewById(R.id.tv_gzdz);

        dialogProgress = new DialogProgress(this);
    }

    @Override
    public void initNet() {
        dialogProgress.show();
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("IDENTITYCARD", identityCard);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_LKRenYuanXinxi", param)
                .setActivity(PopulationPersonActivity.this)
                .setBeanType(Common_LKRenYuanXinxi.class)
                .setCallBack(getDetailCallBack).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("流动人口详情");
    }

    public static void goActivity(Context context, String identityCard) {
        Intent intent = new Intent(context, PopulationPersonActivity.class);
        intent.putExtra("IDENTITYCARD", identityCard);
        context.startActivity(intent);
    }

    private WebServiceCallBack<Common_LKRenYuanXinxi> getDetailCallBack = new WebServiceCallBack<Common_LKRenYuanXinxi>() {
        @Override
        public void onSuccess(Common_LKRenYuanXinxi bean) {
            setDetailInfo(bean.getContent());
            dialogProgress.dismiss();
        }

        @Override
        public void onErrorResult(ErrorResult errorResult) {
            dialogProgress.dismiss();
        }
    };

    private void setDetailInfo(Common_LKRenYuanXinxi.ContentBean bean) {
        mTvZzzh.setText(bean.getZZZH());
        mTvXm.setText(bean.getXM());
        mTvLxdh.setText(bean.getLXDH());
        mTvSfzh.setText(bean.getSFZH());
        mTvHksx.setText(bean.getHKSX());
        mTvHkxz.setText(bean.getHKXZ());
        mTvBm.setText(bean.getBM());
        mTvXb.setText(bean.getXB());
        mTvCsrq.setText(bean.getCSRQ());
        mTvMz.setText(bean.getMZ());
        mTvHyzk.setText(bean.getHYZK());
        mTvWhcd.setText(bean.getWHCD());
        mTvDqfl.setText(bean.getDQFL());
        mTvDjrq.setText(bean.getDJRQ());
        mTvDqrq.setText(bean.getDQRQ());
        mTvCszy.setText(bean.getCSZY());
        mTvGzcs.setText(bean.getGZCS());
        mTvGzdz.setText(bean.getGZDZ());
    }
}
