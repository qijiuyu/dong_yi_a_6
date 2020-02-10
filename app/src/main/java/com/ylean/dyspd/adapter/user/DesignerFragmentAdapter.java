package com.ylean.dyspd.adapter.user;

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
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.adapter.decorate.DesignerTagAdapter;
import com.ylean.dyspd.fragment.user.DesignerFragment;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.view.HorizontalListView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class DesignerFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<DesignerList.DesignerBean> list;
    private DesignerFragment.CancleFocusImpl cancleFocus;
    public DesignerFragmentAdapter(Context context,List<DesignerList.DesignerBean> list,DesignerFragment.CancleFocusImpl cancleFocus) {
        super();
        this.context = context;
        this.list=list;
        this.cancleFocus=cancleFocus;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_designer_fragment, null);
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
            Glide.with(context).load(imgUrl).into(holder.imgHead);
        }
        //显示角标图片
        String tagImg=designerBean.getTagimg();
        holder.imgTag.setTag(R.id.imageid2,tagImg);
        if(holder.imgTag.getTag(R.id.imageid2)!=null && tagImg==holder.imgTag.getTag(R.id.imageid2)){
            Glide.with(context).load(tagImg).centerCrop().into(holder.imgTag);
        }

        holder.tvName.setText(designerBean.getName());
        String year="从业<font color=\"#000000\"><strong>"+designerBean.getWorkingyear()+"</strong></font>年-<font color=\"#000000\"><strong>"+designerBean.getCasecount()+"</strong></font>套作品";
        holder.tvTime.setText(Html.fromHtml(year));
        holder.tvTitle.setText(designerBean.getDlevel());
        holder.tvType.setText(designerBean.getDtype());

        //擅长风格
        DesignerTagAdapter  designerTagAdapter=new DesignerTagAdapter(context,designerBean.getDstyle());
        holder.listTag.setAdapter(designerTagAdapter);

        /**
         * 取消关注
         */
        holder.tvCancle.setTag(designerBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                cancleFocus.cancleFocus((Integer) v.getTag());
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
        @BindView(R.id.img_tag)
        ImageView imgTag;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_type)
        TextView tvType;
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
