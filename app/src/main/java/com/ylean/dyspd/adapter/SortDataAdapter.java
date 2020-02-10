package com.ylean.dyspd.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.main.SelectCityActivity;
import com.zxdc.utils.library.bean.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortDataAdapter extends BaseAdapter {
    private Activity activity;
    private List<City.CityList> list = null;

    public SortDataAdapter(Activity activity, List<City.CityList> list) {
        this.activity = activity;
        this.list = list;
    }

    public int getCount() {
        return list == null ? 0 : list.size();
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
            view = LayoutInflater.from(activity).inflate(R.layout.item_city_data, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        City.CityList cityList = list.get(position);
        holder.title.setText(cityList.getName());
        /**
         * 点击选择城市
         */
        holder.title.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String city= ((TextView)v).getText().toString().trim();
                ((SelectCityActivity)activity).selectCity(city);
            }
        });
        return view;

    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
