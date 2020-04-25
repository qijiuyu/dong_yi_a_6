package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.ConstructionList;
import com.zxdc.utils.library.util.LogUtils;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainConsAdapter extends BaseAdapter {

    private Context context;
    private List<ConstructionList.ConstructionBean> list;
    public MainConsAdapter(Context context, List<ConstructionList.ConstructionBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            if((position+1)%2==0){
                view = LayoutInflater.from(context).inflate(R.layout.item_main_construction2, null);
            }else{
                view = LayoutInflater.from(context).inflate(R.layout.item_main_construction, null);
            }
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ConstructionList.ConstructionBean constructionBean = list.get(position);
        //显示图片
        String imgUrl = constructionBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvTitle.setText(constructionBean.getName());
        holder.tvDes.setText(constructionBean.getDistrict()+" · "+constructionBean.getLoupanname());
        holder.tvType.setText(constructionBean.getStage());

        /**
         * 进入详情页面
         */
        holder.relConstruction.setTag(constructionBean);
        holder.relConstruction.setTag(R.id.tag1,position);
        holder.relConstruction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }
                ConstructionList.ConstructionBean constructionBean = (ConstructionList.ConstructionBean) v.getTag();
                Intent intent = new Intent(context, DecorateWebView.class);
                intent.putExtra("type", 5);
                intent.putExtra("id", constructionBean.getId());
                intent.putExtra("title", constructionBean.getName());
                context.startActivity(intent);

                //埋点
                int position= (int) v.getTag(R.id.tag1);
                MobclickAgent.onEvent(context, "main_cons_"+(position+1));
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.rel_construction)
        RelativeLayout relConstruction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
