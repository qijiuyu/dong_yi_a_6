package com.ylean.dyspd.persenter.user;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.NewsNum;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public class NewsPersenter {

    private Activity activity;

    public NewsPersenter(Activity activity){
        this.activity=activity;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取消息数量
                case HandlerConstant.GET_NEWS_NUM_SUCCESS:
                     final NewsNum newsNum= (NewsNum) msg.obj;
                     if(newsNum==null){
                         break;
                     }
                     if(newsNum.isSussess()){
                         if(newsNum.getData()==null){
                             newsNum.setData(new NewsNum.NewsNumBean());
                         }
                         EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_NEWS_NUM,newsNum.getData()));
                     }else{
                         ToastUtil.showLong(newsNum.getDesc());
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
     * 获取消息数量
     */
    public void getNewsNum(){
        HttpMethod.getNewsNum(handler);
    }
}
