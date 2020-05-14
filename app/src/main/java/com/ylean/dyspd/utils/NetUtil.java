package com.ylean.dyspd.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.zxdc.utils.library.util.LogUtils;

import java.lang.reflect.Method;

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


    public static String getIMEI(Context context){
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                imei = tm.getDeviceId();
            }else {
                Method method = tm.getClass().getMethod("getImei");
                imei = (String) method.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

}
