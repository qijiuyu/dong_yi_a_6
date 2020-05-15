package com.ylean.dyspd.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.ylean.dyspd.activity.web.PrivateWebView;

public class MyCheckTextView2 extends ClickableSpan {
    SpannableString string;
    Context context;
    public MyCheckTextView2(SpannableString str, Context context){
        super();
        this.string = str;
        this.context = context;
    }


    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.BLUE);
    }


    @Override
    public void onClick(View widget) {
        Intent intent=new Intent(context, PrivateWebView.class);
        intent.putExtra("type",2);
        context.startActivity(intent);

    }
}