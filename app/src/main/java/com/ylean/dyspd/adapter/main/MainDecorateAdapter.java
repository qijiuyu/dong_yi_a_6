package com.ylean.dyspd.adapter.main;

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
import com.zxdc.utils.library.bean.MainDecorate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 装修攻略
 */
public class MainDecorateAdapter extends BaseAdapter {

    private Context context;
    private List<MainDecorate.DecorateBean> list;
    public MainDecorateAdapter(Context context, List<MainDecorate.DecorateBean> list) {
        super();
        this.context = context;
        this.list=list;
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
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_main_decorate, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final MainDecorate.DecorateBean decorateBean=list.get(position);
        holder.tvType.setText(decorateBean.getCname());
        holder.tvTitle.setText(decorateBean.getTitle());
        holder.tvTime.setText(decorateBean.getDatetime());

        //显示图片
        String imgUrl=decorateBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }

        /**
         * 进入详情页面
         */
        holder.relDecorate.setTag(decorateBean);
        holder.relDecorate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                MainDecorate.DecorateBean decorateBean= (MainDecorate.DecorateBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",3);
                intent.putExtra("id",decorateBean.getId());
                intent.putExtra("title",decorateBean.getTitle());
                context.startActivity(intent);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.rel_decorate)
        RelativeLayout relDecorate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
