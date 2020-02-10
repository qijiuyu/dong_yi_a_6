package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.ylean.dyspd.R;
import com.ylean.dyspd.fragment.user.VoucherGoodFragment;
import com.ylean.dyspd.view.PagerSlidingTabStrip;
import com.ylean.dyspd.view.ViewPagerCallBack;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.GiftType;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 兑换商品
 * Created by Administrator on 2019/11/14.
 */

public class VoucherGoodActivity extends BaseActivity {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private DisplayMetrics dm;
    //分类标题列表
    public List<GiftType.GiftTypeBean> typeList=new ArrayList<>();
    //对应页面的下标
    public int pagerIndex;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_good);
        ButterKnife.bind(this);
        //获取礼包分类
        getGiftType();

        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(100,new Intent());
                VoucherGoodActivity.this.finish();
            }
        });
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取礼包分类
                case HandlerConstant.GET_GIFT_TYPE_SUCCESS:
                      GiftType giftType= (GiftType) msg.obj;
                      if(giftType==null){
                          break;
                      }
                      if(giftType.isSussess()){
                          typeList=giftType.getData();
                          //设置viewPager
                          initPagerView();
                      }else{
                          ToastUtil.showLong(giftType.getDesc());
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
     * 设置viewPager
     */
    private void initPagerView(){
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(typeList.size());
        tabs.setViewPager(pager);
        tabs.setViewPagerCallBack(viewPagerCallBack);
        setTabsValue();
    }


    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 14, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColorResource(R.color.color_b09b7c);
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setTextColorResource(R.color.color_33333);
        tabs.setSelectedTextColorResource(R.color.color_33333);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {

        private MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return typeList.get(position).getName();
        }

        @Override
        public int getCount() {
            return typeList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return new VoucherGoodFragment();
        }
    }


    private ViewPagerCallBack viewPagerCallBack=new ViewPagerCallBack() {
        public void PageSelected(int position) {
            pagerIndex=position;
        }
    };

    /**
     * 获取礼包分类
     */
    private void getGiftType(){
        DialogUtil.showProgress(this,"数据加载中");
        HttpMethod.getGiftType(handler);
    }


    @Override
    public void onBackPressed() {
        setResult(100,new Intent());
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
