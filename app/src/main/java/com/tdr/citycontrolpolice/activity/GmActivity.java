package com.tdr.citycontrolpolice.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.ActivityUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.view.FixedGridView;

import java.util.Random;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/16 16:11
 * 修改备注：
 */
public class GmActivity extends BackTitleActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private boolean key;
    private int position;
    private int tip;
    private FixedGridView gv_key;
    private Button btn_database;
    private Button btn_bluetooth;
    private Button btn_exception;
    private int wrongCount;

    @Override
    public View setContentView() {
        view = View.inflate(this, R.layout.activity_gm, null);
        return view;
    }

    @Override
    public void initVariables() {

    }

    @Override
    protected void initView() {
        btn_database = (Button) view.findViewById(R.id.btn_database);
        btn_bluetooth = (Button) view.findViewById(R.id.btn_bluetooth);
        btn_exception = (Button) view.findViewById(R.id.btn_exception);
        gv_key = (FixedGridView) view.findViewById(R.id.gv_key);
        gv_key.setOnItemLongClickListener(this);
        btn_database.setOnClickListener(this);
        btn_bluetooth.setOnClickListener(this);
        btn_exception.setOnClickListener(this);
        KeyAdapter keyAdapter = new KeyAdapter(this);
        gv_key.setAdapter(keyAdapter);
    }

    @Override
    public void initNet() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        tip = 10 + new Random().nextInt(90);
        setTitle(tip + "");

    }

    @Override
    public void onClick(View v) {
        if (!key) {
            return;
        }
        switch (v.getId()) {
            case R.id.btn_database:
                ActivityUtil.goActivity(this, DownloadDbActivity.class);
                break;
            case R.id.btn_bluetooth:
                ActivityUtil.goActivity(this, BluetoothChangeActivity.class);
                break;
            case R.id.btn_exception:
                ActivityUtil.goActivity(this, ExceptionActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        String tipString = String.valueOf(tip);
        String a = tipString.substring(0, 1);
        String b = tipString.substring(1, 2);
        int newTip = Integer.valueOf(a) * Integer.valueOf(b);
        if (position == (99 - newTip)) {
            key = true;
            setTitle("...");
            ToastUtil.showMyToast("Successful!");
            gv_key.setVisibility(View.GONE);
            btn_database.setVisibility(View.VISIBLE);
            btn_bluetooth.setVisibility(View.VISIBLE);
            btn_exception.setVisibility(View.VISIBLE);
        } else {
            if (++wrongCount > 1) {
                finish();
            }
        }
        return true;
    }

    class KeyAdapter extends BaseAdapter {
        private Context context;

        public KeyAdapter(Context context) {

            this.context = context;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View
                        .inflate(context, R.layout.item_key, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tv_password.setText(position + "");

            return convertView;
        }
    }

    public class ViewHolder {
        public final TextView tv_password;
        public final View root;

        public ViewHolder(View root) {
            tv_password = (TextView) root.findViewById(R.id.tv_password);
            this.root = root;
        }
    }
}
