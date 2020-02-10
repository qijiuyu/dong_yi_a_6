package com.ylean.dyspd.adapter.init;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Screening;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomEntryAdapter extends BaseAdapter {

    private Activity activity;
    private List<Screening.ScreeningBean> list;

    public RoomEntryAdapter(Activity activity, List<Screening.ScreeningBean> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            view = LayoutInflater.from(activity).inflate(R.layout.item_roomentry, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Screening.ScreeningBean screeningBean = list.get(position);
        holder.tvName.setText(screeningBean.getName());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
