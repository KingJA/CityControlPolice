package com.tdr.citycontrolpolice.activity;

import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.UserService;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/27 17:25
 * 修改备注：
 */
public class CommonQuestionActivity extends BackTitleActivity {
    @Override
    public View setContentView() {
        view=View.inflate(this, R.layout.activity_request,null);
        return view;
    }
    private WebView wb_request;
    private ProgressBar pb_request;


    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        wb_request = (WebView) findViewById(R.id.wb_request);
        pb_request = (ProgressBar) findViewById(R.id.pb_request);
        String url= Constants.WEBVIEW_HOST+Constants.COMMON_QUESTION+"?token="+ UserService.getInstance(App.getContext()).getToken();
        Log.e("CommonQuestionActivity", url);
        wb_request.loadUrl(url);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        wb_request.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wb_request.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb_request.setProgress(newProgress);
                if (newProgress == 100) {
                    pb_request.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void setData() {
        setTitle("常见问题");

    }
}
