package com.tdr.citycontrolpolice.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.net.DownloadDbManager;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/18 16:47
 * 修改备注：
 */
public class DownloadDbActivity extends BackTitleActivity {
    private Button btn_download_db;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            size = msg.arg1;
            switch (msg.what) {
                case DownloadDbManager.Done_Basic_Dictionary:
                    tv_dictionary.setText("完成" + size + "条");
                    break;
                case DownloadDbManager.Done_Basic_PaiChuSuo:
                    tv_paiChuSuo.setText("完成" + size + "条");
                    break;
                case DownloadDbManager.Done_Basic_XingZhengQuHua:
                    tv_xingZhengQuHua.setText("完成" + size + "条");
                    break;
                case DownloadDbManager.Done_Basic_JuWeiHui:
                    tv_juWeiHui.setText("完成" + size + "条");
                    break;

            }
        }
    };
    private TextView tv_dictionary;
    private TextView tv_paiChuSuo;
    private TextView tv_xingZhengQuHua;
    private TextView tv_juWeiHui;
    private int size;

    @Override
    public View setContentView() {
        view = View.inflate(DownloadDbActivity.this, R.layout.activity_download_db, null);
        return view;
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        btn_download_db = (Button) view.findViewById(R.id.btn_download_db);
        tv_dictionary = (TextView) view.findViewById(R.id.tv_Dictionary);
        tv_paiChuSuo = (TextView) view.findViewById(R.id.tv_PaiChuSuo);
        tv_xingZhengQuHua = (TextView) view.findViewById(R.id.tv_XingZhengQuHua);
        tv_juWeiHui = (TextView) view.findViewById(R.id.tv_JuWeiHui);
    }

    @Override
    public void initNet() {


    }

    @Override
    public void initData() {
        btn_download_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_dictionary.setText("下载中");
                tv_paiChuSuo.setText("下载中");
                tv_xingZhengQuHua.setText("下载中");
                tv_juWeiHui.setText("下载中");
                new DownloadDbManager(handler).startDownloadDb();
            }
        });
    }

    @Override
    public void setData() {
        setTitle("...");
    }


}
