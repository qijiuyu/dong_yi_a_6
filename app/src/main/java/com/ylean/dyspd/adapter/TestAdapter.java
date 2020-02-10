package com.ylean.dyspd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.dyspd.R;

import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class TestAdapter extends BaseAdapter {

    private Context context;

    public TestAdapter(Context context) {
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return 35;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.aaaaa, null);
        TextView tvNum=view.findViewById(R.id.tv_num);
        tvNum.setText(position+"");
        view.setTag(position);
        return view;
    }


}
