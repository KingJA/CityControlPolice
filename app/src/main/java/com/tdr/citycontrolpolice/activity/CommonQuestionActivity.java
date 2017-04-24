package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
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
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

/**
 * 项目名称：常见问题
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/27 17:25
 * 修改备注：
 */
public class CommonQuestionActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout mRlBack;
    private TextView mTvTitle;
    private WebView mWb;
    private ProgressBar mPb;
    private TextView tv_network_retry;
    private LinearLayout ll_network_root;
    private String url;
    private RelativeLayout rl_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.include_webview);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        rl_refresh = (RelativeLayout) findViewById(R.id.rl_refresh);
        ll_network_root = (LinearLayout) findViewById(R.id.ll_network_root);
        tv_network_retry = (TextView) findViewById(R.id.tv_network_retry);
        mRlBack = (RelativeLayout) findViewById(R.id.rl_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mWb = (WebView) findViewById(R.id.wb);
        mPb = (ProgressBar) findViewById(R.id.pb);
        url = Constants.getWebViewUrl() + Constants.COMMON_QUESTION + "?token=" + UserService.getInstance(App.getContext()).getToken();
        mWb.loadUrl(url);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
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
//
//            @Override
//            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
//                super.onReceivedHttpError(view, request, errorResponse);
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
    public void setData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                if (mWb.canGoBack()) {
                    mWb.goBack();
                } else {
                   finish();
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
