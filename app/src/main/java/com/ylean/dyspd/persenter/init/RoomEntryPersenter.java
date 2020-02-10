package com.ylean.dyspd.persenter.init;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.ylean.dyspd.activity.init.HobbyActivity;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.Screening;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class RoomEntryPersenter {

    private Activity activity;

    public RoomEntryPersenter(Activity activity){
        this.activity=activity;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            Screening screening;
            switch (msg.what){
                //获取户型数据
                case HandlerConstant.GET_SCREENING_MODEL:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        EventBus.getDefault().post(new EventBusType(EventStatus.ROOM_ENTRY_MODEL,screening.getData()));
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取面积数据
                case HandlerConstant.GET_SCREENING_AREA:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        EventBus.getDefault().post(new EventBusType(EventStatus.ROOM_ENTRY_AREA,screening.getData()));
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //设置户型
                case HandlerConstant.SET_ROOM_ENTRY_SUCCESS:
                      final BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          Intent intent=new Intent(activity, HobbyActivity.class);
                          activity.startActivity(intent);
                      }else{
                          ToastUtil.showLong(baseBean.getDesc());
                      }
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 获取风格,户型,面积数据
     */
    public void getScreening(String type, int index) {
        DialogUtil.showProgress(activity,"数据加载中...");
        HttpMethod.getScreening(type, index, handler);
    }


    /**
     * 会员设置房型
     */
    public void setRoomEntry(String houserarea,String housertype){
        DialogUtil.showProgress(activity,"设置中...");
        HttpMethod.setRoomEntry(houserarea,housertype,handler);
    }
}
