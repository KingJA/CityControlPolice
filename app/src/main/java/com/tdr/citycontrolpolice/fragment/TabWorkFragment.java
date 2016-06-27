package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.App;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.UserService;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：工具预警Fragment
 * 创建人：KingJA
 * 创建时间：2016/4/26 10:20
 * 修改备注：
 */
public class TabWorkFragment extends KjBaseFragment {


    private WebView wb_tongji;
    private ProgressBar pb_tongji;

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tongji, container, false);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {

    }

    @Override
    protected void initFragmentView() {
        pb_tongji = (ProgressBar) rootView.findViewById(R.id.pb_tongji);
        wb_tongji = (WebView) rootView.findViewById(R.id.wb_tongji);
        String url= Constants.WEBVIEW_HOST+Constants.JOB_LARAM+"?token="+ UserService.getInstance(App.getContext()).getToken();
        wb_tongji.loadUrl(url);
        Log.e("TabWorkFragment", url);

    }

    @Override
    protected void initFragmentNet() {

    }

    @Override
    protected void initFragmentData() {
        wb_tongji.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wb_tongji.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb_tongji.setProgress(newProgress);
                if (newProgress == 100) {
                    pb_tongji.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void setFragmentData() {

    }
}
