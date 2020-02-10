package com.zxdc.utils.library.http;

import android.os.Handler;
import android.text.TextUtils;

import com.zxdc.utils.library.bean.Area;
import com.zxdc.utils.library.bean.BuildingDetails;
import com.zxdc.utils.library.bean.CaseDetails;
import com.zxdc.utils.library.bean.ChiefDesigner;
import com.zxdc.utils.library.bean.City;
import com.zxdc.utils.library.bean.CollDecorate;
import com.zxdc.utils.library.bean.CollNum;
import com.zxdc.utils.library.bean.ConstructionDetails;
import com.zxdc.utils.library.bean.DecorateType;
import com.zxdc.utils.library.bean.DesignerDetails;
import com.zxdc.utils.library.bean.DownLoad;
import com.zxdc.utils.library.bean.Found;
import com.zxdc.utils.library.bean.FoundBanner;
import com.zxdc.utils.library.bean.GoodDetail;
import com.zxdc.utils.library.bean.Help;
import com.zxdc.utils.library.bean.Hobby;
import com.zxdc.utils.library.bean.IsColl;
import com.zxdc.utils.library.bean.MainBrand;
import com.zxdc.utils.library.bean.MainCaseImg;
import com.zxdc.utils.library.bean.MainDesigner;
import com.zxdc.utils.library.bean.Move;
import com.zxdc.utils.library.bean.Banner;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.BuildingList;
import com.zxdc.utils.library.bean.CaseList;
import com.zxdc.utils.library.bean.ConstructionList;
import com.zxdc.utils.library.bean.Coupons;
import com.zxdc.utils.library.bean.CouponsNum;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.bean.FocusCase;
import com.zxdc.utils.library.bean.GalleryList;
import com.zxdc.utils.library.bean.GiftData;
import com.zxdc.utils.library.bean.GiftType;
import com.zxdc.utils.library.bean.HotCity;
import com.zxdc.utils.library.bean.HotSearch;
import com.zxdc.utils.library.bean.MainBuilding;
import com.zxdc.utils.library.bean.MainCase;
import com.zxdc.utils.library.bean.MainDecorate;
import com.zxdc.utils.library.bean.NearDetails;
import com.zxdc.utils.library.bean.NearList;
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.bean.NewsNum;
import com.zxdc.utils.library.bean.Pinzhi;
import com.zxdc.utils.library.bean.Screening;
import com.zxdc.utils.library.bean.ShareList;
import com.zxdc.utils.library.bean.Site;
import com.zxdc.utils.library.bean.SoftLoadingList;
import com.zxdc.utils.library.bean.Telphone;
import com.zxdc.utils.library.bean.TotalSearch;
import com.zxdc.utils.library.bean.UploadFile;
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.bean.Version;
import com.zxdc.utils.library.bean.Voucher;
import com.zxdc.utils.library.bean.VoucherDetails;
import com.zxdc.utils.library.bean.VoucherNum;
import com.zxdc.utils.library.bean.VrList;
import com.zxdc.utils.library.http.base.BaseRequst;
import com.zxdc.utils.library.http.base.Http;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpMethod extends BaseRequst {

    private static String size="10";
    public static int pageSize=10;

    /**
     * 获取站点
     */
    public static void getSite(String city,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getSite(city).enqueue(new Callback<Site>() {
            public void onResponse(Call<Site> call, Response<Site> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SITE_SUCCESS, response.body());
            }
            public void onFailure(Call<Site> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页的banner数据
     */
    public static void getBanner(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getBanner().enqueue(new Callback<Banner>() {
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_BANNER_SUCCESS, response.body());
            }
            public void onFailure(Call<Banner> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页的案例数据
     */
    public static void getMainCase(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainCase().enqueue(new Callback<MainCase>() {
            public void onResponse(Call<MainCase> call, Response<MainCase> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_CASE_SUCCESS, response.body());
            }
            public void onFailure(Call<MainCase> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页楼盘
     */
    public static void getMainBuilding(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainBuilding().enqueue(new Callback<MainBuilding>() {
            public void onResponse(Call<MainBuilding> call, Response<MainBuilding> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_BUILDING_SUCCESS, response.body());
            }
            public void onFailure(Call<MainBuilding> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页附近门店
     */
    public static void getMainNear(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainNear().enqueue(new Callback<NearList>() {
            public void onResponse(Call<NearList> call, Response<NearList> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_NEAR_SUCCESS, response.body());
            }
            public void onFailure(Call<NearList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页的装修攻略
     */
    public static void getMainDecorate(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainDecorate().enqueue(new Callback<MainDecorate>() {
            public void onResponse(Call<MainDecorate> call, Response<MainDecorate> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_DECORATE, response.body());
            }
            public void onFailure(Call<MainDecorate> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 注册
     */
    public static void register(String city,String mobile,String password,String smscode,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).register(city,mobile,password,smscode).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.REGISTER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取验证码
     */
    public static void getSmsCode(String ph,String smstype,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("ph",ph);
        map.put("smstype",smstype);
        Http.getRetrofit().create(HttpApi.class).getSmsCode(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SMS_CODE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 登录
     */
    public static void login(String mobile,String password,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).login(mobile,password).enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.LOGIN_SUCCESS, response.body());
            }
            public void onFailure(Call<UserInfo> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取用户信息
     */
    public static void getUserInfo(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getUserInfo().enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_USERINFO_SUCCESS, response.body());
            }
            public void onFailure(Call<UserInfo> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 上传文件
     */
    public static void uploadFile(String relationtype, List<File> list,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("ch","1");
        map.put("relationtype",relationtype);
        Http.upLoadFile(HttpConstant.UPLOAD_FILE,"images", list, map, new okhttp3.Callback() {
            public void onResponse(okhttp3.Call call, okhttp3.Response response){
                try {
                    String str = response.body().string();
                    LogUtils.e(str+"+++++++++++++++++++");
                    sendMessage(handler, HandlerConstant.UPLOAD_FILE_SUCCESS, SPUtil.gson.fromJson(str,UploadFile.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(okhttp3.Call call, IOException e) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, e.getMessage());
            }
        });
    }


    /**
     * 修改用户信息
     */
    public static void editUser(String imgurl,String nickname,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(imgurl)){
            map.put("imgurl",imgurl);
        }
        if(!TextUtils.isEmpty(nickname)){
            map.put("nickname",nickname);
        }
        Http.getRetrofit().create(HttpApi.class).editUser(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.EDIT_USER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 更新手机号码
     */
    public static void editMobile(String mobile,String password,String smscode,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("smscode",smscode);
        Http.getRetrofit().create(HttpApi.class).editMobile(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.EDIT_MOBILE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 意见反馈
     */
    public static void feedBack(String content,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("content",content);
        Http.getRetrofit().create(HttpApi.class).feedBack(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.FEEDBACK_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 修改密码
     */
    public static void editPassword(String newpass,String password,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("newpass",newpass);
        map.put("password",password);
        Http.getRetrofit().create(HttpApi.class).editPassword(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.EDIT_PASSWORD_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取热门城市
     */
    public static void getHotCity(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getHotCity().enqueue(new Callback<HotCity>() {
            public void onResponse(Call<HotCity> call, Response<HotCity> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_HOT_CITY_SUCCESS, response.body());
            }
            public void onFailure(Call<HotCity> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取搜索属性
     */
    public static void getScreening(String type,final int what,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getScreening(type).enqueue(new Callback<Screening>() {
            public void onResponse(Call<Screening> call, Response<Screening> response) {
                BaseRequst.sendMessage(handler, what, response.body());
            }
            public void onFailure(Call<Screening> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取筛选店铺
     */
    public static void getScreeningStore(String size,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getScreeningStore(size).enqueue(new Callback<Screening>() {
            public void onResponse(Call<Screening> call, Response<Screening> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SCREENING_STORE, response.body());
            }
            public void onFailure(Call<Screening> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取筛选案例属性
     */
    public static void getScreeningCase(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getScreeningCase().enqueue(new Callback<Screening>() {
            public void onResponse(Call<Screening> call, Response<Screening> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SCREENING_CASE, response.body());
            }
            public void onFailure(Call<Screening> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取筛选施工阶段
     */
    public static void getScreeningConstrnction(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getScreeningConstrnction().enqueue(new Callback<Screening>() {
            public void onResponse(Call<Screening> call, Response<Screening> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SCREENING_CONSTRNCTION, response.body());
            }
            public void onFailure(Call<Screening> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取兑换券的分类数量
     */
    public static void getVoucherNum(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getVoucherNum().enqueue(new Callback<VoucherNum>() {
            public void onResponse(Call<VoucherNum> call, Response<VoucherNum> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_VOUCHER_NUM_SUCCESS, response.body());
            }
            public void onFailure(Call<VoucherNum> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取兑换券列表
     */
    public static void getVoucherList(String page,String status,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getVoucherList(page,size,status).enqueue(new Callback<Voucher>() {
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<Voucher> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取礼包分类
     */
    public static void getGiftType(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getGiftType().enqueue(new Callback<GiftType>() {
            public void onResponse(Call<GiftType> call, Response<GiftType> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_GIFT_TYPE_SUCCESS, response.body());
            }
            public void onFailure(Call<GiftType> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取礼包数据
     */
    public static void getGiftList(String cid,String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getGiftList(cid,page,size).enqueue(new Callback<GiftData>() {
            public void onResponse(Call<GiftData> call, Response<GiftData> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<GiftData> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }




    /**
     * 兑换礼品
     */
    public static void addGift(String mobile,String name,String sid,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("name",name);
        map.put("sid",sid);
        Http.getRetrofit().create(HttpApi.class).addGift(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.ADD_GIFT_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取装修案例数据
     */
    public static void getCaseList(String casetype,String dstyle,String housearea ,String housetype,String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCaseList(casetype,dstyle,housearea,housetype,name,page,size,sortparam).enqueue(new Callback<CaseList>() {
            public void onResponse(Call<CaseList> call, Response<CaseList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<CaseList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取案例图库
     */
    public static void getGalleryList(String dstyle,String element,String housearea ,String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getGalleryList(dstyle,element,housearea,name,page,size,sortparam).enqueue(new Callback<GalleryList>() {
            public void onResponse(Call<GalleryList> call, Response<GalleryList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<GalleryList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取VR样板间列表
     */
    public static void getVRList(String dstyle,String housearea ,String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getVRList(dstyle,housearea,name,page,size,sortparam).enqueue(new Callback<VrList>() {
            public void onResponse(Call<VrList> call, Response<VrList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<VrList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取V施工地列表
     */
    public static void getConstructionList(String dstyle ,String name,String page,String sortparam,String stage,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getConstructionList(dstyle,name,page,size,sortparam,stage).enqueue(new Callback<ConstructionList>() {
            public void onResponse(Call<ConstructionList> call, Response<ConstructionList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<ConstructionList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取热门楼盘列表
     */
    public static void getBuildingList(String district ,String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getBuildingList(district,name,page,size,sortparam).enqueue(new Callback<BuildingList>() {
            public void onResponse(Call<BuildingList> call, Response<BuildingList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<BuildingList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }



    /**
     * 获取软装范本列表
     */
    public static void getSoftList(String dstyle,String housearea ,String housetype,String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getSoftList(dstyle,housearea,housetype,name,page,size,sortparam).enqueue(new Callback<SoftLoadingList>() {
            public void onResponse(Call<SoftLoadingList> call, Response<SoftLoadingList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<SoftLoadingList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取设计师列表
     */
    public static void getDesignerList(String dtype,String housetype,String name,String page,String shopid,String sortparam,String style,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDesignerList(dtype,housetype,name,page,shopid,size,sortparam,style).enqueue(new Callback<DesignerList>() {
            public void onResponse(Call<DesignerList> call, Response<DesignerList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<DesignerList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取体验店列表
     */
    public static void getNearList(String name,String page,String sortparam,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getNearList(name,page,size,sortparam).enqueue(new Callback<NearList>() {
            public void onResponse(Call<NearList> call, Response<NearList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<NearList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页在施工地
     */
    public static void getMainCoustruction(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainCoustruction().enqueue(new Callback<ConstructionList>() {
            public void onResponse(Call<ConstructionList> call, Response<ConstructionList> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_COUSTRUCTION_SUCCESSS, response.body());
            }
            public void onFailure(Call<ConstructionList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取热门搜索
     */
    public static void getHotSearch(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getHotSearch().enqueue(new Callback<HotSearch>() {
            public void onResponse(Call<HotSearch> call, Response<HotSearch> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_HOT_SEARCH_SUCCESS, response.body());
            }
            public void onFailure(Call<HotSearch> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的风格案例
     */
    public static void getCollectionCase(String casetype,String dstyle,String housearea,String housetype,String page,String sorttype,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollectionCase(casetype,dstyle,housearea,housetype,page,size,sorttype).enqueue(new Callback<CaseList>() {
            public void onResponse(Call<CaseList> call, Response<CaseList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<CaseList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的设计师
     */
    public static void getCollDesigner(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollDesigner(page,size).enqueue(new Callback<DesignerList>() {
            public void onResponse(Call<DesignerList> call, Response<DesignerList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<DesignerList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 取消收藏
     */
    public static void cancleColl(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).cancleColl(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.CANCLE_COLL_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的案例图库
     */
    public static void getCollGallery(String dstyle,String element,String housespace,String page, String sorttype,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollGallery(dstyle,element,housespace,page,size,sorttype).enqueue(new Callback<GalleryList>() {
            public void onResponse(Call<GalleryList> call, Response<GalleryList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<GalleryList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的VR
     */
    public static void getCollVr(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollVr(page,size).enqueue(new Callback<VrList>() {
            public void onResponse(Call<VrList> call, Response<VrList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<VrList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的软装范本
     */
    public static void getCollSoft(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollSoft(page,size).enqueue(new Callback<SoftLoadingList>() {
            public void onResponse(Call<SoftLoadingList> call, Response<SoftLoadingList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<SoftLoadingList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的在施工地
     */
    public static void getCollCons(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollCons(page,size).enqueue(new Callback<ConstructionList>() {
            public void onResponse(Call<ConstructionList> call, Response<ConstructionList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<ConstructionList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的热门楼盘
     */
    public static void getCollBuilding(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollBuilding(page,size).enqueue(new Callback<BuildingList>() {
            public void onResponse(Call<BuildingList> call, Response<BuildingList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<BuildingList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的门店
     */
    public static void getCollNear(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollNear(page,size).enqueue(new Callback<NearList>() {
            public void onResponse(Call<NearList> call, Response<NearList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<NearList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取关注的风格
     */
    public static void getFocusCase(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getFocusCase().enqueue(new Callback<FocusCase>() {
            public void onResponse(Call<FocusCase> call, Response<FocusCase> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_FOCUS_CASE_SUCCESS, response.body());
            }
            public void onFailure(Call<FocusCase> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取关注的设计师列表
     */
    public static void getFocusDesigner(String page,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getFocusDesigner(page,size).enqueue(new Callback<DesignerList>() {
            public void onResponse(Call<DesignerList> call, Response<DesignerList> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<DesignerList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 设置关注的风格
     */
    public static void setFocus(String styles,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("styles",styles);
        Http.getRetrofit().create(HttpApi.class).setFocus(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.SET_FOCUS_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取分享列表
     */
    public static void getShareList(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getShareList().enqueue(new Callback<ShareList>() {
            public void onResponse(Call<ShareList> call, Response<ShareList> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_SHARE_LIST_SUCCESS, response.body());
            }
            public void onFailure(Call<ShareList> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取优惠券数量
     */
    public static void getCouponsNum(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCouponsNum().enqueue(new Callback<CouponsNum>() {
            public void onResponse(Call<CouponsNum> call, Response<CouponsNum> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_COUPONS_NUM_SUCCESS, response.body());
            }
            public void onFailure(Call<CouponsNum> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取优惠券列表
     */
    public static void getCouponsList(String page,String status,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCouponsList(page,size,status).enqueue(new Callback<Coupons>() {
            public void onResponse(Call<Coupons> call, Response<Coupons> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<Coupons> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页活动
     */
    public static void getActivity(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getActivity().enqueue(new Callback<Move>() {
            public void onResponse(Call<Move> call, Response<Move> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ACTIVITY_SUCCESS, response.body());
            }
            public void onFailure(Call<Move> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页设计师
     */
    public static void getMainDesigner(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainDesigner().enqueue(new Callback<MainDesigner>() {
            public void onResponse(Call<MainDesigner> call, Response<MainDesigner> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_DESIGNER_SUCCESS, response.body());
            }
            public void onFailure(Call<MainDesigner> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取最新token
     */
    public static void getToken(String token,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getToken(token).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_TOKEN_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取客服电话
     */
    public static void getCall(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCall().enqueue(new Callback<Telphone>() {
            public void onResponse(Call<Telphone> call, Response<Telphone> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_CALL_SUCCESS, response.body());
            }
            public void onFailure(Call<Telphone> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 查询全部搜索
     */
    public static void getTotalSearch(String token,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getTotalSearch(token).enqueue(new Callback<TotalSearch>() {
            public void onResponse(Call<TotalSearch> call, Response<TotalSearch> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_TOTAL_SEARCH_SUCCESS, response.body());
            }
            public void onFailure(Call<TotalSearch> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 预约
     */
    public static void bespoke(String city,String community,String mobile,String name,String orderid,String ordertype,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("city",city);
        if(!TextUtils.isEmpty(community)){
            map.put("community",community);
        }
        if(!TextUtils.isEmpty(mobile)){
            map.put("mobile",mobile);
        }
        if(!TextUtils.isEmpty(name)){
            map.put("name",name);
        }
        if(!TextUtils.isEmpty(orderid)){
            map.put("orderid",orderid);
        }
        if(!TextUtils.isEmpty(ordertype)){
            map.put("ordertype",ordertype);
        }
        Http.getRetrofit().create(HttpApi.class).bespoke(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.BESPOKE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 会员设置房型
     */
    public static void setRoomEntry(String houserarea,String housertype,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("houserarea",houserarea);
        map.put("housertype",housertype);
        Http.getRetrofit().create(HttpApi.class).setRoomEntry(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.SET_ROOM_ENTRY_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取兑换记录详情
     */
    public static void getVoucherDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getVoucherDetails(id).enqueue(new Callback<VoucherDetails>() {
            public void onResponse(Call<VoucherDetails> call, Response<VoucherDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_VOUCHER_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<VoucherDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 微信登录
     */
    public static void wxLogin(String city,String img,String name,String openid,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).wxLogin(city,img,name,openid).enqueue(new Callback<UserInfo>() {
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.LOGIN_SUCCESS, response.body());
            }
            public void onFailure(Call<UserInfo> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取消息数量
     */
    public static void getNewsNum(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getNewsNum().enqueue(new Callback<NewsNum>() {
            public void onResponse(Call<NewsNum> call, Response<NewsNum> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_NEWS_NUM_SUCCESS, response.body());
            }
            public void onFailure(Call<NewsNum> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取消息列表
     */
    public static void getNews(String page,String status,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getNews(page,size,status).enqueue(new Callback<News>() {
            public void onResponse(Call<News> call, Response<News> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<News> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 根据城市名称获取地区
     */
    public static void getArea(String city,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getArea(city).enqueue(new Callback<Area>() {
            public void onResponse(Call<Area> call, Response<Area> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_ARES_SUCCESS, response.body());
            }
            public void onFailure(Call<Area> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页的案例图片
     */
    public static void getMainCaseImg(String style,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainCaseImg(style).enqueue(new Callback<MainCaseImg>() {
            public void onResponse(Call<MainCaseImg> call, Response<MainCaseImg> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_CASE_IMG_SUCCESS, response.body());
            }
            public void onFailure(Call<MainCaseImg> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 忘记密码
     */
    public static void forgetPwd(String mobile ,String password,String smscode,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("smscode",smscode);
        Http.getRetrofit().create(HttpApi.class).forgetPwd(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.FORGET_PASSWORD_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取发现页面底部数据
     */
    public static void getFoundBottom(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getFoundBottom().enqueue(new Callback<Found>() {
            public void onResponse(Call<Found> call, Response<Found> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_FOUND_BOTTOM_SUCCESS, response.body());
            }
            public void onFailure(Call<Found> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取发现页面banner数据
     */
    public static void getFoundBanner(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getFoundBanner().enqueue(new Callback<FoundBanner>() {
            public void onResponse(Call<FoundBanner> call, Response<FoundBanner> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_FOUND_BANNER_SUCCESS, response.body());
            }
            public void onFailure(Call<FoundBanner> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首席设计师
     */
    public static void getChiefDegister(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getChiefDegister().enqueue(new Callback<ChiefDesigner>() {
            public void onResponse(Call<ChiefDesigner> call, Response<ChiefDesigner> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_CHIEF_DEGISTER_SUCCESS, response.body());
            }
            public void onFailure(Call<ChiefDesigner> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取首页品牌图片
     */
    public static void getMainBrand(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getMainBrand().enqueue(new Callback<MainBrand>() {
            public void onResponse(Call<MainBrand> call, Response<MainBrand> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_MAIN_BRAND_SUCCESS, response.body());
            }
            public void onFailure(Call<MainBrand> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取装修攻略分类
     */
    public static void getDecorateType(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDecorateType().enqueue(new Callback<DecorateType>() {
            public void onResponse(Call<DecorateType> call, Response<DecorateType> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_DOCORATE_TYPE_SUCCESS, response.body());
            }
            public void onFailure(Call<DecorateType> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取装修列表
     */
    public static void getDecorateList(String page,String cid,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDecorateList(page,size,cid).enqueue(new Callback<MainDecorate>() {
            public void onResponse(Call<MainDecorate> call, Response<MainDecorate> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<MainDecorate> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取装修咨询列表
     */
    public static void getDecorateNews(String page,String title,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDecorateNews(page,size,title).enqueue(new Callback<MainDecorate>() {
            public void onResponse(Call<MainDecorate> call, Response<MainDecorate> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<MainDecorate> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取品质生活
     */
    public static void getPinzhi(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getPinzhi().enqueue(new Callback<Pinzhi>() {
            public void onResponse(Call<Pinzhi> call, Response<Pinzhi> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_PIN_ZHI_SUCCESS, response.body());
            }
            public void onFailure(Call<Pinzhi> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏VR 软装范本 风格案例
     */
    public static void coll_vr_soft_case(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).coll_vr_soft_case(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏案例图库
     */
    public static void collGallery(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collGallery(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏在施工地
     */
    public static void collConstruction(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collConstruction(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏设计师
     */
    public static void collDesigner(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collDesigner(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏楼盘
     */
    public static void collBuilding(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collBuilding(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏咨询
     */
    public static void collNews(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collNews(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 收藏门店
     */
    public static void collNear(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).collNear(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.COLLECTION_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 判断是否收藏过
     */
    public static void isColl(String id,String  stype,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).isColl(id, stype).enqueue(new Callback<IsColl>() {
            public void onResponse(Call<IsColl> call, Response<IsColl> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.IS_COLL_SUCCESS, response.body());
            }
            public void onFailure(Call<IsColl> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 取消收藏
     */
    public static void cancleCollection(String id,String stype,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        map.put("stype",stype);
        Http.getRetrofit().create(HttpApi.class).cancleCollection(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.CANCLE_COLL_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 添加分享记录
     */
    public static void addShare(String channel,String id,String name,String stype,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("channel",channel);
        map.put("id",id);
        map.put("name",name);
        map.put("stype",stype);
        Http.getRetrofit().create(HttpApi.class).addShare(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.ADD_SHARE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏的装修攻略
     */
    public static void getCollDecorate(String page,String cid,final int index,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollDecorate(page,size,cid).enqueue(new Callback<CollDecorate>() {
            public void onResponse(Call<CollDecorate> call, Response<CollDecorate> response) {
                BaseRequst.sendMessage(handler, index, response.body());
            }
            public void onFailure(Call<CollDecorate> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取收藏数量
     */
    public static void getCollNum(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCollNum().enqueue(new Callback<CollNum>() {
            public void onResponse(Call<CollNum> call, Response<CollNum> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_COLL_NUM_SUCCESS, response.body());
            }
            public void onFailure(Call<CollNum> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取楼盘详情
     */
    public static void getBuildingDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getBuildingDetails(id).enqueue(new Callback<BuildingDetails>() {
            public void onResponse(Call<BuildingDetails> call, Response<BuildingDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_BUILDING_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<BuildingDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取施工地详情
     */
    public static void getConsDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getConsDetails(id).enqueue(new Callback<ConstructionDetails>() {
            public void onResponse(Call<ConstructionDetails> call, Response<ConstructionDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_CONS_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<ConstructionDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取案例详情
     */
    public static void getCaseDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCaseDetails(id).enqueue(new Callback<CaseDetails>() {
            public void onResponse(Call<CaseDetails> call, Response<CaseDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_CASE_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<CaseDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取设计师详情
     */
    public static void getDesignerDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDesignerDetails(id).enqueue(new Callback<DesignerDetails>() {
            public void onResponse(Call<DesignerDetails> call, Response<DesignerDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_DESIGNER_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<DesignerDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取门店详情
     */
    public static void getNearDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getNearDetails(id).enqueue(new Callback<NearDetails>() {
            public void onResponse(Call<NearDetails> call, Response<NearDetails> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_NEAR_DETAILLS_SUCCESS, response.body());
            }
            public void onFailure(Call<NearDetails> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 查询版本号
     */
    public static void getVersion(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getVersion().enqueue(new Callback<Version>() {
            public void onResponse(Call<Version> call, Response<Version> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.UPDATE_VERSION_SUCCESS, response.body());
            }
            public void onFailure(Call<Version> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 文件下载
     * @param handler
     */
    public static void download(final DownLoad downLoad, final Handler handler) {
        Http.dowload(downLoad.getDownPath(), downLoad.getSavePath(),handler, new okhttp3.Callback() {
            public void onFailure(okhttp3.Call call, IOException e) {
                sendMessage(handler, HandlerConstant.REQUST_ERROR, null);
            }
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if(response.isSuccessful()){
                    sendMessage(handler, HandlerConstant.DOWNLOAD_SUCCESS, downLoad);
                }
            }
        });
    }


    /**
     * 获取商品详情
     */
    public static void getGoodDetails(String id,final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getGoodDetails(id).enqueue(new Callback<GoodDetail>() {
            public void onResponse(Call<GoodDetail> call, Response<GoodDetail> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_GOOD_DETAILS_SUCCESS, response.body());
            }
            public void onFailure(Call<GoodDetail> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取设计师筛选类型
     */
    public static void getDesignerType(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getDesignerType().enqueue(new Callback<Screening>() {
            public void onResponse(Call<Screening> call, Response<Screening> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_DESIGNER_TYPE_SUCCESS, response.body());
            }
            public void onFailure(Call<Screening> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 设置消息为已读
     */
    public static void setNewsStatus(String msgid,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("msgid",msgid);
        Http.getRetrofit().create(HttpApi.class).setNewsStatus(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.SET_NEWS_STATUS_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 关注设计师
     */
    public static void fouceDesigner(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).fouceDesigner(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.FOUCE_DESIGNER_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 取消关注设计师
     */
    public static void cancleFouce(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).cancleFouce(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.CANCLE_FOUCE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取帮助列表
     */
    public static void getHelp(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getHelp().enqueue(new Callback<Help>() {
            public void onResponse(Call<Help> call, Response<Help> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_HELP_SUCCESS, response.body());
            }
            public void onFailure(Call<Help> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 领取优惠券
     */
    public static void takeCoupon(String id,final Handler handler) {
        Map<String ,String> map=new HashMap<>();
        map.put("id",id);
        Http.getRetrofit().create(HttpApi.class).takeCoupon(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.TAKE_COUPON_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取推荐关注的风格
     */
    public static void getHobby(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getHobby().enqueue(new Callback<Hobby>() {
            public void onResponse(Call<Hobby> call, Response<Hobby> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_HOBBY_SUCCESS, response.body());
            }
            public void onFailure(Call<Hobby> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 获取城市列表
     */
    public static void getCity(final Handler handler) {
        Http.getRetrofit().create(HttpApi.class).getCity().enqueue(new Callback<City>() {
            public void onResponse(Call<City> call, Response<City> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.GET_CITY_SUCCESS, response.body());
            }
            public void onFailure(Call<City> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }


    /**
     * 绑定手机号
     */
    public static void bingMobile(String mobile,String password,String smscode,final Handler handler) {
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("smscode",smscode);
        Http.getRetrofit().create(HttpApi.class).bingMobile(map).enqueue(new Callback<BaseBean>() {
            public void onResponse(Call<BaseBean> call, Response<BaseBean> response) {
                BaseRequst.sendMessage(handler, HandlerConstant.BING_MOBILE_SUCCESS, response.body());
            }
            public void onFailure(Call<BaseBean> call, Throwable t) {
                BaseRequst.sendMessage(handler, HandlerConstant.REQUST_ERROR, t.getMessage());
            }
        });
    }
}
