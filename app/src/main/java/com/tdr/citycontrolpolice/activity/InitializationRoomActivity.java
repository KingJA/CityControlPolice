package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.AddRoom;
import com.tdr.citycontrolpolice.entity.Param_ChuZuWu_AddRoomList;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class InitializationRoomActivity extends Activity {
    private static final String TAG = "Initialization";
    private EditText et_numble;
    private ListView lv;
    private int floor;
    private Button bt_floor, bt_room;
    private int[] numbles;
    private String houseId;
    private Gson gson = new Gson();
    private TextView tv_pb;
    private ProgressBar pb;
    private final static int ROOMADD = 1001;
    private final static int ROOMTO = 1002;
    private int max, to;
    private LinearLayout ll;
    private final static int ERROR = 4001;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ROOMADD:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        setResult(RESULT_OK);
                        finish();
                    }
                    finish();
                    Log.i(TAG, "houseId: " + houseId);
                    Intent intent = new Intent(InitializationRoomActivity.this, KjCzfInfoActivity.class);
                    intent.putExtra("HouseID", houseId);
                    startActivity(intent);
                    break;
                case ROOMTO:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        pb.incrementProgressBy(1);
                        to = to + 1;
                        tv_pb.setText("当前进度:" + to + "/" + max);
                    }
                    break;
                case ERROR:
                    Toast.makeText(InitializationRoomActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization_room);
        houseId = getIntent().getStringExtra("HouseID");
        title();
        init_view();
    }

    private ImageView img_title;

    /**
     * 标题栏
     */
    private void title() {
        img_title = (ImageView) findViewById(R.id.image_back);
        img_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("初始化房间");
    }

    /**
     * 初始化控件
     */
    private void init_view() {
        ll = (LinearLayout) findViewById(R.id.ll_initialization_room);
        pb = (ProgressBar) findViewById(R.id.pb_initialization_room);
        tv_pb = (TextView) findViewById(R.id.tv_initialization_room_pb);
        et_numble = (EditText) findViewById(R.id.et_initialization_room);

        lv = (ListView) findViewById(R.id.lv_initialization_room);
        bt_floor = (Button) findViewById(R.id.bt_initialization_room_floor);
        bt_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_numble.getText().toString().trim();
                if (TextUtils.isEmpty(s) || Integer.valueOf(s) == 0) {
                    Toast.makeText(InitializationRoomActivity.this, "请输入楼层数", Toast.LENGTH_LONG).show();
                    return;
                }
                floor = Integer.parseInt(s);
                numbles = new int[floor];
                lv.setAdapter(new LvAdapter());
//                if (s.equals("") || s == null) {
//                    Toast.makeText(InitializationRoomActivity.this, "请输入楼层数", Toast.LENGTH_LONG).show();
//                    return;
//                } else {
//                    floor = Integer.parseInt(s);
//                    if (floor == 0) {
//                        Toast.makeText(InitializationRoomActivity.this, "请输入楼层数", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                    numbles = new int[floor];
//                    lv.setAdapter(new LvAdapter());
//                }
//                if ()

            }
        });
        bt_room = (Button) findViewById(R.id.bt_initialization_room);
        bt_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bt_room.setClickable(false);
//                bt_floor.setClickable(false);
//                img_title.setClickable(false);
                if (floor == 0) {
                    Toast.makeText(InitializationRoomActivity.this, "请输入楼层数", Toast.LENGTH_LONG).show();
//                    bt_room.setClickable(true);
//                    bt_floor.setClickable(true);
                    return;
                }
                ll.setVisibility(View.VISIBLE);
                max = 0;
                to = 0;
                for (int i = 0; i < numbles.length; i++) {
                    int f = i + 1;
                    max = max + numbles[i];
                    if (numbles[i] == 0) {
                        Toast.makeText(InitializationRoomActivity.this, f + "楼未设置房间数", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                pb.setMax(max);
                tv_pb.setText("当前进度:" + to + "/" + max);
                add();

            }
        });
    }

    /**
     * listview
     */
    class LvAdapter extends BaseAdapter {
        private class ViewHolder {
            TextView tv_name;
            EditText et_numble;
            int ref;
        }

        @Override
        public int getCount() {
            return floor;
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
            final ViewHolder mViewHolder;
            if (convertView == null) {
                mViewHolder = new ViewHolder();
                convertView = convertView.inflate(InitializationRoomActivity.this, R.layout.initialization_room_list, null);
                mViewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_initialization_room_list);
                mViewHolder.et_numble = (EditText) convertView.findViewById(R.id.et_initialization_room_list);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ViewHolder) convertView.getTag();
            }
            mViewHolder.ref = position;
            mViewHolder.tv_name.setText(position + 1 + "楼");
            mViewHolder.et_numble.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = s.toString();
                    if (text.equals("")) {
                        numbles[mViewHolder.ref] = 0;
                    } else {
                        numbles[mViewHolder.ref] = Integer.parseInt(text);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            return convertView;
        }
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getAction() == KeyEvent.ACTION_DOWN) {
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private void add() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Param_ChuZuWu_AddRoomList addRoom = new Param_ChuZuWu_AddRoomList();
                addRoom.setHOUSEID(houseId);
                addRoom.setTaskID("1");
                List<Param_ChuZuWu_AddRoomList.ROOMLISTBean> roomlist = new ArrayList<Param_ChuZuWu_AddRoomList.ROOMLISTBean>();

                for (int i = 0; i < numbles.length; i++) {
                    int f = i + 1;
                    for (int r = 1; r <= numbles[i]; r++) {
                        int no = f * 100 + r;
                        Param_ChuZuWu_AddRoomList.ROOMLISTBean roomlistBean = new Param_ChuZuWu_AddRoomList.ROOMLISTBean();
                        roomlistBean.setROOMID(MyUtil.getUUID());
                        roomlistBean.setROOMNO(no);
                        roomlist.add(roomlistBean);
//                        AddRoom addRoom = new AddRoom();
//                        addRoom.setROOMNO(no + "");
//                        addRoom.setROOMID(MyUtil.getUUID());
//                        addRoom.setHOUSEID(houseId);
//                        addRoom.setTaskID("1");
//                        addlist.add(addRoom);
                    }
                }
                addRoom.setROOMLIST(roomlist);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("token", UserService.getInstance(InitializationRoomActivity.this).getToken());
                param.put("encryption", 0);
                param.put("dataTypeCode", "ChuZuWu_AddRoomList");
                param.put("content", gson.toJson(addRoom));
                Log.e("(addRoom).toString()", gson.toJson(addRoom).toString());
                try {
                    String result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    Log.e("initadd", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("content", rootObject.getString("Content"));
                    bundle.putString("resultText", rootObject.getString("ResultText"));
                    msg.setData(bundle);
                    msg.what = ROOMADD;
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
