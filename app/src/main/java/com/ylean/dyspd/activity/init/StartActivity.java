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

                }else{
//                    showPrivate();
                    setClass(GuideActivity.class);
                }
                finish();
            }
        });
    }


    private Dialog dialog;
    private void showPrivate(){
        if(dialog!=null && dialog.isShowing()){
            return;
        }
        View view= LayoutInflater.from(this).inflate(R.layout.dialog_privacy,null);
        dialog= DialogUtil.getDialog(this,view);
        dialog.setCancelable(false);
        TextView textview=view.findViewById(R.id.tv_content);

        SpannableString fw = new SpannableString("服务协议");
        SpannableString ys = new SpannableString("隐私协议");
        ClickableSpan clickttt = new MyCheckTextView(fw, this);
        ClickableSpan clicksss = new MyCheckTextView2(ys, this);
        fw.setSpan(clickttt, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ys.setSpan(clicksss, 0, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText("哈哈");
        textview.append(fw);
        textview.append("和");
        textview.append(ys);
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
