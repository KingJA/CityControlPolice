package com.tdr.citycontrolpolice.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/27 14:35
 * 修改备注：
 */
public class TabTongjiFragment extends KjBaseFragment {


    private RelativeLayout mRlBack;
    private TextView mTvTitle;
    private WebView mWb;
    private ProgressBar mPb;
    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.include_webview, container, false);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {

    }

    @Override
    protected void initFragmentView() {
        mRlBack = (RelativeLayout) rootView.findViewById(R.id.rl_back);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mWb = (WebView) rootView.findViewById(R.id.wb);
        mPb = (ProgressBar) rootView.findViewById(R.id.pb);
        String url= Constants.WEBVIEW_HOST+Constants.JOB_TONGJI+"?token="+UserService.getInstance(App.getContext()).getToken();
        mWb.loadUrl(url);
    }

    @Override
    protected void initFragmentNet() {

    }

    @Override
    protected void initFragmentData() {
        WebSettings settings = mWb.getSettings();
        settings.setJavaScriptEnabled(true);
        mWb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPb.setProgress(newProgress);
                mPb.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }
        });
        mRlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWb.canGoBack()) {
                    mWb.goBack();
                } else {
                    ToastUtil.showMyToast("没有可返回的页面了");
                }
            }
        });
    }

    @Override
    protected void setFragmentData() {
        mTvTitle.setText("工作统计");
    }
}
