package com.ylean.dyspd.adapter.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.SortDataAdapter;
import com.ylean.dyspd.utils.pinyin.SortModel;
import com.zxdc.utils.library.bean.City;
import com.zxdc.utils.library.view.MeasureListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortAdapter extends BaseAdapter {
    private Activity activity;
    private List<City.CityBean> list = null;

    public SortAdapter(Activity activity, List<City.CityBean> list) {
        this.activity = activity;
        this.list = list;
    }

    public int getCount() {
        return list==null ? 0 : list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.item_city, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        City.CityBean cityBean=list.get(position);
        holder.catalog.setText(cityBean.getCode());
        SortDataAdapter sortDataAdapter=new SortDataAdapter(activity,cityBean.getCitylist());
        holder.listView.setAdapter(sortDataAdapter);
        return view;

    }


    static class ViewHolder {
        @BindView(R.id.catalog)
        TextView catalog;
        @BindView(R.id.listView)
        MeasureListView listView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
