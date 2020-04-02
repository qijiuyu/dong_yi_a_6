package com.ylean.dyspd.activity.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.utils.PointUtil;
import com.ylean.dyspd.view.SuspensionButtonView;
import com.zxdc.utils.library.base.BaseApplication;
import com.zxdc.utils.library.base.BaseWebView;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * webview加载h5
 * Created by Administrator on 2019/11/21.
 */
public class WebViewActivity extends BaseWebView {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.suspensionView)
    SuspensionButtonView suspensionView;
    //页面类型
    private int type;
    private int id;
    private String url;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getIntExtra("id", 0);
        url=getIntent().getStringExtra("url");
        initWebView(webView, progressBar);
        String token = SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.TOKEN);
        String sid = SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.SITEID);
        String city = SPUtil.getInstance(activity).getString(SPUtil.CITY);
        if (TextUtils.isEmpty(city)) {
            city = SPUtil.getInstance(activity).getString(SPUtil.LOCATION_CITY);
        }
        switch (type) {
            //私享量房
            case 3:
                webView.loadUrl(HttpConstant.HTML + "exclusiveHousing?token=" + token + "&city=" + city);
                tvTitle.setText("私享量房");
                break;
            //无忧验房
            case 4:
                webView.loadUrl(HttpConstant.HTML + "exclusiveInspection?token=" + token + "&city=" + city);
                tvTitle.setText("无忧验房");
                break;
            //专车接驾
            case 5:
                webView.loadUrl(HttpConstant.HTML + "specialCar?token=" + token + "&city=" + city);
                tvTitle.setText("专车接驾");
                break;
            //专属设计
            case 6:
                webView.loadUrl(HttpConstant.HTML + "exclusiveDesign?token=" + token + "&city=" + city);
                tvTitle.setText("专属设计");
                break;
            //我的风格
            case 7:
                suspensionView.setVisibility(View.VISIBLE);
                webView.loadUrl(HttpConstant.HTML + "test?token=" + token + "&sid=" + sid + "&city=" + city);
                tvTitle.setText("我的风格");
                break;
            //计算器
            case 8:
                webView.loadUrl(HttpConstant.HTML + "quotationCalculator?token=" + token + "&city=" + city);
                tvTitle.setText("计算器");
                break;
            //注册协议
            case 9:
                tvTitle.setText("用户协议");
                webView.loadUrl(HttpConstant.HTML + "agreement");
                break;
            //使用帮助
            case 10:
                tvTitle.setText("使用帮助");
                webView.loadUrl(HttpConstant.HTML + "help?id=" + id);
                break;
            //品质生活详情
            case 11:
                webView.loadUrl(HttpConstant.HTML + "lifeparticulars");
                tvTitle.setText("品质生活");
                break;
            //品牌页面的“社会责任”
            case 12:
                 tvTitle.setText("品牌");
                 webView.loadUrl(url);
                  break;
            default:
                break;
        }
    }


    /**
     * 预约报名成功
     */
    @JavascriptInterface
    public void bespokeSuccess() {
        //报名埋点
        PointUtil.getInstent().respokePoint(this);
    }


    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
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
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
    }
}
