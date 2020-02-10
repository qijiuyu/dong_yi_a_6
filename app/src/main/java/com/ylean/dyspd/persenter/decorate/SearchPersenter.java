package com.ylean.dyspd.persenter.decorate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.SearchListActivity;
import com.ylean.dyspd.view.TagsLayout;
import com.zxdc.utils.library.bean.HotSearch;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/11/28.
 */

public class SearchPersenter {

    private Activity activity;
    //热门搜索控件
    private TagsLayout tagHot;

    public SearchPersenter(Activity activity){
        this.activity=activity;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取热门搜索回执
                case HandlerConstant.GET_HOT_SEARCH_SUCCESS:
                      final HotSearch hotSearch= (HotSearch) msg.obj;
                      if(hotSearch==null){
                          break;
                      }
                      if(hotSearch.isSussess()){
                          //显示热门搜索
                          showHotSearch(hotSearch.getData());
                      }else{
                          ToastUtil.showLong(hotSearch.getDesc());
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
     * 显示热门搜索
     * @param list
     */
    private void showHotSearch(List<HotSearch.HotSearchBean> list){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0,len=list.size();i<len;i++) {
            TextView textView = new TextView(activity);
            textView.setText(list.get(i).getCommonvalue());
            textView.setTag(list.get(i).getCommontype());
            textView.setTextColor(activity.getResources().getColor(R.color.color_33333));
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.bg_tag_store);
            textView.setPadding(15, 10, 15, 10);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    gotoSearchList(((TextView) v).getText().toString());
                }
            });
            tagHot.addView(textView, lp);
        }
    }

    /**
     * 显示搜索过的历史
     * @param tags
     */
    public void showHistory(TagsLayout tags){
        tags.removeAllViews();
        final String keyStr= SPUtil.getInstance(activity).getString(SPUtil.SEARCH_KEY);
        if(TextUtils.isEmpty(keyStr)) {
            return;
        }
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Map<String, String> keyMap = SPUtil.gson.fromJson(keyStr, Map.class);
        Set<String> keys = keyMap.keySet();
        for (String key : keys) {
            TextView textView = new TextView(activity);
            textView.setText(keyMap.get(key));
            textView.setTextColor(activity.getResources().getColor(R.color.color_33333));
            textView.setTextSize(12);
            textView.setBackgroundResource(R.drawable.bg_tag_store);
            textView.setPadding(15, 10, 15, 10);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    gotoSearchList(((TextView) v).getText().toString());
                }
            });
            tags.addView(textView, lp);
        }

    }


    /**
     * 获取热门搜索
     */
    public void getHotSearch(TagsLayout tagHot){
        this.tagHot=tagHot;
        DialogUtil.showProgress(activity,"数据加载中...");
        HttpMethod.getHotSearch(handler);
    }

    /**
     * 跳转搜索结果页
     * @param keys
     */
    public void gotoSearchList(String keys){
        Intent intent=new Intent(activity,SearchListActivity.class);
        intent.putExtra("keys",keys);
        activity.startActivity(intent);
    }
}
