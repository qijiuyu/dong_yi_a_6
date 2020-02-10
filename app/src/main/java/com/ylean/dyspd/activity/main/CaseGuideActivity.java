package com.ylean.dyspd.activity.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/12/12.
 */

public class CaseGuideActivity extends BaseActivity {

    @BindView(R.id.img_anim3)
    ImageView imgAnim3;
    @BindView(R.id.rel3)
    RelativeLayout rel3;
    @BindView(R.id.img_anim1)
    ImageView imgAnim1;
    @BindView(R.id.rel1)
    RelativeLayout rel1;
    @BindView(R.id.img_anim2)
    ImageView imgAnim2;
    @BindView(R.id.rel2)
    RelativeLayout rel2;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.tv2)
    TextView tv2;
    private Animation animation;
    /**
     * 8：设计师
     * 12：案例
     */
    private int type;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_case_guide);
        ButterKnife.bind(this);

        type = getIntent().getIntExtra("type", 8);
        animation = AnimationUtils.loadAnimation(this, R.anim.case_guide_1);
        imgAnim1.setAnimation(animation);
    }

    @OnClick({R.id.tv_close1,R.id.img_close1, R.id.img_close2, R.id.tv_close2, R.id.img_close3, R.id.tv_close3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close1:
            case R.id.img_close2:
            case R.id.img_close3:
            case R.id.tv_close3:
                 finish();
                 break;
            case R.id.tv_close1:
                imgAnim1.clearAnimation();
                rel1.setVisibility(View.GONE);
                rel2.setVisibility(View.VISIBLE);
                if(type==8){
                    img2.setImageResource(R.mipmap.main_guide_two);
                    tv2.setText("左右滑动点击切换案例");
                }else{
                    img2.setImageResource(R.mipmap.main_guide_three);
                    tv2.setText("左右滑动点击切换空间区域");
                }
                animation = AnimationUtils.loadAnimation(this, R.anim.guide_trans);
                imgAnim2.setAnimation(animation);
                break;
            case R.id.tv_close2:
                imgAnim2.clearAnimation();
                rel2.setVisibility(View.GONE);
                rel3.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(this, R.anim.guide_scale);
                imgAnim3.setAnimation(animation);
                break;
            default:
                break;
        }
    }

}
