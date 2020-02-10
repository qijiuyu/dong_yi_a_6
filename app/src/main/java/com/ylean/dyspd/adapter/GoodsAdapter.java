package com.ylean.dyspd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class GoodsAdapter extends BaseAdapter {

    private Context context;

    public GoodsAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
//            view = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;
    }


    static class ViewHolder {
//        @BindView(R.id.tv_name)
//        TextView tvName;
//        @BindView(R.id.tv_num)
//        TextView tvNum;
//        @BindView(R.id.tv_money)
//        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
