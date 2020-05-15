package com.ylean.dyspd.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

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


    /*
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     *
     * 渠道标志为：
     * 1，andriod（a）
     *
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     * 4， id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        StringBuilder deviceId = new StringBuilder();
        try {
            //wifi mac地址
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String wifiMac = info.getMacAddress();
            if(!TextUtils.isEmpty(wifiMac)){
                deviceId.append(wifiMac);
                return deviceId.toString();
            }

            //IMEI（imei）
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            deviceId.append(imei);
            //序列号（sn）
            @SuppressLint("MissingPermission") String sn = tm.getSimSerialNumber();
            deviceId.append(sn);
            //如果上面都没有， 则生成一个id：随机码
            String uuid = getDeviceID();
            deviceId.append(uuid);
        } catch (Exception e) {
            e.printStackTrace();
            deviceId.append(getDeviceID());
        }
        return deviceId.toString();
    }
    public static String getDeviceID() {
        String deviceID= "";
        try{
            //一共13位  如果位数不够可以继续添加其他信息
            deviceID= ""+ Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

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
