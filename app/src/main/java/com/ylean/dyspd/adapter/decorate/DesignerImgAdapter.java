package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.bean.DesignerList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class DesignerImgAdapter extends BaseAdapter {

    private Context context;
    private List<DesignerList.CaseImg> list;
    public DesignerImgAdapter(Context context,List<DesignerList.CaseImg> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_designer_img, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final DesignerList.CaseImg caseImg=list.get(position);
        //显示图片
        String imgUrl=caseImg.getCommonvalue();
        holder.imgPhoto.setTag(R.id.imageid,imgUrl);
        if(holder.imgPhoto.getTag(R.id.imageid)!=null && imgUrl==holder.imgPhoto.getTag(R.id.imageid)){
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgPhoto);
        }
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.img_photo)
        ImageView imgPhoto;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
