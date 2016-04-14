package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary;
import com.tdr.citycontrolpolice.entity.Basic_JuWeiHui;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo;
import com.tdr.citycontrolpolice.entity.Basic_XingZhengQuHua;
import com.tdr.citycontrolpolice.entity.CheckUpdate;
import com.tdr.citycontrolpolice.entity.DownDatas;
import com.tdr.citycontrolpolice.entity.Login;
import com.tdr.citycontrolpolice.entity.PhoneInfo;
import com.tdr.citycontrolpolice.entity.UpDatas;
import com.tdr.citycontrolpolice.entity.User;
import com.tdr.citycontrolpolice.materialedittext.MaterialEditText;
import com.tdr.citycontrolpolice.update.GetVersionCodeAsynckTask;
import com.tdr.citycontrolpolice.update.UpdateManager;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.PhoneUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;
import com.tdr.citycontrolpolice.view.ZProgressHUD;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/22.
 */
public class LoginActivity extends Activity implements RadioGroup.OnCheckedChangeListener {
    private ZProgressHUD progressHUD;
    private Button bt_login;
    private final static int UPDIC = 1001;
    private final static int ISUPDATA = 1002;
    private final static int UPDATA = 1003;
    private final static int LOGIN = 1004;
    private final static int ERROR = 4001;
    private int page = 0, dataspage = 0;
    private ArrayList<String> newdata;
    private DbUtils db;
    private String date, name, password;
    private MaterialEditText et_name, et_password;
    private int loginType = 0;
    private UserService userService;
    Gson gson = new Gson();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDIC:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        if (data.equals("[]")) {
                            SharedPreferences preferences = getSharedPreferences(Constants.USER_DETAILS, Context.MODE_APPEND);
                            SharedPreferences.Editor editor = preferences.edit();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateString = formatter.format(new Date());
                            editor.putString("upDate", dateString);
                            editor.commit();
                            checkUpdate();
                        } else {
                            dbDic(data);
                        }
                    }
                    break;
                //其他数据更新判断
                case ISUPDATA:

                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        Log.e("data", data);
                        ArrayList<DownDatas> result = gson.fromJson(data,
                                new TypeToken<ArrayList<DownDatas>>() {
                                }.getType());
                        ArrayList<String> dataTypeCode = new ArrayList<String>();
                        for (DownDatas d : result) {
                            if (d.isUpdate()) {
                                dataTypeCode.add(d.getName());
                            }
                        }
                        upData(dataTypeCode);
                    }
                    break;
                //其他数据更新
                case UPDATA:
                    ArrayList<String> dataTypeCode = msg.getData().getStringArrayList("datalist");
                    if (msg.getData().getInt("error") == 0) {
                        newdata = dataTypeCode;
                        Bundle bundle = msg.getData();
                        dbData(bundle, dataTypeCode);
                    } else {
                        upData(dataTypeCode);
                    }
                    break;
                case LOGIN:
                    progressHUD.dismiss();
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        User result = gson.fromJson(data,
                                new TypeToken<User>() {
                                }.getType());

                        SharedPreferences preferences = getSharedPreferences(Constants.USER_DETAILS, Context.MODE_APPEND);
                        SharedPreferences.Editor editor = preferences.edit();
                        if (checkbox_remmber.isChecked()) {
                            editor.putString("pwd", et_password.getText().toString());
                            editor.putBoolean("checked", true);
                        }
                        editor.putString("uid", result.getUserID());
                        editor.putString("token", result.getToken());
                        editor.putString("login_name", et_name.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "账户或密码错误", Toast.LENGTH_LONG).show();
                    }
                    break;
                case DBDIC:
                    if (msg.getData().getInt("updata") == 0) {
                        page = page + 1;
                        upDictionary();
                    } else {
                        upDictionary();
                    }
                    break;
                case DBDATA:
                    dataspage = dataspage + 1;
                    upData(newdata);
                    break;
                case ERROR:
                    progressHUD.dismiss();
                    Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    break;

                // 更新相关
                case Constants.HANDLER_KEY_GETVERSION_SUCCESS:// 获取版本号成功
                    double newVersion = Double.parseDouble(msg.obj.toString());
                    try {
                        double vCode = LoginActivity.this.getPackageManager().getPackageInfo(
                                "com.tdr.citycontrolpolice", 0).versionCode;
                        if (vCode < newVersion && updateCount > 0) {
                            finish();
                            break;
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    updateCount++;
                    UpdateManager updateManager = new UpdateManager(LoginActivity.this,
                            newVersion);
                    updateManager.checkUpdate();// 开始检查版本更新
                    break;
                case Constants.HANDLER_KEY_GETVERSION_FAIL:

                    break;
            }
        }
    };

    private String versionName;
    private int updateCount;

    private TextView tv_login_version;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init_view();
        init_data();

        try {
            versionName = LoginActivity.this.getPackageManager().getPackageInfo("com.tdr.citycontrolpolice", 0).versionName;
            tv_login_version.setText("当前软件版本：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//         检测版本更新
        GetVersionCodeAsynckTask asynckTask = new GetVersionCodeAsynckTask(
                LoginActivity.this, mHandler);
        asynckTask.execute("CityControlPolice.apk");
        super.onResume();
    }

    private CheckBox checkbox_remmber;

    /**
     * 初始化组件
     */
    private void init_view() {
        RadioGroup rg_login = (RadioGroup) findViewById(R.id.rg_login);
        RadioButton rb_account = (RadioButton) findViewById(R.id.rb_account);
        RadioButton rb_police = (RadioButton) findViewById(R.id.rb_police);
        rg_login.setOnCheckedChangeListener(this);
        progressHUD = new ZProgressHUD(LoginActivity.this);
        progressHUD.setMessage("初始化数据");
        progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);
        progressHUD.show();


        bt_login = (Button) findViewById(R.id.btn_login);
        bt_login.setOnClickListener(new LoginOnclick());
        et_name = (MaterialEditText) findViewById(R.id.et_login_name);
        SharedPreferences sp = getSharedPreferences(Constants.USER_DETAILS,
                Activity.MODE_PRIVATE);
        et_name.setText(sp.getString("login_name", ""));
        et_password = (MaterialEditText) findViewById(R.id.et_login_password);
        et_password.setText(sp.getString("pwd", ""));
        tv_login_version = (TextView) findViewById(R.id.tv_login_version);

        checkbox_remmber = (CheckBox) findViewById(R.id.checkbox_remmber);
        if (sp.getBoolean("checked", false)) {
            checkbox_remmber.setChecked(true);
        }
    }

    /**
     * 初始化化数据
     */
    private void init_data() {
        db = DbUtils.create(LoginActivity.this);
        loginType = getIntent().getIntExtra("loginType", 0);
        if (loginType == 0) {
            userService = UserService.getInstance(LoginActivity.this);
            date = userService.getUpDate();
            if (date.equals("")) {
                date = "2014-10-30 23:11:02";
            }
            upDictionary();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_account:
                ToastUtil.showMyToast("账号登录");

                break;
            case R.id.rb_police:
                ToastUtil.showMyToast("警号登录");
                break;
            default:
                break;
        }
    }

    /**
     * 登陆按钮
     */
    class LoginOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            login();

        }
    }

    /**
     * 字典
     */
    private void upDictionary() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("UpdateTime", date);
                    object.put("PageSize", 50);
                    object.put("PageIndex", page);
                    Log.e("code", object.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("token", "");
                param.put("encryption", 0);
                param.put("dataTypeCode", "Basic_Dictionary");
                param.put("content", object.toString());

                try {
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    Log.e("code", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = UPDIC;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 请求判断是否更新
     */
    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CheckUpdate checkUpdate = new CheckUpdate();
                String[] names = {"Basic_PaiChuSuo", "Basic_XingZhengQuHua", "Basic_JuWeiHui"};
                List<UpDatas> datas = new ArrayList<UpDatas>();
                for (String s : names) {
                    UpDatas upDatas = new UpDatas();
                    upDatas.setName(s);
                    upDatas.setTime(userService.getUpDate(s));
                    datas.add(upDatas);
                }
                checkUpdate.setTaskID("1");
                checkUpdate.setDatas(datas);
                String checkUpdateJson = gson.toJson(checkUpdate);
                Log.e("checkUpdateJson", checkUpdateJson);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("token", 1);
                param.put("encryption", 0);
                param.put("dataTypeCode", "Basic_CheckUpdate");
                param.put("content", checkUpdateJson);
                try {
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = ISUPDATA;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 更新其他数据
     *
     * @param dataTypeCode
     */
    private void upData(final ArrayList<String> dataTypeCode) {
        if (dataTypeCode.size() == 0) {
            progressHUD.dismiss();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                Bundle bundle = new Bundle();
                for (String s : dataTypeCode) {
                    final JSONObject object = new JSONObject();
                    try {
                        object.put("TaskID", "1");
                        object.put("PageSize", 500);
                        object.put("PageIndex", dataspage);
                        Map<String, Object> param = new HashMap<String, Object>();
                        param.put("token", "");
                        param.put("encryption", 0);
                        param.put("dataTypeCode", s);
                        param.put("content", object.toString());
                        String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                        JSONObject rootObject = new JSONObject(result);
                        int error = rootObject.getInt("ResultCode");
                        bundle.putInt("error", error);
                        bundle.putString(s, rootObject.getString("Content"));
                        msg.what = UPDATA;
                    } catch (Exception e) {
                        e.printStackTrace();
                        msg.what = ERROR;
                        mHandler.sendMessage(msg);
                        e.printStackTrace();
                    }
                }
                bundle.putStringArrayList("datalist", dataTypeCode);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 做登陆
     */
    private void login() {
        name = et_name.getText().toString().trim();
        if (name.equals("") || name == null) {
            Toast.makeText(LoginActivity.this, "账户不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        password = et_password.getText().toString().trim();
        if (password.equals("") || password == null) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        progressHUD.setMessage("登陆中");
        progressHUD.setSpinnerType(ZProgressHUD.FADED_ROUND_SPINNER);
        progressHUD.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                PhoneInfo phoneInfo = new PhoneUtil(LoginActivity.this).getInfo();
                Login login = new Login();
                login.setUSERNAME(name);
                login.setPASSWORD(password);
                login.setPHONEINFO(phoneInfo);
                login.setSOFTTYPE(2);
                login.setSOFTVERSION(1.1);
                String loginJson = gson.toJson(login);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("token", "");
                param.put("encryption", 0);
                param.put("dataTypeCode", "User_LoginByPolice");
                param.put("content", loginJson.toString());
                try {
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    Log.e("code", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = LOGIN;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    /**
     * 存储字典数据
     */
    private final static int DBDIC = 3001;

    private void dbDic(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Basic_Dictionary> result = gson.fromJson(data,
                        new TypeToken<ArrayList<Basic_Dictionary>>() {
                        }.getType());
                Message msg = new Message();
                Bundle bundle = new Bundle();
                try {
                    db.saveOrUpdateAll(result);
                    bundle.putInt("updata", 0);
                } catch (DbException e) {
                    e.printStackTrace();
                    bundle.putInt("updata", 1);
                }
                msg.what = DBDIC;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

    /**
     * 存储基础数据
     */
    private final static int DBDATA = 3002;

    private void dbData(final Bundle bundle, final ArrayList<String> dataTypeCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                ArrayList<String> d = new ArrayList<String>();
                for (String s : dataTypeCode) {
                    String data = bundle.getString(s);
                    Log.e("result", data);
                    if (data.equals("[]")) {
                        SharedPreferences preferences = getSharedPreferences(Constants.USER_DETAILS, Context.MODE_APPEND);
                        SharedPreferences.Editor editor = preferences.edit();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String dateString = formatter.format(new Date());
                        editor.putString(s, dateString);
                        editor.commit();
                        d.add(s);
                    } else {
                        Log.e("sss", s);

                        try {
                            if (s.equals("Basic_PaiChuSuo")) {
                                if (dataspage == 0) {
                                    db.dropTable(Basic_PaiChuSuo.class);
                                }
                                ArrayList<Basic_PaiChuSuo> result = gson.fromJson(data,
                                        new TypeToken<ArrayList<Basic_PaiChuSuo>>() {
                                        }.getType());
                                db.saveAll(result);
                            }
                            if (s.equals("Basic_XingZhengQuHua")) {
                                if (dataspage == 0) {
                                    db.dropTable(Basic_XingZhengQuHua.class);
                                }
                                ArrayList<Basic_XingZhengQuHua> result = gson.fromJson(data,
                                        new TypeToken<ArrayList<Basic_XingZhengQuHua>>() {
                                        }.getType());
                                db.saveAll(result);
                            }
                            if (s.equals("Basic_JuWeiHui")) {
                                if (dataspage == 0) {
                                    db.dropTable(Basic_JuWeiHui.class);
                                }
                                ArrayList<Basic_JuWeiHui> result = gson.fromJson(data,
                                        new TypeToken<ArrayList<Basic_JuWeiHui>>() {
                                        }.getType());
                                db.saveAll(result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("e", e.toString());
                        }
                    }
                }
                newdata.removeAll(d);
                msg.what = DBDATA;
                mHandler.sendMessage(msg);
            }
        }).start();
    }

}
