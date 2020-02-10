package com.ylean.dyspd.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/19.
 */

public class CustomViewPager extends ViewPager {

    private boolean isCanScroll = true;
    private int screenWidth;//屏幕宽度

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置其是否能滑动换页
     * @param isCanScroll false 不能换页， true 可以滑动换页
     */
    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        screenWidth = MeasureSpec.getSize(widthMeasureSpec);//view测量时获取屏幕宽度
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // screenWidth = getResources().getDisplayMetrics().widthPixels;
        System.out.println("屏幕宽度" + screenWidth);
        /*判断屏幕是否满足一定条件，满足则中断时间
        即，两边各留出一定宽度使靠边滑动时可以相应父pagerview   的事件，例如左边有侧滑菜单，右边靠边可以滑到另一个父viewpager的下一个*/
        if (ev.getRawX() > screenWidth / 8 && ev.getRawX() < screenWidth * 7 / 8) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
