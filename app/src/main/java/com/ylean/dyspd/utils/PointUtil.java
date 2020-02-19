package com.ylean.dyspd.utils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.SPUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 埋点
 * Created by Administrator on 2020/2/18.
 */

public class PointUtil {

    private static PointUtil pointUtil;

    public static PointUtil getInstent(){
        if(pointUtil==null){
            pointUtil=new PointUtil();
        }
        return pointUtil;
    }

    /**
     * 收藏埋点
     * @param type
     */
    public static void collPoint(Context context,int type){
        Map<String, Object> map = new HashMap();
        switch (type){
            //收藏图库
            case 1:
                MobclickAgent.onEventObject(context, "colltk", map);
                break;
            //收藏VR
            case 2:
                MobclickAgent.onEventObject(context, "collvr", map);
                break;
            //软装范本
            case 7:
                MobclickAgent.onEventObject(context, "collrz", map);
                break;
            //风格案例
            case 12:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //收藏装修攻略
            case 3:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //收藏体验店
            case 4:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //收藏在施工地
            case 5:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //收藏楼盘
            case 6:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //收藏设计师
            case 8:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            default:
                break;
        }
    }


    /**
     * 微信或手机注册埋点
     * @param context
     */
    public static void registerPoint(Context context,int type){
        Map<String, Object> map = new HashMap();
        map.put("coll","");
        //手机号注册
        if(type==1){
            MobclickAgent.onEventObject(context, "play_music", map);
        }else{
            MobclickAgent.onEventObject(context, "play_music", map);
        }
    }


    /**
     * 报名埋点
     * @param context
     */
    public static void respokePoint(Context context){
        Map<String, Object> map = new HashMap();
        final String token = SPUtil.getInstance(context).getString(SPUtil.TOKEN);
        //未注册报名
        if (TextUtils.isEmpty(token)) {
            MobclickAgent.onEventObject(context, "play_music", map);
        } else {
            MobclickAgent.onEventObject(context, "play_music", map);
        }
    }


    /**
     * 页面埋点
     * @param context
     * @param type
     */
    public static void pagePoint(Context context,int type){
        Map<String, Object> map = new HashMap();
        map.put("coll","");
        switch (type){
            //设计师
            case 1:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //体验店
            case 2:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //风格案例
            case 3:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //案例图库
            case 4:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //VR样板房
            case 5:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //软装案例
            case 6:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //热装楼盘
            case 7:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //实景工地
            case 8:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            //攻略
            case 9:
                MobclickAgent.onEventObject(context, "play_music", map);
                break;
            default:
                break;
        }
    }
}
