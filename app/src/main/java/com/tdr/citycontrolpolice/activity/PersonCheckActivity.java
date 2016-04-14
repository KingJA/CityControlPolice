package com.tdr.citycontrolpolice.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.BluetoothBean;
import com.tdr.citycontrolpolice.net.ConnectThread;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.view.dialog.DialogBluetooth;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;
import com.yunmai.android.engine.OcrEngine;
import com.yunmai.android.idcard.ACamera;
import com.yunmai.android.vo.IDCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/6 17:03
 * 修改备注：
 */
public class PersonCheckActivity extends BackTitleActivity {

    private static final String TAG = "PersonCheckActivity";
    private ImageView iv_bluetooth;
    private ImageView iv_camera;
    private TextView tv_gender;
    private TextView tv_address;
    private TextView tv_birthday;
    private TextView tv_nation;
    private TextView tv_card;
    private TextView tv_name;
    private TextView tv_card_no;
    private DialogBluetooth dialogBluetooth;
    private String cardNo;
    private IDCard idCard;
    private DialogProgress dialogProgress;
    private List<BluetoothBean> searchDevices;
    private List<BluetoothBean> boundDevices = new ArrayList<>();

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_person_check, null);
        return view;
    }

    @Override
    public void initVariables() {
        initBluetooth();
    }

    @Override
    protected void initView() {
        tv_card_no = (TextView) view.findViewById(R.id.tv_card_no);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_card = (TextView) view.findViewById(R.id.tv_card);
        tv_nation = (TextView) view.findViewById(R.id.tv_nation);
        tv_birthday = (TextView) view.findViewById(R.id.tv_birthday);
        tv_address = (TextView) view.findViewById(R.id.tv_address);
        tv_gender = (TextView) view.findViewById(R.id.tv_gender);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        iv_bluetooth = (ImageView) view.findViewById(R.id.iv_bluetooth);
        dialogBluetooth = new DialogBluetooth(this, boundDevices);
        dialogProgress = new DialogProgress(this);

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

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(receiver, intentFilter);
        intentFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(receiver, intentFilter);

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
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.goActivityForResult(PersonCheckActivity.this, ACamera.class, 100);
            }
        });
        iv_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBluetooth.show();
            }
        });
        dialogBluetooth.setOnBuletoothListener(new DialogBluetooth.OnBuletoothListener() {

            @Override
            public void onBluetoothItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothBean bean = (BluetoothBean) parent.getItemAtPosition(position);
                if (defaultAdapter.isDiscovering()) {
                    defaultAdapter.cancelDiscovery();
                }
                if (bluetoothDevice == null) {
                    bluetoothDevice = defaultAdapter.getRemoteDevice(bean.getAddress());
                    startConnect();
                }
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
    }

    @Override
    public void setData() {
        setTitle("人员核查");
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
                                mOcrHandler.sendMessage(mOcrHandler.obtainMessage(OcrEngine.RECOG_OK, headPath));
                            } else {
                                mOcrHandler.sendEmptyMessage(idCard.getRecogStatus());
                            }
                        } catch (Exception e) {
                            mOcrHandler.sendEmptyMessage(OcrEngine.RECOG_FAIL);
                        }
                    }
                }.start();
                break;
        }
    }

    public static final int REQUEST_CODE_RECOG = 113;            //	识别

    /**
     * 识别成功
     */
    public static final int RESULT_RECOG_SUCCESS = 103;

    /**
     * 识别失败
     */
    public static final int RESULT_RECOG_FAILED = 104;
    private Handler mOcrHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    cardNo = (String) msg.obj;
                    tv_card_no.setText(cardNo);
                    startConnect();
                    break;
                case OcrEngine.RECOG_FAIL:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_BLUR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_OK:
                    Log.i(TAG, "handleMessage: " + idCard.toString());
                    dialogProgress.dismiss();
                    setCardInfo(idCard);
                    break;
                case OcrEngine.RECOG_IMEI_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_imei, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_FAIL_CDMA:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_cdma, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_LICENSE_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_licens, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_TIME_OUT:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_time_out, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_ENGINE_INIT_ERROR:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_engine_init, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOG_COPY:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_fail_copy, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                default:
                    Toast.makeText(PersonCheckActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
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
        tv_name.setText(idCard.getName());
        tv_card.setText(idCard.getCardNo());
        tv_nation.setText(idCard.getEthnicity());
        tv_birthday.setText(idCard.getBirth());
        tv_address.setText(idCard.getAddress());
        tv_gender.setText(idCard.getSex());
    }

    /**
     * 蓝牙搜索广播
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    BluetoothBean bluetoothBean = new BluetoothBean();
                    bluetoothBean.setAddress(device.getAddress());
                    bluetoothBean.setName(device.getName());
                    searchDevices.add(bluetoothBean);
                    mOcrHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            dialogBluetooth.refresh(searchDevices);

                        }
                    });
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                ToastUtil.showMyToast("搜索完毕！");
            }

        }
    };

    /**
     * 退出界面时，如果在搜索停止搜索，停止接收信号输入，解绑广播
     */
    @Override
    protected void onDestroy() {
        if (defaultAdapter != null && defaultAdapter.isDiscovering()) {
            defaultAdapter.cancelDiscovery();
        }

        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private BluetoothDevice bluetoothDevice;
    private final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";//蓝牙模式串口服务
    private ConnectThread connectThread;

    /**
     * 开启连接线程
     */
    private void startConnect() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }
        connectThread = new ConnectThread(bluetoothDevice, defaultAdapter, SPP_UUID, mOcrHandler);
        connectThread.start();
    }


}
