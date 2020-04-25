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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.MainDesigner;
import java.util.List;

public class MainDesignerAdapter extends RecyclerView.Adapter<MainDesignerAdapter.MyHolder> {

    private Context context;
    private List<MainDesigner.MainDesignerBean> list;
    public MainDesignerAdapter(Context context, List<MainDesigner.MainDesignerBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_designer, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        final MainDesigner.MainDesignerBean mainDesignerBean=list.get(i);
        //显示图片
        String imgUrl = mainDesignerBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(holder.imgHead);
        }
        holder.tvLevel.setText(mainDesignerBean.getDlevel());
        String des="案例数：<font color=\"#AD9676\">"+mainDesignerBean.getCasecount()+"</font>套";
        holder.tvCaseNum.setText(Html.fromHtml(des));
        holder.tvName.setText(mainDesignerBean.getName());
        holder.tvTags.setText("︻擅长："+mainDesignerBean.getStyle()+"︼");

        /**
         * 进入详情页面
         */
        holder.linDesigner.setTag(mainDesignerBean);
        holder.linDesigner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                MainDesigner.MainDesignerBean mainDesignerBean= (MainDesigner.MainDesignerBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",8);
                intent.putExtra("id",mainDesignerBean.getId());
                intent.putExtra("title",mainDesignerBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "main_designer_details");
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout linDesigner;
        ImageView imgHead;
        TextView tvLevel,tvCaseNum,tvName,tvTags;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linDesigner=itemView.findViewById(R.id.rel_designer);
            imgHead=itemView.findViewById(R.id.img_head);
            tvLevel=itemView.findViewById(R.id.tv_level);
            tvCaseNum=itemView.findViewById(R.id.tv_case_num);
            tvName=itemView.findViewById(R.id.tv_name);
            tvTags=itemView.findViewById(R.id.tv_tags);
        }
    }

}

