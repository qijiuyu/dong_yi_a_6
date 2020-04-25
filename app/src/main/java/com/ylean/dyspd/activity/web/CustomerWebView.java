package com.ylean.dyspd.activity.web;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseWebView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 客服
 * Created by Administrator on 2019/12/31.
 */

public class CustomerWebView extends BaseWebView{

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private static final int REQUEST_CODE_FILE_CHOOSER = 1;

    private ValueCallback<Uri> mUploadCallbackForLowApi;
    private ValueCallback<Uri[]> mUploadCallbackForHighApi;
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
        initWebView(webView, progressBar);
        webView.setWebChromeClient(myWebChromeClient);
        tvTitle.setText("客服");
        webView.loadUrl("http://ddt.zoosnet.net/LR/Chatpre.aspx?andriodA6&id=DDT92914587&lng=cn");
    }


    private WebChromeClient myWebChromeClient = new WebChromeClient() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadCallbackForHighApi = filePathCallback;
            Intent intent = fileChooserParams.createIntent();
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(intent, REQUEST_CODE_FILE_CHOOSER);
            } catch (ActivityNotFoundException e) {
                mUploadCallbackForHighApi = null;
                return false;
            }
            return true;
        }

        // For 3.0+
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            openFilerChooser(uploadMsg);
        }

        //For Android 4.1+
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFilerChooser(uploadMsg);
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FILE_CHOOSER && (resultCode == RESULT_OK || resultCode == RESULT_CANCELED)) {
            afterFileChooseGoing(resultCode, data);
        }
    }

    private void afterFileChooseGoing(int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadCallbackForHighApi == null) {
                return;
            }
            mUploadCallbackForHighApi.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            mUploadCallbackForHighApi = null;
        } else {
            if (mUploadCallbackForLowApi == null) {
                return;
            }
            Uri result = data == null ? null : data.getData();
            mUploadCallbackForLowApi.onReceiveValue(result);
            mUploadCallbackForLowApi = null;
        }
    }


    private void openFilerChooser(ValueCallback<Uri> uploadMsg) {
        mUploadCallbackForLowApi = uploadMsg;
        startActivityForResult(Intent.createChooser(getFilerChooserIntent(), "File Chooser"), REQUEST_CODE_FILE_CHOOSER);
    }

    private Intent getFilerChooserIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return intent;
    }

    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
            finish();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.clearCache(true);
    }
}
