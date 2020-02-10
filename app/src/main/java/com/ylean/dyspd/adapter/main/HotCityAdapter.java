package com.ylean.dyspd.adapter.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.main.SelectCityActivity;
import com.zxdc.utils.library.bean.HotCity;
import java.util.List;
/**
 * 热门城市
 */
public class HotCityAdapter extends RecyclerView.Adapter<HotCityAdapter.MyHolder> {

    private Activity activity;
    private List<HotCity.HotCityBean> list;
    public HotCityAdapter(Activity activity,List<HotCity.HotCityBean> list) {
        this.activity = activity;
        this.list=list;
    }

    public interface OnItemClickListener{
        void onItemClick(HotCity.HotCityBean hotCityBean);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.item_hot_city, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder =myHolder;
        final HotCity.HotCityBean hotCityBean=list.get(i);
        if(hotCityBean==null){
            return;
        }
        String city=hotCityBean.getCityname();
        holder.tvCity.setText(city);
        if(city.equals("全国所有城市")){
            holder.imgCity.setImageResource(R.mipmap.total_city);
        }else if(city.equals("我的附近")){
            holder.imgCity.setImageResource(R.mipmap.location_city);
        }else{
            Glide.with(activity).load(hotCityBean.getImg()).centerCrop().into(holder.imgCity);
        }
        holder.imgCity.setTag(city);
        holder.imgCity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                String city=v.getTag().toString();
                ((SelectCityActivity)activity).selectCity(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
       ImageView imgCity;
        TextView tvCity;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgCity=itemView.findViewById(R.id.img_city);
            tvCity=itemView.findViewById(R.id.tv_city);
        }
    }

}

