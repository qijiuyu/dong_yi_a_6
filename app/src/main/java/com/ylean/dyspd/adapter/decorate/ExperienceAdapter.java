package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.bespoke.BespokeNearActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.NearList;
import com.zxdc.utils.library.view.ClickTextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 体验店adapter
 */
public class ExperienceAdapter extends BaseAdapter {

    private Context context;
    private List<NearList.NearBean> list;

    public ExperienceAdapter(Context context, List<NearList.NearBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_experience, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NearList.NearBean nearBean = list.get(position);
        //显示图片
        String imgUrl = nearBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvTitle.setText(nearBean.getName());
        holder.tvDes.setText("设计案例："+nearBean.getCasecount()+" 套 | 装修工地："+nearBean.getConstructcount());
        holder.tvTel.setText(nearBean.getTelphone());

        //打电话
        holder.linTel.setTag(nearBean.getTelphone());
        holder.linTel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + v.getTag().toString()));
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "store_list_tel");
            }
        });

        /**
         * 预约参观
         */
        holder.linBespoke.setTag(nearBean);
        holder.linBespoke.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final NearList.NearBean nearBean= (NearList.NearBean) v.getTag();
                Intent intent=new Intent(context, BespokeNearActivity.class);
                intent.putExtra("id",nearBean.getId());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "store_list_bespoke");
            }
        });

        /**
         * 进入详情页面
         */
        holder.linNear.setTag(nearBean);
        holder.linNear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                NearList.NearBean nearBean= (NearList.NearBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",4);
                intent.putExtra("id",nearBean.getId());
                intent.putExtra("title",nearBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "store_list_cover");
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.lin_near)
        LinearLayout linNear;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.lin_bespoke)
        LinearLayout linBespoke;
        @BindView(R.id.lin_tel)
        LinearLayout linTel;
        @BindView(R.id.tv_tel)
        TextView tvTel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
