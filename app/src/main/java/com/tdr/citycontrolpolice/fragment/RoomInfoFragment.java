package com.tdr.citycontrolpolice.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.activity.ModifyRoomActivity;
import com.tdr.citycontrolpolice.base.KjBaseFragment;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_RoomInfo;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;
import com.tdr.citycontrolpolice.util.UserService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/6/21 10:04
 * 修改备注：
 */
public class RoomInfoFragment extends KjBaseFragment {
    private HashMap<String, Object> mParam = new HashMap<>();
    private String mRoomId;
    private String mRoomNo;
    private Unbinder bind;

    private TextView mTvNumber;
    private TextView mTvType;
    private TextView mTvArea;
    private TextView mTvFixture;
    private TextView mTvPrice;
    private TextView mTvDeposit;
    private TextView mTvGalleryful;
    private TextView mTvModify;
    private ChuZuWu_RoomInfo.ContentBean roomInfo;


    public static RoomInfoFragment newInstance(String roomId, String rommNo) {
        RoomInfoFragment personInfoFragment = new RoomInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ROOM_ID", roomId);
        bundle.putString("ROOM_NO", rommNo);
        personInfoFragment.setArguments(bundle);
        return personInfoFragment;

    }

    @Override
    public View onFragmentCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_room_info, container, false);
        bind = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initFragmentVariables() {
        EventBus.getDefault().register(this);
        Bundle bundle = getArguments();
        mRoomId = bundle.getString("ROOM_ID");
        mRoomNo = bundle.getString("ROOM_NO");

        mParam.put("TaskID", "1");
        mParam.put("ROOMID", mRoomId);

    }

    @Override
    protected void initFragmentView() {
        mTvNumber = (TextView) rootView.findViewById(R.id.tv_number);
        mTvType = (TextView) rootView.findViewById(R.id.tv_type);
        mTvArea = (TextView) rootView.findViewById(R.id.tv_Area);
        mTvFixture = (TextView) rootView.findViewById(R.id.tv_fixture);
        mTvPrice = (TextView) rootView.findViewById(R.id.tv_price);
        mTvDeposit = (TextView) rootView.findViewById(R.id.tv_deposit);
        mTvGalleryful = (TextView) rootView.findViewById(R.id.tv_galleryful);
        mTvModify = (TextView) rootView.findViewById(R.id.tv_modify);
    }

    @Override
    protected void initFragmentNet() {
        setProgressDialog(true);
        new ThreadPoolTask.Builder()
                .setGeneralParam(UserService.getInstance(getActivity()).getToken(), 0, "ChuZuWu_RoomInfo", mParam)
                .setBeanType(ChuZuWu_RoomInfo.class)
                .setCallBack(new WebServiceCallBack<ChuZuWu_RoomInfo>() {
                    @Override
                    public void onSuccess(ChuZuWu_RoomInfo bean) {
                        setProgressDialog(false);
                        fillRoomInfo(bean);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void fillRoomInfo(ChuZuWu_RoomInfo bean) {
        roomInfo = bean.getContent();
        if (roomInfo == null) {
            return;
        }
        Basic_Dictionary_Kj deposit = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst
                (Basic_Dictionary_Kj.class, "COLUMNCODE", "DEPOSIT", "COLUMNVALUE", roomInfo.getDEPOSIT() + "");
        Basic_Dictionary_Kj fixture = (Basic_Dictionary_Kj) DbDaoXutils3.getInstance().sleectFirst
                (Basic_Dictionary_Kj.class, "COLUMNCODE", "FIXTURE", "COLUMNVALUE", roomInfo.getFIXTURE() + "");
        mTvNumber.setText(roomInfo.getROOMNO());
        mTvType.setText(roomInfo.getSHI() + "室" + roomInfo.getTING() + "厅" + roomInfo.getWEI() + "卫" + roomInfo
                .getYANGTAI() + "阳台");
        mTvArea.setText(roomInfo.getSQUARE() + "平米");
        mTvFixture.setText(fixture.getCOLUMNCOMMENT());
        mTvPrice.setText(roomInfo.getPRICE() + "元/月");
        mTvDeposit.setText(deposit.getCOLUMNCOMMENT());
        mTvGalleryful.setText(roomInfo.getGALLERYFUL() + "人");
    }

    @Override
    protected void initFragmentData() {
        mTvModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyRoomActivity.goActivity(getActivity(), roomInfo);
            }
        });
    }

    @Override
    protected void setFragmentData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        bind.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetRoomInfo(ChuZuWu_RoomInfo roomInfo) {
        initFragmentNet();
    }

}
