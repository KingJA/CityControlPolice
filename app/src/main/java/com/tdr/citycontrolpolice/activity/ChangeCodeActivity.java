package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ChangeMenPai;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DetailInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.CustomConstants;
import com.tdr.citycontrolpolice.util.QRCodeUtil;
import com.tdr.citycontrolpolice.util.SharedPreferencesUtils;
import com.tdr.citycontrolpolice.util.TimeUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;
import com.tdr.citycontrolpolice.view.popupwindow.PopupReaconType;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/30 13:03
 * 修改备注：
 */
public class ChangeCodeActivity extends BackTitleActivity implements View.OnClickListener {
    private static final String HOUSE_ID = "HOUSE_ID";
    private static final int NEW_CODE = 101;
    private static final String TAG = "ChangeCodeActivity";
    private TextView mTvChangeCodeUser;
    private TextView mTvChangeCodeDate;
    private TextView mTvChangeCodeOld;
    private ImageView mIvChangeCodeNew;
    private EditText mEtChangeCodeNew;
    private ImageView mIvChangeCodeDown;
    private EditText mEtChangeCodeReasonType;
    private EditText mEtChangeCodeReason;
    private String houseId;
    private TextView mTvUpload;
    private int reasonType=9;
    private DialogConfirm dialogConfirm;
    private PopupReaconType popupReaconType;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_change_code, null);
        return view;
    }

    @Override
    public void initVariables() {
        houseId = getIntent().getStringExtra(HOUSE_ID);
    }

    @Override
    protected void initView() {
        mTvUpload = (TextView) view.findViewById(R.id.tv_upload);
        mIvChangeCodeNew = (ImageView) view.findViewById(R.id.iv_changeCode_new);
        mIvChangeCodeDown = (ImageView) view.findViewById(R.id.iv_changeCode_down);
        mTvChangeCodeUser = (TextView) view.findViewById(R.id.tv_changeCode_user);
        mTvChangeCodeDate = (TextView) view.findViewById(R.id.tv_changeCode_date);
        mTvChangeCodeOld = (TextView) view.findViewById(R.id.tv_changeCode_old);
        mEtChangeCodeNew = (EditText) view.findViewById(R.id.et_changeCode_new);
        mEtChangeCodeReasonType = (EditText) view.findViewById(R.id.et_changeCode_resonType);
        mEtChangeCodeReason = (EditText) view.findViewById(R.id.et_changeCode_reason);
        dialogConfirm = new DialogConfirm(ChangeCodeActivity.this, "登记牌更改成功", "确定");
        popupReaconType = new PopupReaconType(mEtChangeCodeReasonType, this);

    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_DETAILINFO, param)
                .setBeanType(ChuZuWu_DetailInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DetailInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_DetailInfo bean) {
                        setProgressDialog(false);
                        mTvChangeCodeOld.setText(bean.getContent().getQRMENPAI());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void initData() {
        mTvUpload.setOnClickListener(this);
        mIvChangeCodeNew.setOnClickListener(this);
        mIvChangeCodeDown.setOnClickListener(this);
        dialogConfirm.setOnConfirmClickListener(new DialogConfirm.OnConfirmClickListener() {
            @Override
            public void onConfirm() {
                finish();
            }
        });
        popupReaconType.setOnReasonTypeSelectListener(new PopupReaconType.OnReasonTypeSelectListener() {
            @Override
            public void onReasonTypeSelect(int position) {
                reasonType=position;
                switch (position) {
                    case 1:
                        mEtChangeCodeReasonType.setText("损坏");
                        break;
                    case 2:
                        mEtChangeCodeReasonType.setText("丢失");
                        break;
                    case 9:
                        mEtChangeCodeReasonType.setText("其他(请填写)");
                        break;

                }
            }
        });
    }

    @Override
    public void setData() {
        setTitle("登记牌变更");
        mTvChangeCodeDate.setText(TimeUtil.getFormatDate());
        mTvChangeCodeUser.setText((String) SharedPreferencesUtils.get("login_name", ""));
    }

    public static void goActivity(Context context, String houseId) {
        Intent intent = new Intent(context, ChangeCodeActivity.class);
        intent.putExtra(HOUSE_ID, houseId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_upload:
                upload();
                break;
            case R.id.iv_changeCode_new:
                goCaptureActivity(NEW_CODE);
                break;
            case R.id.iv_changeCode_down:
                ToastUtil.showMyToast("下拉");
                popupReaconType.showPopupWindowDown();
                break;

        }
    }

    private void goCaptureActivity(int requestCode) {
        Intent intent = new Intent();
        intent.setClass(ChangeCodeActivity.this, zbar.CaptureActivity.class);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NEW_CODE:
                if (resultCode == RESULT_OK) {
                    inquireDevice(data);
                }
                break;
        }
    }

    private void inquireDevice(Intent data) {
        String code = QRCodeUtil.inquireCzf(data);
        Log.e(TAG, "code: " + code);
        if (!TextUtils.isEmpty(code)) {
            mEtChangeCodeNew.setText(code);
        }
    }

    public void upload() {
        String reason = mEtChangeCodeReason.getText().toString().trim();
        String newCode = mEtChangeCodeNew.getText().toString().trim();
        if (!CheckUtil.checkEmpty(newCode, "请提供新的登记牌二维码")) {
            return;
        }
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("HOUSEID", houseId);
        param.put("NEWDEVICECODE", newCode);
        param.put("REASON_TYPE", reasonType);
        param.put("REASON", reason);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(this).getToken(), 0, CustomConstants.CHUZUWU_CHANGEMENPAI, param)
                .setBeanType(ChuZuWu_ChangeMenPai.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_ChangeMenPai>() {
                    @Override
                    public void onSuccess(ChuZuWu_ChangeMenPai bean) {
                        setProgressDialog(false);
                        EventBus.getDefault().post(new Object());
                        dialogConfirm.show();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }
}
