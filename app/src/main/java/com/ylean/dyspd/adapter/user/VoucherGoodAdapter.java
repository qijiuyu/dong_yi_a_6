package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.GoodDetailsActivity;
import com.ylean.dyspd.fragment.user.VoucherGoodFragment;
import com.zxdc.utils.library.bean.GiftData;
import com.zxdc.utils.library.util.Util;
import java.util.List;
/**
 * 兑换礼品
 */
public class VoucherGoodAdapter extends RecyclerView.Adapter<VoucherGoodAdapter.MyHolder> {

    private Context context;
    private List<GiftData.GiftDataBean> list;
    private VoucherGoodFragment.OnItemClickListener onItemClickListener;
    public VoucherGoodAdapter(Context context,List<GiftData.GiftDataBean> list, VoucherGoodFragment.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list=list;
        this.onItemClickListener=onItemClickListener;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_voucher_good, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        final GiftData.GiftDataBean giftDataBean=list.get(i);
        if(giftDataBean==null){
            return;
        }
        //显示图片
        String imgUrl=giftDataBean.getImg();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(giftDataBean.getName());
        holder.tvMoney.setText("¥"+Util.setDouble(giftDataBean.getPrice(),1));
        holder.tvMoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        /**
         * 兑换商品
         */
        holder.tvVoucher.setTag(giftDataBean);
        holder.tvVoucher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                onItemClickListener.onItemClick((GiftData.GiftDataBean) v.getTag());
            }
        });


        /**
         * 进入商品详情
         */
        holder.linClick.setTag(giftDataBean.getId());
        holder.linClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                int id= (int) v.getTag();
                Intent intent=new Intent(context, GoodDetailsActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout linClick;
        ImageView imgHead;
        TextView tvName,tvMoney,tvVoucher;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linClick=itemView.findViewById(R.id.lin_click);
            imgHead=itemView.findViewById(R.id.img_head);
            tvName=itemView.findViewById(R.id.tv_name);
            tvMoney=itemView.findViewById(R.id.tv_money);
            tvVoucher=itemView.findViewById(R.id.tv_voucher);
        }
    }
}

