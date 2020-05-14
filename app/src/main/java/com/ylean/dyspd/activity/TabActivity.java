package com.ylean.dyspd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.brand.BrandActivity;
import com.ylean.dyspd.activity.decorate.DecorateActivity;
import com.ylean.dyspd.activity.found.FoundActivity;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.ylean.dyspd.activity.main.MainActivity;
import com.ylean.dyspd.activity.user.UserActivity;
import com.ylean.dyspd.application.MyApplication;
import com.ylean.dyspd.utils.DataCleanManager;
import com.ylean.dyspd.utils.NetUtil;
import com.ylean.dyspd.utils.UpdateVersionUtils;
import com.ylean.dyspd.view.SuspensionButtonView;
import com.zxdc.utils.library.bean.Telphone;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ActivitysLifecycle;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.error.CockroachUtil;
import com.zxdc.utils.library.view.ClickLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
/**
 * tab首页
 * Created by Administrator on 2019/11/7.
 */

public class TabActivity extends android.app.TabActivity{

    @BindView(R.id.lin_tab)
    LinearLayout linTab;
    @BindView(R.id.img_main)
    ImageView imgMain;
    @BindView(R.id.tv_main)
    TextView tvMain;
    @BindView(R.id.lin_main)
    ClickLinearLayout linMain;
    @BindView(R.id.img_decorate)
    ImageView imgDecorate;
    @BindView(R.id.tv_decorate)
    TextView tvDecorate;
    @BindView(R.id.lin_decorate)
    ClickLinearLayout linDecorate;
    @BindView(R.id.img_brand)
    ImageView imgBrand;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.lin_brand)
    ClickLinearLayout linBrand;
    @BindView(R.id.img_found)
    ImageView imgFound;
    @BindView(R.id.tv_found)
    TextView tvFound;
    @BindView(R.id.lin_found)
    ClickLinearLayout linFound;
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.lin_user)
    ClickLinearLayout linUser;
    @BindView(android.R.id.tabhost)
    TabHost tabhost;
    @BindView(R.id.suspensionView)
    SuspensionButtonView suspensionView;
    private int[] notClick = new int[]{R.mipmap.tab_1_false, R.mipmap.tab_2_false, R.mipmap.tab_3_false, R.mipmap.tab_4_false, R.mipmap.tab_5_false};
    private int[] yesClick = new int[]{R.mipmap.tab_1_true, R.mipmap.tab_2_true, R.mipmap.tab_3_true, R.mipmap.tab_4_true, R.mipmap.tab_5_true};
    // 按两次退出
    protected long exitTime = 0;
    private List<TextView> tvList = new ArrayList<>();
    private List<ImageView> imgList = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
        //查询最新版本
        new UpdateVersionUtils().getVersion(this);
        //设置推送
        setPush();
        //获取客服电话
        getCall();

        /**
         * 设置是否推送
         */
        final int jpush=SPUtil.getInstance(this).getInteger(SPUtil.JPUSH);
        if(jpush==0 && MyApplication.isLogin()){
            JPushInterface.resumePush(this);  		// 恢复推送
        }else{
            JPushInterface.stopPush(this);      //停止推送
        }

        //上传imei
        HttpMethod.sendImei(NetUtil.getIMEI(this),handler);
    }


    /**
     * 初始化
     */
    private void initView() {
        imgList.add(imgMain);
        imgList.add(imgDecorate);
        imgList.add(imgBrand);
        imgList.add(imgFound);
        imgList.add(imgUser);
        tvList.add(tvMain);
        tvList.add(tvDecorate);
        tvList.add(tvBrand);
        tvList.add(tvFound);
        tvList.add(tvUser);
        tabhost = this.getTabHost();
        TabHost.TabSpec spec;
        spec = tabhost.newTabSpec("首页").setIndicator("首页").setContent(new Intent(this, MainActivity.class));
        tabhost.addTab(spec);
        spec = tabhost.newTabSpec("装修").setIndicator("装修").setContent(new Intent(this, DecorateActivity.class));
        tabhost.addTab(spec);
        spec = tabhost.newTabSpec("品牌").setIndicator("品牌").setContent(new Intent(this, BrandActivity.class));
        tabhost.addTab(spec);
        spec = tabhost.newTabSpec("发现").setIndicator("发现").setContent(new Intent(this, FoundActivity.class));
        tabhost.addTab(spec);
        spec = tabhost.newTabSpec("我的").setIndicator("我的").setContent(new Intent(this, UserActivity.class));
        tabhost.addTab(spec);
        tabhost.setCurrentTab(0);

        //设置悬浮框没有返回首页的按钮
        suspensionView.isMain();

        //清理缓存
        new Handler().post(new Runnable() {
            public void run() {
                DataCleanManager.clearAllCache(TabActivity.this);
            }
        });
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_main, R.id.lin_decorate, R.id.lin_brand, R.id.lin_found, R.id.lin_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_main:
                updateTag(0);
                tabhost.setCurrentTabByTag("首页");
                break;
            case R.id.lin_decorate:
                updateTag(1);
                tabhost.setCurrentTabByTag("装修");
                break;
            case R.id.lin_brand:
                updateTag(2);
                tabhost.setCurrentTabByTag("品牌");

                //埋点
                MobclickAgent.onEvent(this, "menu_brand");
                break;
            case R.id.lin_found:
                updateTag(3);
                tabhost.setCurrentTabByTag("发现");
                break;
            case R.id.lin_user:
                final String token = SPUtil.getInstance(this).getString(SPUtil.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    updateTag(4);
                    tabhost.setCurrentTabByTag("我的");
                }
                break;
            default:
                break;
        }
    }


    /**
     * 切换tab时，更新图片和文字颜色
     */
    private void updateTag(int type) {
        for (int i = 0; i < 5; i++) {
            if (i == type) {
                imgList.get(i).setImageDrawable(getResources().getDrawable(yesClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_333333));
            } else {
                imgList.get(i).setImageDrawable(getResources().getDrawable(notClick[i]));
                tvList.get(i).setTextColor(getResources().getColor(R.color.color_999999));
            }
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //获取客服电话
                case HandlerConstant.GET_CALL_SUCCESS:
                    Telphone telphone= (Telphone) msg.obj;
                    if(telphone==null){
                        break;
                    }
                    if(telphone.isSussess()){
                        SPUtil.getInstance(TabActivity.this).addString(SPUtil.TEL,telphone.getData());
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
     * 设置推送
     */
    private void setPush() {
        int userId = SPUtil.getInstance(this).getInteger(SPUtil.USER_ID);
        if (userId == 0) {
            return;
        }
        //设置极光推送的别名
        JPushInterface.setAliasAndTags(getApplicationContext(), String.valueOf(userId), null, mAliasCallback);
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                //设置别名成功
                case 0:
                    LogUtils.e("推送设置成功");
                    break;
                //设置别名失败
                case 6002:
                    LogUtils.e("推送设置失败");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            setPush();
                        }
                    }, 30000);
                    break;
                default:
            }
        }
    };


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //悬浮框状态
            case EventStatus.SUSPENSION_STATUS:
                onTouch((MotionEvent) eventBusType.getObject());
                break;
            //跳转品牌菜单
            case EventStatus.GO_TO_BRAND:
                updateTag(2);
                tabhost.setCurrentTabByTag("品牌");
                StatusBarUtils.setStatusBarColor(this, android.R.color.black);
                break;
            //隐藏/显示底部tab
            case EventStatus.IS_SHOW_TAB:
                 boolean isShow= (boolean) eventBusType.getObject();
                 if(isShow){
                    linTab.setVisibility(View.VISIBLE);
                 }else{
                    linTab.setVisibility(View.GONE);
                 }
                  break;
            default:
                break;

        }
    }

    /**
     * 监听滑动事件
     *
     * @param event
     */
    float eventY;
    private void onTouch(MotionEvent event) {
        float newY = event.getY();
        if(eventY!=0){
            suspensionView.isVisible(newY,eventY);
        }
        eventY = newY;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 获取客服电话
     */
    private void getCall() {
        HttpMethod.getCall(handler);
    }

    /**
     * 连续点击两次返回退出
     *
     * @param event
     * @return
     */
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if(linTab.getVisibility()==View.GONE){
                EventBus.getDefault().post(new EventBusType(EventStatus.BRAND_BACK));
                return  false;
            }
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showLong("再按一次退出程序!");
                exitTime = System.currentTimeMillis();
            } else {
                //关闭小强
                CockroachUtil.clear();
                ActivitysLifecycle.getInstance().exit();
            }
            return false;
        }
        return super.dispatchKeyEvent(event);
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
