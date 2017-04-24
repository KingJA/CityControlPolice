package com.tdr.citycontrolpolice.fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.entity.ChuZuWu_DevicePeopleNumberChange;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.TimeUtil;
import com.tdr.citycontrolpolice.util.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import lib.kingja.switchbutton.SwitchMultiButton;

/**
 * 项目名称：
 * 类描述：历史人数
 * 创建人：KingJA
 * 创建时间：2016/6/21 10:04
 * 修改备注：
 */
public class PersonHistoryFragment extends KjBaseFragment {
    @Bind(R.id.smb)
    SwitchMultiButton smb;
    @Bind(R.id.chart)
    LineChartView chart;
    private String mRoomId;
    private List<PointValue> mPointValuesList = new ArrayList<>();
    private List<AxisValue> mAxisXValuesList = new ArrayList<>();

    public static PersonHistoryFragment newInstance(String houseId, String roomId) {
        PersonHistoryFragment personAccreditFragment = new PersonHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("HOUSE_ID", houseId);
        bundle.putString("ROOM_ID", roomId);
        personAccreditFragment.setArguments(bundle);
        return personAccreditFragment;

    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person_history, container, false);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {
        Bundle bundle = getArguments();
        mHouseId = bundle.getString("HOUSE_ID");
        mRoomId = bundle.getString("ROOM_ID");
    }

    @Override
    protected void initFragmentView() {
        smb.setText(Arrays.asList("15天", "30天")).setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if (position == 0) {
                    loadNet(TimeUtil.getFormatDate(), "15");
                } else {
                    loadNet(TimeUtil.getFormatDate(), "30");
                }
            }
        });
    }

    @Override
    protected void initFragmentNet() {
        loadNet(TimeUtil.getFormatDate(), "15");
    }

    private void loadNet(String calculatedate, String interval) {
        setProgressDialog(true);
        Map<String, Object> mParam = new HashMap<>();
        mParam.put("TaskID", "1");
        mParam.put("HOUSEID", mHouseId);
        mParam.put("ROOMID", mRoomId);
        mParam.put("CALCULATEDATE", calculatedate);
        mParam.put("INTERVAL", interval);
        mParam.put("PageSize", interval);
        mParam.put("PageIndex", 0);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_DevicePeopleNumberChange", mParam)
                .setBeanType(ChuZuWu_DevicePeopleNumberChange.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_DevicePeopleNumberChange>() {
                    @Override
                    public void onSuccess(ChuZuWu_DevicePeopleNumberChange bean) {
                        setProgressDialog(false);
                        List<ChuZuWu_DevicePeopleNumberChange.ContentBean.PERSONNELINFOLISTBean> personnelInfoList = bean.getContent().getPERSONNELINFOLIST();
                        chart.setVisibility(personnelInfoList.size() > 0 ? View.VISIBLE : View.GONE);
                        initChartDate(personnelInfoList);
                        initChart();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);

                    }
                }).build().execute();
    }

    @Override
    protected void initFragmentData() {
    }


    @Override
    protected void setFragmentData() {

    }


    private void initChart() {
        List<Line> lines = new ArrayList<>();//一条连续的折线
        Line line = new Line(mPointValuesList);
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setColor(Color.parseColor("#5890FF"));//折线颜色，直接影响填充和数字显示框的颜色
        line.setFilled(true);//是否填充曲线的面积
        line.setHasLabels(true);//圆点附近显示数值备注,和setHasLabelsOnlyForSelected互斥
//        line.setHasLabelsOnlyForSelected(true);//点击显示数值
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.parseColor("#999999"));//设置字体颜色
        axisX.setLineColor(Color.parseColor("#999999"));//设置坐标轴颜色,垂直
        axisX.setTextSize(11);//设置字体大小
        axisX.setValues(mAxisXValuesList);  //填充X轴的坐标名称
        axisX.setHasLines(false); //坐标轴分割线
        data.setAxisXBottom(axisX); //x 轴在底部

        //Y轴
        Axis axisY = new Axis();
        axisY.setName("");//y轴标注
        axisY.setTextColor(Color.parseColor("#999999"));//设置字体颜色
        axisY.setLineColor(Color.parseColor("#eeeeee"));//设置坐标轴颜色,水平
        axisY.setTextSize(11);//设置字体大小
        axisY.setHasLines(true); //坐标轴分割线
        data.setAxisYLeft(axisY);  //Y轴设置在左边

        //设置行为属性，支持缩放、滑动以及平移
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        chart.setMaxZoom((float) 3);//缩放比例
        chart.setLineChartData(data);
        chart.setVisibility(View.VISIBLE);
        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = 20;
        v.left = 0;
        v.right = mAxisXValuesList.size();
        chart.setMaximumViewport(v);
        chart.setCurrentViewport(v);
    }

    private void initChartDate(List<ChuZuWu_DevicePeopleNumberChange.ContentBean.PERSONNELINFOLISTBean> personnelInfoList) {
        mPointValuesList.clear();
        mAxisXValuesList.clear();
        for (int i = 0; i < personnelInfoList.size(); i++) {
            mAxisXValuesList.add(new AxisValue(i).setLabel(personnelInfoList.get(i).getCALCULATEDATE()));
            mPointValuesList.add(new PointValue(i, Integer.valueOf(personnelInfoList.get(i).getFORECASTNUMBER())));
        }
    }
}
