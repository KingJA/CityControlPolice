package com.tdr.citycontrolpolice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.CzfInfoActivity;
import com.tdr.citycontrolpolice.adapter.StandardCzfHistoryAdapter;
import com.tdr.citycontrolpolice.adapter.SandardCzfQueryAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_SearchInfoByStandardAddr;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.SQL_Query;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.AppUtil;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2018/3/31 9:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StandardAddressQueryFragment extends KjBaseFragment implements TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {
    @BindView(R.id.et_query)
    EditText etQuery;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_clearHistory)
    TextView tvClearHistory;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.lv_history)
    ListView lvHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.btn_query)
    Button btnQuery;
    Unbinder unbinder;
    private List<Basic_StandardAddressCodeByKey_Kj.ContentBean> addressList = new ArrayList<>();
    private SandardCzfQueryAdapter sandardCzfQueryAdapter;
    private List<SQL_Query> history;
    private StandardCzfHistoryAdapter standardCzfHistoryAdapter;
    private String queryAddress;
    private String geocode;

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_standard_address_query, container, false);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {

    }

    @Override
    protected void initFragmentView() {
        sandardCzfQueryAdapter = new SandardCzfQueryAdapter(getActivity(), addressList);
        srl.setColorSchemeResources(R.color.bg_blue_solid);
        srl.setProgressViewOffset(false, 0, AppUtil.dp2px(48));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initFragmentNet() {

    }

    @Override
    protected void initFragmentData() {
        etQuery.addTextChangedListener(this);
        tvSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        tvClearHistory.setOnClickListener(this);
        btnQuery.setOnClickListener(this);
        lv.setAdapter(sandardCzfQueryAdapter);
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
    protected void setFragmentData() {
        initHistory();
    }

    /**
     * 初始化历史搜索记录
     */
    private void initHistory() {
        history = DbDaoXutils3.getInstance().selectAllAndOrder(SQL_Query.class, "date");
        llHistory.setVisibility(View.VISIBLE);
        tvClearHistory.setVisibility(history.size() > 0 ? View.VISIBLE : View.GONE);
        standardCzfHistoryAdapter = new StandardCzfHistoryAdapter(getActivity(), history);
        lvHistory.setAdapter(standardCzfHistoryAdapter);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvHistory.setVisibility(View.GONE);
                SQL_Query keyWord = (SQL_Query) parent.getItemAtPosition(position);
                etQuery.setText(keyWord.getKeyWord());
                Selection.setSelection(etQuery.getText(), etQuery.getText().length());
                serach(keyWord.getKeyWord());
            }
        });
    }

    private void hideInput() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager
                .HIDE_NOT_ALWAYS);
    }

    private void serach(final String queryAddress) {
        hideInput();
        srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "30");
        param.put("PageIndex", "0");
        param.put("KEY", queryAddress);

        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0,
                "Basic_StandardAddressCodeByKey", param)
                .setActivity(getActivity())
                .setBeanType(Basic_StandardAddressCodeByKey_Kj.class)
                .setCallBack(new WebServiceCallBack<Basic_StandardAddressCodeByKey_Kj>() {
                    @Override
                    public void onSuccess(Basic_StandardAddressCodeByKey_Kj bean) {
                        llHistory.setVisibility(View.GONE);
                        srl.setRefreshing(false);
                        addressList = bean.getContent();
                        Log.i(TAG, "addressList: " + addressList.size());
                        if (addressList.size() == 0) {
                            ToastUtil.showMyToast("无搜索结果，请核对地址");
                        }
                        sandardCzfQueryAdapter.setData(addressList);
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


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        queryAddress = etQuery.getText().toString().trim();
        switch (v.getId()) {
            case R.id.iv_clear:
                etQuery.setText("");
                break;
            case R.id.tv_search:
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
            default:
                break;
        }
    }
    private void submit(String geocode) {
       setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("STANDARDADDRCODE", geocode);

        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_SearchInfoByStandardAddr", param)
                .setActivity(getActivity())
                .setBeanType(ChuZuWu_SearchInfoByStandardAddr.class)
                .setCallBack(getHouseIdCallBack).build();
        PoolManager.getInstance().execute(task);
    }

    private WebServiceCallBack<ChuZuWu_SearchInfoByStandardAddr> getHouseIdCallBack = new WebServiceCallBack<ChuZuWu_SearchInfoByStandardAddr>() {
        @Override
        public void onSuccess(ChuZuWu_SearchInfoByStandardAddr bean) {
            setProgressDialog(false);
            getActivity().finish();
            CzfInfoActivity.goActivity(getActivity(), bean.getContent().getHOUSEID());
        }

        @Override
        public void onErrorResult(ErrorResult errorResult) {
            setProgressDialog(false);

        }
    };
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Basic_StandardAddressCodeByKey_Kj.ContentBean bean = (Basic_StandardAddressCodeByKey_Kj.ContentBean) parent.getItemAtPosition(position);
        geocode = bean.getGeocode();
        submit(geocode);
    }
}
