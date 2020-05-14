package com.zxdc.utils.library.http;



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
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.bean.Version;
import com.zxdc.utils.library.bean.Voucher;
import com.zxdc.utils.library.bean.VoucherDetails;
import com.zxdc.utils.library.bean.VoucherNum;
import com.zxdc.utils.library.bean.VrList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpApi {

    @GET(HttpConstant.GET_SITE)
    Call<Site> getSite(@Query("city") String city);

    @GET(HttpConstant.MAIN_BANNER)
    Call<Banner> getBanner();

    @GET(HttpConstant.MAIN_CASE)
    Call<MainCase> getMainCase();

    @GET(HttpConstant.MAIN_BUILDING)
    Call<MainBuilding> getMainBuilding();

    @GET(HttpConstant.MAIN_NEAR)
    Call<NearList> getMainNear();

    @GET(HttpConstant.MAIN_DECORATE)
    Call<MainDecorate> getMainDecorate();

    @GET(HttpConstant.REGISTER)
    Call<BaseBean> register(@Query("city") String city,@Query("mobile") String mobile, @Query("password") String password, @Query("smscode") String smscode);

    @FormUrlEncoded
    @POST(HttpConstant.GET_SMS_CODE)
    Call<BaseBean> getSmsCode(@FieldMap Map<String, String> map);

    @GET(HttpConstant.LOGIN)
    Call<UserInfo> login(@Query("mobile") String mobile, @Query("password") String password);

    @GET(HttpConstant.GET_USER)
    Call<UserInfo> getUserInfo();

    @FormUrlEncoded
    @POST(HttpConstant.EDIT_USER)
    Call<BaseBean> editUser(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.EDIT_MOBILE)
    Call<BaseBean> editMobile(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.FEEDBACK)
    Call<BaseBean> feedBack(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.EDIT_PASSWORD)
    Call<BaseBean> editPassword(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_HOT_CITY)
    Call<HotCity> getHotCity();

    @GET(HttpConstant.GET_SCREENING_DATA)
    Call<Screening> getScreening(@Query("type") String type);

    @GET(HttpConstant.GET_SCREENING_STORE)
    Call<Screening> getScreeningStore(@Query("size") String size);

    @GET(HttpConstant.GET_SCREENING_CASE)
    Call<Screening> getScreeningCase();

    @GET(HttpConstant.GET_SCREENING_CONSTRNCTION)
    Call<Screening> getScreeningConstrnction();

    @GET(HttpConstant.GET_VOUCHER_NUM)
    Call<VoucherNum> getVoucherNum();

    @GET(HttpConstant.GET_VOUCHER_LIST)
    Call<Voucher> getVoucherList(@Query("page") String page, @Query("size") String size, @Query("status") String status);

    @GET(HttpConstant.GET_GIFT_TYPE)
    Call<GiftType> getGiftType();

    @GET(HttpConstant.GET_GIFT_LIST)
    Call<GiftData> getGiftList(@Query("cid") String cid,@Query("page") String page, @Query("size") String size);

    @FormUrlEncoded
    @POST(HttpConstant.ADD_GIFT)
    Call<BaseBean> addGift(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_CASE_LIST)
    Call<CaseList> getCaseList(@Query("casetype") String casetype, @Query("dstyle") String dstyle, @Query("housearea") String housearea  , @Query("housetype") String housetype, @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_GALLERY_LIST)
    Call<GalleryList> getGalleryList(@Query("dstyle") String dstyle, @Query("element") String element, @Query("housespace") String housespace, @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_VR_LIST)
    Call<VrList> getVRList(@Query("dstyle") String dstyle, @Query("housearea") String housearea, @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_COUSTRUCTION_LIST)
    Call<ConstructionList> getConstructionList(@Query("housetype") String housetype, @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype,@Query("sorttype") String sorttype,@Query("stage") String stage);

    @GET(HttpConstant.GET_BUILDING_LIST)
    Call<BuildingList> getBuildingList(@Query("district") String district,  @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_SOFT_LIST)
    Call<SoftLoadingList> getSoftList(@Query("dstyle") String dstyle, @Query("housearea") String housearea,@Query("housetype") String housetype, @Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_DESIGNER_LIST)
    Call<DesignerList> getDesignerList(@Query("dtype") String dtype,@Query("housetype") String housetype,@Query("name") String name, @Query("page") String page, @Query("shopid") String shopid, @Query("size") String size, @Query("ordertype") String ordertype,@Query("sorttype") String sorttype, @Query("style") String style);

    @GET(HttpConstant.GET_NEAR_LIST)
    Call<NearList> getNearList(@Query("name") String name, @Query("page") String page, @Query("size")String size, @Query("ordertype") String ordertype, @Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_MAIN_COUSTRUCTION)
    Call<ConstructionList> getMainCoustruction();

    @GET(HttpConstant.GET_HOT_SEARCH)
    Call<HotSearch> getHotSearch();

    @GET(HttpConstant.GET_COLLECTION_CASE)
    Call<CaseList> getCollectionCase(@Query("casetype") String casetype, @Query("dstyle") String dstyle, @Query("housearea") String housearea  , @Query("housetype") String housetype,  @Query("page") String page, @Query("size")String size, @Query("sorttype")String sorttype);

    @GET(HttpConstant.GET_COLLECTION_DESIGNER)
    Call<DesignerList> getCollDesigner(@Query("page") String page, @Query("size") String size);

    @FormUrlEncoded
    @POST(HttpConstant.CANCLE_COLL)
    Call<BaseBean> cancleColl(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_COLLECTION_GALLERY)
    Call<GalleryList> getCollGallery(@Query("dstyle") String dstyle, @Query("element") String element, @Query("housespace") String housespace,@Query("page") String page, @Query("size") String size,@Query("sorttype") String sorttype);

    @GET(HttpConstant.GET_COLLECTION_VR)
    Call<VrList> getCollVr(@Query("page") String page, @Query("size") String size);

    @GET(HttpConstant.GET_COLLECTION_SOFT)
    Call<SoftLoadingList> getCollSoft(@Query("page") String page, @Query("size") String size);

    @GET(HttpConstant.GET_COLLECTION_CONS)
    Call<ConstructionList> getCollCons(@Query("page") String page, @Query("size") String size);

    @GET(HttpConstant.GET_COLLECTION_BUILDING)
    Call<BuildingList> getCollBuilding(@Query("page") String page, @Query("size") String size);

    @GET(HttpConstant.GET_COLLECTION_NEAR)
    Call<NearList> getCollNear(@Query("page") String page, @Query("size") String size);

    @GET(HttpConstant.GET_FOCUS_CASE)
    Call<FocusCase> getFocusCase();

    @GET(HttpConstant.GET_FOCUS_DESIGNER)
    Call<DesignerList> getFocusDesigner(@Query("page") String page, @Query("size") String size);

    @FormUrlEncoded
    @POST(HttpConstant.SET_FOCUS)
    Call<BaseBean> setFocus(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_SHARE_LIST)
    Call<ShareList> getShareList();

    @GET(HttpConstant.GET_COUPONS_NUM)
    Call<CouponsNum> getCouponsNum();

    @GET(HttpConstant.GET_COUPONS_LIST)
    Call<Coupons> getCouponsList(@Query("page") String page, @Query("size") String size,@Query("status") String status);

    @GET(HttpConstant.GET_ACTIVITY)
    Call<Move> getActivity();

    @GET(HttpConstant.GET_MAIN_DESIGNER)
    Call<MainDesigner> getMainDesigner();

    @GET(HttpConstant.GET_TOKEN)
    Call<BaseBean> getToken(@Query("token")String token);

    @GET(HttpConstant.GET_CALL)
    Call<Telphone> getCall();

    @GET(HttpConstant.GET_TOTAL_SEARCH)
    Call<TotalSearch> getTotalSearch(@Query("name")String name);

    @FormUrlEncoded
    @POST(HttpConstant.BESPOKE)
    Call<BaseBean> bespoke(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.SET_ROOM_ENTRY)
    Call<BaseBean> setRoomEntry(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_VOUCHER_DETAILS)
    Call<VoucherDetails> getVoucherDetails(@Query("id") String id);

    @GET(HttpConstant.WX_LOGIN)
    Call<UserInfo> wxLogin(@Query("city") String city,@Query("img") String img,@Query("name") String name,@Query("openid") String openid);

    @GET(HttpConstant.GET_NEWS_NUM)
    Call<NewsNum> getNewsNum();

    @GET(HttpConstant.GET_NEWS)
    Call<News> getNews(@Query("page") String page, @Query("size") String size, @Query("status") String status);

    @GET(HttpConstant.GET_AREA)
    Call<Area> getArea(@Query("city") String city);

    @GET(HttpConstant.GET_MAIN_CASE_IMG)
    Call<MainCaseImg> getMainCaseImg(@Query("style") String style);

    @FormUrlEncoded
    @POST(HttpConstant.FORGET_PASSWORD)
    Call<BaseBean> forgetPwd(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_FOUND_BOTTOM)
    Call<Found> getFoundBottom();

    @GET(HttpConstant.GET_FOUND_BANNER)
    Call<FoundBanner> getFoundBanner();

    @GET(HttpConstant.GET_CHIEF_DESIGNER)
    Call<ChiefDesigner> getChiefDegister();

    @GET(HttpConstant.GET_MAIN_BRAND)
    Call<MainBrand> getMainBrand();

    @GET(HttpConstant.GET_DECORATE_TYPE)
    Call<DecorateType> getDecorateType();

    @GET(HttpConstant.GET_DECORATE_LIST)
    Call<MainDecorate> getDecorateList(@Query("page") String page, @Query("size") String size, @Query("cid") String cid);

    @GET(HttpConstant.GET_DECORATE_NEWS)
    Call<MainDecorate> getDecorateNews(@Query("page") String page, @Query("size") String size, @Query("title") String title);

    @GET(HttpConstant.GET_PIN_ZHI)
    Call<Pinzhi> getPinzhi();

    @FormUrlEncoded
    @POST(HttpConstant.COLL_VR_SOFT_CASE)
    Call<BaseBean> coll_vr_soft_case(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_GALLERY)
    Call<BaseBean> collGallery(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_CONSTRUCTION)
    Call<BaseBean> collConstruction(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_DESIGNER)
    Call<BaseBean> collDesigner(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_BUILDING)
    Call<BaseBean> collBuilding(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_NEWS)
    Call<BaseBean> collNews(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.COLL_NEAR)
    Call<BaseBean> collNear(@FieldMap Map<String, String> map);

    @GET(HttpConstant.IS_COLL)
    Call<IsColl> isColl(@Query("id") String id,@Query("stype") String  stype);

    @FormUrlEncoded
    @POST(HttpConstant.CANCLE_COLLECTION)
    Call<BaseBean> cancleCollection(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.ADD_SHARE)
    Call<BaseBean> addShare(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_COLL_DECORATE)
    Call<CollDecorate> getCollDecorate(@Query("page") String page, @Query("size") String size, @Query("cid") String cid);

    @GET(HttpConstant.GET_COLL_NUM)
    Call<CollNum> getCollNum();

    @GET(HttpConstant.GET_BUILDING_DETAILS)
    Call<BuildingDetails> getBuildingDetails(@Query("id") String id);

    @GET(HttpConstant.GET_CONS_DETAILS)
    Call<ConstructionDetails> getConsDetails(@Query("id") String id);

    @GET(HttpConstant.GET_CASE_DETAILS)
    Call<CaseDetails> getCaseDetails(@Query("id") String id);

    @GET(HttpConstant.GET_DESIGNER_DETAILS)
    Call<DesignerDetails> getDesignerDetails(@Query("id") String id);

    @GET(HttpConstant.GET_NEAR_DETAILS)
    Call<NearDetails> getNearDetails(@Query("id") String id);

    @GET(HttpConstant.GET_VERSION)
    Call<Version> getVersion();

    @GET(HttpConstant.GET_GOOD_DETAILS)
    Call<GoodDetail> getGoodDetails(@Query("id") String id);

    @GET(HttpConstant.GET_DESIGNER_TYPE)
    Call<Screening> getDesignerType();

    @FormUrlEncoded
    @POST(HttpConstant.SET_NEW_STATUS)
    Call<BaseBean> setNewsStatus(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.FOUCE_DEGISTER)
    Call<BaseBean> fouceDesigner(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.CANCLE_FOUCE)
    Call<BaseBean> cancleFouce(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_HELP)
    Call<Help> getHelp();

    @FormUrlEncoded
    @POST(HttpConstant.TAKE_CONPON)
    Call<BaseBean> takeCoupon(@FieldMap Map<String, String> map);

    @GET(HttpConstant.GET_HOBBY)
    Call<Hobby> getHobby();

    @GET(HttpConstant.GET_CITY)
    Call<City> getCity();

    @FormUrlEncoded
    @POST(HttpConstant.BING_MOBILE)
    Call<BaseBean> bingMobile(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(HttpConstant.SEND_IMEI)
    Call<BaseBean> sendImei(@FieldMap Map<String, String> map);
}
