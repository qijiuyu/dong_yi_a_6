package com.ylean.dyspd.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.init.RoomEntryAdapter;
import com.zxdc.utils.library.bean.Screening;

import java.util.List;

/**
 * Created by Administrator on 2019/12/2.
 */

public class AreaPopWindow extends PopupWindow {

    private Activity activity;
    private View view;
    private ListView listView;
    //展示及关闭动画
    private Animation animation;
    private RoomEntryAdapter roomEntryAdapter;

    public AreaPopWindow(Activity activity){
        this.activity=activity;
        initView();
    }


    private void initView(){
        view= LayoutInflater.from(activity).inflate(R.layout.popwindow_room,null);
        listView=view.findViewById(R.id.listView);
        this.setContentView(view);
        //设置弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置背景色
        ColorDrawable cd = new ColorDrawable(Color.argb(0, 0, 0, 0));
        this.setBackgroundDrawable(cd);
        //设置外部点击不会关闭
        this.setFocusable(false);
        this.setOutsideTouchable(false);
    }


    /**
     * 展开动画
     */
    public void openShow(){
        animation=new TranslateAnimation(0,0,-500,0);
        animation.setDuration(600);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }


    /**
     * 关闭动画弹框
     */
    public void closeShow(){
        animation=new TranslateAnimation(0,0,0,-500);
        animation.setDuration(600);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void setData(final List<Screening.ScreeningBean> list, final TextView tvName){
        listView.setAdapter(new RoomEntryAdapter(activity,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvName.setText(list.get(position).getName());
                closeShow();
            }
        });
    }
}
