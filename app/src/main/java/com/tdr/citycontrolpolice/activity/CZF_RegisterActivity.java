package com.tdr.citycontrolpolice.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Administrator;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary;
import com.tdr.citycontrolpolice.entity.Basic_JuWeiHui;
import com.tdr.citycontrolpolice.entity.Basic_PaiChuSuo;
import com.tdr.citycontrolpolice.entity.Basic_StandardAddressCodeByKey;
import com.tdr.citycontrolpolice.entity.ChuZuWu_Add;
import com.tdr.citycontrolpolice.entity.ChuZuiWu_GETSSYBYSTANDADDRESSCODE;
import com.tdr.citycontrolpolice.entity.Deivce;
import com.tdr.citycontrolpolice.entity.Photo;
import com.tdr.citycontrolpolice.util.CheckUtil;
import com.tdr.citycontrolpolice.util.Constants;
import com.tdr.citycontrolpolice.util.MyUtil;
import com.tdr.citycontrolpolice.util.ToastUtil;
import com.tdr.citycontrolpolice.util.UserService;
import com.tdr.citycontrolpolice.util.WebService;
import com.tdr.citycontrolpolice.view.ZProgressHUD;
import com.tdr.citycontrolpolice.view.niftydialog.NiftyDialogBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/22.
 */
