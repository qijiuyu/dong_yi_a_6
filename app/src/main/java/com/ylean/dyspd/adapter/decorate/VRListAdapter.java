package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.zxdc.utils.library.bean.VrList;
import java.util.List;
public class VRListAdapter extends RecyclerView.Adapter<VRListAdapter.MyHolder> {

    private Context context;
    private List<VrList.VrBean> list;
    public VRListAdapter(Context context,List<VrList.VrBean> list) {
        this.context = context;
        this.list=list;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_vr, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = myHolder;
        final VrList.VrBean vrBean=list.get(i);
        if(vrBean==null){
            return;
        }
        //显示图片
        String imgUrl=vrBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(vrBean.getName());

        /**
         * 跳转详情页面
         */
        holder.linClick.setTag(vrBean);
        holder.linClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                VrList.VrBean vrBean= (VrList.VrBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",2);
                intent.putExtra("id",vrBean.getId());
                intent.putExtra("url",vrBean.getDetailurl());
                intent.putExtra("title",vrBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "vr_list_cover");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linClick;
        ImageView imgHead;
        TextView tvName,tvType;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linClick=itemView.findViewById(R.id.lin_click);
            imgHead=itemView.findViewById(R.id.img_head);
            tvName = itemView.findViewById(R.id.tv_name);
            tvType=itemView.findViewById(R.id.tv_type);
        }
    }
}
