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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.utils.PermissionCallBack;
import com.ylean.dyspd.utils.PermissionUtil;
import com.ylean.dyspd.view.MyCheckTextView;
import com.ylean.dyspd.view.MyCheckTextView2;
import com.zxdc.utils.library.base.BaseActivity;
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



    @Override
    public void onResume() {
        super.onResume();
        PermissionUtil.getPermission(this, new PermissionCallBack() {
            public void onclick() {
                if(SPUtil.getInstance(StartActivity.this).getBoolean(SPUtil.IS_FIRST_OPEN)){
                    setClass(TabActivity.class);
                    finish();

                }else{
                    showPrivate();
//                    setClass(GuideActivity.class);
//                    finish();
                }
            }
        });
    }


    private Dialog dialog;
    private void showPrivate(){
        if(dialog!=null && dialog.isShowing()){
            return;
        }
        View view= LayoutInflater.from(activity).inflate(R.layout.dialog_privacy,null);
        dialog= DialogUtil.getDialog(activity,view);
        dialog.setCancelable(false);
        TextView textview=view.findViewById(R.id.tv_content);

        SpannableString fw = new SpannableString("服务协议");
        SpannableString ys = new SpannableString("隐私政策");
        ClickableSpan clickttt = new MyCheckTextView(fw, activity);
        ClickableSpan clicksss = new MyCheckTextView2(ys, activity);
        fw.setSpan(clickttt, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ys.setSpan(clicksss, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText("请您务必审慎阅读、充分理解“服务协议”和“隐私政策”各条款，包括但不限于：为了向您提供即时通讯、内容分享等服务，我们需要手机您的设备信息、操作日志等个人信息。您可以在“设置”中查看、变更、删除个人信息并管理你的授权。\n" +
                "您可阅读《");
        textview.append(fw);
        textview.append("》和《");
        textview.append(ys);
        textview.append("》了解详细信息。如您同意，请点击“同意”开始接受我们的服务。我们会按照协议约定处理您的个人信息，并全力保护您的个人信息安全。");
        textview.setMovementMethod(LinkMovementMethod.getInstance());
        textview.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明

        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog=null;
                setClass(GuideActivity.class);
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
