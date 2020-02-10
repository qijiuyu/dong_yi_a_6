package com.ylean.dyspd.adapter.user.collection;

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
import com.ylean.dyspd.activity.user.collection.CollectionGalleryActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.GalleryList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CollectionGalleryAdapter extends BaseAdapter {

    private Context context;
    private List<GalleryList.GalleryBean> list;
    public CollectionGalleryAdapter(Context context, List<GalleryList.GalleryBean> list) {
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

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_photo, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final GalleryList.GalleryBean galleryBean = list.get(position);
        //显示案例图片
        String imgUrl = galleryBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(galleryBean.getName());
        holder.tvType.setText(galleryBean.getHousespace());

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(galleryBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }
                ((CollectionGalleryActivity) context).cancleColl((Integer) v.getTag());
            }
        });

        /**
         *
         * 进入详情页面
         */
        holder.relContent.setTag(galleryBean);
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                GalleryList.GalleryBean galleryBean= (GalleryList.GalleryBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",1);
                intent.putExtra("id",galleryBean.getImgid());
                intent.putExtra("title",galleryBean.getName());
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
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
