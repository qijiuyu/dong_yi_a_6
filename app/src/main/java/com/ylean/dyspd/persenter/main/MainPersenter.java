package com.ylean.dyspd.persenter.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.ylean.dyspd.activity.main.MainActivity;
import com.ylean.dyspd.activity.main.MainGuideActivity;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.utils.GetLocation;
import com.zxdc.utils.library.bean.ConstructionList;
import com.zxdc.utils.library.bean.MainBrand;
import com.zxdc.utils.library.bean.MainBuilding;
import com.zxdc.utils.library.bean.MainCase;
import com.zxdc.utils.library.bean.MainCaseImg;
import com.zxdc.utils.library.bean.MainDecorate;
import com.zxdc.utils.library.bean.MainDesigner;
import com.zxdc.utils.library.bean.Move;
import com.zxdc.utils.library.bean.NearList;
import com.zxdc.utils.library.bean.Site;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.bean.Banner;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class MainPersenter {

    private MainActivity activity;
    /**
     * true：定位成功
     * false：没定位
     */
    private boolean isLocation = false;

    public MainPersenter(MainActivity activity){
        this.activity=activity;
    }


    public Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            //停止刷新
            activity.reList.refreshComplete();
            switch (msg.what){
                //定位成功
                case HandlerConstant.START_LOCATION_SUCCESS:
                     if (isLocation || msg.obj==null) {
                         break;
                     }
                     isLocation = true;
                     BDLocation location= (BDLocation) msg.obj;
                     //保存城市名称
                     SPUtil.getInstance(activity).addString(SPUtil.CITY,location.getCity());
                     //首页左上角展示城市名称
                     EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_CITY));
                     //获取站点
                     getSite();
                     break;
                //获取站点
                case HandlerConstant.GET_SITE_SUCCESS:
                      Site site= (Site) msg.obj;
                      if(site==null){
                          break;
                      }
                      if(site.isSussess()){
                          if(site.getData()!=null){
                              //存储siteId在本地
                              SPUtil.getInstance(activity).addString(SPUtil.SITEID,String.valueOf(site.getData().getId()));
                              //获取banner数据
                              getBanner();
                              //获取案例数据
                              getMainCase();
                              //获取首页设计师
                              getMainDesigner();
                              //获取楼盘
                              getMainBuilding();
                              //获取首页在施工地
                              getMainCoustruction();
                              //获取附近门店
                              getMainNear();
                              //获取首页品牌图片
                              getMainBrand();
                              //获取首页的装修攻略
                              getMainDecorate();
                              //获取首页活动
                              getActivity();
                          }else{
                              ToastUtil.showLong("当前城市没有分公司");
                              //定位到全国
                              SPUtil.getInstance(activity).addString(SPUtil.CITY,"");
                              EventBus.getDefault().post(new EventBusType(EventStatus.SELECT_CITY_SUCCESS));
                          }
                      }else{
                          if(!TextUtils.isEmpty(site.getDesc())){
                              ToastUtil.showLong(site.getDesc());
                          }
                      }
                     break;
                //获取banner数据
                case HandlerConstant.GET_BANNER_SUCCESS:
                      Banner banner= (Banner) msg.obj;
                      if(null==banner){
                          break;
                      }
                      if(banner.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_BANNER,banner.getData()));
                      }else{
                          ToastUtil.showLong(banner.getDesc());
                      }
                      break;
                //获取案例数据
                case HandlerConstant.GET_MAIN_CASE_SUCCESS:
                      MainCase mainCase= (MainCase) msg.obj;
                      if(mainCase==null){
                          break;
                      }
                      if(mainCase.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_CASE,mainCase.getData()));
                      }else{
                          ToastUtil.showLong(mainCase.getDesc());
                      }
                      break;
                //获取案例图片
                case HandlerConstant.GET_MAIN_CASE_IMG_SUCCESS:
                      MainCaseImg mainCaseImg= (MainCaseImg) msg.obj;
                      if(mainCaseImg==null){
                          break;
                      }
                      if(mainCaseImg.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_CASE_IMG,mainCaseImg.getData()));
                      }else{
                          ToastUtil.showLong(mainCaseImg.getDesc());
                      }
                      break;
                //获取设计师
                case HandlerConstant.GET_MAIN_DESIGNER_SUCCESS:
                      MainDesigner mainDesigner= (MainDesigner) msg.obj;
                      if(mainDesigner==null){
                          break;
                      }
                      if(mainDesigner.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_DESIGNER,mainDesigner.getData()));
                      }else{
                          ToastUtil.showLong(mainDesigner.getDesc());
                      }
                      break;
                //获取楼盘
                case HandlerConstant.GET_MAIN_BUILDING_SUCCESS:
                      MainBuilding mainBuilding= (MainBuilding) msg.obj;
                      if(mainBuilding==null){
                          break;
                      }
                      if(mainBuilding.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_BUILDONG,mainBuilding.getData()));
                      }else{
                          ToastUtil.showLong(mainBuilding.getDesc());
                      }
                      break;
                //获取首页在施工地
                case HandlerConstant.GET_MAIN_COUSTRUCTION_SUCCESSS:
                      ConstructionList constructionList= (ConstructionList) msg.obj;
                      if(constructionList==null){
                          break;
                      }
                      if(constructionList.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_CONSTRUCTION,constructionList.getData()));
                      }else{
                          ToastUtil.showLong(constructionList.getDesc());
                      }
                      break;
                //获取附近门店
                case HandlerConstant.GET_MAIN_NEAR_SUCCESS:
                      NearList nearList= (NearList) msg.obj;
                      if(nearList==null){
                          break;
                      }
                      if(nearList.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_NEAR,nearList.getData()));
                      }else{
                          ToastUtil.showLong(nearList.getDesc());
                      }
                      break;
                //获取首页品牌图片
                case HandlerConstant.GET_MAIN_BRAND_SUCCESS:
                      MainBrand mainBrand= (MainBrand) msg.obj;
                      if(mainBrand==null){
                          break;
                      }
                      if(mainBrand.isSussess()){
                          if(!TextUtils.isEmpty(mainBrand.getData())){
                              EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_BRAND,mainBrand.getData()));
                          }
                      }else{
                          ToastUtil.showLong(mainBrand.getDesc());
                      }
                      break;
                //获取首页的装修攻略
                case HandlerConstant.GET_MAIN_DECORATE:
                      MainDecorate mainDecorate= (MainDecorate) msg.obj;
                      if(mainDecorate==null){
                          break;
                      }
                      if(mainDecorate.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_DECORATE,mainDecorate.getData()));
                      }else{
                          ToastUtil.showLong(mainDecorate.getDesc());
                      }
                      break;
                //获取首页活动
                case HandlerConstant.GET_ACTIVITY_SUCCESS:
                      Move move= (Move) msg.obj;
                      if(move==null){
                          break;
                      }
                      if(move.isSussess() && move.getData()!=null && !TextUtils.isEmpty(move.getData().getImg())){
                          //如果活动重复就不再提示
                          if(isActivity(move.getData().getId())){
                              break;
                          }
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_MAIN_MOVE,move.getData()));
                      }else{
                          ToastUtil.showLong(move.getDesc());
                      }
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 判断是否存储过该活动id
     * @param id
     * @return
     */
    private boolean isActivity(int id){
        boolean isActivity=false;
        String activityIds=SPUtil.getInstance(activity).getString(SPUtil.ACTIVITY_ID);
        if(TextUtils.isEmpty(activityIds)){
            return isActivity;
        }
        if (!TextUtils.isEmpty(activityIds)) {
            Map<String, String> map = SPUtil.gson.fromJson(activityIds, Map.class);
            if(map!=null && map.get(String.valueOf(id))!=null){
                isActivity=true;
            }
        }
        return isActivity;
    }


    /**
     * 存储活动id
     * @param id
     */
    public void addActivityId(String id,boolean isAdd){
        String activityIds=SPUtil.getInstance(activity).getString(SPUtil.ACTIVITY_ID);
        Map<String, String> map=new HashMap<>();
        if (!TextUtils.isEmpty(activityIds)) {
            map = SPUtil.gson.fromJson(activityIds, Map.class);
        }
        if(isAdd){
            map.put(id,id);
        }else{
            map.remove(id);
        }
        SPUtil.getInstance(activity).addString(SPUtil.ACTIVITY_ID, SPUtil.gson.toJson(map));
    }


    /**
     * 开始定位
     */
    public void startLocation(){
        DialogUtil.showProgress(activity,"定位中...");
        GetLocation.getInstance().setLocation(activity,handler);
    }


    /**
     * 获取站点
     */
    public void getSite(){
        String city=SPUtil.getInstance(activity).getString(SPUtil.CITY);
        HttpMethod.getSite(city,handler);
    }


    /**
     * 获取banner数据
     */
    private void getBanner(){
        HttpMethod.getBanner(handler);
    }


    /**
     * 获取案例数据
     */
    private void getMainCase(){
        HttpMethod.getMainCase(handler);
    }


    /**
     * 获取首页的案例图片
     */
    public void getMainCaseImg(String style){
        HttpMethod.getMainCaseImg(style,handler);
    }


    /**
     * 获取首页设计师
     */
    private void getMainDesigner(){
        HttpMethod.getMainDesigner(handler);
    }

    /**
     * 获取楼盘
     */
    private void getMainBuilding(){
        HttpMethod.getMainBuilding(handler);
    }


    /**
     * 获取首页在施工地
     */
    private void getMainCoustruction(){
        HttpMethod.getMainCoustruction(handler);
    }


    /**
     * 获取首页附近门店
     */
    private void getMainNear(){
        HttpMethod.getMainNear(handler);
    }


    /**
     * 获取首页品牌图片
     */
    private void getMainBrand(){
        HttpMethod.getMainBrand(handler);
    }


    /**
     * 获取首页的装修攻略
     */
    private void getMainDecorate(){
        HttpMethod.getMainDecorate(handler);
    }


    /**
     * 获取首页活动
     */
    private void getActivity(){
        HttpMethod.getActivity(handler);
    }


    /**
     * 是否首次进入首页
     */
    public void isOpenMain(){
        int isOpenMain=SPUtil.getInstance(activity).getInteger(SPUtil.ISOPENMAIN);
        if(isOpenMain==0){
            SPUtil.getInstance(activity).addInt(SPUtil.ISOPENMAIN,1);
            Intent intent=new Intent(activity, MainGuideActivity.class);
            activity.startActivity(intent);
        }
    }
}
