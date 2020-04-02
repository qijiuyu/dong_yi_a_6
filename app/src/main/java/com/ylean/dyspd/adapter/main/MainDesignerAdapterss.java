package com.ylean.dyspd.adapter.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDesignerAdapterss extends BaseAdapter {
    private Activity activity;

    public MainDesignerAdapterss(Activity activity) {
        this.activity = activity;
    }

    public int getCount() {
        return 10;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;
    public View getView(final int position, View view, ViewGroup arg2) {
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_main_designer, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        return view;

    }


    static class ViewHolder {
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_case_num)
        TextView tvCaseNum;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_tags)
        TextView tvTags;
        @BindView(R.id.rel_designer)
        RelativeLayout relDesigner;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
