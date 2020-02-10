package com.ylean.dyspd.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页引导页
 * Created by Administrator on 2019/12/6.
 */
public class MainGuideActivity extends BaseActivity {

    @BindView(R.id.img_anim3)
    ImageView imgAnim3;
    @BindView(R.id.img_anim1)
    ImageView imgAnim1;
    @BindView(R.id.rel3)
    RelativeLayout rel3;
    @BindView(R.id.rel1)
    RelativeLayout rel1;
    private Animation animation;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_main_guide);
        ButterKnife.bind(this);

        animation = AnimationUtils.loadAnimation(this, R.anim.guide_trans);
        imgAnim1.setAnimation(animation);
    }

    @OnClick({R.id.tv_close1, R.id.img_close3, R.id.tv_close3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_close1:
                imgAnim1.clearAnimation();
                rel1.setVisibility(View.GONE);
                rel3.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(this, R.anim.guide_scale);
                imgAnim3.setAnimation(animation);
                break;
            case R.id.img_close3:
            case R.id.tv_close3:
                rel3.setVisibility(View.GONE);
                finish();
                break;
            default:
                break;
        }
    }
}
