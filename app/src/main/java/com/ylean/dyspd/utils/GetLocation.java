package com.ylean.dyspd.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.util.SPUtil;

/**
 * 定位
 * Created by Administrator on 2017/3/15 0015.
 */
public class GetLocation {

    private static GetLocation getLocation;
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private Handler handler;
    private Context context;
    public static GetLocation getInstance() {
        if (null == getLocation) {
            getLocation = new GetLocation();
        }
        return getLocation;
    }

    /**
     * 设置定位
     */
    public void setLocation(Context mContext,Handler handler) {
        this.context=mContext;
        this.handler=handler;
        mLocClient = new LocationClient(mContext.getApplicationContext());
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置火星坐标
        option.setScanSpan(10000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        public void onReceiveLocation(BDLocation location) {
            Message message = new Message();
            //GPS定位成功、网络定位成功、离线定位成功
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation || location.getLocType() == BDLocation.TypeOffLineLocation) {
//                MyLocationData locData = new MyLocationData.Builder()
//                        .accuracy(0)//0：去掉蓝色小图标
//                        // 此处设置开发者获取到的方向信息，顺时针0-360
//                        .latitude(location.getLatitude())
//                        .longitude(location.getLongitude()).build();

                message.obj=location;
                SPUtil.getInstance(context).addString(SPUtil.LAT,String.valueOf(location.getLatitude()));
                SPUtil.getInstance(context).addString(SPUtil.LONG,String.valueOf(location.getLongitude()));
                SPUtil.getInstance(context).addString(SPUtil.LOCATION_CITY,location.getCity());
                stopLocation();
            }
            message.what= HandlerConstant.START_LOCATION_SUCCESS;
            handler.sendMessage(message);
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }


    /**
     * 停止定位
     */
    public void stopLocation() {
        if (null != mLocClient) {
            mLocClient.unRegisterLocationListener(myListener);
            mLocClient.stop();
            mLocClient=null;
        }
    }
}
