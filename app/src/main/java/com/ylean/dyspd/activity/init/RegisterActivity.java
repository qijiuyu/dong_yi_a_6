package com.ylean.dyspd.activity.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.persenter.init.LoginPersenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.util.Constant;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2020/1/8.
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.img_agreement)
    ImageView imgAgreement;
    private LoginPersenter loginPersenter;
    //计数器
    private Timer mTimer;
    private int time = 0;
    /**
     * 是否选择协议
     */
    private boolean isSelect=false;
    private IWXAPI api;
    private Handler handler=new Handler();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.transparencyBar(this);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
        //判断验证码秒数是否超过一分钟
        checkTime();
    }


    /**
     * 初始化
     */
    private void initView(){
        //注册eventBus
        EventBus.getDefault().register(this);
        loginPersenter=new LoginPersenter(this);
        //注册微信d
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID, true);
        api.registerApp(Constant.WX_APPID);
    }


    @OnClick({R.id.img_close, R.id.tv_login, R.id.tv_send, R.id.tv_agreement, R.id.tv_register, R.id.img_wx,R.id.img_agreement})
    public void onViewClicked(View view) {
        String mobile=etMobile.getText().toString().trim();
        String code=etCode.getText().toString().trim();
        String pwd=etPwd.getText().toString().trim();
        switch (view.getId()) {
            case R.id.img_close:
            case R.id.tv_login:
                finish();
                break;
            //发送验证码
            case R.id.tv_send:
                if(time>0){
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入手机号码！");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入正确的手机号码！");
                    return;
                }
                //获取短信验证码
                loginPersenter.getSmsCode(mobile);
                break;
            //协议
            case R.id.img_agreement:
                if(isSelect){
                    isSelect=false;
                    imgAgreement.setImageResource(R.mipmap.check_no);
                }else{
                    isSelect=true;
                    imgAgreement.setImageResource(R.mipmap.check_yes);
                }
                break;
            //进入协议详情
            case R.id.tv_agreement:
                Intent intent=new Intent(this, WebViewActivity.class);
                intent.putExtra("type",9);
                startActivity(intent);
                break;
            //注册
            case R.id.tv_register:
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入手机号码！");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入正确的手机号码！");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showLong("请输入验证码");
                    return;
                }
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入密码");
                    return;
                }
                if(pwd.length()<6 || pwd.contains("_")){
                    ToastUtil.showLong("密码由6-12位数字或字母组成，不能有下划线");
                    return;
                }
                if(!isSelect){
                    ToastUtil.showLong("请阅读并同意《东易日盛用户协议》");
                    return;
                }
                loginPersenter.register(mobile,pwd,code);
                break;
            //微信登录
            case R.id.img_wx:
                if (!api.isWXAppInstalled()) {
                    ToastUtil.showLong("请先安装微信客户端!");
                }else{
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
            //短信验证码
            case EventStatus.SHOW_SMS_CODE:
                startTime();
                break;
            //注册成功
            case EventStatus.REGISTER_SUCCESS:
                finish();
                break;
            //微信登录
            case EventStatus.WX_LOGIN:
                String msg= (String) eventBusType.getObject();
                if(TextUtils.isEmpty(msg)){
                    return;
                }
                String[] str=msg.split(",");
                loginPersenter.wxLogin(str[0],str[1],str[2],2);
                break;
            default:
                break;
        }
    }


    /**
     * 动态改变验证码秒数
     */
    private void startTime() {
        time=60;
        //保存计时时间
        SPUtil.getInstance(activity).addString("retister_time", String.valueOf((System.currentTimeMillis() + 60000)));
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (time <= 0) {
                    handler.post(new Runnable() {
                        public void run() {
                            mTimer.cancel();
                            tvSend.setText("获取验证码");
                            SPUtil.getInstance(activity).removeMessage("retister_time");
                        }
                    });
                } else {
                    --time;
                    handler.post(new Runnable() {
                        public void run() {
                            tvSend.setText(time + "秒");
                        }
                    });
                }
            }
        }, 0, 1000);
    }


    /**
     * 判断验证码秒数是否超过一分钟
     */
    private void checkTime() {
        String stoptime = SPUtil.getInstance(activity).getString("retister_time");
        if (!TextUtils.isEmpty(stoptime)) {
            int t = (int) ((Double.parseDouble(stoptime) - System.currentTimeMillis()) / 1000);
            if (t > 0) {
                time = t;
                startTime();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null){
            mTimer.cancel();
            mTimer.purge();
            mTimer=null;
        }
        EventBus.getDefault().unregister(this);
        removeHandler(handler);
    }
}
