package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Voucher;
import com.zxdc.utils.library.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 兑换券
 */
public class VoucherAdapter extends BaseAdapter {

    private Context context;
    private List<Voucher.VoucherBean> list;
    public VoucherAdapter(Context context,List<Voucher.VoucherBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_voucher, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Voucher.VoucherBean voucherBean=list.get(position);

        //显示图片
        String imgUrl=voucherBean.getSpuimg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }

        holder.tvName.setText(voucherBean.getSpuname());
        holder.tvMoney.setText(Util.setDouble(voucherBean.getSpuprice(),1));
        holder.tvMoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        holder.tvTime.setText("兑换时间："+voucherBean.getCreatedate());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
