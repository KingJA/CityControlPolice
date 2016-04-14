package com.tdr.citycontrolpolice.update;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.tdr.citycontrolpolice.util.Constants;

/**
 * Created by Linus_Xie on 2016/3/12.
 */
public class GetVersionCodeAsynckTask extends AsyncTask<String, Integer, Boolean> {

    private Context mContext;
    private Handler mHander;
    private VersionInfo versionInfo;

    public GetVersionCodeAsynckTask(Context mContext, Handler mHander) {
        this.mContext = mContext;
        this.mHander = mHander;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        WebserviceRequest request = new WebserviceRequest();

        try {
            versionInfo = request.getVersionCode(params[0]);
            if (versionInfo != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
            Message msg = mHander.obtainMessage();
            msg.obj = versionInfo.getVersionCode();
            msg.what = Constants.HANDLER_KEY_GETVERSION_SUCCESS;
            mHander.sendMessage(msg);
        } else {
            mHander.sendEmptyMessage(Constants.HANDLER_KEY_GETVERSION_FAIL);
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }
}
