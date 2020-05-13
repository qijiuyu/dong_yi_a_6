package com.ylean.dyspd.adapter.decorate;

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
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.MainDecorate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DesignerProgressAdapter extends BaseAdapter {

    private Context context;
    private List<MainDecorate.DecorateBean> list;
    private String title;
    public DesignerProgressAdapter(Context context,List<MainDecorate.DecorateBean> list,String title) {
        super();
        this.context = context;
        this.list=list;
        this.title=title;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_decorate_progress, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MainDecorate.DecorateBean decorateBean=list.get(position);
        //显示图片
        String imgUrl = decorateBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvTitle.setText(decorateBean.getTitle());
        holder.tvTime.setText(decorateBean.getDatetime());

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

                //埋点
                switch (title){
                    case "装修前":
                        MobclickAgent.onEvent(context, "decorate_before_cover");
                        break;
                    case "装修中":
                        MobclickAgent.onEvent(context, "decorate_the_cover");
                        break;
                    case "装修后":
                        MobclickAgent.onEvent(context, "decorate_after_cover");
                        break;
                    default:
                        break;
                }
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.rel_decorate)
        RelativeLayout relDecorate;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
