package com.tdr.citycontrolpolice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kingja.ui.popupwindow.BaseTopPop;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.CzfInfoActivity;
import com.tdr.citycontrolpolice.adapter.CzfHistoryAdapter;
import com.tdr.citycontrolpolice.adapter.CzfQueryAdapter;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo_Kj;
import com.tdr.citycontrolpolice.entity.Basic_XingZhengQuHua_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_HaveDeviceInquire;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.entity.HistoryCzfAddress;
import com.tdr.citycontrolpolice.entity.SQL_Query;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
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
public class CzfQueryFragment extends KjBaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.et_query)
    EditText etQuery;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_xq)
    TextView tvXq;
    @BindView(R.id.iv_xq)
    ImageView ivXq;
    @BindView(R.id.ll_xq)
    LinearLayout llXq;
    @BindView(R.id.tv_pcs)
    TextView tvPcs;
    @BindView(R.id.iv_pcs)
    ImageView ivPcs;
    @BindView(R.id.ll_pcs)
    LinearLayout llPcs;
    @BindView(R.id.cb_device)
    CheckBox cbDevice;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_clearHistory)
    TextView tvClearHistory;
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
    private List<Basic_XingZhengQuHua_Kj> areas = new ArrayList<>();
    private List<Basic_PaiChuSuo_Kj> policeStations = new ArrayList<>();

    private BaseTopPop xqPop;
    private BaseTopPop pcsPop;
    private String pcscode;
    private String xqcode;
    private List<ChuZuWu_HaveDeviceInquire.ContentBean> addresses=new ArrayList<>();
    private CzfQueryAdapter czfQueryAdapter;
    private CzfHistoryAdapter czfHistoryAdapter;

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_czf_query, container, false);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {
        areas = DbDaoXutils3.getInstance().selectAll
                (Basic_XingZhengQuHua_Kj.class);
        for (int i = 0; i < areas.size(); i++) {
            if ("330300".equals(areas.get(i).getDMZM())) {
                areas.remove(i);
                break;
            }
        }
    }

    @Override
    protected void initFragmentView() {
        czfQueryAdapter = new CzfQueryAdapter(getActivity(), addresses);
    }

    @Override
    protected void initFragmentNet() {

    }

    @Override
    protected void initFragmentData() {
        llXq.setOnClickListener(this);
        llPcs.setOnClickListener(this);
        tvSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        tvClearHistory.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        lv.setAdapter(czfQueryAdapter);
        initAreaPop();
        initPoliceStationPop();
    }

    @Override
    protected void setFragmentData() {
        initHistory();
    }
    private List<HistoryCzfAddress> history;
    private void initHistory() {
        history = DbDaoXutils3.getInstance().selectAllAndOrder(HistoryCzfAddress.class,"date");
        llHistory.setVisibility(View.VISIBLE);
        tvClearHistory.setVisibility(history.size()>0?View.VISIBLE:View.GONE);
        czfHistoryAdapter = new CzfHistoryAdapter(getActivity(), history);
        lvHistory.setAdapter(czfHistoryAdapter);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvHistory.setVisibility(View.GONE);
                HistoryCzfAddress keyWord = (HistoryCzfAddress) parent.getItemAtPosition(position);
                etQuery.setText(keyWord.getKeyWord());
                Selection.setSelection(etQuery.getText(), etQuery.getText().length());
                search(keyWord.getKeyWord());
            }
        });
    }
    private void initAreaPop() {
        xqPop = new BaseTopPop<Basic_XingZhengQuHua_Kj>(getActivity(), areas) {
            @Override
            protected void fillLvData(List<Basic_XingZhengQuHua_Kj> list, int position, TextView tv) {
                tv.setText(list.get(position).getDMMC());
            }

            @Override
            protected void onItemSelect(Basic_XingZhengQuHua_Kj basic_xingZhengQuHua_kj) {
                tvPcs.setText("派出所");
                pcscode = "";
                xqcode = basic_xingZhengQuHua_kj.getDMZM();
                tvXq.setText(basic_xingZhengQuHua_kj.getDMMC());

            }
        };
        xqPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivXq.setBackgroundResource(R.drawable.spinner_arow_nor);
                policeStations = DbDaoXutils3.getInstance().selectAllWhereLike(Basic_PaiChuSuo_Kj
                        .class, "SANSHIYOUDMZM", xqcode + "%");
                Log.e(TAG, "policeStations: " + policeStations.size());
                initPoliceStationPop();
            }
        });
    }

    private void initPoliceStationPop() {
        pcsPop = new BaseTopPop<Basic_PaiChuSuo_Kj>(getActivity(), policeStations) {
            @Override
            protected void fillLvData(List<Basic_PaiChuSuo_Kj> list, int position, TextView tv) {
                tv.setText(list.get(position).getDMMC());
            }

            @Override
            protected void onItemSelect(Basic_PaiChuSuo_Kj basic_PaiChuSuo_Kj) {
                pcscode = basic_PaiChuSuo_Kj.getDMZM();
                tvPcs.setText(basic_PaiChuSuo_Kj.getDMMC());
            }
        };
        pcsPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivPcs.setBackgroundResource(R.drawable.spinner_arow_nor);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_xq:
                ivXq.setBackgroundResource(R.drawable.spinner_arow_sel);
                xqPop.showPopAsDropDown(llRoot);
                break;
            case R.id.tv_search:
                String queryAddress = etQuery.getText().toString().trim();
                if (CheckUtil.checkEmpty(queryAddress, "请输入搜索地址")) {
                    search(queryAddress);
                }
                break;
            case R.id.iv_clear:
                etQuery.setText("");
                break;
            case R.id.ll_pcs:
                ivPcs.setBackgroundResource(R.drawable.spinner_arow_sel);
                if (TextUtils.isEmpty(xqcode)) {
                    ToastUtil.showToast("请先选择分局");
                } else if (policeStations.size() == 0) {
                    ToastUtil.showToast("没找到对应派出所数据");
                } else {
                    pcsPop.showPopAsDropDown(llRoot);
                }
                break;
            case R.id.tv_clearHistory:
                DbDaoXutils3.getInstance().deleteAll(SQL_Query.class);
                initHistory();
            default:
                break;

        }
    }
    private void hideInput() {
        InputMethodManager  inputManager = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void search(final String queryAddress) {
        hideInput();
        srl.setRefreshing(true);
        Map<String, Object> param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("PageSize", "30");
        param.put("PageIndex", "0");
        param.put("Address", queryAddress);
        param.put("XQCODE", xqcode);
        param.put("PCSCODE", pcscode);
        param.put("DEVICETYPE",cbDevice.isChecked()?"1041":"");

        ThreadPoolTask.Builder builder = new ThreadPoolTask.Builder();
        ThreadPoolTask task = builder.setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_HaveDeviceInquire", param)
                .setActivity(getActivity())
                .setBeanType(ChuZuWu_HaveDeviceInquire.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_HaveDeviceInquire>() {
                    @Override
                    public void onSuccess(ChuZuWu_HaveDeviceInquire bean) {
                        llHistory.setVisibility(View.GONE);
                        srl.setRefreshing(false);
                        addresses = bean.getContent();
                        Log.i(TAG, "addresses: " + addresses.size());
                        if (addresses.size() == 0) {
                            ToastUtil.showMyToast("无搜索结果，请核对地址");
                        }
                        czfQueryAdapter.setData(addresses);
                        HistoryCzfAddress historyAddress = new HistoryCzfAddress();
                        historyAddress.setKeyWord(queryAddress);
                        historyAddress.setDate(System.currentTimeMillis());
                        DbDaoXutils3.getInstance().saveOrUpdate(historyAddress);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        srl.setRefreshing(false);
                    }
                }).build();
        PoolManager.getInstance().execute(task);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChuZuWu_HaveDeviceInquire.ContentBean czfInfo = (ChuZuWu_HaveDeviceInquire.ContentBean) parent.getItemAtPosition(position);
        CzfInfoActivity.goActivity(getActivity(),czfInfo.getHOUSEID());
    }
}
