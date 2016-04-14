package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.util.DipPx;


/**
 * Created by Administrator on 2016/2/25.
 */
public class RoomInsideActivity extends Activity {
    private String[] date = {};
    private ExpandableListView expandableListView;
    private String roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_inside);
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
        tv_title.setText("房间内部");
        ImageView img_add = (ImageView) findViewById(R.id.image_add);
        img_add.setBackgroundResource(R.mipmap.header_menu_on);
        img_add.setVisibility(View.VISIBLE);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_top(v);
            }
        });
    }

    /**
     * 初始化组件
     */
    private void init_view() {
        expandableListView = (ExpandableListView) findViewById(R.id.elv_room_inside);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(new ExpandApadter());
    }

    /**
     * 初始化数据
     */
    private void init_data() {
        roomId = getIntent().getStringExtra("ROOMID");
    }

    /**
     * exListView
     */
    class ExpandApadter extends BaseExpandableListAdapter {

        @Override
        //获得父数量
        public int getGroupCount() {
            return date.length;
        }

        @Override
        //获得子数量
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        //获取父数据
        public Object getGroup(int groupPosition) {
            return date[groupPosition];
        }

        @Override
        //获取子数据
        public Object getChild(int groupPosition, int childPosition) {

            return childPosition;
        }

        @Override
        //获得父ID
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        //获得子ID
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        //获得父类布局
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) RoomInsideActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.room_inside_parent, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.tv_room_inside_parent_name);
            tv.setText(RoomInsideActivity.this.date[groupPosition]);
            ImageView img_up = (ImageView) convertView.findViewById(R.id.img_room_inside_parent_up);
            if (isExpanded) {
                img_up.setImageResource(R.mipmap.down);
            } else {
                img_up.setImageResource(R.mipmap.up);
            }
            return convertView;
        }

        //获得子类布局
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) RoomInsideActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.room_inside_children, null);
            }
            return convertView;

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    //设置添加店铺
    private void pop_top(View v) {
        View contentView = LayoutInflater.from(RoomInsideActivity.this).inflate(
                R.layout.pop_room_inside, null);
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
                Intent intent = new Intent(RoomInsideActivity.this, RoomModifyActivity.class);
                intent.putExtra("ROOMID", roomId);
                startActivity(intent);
            }
        });
        popupWindow.showAtLocation(v, Gravity.TOP, 0, DipPx.dip2px(RoomInsideActivity.this, 50));
    }
}
