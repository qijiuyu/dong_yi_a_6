package com.ylean.dyspd.activity.found;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.CaseListActivity;
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.application.MyApplication;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.persenter.found.FoundPersenter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Found;
import com.zxdc.utils.library.bean.FoundBanner;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 发现菜单类
 * Created by Administrator on 2019/11/7.
 */

public class FoundActivity extends BaseActivity{

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.img_1)
    ImageView img1;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.img_2)
    ImageView img2;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.img_3)
    ImageView img3;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.img_4)
    ImageView img4;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.img_5)
    ImageView img5;
    @BindView(R.id.tv_5)
    TextView tv5;
    //页面顶部图片对象
    private  FoundBanner.BannerBean bannerBean;
    //页面底部数据
    private List<Found.FoundBean> list;
    private FoundPersenter foundPersenter;
    public SHARE_MEDIA share_media;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        foundPersenter = new FoundPersenter(this);
    }

    @OnClick({R.id.tv_to_receive, R.id.lin_sxlf, R.id.rel_wyyf, R.id.rel_zcjj, R.id.rel_zssj, R.id.rel_wdfg, R.id.rel_jsq, R.id.rel_click1, R.id.rel_click2, R.id.rel_click3, R.id.img_1, R.id.img_2, R.id.img_3, R.id.img_4, R.id.img_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //领取
            case R.id.tv_to_receive:
                if (!MyApplication.isLogin()) {
                    setClass(LoginActivity.class);
                    return;
                }
                if(bannerBean==null){
                    return;
                }
                //领取优惠券
                takeCoupon();
                break;
            //私享量房
            case R.id.lin_sxlf:
                gotoWebView(3);
                break;
            //无忧验房
            case R.id.rel_wyyf:
                gotoWebView(4);
                break;
            //专车接驾
            case R.id.rel_zcjj:
                gotoWebView(5);
                break;
            //专属设计
            case R.id.rel_zssj:
                gotoWebView(6);
                break;
            //我的风格
            case R.id.rel_wdfg:
                gotoWebView(7);
                break;
            //计算器
            case R.id.rel_jsq:
                 gotoWebView(8);
                 break;
            case R.id.rel_click1:
                 setClass(DesignerListActivity.class);
                break;
            case R.id.rel_click2:
                setClass(CaseListActivity.class);
                break;
            //我要代言
            case R.id.rel_click3:
                shareDialog();
                break;
            case R.id.img_1:
            case R.id.img_2:
            case R.id.img_3:
            case R.id.img_4:
            case R.id.img_5:
                setClass(PinZhiActivity.class);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //获取页面banner数据
                case HandlerConstant.GET_FOUND_BANNER_SUCCESS:
                      FoundBanner foundBanner= (FoundBanner) msg.obj;
                      if(foundBanner==null){
                          break;
                      }
                      if(foundBanner.isSussess() && foundBanner.getData()!=null){
                          bannerBean=foundBanner.getData();
                          Glide.with(FoundActivity.this).load(bannerBean.getImg()).centerCrop().into(imgBanner);
                      }
                      break;
                //获取页面底部数据
                case HandlerConstant.GET_FOUND_BOTTOM_SUCCESS:
                    Found found = (Found) msg.obj;
                    if (found == null) {
                        break;
                    }
                    if (found.isSussess() && found.getData()!=null) {
                        list=found.getData();
                        //展示页面底部品质数据
                        showBottomData();
                    }
                    break;
                //领取优惠券回执
                case HandlerConstant.TAKE_COUPON_SUCCESS:
                      BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      ToastUtil.showLong(baseBean.getDesc());
                      break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 展示页面底部品质数据
     */
    private void showBottomData(){
        if(list.size()!=5){
            return;
        }
        tv1.setText(list.get(0).getTitle());
        Glide.with(this).load(list.get(0).getImg()).centerCrop().into(img1);

        tv2.setText(list.get(1).getTitle());
        Glide.with(this).load(list.get(1).getImg()).centerCrop().into(img2);

        tv3.setText(list.get(2).getTitle());
        Glide.with(this).load(list.get(2).getImg()).centerCrop().into(img3);

        tv4.setText(list.get(3).getTitle());
        Glide.with(this).load(list.get(3).getImg()).centerCrop().into(img4);

        tv5.setText(list.get(4).getTitle());
        Glide.with(this).load(list.get(4).getImg()).centerCrop().into(img5);
    }



    /**
     * 弹出分享弹框
     */
    public void shareDialog() {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_share, null);
        final PopupWindow mPopuwindow = DialogUtil.showPopWindow(view);
        mPopuwindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        view.findViewById(R.id.tv_wx).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share_media = SHARE_MEDIA.WEIXIN;
                startShare();
                mPopuwindow.dismiss();
            }
        });
        view.findViewById(R.id.tv_pyq).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share_media = SHARE_MEDIA.WEIXIN_CIRCLE;
                startShare();
                mPopuwindow.dismiss();
            }
        });
        view.findViewById(R.id.tv_qq).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share_media = SHARE_MEDIA.QQ;
                startShare();
                mPopuwindow.dismiss();
            }
        });
        view.findViewById(R.id.tv_kj).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share_media = SHARE_MEDIA.QZONE;
                startShare();
                mPopuwindow.dismiss();
            }
        });
        view.findViewById(R.id.tv_wb).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share_media = SHARE_MEDIA.SINA;
                startShare();
                mPopuwindow.dismiss();
            }
        });
        view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPopuwindow.dismiss();
            }
        });
    }


    /**
     * 分享
     */
    private void startShare() {
        UMWeb web = new UMWeb(HttpConstant.HTML+"share");
        web.setTitle("我要代言");
        new ShareAction(activity).setPlatform(share_media)
                .setCallback(umShareListener)
                .withMedia(web)
                .share();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.showLong(activity.getString(R.string.share_success));
            } else {
                ToastUtil.showLong(activity.getString(R.string.share_success));
            }
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().indexOf("2008") != -1) {
                if (platform.name().equals("WEIXIN") || platform.name().equals("WEIXIN_CIRCLE")) {
                    ToastUtil.showLong(activity.getString(R.string.share_failed_install_wechat));
                } else if (platform.name().equals("QQ") || platform.name().equals("QZONE")) {
                    ToastUtil.showLong(activity.getString(R.string.share_failed_install_qq));
                }
            }
            ToastUtil.showLong(activity.getString(R.string.share_failed));
        }

        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showLong(activity.getString(R.string.share_canceled));
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 加载h5页面
     *
     * @param type
     */
    private void gotoWebView(int type) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }


    /**
     * 获取发现页面banner数据
     */
    private void getFoundBanner(){
        HttpMethod.getFoundBanner(handler);
    }

    /**
     * 获取发现页面底部数据
     */
    private void getFoundBottom() {
        HttpMethod.getFoundBottom(handler);
    }


    /**
     * 领取优惠券
     */
    private void takeCoupon(){
        DialogUtil.showProgress(this,"领取中...");
        HttpMethod.takeCoupon(String.valueOf(bannerBean.getId()),handler);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(bannerBean==null){
            //获取发现页面banner数据
            getFoundBanner();
        }
        if(list==null){
            //获取发现页面底部数据
            getFoundBottom();
        }

    }
}
