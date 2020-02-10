package com.ylean.dyspd.adapter.main;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.MainCase;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品列表adapter
 */
public class MainTypeAdapter extends RecyclerView.Adapter<MainTypeAdapter.MyHolder> {

    private Context context;
    private List<MainCase.MainCaseBean> list;
    //选中的item
    private int index;
    private OnItemClickListener onItemClickListener;
    public MainTypeAdapter(Context context,List<MainCase.MainCaseBean> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list=list;
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(MainCase.MainCaseBean mainCaseBean);
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_main_type, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder =myHolder;
        final MainCase.MainCaseBean mainCaseBean=list.get(i);
        holder.tvName.setText(mainCaseBean.getStyle());
        if(i==index){
            holder.tvName.setTextSize(15);
            holder.viewLine.setVisibility(View.VISIBLE);
            holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }else{
            holder.tvName.setTextSize(13);
            holder.viewLine.setVisibility(View.GONE);
            holder.tvName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        }
        holder.tvName.setTag(i);
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                index= (int) v.getTag();
                notifyDataSetChanged();
                onItemClickListener.onItemClick(list.get(index));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        View viewLine;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            viewLine=itemView.findViewById(R.id.view_line);
        }
    }
}

