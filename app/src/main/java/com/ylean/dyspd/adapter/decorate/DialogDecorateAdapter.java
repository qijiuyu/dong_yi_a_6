package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.DecorateType;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 商品列表adapter
 */
public class DialogDecorateAdapter extends BaseAdapter {

    private Context context;
    private List<DecorateType.TypeBean> list;
    //选中的id
    private int cid;
    public DialogDecorateAdapter(Context context, List<DecorateType.TypeBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_dialog_decorate, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DecorateType.TypeBean typeBean=list.get(position);
        holder.tvName.setText(typeBean.getCommonvalue());
        if(cid==typeBean.getCommonid()){
            holder.imgOk.setVisibility(View.VISIBLE);
        }else{
            holder.imgOk.setVisibility(View.GONE);
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.img_ok)
        ImageView imgOk;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setId(int cid){
        this.cid=cid;
        notifyDataSetChanged();
    }
}
