package com.tdr.citycontrolpolice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.CheckUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/8 16:03
 * 修改备注：
 */
public class KingJA_AddNextLine extends LinearLayout {
    private static final String TAG = "KingJA_AddNextLine";
    private Context context;
    private EditText et_floor;
    private EditText et_room;
    private String floor;
    private String room;
    private List<String> roomlist;


    public KingJA_AddNextLine(Context context) {
        this(context,null);
        this.context = context;
        initView();
    }

    public KingJA_AddNextLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KingJA_AddNextLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 初始化布局
     */
    private void initView() {
        View view = View.inflate(context, R.layout.add_next_line, null);
        et_floor = (EditText) view.findViewById(R.id.et_floor);
        et_room = (EditText) view.findViewById(R.id.et_room);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view, layoutParams);

    }

    public String getAddRoom() {
        floor = et_floor.getText().toString().trim();
        room = et_room.getText().toString().trim();
        if (!CheckUtil.checkEmpty(floor, "楼层数不能为空") || !CheckUtil.checkEmpty(room, "房间序号不能为空") || !CheckUtil.checkZero(floor) || !CheckUtil.checkZero(room)) {
            Log.i(TAG, "输入有误: ");
            return "";
        }
        return floor + String.format("%02d", Integer.valueOf(room));
    }
    /**
     * 获取房间号码
     * @return
     */
    public List<String> getInitRooms() {
        floor = et_floor.getText().toString().trim();
        room = et_room.getText().toString().trim();
        Log.i(TAG, "floor: " + floor + "room: " + room);
        if (!CheckUtil.checkEmpty(floor,"楼层数不能为空")||!CheckUtil.checkEmpty(room,"房间数不能为空")||!CheckUtil.checkZero(floor)||!CheckUtil.checkZero(room)) {
            Log.i(TAG, "输入有误: ");
          return null;
        }

        roomlist = new ArrayList<>();
        for (int i = 1; i <= Integer.valueOf(room); i++) {
            String room = floor + String.format("%02d", i);
            Log.i(TAG, "getInitRooms: " + room);
            roomlist.add(room);
        }
        return roomlist;
    }

    public String getFloor() {
        floor = et_floor.getText().toString().trim();
        if (!CheckUtil.checkEmpty(floor, "楼层不能为空")) {
            return null;
        }
        return floor;
    }

}
