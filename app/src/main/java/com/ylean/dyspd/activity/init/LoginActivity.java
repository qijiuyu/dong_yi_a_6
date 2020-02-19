package com.ylean.dyspd.activity.init;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.persenter.init.LoginPersenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.util.Constant;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 * Created by Administrator on 2019/11/13.
 */

public class LoginActivity extends BaseActivity {


    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    private LoginPersenter loginPersenter;
    private IWXAPI api;
    /**
     * 登录类型
     * 1：账号登录
     * 2：微信登录
     */
    public int loginType;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        //注册eventBus
        EventBus.getDefault().register(this);
        loginPersenter = new LoginPersenter(this);
        //注册微信d
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, true);
        api.registerApp(Constant.WX_APPID);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.img_close, R.id.tv_register, R.id.tv_forget, R.id.tv_login, R.id.img_wx})
    public void onViewClicked(View view) {
        String mobile = etMobile.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.img_close:
                setClass(TabActivity.class);
                finish();
                break;
            //注册
            case R.id.tv_register:
                setClass(RegisterActivity.class);
                break;
            //忘记密码
            case R.id.tv_forget:
                setClass(ForgetPwdActivity.class);
                break;
            //登录
            case R.id.tv_login:
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showLong("请输入手机号码！");
                    return;
                }
                if (mobile.length() < 11) {
                    ToastUtil.showLong("请输入正确的手机号码！");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showLong("请输入密码");
                    return;
                }
                if(pwd.length()<6 || pwd.contains("_")){
                    ToastUtil.showLong("密码由6-12位数字或字母组成，不能有下划线");
                    return;
                }
                loginType=1;
                loginPersenter.login(mobile, pwd,loginType);
                break;
            //微信登录
            case R.id.img_wx:
                loginType=2;
                if (!api.isWXAppInstalled()) {
                    ToastUtil.showLong("请先安装微信客户端!");
                } else {
                    final SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    api.sendReq(req);
                }
                break;
            default:
                break;
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //微信登录
            case EventStatus.WX_LOGIN:
                String msg = (String) eventBusType.getObject();
                if (TextUtils.isEmpty(msg)) {
                    return;
                }
                String[] str = msg.split(",");
                loginPersenter.wxLogin(str[0], str[1], str[2],loginType);
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
