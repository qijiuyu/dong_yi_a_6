package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.view.HorizontalListView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class DesignerListAdapter extends BaseAdapter {

    private Context context;
    private List<DesignerList.DesignerBean> list;
    //标签适配器
    private DesignerTagAdapter designerTagAdapter;
    //图片适配器
    private DesignerImgAdapter designerImgAdapter;
    public DesignerListAdapter(Context context,List<DesignerList.DesignerBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_designer_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final DesignerList.DesignerBean designerBean=list.get(position);
        //显示图片
        String imgUrl=designerBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        //显示角标图片
        String tagImg=designerBean.getTagimg();
        holder.imgStatus.setTag(R.id.imageid,tagImg);
        if(holder.imgStatus.getTag(R.id.imageid)!=null && tagImg==holder.imgStatus.getTag(R.id.imageid)){
            Glide.with(context).load(tagImg).centerCrop().into(holder.imgStatus);
        }
        holder.tvName.setText(designerBean.getName());
        String year="从业<font color=\"#000000\"><strong>"+designerBean.getWorkingyear()+"</strong></font>年-<font color=\"#000000\"><strong>"+designerBean.getCasecount()+"</strong></font>套作品";
        holder.tvYear.setText(Html.fromHtml(year));
        holder.tvTitle.setText(designerBean.getDlevel());
        holder.tvType.setText(designerBean.getDtype());

        //擅长风格
        designerTagAdapter=new DesignerTagAdapter(context,designerBean.getDstyle());
        holder.listTag.setAdapter(designerTagAdapter);

        //显示案例图片
        designerImgAdapter=new DesignerImgAdapter(context,designerBean.getCaseimgs());
        holder.listImg.setAdapter(designerImgAdapter);

        /**
         * 进入详情页面
         */
        holder.relDesigner.setTag(designerBean);
        holder.relDesigner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                DesignerList.DesignerBean designerBean= (DesignerList.DesignerBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",8);
                intent.putExtra("id",designerBean.getId());
                intent.putExtra("title",designerBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "designer_list_details");
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.rel_designer)
        RelativeLayout relDesigner;
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
        @BindView(R.id.img_Status)
        ImageView imgStatus;
        @BindView(R.id.list_tag)
        HorizontalListView listTag;
        @BindView(R.id.list_img)
        HorizontalListView listImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
