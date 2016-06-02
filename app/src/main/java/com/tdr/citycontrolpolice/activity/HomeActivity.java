package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.fragment.TabHomeFragment;
import com.tdr.citycontrolpolice.fragment.TabMineFragment;
import com.tdr.citycontrolpolice.fragment.TabWorkFragment;
import com.tdr.citycontrolpolice.util.NetUtil;
import com.tdr.citycontrolpolice.util.StatusBarCompat;
import com.tdr.citycontrolpolice.view.CustomRadioGroup;


/**
 * Created by Administrator on 2016/1/21.
 */
public class HomeActivity extends Activity {
    private CustomRadioGroup footer;// 仿微信底部菜单
    private int[] itemImage = {
            R.drawable.home_off,
            R.drawable.police_off,
            R.drawable.personal_off};
    private int[] itemCheckedImage = {
            R.drawable.home_on,
            R.drawable.police_on,
            R.drawable.personal_on};

    private Fragment fragment;
    private TabHomeFragment homeFragment;
    private TabMineFragment myinfoFragment;
    private TabWorkFragment workFragment;
    private NetChangedReceiver netChangedReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        StatusBarCompat.initStatusBar(this);
        init_view();
//        registerDateTransReceiver();
    }

    /**
     * 初始化控件
     */
    private void init_view() {
        footer = (CustomRadioGroup) findViewById(R.id.crg_home);
        for (int i = 0; i < itemImage.length; i++) {
            footer.addItem(itemImage[i], itemCheckedImage[i]);
        }
        footer.setCheckedIndex(0);
        footer.setOnItemChangedListener(new FooterChange());
        setDefaultFragment();
    }

    //默认初始化fragment
    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new TabHomeFragment();
        fragment = homeFragment;
        transaction.add(R.id.fl_home, homeFragment);
        transaction.commit();
    }

    /**
     * 底部栏变化监听
     */
    class FooterChange implements CustomRadioGroup.OnItemChangedListener {

        @Override
        public void onItemChanged() {

            switch (footer.getCheckedIndex()) {
                case 0:
                    if (homeFragment == null) {
                        homeFragment = new TabHomeFragment();
                    }
                    switchContent(homeFragment);

                    break;

                case 1:
                    if (workFragment == null) {
                        workFragment = new TabWorkFragment();
                    }
                    switchContent(workFragment);
                    break;

                case 2:
                    if (myinfoFragment == null) {
                        myinfoFragment = new TabMineFragment();
                    }
                    switchContent(myinfoFragment);
                    break;

            }

        }
    }

    //隐藏显示添加fragment
    public void switchContent(Fragment f) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!f.isAdded()) {    // 先判断是否被add过
            transaction.hide(fragment).add(R.id.fl_home, f).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(fragment).show(f).commit(); // 隐藏当前的fragment，显示下一个
        }
        fragment = f;
    }

    private long exitTime = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void registerDateTransReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.setPriority(1000);
        netChangedReceiver = new NetChangedReceiver();
        registerReceiver(netChangedReceiver, filter);
    }

    class NetChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (NetUtil.getNetworkType()) {
                case 0:
                    Toast.makeText(context, "没有网络:", Toast.LENGTH_SHORT).show();
                    /**
                     * 弹出网络设置对话框
                     */
//                    setNetwork(MainActivity.this);
                    break;
                case 1:
                    Toast.makeText(context, "当前网络:WIFI", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(context, "当前网络:WAP网络", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(context, "当前网络:NET网络", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(netChangedReceiver);
    }
}
