package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.MainBuilding;

import java.util.ArrayList;
import java.util.List;
/**
 * 首页的热装楼盘
 */
public class MainHotAdapter extends RecyclerView.Adapter<MainHotAdapter.MyHolder> {

    private Context context;
    private List<MainBuilding.BuildingBean> list;
    private OnItemClickListener onItemClickListener;
    public MainHotAdapter(Context context, List<MainBuilding.BuildingBean> list) {
        this.context = context;
        this.list=list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_hot, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        final MainBuilding.BuildingBean buildingBean=list.get(i%list.size());
        if(buildingBean==null){
            return;
        }
        holder.tvName.setText(buildingBean.getName());
        String des="在施工地:<font color=\"#000000\"><strong>"+buildingBean.getConstructioncount()+"</strong></font>户 | 户型解析:<font color=\"#000000\"><strong>"+buildingBean.getAnalysiscount()+"</strong></font>户<br>相关案例:<font color=\"#000000\"><strong>"+buildingBean.getCasecount()+"</strong></font>个";
        holder.tvDes.setText(Html.fromHtml(des));
        //显示图片
        String imgUrl=buildingBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().error(R.mipmap.default_building).into(holder.imgHead);
        }

        /**
         * 进入详情页面
         */
        holder.linBuilding.setTag(buildingBean);
        holder.linBuilding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                MainBuilding.BuildingBean buildingBean= (MainBuilding.BuildingBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",6);
                intent.putExtra("id",buildingBean.getId());
                intent.putExtra("title",buildingBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "main_building_details");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null && list.size()>0){
            return Integer.MAX_VALUE;
        }else{
            return 0;
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linBuilding;
        ImageView imgHead;
        TextView tvName,tvDes;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linBuilding=itemView.findViewById(R.id.lin_building);
            imgHead=itemView.findViewById(R.id.img_head);
            tvName=itemView.findViewById(R.id.tv_name);
            tvDes=itemView.findViewById(R.id.tv_des);
        }
    }

}

