package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.ShareList;
import com.zxdc.utils.library.view.MeasureListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分享记录
 */
public class ShareRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ShareList.ShareYear> list;

    public ShareRecordAdapter(Context context, List<ShareList.ShareYear> list) {
        super();
        this.context = context;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_share_record, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ShareList.ShareYear shareYear = list.get(position);
        holder.tvYear.setText(shareYear.getDateyear());

        ShareRecordListAdapter shareRecordListAdapter = new ShareRecordListAdapter(context,shareYear.getList());
        holder.listView.setAdapter(shareRecordListAdapter);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_year)
        TextView tvYear;
        @BindView(R.id.listView)
        MeasureListView listView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
