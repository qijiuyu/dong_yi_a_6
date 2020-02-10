package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.bespoke.BespokeNearActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.NearList;
import com.zxdc.utils.library.view.ClickTextView;
import java.util.List;
/**
 * 附近门店
 */
public class MainNearAdapter extends RecyclerView.Adapter<MainNearAdapter.MyHolder> {

    private Context context;
    private List<NearList.NearBean> list;
    public MainNearAdapter(Context context,List<NearList.NearBean> list) {
        this.context = context;
        this.list=list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_experience, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder =myHolder;
        NearList.NearBean nearBean=list.get(i);
        if(nearBean==null){
            return;
        }
        holder.viewLine.setVisibility(View.GONE);
        holder.tvTitle.setText(nearBean.getName());
        String des="设计案例：<strong>"+nearBean.getCasecount()+"</strong>套 | 装修工地：<strong>"+nearBean.getConstructcount()+"</strong>";
        holder.tvDes.setText(Html.fromHtml(des));
        holder.tvTel.setText(nearBean.getTelphone());
        //显示图片
        String imgUrl=nearBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }


        /**
         * 打电话
         */
        holder.linTel.setTag(nearBean.getTelphone());
        holder.linTel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String tel=v.getTag().toString();
                Uri uri=Uri.parse("tel:"+tel);
                Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                context.startActivity(intent);
            }
        });


        /**
         * 立即预约
         */
        holder.linBespoke.setTag(nearBean);
        holder.linBespoke.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final NearList.NearBean nearBean= (NearList.NearBean) v.getTag();
                Intent intent=new Intent(context, BespokeNearActivity.class);
                intent.putExtra("id",nearBean.getId());
                context.startActivity(intent);
            }
        });

        /**
         * 进入详情页面
         */
        holder.linNear.setTag(nearBean);
        holder.linNear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                NearList.NearBean nearBean= (NearList.NearBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",4);
                intent.putExtra("id",nearBean.getId());
                intent.putExtra("title",nearBean.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linNear,linBespoke,linTel;
        ImageView imgHead;
        TextView tvTitle,tvDes,tvTel;
        View viewLine;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linNear=itemView.findViewById(R.id.lin_near);
            imgHead=itemView.findViewById(R.id.img_head);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvDes=itemView.findViewById(R.id.tv_des);
            linBespoke=itemView.findViewById(R.id.lin_bespoke);
            linTel=itemView.findViewById(R.id.lin_tel);
            tvTel=itemView.findViewById(R.id.tv_tel);
            viewLine=itemView.findViewById(R.id.view);
        }
    }

}

