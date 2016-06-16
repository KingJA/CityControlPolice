package com.tdr.citycontrolpolice.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.SDCardUtil;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/16 16:23
 * 修改备注：
 */
public class ExceptionActivity extends BackTitleActivity{

    private WebView wb_exception;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_exception, null);
        return view;
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        wb_exception = (WebView) view.findViewById(R.id.wb_exception);

    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        setTitle("...");
    }

    @Override
    public void setData() {
        WebSettings webSettings =   wb_exception.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        wb_exception.loadData(SDCardUtil.txt2Html("KLogs","CrashLogs.txt",getApplication()), "text/html", "UTF-8");
    }
}
