package com.ylean.dyspd.activity.web.decorate;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.bespoke.BespokeBuildingActivity;
import com.ylean.dyspd.activity.bespoke.BespokeConstructionActivity;
import com.ylean.dyspd.activity.bespoke.BespokeDecorateActivity;
import com.ylean.dyspd.activity.bespoke.BespokeDesignerActivity;
import com.ylean.dyspd.activity.bespoke.BespokeNearActivity;
import com.ylean.dyspd.activity.init.LoginActivity;
import com.ylean.dyspd.application.MyApplication;
import com.ylean.dyspd.persenter.webview.DecorateWebPersenter;
import com.ylean.dyspd.utils.PointUtil;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import org.apache.http.client.utils.URLEncodedUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DecorateWebView extends BaseWebView {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_coll)
    ImageView imgColl;
    @BindView(R.id.img_focus)
    ImageView imgFocus;
    @BindView(R.id.img_share)
    ImageView imgShare;
    //加载详情用的id
    private int id;
    //h5页面类型
    public int type;
    //加载的url，及标题
    public String url, title;
    public SHARE_MEDIA share_media;
    private DecorateWebPersenter decorateWebPersenter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_decorate);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        decorateWebPersenter = new DecorateWebPersenter(this);
        id = getIntent().getIntExtra("id", 0);
        type = getIntent().getIntExtra("type", 0);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        //初始化webview
        initWebView(webView, progressBar);
        webView.setWebViewClient(new webViewClient());
        String token = SPUtil.getInstance(this).getString(SPUtil.TOKEN);
        switch (type) {
            //图库详情
            case 1:
                tvTitle.setText("案例图库详情");
                webView.loadUrl(url = HttpConstant.HTML + "imgsparticulars?id=" + id + "&token=" + token);
                break;
            //VR详情
            case 2:
                tvTitle.setText("VR样板房详情");
                webView.loadUrl(url);
                break;
            //装修攻略详情
            case 3:
                tvTitle.setText("装修攻略详情");
                if (TextUtils.isEmpty(url)) {
                    webView.loadUrl(url = HttpConstant.HTML + "decorationparticulars?id=" + id + "&token=" + token);
                } else {
                    imgColl.setVisibility(View.GONE);
                    webView.loadUrl(url);
                }
                break;
            //体验店详情
            case 4:
                tvTitle.setText("体验店详情");
                webView.loadUrl(url = HttpConstant.HTML + "experienceshopparticulars?id=" + id + "&token=" + token);
                break;
            //在施工地详情
            case 5:
                tvTitle.setText("在施工地详情");
                webView.loadUrl(url = HttpConstant.HTML + "constructionparticulars?id=" + id + "&token=" + token);
                break;
            //楼盘详情
            case 6:
                tvTitle.setText("热装楼盘详情");
                webView.loadUrl(url = HttpConstant.HTML + "estateparticulars?id=" + id + "&token=" + token);
                break;
            //软装范本详情
            case 7:
                tvTitle.setText("软装案例详情");
                webView.loadUrl(url = HttpConstant.HTML + "modelparticulars?id=" + id + "&token=" + token);
                break;
            //设计师详情
            case 8:
                imgFocus.setVisibility(View.VISIBLE);
                tvTitle.setText("设计师详情");
                webView.loadUrl(url = HttpConstant.HTML + "designer?id=" + id + "&token=" + token);
                break;
            //首席设计师
            case 9:
                imgColl.setVisibility(View.GONE);
                tvTitle.setText("首席设计师");
                webView.loadUrl(url = HttpConstant.HTML + "chiefDesigner?id=" + id + "&token=" + token);
                break;
            //首页banner点击进入
            case 10:
                imgColl.setVisibility(View.GONE);
                if (TextUtils.isEmpty(url)) {
                    webView.loadUrl(url = HttpConstant.HTML + "newsparticulars?token=" + token + "&id=" + id);
                } else {
                    webView.loadUrl(url);
                }
                tvTitle.setText("新闻详情");
                break;
            //首页活动
            case 11:
                imgColl.setVisibility(View.GONE);
                if (TextUtils.isEmpty(url)) {
                    webView.loadUrl(url = HttpConstant.HTML + "activityparticulars?token=" + token + "&id=" + id);
                } else {
                    webView.loadUrl(url);
                }
                tvTitle.setText("活动详情");
                break;
            //案例详情
            case 12:
                tvTitle.setText("案例详情");
                webView.loadUrl(url = HttpConstant.HTML + "modelparticulars?id=" + id + "&token=" + token);
                break;
            //消息详情
            case 13:
                imgColl.setVisibility(View.GONE);
                imgShare.setVisibility(View.GONE);
                tvTitle.setText("消息详情");
                webView.loadUrl(url = HttpConstant.HTML + "msgparticulars?id=" + id);
                break;
            default:
                break;
        }

    }


    @OnClick({R.id.lin_back, R.id.img_focus, R.id.img_coll, R.id.img_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
            //取消或者关注
            case R.id.img_focus:
                if (!MyApplication.isLogin()) {
                    setClass(LoginActivity.class);
                    return;
                }
                if (view.getTag().toString().equals("0")) {
                    decorateWebPersenter.fouceDesigner(id);
                } else {
                    decorateWebPersenter.cancleFouce(id);
                }
                break;
            //收藏与取消收藏
            case R.id.img_coll:
                if (!MyApplication.isLogin()) {
                    setClass(LoginActivity.class);
                    return;
                }
                if (view.getTag().toString().equals("0")) {
                    decorateWebPersenter.collection(id, type);
                } else {
                    decorateWebPersenter.isColl(id, type, 2);
                }
                break;
            //分享
            case R.id.img_share:
                shareDialog();
                break;
            default:
                break;
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //关注成功
            case EventStatus.FOUCE_DESIGNER_SUCCESS:
                imgFocus.setTag("1");
                imgFocus.setImageResource(R.mipmap.focus_yes_icon);
                break;
            //取消关注成功
            case EventStatus.CANCLE_FOUCE_SUCCESS:
                imgFocus.setTag("0");
                imgFocus.setImageResource(R.mipmap.focus_icon);
                break;
            //收藏成功
            case EventStatus.COLLECTION_SUCCESS:
                imgColl.setTag("1");
                imgColl.setImageResource(R.mipmap.coll_yes);
                break;
            //取消收藏
            case EventStatus.CANCLE_COLLECTION:
                imgColl.setTag("0");
                imgColl.setImageResource(R.mipmap.coll_icon);
                break;
            //分享成功
            case EventStatus.SHARE_SUCCESS:
                decorateWebPersenter.isColl(id, type, 3);
                break;
            //H5页面加载完成
            case EventStatus.HTML_LOADING_SUCCESS:
                decorateWebPersenter.isOpenGuide(type);
                break;
            default:
                break;
        }
    }


    /**
     * 弹出分享弹框
     */
    private void shareDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
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
        UMWeb web = new UMWeb(url);
        web.setTitle(tvTitle.getText().toString().trim());
        web.setDescription(title);
        new ShareAction(DecorateWebView.this).setPlatform(share_media)
                .setCallback(umShareListener)
                .withMedia(web)
                .share();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                ToastUtil.showLong(getString(R.string.share_success));
            } else {
                ToastUtil.showLong(getString(R.string.share_success));
            }
            //分享成功后添加信用分
            decorateWebPersenter.isColl(id, type, 3);
        }

        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t.getMessage().indexOf("2008") != -1) {
                if (platform.name().equals("WEIXIN") || platform.name().equals("WEIXIN_CIRCLE")) {
                    ToastUtil.showLong(getString(R.string.share_failed_install_wechat));
                } else if (platform.name().equals("QQ") || platform.name().equals("QZONE")) {
                    ToastUtil.showLong(getString(R.string.share_failed_install_qq));
                }
            }
            ToastUtil.showLong(getString(R.string.share_failed));
        }

        public void onCancel(SHARE_MEDIA platform) {
            ToastUtil.showLong(getString(R.string.share_canceled));
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @JavascriptInterface
    public void bespoke(int status,int id) {
        Intent intent = new Intent();
        intent.putExtra("id", id);
        switch (status) {
            //进入预约设计师界面
            case 1:
                intent.setClass(this, BespokeDesignerActivity.class);
                break;
            //进入预约装修界面
            case 2:
                intent.setClass(this, BespokeDecorateActivity.class);
                break;
            //进入预约体验店界面
            case 3:
                intent.setClass(this, BespokeNearActivity.class);
                break;
            //进入预约参观楼盘界面
            case 4:
                intent.setClass(this, BespokeBuildingActivity.class);
                break;
            //进入预约参观工地界面
            case 5:
                intent.setClass(this, BespokeConstructionActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }


    /**
     * 监听跳转的url
     */
    private class webViewClient extends WebViewClient {
        public void onPageFinished(WebView view, String url) {
            JSONObject jsonObject=null;
            try {
                if(type==7 || type==9 || type==10 || type==11 || type==13){
                    return;
                }
                if(url.startsWith(HttpConstant.HTML)){
                    String strId=DecorateWebPersenter.getParam(url,"id");
                    String details=DecorateWebPersenter.getParam(url,"dataStr");
                    if(TextUtils.isEmpty(strId)){
                        return;
                    }
                    //重置url
                    DecorateWebView.this.url=url;
                    //获取最新id
                    id=Integer.parseInt(strId);
                    if(!TextUtils.isEmpty(details)){
                        jsonObject=new JSONObject(URLDecoder.decode(URLDecoder.decode(details, "utf-8"), "utf-8"));
                    }else{
                        title=getIntent().getStringExtra("title");
                    }
                    //清空标题栏图标状态
                    imgFocus.setImageResource(R.mipmap.focus_icon);
                    imgColl.setImageResource(R.mipmap.coll_icon);
                    imgFocus.setVisibility(View.GONE);
                    imgShare.setVisibility(View.VISIBLE);
                    imgColl.setVisibility(View.VISIBLE);
                    imgFocus.setTag("0");
                    imgColl.setTag("0");

                    if(url.startsWith(HttpConstant.HTML+"imgsparticulars?")){
                        type=1;
                        tvTitle.setText("案例图库详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"decorationparticulars?")){
                        type=3;
                        tvTitle.setText("装修攻略详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("title");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"experienceshopparticulars?")){
                        type=4;
                        tvTitle.setText("体验店详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"constructionparticulars?")){
                        type=5;
                        tvTitle.setText("在施工地详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"estateparticulars?")){
                        type=6;
                        tvTitle.setText("热装楼盘详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"designer?")){
                        type=8;
                        tvTitle.setText("设计师详情");
                        imgFocus.setVisibility(View.VISIBLE);
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"modelparticulars?")){
                        type=12;
                        tvTitle.setText("案例详情");
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                    }else if(url.startsWith(HttpConstant.HTML+"samplereels?")){
                        tvTitle.setText("作品集详情");
                        imgColl.setVisibility(View.GONE);
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                        return;
                    }else if(url.startsWith(HttpConstant.HTML+"houseanalysis?")){
                        tvTitle.setText("户型解析详情");
                        imgColl.setVisibility(View.GONE);
                        if(jsonObject!=null){
                            title=jsonObject.getString("name");
                        }
                        return;
                    }

                    /**
                     * 判断是否收藏过
                     */
                    decorateWebPersenter.isColl(id, type, 1);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            super.onPageFinished(view, url);
        }
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url == null) return false;
            if (url.startsWith("http:") || url.startsWith("https:") ){
                view.loadUrl(url);
                return false;
            }else{
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }catch (Exception e){
//                    ToastUtils.showShort("暂无应用打开此链接");
                }
                return true;
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }
        }
        return false;
    }


    @Override
    public void onResume() {
        super.onResume();
        /**
         * 判断是否收藏过
         */
        decorateWebPersenter.isColl(id, type, 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        webView.clearCache(true);
    }
}
