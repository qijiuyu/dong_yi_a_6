package com.ylean.dyspd.adapter.user;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.collection.CollDecorateActivity;
import com.ylean.dyspd.activity.user.collection.CollectionBuildingActivity;
import com.ylean.dyspd.activity.user.collection.CollectionCaseActivity;
import com.ylean.dyspd.activity.user.collection.CollectionConstructionActivity;
import com.ylean.dyspd.activity.user.collection.CollectionDesignerActivity;
import com.ylean.dyspd.activity.user.collection.CollectionGalleryActivity;
import com.ylean.dyspd.activity.user.collection.CollectionNearActivity;
import com.ylean.dyspd.activity.user.collection.CollectionSoftActivity;
import com.ylean.dyspd.activity.user.collection.CollectionVRActivity;

import java.util.List;

/**
 * 我的收藏
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyHolder> {

    private Context context;
    private List<String> nameList;
    private List<Integer> imgList;
    private List<Integer> numList;
    public CollectionAdapter(Context context, List<String> nameList, List<Integer> imgList,List<Integer> numList) {
        this.context = context;
        this.nameList=nameList;
        this.imgList=imgList;
        this.numList=numList;
    }

    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_collection, viewGroup,false);
        MyHolder holder = new MyHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder =myHolder;
        holder.tvName.setText(nameList.get(i));
        holder.imgHead.setImageDrawable(context.getResources().getDrawable(imgList.get(i)));
        if(numList.size()==9){
            holder.tvNum.setText(String.valueOf(numList.get(i)));
        }
        holder.linCollection.setTag(i);
        holder.linCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final int index=(Integer)v.getTag();
                Intent intent=new Intent();
                switch (index){
                    case 0:
                         intent.setClass(context, CollectionCaseActivity.class);
                         break;
                    case 1:
                        intent.setClass(context, CollectionDesignerActivity.class);
                        break;
                    case 2:
                        intent.setClass(context, CollectionGalleryActivity.class);
                        break;
                    case 3:
                        intent.setClass(context, CollectionVRActivity.class);
                        break;
                    case 4:
                        intent.setClass(context, CollectionSoftActivity.class);
                        break;
                    case 5:
                        intent.setClass(context, CollectionConstructionActivity.class);
                        break;
                    case 6:
                        intent.setClass(context, CollectionBuildingActivity.class);
                        break;
                    case 7:
                        intent.setClass(context, CollectionNearActivity.class);
                        break;
                    case 8:
                        intent.setClass(context, CollDecorateActivity.class);
                        break;
                    default:
                        break;
                }
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private LinearLayout linCollection;
        private ImageView imgHead;
        TextView tvName,tvNum;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            linCollection=itemView.findViewById(R.id.lin_collection);
            imgHead=itemView.findViewById(R.id.img_head);
            tvName=itemView.findViewById(R.id.tv_name);
            tvNum=itemView.findViewById(R.id.tv_num);
        }
    }
}

