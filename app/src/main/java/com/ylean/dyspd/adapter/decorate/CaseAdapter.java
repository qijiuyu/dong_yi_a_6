package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
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
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.CaseList;
import com.zxdc.utils.library.bean.ConstructionList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class CaseAdapter extends BaseAdapter {

    private Context context;
    private List<CaseList.CaseListBean> list;
    public CaseAdapter(Context context,List<CaseList.CaseListBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_cast, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final CaseList.CaseListBean caseListBean=list.get(position);
        //显示设计师头像
        String headUrl=caseListBean.getDesignerimg();
        holder.imgHead.setTag(R.id.imageid2,headUrl);
        if(holder.imgHead.getTag(R.id.imageid2)!=null && headUrl==holder.imgHead.getTag(R.id.imageid2)){
            Glide.with(context).load(headUrl).into(holder.imgHead);
        }
        //显示案例图片
        String imgUrl=caseListBean.getImg();
        holder.imgPhoto.setTag(R.id.imageid,imgUrl);
        if(holder.imgPhoto.getTag(R.id.imageid)!=null && imgUrl==holder.imgPhoto.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgPhoto);
        }
        holder.tvName.setText(caseListBean.getDesignername());
        holder.tvTitle.setText(caseListBean.getName());
        holder.tvLevel.setText(caseListBean.getDesignerlevel());
        holder.tvDes.setText(caseListBean.getCityname()+" | "+caseListBean.getLoupanname()+" | "+caseListBean.getDstyle()+" | "+caseListBean.getSquare());

        /**
         * 进入详情页面
         */
        holder.linCase.setTag(caseListBean);
        holder.linCase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                CaseList.CaseListBean caseListBean= (CaseList.CaseListBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",12);
                intent.putExtra("id",caseListBean.getId());
                intent.putExtra("title",caseListBean.getName());
                context.startActivity(intent);

                //埋点
                MobclickAgent.onEvent(context, "case_list_cover");
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.lin_case)
        LinearLayout linCase;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_level)
        TextView tvLevel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
