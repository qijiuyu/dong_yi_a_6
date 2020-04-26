package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.fragment.user.CouponsFragment;
import com.ylean.dyspd.fragment.user.VoucherFragment;
import com.ylean.dyspd.persenter.user.GiftPersenter;
import com.ylean.dyspd.view.PagerSlidingTabStrip;
import com.ylean.dyspd.view.ViewPagerCallBack;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.CouponsNum;
import com.zxdc.utils.library.bean.VoucherNum;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class GiftActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tv_coupons)
    TextView tvCoupons;
    @BindView(R.id.tv_voucher)
    TextView tvVoucher;
    @BindView(R.id.img_gift)
    ImageView imgGift;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    private DisplayMetrics dm;
    //存储各自的标题名称
    private List<String> titleName=new ArrayList<>();
    /**
     * 0：优惠券
     * 1：兑换券
     */
    private int type;
    private GiftPersenter giftPersenter;
    private MyPagerAdapter myPagerAdapter;
    //页面下标
    public static int pagerIndex;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift);
        ButterKnife.bind(this);
        initView();
        //获取优惠券数量
        giftPersenter.getCouponsNum();
    }


    /**
     * 初始化
     */
    private void initView(){
        //注册eventBus
        EventBus.getDefault().register(this);
        giftPersenter=new GiftPersenter(this);
        dm = getResources().getDisplayMetrics();
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
        tabs.setTypeface(null,Typeface.BOLD);
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);
    }



    @OnClick({R.id.lin_back, R.id.tv_coupons, R.id.tv_voucher, R.id.img_gift})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //优惠券
            case R.id.tv_coupons:
                if(type==0){
                    return;
                }
                type=0;
                tvCoupons.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tvVoucher.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                //获取优惠券数量
                giftPersenter.getCouponsNum();
                break;
            //兑换券
            case R.id.tv_voucher:
                if(type==1){
                    return;
                }
                type=1;
                tvCoupons.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tvVoucher.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                //获取兑换券的分类数量
                giftPersenter.getVoucherNum();
                break;
            //礼品兑换
            case R.id.img_gift:
                Intent intent=new Intent(this,VoucherGoodActivity.class);
                startActivityForResult(intent,100);
                break;
            default:
                break;
        }
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleName.get(position);
        }

        @Override
        public int getCount() {
            return titleName.size();
        }

        @Override
        public Fragment getItem(int position) {
            if(type==0){
                return new CouponsFragment();
            }else{
                return new VoucherFragment();
            }
        }

        public int getItemPosition(@NonNull Object object) {
            if (object.getClass().getName().equals(CouponsFragment.class.getName()) || object.getClass().getName().equals(VoucherFragment.class.getName())) {
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup arg0, int arg1) {
            return super.instantiateItem(arg0, arg1);
        }
    }



    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //优惠券数量
            case EventStatus.SHOW_COUPONS_NUM:
                  setTitleName((CouponsNum.CouponsNumBean) eventBusType.getObject(),null);
                  break;
            //兑换券的分类数量
            case EventStatus.SHOW_VOUCHER_NUM:
                 setTitleName( null,(VoucherNum.VoucherNumBean) eventBusType.getObject());
                 break;
            default:
                break;
        }
    }


    private ViewPagerCallBack viewPagerCallBack=new ViewPagerCallBack() {
        public void PageSelected(int position) {
            pagerIndex=position;


            //埋点
            if(type==0 && position==1){
                MobclickAgent.onEvent(GiftActivity.this, "my_gift_overdue");
            }
            if(type==1){
                if(position==0){
                    MobclickAgent.onEvent(GiftActivity.this, "my_voucher_confirm");
                }
                if(position==1){
                    MobclickAgent.onEvent(GiftActivity.this, "my_voucher_no_reveive");
                }
                if(position==2){
                    MobclickAgent.onEvent(GiftActivity.this, "my_voucher_reveive");
                }
            }
        }
    };


    /**
     * 设置各自的标题
     */
    private void setTitleName(CouponsNum.CouponsNumBean couponsNumBean,VoucherNum.VoucherNumBean voucherNumBean){
        titleName.clear();
        if(type==0){
            titleName.add("未使用("+couponsNumBean.getDsycount()+"）");
            titleName.add("已过期("+couponsNumBean.getYsxcount()+"）");
            titleName.add("已使用("+couponsNumBean.getYsycount()+"）");
        }else{
            titleName.add("待确认("+voucherNumBean.getDqrcount()+")");
            titleName.add("待领取("+voucherNumBean.getDlqcount()+")");
            titleName.add("已领取("+voucherNumBean.getYlqcount()+")");
            titleName.add("已失效("+voucherNumBean.getYsxcount()+")");
        }
        pagerIndex=0;
        tabs.setPageIndex(0);
        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myPagerAdapter);
        pager.setOffscreenPageLimit(titleName.size());
        tabs.setViewPager(pager);
        tabs.setViewPagerCallBack(viewPagerCallBack);
        setTabsValue();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==100){
            if(type==1){
                //获取兑换券的分类数量
                giftPersenter.getVoucherNum();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
