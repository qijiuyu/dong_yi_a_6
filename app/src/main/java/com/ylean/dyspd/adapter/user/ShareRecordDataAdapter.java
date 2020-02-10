package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.ShareList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分享记录
 */
public class ShareRecordDataAdapter extends BaseAdapter {

    private Context context;
    private List<ShareList.ShareData> list;
    public ShareRecordDataAdapter(Context context,List<ShareList.ShareData> list) {
        super();
        this.context = context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_share_record_data, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ShareList.ShareData shareData=list.get(position);

        String title=shareData.getSharetype()+"<font color=\"#000000\">" + shareData.getSharename()+"" + "</font>"+shareData.getSharetitle();
        holder.tvTitle.setText(Html.fromHtml(title));
        holder.tvTime.setText(shareData.getDatehour());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
