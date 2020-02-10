package com.ylean.dyspd.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.BuildingListActivity;
import com.ylean.dyspd.activity.decorate.CaseListActivity;
import com.ylean.dyspd.activity.decorate.ConstructionListActivity;
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.activity.decorate.ExperienceActivity;
import com.ylean.dyspd.activity.decorate.GalleryListActivity;
import com.ylean.dyspd.activity.decorate.SoftLoadingActivity;
import com.ylean.dyspd.activity.decorate.VRListActivity;
import com.ylean.dyspd.activity.init.HobbyActivity;
import com.zxdc.utils.library.view.ClickTextView;

/**
 * Created by Administrator on 2019/12/1.
 */

public class DynamicEmptyView extends LinearLayout implements View.OnClickListener{
    private Context context;
    public DynamicEmptyView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public DynamicEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.dynamic_emptyview,null);
        view.findViewById(R.id.tv_case).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(context,HobbyActivity.class);
                intent.putExtra("type",1);
                context.startActivity(intent);
            }
        });
        view.findViewById(R.id.tv_designer).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(context,DesignerListActivity.class);
                context.startActivity(intent);
            }
        });
        setGravity(Gravity.CENTER);
        addView(view);
    }

    @Override
    public void onClick(View v) {

    }



}
