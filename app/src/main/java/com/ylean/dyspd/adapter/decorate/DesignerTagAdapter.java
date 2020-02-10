package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.dyspd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class DesignerTagAdapter extends BaseAdapter {

    private Context context;
    private String[] tags;
    public DesignerTagAdapter(Context context,String dstyle) {
        super();
        this.context = context;
        if(!TextUtils.isEmpty(dstyle)){
            tags=dstyle.split(",");
        }
    }

    @Override
    public int getCount() {
        return tags==null ? 0 : tags.length;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_designer_tag, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tvTag.setText(tags[position]);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
