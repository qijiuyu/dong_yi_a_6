package com.ylean.dyspd.activity.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivateWebView extends BaseWebView {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        int type=getIntent().getIntExtra("type",0);
        if(type==1){
            tvTitle.setText("服务协议");
        }else{
            tvTitle.setText("隐私政策");
        }

        LogUtils.e(HttpConstant.HTML + "agreement"+"+++++++++++++++++++++");
        initWebView(webView,null);
        webView.loadUrl(HttpConstant.HTML + "agreement");

        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    PrivateWebView.this.finish();
                }
            }
        });
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
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
    }
}
