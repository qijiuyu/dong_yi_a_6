package com.ylean.dyspd.adapter.user.collection;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.collection.CollectionSoftActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.SoftLoadingList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CollectionSoftAdapter extends BaseAdapter {

    private Context context;
    private List<SoftLoadingList.SoftLoadingBean> list;
    public CollectionSoftAdapter(Context context, List<SoftLoadingList.SoftLoadingBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final SoftLoadingList.SoftLoadingBean softLoadingBean = list.get(position);
        //显示图片
        String imgUrl = softLoadingBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(softLoadingBean.getName());
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(softLoadingBean.getCityname() + " | " );
        if(!TextUtils.isEmpty(softLoadingBean.getLoupanname())){
            stringBuffer.append(softLoadingBean.getLoupanname() + " | " );
        }
        stringBuffer.append(softLoadingBean.getDstyle()+" | "+softLoadingBean.getSquare());
        holder.tvDes.setText(stringBuffer.toString());

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(softLoadingBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }
                ((CollectionSoftActivity) context).cancleColl((Integer) v.getTag());
            }
        });


        /**
         * 进入详情页面
         */
        holder.relContent.setTag(softLoadingBean);
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                SoftLoadingList.SoftLoadingBean softLoadingBean= (SoftLoadingList.SoftLoadingBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",7);
                intent.putExtra("id",softLoadingBean.getCaseid());
                intent.putExtra("title",softLoadingBean.getName());
                context.startActivity(intent);
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.content)
        RelativeLayout relContent;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
