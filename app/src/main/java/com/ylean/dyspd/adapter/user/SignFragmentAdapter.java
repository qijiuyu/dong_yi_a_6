package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.bean.OnItemClickCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class SignFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<News.NewsBean> list;
    private OnItemClickCallBack onItemClickCallBack;
    /**
     * 1：公告进入
     * 2：动态进入
     */
    private int type;
    public SignFragmentAdapter(Context context,List<News.NewsBean> list,OnItemClickCallBack onItemClickCallBack,int type) {
        super();
        this.context = context;
        this.list=list;
        this.onItemClickCallBack=onItemClickCallBack;
        this.type=type;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_sign_fragment, null);
            holder = new ViewHolder(view);
//            view.setTag(holder);
//        } else {
//            holder = (ViewHolder) view.getTag();
//        }
        final News.NewsBean newsBean=list.get(position);
        if(newsBean.getStatus()==0){
            holder.tvRed.setVisibility(View.VISIBLE);
            holder.tvSubmit.setVisibility(View.VISIBLE);
        }else{
            holder.tvRed.setVisibility(View.GONE);
            holder.tvSubmit.setVisibility(View.GONE);
        }
        if(type==1){
            holder.tvContent.setText(newsBean.getTitle());
        }else{
            holder.tvContent.setText(newsBean.getContent());
        }
        holder.tvTime.setText(newsBean.getCreatedate());


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
                if(v.getTag()==null || type==2){
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
        @BindView(R.id.tv_red)
        TextView tvRed;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_submit)
        TextView tvSubmit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
