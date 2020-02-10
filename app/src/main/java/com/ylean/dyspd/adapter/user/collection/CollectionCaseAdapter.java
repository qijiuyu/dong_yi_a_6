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
import com.ylean.dyspd.activity.user.collection.CollectionCaseActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.CaseList;
import com.zxdc.utils.library.bean.ConstructionList;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CollectionCaseAdapter extends BaseAdapter {

    private Context context;
    private List<CaseList.CaseListBean> list;
    public CollectionCaseAdapter(Context context,List<CaseList.CaseListBean> list) {
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

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_details, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final CaseList.CaseListBean caseListBean=list.get(position);
        //显示案例图片
        String imgUrl=caseListBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(caseListBean.getName());
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append(caseListBean.getCityname()+" | ");
        if(!TextUtils.isEmpty(caseListBean.getLoupanname())){
            stringBuffer.append(caseListBean.getLoupanname()+" | ");
        }
        stringBuffer.append(caseListBean.getDstyle()+" |"+caseListBean.getSquare());
        holder.tvDes.setText(stringBuffer.toString());

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(caseListBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                ((CollectionCaseActivity)context).cancleColl((Integer) v.getTag());
            }
        });

        /**
         *
         * 进入详情页面
         */
        holder.relContent.setTag(caseListBean);
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                CaseList.CaseListBean caseListBean= (CaseList.CaseListBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",12);
                intent.putExtra("id",caseListBean.getCaseid());
                intent.putExtra("title",caseListBean.getName());
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
