package com.ylean.dyspd.persenter.init;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.ylean.dyspd.activity.init.RoomEntryActivity;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;

public class LoginPersenter {

    private Activity activity;

    public LoginPersenter(Activity activity){
        this.activity=activity;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            BaseBean baseBean=null;
            switch (msg.what){
                //获取短信验证码
                case HandlerConstant.GET_SMS_CODE_SUCCESS:
                      baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                         break;
                     }
                     if(baseBean.isSussess()){
                         EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_SMS_CODE));
                     }
                     ToastUtil.showLong(baseBean.getDesc());
                     break;
                //注册
                case HandlerConstant.REGISTER_SUCCESS:
                     baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                        break;
                     }
                     if(baseBean.isSussess()){
                        EventBus.getDefault().post(new EventBusType(EventStatus.REGISTER_SUCCESS));
                     }
                     ToastUtil.showLong(baseBean.getDesc());
                      break;
                //登录
                case HandlerConstant.LOGIN_SUCCESS:
                     UserInfo userInfo= (UserInfo) msg.obj;
                     if(userInfo==null){
                         break;
                     }
                     if(userInfo.isSussess() && userInfo.getData()!=null){
                         //存储token数据
                         SPUtil.getInstance(activity).addString(SPUtil.TOKEN,userInfo.getToken());
                         //存储用户id
                         SPUtil.getInstance(activity).addInt(SPUtil.USER_ID,userInfo.getData().getId());
                         //存储用户openid
                         SPUtil.getInstance(activity).addString(SPUtil.OPEN_ID,userInfo.getData().getOpenid());
                         //保存手机号
                         SPUtil.getInstance(activity).addString(SPUtil.ACCOUNT,userInfo.getData().getMobile());
                         //判断是否首次登录
                         if(userInfo.getData().getFirstlogin()==0 && ((LoginActivity)activity).loginType==1){
                             Intent intent=new Intent(activity, RoomEntryActivity.class);
                             activity.startActivity(intent);
                         }else{
                             Intent intent=new Intent(activity, TabActivity.class);
                             activity.startActivity(intent);
                         }
                         activity.finish();
                     }else{
                        ToastUtil.showLong(userInfo.getDesc());
                     }
                      break;
                case HandlerConstant.REQUST_ERROR:
                      ToastUtil.showLong(msg.obj.toString());
                      break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 获取短信验证码
     * @param mobile
     */
    public void getSmsCode(String mobile){
        DialogUtil.showProgress(activity,"获取验证码中...");
        HttpMethod.getSmsCode(mobile,"0",handler);
    }


    /**
     * 注册
     */
    public void register(String mobile,String password,String smscode){
        DialogUtil.showProgress(activity,"注册中...");
        String city=SPUtil.getInstance(activity).getString(SPUtil.LOCATION_CITY);
        HttpMethod.register(city,mobile,password,smscode,handler);
    }


    /**
     * 登录
     */
    public void login(String mobile,String password){
        //保存账号和密码
        SPUtil.getInstance(activity).addString(SPUtil.ACCOUNT,mobile);
        SPUtil.getInstance(activity).addString(SPUtil.PASSWORD,password);
        DialogUtil.showProgress(activity,"登录中...");
        HttpMethod.login(mobile,password,handler);
    }


    /**
     * 微信登录
     */
    public void wxLogin(String img,String name,String openId){
        DialogUtil.showProgress(activity,"登录中...");
        String city=SPUtil.getInstance(activity).getString(SPUtil.LOCATION_CITY);
        HttpMethod.wxLogin(city,img,name,openId,handler);
    }
}
