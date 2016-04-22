package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.BaseFragmentPagerAdapter;
import com.tdr.citycontrolpolice.czffragment.DeclareFragment;
import com.tdr.citycontrolpolice.czffragment.ManagerFragment;
import com.tdr.citycontrolpolice.czffragment.SystemFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;
import com.tdr.citycontrolpolice.util.DipPx;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebUtil;
import com.tdr.citycontrolpolice.view.SimpleIndicatorLayout;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.citycontrolpolice.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Administrator on 2016/3/19.
 */
public class CzfInfoActivity extends FragmentActivity {

    private static final String TAG = "CzfInfoActivity";
    private ImageView img_labdlord_icon, img_menu, img_cursor;

    private TextView tv_labdlord_name, tv_labdlord_phone, tv_labdlord_add;
    private TextView tv_czf_manager, tv_declare, tv_system;
    private List<String> mTitleList = Arrays.asList("出租房管理", "自助申报", "流动人口");
    private final static int HOUSERINFO = 1001;
    private final static int ROOMADD = 2001;
    private final static int REPORTINFO = 3001;
    private final static int ERROR = 4001;
    private ChuZuWuInfo chuZuWuInfo;
    private ZProgressHUD progressHUD;
    private String houseId;
    private Gson gson = new Gson();
    private Context mContext;
    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;
    private List<Fragment> fragmentList = new ArrayList<>();
    private boolean isFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czf_info);
        mContext = this;
        title();
        init_view();
        houseId = getIntent().getStringExtra("HouseID");
        SimpleIndicatorLayout sil_czf_info = (SimpleIndicatorLayout) findViewById(R.id.sil_czf_info);
        ViewPager vp_czf_info = (ViewPager) findViewById(R.id.vp_czf_info);
        fragmentList.add(ManagerFragment.newInstance(houseId));
        fragmentList.add(new DeclareFragment());
        fragmentList.add(new SystemFragment());
        vp_czf_info.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        sil_czf_info.setTitles(mTitleList);
        sil_czf_info.setUpWithViewPager(vp_czf_info, 0);


    }

    private void title() {
        ImageView img_title = (ImageView) findViewById(R.id.image_back);
        img_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("出租房信息");

        ImageView img_add = (ImageView) findViewById(R.id.image_add);
        img_add.setBackgroundResource(R.mipmap.header_menu_on);
        img_add.setVisibility(View.VISIBLE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_top(v);
            }
        });
    }

    //初始化控件并设置监听
    private void init_view() {
        img_labdlord_icon = (ImageView) findViewById(R.id.img_labdlord_icon);
        tv_labdlord_name = (TextView) findViewById(R.id.tv_labdlord_name);
        tv_labdlord_phone = (TextView) findViewById(R.id.tv_labdlord_phone);
        tv_labdlord_add = (TextView) findViewById(R.id.tv_labdlord_add);
        houseId = getIntent().getStringExtra("HouseID");
        gethouseInfo();
    }

    /**
     * 请求获取出租房信息
     */
    private void gethouseInfo() {

        final JSONObject object = new JSONObject();
        try {
            object.put("TaskID", "1");
            object.put("HouseID", houseId);

            String token = UserService.getInstance(mContext).getToken();
            int encryption = 0;
            String dataTypeCode = "ChuZuWu_Info";
            String content = object.toString();

            WebUtil webUtil = new WebUtil(mHandler);
            webUtil.send(token, encryption, dataTypeCode, content, HOUSERINFO);


        } catch (Exception e) {

        }
    }

//    class MyPagerAdapter extends FragmentPagerAdapter {
//
//        private List<Fragment> list;
//
//        public MyPagerAdapter(FragmentManager fm, List<Fragment> list) {
//            super(fm);
//            this.list = list;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//    }


    //设置添加店铺
    private void pop_top(View v) {
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.pop_room_houseinfo, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //必须设置背景不然无法dismiss
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.transparent));
        //设置其他地方变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        LinearLayout ll_modify = (LinearLayout) contentView.findViewById(R.id.ll_pop_room_inside_modify);
        ll_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(HouseInfoActivity.this,RoomModifyActivity.class);
                //intent.putExtra("ROOMID",roomId);
                //startActivity(intent);
            }
        });

        LinearLayout ll_report = (LinearLayout) contentView.findViewById(R.id.ll_pop_room_houseinfo_report);
        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing()) {
                    dialogShow(0, "确认进行出租房设备报装？");
                }

            }
        });
        popupWindow.showAtLocation(v, Gravity.TOP, 0, DipPx.dip2px(mContext, 50));
    }


    public void dialogShow(int flag, String message) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 0) {// 确定提交数据
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#ffffffff")
                    .withMessage(message).isCancelableOnTouchOutside(false)
                    .withEffect(effectstype).withButton1Text("确认")
                    .setCustomView(R.layout.custom_view, mContext)
                    .withButton2Text("取消")
                    .withButtonDrawable(R.drawable.dialog_btn_change)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();

                            progressHUD.show();
//                            equipReport();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).show();
        }

    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HOUSERINFO:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        Log.i(TAG, "handleMessage: ");
                        chuZuWuInfo = gson.fromJson(data, new TypeToken<ChuZuWuInfo>() {
                        }.getType());
                        tv_labdlord_name.setText("房东姓名:" + chuZuWuInfo.getOWNERNAME());
                        tv_labdlord_phone.setText("手机号码:" + chuZuWuInfo.getPHONE());
                        tv_labdlord_add.setText("地址:" + chuZuWuInfo.getADDRESS());

                    }
                    break;


                case 1:
                    Toast.makeText(mContext, "网络异常", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


}
