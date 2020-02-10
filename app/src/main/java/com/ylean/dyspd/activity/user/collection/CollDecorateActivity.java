package com.ylean.dyspd.activity.user.collection;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import com.ylean.dyspd.R;
import com.ylean.dyspd.fragment.user.CollDecorateFragment;
import com.ylean.dyspd.view.CustomViewPager;
import com.ylean.dyspd.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.DecorateType;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/12/7.
 */
public class CollDecorateActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    //分类列表
    public static List<DecorateType.TypeBean> typelist=new ArrayList<>();
    private DisplayMetrics dm;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coll_decorate);
        ButterKnife.bind(this);
        //获取装修攻略分类
        getDecorateType();

        //返回
        linBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CollDecorateActivity.this.finish();
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //获取装修攻略分类
                case HandlerConstant.GET_DOCORATE_TYPE_SUCCESS:
                    DecorateType decorateType = (DecorateType) msg.obj;
                    if (decorateType == null) {
                        break;
                    }
                    if (decorateType.isSussess() && decorateType.getData() != null) {
                        typelist=decorateType.getData();
                        //初始化viewpager
                        initPager();
                    } else {
                        ToastUtil.showLong(decorateType.getDesc());
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
     * 初始化viewpager
     */
    private void initPager(){
        dm = getResources().getDisplayMetrics();
        pager.setScanScroll(false);
        pager.setAdapter(new CollDecorateActivity.MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(typelist.size());
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


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return typelist.get(position).getCommonvalue();
        }

        @Override
        public int getCount() {
            return typelist.size();
        }

        @Override
        public Fragment getItem(int position) {
            CollDecorateFragment collDecorateFragment=new CollDecorateFragment();
            collDecorateFragment.fragmentIndex=position;
            return collDecorateFragment;
        }
    }


    /**
     * 获取装修攻略分类
     */
    private void getDecorateType() {
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getDecorateType(handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
