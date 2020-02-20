package com.ylean.dyspd.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.BuildConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLogCommon;
import com.umeng.socialize.PlatformConfig;
import com.zxdc.utils.library.base.BaseApplication;
import com.zxdc.utils.library.util.ActivitysLifecycle;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.error.CockroachUtil;

import cn.jpush.android.api.JPushInterface;
/**
 * Created by Administrator on 2019/11/7.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
        if(!isMain()){
            return;
        }

        //开启小强
        CockroachUtil.install();

        //初始化百度地图
        initMap();

        //初始化消息推送
        initPush();

        //初始化友盟分享
        initShare();

        //管理Activity
        registerActivityLifecycleCallbacks(ActivitysLifecycle.getInstance());
    }


    /**
     * 初始化百度地图
     */
    private void initMap(){
        //初始化地图
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    /**
     * 初始化消息推送
     */
    private void initPush(){
        //设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(true);
        //初始化 JPush
        JPushInterface.init(this);
        final int jpush=SPUtil.getInstance(this).getInteger(SPUtil.JPUSH);
        if(jpush==0){
            JPushInterface.stopPush(this);      //停止推送
        }else{
            JPushInterface.resumePush(this);  		// 恢复推送
        }
    }




    /**
     * 初始化友盟分享
     */
    private void initShare(){
        //初始化
//        UMConfigure.init(this,"5dfc65a30cafb2cfa300053e","Android",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.init(this,UMConfigure.DEVICE_TYPE_PHONE,"");
        //微信
        PlatformConfig.setWeixin("wx41f56978f20ce2fe", "696afe62e1a457cf0440cd30673b90d3");
        //新浪微博
        PlatformConfig.setSinaWeibo("620570357", "d7bed770e0574a2d92f083883179edc8", "http://sns.whalecloud.com");
        //QQ
        PlatformConfig.setQQZone("101838160", "3192d2ae301f12f59cd63a99283baa6f");

        // 选用LEGACY_AUTO页面采集模式
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
//        // 支持在子进程中统计自定义事件
//        UMConfigure.setProcessEvent(true);
//        UMConfigure.setLogEnabled(false);
//
//        MobclickAgent.setSessionContinueMillis(20*1000);
    }

    /**
     * 判断是否登录过
     * @return
     */
    public static boolean isLogin(){
        final String token= SPUtil.getInstance(getContext()).getString(SPUtil.TOKEN);
        if(!TextUtils.isEmpty(token)){
            return true;
        }
        return false;
    }


    /**
     * 判断是否是主进程
     * @return
     */
    private boolean isMain() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName;
                break;
            }
        }
        String packageName = this.getPackageName();
        if (processName.equals(packageName)) {
            return true;
        }
        return false;
    }
}
