package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWuInfo;
import com.tdr.citycontrolpolice.entity.Room;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.DipPx;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.citycontrolpolice.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/25.
 */
public class HouseInfoActivity extends Activity {
    private TextView tv_name, tv_phone, tv_address;
    private ListView lv;
    private ImageView img_add;
    private String houseId;
    private final static int HOUSERINFO = 1001;
    private final static int ROOMADD = 2001;
    private final static int REPORTINFO = 3001;
    private final static int ERROR = 4001;
    private Gson gson = new Gson();
    private ChuZuWuInfo chuZuWuInfo;
    private List<Room> roomList;
    private Button bt_room;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HOUSERINFO:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        chuZuWuInfo = gson.fromJson(data,
                                new TypeToken<ChuZuWuInfo>() {
                                }.getType());
                        tv_name.setText("房东姓名:" + chuZuWuInfo.getOWNERNAME());
                        tv_address.setText("地址:" + chuZuWuInfo.getADDRESS());
                        tv_phone.setText("手机号码:" + chuZuWuInfo.getPHONE());
                        roomList = chuZuWuInfo.getRoomList();
                        if (roomList.size() == 0) {
                            bt_room.setVisibility(View.VISIBLE);
                        } else {
                            bt_room.setVisibility(View.GONE);
                            lv.setAdapter(new LVAdapter());
                        }
                    }
                    break;

                case REPORTINFO:
                    progressHUD.dismiss();
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        try {
                            JSONObject json = new JSONObject(data);
                            Log.e("HOUSEID", json.getString("HOUSEID"));
                            if (houseId.equals(json.getString("HOUSEID"))) {
                                if (json.getString("InstallStatus").equals("1")) {
                                    Toast.makeText(HouseInfoActivity.this, "设备已报装", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    Toast.makeText(HouseInfoActivity.this, "设备未报装", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            } else {
                                Toast.makeText(HouseInfoActivity.this, "返回房间参数有误，请重新申报", Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(HouseInfoActivity.this, msg.getData().getString("resultText"), Toast.LENGTH_LONG).show();
                    }
                    break;

                case ERROR:
                    Toast.makeText(HouseInfoActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_info);
        title();
        init_view();
        init_data();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ROOMADD:
                if (resultCode == RESULT_OK) {
                    gethouseInfo();
                }

                break;
        }

    }

    /**
     * 标题栏
     */
    private void title() {
        ImageView img_title = (ImageView) findViewById(R.id.image_back);
        img_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("出租房信息");

        ImageView img_add = (ImageView) findViewById(R.id.image_add);
        img_add.setBackgroundResource(R.mipmap.header_menu_on);
//        img_add.setVisibility(View.VISIBLE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_top(v);
            }
        });
    }

    private ZProgressHUD progressHUD;

    /**
     * 初始化控件
     */
    private void init_view() {
        progressHUD = new ZProgressHUD(HouseInfoActivity.this);
        progressHUD.setMessage("提交中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);

        tv_name = (TextView) findViewById(R.id.tv_huose_info_name);
        tv_phone = (TextView) findViewById(R.id.tv_huose_info_phone);
        tv_address = (TextView) findViewById(R.id.tv_huose_info_address);

        lv = (ListView) findViewById(R.id.lv_house_info);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HouseInfoActivity.this, RoomInsideActivity.class);
                intent.putExtra("ROOMID", roomList.get(position).getROOMID());
                startActivity(intent);
            }
        });

        img_add = (ImageView) findViewById(R.id.img_huose_info_add);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseInfoActivity.this, RoomInfoActivity.class);
                intent.putExtra("HouseID", houseId);
                startActivityForResult(intent, ROOMADD);
            }
        });
        bt_room = (Button) findViewById(R.id.bt_house_info);
        bt_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseInfoActivity.this, InitializationRoomActivity.class);
                intent.putExtra("HouseID", houseId);
                startActivityForResult(intent, ROOMADD);
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void init_data() {
        houseId = getIntent().getStringExtra("HouseID");
        gethouseInfo();
    }

    /**
     * listview
     */
    class LVAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return roomList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = convertView.inflate(HouseInfoActivity.this, R.layout.house_info_list, null);
            }
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_house_info_list_name);
            tv_name.setText(roomList.get(position).getROOMNO());
            return convertView;
        }
    }

    /**
     * 请求获取出租房信息
     */
    private void gethouseInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("HouseID", houseId);
                    Log.e("code", object.toString());
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("token", UserService.getInstance(HouseInfoActivity.this).getToken());
                    param.put("encryption", 0);
                    param.put("dataTypeCode", "ChuZuWu_Info");
                    param.put("content", object.toString());
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    Log.e("code", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = HOUSERINFO;
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

    //设置添加店铺
    private void pop_top(View v) {
        View contentView = LayoutInflater.from(HouseInfoActivity.this).inflate(
                R.layout.pop_room_houseinfo, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //必须设置背景不然无法dismiss
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.transparent));
        //设置其他地方变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        LinearLayout ll_modify = (LinearLayout) contentView.findViewById(R.id.ll_pop_room_inside_modify);
        ll_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(HouseInfoActivity.this,RoomModifyActivity.class);
                //intent.putExtra("ROOMID",roomId);
                //startActivity(intent);
            }
        });

        LinearLayout ll_report = (LinearLayout) contentView.findViewById(R.id.ll_pop_room_houseinfo_report);
        ll_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing()) {
                    dialogShow(0, "确认进行出租房设备报装？");
                }

            }
        });
        popupWindow.showAtLocation(v, Gravity.TOP, 0, DipPx.dip2px(HouseInfoActivity.this, 50));
    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    public void dialogShow(int flag, String message) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 0) {// 确定提交数据
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#ffffffff")
                    .withMessage(message).isCancelableOnTouchOutside(false)
                    .withEffect(effectstype).withButton1Text("确认")
                    .setCustomView(R.layout.custom_view, HouseInfoActivity.this)
                    .withButton2Text("取消")
                    .withButtonDrawable(R.drawable.dialog_btn_change)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();

                            progressHUD.show();
                            equipReport();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).show();
        }

    }

    private void equipReport() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("HOUSEID", houseId);
                    object.put("ISREGISTER", "1");
                    Log.e("content", object.toString());
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("token", UserService.getInstance(HouseInfoActivity.this).getToken());
                    params.put("encryption", 0);
                    params.put("dataTypeCode", "ChuZuWu_InstallStatus");
                    params.put("content", object.toString());
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, params);
                    Log.e("result", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    String resultText = rootObject.getString("ResultText");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("resultText", resultText);
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = REPORTINFO;
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

}
