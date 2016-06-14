package com.tdr.citycontrolpolice.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：WebService管理类
 * 创建人：KingJA
 * 创建时间：2016/3/22 13:08
 * 修改备注：
 */
public class PoolManager {
    private ExecutorService cachedThreadPool;
    private static PoolManager mPoolManager;

    private PoolManager() {
        cachedThreadPool = Executors.newCachedThreadPool();
    }

    public static PoolManager getInstance() {
        if (mPoolManager == null) {
            synchronized (PoolManager.class) {
                if (mPoolManager == null) {
                    mPoolManager = new PoolManager();
                }
            }
        }
        return mPoolManager;
    }

    public void execute(Runnable r) {
        cachedThreadPool.execute(r);
    }

    public boolean isOver() {
        return cachedThreadPool.isTerminated();
    }
}
