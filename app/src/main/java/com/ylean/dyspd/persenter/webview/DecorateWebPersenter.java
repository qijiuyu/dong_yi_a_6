package com.ylean.dyspd.persenter.webview;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.ylean.dyspd.activity.main.CaseGuideActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.application.MyApplication;
import com.ylean.dyspd.utils.PointUtil;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.IsColl;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecorateWebPersenter {

    private DecorateWebView activity;

    public DecorateWebPersenter(DecorateWebView activity){
        this.activity=activity;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            BaseBean baseBean;
            switch (msg.what){
                //判断是否收藏过
                case HandlerConstant.IS_COLL_SUCCESS:
                      IsColl isColl= (IsColl) msg.obj;
                      if(isColl==null){
                          break;
                      }
                      if(isColl.isSussess() && isColl.getData()!=null){
                          if(isColl.getData().getIscollect()==1){
                              EventBus.getDefault().post(new EventBusType(EventStatus.COLLECTION_SUCCESS));
                          }
                          if(isColl.getData().getIsfollow()==1){
                              EventBus.getDefault().post(new EventBusType(EventStatus.FOUCE_DESIGNER_SUCCESS));
                          }
                      }
                      break;
                //收藏成功
                case HandlerConstant.COLLECTION_SUCCESS:
                      baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.COLLECTION_SUCCESS));
                          ToastUtil.showLong("收藏成功");
                          //埋点收藏
                          PointUtil.collPoint(activity,activity.type);
                      }else{
                          ToastUtil.showLong(baseBean.getDesc());
                      }
                      break;
                //取消收藏
                case HandlerConstant.CANCLE_COLL_SUCCESS:
                     baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                         break;
                     }
                     if(baseBean.isSussess()){
                         EventBus.getDefault().post(new EventBusType(EventStatus.CANCLE_COLLECTION));
                         ToastUtil.showLong("取消收藏成功");
                     }else{
                        ToastUtil.showLong(baseBean.getDesc());
                     }
                     break;
                //关注设计师
                case HandlerConstant.FOUCE_DESIGNER_SUCCESS:
                     baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                         break;
                     }
                     if(baseBean.isSussess()){
                         EventBus.getDefault().post(new EventBusType(EventStatus.FOUCE_DESIGNER_SUCCESS));
                         ToastUtil.showLong("关注成功");

                         //埋点
                         MobclickAgent.onEvent(activity, "designer_details_focus");
                     }else{
                         ToastUtil.showLong(baseBean.getDesc());
                     }
                      break;
                //取消关注设计师
                case HandlerConstant.CANCLE_FOUCE_SUCCESS:
                     baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                        break;
                     }
                     if(baseBean.isSussess()){
                         EventBus.getDefault().post(new EventBusType(EventStatus.CANCLE_FOUCE_SUCCESS));
                         ToastUtil.showLong("取消成功");
                     }else{
                        ToastUtil.showLong(baseBean.getDesc());
                     }
                     break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 收藏
     * @param id
     * @param type
     */
    public void collection(int id,int type){
        DialogUtil.showProgress(activity,"收藏中...");
        switch (type){
            //收藏图库
            case 1:
                 HttpMethod.collGallery(String.valueOf(id),handler);
                 break;
            //收藏VR 软装范本 风格案例
            case 2:
            case 7:
            case 12:
                HttpMethod.coll_vr_soft_case(String.valueOf(id),handler);
                break;
            //收藏装修攻略
            case 3:
                HttpMethod.collNews(String.valueOf(id),handler);
                 break;
            //收藏体验店
            case 4:
                HttpMethod.collNear(String.valueOf(id),handler);
                 break;
            //收藏在施工地
            case 5:
                HttpMethod.collConstruction(String.valueOf(id),handler);
                 break;
            //收藏楼盘
            case 6:
                HttpMethod.collBuilding(String.valueOf(id),handler);
                 break;
            //收藏设计师
            case 8:
                HttpMethod.collDesigner(String.valueOf(id),handler);
                 break;
            default:
                break;
        }
    }


    /**
     * 1：判断是否收藏过
     * 2：取消收藏
     * 3：添加分享记录
     */
    public void isColl(int id,int type,int playType){
        String style=null;
        switch (type){
            //图库
            case 1:
                style="8";
                break;
            //VR
            case 2:
                style="5";
                break;
            //装修攻略
            case 3:
                style="9";
                break;
            //体验店
            case 4:
                style="1";
                break;
            //在施工地
            case 5:
                style="4";
                break;
            //楼盘
            case 6:
                style="3";
                break;
            //软装范本
            case 7:
                style="6";
                 break;
            //设计师
            case 8:
                style="2";
                break;
            //风格案例
            case 12:
                 style="7";
                 break;
            default:
                break;
        }
        if(TextUtils.isEmpty(style)){
            return;
        }
        switch (playType){
            //判断是否收藏过
            case 1:
                if (MyApplication.isLogin() && id != 0) {
                    HttpMethod.isColl(String.valueOf(id),style,handler);
                }
                 break;
            //取消收藏
            case 2:
                HttpMethod.cancleCollection(String.valueOf(id),style,handler);
                 break;
            case 3:
                 String channel;
                 if(activity.share_media== SHARE_MEDIA.WEIXIN || activity.share_media== SHARE_MEDIA.WEIXIN_CIRCLE){
                     channel="微信";
                 }else if(activity.share_media== SHARE_MEDIA.SINA){
                     channel="微薄";
                 }else{
                     channel="QQ";
                 }
                 HttpMethod.addShare(channel,String.valueOf(id),activity.title,style,handler);
                 break;
            default:
                break;
        }
    }


    /**
     * 进入引导页面
     * @param type
     */
    public void isOpenGuide(int type){
        Intent intent=new Intent(activity, CaseGuideActivity.class);
        if(type==8){
            int isOpen= SPUtil.getInstance(activity).getInteger(SPUtil.IS_OPEN_DESIGNER_DETAILS);
            if(isOpen==0){
                SPUtil.getInstance(activity).addInt(SPUtil.IS_OPEN_DESIGNER_DETAILS,1);
                intent.putExtra("type",8);
                activity.startActivity(intent);
            }
            return;
        }

        if(type==12){
            int isOpen= SPUtil.getInstance(activity).getInteger(SPUtil.IS_OPEN_CASE_DETAIILS);
            if(isOpen==0){
                SPUtil.getInstance(activity).addInt(SPUtil.IS_OPEN_CASE_DETAIILS,1);
                intent.putExtra("type",12);
                activity.startActivity(intent);
            }
            return;
        }
    }


    /**
     * 关注设计师
     */
    public void fouceDesigner(int id){
        DialogUtil.showProgress(activity,"关注中...");
        HttpMethod.fouceDesigner(String.valueOf(id),handler);
    }


    /**
     * 取消关注设计师
     */
    public void cancleFouce(int id){
        DialogUtil.showProgress(activity,"取消中...");
        HttpMethod.cancleFouce(String.valueOf(id),handler);
    }


    public static String getParam(String url, String name) {
        url += "&";
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }
}
