package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
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
 * 项目名称：物联网城市防控(警用版)
 * 类描述：工具预警Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/26 10:20
 * 修改备注：
 */
public class TabWorkFragment extends KjBaseFragment implements View.OnClickListener {
    private TextView tv_network_retry;
    private LinearLayout ll_network_root;
    private RelativeLayout mRlBack;
    private TextView mTvTitle;
    private WebView mWb;
    private ProgressBar mPb;
    private RelativeLayout rl_refresh;


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
        ll_network_root = (LinearLayout) rootView.findViewById(R.id.ll_network_root);
        tv_network_retry = (TextView) rootView.findViewById(R.id.tv_network_retry);
        rl_refresh = (RelativeLayout) rootView.findViewById(R.id.rl_refresh);
        mRlBack = (RelativeLayout) rootView.findViewById(R.id.rl_back);
        mTvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        mWb = (WebView) rootView.findViewById(R.id.wb);
        mPb = (ProgressBar) rootView.findViewById(R.id.pb);
        String url = Constants.getWebViewUrl() + Constants.JOB_LARAM + "?token=" + UserService.getInstance(App.getContext()).getToken();
        Log.e(TAG, "initFragmentView url: "+url );
        mWb.loadUrl(url);
    }

    @Override
    protected void initFragmentNet() {

    }

    @Override
    protected void initFragmentData() {
        tv_network_retry.setOnClickListener(this);
        rl_refresh.setOnClickListener(this);
        mRlBack.setOnClickListener(this);
        WebSettings settings = mWb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(false);
//        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                ll_network_root.setVisibility(View.VISIBLE);
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }

//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
//                Log.e(TAG, "onReceivedHttpError: "+errorResponse.toString() );
//                ll_network_root.setVisibility(View.VISIBLE);
//            }
        });
        mWb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPb.setProgress(newProgress);
                mPb.setVisibility(newProgress == 100 ? View.GONE : View.VISIBLE);
            }
        });

    }

    @Override
    protected void setFragmentData() {
        mTvTitle.setText("工作预警");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                if (mWb.canGoBack()) {
                    mWb.goBack();
                } else {
                    ToastUtil.showMyToast("没有可返回的页面了");
                }
                break;
            case R.id.rl_refresh:
                mWb.reload();
                break;
            case R.id.tv_network_retry:
                ll_network_root.setVisibility(View.GONE);
                mWb.reload();
                break;

        }
    }

}
