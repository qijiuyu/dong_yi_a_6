package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.GalleryList;

import java.util.List;

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.MyHolder> {

    private Context context;
    private List<GalleryList.GalleryBean> list;
    public GalleryListAdapter(Context context,List<GalleryList.GalleryBean> list) {
        this.context = context;
        this.list=list;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_gallery, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        final GalleryList.GalleryBean galleryBean=list.get(i);
        if(galleryBean==null){
            return;
        }
        //显示图片
        String imgUrl=galleryBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }

        holder.tvName.setText(galleryBean.getName());
        holder.tvType.setText(galleryBean.getHousespace());

        /**
         * 进入详情页面
         */
        holder.linGallery.setTag(galleryBean);
        holder.linGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                GalleryList.GalleryBean galleryBean= (GalleryList.GalleryBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",1);
                intent.putExtra("id",galleryBean.getId());
                intent.putExtra("title",galleryBean.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linGallery;
        ImageView imgHead;
        TextView tvName,tvType;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linGallery=itemView.findViewById(R.id.lin_gallery);
            imgHead=itemView.findViewById(R.id.img_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType=itemView.findViewById(R.id.tv_type);
        }
    }
}
