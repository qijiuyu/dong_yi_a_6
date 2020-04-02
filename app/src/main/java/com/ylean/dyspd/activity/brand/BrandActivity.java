package com.ylean.dyspd.activity.brand;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 品牌菜单类
 * Created by Administrator on 2019/11/7.
 */

public class BrandActivity extends BaseWebView {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lin_back)
    LinearLayout linBack;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        tvTitle.setText("品牌");
        linBack.setVisibility(View.GONE);
        initWebView(webView, progressBar);
        webView.setWebViewClient(new webViewClient());
        webView.loadUrl(HttpConstant.HTML + "brand");
    }


    /**
     * 监听跳转的url
     */
    private class webViewClient extends WebViewClient {

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString()+"++++++++++");
            } else {
                view.loadUrl(request.toString()+"++++++++++++++");
            }
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            if(url.startsWith(HttpConstant.HTML)){
                if(url.endsWith("brand")){
                    linBack.setVisibility(View.GONE);
                    EventBus.getDefault().post(new EventBusType(EventStatus.IS_SHOW_TAB,true));
                }else{
                    linBack.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new EventBusType(EventStatus.IS_SHOW_TAB,false));
                }
            }

//            if(url.startsWith(HttpConstant.HTML+"newsparticulars")){
//                Intent intent=new Intent(BrandActivity.this, WebViewActivity.class);
//                intent.putExtra("type",12);
//                intent.putExtra("url",url);
//                startActivity(intent);
//                return;
//            }
            super.onPageFinished(view, url);
        }
    }


    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            case EventStatus.BRAND_BACK:
                 if(webView.canGoBack()){
                     webView.goBack();
                 }
                  break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.reload(); //刷新
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
        EventBus.getDefault().unregister(this);
    }
}
