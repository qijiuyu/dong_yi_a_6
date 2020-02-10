package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.content.Intent;
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
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.bean.OnItemClickCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class ActivityFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<News.NewsBean> list;
    private OnItemClickCallBack onItemClickCallBack;
    public ActivityFragmentAdapter(Context context,List<News.NewsBean> list,OnItemClickCallBack onItemClickCallBack) {
        super();
        this.context = context;
        this.list=list;
        this.onItemClickCallBack=onItemClickCallBack;
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
//        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_activity_fragment, null);
            holder = new ViewHolder(view);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
        final News.NewsBean newsBean=list.get(position);
        //显示图片
        String imgUrl=newsBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvType.setText(newsBean.getActstatus());
        holder.tvTitle.setText(newsBean.getTitle());
        holder.tvTime.setText(newsBean.getActstart()+"至"+newsBean.getActend());
        if(newsBean.getStatus()==0){
            holder.tvSubmit.setVisibility(View.VISIBLE);
        }else{
            holder.tvSubmit.setVisibility(View.GONE);
        }
        /**
         * 设置消息已读
         */
        holder.tvSubmit.setTag(newsBean.getId());
        holder.tvSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id= (int) v.getTag();
                onItemClickCallBack.ItemClick(id);
            }
        });


        /**
         * 进入详情
         */
        holder.relContent.setTag(newsBean.getId());
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final int id= (int) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",13);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.rel_content)
        RelativeLayout relContent;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_submit)
        TextView tvSubmit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
