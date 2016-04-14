package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;

/**
 * Created by Administrator on 2016/3/29.v
 */
public class AboutActivity extends Activity {

    int versionCode;
    String versionName;
    String packageName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        title();
        getVersion();
        init_view();
    }

    /**
     * 标题栏
     */
    private void title() {
        ImageView img_title = (ImageView) findViewById(R.id.image_back);
        img_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("关于我们");
    }

    private void init_view() {
        TextView tv_versionCode = (TextView) findViewById(R.id.tv_versionCode);
        tv_versionCode.setText("当前软件版本" + versionName);
    }


    public void getVersion() {
        PackageManager manager;
        PackageInfo info = null;
        manager = this.getPackageManager();
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            versionCode = info.versionCode;
            versionName = info.versionName;
            packageName = info.packageName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }


    }
}
