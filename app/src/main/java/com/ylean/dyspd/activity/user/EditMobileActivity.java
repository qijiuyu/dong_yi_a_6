package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.ClickTextView;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更换手机号
 * Created by Administrator on 2019/11/8.
 */

public class EditMobileActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.tv_confirm)
    ClickTextView tvConfirm;
    //计数器
    private Timer mTimer;
    private int time = 0;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mobile);
        ButterKnife.bind(this);
        //判断验证码秒数是否超过一分钟
        checkTime();
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.tv_send_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        String pwd=etPwd.getText().toString().trim();
        String mobile=etMobile.getText().toString().trim();
        String code=etCode.getText().toString().trim();
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            //获取验证码
            case R.id.tv_send_code:
                if(time>0){
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入新的手机号！");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入正确的手机号！");
                    return;
                }
                //获取短信验证码
                getSmsCode(mobile);
                break;
            case R.id.tv_confirm:
                if(TextUtils.isEmpty(pwd)){
                    ToastUtil.showLong("请输入登录密码！");
                    return;
                }
                if(pwd.length()<6 || pwd.contains("_")){
                    ToastUtil.showLong("密码由6-12位数字或字母组成，不能有下划线");
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入新的手机号！");
                    return;
                }
                if(mobile.length()<11){
                    ToastUtil.showLong("请输入正确的手机号！");
                    return;
                }
                if(TextUtils.isEmpty(code)){
                    ToastUtil.showLong("请输入验证码！");
                    return;
                }
                //修改手机号
                editMobile(mobile,pwd,code);
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DialogUtil.closeProgress();
            BaseBean baseBean=null;
            switch (msg.what){
                //获取短信验证码
                case HandlerConstant.GET_SMS_CODE_SUCCESS:
                    baseBean= (BaseBean) msg.obj;
                    if(baseBean==null){
                        break;
                    }
                    if(baseBean.isSussess()){
                        startTime();
                    }
                    ToastUtil.showLong(baseBean.getDesc());
                    break;
                //更新手机号码
                case HandlerConstant.EDIT_MOBILE_SUCCESS:
                     baseBean= (BaseBean) msg.obj;
                     if(baseBean==null){
                         break;
                     }
                     if(baseBean.isSussess()){
                         SPUtil.getInstance(activity).addString(SPUtil.ACCOUNT,etMobile.getText().toString().trim());
                         Intent intent=new Intent();
                         intent.putExtra("mobile",etMobile.getText().toString().trim());
                         setResult(1,intent);
                         finish();
                     }
                     ToastUtil.showLong(baseBean.getDesc());
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };


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
                            tvSendCode.setText("获取验证码");
                            SPUtil.getInstance(activity).removeMessage("retister_time");
                        }
                    });
                } else {
                    --time;
                    handler.post(new Runnable() {
                        public void run() {
                            tvSendCode.setText(time + "秒");
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


    /**
     * 获取短信验证码
     * @param mobile
     */
    public void getSmsCode(String mobile){
        DialogUtil.showProgress(activity,"获取验证码中...");
        HttpMethod.getSmsCode(mobile,"2",handler);
    }


    /**
     * 修改手机号
     * @param mobile
     * @param password
     * @param smscode
     */
    private void editMobile(String mobile,String password,String smscode){
        DialogUtil.showProgress(activity,"号码更新中...");
        HttpMethod.editMobile(mobile,password,smscode,handler);
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
        }
        removeHandler(handler);
        super.onDestroy();
    }
}
