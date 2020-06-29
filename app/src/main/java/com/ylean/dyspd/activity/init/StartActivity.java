package com.ylean.dyspd.activity.init;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.utils.PermissionCallBack;
import com.ylean.dyspd.utils.PermissionUtil;
import com.ylean.dyspd.view.MyCheckTextView;
import com.ylean.dyspd.view.MyCheckTextView2;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.ActivitysLifecycle;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;

/**
 * Created by Administrator on 2020/2/8.
 */

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_wellcome);
        initView();
        PermissionUtil.getPermission(this, new PermissionCallBack() {
            @Override
            public void onSuccess() {
                if(SPUtil.getInstance(StartActivity.this).getBoolean(SPUtil.IS_FIRST_OPEN)){
                    setClass(TabActivity.class);
                    finish();

                }else{
                    showPrivate();
//                    setClass(GuideActivity.class);
//                    finish();
                }
            }

            @Override
            public void onFail() {

            }
        });
    }


    /**
     * 初始化
     */
    private void initView() {
        LinearLayout linearLayout=findViewById(R.id.lin_wellcome);
        Animation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
        myAnimation_Alpha.setDuration(2500);
        myAnimation_Alpha.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }
            public void onAnimationRepeat(Animation animation) {
            }
            public void onAnimationEnd(Animation animation) {

            }
        });
        linearLayout.setAnimation(myAnimation_Alpha);
        myAnimation_Alpha.start();
    }


    private Dialog dialog;
    private void showPrivate(){
        if(dialog!=null && dialog.isShowing()){
            return;
        }
        View view= LayoutInflater.from(activity).inflate(R.layout.dialog_privacy,null);
        dialog= DialogUtil.getDialog(activity,view);
        dialog.setCancelable(false);
        WebView webView=view.findViewById(R.id.webView);
        new BaseWebView().initWebView(webView,null);

       webView.loadUrl(HttpConstant.HTML + "agreement");

        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog=null;
                finish();
            }
        });
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog=null;
                setClass(GuideActivity.class);
                finish();
            }
        });
    }
}
