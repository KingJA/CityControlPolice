package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.AddRoom;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/24.
 */
public class RoomModifyActivity extends Activity {
    private EditText et_name, et_shi, et_ting, et_wei, et_mm, et_numble, et_yangtai;
    private TextView tv_renovation, tv_payment;
    private String name, renovation, shi, ting, wei, mm, numble, payment, yangtai, roomId;
    private Button bt_submit;
    private List<Basic_Dictionary> paymentList, renovationList;
    private Gson gson = new Gson();
    private int dictype;
    private AddRoom roomInfo;
    private DbUtils db;


    private final static int ROOMADD = 1001;
    private final static int ROOMINFO = 1002;
    private final static int ERROR = 4001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ROOMADD:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        finish();
                    }
                    break;
                case ROOMINFO:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        roomInfo = gson.fromJson(data,
                                new TypeToken<AddRoom>() {
                                }.getType());
                        et_shi.setText(roomInfo.getSHI());
                        et_wei.setText(roomInfo.getWEI());
                        et_ting.setText(roomInfo.getTING());
                        et_yangtai.setText(roomInfo.getYANGTAI());
                        et_mm.setText(roomInfo.getSQUARE());
                        et_numble.setText(roomInfo.getGALLERYFUL());
                        renovation = roomInfo.getFIXTURE();
                        payment = roomInfo.getDEPOSIT();
                        try {
                            Basic_Dictionary r = db.findFirst(Selector.from(Basic_Dictionary.class)
                                    .where("COLUMNCODE", "=", "FIXTURE")
                                    .and("COLUMNVALUE", "=", renovation));
                            tv_renovation.setText(r.getCOLUMNCOMMENT());
                            Basic_Dictionary p = db.findFirst(Selector.from(Basic_Dictionary.class)
                                    .where("COLUMNCODE", "=", "DEPOSIT")
                                    .and("COLUMNVALUE", "=", payment));
                            tv_payment.setText(p.getCOLUMNCOMMENT());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(RoomModifyActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    break;
                case ERROR:
                    Toast.makeText(RoomModifyActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_info);
        title();
        init_view();
        init_data();
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
        tv_title.setText("房间信息");
    }

    /**
     * 初始化组件
     */
    private void init_view() {
        db = DbUtils.create(this);
        tv_renovation = (TextView) findViewById(R.id.tv_room_info_renovation);
        tv_renovation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dictype = 0;
                pop_text(v, renovationList);
            }
        });
        tv_payment = (TextView) findViewById(R.id.tv_room_info_payment);
        tv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dictype = 1;
                pop_text(v, paymentList);
            }
        });
        et_name = (EditText) findViewById(R.id.et_room_info_name);
        et_shi = (EditText) findViewById(R.id.et_room_info_shi);
        et_ting = (EditText) findViewById(R.id.et_room_info_ting);
        et_wei = (EditText) findViewById(R.id.et_room_info_wei);
        et_mm = (EditText) findViewById(R.id.et_room_info_mm);
        et_numble = (EditText) findViewById(R.id.et_room_info_numble);
        et_yangtai = (EditText) findViewById(R.id.et_room_info_yangtai);

        LinearLayout ll_no = (LinearLayout) findViewById(R.id.ll_room_no);
        ll_no.setVisibility(View.GONE);
        LinearLayout ll_photo = (LinearLayout) findViewById(R.id.ll_room_photo);
        ll_photo.setVisibility(View.GONE);

        bt_submit = (Button) findViewById(R.id.bt_room_info_submit);
        bt_submit.setOnClickListener(new SubmitOnClick());

    }

    /**
     * 初始化数据
     */
    private void init_data() {
        roomId = getIntent().getStringExtra("ROOMID");

        try {
            paymentList = db.findAll(Selector.from(Basic_Dictionary.class)
                    .where("COLUMNCODE", "=", "DEPOSIT"));
            renovationList = db.findAll(Selector.from(Basic_Dictionary.class)
                    .where("COLUMNCODE", "=", "FIXTURE"));
        } catch (DbException e) {
            e.printStackTrace();
        }
        getRoomInfo();
    }

    /**
     * pop
     *
     * @param v
     * @param dic
     */
    private void pop_text(View v, List<Basic_Dictionary> dic) {
        View contentView = LayoutInflater.from(RoomModifyActivity.this).inflate(
                R.layout.pop_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final ListView lv = (ListView) contentView.findViewById(R.id.lv_pop_listview);
        final ImageView img_up = (ImageView) contentView.findViewById(R.id.img_pop_list_up);
        final ImageView img_down = (ImageView) contentView.findViewById(R.id.img_pop_list_down);
        lv.setAdapter(new ListViewAdapter(dic));
        lv.setOnItemClickListener(new ListViewOnClick(dic, popupWindow));
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 判断滚动到底部
                if (lv.getLastVisiblePosition() == (lv.getCount() - 1)) {
                    img_up.setVisibility(View.INVISIBLE);
                } else {
                    img_up.setVisibility(View.VISIBLE);
                }
                // 判断滚动到顶部
                if (lv.getFirstVisiblePosition() == 0) {
                    img_down.setVisibility(View.INVISIBLE);
                } else {
                    img_down.setVisibility(View.VISIBLE);
                }
                if (lv.getLastVisiblePosition() != (lv.getCount() - 1) && lv.getFirstVisiblePosition() != 0) {
                    img_up.setVisibility(View.VISIBLE);
                    img_down.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
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
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);

    }

    /**
     * poplist
     */
    class ListViewAdapter extends BaseAdapter {
        private List<Basic_Dictionary> dic;

        ListViewAdapter(List<Basic_Dictionary> dic) {
            this.dic = dic;
        }

        @Override
        public int getCount() {
            return dic.size();
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
                convertView = convertView.inflate(RoomModifyActivity.this, R.layout.pop_list_list, null);
            }
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_pop_list_list_text);
            tv_name.setText(dic.get(position).getCOLUMNCOMMENT());
            return convertView;
        }
    }

    /**
     * listItem
     */
    class ListViewOnClick implements AdapterView.OnItemClickListener {
        private List<Basic_Dictionary> dic;
        private PopupWindow popupWindow;

        ListViewOnClick(List<Basic_Dictionary> dic, PopupWindow popupWindow) {
            this.dic = dic;
            this.popupWindow = popupWindow;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String type = dic.get(position).getCOLUMNVALUE();
            String name = dic.get(position).getCOLUMNCOMMENT();
            switch (dictype) {
                case 0:
                    renovation = type;
                    tv_renovation.setText(name);
                    break;
                case 1:
                    payment = type;
                    tv_payment.setText(name);
                    break;
            }
            popupWindow.dismiss();
        }
    }

    /**
     * 提交按钮
     */
    class SubmitOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    AddRoom addRoom = roomInfo;
                    addRoom.setTaskID("1");
                    addRoom.setSHI(et_shi.getText().toString().trim());
                    addRoom.setTING(et_ting.getText().toString().trim());
                    addRoom.setWEI(et_wei.getText().toString().trim());
                    addRoom.setYANGTAI(et_yangtai.getText().toString().trim());
                    addRoom.setGALLERYFUL(et_numble.getText().toString().trim());
                    addRoom.setFIXTURE(renovation);
                    addRoom.setDEPOSIT(payment);
                    addRoom.setSQUARE(et_mm.getText().toString().trim());


                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("token", UserService.getInstance(RoomModifyActivity.this).getToken());
                    param.put("encryption", 0);
                    param.put("dataTypeCode", "ChuZuWu_ModifyRoom");
                    param.put("content", gson.toJson(addRoom));
                    Log.e("json", gson.toJson(addRoom).toString());
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
                        msg.what = ROOMADD;
                        mHandler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    /**
     * 获取房间信息
     */
    private void getRoomInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("ROOMID", roomId);
                    Log.e("code", object.toString());
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("token", UserService.getInstance(RoomModifyActivity.this).getToken());
                    param.put("encryption", 0);
                    param.put("dataTypeCode", "ChuZuWu_RoomInfo");
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
                    msg.what = ROOMINFO;
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
