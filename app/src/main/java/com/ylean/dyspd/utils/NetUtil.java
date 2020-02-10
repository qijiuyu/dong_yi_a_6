package com.ylean.dyspd.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.zxdc.utils.library.util.LogUtils;

/**
 * Created by Administrator on 2019/12/30.
 */

public class NetUtil {


    public static void net(Context context){
        ConnectivityManager mConnectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mTelephony = (TelephonyManager)context.getSystemService(context.TELEPHONY_SERVICE);
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();

        int netType = info.getType();
        int netSubtype = info.getSubtype();

        if (netType == ConnectivityManager.TYPE_WIFI) {  //WIFI
            LogUtils.e("wifi");
        } else if (netType == ConnectivityManager.TYPE_MOBILE) {   //MOBILE
            LogUtils.e("流量");
        } else {
            LogUtils.e("++++++++++++++++++++++++3");
        }
    }
}
