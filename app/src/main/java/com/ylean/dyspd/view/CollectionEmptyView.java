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
import com.ylean.dyspd.activity.decorate.DecorateProgressActivity;
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.activity.decorate.ExperienceActivity;
import com.ylean.dyspd.activity.decorate.GalleryListActivity;
import com.ylean.dyspd.activity.decorate.SoftLoadingActivity;
import com.ylean.dyspd.activity.decorate.VRListActivity;
import com.zxdc.utils.library.view.ClickTextView;

/**
 * Created by Administrator on 2019/12/1.
 */

public class CollectionEmptyView extends LinearLayout implements View.OnClickListener{
    private Context context;
    private TextView tvName;
    private ClickTextView tvConfirm;
    private int type;

    public CollectionEmptyView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public CollectionEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }


    private void initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.search_trip,null);
        tvName=view.findViewById(R.id.tv_name);
        tvConfirm=view.findViewById(R.id.tv_confirm);
        tvConfirm.setOnClickListener(this);
        setGravity(Gravity.CENTER);
        addView(view);
    }

    @Override
    public void onClick(View v) {
      if(v.getId()==R.id.tv_confirm){
          Intent intent=new Intent();
          switch (type){
              case 0:
                   intent.setClass(context, DesignerListActivity.class);
                   break;
              case 1:
                  intent.setClass(context, CaseListActivity.class);
                  break;
              case 2:
                  intent.setClass(context, SoftLoadingActivity.class);
                  break;
              case 3:
                  intent.setClass(context, BuildingListActivity.class);
                  break;
              case 4:
                  intent.setClass(context, ConstructionListActivity.class);
                  break;
              case 5:
                  intent.setClass(context, GalleryListActivity.class);
                  break;
              case 6:
                  intent.setClass(context, VRListActivity.class);
                  break;
              case 7:
                  intent.setClass(context, ExperienceActivity.class);
                  break;
              case 8:
                  intent.setClass(context, DecorateProgressActivity.class);
                  break;
              default:
                  break;
          }
          context.startActivity(intent);
      }
    }


    public void setType(int type){
        this.type=type;
        switch (type){
            case 0:
                 tvName.setText("没有找到您收藏的设计师");
                 tvConfirm.setText("去收藏设计师");
                 break;
            case 1:
                tvName.setText("没有找到您收藏的风格案例");
                tvConfirm.setText("去收藏风格案例");
                break;
            case 2:
                tvName.setText("没有找到您收藏的软装范本");
                tvConfirm.setText("去收藏软装范本");
                break;
            case 3:
                tvName.setText("没有找到您收藏的楼盘/小区");
                tvConfirm.setText("去收藏楼盘/小区");
                break;
            case 4:
                tvName.setText("没有找到您收藏的在施工地");
                tvConfirm.setText("去收藏在施工地");
                break;
            case 5:
                tvName.setText("没有找到您收藏的案例图库");
                tvConfirm.setText("去收藏案例图库");
                break;
            case 6:
                tvName.setText("没有找到您收藏的VR样板间");
                tvConfirm.setText("去收藏VR样板间");
                break;
            case 7:
                tvName.setText("没有找到您收藏的体验店");
                tvConfirm.setText("去收藏体验店");
                break;
            case 8:
                tvName.setText("没有找到您收藏的资讯");
                tvConfirm.setText("去收藏资讯");
                break;
            default:
                break;
        }
    }
}
