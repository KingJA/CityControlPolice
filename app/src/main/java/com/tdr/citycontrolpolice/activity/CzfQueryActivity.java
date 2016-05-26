package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfQueryAdapter;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SearchInfoByStandardAddr;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.view.dialog.DialogProgress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/13 9:58
 * 修改备注：
 */
public class CzfQueryActivity extends Activity implements TextWatcher, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "CzfQueryActivity";
    private ImageView ivSearch;
    private ImageView ivClear;
    private EditText etQuery;
    private SwipeRefreshLayout srl;
    private ListView lv;
    private Button btnQuery;
    private String queryAddress;
    private List<Basic_StandardAddressCodeByKey_Kj.ContentBean> addressList = new ArrayList<>();
    private CzfQueryAdapter czfQueryAdapter;
    private DialogProgress dialogProgress;
    private InputMethodManager inputManager;
    private String geocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czf_query);
        initView();
        initData();

    }

    protected void initView() {
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
        etQuery = (EditText) findViewById(R.id.et_query);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        lv = (ListView) findViewById(R.id.lv);
        btnQuery = (Button) findViewById(R.id.btn_query);
        czfQueryAdapter = new CzfQueryAdapter(this, addressList);
        dialogProgress = new DialogProgress(this);
        srl.setColorSchemeResources(R.color.bg_blue_solid);
        srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }


    public void initData() {
        dialogProgress = new DialogProgress(this);
        srl.setOnRefreshListener(this);
        etQuery.addTextChangedListener(this);
        ivSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        lv.setAdapter(czfQueryAdapter);
        lv.setOnItemClickListener(this);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        ivClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        queryAddress = etQuery.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_clear:
                etQuery.setText("");
                break;
            case R.id.iv_search:
                if (CheckUtil.checkEmpty(queryAddress, "请输入搜索地址")) {
                    serach(queryAddress);
                }
                break;
            case R.id.btn_query:
                if (CheckUtil.checkEmpty(geocode, "请先搜索并选择地址")) {
                    submit(geocode);
                }
                break;
        }
    }

    /**
     * 根据字符串检索
     *
     * @param queryAddress
     */
    @SuppressWarnings("unchecked ")
    private void serach(String queryAddress) {
        hideInput();
        srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "30");
        param.put("PageIndex", "0");
        param.put("KEY", queryAddress);

        ThreadPoolTask.Builder<Basic_StandardAddressCodeByKey_Kj> builder = new ThreadPoolTask.Builder<Basic_StandardAddressCodeByKey_Kj>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Basic_StandardAddressCodeByKey", param)
                .setActivity(CzfQueryActivity.this)
                .setBeanType(Basic_StandardAddressCodeByKey_Kj.class)
                .setCallBack(searchCallBack).build();
        PoolManager.getInstance().execute(task);

    }

    /**
     * 根据三实有标准地址获取houseId
     *
     * @param geocode
     */
    private void submit(String geocode) {
        dialogProgress.show();
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("STANDARDADDRCODE", geocode);

        ThreadPoolTask.Builder<ChuZuWu_SearchInfoByStandardAddr> builder = new ThreadPoolTask.Builder<ChuZuWu_SearchInfoByStandardAddr>();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_SearchInfoByStandardAddr", param)
                .setActivity(CzfQueryActivity.this)
                .setBeanType(ChuZuWu_SearchInfoByStandardAddr.class)
                .setCallBack(getHouseIdCallBack).build();
        PoolManager.getInstance().execute(task);
    }

    private WebServiceCallBack<Basic_StandardAddressCodeByKey_Kj> searchCallBack = new WebServiceCallBack<Basic_StandardAddressCodeByKey_Kj>() {
        @Override
        public void onSuccess(Basic_StandardAddressCodeByKey_Kj bean) {
            srl.setRefreshing(false);
            addressList = bean.getContent();
            Log.i(TAG, "addressList: " + addressList.size());
            if (addressList.size() == 0) {
                ToastUtil.showMyToast("无搜索结果，请核对地址");
            }
            czfQueryAdapter.setData(addressList);
        }

        @Override
        public void onErrorResult(ErrorResult errorResult) {
            srl.setRefreshing(false);
        }
    };

    private WebServiceCallBack<ChuZuWu_SearchInfoByStandardAddr> getHouseIdCallBack = new WebServiceCallBack<ChuZuWu_SearchInfoByStandardAddr>() {
        @Override
        public void onSuccess(ChuZuWu_SearchInfoByStandardAddr bean) {
            dialogProgress.dismiss();
            finish();
            CzfInfoActivity.goActivity(CzfQueryActivity.this, bean.getContent().getHOUSEID());
        }

        @Override
        public void onErrorResult(ErrorResult errorResult) {
            dialogProgress.dismiss();

        }
    };

    /**
     * 隐藏软键盘
     */
    private void hideInput() {
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basic_StandardAddressCodeByKey_Kj.ContentBean bean = (Basic_StandardAddressCodeByKey_Kj.ContentBean) parent.getItemAtPosition(position);
        geocode = bean.getGeocode();
        submit(geocode);
    }
}
