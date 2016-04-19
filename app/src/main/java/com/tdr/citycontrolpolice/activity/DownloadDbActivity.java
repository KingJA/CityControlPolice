package com.tdr.citycontrolpolice.activity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Return;
import com.tdr.citycontrolpolice.entity.ErrorResult;
import com.tdr.citycontrolpolice.net.PoolManager;
import com.tdr.citycontrolpolice.net.ThreadPoolTask;
import com.tdr.citycontrolpolice.net.WebServiceCallBack;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/18 16:47
 * 修改备注：
 */
public class DownloadDbActivity extends BackTitleActivity {

    private static final String TAG = "DownloadDbActivity";
    private Button btn_download_db;
    private Map<String, Object> param;
    private int page;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    page += 1;
                    loadDb("Basic_Dictionary", page);
                    break;
            }
        }
    };
    private DbManager.DaoConfig daoConfig;
    private DbManager db;

    @Override
    public View setContentView() {
        view = View.inflate(DownloadDbActivity.this, R.layout.activity_download_db, null);
        return view;
    }

    @Override
    public void initVariables() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName("king5.db")
                .setDbVersion(1)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                });
        db = x.getDb(daoConfig);
    }

    @Override
    protected void initView() {
        btn_download_db = (Button) view.findViewById(R.id.btn_download_db);
    }

    @Override
    public void initNet() {
        btn_download_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDb("Basic_Dictionary", page);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void setData() {
        setTitle("Admin");
    }

    /**
     * 下载 Basic_Dictionary
     *
     * @param method
     * @param page
     */
    public void loadDb(String method, final int page) {
        param = new HashMap<>();
        param.put("TaskID", "1");
        param.put("UpdateTime", "2014-10-30 23:11:02");
        param.put("PageSize", 200);
        param.put("PageIndex", page);
        ThreadPoolTask.Builder<Basic_Dictionary_Return> builder = new ThreadPoolTask.Builder<Basic_Dictionary_Return>();
        ThreadPoolTask task = builder.setGeneralParam("", 0, method, param)
                .setBeanType(Basic_Dictionary_Return.class)
                .setActivity(DownloadDbActivity.this)
                .setCallBack(new WebServiceCallBack<Basic_Dictionary_Return>() {
                    @Override
                    public void onSuccess(Basic_Dictionary_Return bean) {
                        final List<Basic_Dictionary_Kj> content = bean.getContent();
                        Log.i(TAG, "onSuccess: " + content.size());
                        if (content.size() > 0) {
                            saveDate(content);
                        } else {
                            Log.i(TAG, "完成数据库下载: ");
                        }

                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build();
        PoolManager.getInstance().execute(task);
    }

    /**
     * 保存数据
     *
     * @param content
     */
    private void saveDate(final List<Basic_Dictionary_Kj> content) {

        x.task().run(new Runnable() {
            @Override
            public void run() {
                for (Basic_Dictionary_Kj bean : content) {
                    try {
                        db.saveOrUpdate(bean);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public String getFormatTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
}
