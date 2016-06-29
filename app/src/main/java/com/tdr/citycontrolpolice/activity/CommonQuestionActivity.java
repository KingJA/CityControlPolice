package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/27 17:25
 * 修改备注：
 */
public class CommonQuestionActivity extends Activity {

    private RelativeLayout mRlBack;
    private TextView mTvTitle;
    private WebView mWb;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_webview);
        initView();
        initData();
    }


    protected void initView() {
        mRlBack = (RelativeLayout) findViewById(R.id.rl_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mWb = (WebView) findViewById(R.id.wb);
        mPb = (ProgressBar) findViewById(R.id.pb);
        String url = Constants.WEBVIEW_HOST + Constants.COMMON_QUESTION + "?token=" + UserService.getInstance(App.getContext()).getToken();
        mWb.loadUrl(url);
    }

    public void initData() {
        WebSettings settings = mWb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
