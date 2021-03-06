package com.tdr.citycontrolpolice.activity;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.Converter;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.BluetoothBean;
import com.tdr.citycontrolpolice.entity.Common_IdentityCardAuthentication;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.OCR_Kj;
import com.tdr.citycontrolpolice.net.ConnectThread;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.GoUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.NetUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogBluetooth;
import com.tdr.citycontrolpolice.view.dialog.DialogDouble;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.yunmai.android.engine.OcrEngine;
import com.yunmai.android.idcard.ACamera;
import com.yunmai.android.vo.IDCard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：人员核查页面
 * 创建人：KingJA
 * 创建时间：2016/4/6 17:03
 * 修改备注：
 */
public class PersonCheckActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PersonCheckActivity";
    private ImageView iv_nfc;
    private ImageView iv_bluetooth;
    private ImageView iv_camera;
    private TextView tv_gender;
    private TextView tv_address;
    private TextView tv_birthday;
    private TextView tv_nation;
    private TextView tv_card;
    private EditText et_name;
    private TextView tv_card_no;
    private DialogBluetooth dialogBluetooth;
    private String cardNo;
    private IDCard idCard;
    private DialogProgress dialogProgress;
    private List<BluetoothBean> searchDevices;
    private List<BluetoothBean> boundDevices = new ArrayList<>();
    private TextView tv_submit;
    private String bluetoothAddress;
    private NfcAdapter mAdapter;
    private PendingIntent pi;
    private IntentFilter tagDetected;
    private IntentFilter ocrDetected;
    private String[][] mTechLists;
    private TextView tv_count;
    private RelativeLayout rl_count;
    private RelativeLayout rl_queue;
    private RelativeLayout rl_top_back_left;
    private List<OCR_Kj> ocrList;
    private DialogDouble dialogDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_person_check);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initVariables() {
        initBluetooth();
    }

    @Override
    protected void initView() {
        rl_top_back_left = (RelativeLayout) findViewById(R.id.rl_top_back_left);
        rl_queue = (RelativeLayout) findViewById(R.id.rl_queue);
        rl_count = (RelativeLayout) findViewById(R.id.rl_count);
        tv_count = (TextView) findViewById(R.id.tv_count);
        tv_card_no = (TextView) findViewById(R.id.tv_card_no);
        et_name = (EditText) findViewById(R.id.et_name);
        tv_card = (TextView) findViewById(R.id.tv_card);
        tv_nation = (TextView) findViewById(R.id.tv_nation);
        tv_birthday = (TextView) findViewById(R.id.tv_birthday);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_gender = (TextView) findViewById(R.id.tv_gender);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        iv_bluetooth = (ImageView) findViewById(R.id.iv_bluetooth);
        iv_nfc = (ImageView) findViewById(R.id.iv_nfc);
        dialogBluetooth = new DialogBluetooth(this, boundDevices);
        dialogProgress = new DialogProgress(this);
        dialogDouble = new DialogDouble(this, "是否继续进行审查", "是", "否");

    }

    private BluetoothAdapter defaultAdapter;

    /**
     * 初始化蓝牙
     */
    private void initBluetooth() {
        /**
         * 获取并打开蓝牙设备
         */
        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter == null) {
            ToastUtil.showMyToast("没有蓝牙设备");
        } else if (!defaultAdapter.enable()) {
            defaultAdapter.enable();
        }
        /**
         * 将已经配对的设备存入列表
         */
        boundDevices = getBoundDevices();


    }

    /**
     * 获取已经绑定的蓝牙设备
     *
     * @return
     */
    private List<BluetoothBean> getBoundDevices() {
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
        List<BluetoothBean> boundDevices = new ArrayList<>();
        if (bondedDevices.size() > 0) {
            for (BluetoothDevice device : bondedDevices) {
                BluetoothBean bluetoothBean = new BluetoothBean();
                bluetoothBean.setAddress(device.getAddress());
                bluetoothBean.setName(device.getName());
                boundDevices.add(bluetoothBean);
            }
            return boundDevices;
        }
        return boundDevices;
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        rl_top_back_left.setOnClickListener(this);
        rl_queue.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        iv_nfc.setOnClickListener(this);
        iv_bluetooth.setOnClickListener(this);
        dialogDouble.setOnDoubleClickListener(new DialogDouble.OnDoubleClickListener() {
            @Override
            public void onLeft() {
                reset();
            }

            @Override
            public void onRight() {
                finish();
            }
        });
        dialogBluetooth.setOnBuletoothListener(new DialogBluetooth.OnBuletoothListener() {

            @Override
            public void onBluetoothItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO 记住选择的蓝牙设备

                BluetoothBean bean = (BluetoothBean) parent.getItemAtPosition(position);
                bluetoothAddress = bean.getAddress();
                SharedPreferencesUtils.put("BLUETOOTH", bluetoothAddress);
                if (defaultAdapter.isDiscovering()) {
                    defaultAdapter.cancelDiscovery();
                }
                startBluetooth(bean.getAddress());
            }

            @Override
            public void onScan() {
                if (defaultAdapter.isDiscovering()) {
                    defaultAdapter.cancelDiscovery();
                }
                searchDevices = getBoundDevices();
                dialogBluetooth.refresh(searchDevices);
                defaultAdapter.startDiscovery();
                ToastUtil.showMyToast("正在查找周围的蓝牙设备");
            }
        });

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            ToastUtil.showMyToast("当前设备不支持nfc");
        } else {
            initNfc();
        }

    }

    private void reset() {
        et_name.setText("");
        tv_gender.setText("");
        tv_nation.setText("");
        tv_birthday.setText("");
        tv_address.setText("");
        tv_card.setText("");
        tv_card_no.setText("");
    }

    private void initNfc() {
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        ocrDetected = new IntentFilter("com.android.activity.OCR");
        ocrDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][]{new String[]{NfcB.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{MifareClassic.class.getName()}};
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAdapter != null) {
            stopNfcListener();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null)
            startNfcListener();
    }

    private void startNfcListener() {
        mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected, ocrDetected}, mTechLists);
    }

    private void stopNfcListener() {
        mAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String tagId = Converter.bytesToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)).toUpperCase();
        Log.i(TAG, "tagId: " + tagId);
        if (tagId.length() != 16) {
            ToastUtil.showMyToast("请使用身份证刷卡");
            return;
        }
        tv_card_no.setText(tagId);

    }

    /**
     * 开启配对蓝牙
     *
     * @param bluetoothAddress
     */
    private void startBluetooth(String bluetoothAddress) {
        if (bluetoothDevice == null) {
            bluetoothDevice = defaultAdapter.getRemoteDevice(bluetoothAddress);
        }
        startConnect();
    }

    @Override
    public void setData() {

        setQueueSize();
    }

    private void setQueueSize() {
        ocrList = DbDaoXutils3.getInstance().selectAll(OCR_Kj.class);
        Log.e(TAG, "setQueueSize: " + ocrList.size());
        rl_count.setVisibility(ocrList.size() > 0 ? View.VISIBLE : View.GONE);
        tv_count.setText(ocrList.size() > 99 ? "..." : String.valueOf(ocrList.size()));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        switch (requestCode) {
            case 100:
                dialogProgress.show();
                final Intent result = data;

                new Thread() {
                    @Override
                    public void run() {
                        OcrEngine ocrEngine = new OcrEngine();
                        try {
                            byte[] bytes = result.getByteArrayExtra("idcardA");

                            String headPath = "";
                            idCard = ocrEngine.recognize(PersonCheckActivity.this, bytes, null, headPath);
                            if (idCard.getRecogStatus() == OcrEngine.RECOG_OK) {
                                dialogProgress.dismiss();
                                mOcrHandler.sendMessage(mOcrHandler.obtainMessage(OcrEngine.RECOG_OK, headPath));
                            } else {
                                dialogProgress.dismiss();
                                mOcrHandler.sendEmptyMessage(idCard.getRecogStatus());
                            }
                        } catch (Exception e) {
                            dialogProgress.dismiss();
                            mOcrHandler.sendEmptyMessage(OcrEngine.RECOG_FAIL);
                        }
                    }
                }.start();
                break;
        }
    }

    public static final int REQUEST_CODE_RECOG = 113;  //	识别
    public static final int RESULT_RECOG_SUCCESS = 103;//识别成功
    public static final int RESULT_RECOG_FAILED = 104;//识别失败
    private Handler mOcrHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    cardNo = (String) msg.obj;
                    tv_card_no.setText(cardNo);
                    stopConnectThread();
                    break;
                case OcrEngine.RECOG_FAIL:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_BLUR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_OK:
                    Log.i(TAG, "handleMessage: " + idCard.toString());
                    setCardInfo(idCard);
                    break;
                case OcrEngine.RECOG_IMEI_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_imei, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_FAIL_CDMA:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_cdma, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_LICENSE_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_licens, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_TIME_OUT:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_time_out, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_ENGINE_INIT_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_engine_init, Toast.LENGTH_SHORT).show();
                    break;
                case OcrEngine.RECOG_COPY:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_fail_copy, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    /**
     * 在布局显示身份证信息
     *
     * @param idCard
     */
    private void setCardInfo(IDCard idCard) {
        et_name.setText(idCard.getName());
        tv_card.setText(idCard.getCardNo());
        tv_nation.setText(idCard.getEthnicity());
        tv_birthday.setText(idCard.getBirth());
        tv_address.setText(idCard.getAddress());
        tv_gender.setText(idCard.getSex());
    }


    /**
     * 退出界面时，如果在搜索停止搜索，停止接收信号输入，解绑广播
     */
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (defaultAdapter != null && defaultAdapter.isDiscovering()) {
            defaultAdapter.cancelDiscovery();
        }

        stopConnectThread();

        super.onDestroy();
    }

    private void stopConnectThread() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
    }

    private BluetoothDevice bluetoothDevice;
    private final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";//蓝牙模式串口服务
    private ConnectThread connectThread;

    /**
     * 开启连接线程
     */
    private void startConnect() {
        stopConnectThread();
        connectThread = new ConnectThread(bluetoothDevice, defaultAdapter, SPP_UUID, mOcrHandler);
        connectThread.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top_back_left:
                finish();
                break;
            case R.id.rl_queue:
                GoUtil.goActivity(this, QueueActivity.class);
                break;
            case R.id.iv_nfc:
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.iv_camera:
                GoUtil.goActivityForResult(PersonCheckActivity.this, ACamera.class, 100);
                break;
            case R.id.iv_bluetooth:
                bluetoothAddress = (String) SharedPreferencesUtils.get("BLUETOOTH", "");
                if (!TextUtils.isEmpty(bluetoothAddress)) {
                    ToastUtil.showMyToast("请在收到刷卡提示后开始刷卡");
                    startBluetooth(bluetoothAddress);
                } else {
                    dialogBluetooth.show();
                }
                break;
            default:
                break;

        }
    }

    /**
     * 上传身份证信息
     */
    private void submit() {
        String name = et_name.getText().toString().trim();
        String gender = tv_gender.getText().toString().trim();
        String nation = tv_nation.getText().toString().trim();
        String birthday = tv_birthday.getText().toString().trim();
        String address = tv_address.getText().toString().trim();
        String cardNO = tv_card.getText().toString().trim();
        String cardID = tv_card_no.getText().toString().trim();
        if (CheckUtil.checkEmpty(cardID, "请通过蓝牙或NFC获取身份证卡号") && CheckUtil.checkEmpty(cardNO, "身份证号获取错误，请重新获取")
                && CheckUtil.checkEmpty(address, "地址获取错误，请重新获取")
                && CheckUtil.checkBirthday(birthday, "出生日期获取错误，请重新获取")
                && CheckUtil.checkEmpty(nation, "名族获取错误，请重新获取")
                && CheckUtil.checkEmpty(gender, "性别获取错误，请重新获取")
                && CheckUtil.checkEmpty(name, "姓名获取错误，请重新获取")) {
            if (!NetUtil.netAvailable()) {
                ToastUtil.showMyToast("当前网络不可用，信息暂存在队列");
                saveToDb(name, gender, nation, birthday, address, cardNO, cardID);
                dialogDouble.show();
                return;
            }
            uploadToService(name, gender, nation, birthday, address, cardNO, cardID);
        }
    }

    private void uploadToService(String name, String gender, String nation, String birthday, String address, String cardNO, String cardID) {
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("NAME", name);
        param.put("SEX", gender);
        param.put("NATION", nation);
        param.put("BIRTHDAY", birthday);
        param.put("ADDRESS", address);
        param.put("IDENTITYCARD", cardNO);
        param.put("IDENTITYCARDID", cardID);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(PersonCheckActivity.this).getToken(), 0, "Common_IdentityCardAuthentication", param)
                .setBeanType(Common_IdentityCardAuthentication.class)
                .setActivity(PersonCheckActivity.this)
                .setCallBack(new WebServiceCallBack<Common_IdentityCardAuthentication>() {
                    @Override
                    public void onSuccess(Common_IdentityCardAuthentication bean) {
                        ToastUtil.showMyToast("完成审核");
                        dialogDouble.show();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {

                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    private void saveToDb(String name, String gender, String nation, String birthday, String address, String cardNO, String cardID) {
        OCR_Kj ocr = new OCR_Kj();
        ocr.setTaskID("1");
        ocr.setNAME(name);
        ocr.setSEX(gender);
        ocr.setNATION(nation);
        ocr.setBIRTHDAY(birthday);
        ocr.setADDRESS(address);
        ocr.setIDENTITYCARD(cardNO);
        ocr.setIDENTITYCARDID(cardID);
        Log.e(TAG, ocr.toString());
        DbDaoXutils3.getInstance().saveOrUpdate(ocr);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setQueueSize();
                dialogDouble.show();
            }
        }, 200);
    }

    @Subscribe
    public void onEventMainThread(Object obj) {
        setQueueSize();
    }
}
