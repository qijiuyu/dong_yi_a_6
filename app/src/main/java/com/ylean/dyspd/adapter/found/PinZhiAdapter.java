package com.ylean.dyspd.adapter.found;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Pinzhi;
import com.zxdc.utils.library.view.OvalImage2Views;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinZhiAdapter extends BaseAdapter {

    private Context context;
    private List<Pinzhi.PinzhiBean> list;

    public PinZhiAdapter(Context context, List<Pinzhi.PinzhiBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_pinzhi, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Pinzhi.PinzhiBean pinzhiBean = list.get(position);
        //显示图片
        String imgUrl = pinzhiBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        String title=pinzhiBean.getTitle();
        holder.tvTitle.setText(title.substring(0,2));
        holder.tvTitle2.setText(title.substring(2,title.length()));
        holder.tvContent.setText(Html.fromHtml(pinzhiBean.getSubtitle()));
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        OvalImage2Views imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_title2)
        TextView tvTitle2;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.lin_more)
        LinearLayout linMore;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
