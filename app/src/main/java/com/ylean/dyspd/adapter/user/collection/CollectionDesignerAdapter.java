package com.ylean.dyspd.adapter.user.collection;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.collection.CollectionCaseActivity;
import com.ylean.dyspd.activity.user.collection.CollectionDesignerActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.adapter.decorate.DesignerTagAdapter;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.view.HorizontalListView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionDesignerAdapter extends BaseAdapter {

    private Context context;
    private List<DesignerList.DesignerBean> list;
    //标签适配器
    private DesignerTagAdapter designerTagAdapter;

    public CollectionDesignerAdapter(Context context, List<DesignerList.DesignerBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_designer, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final DesignerList.DesignerBean designerBean = list.get(position);
        //显示图片
        String imgUrl = designerBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).into(holder.imgHead);
        }
        holder.tvName.setText(designerBean.getName());
        String year="从业<font color=\"#000000\"><strong>"+designerBean.getWorkingyear()+"</strong></font>年-<font color=\"#000000\"><strong>"+designerBean.getCasecount()+"</strong></font>套作品";
        holder.tvYear.setText(Html.fromHtml(year));
        holder.tvTitle.setText(designerBean.getDlevel());
        holder.tvType.setText(designerBean.getDtype());

        //擅长风格
        designerTagAdapter = new DesignerTagAdapter(context, designerBean.getDstyle());
        holder.listTag.setAdapter(designerTagAdapter);

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(designerBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                ((CollectionDesignerActivity)context).cancleColl((Integer) v.getTag());
            }
        });


        /**
         *
         * 进入详情页面
         */
        holder.relContent.setTag(designerBean);
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                DesignerList.DesignerBean designerBean= (DesignerList.DesignerBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",8);
                intent.putExtra("id",designerBean.getDesignerid());
                intent.putExtra("title",designerBean.getName());
                context.startActivity(intent);

            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.rel)
        LinearLayout rel;
        @BindView(R.id.list_tag)
        HorizontalListView listTag;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;
        @BindView(R.id.content)
        RelativeLayout relContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
