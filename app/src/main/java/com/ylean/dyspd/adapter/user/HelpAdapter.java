package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.Help;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpAdapter extends BaseAdapter {

    private Context context;
    private List<Help.HelpBean> list;

    public HelpAdapter(Context context, List<Help.HelpBean> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_help, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Help.HelpBean helpBean = list.get(position);
        if(helpBean==null){
            return  view;
        }
        holder.tvName.setText(helpBean.getCommonvalue());
        holder.tvName.setTag(helpBean.getCommonid());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                int id= (int) v.getTag();
                Intent intent=new Intent(context, WebViewActivity.class);
                intent.putExtra("type",10);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        });
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
