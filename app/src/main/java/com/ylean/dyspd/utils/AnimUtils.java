package com.ylean.dyspd.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.ylean.dyspd.view.SuspensionButtonView;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.Util;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2019/12/8.
 */

public class AnimUtils {


    public static void showAnim(Activity context, List<ImageView> list, ImageView parentView, final SuspensionButtonView.AnimEndCallBack animEndCallBack){
        ObjectAnimator animatorY=null,animatorX=null;
        AnimatorSet animatorSet= new AnimatorSet();
        int[] location = new int[2];
        //获取选中图片的坐标
        parentView.getLocationOnScreen (location);
        int x = location[0]+(parentView.getWidth()/4);
        int y = (location[1]+(parentView.getHeight()/4)- Util.getStatusBarHeight(context));
        for (int i=0;i<list.size();i++){
             ImageView imageView=list.get(i);
             if(imageView.getTag().toString().equals("0")){
                continue;
             }
             if(i==0){
                 imageView.setVisibility(View.VISIBLE);
                 animatorY = ObjectAnimator.ofFloat(imageView, "y", y, y-Util.dip2px(context,60));
                 animatorX = ObjectAnimator.ofFloat(imageView, "x", x, x-Util.dip2px(context,60));
             }
             if(i==1){
                 imageView.setVisibility(View.VISIBLE);
                 animatorY = ObjectAnimator.ofFloat(imageView, "y", y, y);
                 animatorX = ObjectAnimator.ofFloat(imageView, "x", x, x-Util.dip2px(context,80));
             }
             if(i==2){
                 imageView.setVisibility(View.VISIBLE);
                 animatorY = ObjectAnimator.ofFloat(imageView, "y", y, y+Util.dip2px(context,60));
                 animatorX = ObjectAnimator.ofFloat(imageView, "x", x, x-Util.dip2px(context,60));
             }
            animatorSet.playTogether(animatorX, animatorY);
            animatorSet.setDuration(200);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animEndCallBack.openAnimEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }


    public static void closeAnim(Activity context, List<ImageView> list, ImageView parentView, final SuspensionButtonView.AnimEndCallBack animEndCallBack){
        ObjectAnimator animatorY=null,animatorX=null;
        AnimatorSet animatorSet= new AnimatorSet();
        int[] location = new int[2];
        //获取选中图片的坐标
        parentView.getLocationOnScreen (location);
        int x = location[0]+(parentView.getWidth()/4);
        int y = (location[1]+(parentView.getHeight()/4)- Util.getStatusBarHeight(context));
        for (int i=0;i<list.size();i++){
            final ImageView imageView=list.get(i);
            if(imageView.getTag().toString().equals("0")){
                continue;
            }
            if(i==0){
                animatorY = ObjectAnimator.ofFloat(imageView, "y", y-Util.dip2px(context,60), y);
                animatorX = ObjectAnimator.ofFloat(imageView, "x", x-Util.dip2px(context,60), x);
            }
            if(i==1){
                animatorY = ObjectAnimator.ofFloat(imageView, "y", y, y);
                animatorX = ObjectAnimator.ofFloat(imageView, "x", x-Util.dip2px(context,80),x);
            }
            if(i==2){
                animatorY = ObjectAnimator.ofFloat(imageView, "y", y+Util.dip2px(context,60), y);
                animatorX = ObjectAnimator.ofFloat(imageView, "x", x-Util.dip2px(context,60), x);
            }
            animatorSet.playTogether(animatorX, animatorY);
            animatorSet.setDuration(200);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    imageView.setVisibility(View.GONE);
                    animEndCallBack.closeAnimEnd();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }


    public static void setXY(ImageView imageView,int x,int y){
        ObjectAnimator animatorY=null,animatorX=null;
        AnimatorSet animatorSet= new AnimatorSet();
        animatorY = ObjectAnimator.ofFloat(imageView, "y", y, y);
        animatorX = ObjectAnimator.ofFloat(imageView, "x", x, x);
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setDuration(0);
        animatorSet.start();
    }
}
