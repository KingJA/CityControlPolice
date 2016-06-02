package com.tdr.citycontrolpolice.view.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.CheckUtil;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/8 16:03
 * 修改备注：
 */
public class KingJA_AddNextRoom extends LinearLayout {
    private static final String TAG = "KingJA_AddNextRoom";
    private Context context;
    private EditText et_room;
    private String room;

    public KingJA_AddNextRoom(Context context) {
        this(context, null);
        this.context = context;
        initView();
    }

    public KingJA_AddNextRoom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KingJA_AddNextRoom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    /**
     * 初始化布局
     */
    private void initView() {
        View view = View.inflate(context, R.layout.add_next_room, null);
        et_room = (EditText) view.findViewById(R.id.et_room);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(view, layoutParams);

    }

    public String getAddRoom() {
        room = et_room.getText().toString().trim();
        if (!CheckUtil.checkEmpty(room, "房间号不能为空") || !CheckUtil.checkZero(room) || !CheckUtil.checkLengthMin(room, 3, "房间号格式错误")) {
            Log.i(TAG, "输入有误: ");
            return "";
        }
        return room;
    }


}
