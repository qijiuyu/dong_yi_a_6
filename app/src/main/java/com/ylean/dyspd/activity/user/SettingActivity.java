package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.ylean.dyspd.activity.web.PrivateWebView;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.utils.DataCleanManager;
import com.ylean.dyspd.utils.UpdateVersionUtils;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.ClickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 设置界面
 * Created by Administrator on 2019/11/8.
 */

public class SettingActivity extends BaseActivity {

    @BindView(R.id.img_push)
    ImageView imgPush;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    /**
     * true：设置推送
     * false：不推送
     */
    private boolean isPush=false;
    private Handler handler=new Handler();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        //获取本地缓存大小
        getCache();
    }

    /**
     * 初始化
     */
    private void initView(){
        final int versionCode=SPUtil.getInstance(this).getInteger(SPUtil.VERSION_CODE);
        if(versionCode== Util.getVersionCode(this)){
            tvVersion.setText("V"+Util.getVersionName(this)+"已经是最新版本");
        }else{
            tvVersion.setText("有最新版本");
        }
        final int isPush=SPUtil.getInstance(this).getInteger(SPUtil.JPUSH);
        if(isPush==0){
            imgPush.setImageDrawable(getResources().getDrawable(R.mipmap.open_news));
        }else{
            imgPush.setImageDrawable(getResources().getDrawable(R.mipmap.close));
        }
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_push, R.id.rel_update_pwd, R.id.rel_score, R.id.rel_help, R.id.rel_cache,R.id.tv_version, R.id.rel_agrement1,R.id.rel_agrement2,R.id.tv_out})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            //返回
            case R.id.lin_back:
                 finish();
                break;
            //设置推送
            case R.id.img_push:
                if(isPush){
                    isPush=false;
                    imgPush.setImageDrawable(getResources().getDrawable(R.mipmap.close_news));
                    SPUtil.getInstance(this).addInt(SPUtil.JPUSH,1);
                    //停止推送
                    JPushInterface.stopPush(this);
                }else{
                    isPush=true;
                    imgPush.setImageDrawable(getResources().getDrawable(R.mipmap.open_news));
                    SPUtil.getInstance(this).addInt(SPUtil.JPUSH,0);
                    //恢复推送
                    JPushInterface.resumePush(this);
                }
                break;
            //修改密码
            case R.id.rel_update_pwd:
                final String mobile=SPUtil.getInstance(this).getString(SPUtil.ACCOUNT);
                if(TextUtils.isEmpty(mobile)){
                    setClass(BindingMobileActivity.class);
                }else{
                    setClass(EditPwdActivity.class);
                }
                break;
            //评分
            case R.id.rel_score:
                Uri uri = Uri.parse("market://details?id="+getPackageName());
                Intent intent2 = new Intent(Intent.ACTION_VIEW,uri);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                break;
            //使用帮助
            case R.id.rel_help:
                setClass(HelpActivity.class);
                break;
            //清理缓存
            case R.id.rel_cache:
                clearCache();
                break;
            //更新版本
            case R.id.tv_version:
                  if(tvVersion.getText().toString().trim().equals("有最新版本")){
                      new UpdateVersionUtils().getVersion(this);
                  }
                  break;
            case R.id.rel_agrement1:
                 intent.setClass(this, PrivateWebView.class);
                 intent.putExtra("type",2);
                 startActivity(intent);
                 break;
            case R.id.rel_agrement2:
                intent.setClass(this, PrivateWebView.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            //退出登录
            case R.id.tv_out:
                JPushInterface.stopPush(this);      //停止推送
                SPUtil.getInstance(this).removeMessage(SPUtil.TOKEN);
                intent.setClass(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 获取本地缓存大小
     */
    private void getCache(){
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清理缓存
     */
    private void clearCache(){
        DialogUtil.showProgress(this,"缓存清理中...");
        try {
            DataCleanManager.clearAllCache(this);
            handler.postDelayed(new Runnable() {
                public void run() {
                    DialogUtil.closeProgress();
                    tvCache.setText("0.00K");
                }
            },2000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final String mobile=SPUtil.getInstance(this).getString(SPUtil.ACCOUNT);
        if(TextUtils.isEmpty(mobile)){
            tvEdit.setText("绑定手机号");
        }else{
            tvEdit.setText("修改密码");
        }
    }
}
