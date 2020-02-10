package com.ylean.dyspd.persenter.found;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.http.HttpConstant;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.List;

/**
 * Created by Administrator on 2019/11/20.
 */

public class FoundPersenter{

    private Activity activity;
    public FoundPersenter(Activity activity){
        this.activity=activity;
    }


    /**
     * 设置自定义字体
     * @param tv
     */
    public void setFont(TextView tv){
        AssetManager mgr = activity.getAssets();
        Typeface tf = Typeface.createFromAsset(mgr, "FZYTJW_GB1.ttf");
        tv.setTypeface(tf);
    }


}
