package com.ylean.dyspd.persenter.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.main.SelectCityActivity;
import com.zxdc.utils.library.bean.City;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.utils.pinyin.CharacterParser;
import com.ylean.dyspd.utils.pinyin.SortModel;
import com.ylean.dyspd.view.TagsLayout;
import com.zxdc.utils.library.bean.HotCity;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class SelectCityPersenter {

    private Activity activity;

    public SelectCityPersenter(Activity activity){
        this.activity=activity;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取热门城市
                case HandlerConstant.GET_HOT_CITY_SUCCESS:
                      HotCity hotCity= (HotCity) msg.obj;
                      if(hotCity==null){
                          break;
                      }
                      if(!hotCity.isSussess()){
                          ToastUtil.showLong(hotCity.getDesc());
                      }
                      EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_HOT_CITY,hotCity.getData()));
                      break;
                //获取所有城市
                case HandlerConstant.GET_CITY_SUCCESS:
                      City city= (City) msg.obj;
                      if(city==null){
                          break;
                      }
                      if(city.isSussess()){
                          EventBus.getDefault().post(new EventBusType(EventStatus.SHOW_ALL_CITY,city.getData()));
                      }else{
                          ToastUtil.showLong(city.getDesc());
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
     * 显示搜索过的历史城市
     * @param tags
     */
    public void showHistoryCity(TagsLayout tags){
        final String keyStr=SPUtil.getInstance(activity).getString(SPUtil.HISTORY_CITY);
        if(TextUtils.isEmpty(keyStr)) {
            return;
        }
        List<String> list = new ArrayList<>();
        Map<String, String> keyMap = SPUtil.gson.fromJson(keyStr, Map.class);
        Set<String> keys = keyMap.keySet();
        for (String key : keys) {
             list.add(keyMap.get(key));
        }
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0,len=list.size(); i < len; i++) {
            TextView textView = new TextView(activity);
            textView.setText(list.get(i));
            textView.setTextColor(activity.getResources().getColor(R.color.color_33333));
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.bg_tag_store);
            textView.setPadding(15, 10, 15, 10);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ((SelectCityActivity)activity).selectCity(((TextView) v).getText().toString());
                }
            });
            tags.addView(textView, lp);
        }
    }


    /**
     * 保存点击过的历史城市
     */
    public void saveHistoryCity(String city){
        if(TextUtils.isEmpty(city)){
            return;
        }
        String keys= SPUtil.getInstance(activity).getString(SPUtil.HISTORY_CITY);
        Map<String,String> keyMap;
        if(!TextUtils.isEmpty(keys)){
            keyMap=SPUtil.gson.fromJson(keys,Map.class);
        }else{
            keyMap=new HashMap<>();
        }
        keyMap.put(city,city);
        SPUtil.getInstance(activity).addString(SPUtil.HISTORY_CITY,SPUtil.gson.toJson(keyMap));
    }


    /**
     * 获取热门城市
     */
    public void getHotCity(){
        DialogUtil.showProgress(activity,"数据加载中...");
        HttpMethod.getHotCity(handler);
    }


    /**
     * 获取城市列表
     */
    public void getCity(){
        HttpMethod.getCity(handler);
    }
}
