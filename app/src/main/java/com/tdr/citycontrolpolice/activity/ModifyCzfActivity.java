package com.tdr.citycontrolpolice.activity;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Modify;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.KjChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_Modify;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogConfirm;

import java.util.HashMap;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：房屋信息修改
 * 创建人：KingJA
 * 创建时间：2016/3/25 13:19
 * 修改备注：
 */
public class ModifyCzfActivity extends BackTitleActivity implements View.OnClickListener, TextWatcher {
    private static final String TAG = "ModifyCzfActivity";
    private KjChuZuWuInfo mCzfInfo = new KjChuZuWuInfo();
    private TextView tv_owner_name;
    private TextView tv_owner_phone;
    private TextView tv_owner_address;
    private EditText et_czf_name;
    private TextView tv_submit;
    private ImageView iv_edit_delete;
    private String mHouseName;
    private String mToken;
    private Param_ChuZuWu_Modify param_chuZuWu_modify;
    private String mHouseId;
    private DialogConfirm mDialogConfirm;
    private HashMap<String, Object> mParam = new HashMap<>();
    private Editable mEtext;

    @Override
    public void initVariables() {
        mCzfInfo = (KjChuZuWuInfo) getIntent().getSerializableExtra("CZF_INFO");
        mHouseId = mCzfInfo.getContent().getHOUSEID();
        mToken = UserService.getInstance(this).getToken();
        Log.i(TAG, "mCzfInfo: " + mCzfInfo.getContent().getOWNERNAME());
        param_chuZuWu_modify = new Param_ChuZuWu_Modify();
        param_chuZuWu_modify.setTaskID("1");
        param_chuZuWu_modify.setHOUSEID(mHouseId);

        mParam.put("TaskID", "1");
        mParam.put("HouseID", mHouseId);

    }

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_czf_modify, null);

        return view;
    }

    @Override
    protected void initView() {
        mDialogConfirm = new DialogConfirm(this, "出租房信息修改成功！", "确定");
        tv_owner_name = (TextView) findViewById(R.id.tv_owner_name);
        tv_owner_phone = (TextView) findViewById(R.id.tv_owner_phone);
        tv_owner_address = (TextView) findViewById(R.id.tv_owner_address);
        et_czf_name = (EditText) findViewById(R.id.et_czf_name);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        iv_edit_delete = (ImageView) findViewById(R.id.iv_edit_delete);

    }

    @Override
    public void initNet() {
        setProgressDialog(true);
        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_Info", mParam)
                .setBeanType(KjChuZuWuInfo.class)
                .setActivity(ModifyCzfActivity.this)
                .setCallBack(new WebServiceCallBack<KjChuZuWuInfo>() {
                    @Override
                    public void onSuccess(KjChuZuWuInfo bean) {
                        setProgressDialog(false);
                        tv_owner_name.setText(bean.getContent().getOWNERNAME());
                        tv_owner_phone.setText(bean.getContent().getPHONELIST().replace(",","  "));
                        tv_owner_address.setText(bean.getContent().getADDRESS());
                        et_czf_name.setText(bean.getContent().getHOUSENAME());
                        mEtext = et_czf_name.getText();
                        Selection.setSelection(mEtext, mEtext.length());
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    @Override
    public void initData() {
        tv_submit.setOnClickListener(this);
        iv_edit_delete.setOnClickListener(this);
        et_czf_name.addTextChangedListener(this);
    }

    @Override
    public void setData() {
        mDialogConfirm.setOnConfirmClickListener(new DialogConfirm.OnConfirmClickListener() {
            @Override
            public void onConfirm() {
                finish();
            }
        });
        setTitle("房屋信息修改");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_edit_delete:
                et_czf_name.setText("");
                break;
            case R.id.tv_submit:
                mHouseName = et_czf_name.getText().toString().trim();
                modifyHouseName(mHouseName);
                break;
            default:
                break;
        }
    }

    private void modifyHouseName(String houseName) {

        if (CheckUtil.checkEmpty(houseName, "请输入房间名称") && CheckUtil.checkLengthMax(houseName, 30, "房间名过长")) {
            setProgressDialog(true);
            param_chuZuWu_modify.setHOUSENAME(houseName);
            ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
            ThreadPoolTask task = builder.setGeneralParam(mToken, 0, "ChuZuWu_Modify", param_chuZuWu_modify)
                    .setBeanType(ChuZuWu_Modify.class)
                    .setActivity(ModifyCzfActivity.this)
                    .setCallBack(new WebServiceCallBack<ChuZuWu_Modify>() {
                        @Override
                        public void onSuccess(ChuZuWu_Modify bean) {
                            if (bean.getResultCode() == 0) {
                                setProgressDialog(false);
                                mDialogConfirm.show();
                                Log.i(TAG, "onSuccess: " + bean.getResultText());
                            }
                        }

                        @Override
                        public void onErrorResult(ErrorResult errorResult) {
                            setProgressDialog(false);
                        }
                    }).build();
            PoolManager.getInstance().execute(task);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        iv_edit_delete.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
    }
}
