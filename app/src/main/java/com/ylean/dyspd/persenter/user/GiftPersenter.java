package com.ylean.dyspd.persenter.user;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.CouponsNum;
import com.zxdc.utils.library.bean.VoucherNum;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2019/11/23.
 */

public class GiftPersenter {

    private Activity activity;

    public GiftPersenter(Activity activity){
        this.activity=activity;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //获取优惠券数量
                case HandlerConstant.GET_COUPONS_NUM_SUCCESS:
                      CouponsNum couponsNum= (CouponsNum) msg.obj;
                      if(couponsNum==null){
                          break;
                      }
                      if(couponsNum.isSussess() && couponsNum.getData()!=null){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_COUPONS_NUM,couponsNum.getData()));
                      }else{
                          ToastUtil.showLong(couponsNum.getDesc());
                      }
                      break;
                //获取兑换券的分类数量
                case HandlerConstant.GET_VOUCHER_NUM_SUCCESS:
                     VoucherNum voucherNum= (VoucherNum) msg.obj;
                     if(voucherNum==null){
                         break;
                     }
                     if(voucherNum.isSussess() && voucherNum.getData()!=null){
                         EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_VOUCHER_NUM,voucherNum.getData()));
                     }else{
                         ToastUtil.showLong(voucherNum.getDesc());
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
     * 获取优惠券数量
     */
    public void getCouponsNum(){
        HttpMethod.getCouponsNum(handler);
    }

    /**
     * 获取兑换券的分类数量
     */
    public void getVoucherNum(){
        HttpMethod.getVoucherNum(handler);
    }
}
