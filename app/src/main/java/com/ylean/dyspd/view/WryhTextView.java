package com.ylean.dyspd.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zxdc.utils.library.util.LogUtils;

/**
 * Created by Administrator on 2019/11/20.
 */

public class WryhTextView extends TextView {
    public WryhTextView(Context context) {
        super(context);
    }

    public WryhTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        AssetManager mgr = context.getAssets();
//        Typeface tf = Typeface.createFromAsset(mgr, "PingFang.otf");
//        setTypeface(tf);
    }

    public WryhTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