public class CZF_RegisterActivity extends Activity {
    private NfcAdapter nfcAdapter;
    private ImageView img_dz, img_fw, img_address, img_fdnfc, img_glynfc, img_pop;
    private String fdphone, glyname, glycard, glyphone, fdname, fdcard, czfname, czftype, xqname;
    private String address = "";
    private Button bt_submit;
    private Bitmap bitmap_dz, bitmap_fw;
    private EditText et_fdphone, et_glyname, et_glycard, et_glyphone, et_pop, et_czfname, et_czfaddress, et_xqname;
    private ListView lv_pop;
    private TextView tv_fdname, tv_fdcard, tv_pop, tv_xqpcs, tv_czftype;
    private LinearLayout linear_gly;
    private List<Basic_StandardAddressCodeByKey> addressList;
    private Basic_StandardAddressCodeByKey basic_StandardAddressCodeByKey, standardAddressCodeByKey;
    private Photo dz_photo, fw_photo;
    private ChuZuiWu_GETSSYBYSTANDADDRESSCODE chuZuWuModifyRoom;
    private List<Deivce> deivceList;
    private ZProgressHUD progressHUD;
    private Basic_JuWeiHui basicJuWeiHui;
    private List<Basic_Dictionary> houseType;
    private final static int TEXTCHANGED = 1001;
    private final static int SANSHI = 1002;
    private final static int CHUZUWUADD = 1003;
    private final static int ERROR = 4001;
    private int imgType;
    private DbUtils db;
    private boolean isChecked;
    private Gson gson = new Gson();
    private Handler mHandler = new Handler() {
        public static final String TAG = "CZF_RegisterActivity";

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TEXTCHANGED:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        ArrayList<Basic_StandardAddressCodeByKey> result = gson.fromJson(data,
                                new TypeToken<ArrayList<Basic_StandardAddressCodeByKey>>() {
                                }.getType());
                        if (result.size() == 0) {
                            addressList = new ArrayList<Basic_StandardAddressCodeByKey>();
                            tv_pop.setText("没有相关的地址");
                            et_fdphone.setText("");
                            tv_fdname.setText("");
                            tv_fdcard.setText("");
                        } else {
                            addressList = result;
                            lv_pop.setAdapter(new AddressAdapter());
                            tv_pop.setVisibility(View.GONE);
                            img_pop.setClickable(true);
                            lv_pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    basic_StandardAddressCodeByKey = addressList.get(position);
                                    et_pop.setText(basic_StandardAddressCodeByKey.getAddress().substring(6));
                                    // et_czfname.setText(basic_StandardAddressCodeByKey.getAddressPath());
                                    String add = basic_StandardAddressCodeByKey.getAddressPath();
                                    String[] Add = add.split("/");
                                    String temp_add = "";
                                    for (int i = 5; i < Add.length; i++) {
                                        temp_add += Add[i];
                                    }
                                    et_czfname.setText(temp_add);
                                }
                            });
                        }

                    }
                    break;
                case SANSHI:
                    if (msg.getData().getInt("error") == 0) {
                        String data = msg.getData().getString("content");
                        Log.i(TAG, "data: " + data);
                        if ("{}".equals(data)) {
                            ToastUtil.showMyToast("该地址未在三实有系统中登记为出租房");
                            return;
                        }
                        chuZuWuModifyRoom = gson.fromJson(data,
                                new TypeToken<ChuZuiWu_GETSSYBYSTANDADDRESSCODE>() {
                                }.getType());
                        if (chuZuWuModifyRoom == null) {
                            ToastUtil.showMyToast("该地址未在三实有系统中登记为出租房");
                            return;
                        }
                        tv_fdcard.setText(chuZuWuModifyRoom.getIDENTITYCARD());
                        tv_fdname.setText(chuZuWuModifyRoom.getOWNERNAME());
                        et_fdphone.setText(chuZuWuModifyRoom.getPHONE());
                        try {
                            basicJuWeiHui = db.findFirst(Selector.from(Basic_JuWeiHui.class)
                                    .where("DMZM", "=", chuZuWuModifyRoom.getJWHCODE()));
                            Basic_PaiChuSuo paiChuSuo = db.findFirst(Selector.from(Basic_PaiChuSuo.class)
                                    .where("DMZM", "=", basicJuWeiHui.getFDMZM()));

                            tv_xqpcs.setText(paiChuSuo.getDMMC());
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case CHUZUWUADD:
                    progressHUD.dismiss();
                    if (msg.getData().getInt("error") == 0) {
                        /*Toast.makeText(CZF_RegisterActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                        finish();*/
                        dialogShow(2, "添加成功，是否查看出租房信息？");
                    } else {
                        Toast.makeText(CZF_RegisterActivity.this, msg.getData().getString("resultText"), Toast.LENGTH_LONG).show();

                    }
                    break;
                case ERROR:
                    Toast.makeText(CZF_RegisterActivity.this, "网络异常", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private String houseId = "";
    private CheckBox cb;
    private String temp_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czf_register);
        title();
        init_data();
        init_view();

        houseId = MyUtil.getUUID();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                //图库返回
                case IMAGE_REQUEST_CODE:
                    try {
                        //本地取图先进行切图
                        ContentResolver resolver = getContentResolver();
                        Bitmap bm = MediaStore.Images.Media.getBitmap(resolver, data.getData());
                        if (imgType == 1) {
                            bitmap_dz = MyUtil.thumbnailBitmap(bm);
                            img_dz.setImageBitmap(bitmap_dz);
                            dz_photo = new Photo();
                            dz_photo.setLISTID(MyUtil.getUUID());
                            dz_photo.setTAG("电子门牌");
                            dz_photo.setIMAGE(MyUtil.bitmapToBase64(bitmap_dz));
                        } else {
                            bitmap_fw = MyUtil.thumbnailBitmap(bm);
                            img_fw.setImageBitmap(bitmap_fw);
                            fw_photo = new Photo();
                            fw_photo.setLISTID(MyUtil.getUUID());
                            fw_photo.setTAG("房屋外景");
                            fw_photo.setIMAGE(MyUtil.bitmapToBase64(bitmap_fw));
                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                //处理摄像机
                case CAMERA_REQUEST_CODE:
                    if (MyUtil.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory() + "/"
                                        + IMAGE_FILE_NAME);
//                        Bitmap bm= BitmapFactory.decodeFile(tempFile.toString());
                        if (imgType == 1) {
                            bitmap_dz = MyUtil.getBitmapFromFile(tempFile, 400, 600);
                            img_dz.setImageBitmap(bitmap_dz);
                            dz_photo = new Photo();
                            dz_photo.setLISTID(MyUtil.getUUID());
                            dz_photo.setIMAGE(MyUtil.bitmapToBase64(bitmap_dz));
                        } else {
                            bitmap_fw = MyUtil.getBitmapFromFile(tempFile, 400, 600);
                            img_fw.setImageBitmap(bitmap_fw);
                            fw_photo = new Photo();
                            fw_photo.setLISTID(MyUtil.getUUID());
                            fw_photo.setIMAGE(MyUtil.bitmapToBase64(bitmap_fw));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }

                    break;
            }
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
                dialogShow(1, "信息编辑中，确认离开该页面？");

            }
        });
        TextView tv_title = (TextView) findViewById(R.id.text_title);
        tv_title.setText("出租房登记");
    }

    /**
     * 初始化组件
     */
    private String mStr_glyname = "";
    private String mStr_glyphone = "";
    private String mStr_glycard = "";

    private void init_view() {
        progressHUD = new ZProgressHUD(CZF_RegisterActivity.this);
        progressHUD.setMessage("提交中");
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
//img
        img_fdnfc = (ImageView) findViewById(R.id.img_czf_register_fdnfc);
        img_glynfc = (ImageView) findViewById(R.id.img_czf_register_glynfc);
        img_dz = (ImageView) findViewById(R.id.img_czf_register_dz);
        img_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgType = 1;
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (MyUtil.hasSdcard()) {

                    intentFromCapture.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    IMAGE_FILE_NAME)));
                }

                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);
                //showDialog();
            }
        });
        img_fw = (ImageView) findViewById(R.id.img_czf_register_fw);
        img_fw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgType = 0;
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (MyUtil.hasSdcard()) {

                    intentFromCapture.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    IMAGE_FILE_NAME)));
                }

                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);
                //showDialog();
            }
        });
        img_address = (ImageView) findViewById(R.id.img_czf_register_address);
        img_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_address(v);
            }
        });

