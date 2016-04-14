package com.tdr.citycontrolpolice.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.Common_AddDevice;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.Param_Common_AddDevice;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.UUID;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/7 16:42
 * 修改备注：
 */
public class BindingDeviceActivity extends BackTitleActivity {

    private static final String TAG = "BindingDeviceActivity";
    private int deviceType;
    private int deviceNO;
    private String houseId;
    private String roomId;
    private int roomNo;
    private EditText et_device_name;
    private TextView tv_device_room;
    private TextView tv_device_type;
    private TextView tv_device_submit;
    private ImageView iv_device_icon;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_binding_device, null);
        return view;
    }

    @Override
    public void initVariables() {
        deviceType = getIntent().getIntExtra("DEVICE_TYPE", 888);
        deviceNO = getIntent().getIntExtra("DEVICE_NO", 888);
        houseId = getIntent().getStringExtra("HOUSE_ID");
        roomId = getIntent().getStringExtra("ROOM_ID");
        roomNo = getIntent().getIntExtra("ROOM_NO", 888);
    }

    @Override
    protected void initView() {
        et_device_name = (EditText) view.findViewById(R.id.et_device_name);
        tv_device_room = (TextView) view.findViewById(R.id.tv_device_room);
        tv_device_type = (TextView) view.findViewById(R.id.tv_device_type);
        tv_device_submit = (TextView) view.findViewById(R.id.tv_device_submit);
        iv_device_icon = (ImageView) view.findViewById(R.id.iv_device_icon);
    }

    @Override
    public void initNet() {


    }

    private void bindDevice() {
        String deviceName = et_device_name.getText().toString().trim();
        if (CheckUtil.checkEmpty(deviceName, "请输入设备名称")) {
            Param_Common_AddDevice param = new Param_Common_AddDevice();
            param.setTaskID("1");
            param.setDEVICEID(MyUtil.getUUID());
            param.setDEVICETYPE(deviceType);
            param.setDEVICECODE(deviceNO);
            param.setDEVICENAME(deviceName);
            param.setOTHERTYPE(2);
            param.setOTHERID(houseId);
            param.setROOMID(roomId);
            param.setPHOTOCOUNT(0);
            ThreadPoolTask.Builder<Common_AddDevice> builder = new ThreadPoolTask.Builder<Common_AddDevice>();
            ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Common_AddDevice", param)
                    .setBeanType(Common_AddDevice.class)
                    .setActivity(BindingDeviceActivity.this)
                    .setCallBack(new WebServiceCallBack<Common_AddDevice>() {
                        @Override
                        public void onSuccess(Common_AddDevice bean) {
                            if (bean.getResultCode() == 0) {
                                ToastUtil.showMyToast("绑定设备成功！");
                                finish();
                            }
                        }

                        @Override
                        public void onErrorResult(ErrorResult errorResult) {

                        }
                    }).build();
            PoolManager.getInstance().execute(task);
        }

    }

    @Override
    public void initData() {
        iv_device_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMyToast("拍照");
            }
        });
        tv_device_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindDevice();
            }
        });
    }

    @Override
    public void setData() {
        setTitle("设备绑定");
        tv_device_room.setText(roomNo + "");
        Basic_Dictionary_Kj bean = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst(Basic_Dictionary_Kj.class, "COLUMNCODE", "DEVICETYPE", "COLUMNVALUE", deviceType + "");
        tv_device_type.setText(bean.getCOLUMNCOMMENT());
    }
}
