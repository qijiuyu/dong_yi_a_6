package com.ylean.dyspd.adapter.decorate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Area;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 热装楼盘adapter
 */
public class ScreeningHotAdapter extends BaseAdapter {

    private Activity activity;
    private List<Area.AreaBean> list;
    //选中的下标
    private int index;
    public ScreeningHotAdapter(Activity activity,List<Area.AreaBean> list) {
        super();
        this.activity = activity;
        this.list=list;
        Area.AreaBean areaBean=new Area.AreaBean();
        areaBean.setText("全部区域");
        this.list.add(0,areaBean);
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
            view = LayoutInflater.from(activity).inflate(R.layout.item_screening_hot, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Area.AreaBean areaBean=list.get(position);
        holder.tvName.setText(areaBean.getText());
        if(index==position){
            holder.imgOk.setVisibility(View.VISIBLE);
            holder.tvName.setTextColor(activity.getResources().getColor(R.color.color_b09b7c));
        }else{
            holder.imgOk.setVisibility(View.GONE);
            holder.tvName.setTextColor(activity.getResources().getColor(android.R.color.black));
        }
        holder.relSelect.setTag(position);
        holder.relSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                index= (int) v.getTag();
                notifyDataSetChanged();
            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.rel_select)
        RelativeLayout relSelect;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_ok)
        ImageView imgOk;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    /**
     * 获取选中的区域名称
     * @return
     */
    public String getSelectArea(){
        if(list==null || list.size()==0){
            return null;
        }
        return list.get(index).getText();
    }
}
