package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.tendencynfc.TendencyReadAPI;

/**
 * Created by Linus_Xie on 2016/3/14.
 */
public class PrePersonActivity extends Activity implements Handler.Callback {

    private Context mContext;
    private Handler mHandler;

    private TendencyReadAPI mRead;

    private NfcAdapter mAdapter = null;
    private PendingIntent pi = null;
    //过滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preperson);

        mContext = this;
        mHandler = new Handler(this);

        title();
        initView();

        mRead = new TendencyReadAPI(mContext);

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            text_desc.setText("该设备不支持Nfc，无法读取身份证");
        } else {
            initNfc();
        }
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
        tv_title.setText("出租房登记");
    }

    private ZProgressHUD progressHUD;
    private TextView text_desc, text_name, text_identity, text_police, text_nation, text_sex, text_addr;

    private void initView() {
        progressHUD = new ZProgressHUD(mContext);
        progressHUD.setMessage("初始化数据");
        progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);
        // progressHUD.show();

        text_desc = (TextView) findViewById(R.id.text_desc);
        text_name = (TextView) findViewById(R.id.text_name);
        text_identity = (TextView) findViewById(R.id.text_identity);
        text_police = (TextView) findViewById(R.id.text_police);
        text_nation = (TextView) findViewById(R.id.text_nation);
        text_sex = (TextView) findViewById(R.id.text_sex);
        text_addr = (TextView) findViewById(R.id.text_add);
    }

    private void initNfc() {
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][]{new String[]{NfcB.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{MifareClassic.class.getName()}};
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        mRead.NfcReadCard(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            startNfcListener();
        }
    }

    private void startNfcListener() {
        mAdapter.enableForegroundDispatch(this, pi,
                new IntentFilter[]{tagDetected}, mTechLists);
    }

    private void stopNfcListener() {
        mAdapter.disableForegroundDispatch(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    public static final String TAGID = "tagId";//卡片ID
    public static final String NAME = "name";//姓名
    public static final String SEX = "sex";//性别，男、女
    public static final String NATION = "nation";//名族，汉....
    public static final String BIRTHDAY = "birthday";//出生年月，19900101
    public static final String ADDRESS = "address";//住址
    public static final String IDENTITY = "identity";//身份证
    public static final String POLICE = "police";//发证机关
    public static final String VALIDITY = "validity";//有效期
    public static final String DNCODE = "DNcode";//DN码

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.tdr.identity.readcard")) {// 身份证
                Bundle bundle = intent.getBundleExtra("data");
                String tt = bundle.getString("state");
                if (tt.equals("2")) {
                    Toast.makeText(mContext, "接收数据超时！", Toast.LENGTH_LONG).show();
                } else if (tt.equals("41")) {
                    Toast.makeText(mContext, "读卡失败！读身份证时，请不要移动身份证！", Toast.LENGTH_LONG).show();
                } else if (tt.equals("42")) {
                    Toast.makeText(mContext, "没有找到服务器!", Toast.LENGTH_LONG).show();
                } else if (tt.equals("43")) {
                    Toast.makeText(mContext, "服务器忙！", Toast.LENGTH_LONG).show();
                } else if (tt.equals("90")) {
                    String tagId = bundle.getString(TAGID);
                    String name = bundle.getString(NAME);
                    text_name.setText(name);
                    String sex = bundle.getString(SEX);
                    text_sex.setText(sex);
                    String nation = bundle.getString(NATION);
                    text_nation.setText(nation);
                    String birthday = bundle.getString(BIRTHDAY);
                    String address = bundle.getString(ADDRESS);
                    text_addr.setText(address);
                    String identity = bundle.getString(IDENTITY);
                    text_identity.setText(identity);
                    String police = bundle.getString(POLICE);
                    text_police.setText(police);
                    String validity = bundle.getString(VALIDITY);
                    String dncode = bundle.getString(DNCODE);
                }
            }
        }
    };
}
