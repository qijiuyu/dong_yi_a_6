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


    public static String getDeviceID() {
        String deviceID= "";
        try{
            //一共13位  如果位数不够可以继续添加其他信息
            m_szDevIDShort= ""+Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                    Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                    Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                    Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                    Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                    Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                    Build.USER.length() % 10;
        }catch (Exception e){
            return "";
        }
        return deviceID;
    }


}