//button
        bt_submit = (Button) findViewById(R.id.bt_czf_register_submit);
        bt_submit.setOnClickListener(new SubmitOnClick());
//textview
        tv_fdname = (TextView) findViewById(R.id.tv_czf_register_fdname);
        tv_fdcard = (TextView) findViewById(R.id.tv_czf_register_fdcard);
        tv_xqpcs = (TextView) findViewById(R.id.tv_czf_register_xqpcs);
        tv_czftype = (TextView) findViewById(R.id.tv_czf_register_czftype);

        tv_czftype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop_text(v);
            }
        });
//edit
        et_czfname = (EditText) findViewById(R.id.et_czf_register_czfname);
        et_czfaddress = (EditText) findViewById(R.id.et_czf_register_czfaddress);
        et_xqname = (EditText) findViewById(R.id.et_czf_register_xqname);
        et_fdphone = (EditText) findViewById(R.id.et_czf_register_fdphone);
        et_glyname = (EditText) findViewById(R.id.et_czf_register_glyname);
        et_glycard = (EditText) findViewById(R.id.et_czf_register_glycard);
        et_glyphone = (EditText) findViewById(R.id.et_czf_register_glyphone);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null || !nfcAdapter.isEnabled()) {
            img_fdnfc.setImageResource(R.mipmap.nfc_off);
            img_glynfc.setImageResource(R.mipmap.nfc_off);
        }
        linear_gly = (LinearLayout) findViewById(R.id.linear_gly);
        //checkbox
        cb = (CheckBox) findViewById(R.id.cb_czf_register);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CZF_RegisterActivity.this.isChecked = isChecked;
                    fdname = tv_fdname.getText().toString().trim();
                    fdcard = tv_fdcard.getText().toString().trim();
                    fdphone = et_fdphone.getText().toString().trim();
                    linear_gly.setVisibility(View.GONE);

                    et_glyname.setText(fdname);
                    et_glycard.setText(fdcard);
                    et_glyphone.setText(fdphone);
                } else {
                    linear_gly.setVisibility(View.VISIBLE);
                    et_glyname.setText("");
                    et_glycard.setText("");
                    et_glyphone.setText("");

                }
            }
        });
    }

    /**
     * 初始数据
     */
    private void init_data() {
        czftype = "";
        deivceList = new ArrayList<Deivce>();
        Deivce deivce = new Deivce();
        Intent intent = getIntent();
        deivce.setDEVICENAME("二维码门牌");
        deivce.setDEVICEID(MyUtil.getUUID());
        deivce.setDEVICECODE(intent.getStringExtra("DEVICECODE"));
        deivce.setDEVICETYPE(intent.getStringExtra("DEVICETYPE"));
        deivceList.add(deivce);
        db = DbUtils.create(this, "citypolice.db");
        try {
            houseType = db.findAll(Selector.from(Basic_Dictionary.class)
                    .where("COLUMNCODE", "=", "HOUSETYPE"));
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 拍照与本地图库
     */
    private String[] items = new String[]{"拍照"};
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final String IMAGE_FILE_NAME = "czw_add.jpg";

    private void showDialog() {
        new AlertDialog.Builder(CZF_RegisterActivity.this)
                .setTitle("图片选择")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
//                            case 0:
//                                Intent intentFromGallery = new Intent();
//                                intentFromGallery.setType("image/*"); // 设置文件类型
//                                intentFromGallery
//                                        .setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(intentFromGallery,
//                                        IMAGE_REQUEST_CODE);
//                                break;
                            case 0:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                // 判断存储卡是否可以用，可用进行存储
                                if (MyUtil.hasSdcard()) {

                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    IMAGE_FILE_NAME)));
                                }

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    /**
     * 提交按钮
     */
    class SubmitOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            czfname = et_czfname.getText().toString().trim();
            if (czfname.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入出租房名称", Toast.LENGTH_LONG).show();
                return;
            }
            if (czftype.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请选择出租房类型", Toast.LENGTH_LONG).show();
                return;
            }

            if (address.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请选择出租房地址", Toast.LENGTH_LONG).show();
                return;
            }
            fdname = tv_fdname.getText().toString().trim();
            if (fdname.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入房东姓名", Toast.LENGTH_LONG).show();
                return;
            }
            fdcard = tv_fdcard.getText().toString().trim().toUpperCase();
            if (fdcard.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入房东身份证", Toast.LENGTH_LONG).show();
                return;
            } else if (!Constants.isIDCard18(fdcard)) {
                Toast.makeText(CZF_RegisterActivity.this, "房东身份证有误", Toast.LENGTH_LONG).show();
                return;
            }

            fdphone = et_fdphone.getText().toString().trim();
            if (fdphone.equals("") || fdphone == null) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入房东手机号", Toast.LENGTH_LONG).show();
                return;
            } else if (!MyUtil.isPhone(fdphone)) {
                Toast.makeText(CZF_RegisterActivity.this, "输入的房东手机号可能有误", Toast.LENGTH_LONG).show();
                return;
            }
            glyname = et_glyname.getText().toString().trim();
            if (glyname.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入管理员姓名", Toast.LENGTH_LONG).show();
                return;
            }
            glycard = et_glycard.getText().toString().trim();
            if (glycard.equals("")) {
                Toast.makeText(CZF_RegisterActivity.this, "请输入管理员身份证", Toast.LENGTH_LONG).show();
                return;
            } else if (!Constants.isIDCard18(glycard)) {
                Toast.makeText(CZF_RegisterActivity.this, "管理员身份证有误", Toast.LENGTH_LONG).show();
                return;
            }

            glyphone = et_glyphone.getText().toString().trim();
            if (cb.isChecked()) {
                if (!CheckUtil.checkPhoneFormat(fdphone)) {
                    return;
                }
//                if (fdphone.equals("") || fdphone == null) {
//                    Toast.makeText(CZF_RegisterActivity.this, "请输入房东手机号", Toast.LENGTH_LONG).show();
//                    return;
//                } else if (!MyUtil.isPhone(fdphone)) {
//                    Toast.makeText(CZF_RegisterActivity.this, "输入的房东手机号可能有误", Toast.LENGTH_LONG).show();
//                    return;
//                }
            } else {
                if (glyphone.equals("") || glyphone == null) {
                    Toast.makeText(CZF_RegisterActivity.this, "请输入管理员手机号", Toast.LENGTH_LONG).show();
                    return;
                } else if (!MyUtil.isPhone(glyphone)) {
                    Toast.makeText(CZF_RegisterActivity.this, "输入的管理员手机号可能有误", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            //if (dz_photo == null) {
            //   Toast.makeText(CZF_RegisterActivity.this, "请上传电子门牌", Toast.LENGTH_SHORT).show();
            // return;
            //}
//            if (fw_photo == null) {
//                Toast.makeText(CZF_RegisterActivity.this, "请上传房屋外景", Toast.LENGTH_SHORT).show();
//                return;
//            }

            if (!isFinishing()) {
                dialogShow(0, "确认提交该出租房信息？");
            }

        }
    }

    /**
     * 搜索框
     *
     * @param v
     */
    private void pop_address(View v) {
        View contentView = LayoutInflater.from(CZF_RegisterActivity.this).inflate(
                R.layout.pop_address, null);
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
        lv_pop = (ListView) contentView.findViewById(R.id.lv_pop_address);
        et_pop = (EditText) contentView.findViewById(R.id.et_pop_address);
        tv_pop = (TextView) contentView.findViewById(R.id.tv_pop_address_content);
        img_pop = (ImageView) contentView.findViewById(R.id.img_pop_address);
        img_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = et_pop.getText().toString().trim();
                if (content.equals("")) {
                    return;
                }
                tv_pop.setVisibility(View.VISIBLE);
                tv_pop.setText("搜索中");
                search(content);
            }
        });

        //取消
        Button bt_pop_cancel = (Button) contentView.findViewById(R.id.bt_pop_address_cancel);

        bt_pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //确定
        Button bt_pop_confirm = (Button) contentView.findViewById(R.id.bt_pop_address_confirm);
        bt_pop_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String address = et_pop.getText().toString().trim();
                if (!address.equals("") && basic_StandardAddressCodeByKey != null) {
                    et_czfaddress.setText(address);
                    standardAddressCodeByKey = basic_StandardAddressCodeByKey;
                    sanshi(standardAddressCodeByKey.getId());
                    popupWindow.dismiss();
                    cb.setChecked(false);
                    et_czfname.setText(temp_add);

                } else {
                    Toast.makeText(CZF_RegisterActivity.this, "未选择地址", Toast.LENGTH_LONG).show();
                }

            }
        });


        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    /**
     * 三实
     */
    private void sanshi(final String STANDARDADDRCODE) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("STANDARDADDRCODE", STANDARDADDRCODE);
                    Log.e("code", object.toString());
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("token", UserService.getInstance(CZF_RegisterActivity.this).getToken());
                    param.put("encryption", 0);
                    param.put("dataTypeCode", "CHUZUWU_GETSSYBYSTANDADDRESSCODE");
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
                    msg.what = SANSHI;
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
     * 搜索
     */
    private void search(final String s) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject object = new JSONObject();
                try {
                    object.put("TaskID", "1");
                    object.put("KEY", s);
                    object.put("PageSize", "20");
                    object.put("PageIndex", "0");
                    Log.e("code", object.toString());
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("token", UserService.getInstance(CZF_RegisterActivity.this).getToken());
                    param.put("encryption", 0);
                    param.put("dataTypeCode", "Basic_StandardAddressCodeByKey");
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
                    msg.what = TEXTCHANGED;
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
     * poplistview
     */
    class AddressAdapter extends BaseAdapter {
        int i = -1;

        class ViewHolder {
            RadioButton rdBtn;
        }

        @Override
        public int getCount() {
            return addressList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = convertView.inflate(CZF_RegisterActivity.this, R.layout.pop_address_list, null);
                holder = new ViewHolder();
                holder.rdBtn = (RadioButton) convertView.findViewById(R.id.rbt_pop_address_list);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.rdBtn.setText(addressList.get(position).getAddress().substring(6));
            holder.rdBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                private String tempName;

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        i = position;
                        basic_StandardAddressCodeByKey = addressList.get(position);
                        address = basic_StandardAddressCodeByKey.getAddress().substring(6);
                        String add = basic_StandardAddressCodeByKey.getAddressPath();
                        String[] Add = add.split("/");
                        temp_add = "";
                        for (int i = 5; i < Add.length; i++) {
                            temp_add += Add[i];
                        }
//                        String tempName= temp_add;
//                        et_czfname.setText(temp_add);
                        notifyDataSetChanged();
                    }
                }
            });

            if (i == position) {
                holder.rdBtn.setChecked(true);
            } else {
                holder.rdBtn.setChecked(false);
            }
            return convertView;
        }
    }

    /**
     * pop
     *
     * @param v
     */
    private void pop_text(View v) {
        View contentView = LayoutInflater.from(CZF_RegisterActivity.this).inflate(
                R.layout.pop_list, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final ListView lv = (ListView) contentView.findViewById(R.id.lv_pop_listview);
        final ImageView img_up = (ImageView) contentView.findViewById(R.id.img_pop_list_up);
        final ImageView img_down = (ImageView) contentView.findViewById(R.id.img_pop_list_down);
        lv.setAdapter(new ListViewAdapter());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_czftype.setText(houseType.get(position).getCOLUMNCOMMENT());
                czftype = houseType.get(position).getLISTID();
                popupWindow.dismiss();
            }
        });
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

        @Override
        public int getCount() {
            return houseType.size();
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
                convertView = convertView.inflate(CZF_RegisterActivity.this, R.layout.pop_list_list, null);
            }
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_pop_list_list_text);
            tv_name.setText(houseType.get(position).getCOLUMNCOMMENT());

            return convertView;
        }
    }

    /**
     * 出租屋
     */
    private void czwADD() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChuZuWu_Add chuZuWuAdd = new ChuZuWu_Add();
                chuZuWuAdd.setHOUSENAME(czfname);
                chuZuWuAdd.setHOUSEID(houseId);
                chuZuWuAdd.setADDRESS(standardAddressCodeByKey.getAddress().substring(6));
                chuZuWuAdd.setSTANDARDADDRCODE(standardAddressCodeByKey.getId());
                chuZuWuAdd.setHOUSINGESTATE(xqname);
                chuZuWuAdd.setIDENTITYCARD(fdcard);
                chuZuWuAdd.setOWNERNAME(fdname);
                chuZuWuAdd.setHOUSETYPE(czftype);
                chuZuWuAdd.setPHONE(fdphone);
                chuZuWuAdd.setPCSCO(basicJuWeiHui.getFDMZM());
                chuZuWuAdd.setJWHCODE(basicJuWeiHui.getDMZM());
                chuZuWuAdd.setXQCODE(chuZuWuModifyRoom.getJWHCODE().substring(0, 6));
                chuZuWuAdd.setLNG(standardAddressCodeByKey.getX());
                chuZuWuAdd.setLAT(standardAddressCodeByKey.getY());
                chuZuWuAdd.setADMINISTRATORCOUNT("1");
                xqname = et_xqname.getText().toString().trim();
                chuZuWuAdd.setHOUSINGESTATE(xqname);
                Administrator administrator = new Administrator();
                if (cb.isChecked()) {
                    administrator.setPHONE(et_fdphone.getText().toString().trim());
                    administrator.setNAME(tv_fdname.getText().toString().trim());
                    administrator.setIDENTITYCARD(tv_fdcard.getText().toString().trim());
                } else {
                    administrator.setPHONE(et_glyphone.getText().toString().trim());
                    administrator.setNAME(et_glyname.getText().toString().trim());
                    administrator.setIDENTITYCARD(et_glycard.getText().toString().trim());
                }

                List<Administrator> administratorList = new ArrayList<Administrator>();
                administratorList.add(administrator);
                chuZuWuAdd.setADMINISTRATOR(administratorList);
                List<Photo> photoList = new ArrayList<Photo>();
                if (dz_photo != null) {
                    photoList.add(dz_photo);
                }
                if (fw_photo != null) {
                    photoList.add(fw_photo);
                }
                chuZuWuAdd.setPHOTOCOUNT(photoList.size() + "");
                chuZuWuAdd.setPHOTOLIST(photoList);
                chuZuWuAdd.setDEVICECOUNT(deivceList.size() + "");
                chuZuWuAdd.setDEVICELIST(deivceList);
                String chuZuWuAddjson = gson.toJson(chuZuWuAdd);
                Log.e("chuZuWuAddjson", chuZuWuAddjson);
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("token", UserService.getInstance(CZF_RegisterActivity.this).getToken());
//                param.put("token","D49B8A92B47741A6958646CC571844E3");
                param.put("encryption", 0);
                param.put("dataTypeCode", "ChuZuWu_Add");
                param.put("content", chuZuWuAddjson);
                String result = null;
                try {
                    result = WebService.info(Constants.WEBSERVER_PUBLICSECURITYCONTROLAPP, param);
                    Log.e("code", result);
                    JSONObject rootObject = new JSONObject(result);
                    int error = rootObject.getInt("ResultCode");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putInt("error", error);
                    bundle.putString("resultText", rootObject.getString("ResultText"));
                    bundle.putString("content", rootObject.getString("Content"));
                    msg.setData(bundle);
                    msg.what = CHUZUWUADD;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("add", e.toString());
                    Message msg = new Message();
                    msg.what = ERROR;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isFinishing()) {
                dialogShow(1, "信息编辑中，确认离开该页面？");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
                    .setCustomView(R.layout.custom_view, CZF_RegisterActivity.this)
                    .withButton2Text("取消")
                    .withButtonDrawable(R.drawable.dialog_btn_change)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();

                            progressHUD.setMessage("数据提交中");
                            progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                            progressHUD.show();

                            czwADD();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).show();
        } else if (flag == 1) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#ffffffff")
                    .withMessage(message).isCancelableOnTouchOutside(false)
                    .withEffect(effectstype).withButton1Text("确认")
                    .setCustomView(R.layout.custom_view, CZF_RegisterActivity.this)
                    .withButton2Text("取消")
                    .withButtonDrawable(R.drawable.dialog_btn_change)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).show();
        } else if (flag == 2) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#ffffffff")
                    .withMessage(message).isCancelableOnTouchOutside(false)
                    .withEffect(effectstype).withButton1Text("确认")
                    .setCustomView(R.layout.custom_view, CZF_RegisterActivity.this)
                    .withButton2Text("取消")
                    .withButtonDrawable(R.drawable.dialog_btn_change)
                    .setButton1Click(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogBuilder.dismiss();
                            Intent intent = new Intent(CZF_RegisterActivity.this,
                                    HouseInfoActivity.class);
                            intent.putExtra("HouseID", houseId);
                            startActivity(intent);
                            finish();
                        }
                    }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    Intent intent = new Intent(CZF_RegisterActivity.this,
                            HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).show();
        }
    }

}
