package com.ylean.dyspd.activity.brand;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.LogUtils;

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
        public void onPageFinished(WebView view, String url) {
            if(url.startsWith(HttpConstant.HTML)){
                if(url.endsWith("brand")){
                    linBack.setVisibility(View.GONE);
                }else{
                    linBack.setVisibility(View.VISIBLE);
                }
            }
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
    }
}
