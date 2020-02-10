package com.ylean.dyspd.activity.user;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.view.CustomViewPager;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.fragment.user.ActivityFragment;
import com.ylean.dyspd.fragment.user.DynamicFragment;
import com.ylean.dyspd.fragment.user.SignFragment;
import com.ylean.dyspd.persenter.user.NewsPersenter;
import com.ylean.dyspd.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.NewsNum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的消息
 * Created by Administrator on 2019/11/9.
 */
public class NewsActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.rel_sign)
    RelativeLayout relSign;
    @BindView(R.id.tv_activity)
    TextView tvActivity;
    @BindView(R.id.rel_activity)
    RelativeLayout relActivity;
    @BindView(R.id.tv_dynamic)
    TextView tvDynamic;
    @BindView(R.id.rel_dynamic)
    RelativeLayout relDynamic;
    private DisplayMetrics dm;
    private NewsPersenter newsPersenter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        initView();
        //注册eventBus
        EventBus.getDefault().register(this);
        //获取消息数量
        newsPersenter.getNewsNum();
    }


    /**
     * 初始化
     */
    private void initView() {
        newsPersenter=new NewsPersenter(this);
        dm = getResources().getDisplayMetrics();
        //设置其是否能滑动换页
        pager.setScanScroll(false);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(3);
        tabs.setViewPager(pager);
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
        tabs.setTextColorResource(R.color.color_666666);
        tabs.setSelectedTextColorResource(R.color.color_33333);
        tabs.setTypeface(null, Typeface.BOLD);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }

    @OnClick({R.id.lin_back, R.id.rel_sign, R.id.rel_activity, R.id.rel_dynamic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //公告
            case R.id.rel_sign:
                pager.setCurrentItem(0);
                break;
            //活动
            case R.id.rel_activity:
                pager.setCurrentItem(1);
                break;
            //动态
            case R.id.rel_dynamic:
                pager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new SignFragment();
            }else if(position==1){
                return new ActivityFragment();
            }else{
                return new DynamicFragment();
            }
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //更新消息数量
            case EventStatus.UPDATE_NEWS_NUM:
                  newsPersenter.getNewsNum();
                  break;
            //展示消息数量
            case EventStatus.SHOW_NEWS_NUM:
                  final NewsNum.NewsNumBean newsNumBean= (NewsNum.NewsNumBean) eventBusType.getObject();
                  if(newsNumBean==null){
                      return;
                  }
                  if(newsNumBean.getGgcount()>0){
                      tvSign.setVisibility(View.VISIBLE);
                      tvSign.setText(String.valueOf(newsNumBean.getGgcount()));
                  }else{
                      tvSign.setVisibility(View.GONE);
                  }
                  if(newsNumBean.getHdcount()>0){
                      tvActivity.setVisibility(View.VISIBLE);
                      tvActivity.setText(String.valueOf(newsNumBean.getHdcount()));
                  }else{
                      tvActivity.setVisibility(View.GONE);
                  }
                  if(newsNumBean.getDtcount()>0){
                      tvDynamic.setVisibility(View.VISIBLE);
                      tvDynamic.setText(String.valueOf(newsNumBean.getDtcount()));
                  }else{
                      tvDynamic.setVisibility(View.GONE);
                  }
                  break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
