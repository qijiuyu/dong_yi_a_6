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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.DecorateProgressActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.utils.PointUtil;
import com.zxdc.utils.library.bean.DecorateType;
import com.zxdc.utils.library.bean.VrList;

import java.util.List;

public class DecorateItemAdapter extends RecyclerView.Adapter<DecorateItemAdapter.MyHolder> {

    private Context context;
    private List<DecorateType.TypeBean> list;
    public DecorateItemAdapter(Context context, List<DecorateType.TypeBean> list) {
        this.context = context;
        this.list=list;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_activity_decorate, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = myHolder;
        final DecorateType.TypeBean typeBean=list.get(i);
        if(typeBean==null){
            return;
        }
        holder.tvType.setText(typeBean.getCommonvalue());
        holder.tvTitle.setText(typeBean.getCommontype());

        /**
         * 跳转详情页面
         */
        holder.relClick.setTag(typeBean);
        holder.relClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                DecorateType.TypeBean typeBean= (DecorateType.TypeBean) v.getTag();
                Intent intent = new Intent(context, DecorateProgressActivity.class);
                intent.putExtra("cid",typeBean.getCommonid());
                intent.putExtra("title",typeBean.getCommonvalue());
                context.startActivity(intent);

                //埋点
                PointUtil.getInstent().pagePoint(context,9);
                switch (typeBean.getCommonvalue()){
                    case "装修前":
                        MobclickAgent.onEvent(context, "browse_before");
                         break;
                    case "装修中":
                        MobclickAgent.onEvent(context, "browse_the");
                        break;
                    case "装修后":
                        MobclickAgent.onEvent(context, "browse_after");
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        RelativeLayout relClick;
        TextView tvTitle,tvType;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            relClick=itemView.findViewById(R.id.rel_click);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvType=itemView.findViewById(R.id.tv_type);
        }
    }
}
