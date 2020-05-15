package com.ylean.dyspd.activity.web.decorate;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.utils.PointUtil;

public class WebViewPoint {

    private DecorateWebView activity;

    public WebViewPoint(DecorateWebView activity){
        this.activity=activity;
    }


    public void potin(int type){
        switch (type){
            case 1:
                MobclickAgent.onEvent(activity, "case_choosethis");
                 break;
            case 2:
                MobclickAgent.onEvent(activity, "case_concept_more");
                break;
            case 3:
                MobclickAgent.onEvent(activity, "case_shop_img");
                break;
            case 4:
                MobclickAgent.onEvent(activity, "case_shop_btn");
                break;
            case 5:
                MobclickAgent.onEvent(activity, "case_shop_telphone");
                break;
            case 6:
                MobclickAgent.onEvent(activity, "case_shop_appointment");
                break;
            case 7:
                MobclickAgent.onEvent(activity, "caseimg_ascription");
                break;
            case 8:
                MobclickAgent.onEvent(activity, "caseimg_choosethis");
                break;
            case 9:
                MobclickAgent.onEvent(activity, "case_info_bedroom");
                break;
            case 10:
                MobclickAgent.onEvent(activity, "case_info_restaurant");
                break;
            case 11:
                MobclickAgent.onEvent(activity, "case_info_livingroom");
                break;
            case 12:
                MobclickAgent.onEvent(activity, "build_appointment");
                break;
            case 13:
                MobclickAgent.onEvent(activity, "build_case_more");
                break;
            case 14:
                MobclickAgent.onEvent(activity, "build_house_more");
                break;
            case 15:
                MobclickAgent.onEvent(activity, "build_site_more");
                break;
            case 16:
                MobclickAgent.onEvent(activity, "build_designer_more");
                break;
            case 17:
                MobclickAgent.onEvent(activity, "site_appointment_visit");
                break;
            case 18:
                MobclickAgent.onEvent(activity, "build_stage_start");
                break;
            case 19:
                MobclickAgent.onEvent(activity, "build_stage_early");
                break;
            case 20:
                MobclickAgent.onEvent(activity, "build_stage_metaphase");
                break;
            case 21:
                MobclickAgent.onEvent(activity, "build_stage_last");
                break;
            case 22:
                MobclickAgent.onEvent(activity, "build_stage_completed");
                break;
            case 23:
                MobclickAgent.onEvent(activity, "build_shopinfo_img");
                break;
            case 24:
                MobclickAgent.onEvent(activity, "build_shopinfo_phone");
                break;
            case 25:
                MobclickAgent.onEvent(activity, "special_car_name");
                break;
            case 26:
                MobclickAgent.onEvent(activity, "special_car_phone");
                break;
            case 27:
                MobclickAgent.onEvent(activity, "special_car_address");
                break;
            case 28:
                MobclickAgent.onEvent(activity, "special_car_button");
                break;
            case 29:
                MobclickAgent.onEvent(activity, "designer_choosethis");
                break;
            case 30:
                MobclickAgent.onEvent(activity, "designer_introduction_all");
                break;
            case 31:
                MobclickAgent.onEvent(activity, "designer_case_more");
                break;
            case 32:
                MobclickAgent.onEvent(activity, "designer_house_more");
                break;
            case 33:
                MobclickAgent.onEvent(activity, "designer_site_more");
                break;
            case 34:
                MobclickAgent.onEvent(activity, "designer_build_more");
                break;
            case 35:
                MobclickAgent.onEvent(activity, "designer_shop_img");
                break;
            case 36:
                MobclickAgent.onEvent(activity, "designer_shop_button");
                break;
            case 37:
                MobclickAgent.onEvent(activity, "appointment_design_name");
                break;
            case 38:
                MobclickAgent.onEvent(activity, "appointment_design_phone");
                break;
            case 39:
                MobclickAgent.onEvent(activity, "appointment_design_address");
                break;
            case 40:
                MobclickAgent.onEvent(activity, "appointment_design_button");
                break;
            case 41:
                MobclickAgent.onEvent(activity, "shop_introduce_more");
                break;
            case 42:
                MobclickAgent.onEvent(activity, "shop_designer_more");
                break;
            case 43:
                MobclickAgent.onEvent(activity, "shop_case_more");
                break;
            case 44:
                MobclickAgent.onEvent(activity, "shop_house_more");
                break;
            case 45:
                MobclickAgent.onEvent(activity, "shop_site_more");
                break;
            case 46:
                MobclickAgent.onEvent(activity, "shop_getaddress_button");
                break;

        }
    }
}
