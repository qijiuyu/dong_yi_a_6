package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.moxun.tagcloudlib.view.TagsAdapter;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.Hobby;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
public class FocusStyleAdapter extends TagsAdapter {

    private Context context;
    private List<Hobby.HobbyBean> list;
    public Map<String,String> map=new HashMap<>();
    public FocusStyleAdapter(Context context,List<Hobby.HobbyBean> list,String message){
        this.context=context;
        this.list=list;
        if(!TextUtils.isEmpty(message)){
            String[] caseStr=message.split(",");
            for (int i=0,len=caseStr.length;i<len;i++){
                  map.put(caseStr[i],caseStr[i]);
            }
        }
    }

    @Override
    public int getCount() {
        return list==null ? 0 : list.size();
    }

    ViewHolder holder = null;
    public View getView(Context context, int position, ViewGroup parent) {
        View view=null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_focus_style, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if(position/1==0){
            holder.tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_shape));
        }else if(position/2==0){
            holder.tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_shape2));
        }else if(position/3==0){
            holder.tvTag.setBackground(context.getResources().getDrawable(R.drawable.bg_shape3));
        }
        final Hobby.HobbyBean hobbyBean=list.get(position);
        String name=hobbyBean.getName();
        holder.tvTag.setText(name);
        if(map.get(name)!=null){
            holder.imgRed.setVisibility(View.VISIBLE);
        }else{
            holder.imgRed.setVisibility(View.GONE);
        }

        holder.imgRed.setTag(name);
        holder.tvTag.setTag(holder.imgRed);
        holder.tvTag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                ImageView imgRed= (ImageView) v.getTag();
                final String name= (String) imgRed.getTag();
                if(map.get(name)==null){
                    map.put(name,name);
                    imgRed.setVisibility(View.VISIBLE);
                }else{
                    map.remove(name);
                    imgRed.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position/7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }


    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.img_red)
        ImageView imgRed;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
