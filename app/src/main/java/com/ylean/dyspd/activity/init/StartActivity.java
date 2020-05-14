package com.ylean.dyspd.activity.init;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.utils.PermissionUtil;
import com.zxdc.utils.library.base.BaseActivity;
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
        PermissionUtil.getPermission(this, true, false, new PermissionCallBack() {
            public void onclick() {
                if(SPUtil.getInstance(StartActivity.this).getBoolean(SPUtil.IS_FIRST_OPEN)){
                    setClass(TabActivity.class);
                }else{
                    setClass(GuideActivity.class);
                }
                finish();
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


    public interface PermissionCallBack{
        public void onclick();
    }
}
