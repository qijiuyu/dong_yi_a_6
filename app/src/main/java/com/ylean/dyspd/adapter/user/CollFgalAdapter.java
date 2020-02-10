package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.decorate.DesignerTagAdapter;
import com.zxdc.utils.library.view.HorizontalListView;

/**
 * 我的收藏
 */
public class CollFgalAdapter extends BaseAdapter {

    private Context context;
    /**
     * 0：风格案例
     * 1：设计师
     * 2：案例图库
     * 3：vr样板间
     * 4：软装范本
     * 5：在施工地
     * 6：热装楼盘
     * 7：体验门店
     * 8：咨询
     */
    private int type;
    private DesignerTagAdapter designerTagAdapter;
    public CollFgalAdapter(Context context,int type) {
        super();
        this.context = context;
        this.type=type;
    }

    @Override
    public int getCount() {
        return 10;
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
            holder = new ViewHolder();
            switch (type){
                case 0://风格案例
                case 4://软装范本
                case 6://热装楼盘
                case 7://体验门店
                    view = LayoutInflater.from(context).inflate(R.layout.item_coll_details, null);
                    holder.imgHead=view.findViewById(R.id.img_head);
                    holder.tvName=view.findViewById(R.id.tv_name);
                    holder.tvDes=view.findViewById(R.id.tv_des);
                    holder.tvCancle=view.findViewById(R.id.tv_cancle);
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_coll_designer, null);
                    holder.imgHead=view.findViewById(R.id.img_head);
                    holder.tvName=view.findViewById(R.id.tv_name);
                    holder.tvYear=view.findViewById(R.id.tv_year);
                    holder.tvTitle=view.findViewById(R.id.tv_title);
                    holder.tvType=view.findViewById(R.id.tv_type);
                    holder.listTag=view.findViewById(R.id.list_tag);
                    holder.tvCancle=view.findViewById(R.id.tv_cancle);
                    break;
                case 2:
                    view = LayoutInflater.from(context).inflate(R.layout.item_coll_photo, null);
                    holder.imgHead=view.findViewById(R.id.img_head);
                    holder.tvName=view.findViewById(R.id.tv_name);
                    holder.tvType=view.findViewById(R.id.tv_type);
                     break;
                case 5:
                    view = LayoutInflater.from(context).inflate(R.layout.item_coll_construction, null);
                    holder.imgHead=view.findViewById(R.id.img_head);
                    holder.tvName=view.findViewById(R.id.tv_name);
                    holder.tvDes=view.findViewById(R.id.tv_des);
                    holder.tvCancle=view.findViewById(R.id.tv_cancle);
                    holder.tvType=view.findViewById(R.id.tv_type);
                    break;
                default:
                    break;
            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        switch (type){
            case 0:
            case 4:
            case 6:
            case 7:
                break;
            case 1:
                break;
            //案例图库
            case 2:
                 break;
            case 5:
                break;
            default:
                break;
        }

        return view;
    }


    private class ViewHolder{
        ImageView imgHead;
        TextView tvName,tvDes,tvCancle,tvType,tvYear,tvTitle;
        HorizontalListView listTag;
    }
}

