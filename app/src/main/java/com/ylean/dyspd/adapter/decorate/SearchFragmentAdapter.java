package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.bean.TotalSearch;
import com.zxdc.utils.library.view.MeasureListView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * SearchActivity里面的
 */
public class SearchFragmentAdapter extends BaseAdapter {

    private Context context;
    private List<TotalSearch.TotalSearchBean> list;
    public SearchFragmentAdapter(Context context,List<TotalSearch.TotalSearchBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final TotalSearch.TotalSearchBean totalSearchBean=list.get(position);
        holder.tvTitleName.setText(totalSearchBean.getName());

        //展示列表
        SearchFragmentDataAdapter searchFragmentDataAdapter=new SearchFragmentDataAdapter(context,totalSearchBean.getList(),totalSearchBean.getIndex());
        holder.listView.setAdapter(searchFragmentDataAdapter);
        holder.listView.setDivider(null);

        /**
         * 点击更多
         */
        holder.tvMore.setTag(totalSearchBean.getIndex());
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                final int index= (int) v.getTag();
                EventBus.getDefault().post(new EventBusType(EventStatus.SELECT_SEARCH_LIST,index));

            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.tv_title_name)
        TextView tvTitleName;
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.listView)
        MeasureListView listView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
