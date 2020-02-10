package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylean.dyspd.R;

/**
 * 流程
 */
public class MainProcessAdapter extends RecyclerView.Adapter<MainProcessAdapter.MyHolder> {

    private Context context;
    private String[] name=new String[]{"家装咨询\n门店体验\n签署协议","现场量房\n设计方案\n签署合同","开工交底\n中期验收\n竣工验收","售后服务"};
    private int[] img=new int[]{R.mipmap.main_item_zx,R.mipmap.main_item_sj,R.mipmap.main_item_sg,R.mipmap.main_item_sh};
    public MainProcessAdapter(Context context) {
        this.context = context;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_process, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        holder.imgHead.setImageDrawable(context.getResources().getDrawable(img[i]));
        holder.tvDes.setText(name[i]);
        holder.tvNum.setText("0"+(i+1));
        if(i==3){
            holder.imgLine.setVisibility(View.GONE);
        }else{
            holder.imgLine.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgHead,imgLine;
        TextView tvNum,tvDes;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgHead=itemView.findViewById(R.id.img_head);
            tvNum=itemView.findViewById(R.id.tv_num);
            tvDes=itemView.findViewById(R.id.tv_des);
            imgLine=itemView.findViewById(R.id.img_line);
        }
    }

}

