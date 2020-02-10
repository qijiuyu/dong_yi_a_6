package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.BuildingList;
import com.zxdc.utils.library.bean.MainBuilding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildingListAdapter extends BaseAdapter {

    private Context context;
    private List<BuildingList.BuildingBean> list;
    public BuildingListAdapter(Context context,List<BuildingList.BuildingBean> list) {
        super();
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_building, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final BuildingList.BuildingBean buildingBean=list.get(position);
        //显示图片
        String imgUrl=buildingBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvTitle.setText(buildingBean.getName());
        String des="在施工地:<font color=\"#B09B7C\">"+buildingBean.getConstructioncount()+"</font>户 ｜ 户型解析:<font color=\"#B09B7C\">"+buildingBean.getAnalysiscount()+"</font>户 ｜ 相关案例:<font color=\"#B09B7C\">"+buildingBean.getCasecount()+"</font>个";
        holder.tvDes.setText(Html.fromHtml(des));


        /**
         * 进入详情页面
         */
        holder.relBuilding.setTag(buildingBean);
        holder.relBuilding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                BuildingList.BuildingBean buildingBean= (BuildingList.BuildingBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",6);
                intent.putExtra("id",buildingBean.getId());
                intent.putExtra("title",buildingBean.getName());
                context.startActivity(intent);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.rel_building)
        RelativeLayout relBuilding;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_des)
        TextView tvDes;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
