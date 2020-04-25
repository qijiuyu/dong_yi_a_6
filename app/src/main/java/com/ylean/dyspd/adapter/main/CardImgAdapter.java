package com.ylean.dyspd.adapter.main;

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
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.CaseImg;
import com.zxdc.utils.library.util.LogUtils;

import java.util.List;
public class CardImgAdapter extends RecyclerView.Adapter<CardImgAdapter.MyHolder> {

    private Context context;
    private List<CaseImg> list;
    public CardImgAdapter(Context context,List<CaseImg> list) {
        this.context = context;
        this.list=list;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_type_img, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder =myHolder;
        final CaseImg caseImg=list.get(i%list.size());
        if(caseImg==null){
            return;
        }
        holder.tvTitle.setText(caseImg.getTitle());
        //显示图片
        String imgUrl=caseImg.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }

        /**
         * 跳转详情页面
         */
        holder.linClick.setTag(caseImg);
        holder.linClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final CaseImg caseImg= (CaseImg) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("id",caseImg.getId());
                intent.putExtra("title",caseImg.getTitle());
                if(caseImg.getDecoratetype().equals("VR")){
                    intent.putExtra("type",2);
                    intent.putExtra("url",caseImg.getDetailurl());
                }
                if(caseImg.getDecoratetype().equals("硬装")){
                    intent.putExtra("type",12);
                }
                if(caseImg.getDecoratetype().equals("软装")){
                    intent.putExtra("type",7);
                }
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "main_case_details");
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
        LinearLayout linClick;
        ImageView imgHead;
        TextView tvTitle;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linClick=itemView.findViewById(R.id.lin_click);
            imgHead=itemView.findViewById(R.id.img_head);
            tvTitle=itemView.findViewById(R.id.tv_title);
        }
    }

}

