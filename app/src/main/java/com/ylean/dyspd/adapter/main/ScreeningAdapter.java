package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Screening;

import java.util.List;

/**
 *筛选
 */
public class ScreeningAdapter extends RecyclerView.Adapter<ScreeningAdapter.MyHolder> {

    private Context context;
    /**
     * 0:风格
     * 1：所在店面
     * 2：案例属性
     * 3：面积
     * 4：设计师类型
     */
    private int type;
    //选中的下标
    private int index=0;
    private List<Screening.ScreeningBean> list;
    private OnItemClickListener onItemClickListener;
    public ScreeningAdapter(Context context,List<Screening.ScreeningBean> list,int type,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list=list;
        this.type=type;
        this.onItemClickListener=onItemClickListener;
        if(this.list!=null){
            Screening.ScreeningBean screeningBean=new Screening.ScreeningBean();
            screeningBean.setId(-1);
            screeningBean.setName("全部");
            this.list.add(0,screeningBean);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Object object);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View inflate = null;
        switch (type){
            //风格
            case 0:
                 inflate = LayoutInflater.from(context).inflate(R.layout.item_screening_stype, viewGroup,false);
                 break;
            //所在店面
            case 1:
                inflate = LayoutInflater.from(context).inflate(R.layout.item_screening_store, viewGroup,false);
                 break;
            default:
                inflate = LayoutInflater.from(context).inflate(R.layout.item_screening_case, viewGroup,false);
                break;
        }
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        MyHolder holder =myHolder;
        final Screening.ScreeningBean screeningBean=list.get(i);
        switch (type){
            //风格
            case 0:
                if(index==i){
                    holder.relStore.setBackground(context.getResources().getDrawable(R.drawable.bg_screening_style_click));
                }else{
                    holder.relStore.setBackground(context.getResources().getDrawable(R.drawable.bg_screening_style));
                }
                holder.relStore.setTag(screeningBean);
                holder.tvName.setText(screeningBean.getName());
                //显示图片
                if(screeningBean.getName().equals("全部")){
                    holder.imgHed.setImageResource(R.mipmap.black_all);
                }else{
                    String styleImg=screeningBean.getImg();
                    holder.imgHed.setTag(R.id.imageid,styleImg);
                    if(holder.imgHed.getTag(R.id.imageid)!=null && styleImg==holder.imgHed.getTag(R.id.imageid)){
                        Glide.with(context).load(styleImg).centerCrop().into(holder.imgHed);
                    }
                }
                 break;
            default:
                if(index==i){
                    holder.tvName.setBackground(context.getResources().getDrawable(R.drawable.bg_screening_style_click));
                }else{
                    holder.tvName.setBackground(context.getResources().getDrawable(R.drawable.bg_screening_style));
                }
                holder.tvName.setTag(screeningBean);
                holder.tvName.setText(screeningBean.getName());
                break;
        }

        if(type==0){
            holder.relStore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    index=i;
                    ScreeningAdapter.this.notifyDataSetChanged();
                    onItemClickListener.onItemClick(v.getTag());
                }
            });
        }else{
            holder.tvName.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    index=i;
                    ScreeningAdapter.this.notifyDataSetChanged();
                    onItemClickListener.onItemClick(v.getTag());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
       return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout relStore;
        ImageView imgHed;
        TextView tvName;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            if(type==0) {
                relStore = itemView.findViewById(R.id.rel_store);
                imgHed = itemView.findViewById(R.id.img_head);
            }
            tvName=itemView.findViewById(R.id.tv_name);
        }
    }
}

