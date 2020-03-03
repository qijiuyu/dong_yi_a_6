package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.CustomerWebView;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.NewsNum;
import com.zxdc.utils.library.bean.Telphone;
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 我的菜单类
 * Created by Administrator on 2019/11/7.
 */

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_gift)
    TextView tvGift;
    @BindView(R.id.tv_collection)
    TextView tvCollection;
    @BindView(R.id.tv_Focus)
    TextView tvFocus;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.rel_tel)
    RelativeLayout relTel;
    @BindView(R.id.rel_feedback)
    RelativeLayout relFeedback;
    @BindView(R.id.rel_setting)
    RelativeLayout relSetting;
    @BindView(R.id.img_news)
    ImageView imgNews;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_bindin)
    TextView tvBindin;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_call)
    TextView tvCall;
    @BindView(R.id.view_news)
    View viewNews;
    //用户信息对象
    private UserInfo userInfo;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        //获取客服电话
        getCall();
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.img_nickname, R.id.img_head, R.id.tv_gift, R.id.tv_collection, R.id.tv_Focus, R.id.tv_share, R.id.tv_tel, R.id.rel_tel, R.id.rel_feedback, R.id.rel_setting, R.id.img_news, R.id.tv_mobile,R.id.tv_bindin})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //个人信息
            case R.id.img_head:
            case R.id.img_nickname:
                intent.setClass(this, UserInfoActivity.class);
                intent.putExtra("userInfo", userInfo);
                startActivity(intent);
                break;
            //我的礼包
            case R.id.tv_gift:
                setClass(GiftActivity.class);
                break;
            //我的收藏
            case R.id.tv_collection:
                setClass(CollectionActivity.class);
                break;
            //我的关注
            case R.id.tv_Focus:
                setClass(MyFocusActivity.class);
                break;
            //我的分享
            case R.id.tv_share:
                setClass(ShareRecordActivity.class);
                break;
            //专属客服
            case R.id.tv_tel:
                intent.setClass(this, CustomerWebView.class);
                startActivity(intent);
                break;
            //客服电话
            case R.id.rel_tel:
                 String mobile= SPUtil.getInstance(this).getString(SPUtil.TEL);
                 if(!TextUtils.isEmpty(mobile)){
                    intent.setAction(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + mobile));
                    startActivity(intent);
                 }else{
                    //获取客服电话
                    getCall();
                }
                break;
            //意见反馈
            case R.id.rel_feedback:
                setClass(FeedBackActivity.class);
                break;
            //设置
            case R.id.rel_setting:
                setClass(SettingActivity.class);
                break;
            //消息
            case R.id.img_news:
                setClass(NewsActivity.class);
                break;
            //绑定手机号
            case R.id.tv_bindin:
                setClass(BindingMobileActivity.class);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //获取用户信息
                case HandlerConstant.GET_USERINFO_SUCCESS:
                    userInfo = (UserInfo) msg.obj;
                    if (userInfo == null) {
                        break;
                    }
                    if (userInfo.isSussess()) {
                        //展示用户信息
                        showUserInfo();
                    } else {
                        ToastUtil.showLong(userInfo.getDesc());
                    }
                    break;
                //获取客服电话
                case HandlerConstant.GET_CALL_SUCCESS:
                     Telphone telphone= (Telphone) msg.obj;
                     if(telphone==null){
                         break;
                     }
                     if(telphone.isSussess()){
                         SPUtil.getInstance(UserActivity.this).addString(SPUtil.TEL,telphone.getData());
                         tvCall.setText(telphone.getData());
                     }
                    break;
                //获取消息数量
                case HandlerConstant.GET_NEWS_NUM_SUCCESS:
                    final NewsNum newsNum= (NewsNum) msg.obj;
                    if(newsNum==null){
                        break;
                    }
                    if(newsNum.isSussess() && newsNum.getData()!=null){
                        if(newsNum.getData().getGgcount()>0 || newsNum.getData().getDtcount()>0 || newsNum.getData().getHdcount()>0){
                            viewNews.setVisibility(View.VISIBLE);
                        }else{
                            viewNews.setVisibility(View.GONE);
                        }
                    }else{
                        viewNews.setVisibility(View.GONE);
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
     * 展示用户信息
     */
    private void showUserInfo() {
        final UserInfo.UserBean userBean = userInfo.getData();
        if (userBean == null) {
            return;
        }
        //用户头像
        Glide.with(this).load(userBean.getImgurl()).centerCrop().error(R.mipmap.default_head).into(imgHead);
        //昵称
        tvName.setText(userBean.getNickname());
        //手机号码
        final String mobile = userBean.getMobile();
        if (TextUtils.isEmpty(mobile)) {
            tvBindin.setVisibility(View.VISIBLE);
        } else {
            tvBindin.setVisibility(View.GONE);
            tvMobile.setVisibility(View.VISIBLE);
            tvMobile.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
        }
    }


    /**
     * 获取用户信息
     */
    private void getUserInfo() {
        HttpMethod.getUserInfo(handler);
    }


    /**
     * 获取客服电话
     */
    private void getCall() {
        HttpMethod.getCall(handler);
    }


    /**
     * 获取消息数量
     */
    public void getNewsNum(){
        HttpMethod.getNewsNum(handler);
    }


    @Override
    public void onResume() {
        super.onResume();
        //获取用户信息
        getUserInfo();
        //获取消息数量
        getNewsNum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
