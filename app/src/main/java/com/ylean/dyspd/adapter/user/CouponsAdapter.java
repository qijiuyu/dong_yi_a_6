package com.ylean.dyspd.adapter.user;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Coupons;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.Util;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券
 */
public class CouponsAdapter extends BaseAdapter {

    private Activity activity;
    private List<Coupons.CouponsBean> list;
    public CouponsAdapter(Activity activity,List<Coupons.CouponsBean> list) {
        super();
        this.activity = activity;
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
            view = LayoutInflater.from(activity).inflate(R.layout.item_coupons, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Coupons.CouponsBean couponsBean=list.get(position);
        //显示图片
        String imgUrl=couponsBean.getImgurl();
        holder.imgHead.setTag(R.id.imageid,imgUrl);
        if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
            Glide.with(activity).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvCode.setText("编码："+couponsBean.getNum());
        holder.tvTime.setText("使用期限："+couponsBean.getStartdate()+" 至 "+couponsBean.getEnddate());

        //复制编号
        holder.tvCopy.setTag(couponsBean.getNum());
        holder.tvCopy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                Util.copyTxt(v.getTag().toString());
            }
        });

        //使用规则
        holder.tvRules.setTag(couponsBean.getRemark());
        holder.tvRules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                showRulesDialog(v.getTag().toString());
            }
        });
        return view;
    }


    /**
     * 显示规则弹框
     */
    private void showRulesDialog(String remark){
        View view= LayoutInflater.from(activity).inflate(R.layout.dialog_gift_rules,null);
        DialogUtil.getDialog(activity,view);
        TextView tvRemark=view.findViewById(R.id.tv_remark);
        tvRemark.setText(Html.fromHtml(remark));
    }


    static class ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_copy)
        TextView tvCopy;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_rules)
        TextView tvRules;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
