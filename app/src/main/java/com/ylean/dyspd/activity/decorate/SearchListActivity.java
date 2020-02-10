package com.ylean.dyspd.activity.decorate;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.fragment.SearchFragment;
import com.ylean.dyspd.fragment.search.SearchBuildingFragment;
import com.ylean.dyspd.fragment.search.SearchCaseFragment;
import com.ylean.dyspd.fragment.search.SearchConstructionFragment;
import com.ylean.dyspd.fragment.search.SearchDecorateFragment;
import com.ylean.dyspd.fragment.search.SearchDesignerFragment;
import com.ylean.dyspd.fragment.search.SearchGalleryFragment;
import com.ylean.dyspd.fragment.search.SearchNearFragment;
import com.ylean.dyspd.fragment.search.SearchSoftFragment;
import com.ylean.dyspd.fragment.search.SearchVRFragment;
import com.ylean.dyspd.view.PagerSlidingTabStrip;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索的列表页面
 * Created by Administrator on 2019/11/8.
 */

public class SearchListActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    //搜索的关键字
    public static String strKey;
    private DisplayMetrics dm;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();

        //返回
        tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchListActivity.this.finish();
            }
        });
    }


    /**
     * 初始化
     */
    private void initView() {
        //获取要搜索的关键字
        strKey=getIntent().getStringExtra("keys");
        etKey.setOnEditorActionListener(this);
        dm = getResources().getDisplayMetrics();
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(9);
        tabs.setViewPager(pager);
        setTabsValue();
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        //点击“全部”里的更多按钮
        switch (eventBusType.getStatus()) {
            case EventStatus.SELECT_SEARCH_LIST:
                  int page= (int) eventBusType.getObject();
                  pager.setCurrentItem(++page);
                  break;
            default:
                break;
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            strKey = etKey.getText().toString().trim();
            if (TextUtils.isEmpty(strKey)) {
                ToastUtil.showLong("请输入您要搜索的关键字！");
                return false;
            }
            //关闭键盘
            lockKey(etKey);
            //保存搜索过的关键字
            addTabKey();
            EventBus.getDefault().post(new EventBusType(EventStatus.SEARCH_FRAGMENT_BY_KEYS));
        }
        return false;
    }

    /**
     * 保存搜索过的关键字
     */
    private void addTabKey() {
        String keys = SPUtil.getInstance(this).getString(SPUtil.SEARCH_KEY);
        Map<String, String> keyMap;
        if (!TextUtils.isEmpty(keys)) {
            keyMap = SPUtil.gson.fromJson(keys, Map.class);
        } else {
            keyMap = new HashMap<>();
        }
        keyMap.put(strKey, strKey);
        SPUtil.getInstance(this).addString(SPUtil.SEARCH_KEY, SPUtil.gson.toJson(keyMap));
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
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
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
        tabs.setSelectTextSize(15);
        tabs.setTypeface(null, Typeface.BOLD);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        String[] str=new String[]{"全部","设计师","风格案例","软装案例","楼盘/小区","实景工地","效果图","VR样板间","体验店","攻略"};

        @Override
        public CharSequence getPageTitle(int position) {
            return str[position];
        }

        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0){
                return new SearchFragment();
            }
            if(position==1){
                return new SearchDesignerFragment();
            }
            if(position==2){
                return new SearchCaseFragment();
            }
            if(position==3){
                return new SearchSoftFragment();
            }
            if(position==4){
                return new SearchBuildingFragment();
            }
            if(position==5){
                return new SearchConstructionFragment();
            }
            if(position==6){
                return new SearchGalleryFragment();
            }
            if(position==7){
                return new SearchVRFragment();
            }
            if(position==8){
                return new SearchNearFragment();
            }
            if(position==9){
                return new SearchDecorateFragment();
            }
            return null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
