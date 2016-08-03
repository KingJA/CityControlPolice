package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.adapter.CzfHistoryAdapter;
import com.tdr.citycontrolpolice.adapter.CzfQueryAdapter;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SearchInfoByStandardAddr;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.SQL_Query;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.ActivityManager;
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
public class CzfQueryActivity extends BaseActivity implements TextWatcher, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    private static final String TAG = "CzfQueryActivity";
    private ImageView ivSearch;
    private ImageView ivClear;
    private EditText etQuery;
    private SwipeRefreshLayout srl;
    private ListView lv;
    private ListView lv_history;
    private Button btnQuery;
    private String queryAddress;
    private List<Basic_StandardAddressCodeByKey_Kj.ContentBean> addressList = new ArrayList<>();
    private CzfQueryAdapter czfQueryAdapter;
    private DialogProgress dialogProgress;
    private InputMethodManager inputManager;
    private String geocode;
    private LinearLayout ll_history;
    private TextView tv_clearHistory;
    private List<SQL_Query> history;
    private CzfHistoryAdapter czfHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_czf_query);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initVariables() {


    }

    @Override
    protected void initView() {
        tv_clearHistory = (TextView) findViewById(R.id.tv_clearHistory);
        ivSearch = (ImageView) findViewById(R.id.iv_search);
        ll_history = (LinearLayout) findViewById(R.id.ll_history);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
        etQuery = (EditText) findViewById(R.id.et_query);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        lv = (ListView) findViewById(R.id.lv);
        lv_history = (ListView) findViewById(R.id.lv_history);
        btnQuery = (Button) findViewById(R.id.btn_query);
        czfQueryAdapter = new CzfQueryAdapter(this, addressList);
        dialogProgress = new DialogProgress(this);
        srl.setColorSchemeResources(R.color.bg_blue_solid);
        srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {
        dialogProgress = new DialogProgress(this);
        srl.setOnRefreshListener(this);
        etQuery.addTextChangedListener(this);
        ivSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        tv_clearHistory.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        lv.setAdapter(czfQueryAdapter);
        lv.setOnItemClickListener(this);
        etQuery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    initHistory();
                }
                return false;
            }
        });
    }

    @Override
    public void setData() {
        initHistory();

    }

    /**
     * 初始化历史搜索记录
     */
    private void initHistory() {
        history = DbDaoXutils3.getInstance().selectAllAndOrder(SQL_Query.class,"date");
        ll_history.setVisibility(View.VISIBLE);
        tv_clearHistory.setVisibility(history.size()>0?View.VISIBLE:View.GONE);
        czfHistoryAdapter = new CzfHistoryAdapter(this, history);
        lv_history.setAdapter(czfHistoryAdapter);
        lv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ll_history.setVisibility(View.GONE);
                SQL_Query keyWord = (SQL_Query) parent.getItemAtPosition(position);
                etQuery.setText(keyWord.getKeyWord());
                Selection.setSelection(etQuery.getText(), etQuery.getText().length());
                serach(keyWord.getKeyWord());
            }
        });
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
            case R.id.tv_clearHistory:
                DbDaoXutils3.getInstance().deleteAll(SQL_Query.class);
                initHistory();
                break;
        }
    }

    /**
     * 根据字符串检索
     *
     * @param queryAddress
     */
    @SuppressWarnings("unchecked ")
    private void serach(final String queryAddress) {
        hideInput();
        srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "30");
        param.put("PageIndex", "0");
        param.put("KEY", queryAddress);

        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "Basic_StandardAddressCodeByKey", param)
                .setActivity(CzfQueryActivity.this)
                .setBeanType(Basic_StandardAddressCodeByKey_Kj.class)
                .setCallBack(new WebServiceCallBack<Basic_StandardAddressCodeByKey_Kj>() {
                    @Override
                    public void onSuccess(Basic_StandardAddressCodeByKey_Kj bean) {
                        ll_history.setVisibility(View.GONE);
                        srl.setRefreshing(false);
                        addressList = bean.getContent();
                        Log.i(TAG, "addressList: " + addressList.size());
                        if (addressList.size() == 0) {
                            ToastUtil.showMyToast("无搜索结果，请核对地址");
                        }
                        czfQueryAdapter.setData(addressList);
                        SQL_Query sql_query = new SQL_Query();
                        sql_query.setKeyWord(queryAddress);
                        sql_query.setDate(System.currentTimeMillis());
                        DbDaoXutils3.getInstance().saveOrUpdate(sql_query);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        srl.setRefreshing(false);
                    }
                }).build();
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

        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(this).getToken(), 0, "ChuZuWu_SearchInfoByStandardAddr", param)
                .setActivity(CzfQueryActivity.this)
                .setBeanType(ChuZuWu_SearchInfoByStandardAddr.class)
                .setCallBack(getHouseIdCallBack).build();
        PoolManager.getInstance().execute(task);
    }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
    }
}
