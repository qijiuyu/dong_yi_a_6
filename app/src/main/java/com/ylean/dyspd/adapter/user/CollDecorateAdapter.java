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
import com.ylean.dyspd.activity.user.collection.CollectionCaseActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.fragment.user.CollDecorateFragment;
import com.zxdc.utils.library.bean.CollDecorate;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CollDecorateAdapter extends BaseAdapter {

    private Context context;
    private List<CollDecorate.DecorateBean> list;
    private CollDecorateFragment.OnItemCancleImpl onItemCancle;

    public CollDecorateAdapter(Context context, List<CollDecorate.DecorateBean> list,CollDecorateFragment.OnItemCancleImpl onItemCancle) {
        super();
        this.context = context;
        this.list = list;
        this.onItemCancle=onItemCancle;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_decorate, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final CollDecorate.DecorateBean decorateBean = list.get(position);
        //显示图片
        String imgUrl = decorateBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvType.setText(decorateBean.getCname());
        holder.tvTitle.setText(decorateBean.getTitle());
        holder.tvLookNum.setText(String.valueOf(decorateBean.getViewcount()));
        holder.tvShareNum.setText(String.valueOf(decorateBean.getSharecount()));
        holder.tvTime.setText(decorateBean.getDatetime());

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(decorateBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                onItemCancle.onCancleClick((Integer) v.getTag());
            }
        });

        /**
         * 进入详情页面
         */
        holder.relDecorate.setTag(decorateBean);
        holder.relDecorate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                CollDecorate.DecorateBean decorateBean= (CollDecorate.DecorateBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",3);
                intent.putExtra("id",decorateBean.getNewsid());
                intent.putExtra("title",decorateBean.getTitle());
                context.startActivity(intent);
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
        @BindView(R.id.tv_look_num)
        TextView tvLookNum;
        @BindView(R.id.tv_share_num)
        TextView tvShareNum;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.rel_decorate)
        RelativeLayout relDecorate;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
