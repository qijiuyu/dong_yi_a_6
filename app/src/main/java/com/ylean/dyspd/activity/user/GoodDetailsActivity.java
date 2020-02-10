package com.ylean.dyspd.activity.user;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.GoodDetail;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/12/10.
 */
public class GoodDetailsActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_relus)
    TextView tvRelus;
    @BindView(R.id.tv_des)
    HtmlTextView tvDes;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    //商品id
    private int id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        ButterKnife.bind(this);

        id = getIntent().getIntExtra("id", 0);
        //获取商品详情
        getGoodDetails();

        //返回
        linBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoodDetailsActivity.this.finish();
            }
        });
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                case HandlerConstant.GET_GOOD_DETAILS_SUCCESS:
                    GoodDetail goodDetail = (GoodDetail) msg.obj;
                    if (goodDetail == null) {
                        break;
                    }
                    if (goodDetail.isSussess()) {
                        //展示详情内容
                        showDetails(goodDetail.getData());
                    } else {
                        ToastUtil.showLong(goodDetail.getDesc());
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 展示详情内容
     *
     * @param goodBean
     */
    private void showDetails(GoodDetail.GoodBean goodBean) {
        if (goodBean == null) {
            return;
        }
        //设置顶部banner轮播图
        setBanner(goodBean.getImgurls());
        tvTitle.setText(goodBean.getName());
        tvTitle2.setText(goodBean.getSubtitle());
        tvMoney.setText("¥" + Util.setDouble(goodBean.getPrice(), 2));
        tvMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvRelus.setText(goodBean.getRuleinfo());

        tvDes.setHtml(goodBean.getSpuinfo(), new HtmlHttpImageGetter(tvDes));
        scrollView.scrollTo(0,0);
    }


    /**
     * 设置顶部banner轮播图
     */
    public void setBanner(String imgs) {
        if (TextUtils.isEmpty(imgs)) {
            return;
        }
        String[] strImg = imgs.split(",");
        List<String> imgList = new ArrayList<>();
        for (int i = 0; i < strImg.length; i++) {
            imgList.add(strImg[i]);
        }
        //设置样式，里面有很多种样式可以自己都看看效果
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
        banner.setBannerAnimation(Transformer.Default);
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(new ABImageLoader());
        //设置图片集合
        banner.setImages(imgList);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    public class ABImageLoader extends ImageLoader {
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }


    /**
     * 获取商品详情
     */
    private void getGoodDetails() {
        DialogUtil.showProgress(this, "数据加载中...");
        HttpMethod.getGoodDetails(String.valueOf(id), handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
