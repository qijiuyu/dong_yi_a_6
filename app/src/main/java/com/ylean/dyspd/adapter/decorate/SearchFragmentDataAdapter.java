package com.ylean.dyspd.adapter.decorate;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.TotalSearch;
import java.util.List;

/**
 * SearchListtActivity里面的
 */
public class SearchFragmentDataAdapter extends BaseAdapter {

    private Context context;
    private List<TotalSearch.SearchList> list;
    //第几个类型
    private int typeIndex;
    public SearchFragmentDataAdapter(Context context,List<TotalSearch.SearchList> list,int typeIndex) {
        super();
        this.context = context;
        this.list=list;
        this.typeIndex=typeIndex;
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
            holder = new ViewHolder();
            switch (typeIndex){
                //设计师
                case 0:
                     view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment_designer, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.imgStatus=view.findViewById(R.id.img_Status);
                     holder.tvName=view.findViewById(R.id.tv_name);
                     holder.tvYear=view.findViewById(R.id.tv_year);
                     holder.tvLevel=view.findViewById(R.id.tv_level);
                     holder.tvType=view.findViewById(R.id.tv_type);
                     break;
                //风格案例
                //软装范本
                //楼盘/小区
                case 1:
                case 2:
                case 3:
                     view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment_case, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.tvTitle=view.findViewById(R.id.tv_title);
                     holder.tvDes=view.findViewById(R.id.tv_des);
                     break;
                //在施工地
                case 4:
                     view = LayoutInflater.from(context).inflate(R.layout.item_construction, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.tvTitle=view.findViewById(R.id.tv_title);
                     holder.tvDes=view.findViewById(R.id.tv_des);
                     holder.tvType=view.findViewById(R.id.tv_type);
                     break;
                //效果图
                //VR样板房
                case 5:
                case 6:
                     view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment_gallery, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.tvName=view.findViewById(R.id.tv_name);
                     holder.tvType=view.findViewById(R.id.tv_type);
                     break;
                //体验店
                case 7:
                     view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment_near, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.tvName=view.findViewById(R.id.tv_name);
                     holder.tvDes=view.findViewById(R.id.tv_des);
                     break;
                //攻略
                case 8:
                     view = LayoutInflater.from(context).inflate(R.layout.item_search_fragment_decorate, null);
                     holder.imgHead=view.findViewById(R.id.img_head);
                     holder.tvName=view.findViewById(R.id.tv_name);
                     holder.tvType=view.findViewById(R.id.tv_type);
                     break;
                default:
                    break;
            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final TotalSearch.SearchList searchList=list.get(position);
        switch (typeIndex){
            //设计师
            case 0:
                 //显示图片
                 String imgUrl=searchList.getImg();
                 holder.imgHead.setTag(R.id.imageid,imgUrl);
                 if(holder.imgHead.getTag(R.id.imageid)!=null && imgUrl==holder.imgHead.getTag(R.id.imageid)){
                     Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
                 }
                 //显示角标图片
                 String tagImg=searchList.getTagimg();
                 holder.imgStatus.setTag(R.id.imageid,tagImg);
                 if(holder.imgStatus.getTag(R.id.imageid)!=null && tagImg==holder.imgStatus.getTag(R.id.imageid)){
                     Glide.with(context).load(tagImg).centerCrop().into(holder.imgStatus);
                 }
                 holder.tvName.setText(searchList.getName());
                 String year="从业<font color=\"#000000\"><strong>"+searchList.getWorkingyear()+"</strong></font>年-<font color=\"#000000\"><strong>"+searchList.getCasecount()+"</strong></font>套作品";
                 holder.tvYear.setText(Html.fromHtml(year));
                 holder.tvLevel.setText(searchList.getDlevel());
                 holder.tvType.setText(searchList.getDtype());
                 break;
            //风格案例
            case 1:
                 //显示图片
                 String caseImg=searchList.getImg();
                 holder.imgHead.setTag(R.id.imageid,caseImg);
                 if(holder.imgHead.getTag(R.id.imageid)!=null && caseImg==holder.imgHead.getTag(R.id.imageid)){
                    Glide.with(context).load(caseImg).centerCrop().into(holder.imgHead);
                 }
                 holder.tvTitle.setText(searchList.getName());
                 holder.tvDes.setText(searchList.getCityname()+" | "+searchList.getLoupanname()+" | "+searchList.getDstyle()+" | "+searchList.getSquare());
                 break;
            //软装范本
            case 2:
                //显示图片
                String softImg=searchList.getImg();
                holder.imgHead.setTag(R.id.imageid,softImg);
                if(holder.imgHead.getTag(R.id.imageid)!=null && softImg==holder.imgHead.getTag(R.id.imageid)){
                    Glide.with(context).load(softImg).centerCrop().into(holder.imgHead);
                }
                holder.tvTitle.setText(searchList.getName());
                holder.tvDes.setText(searchList.getCityname()+" | "+searchList.getDstyle()+" | "+searchList.getSquare());
                break;
            //楼盘小区
            case 3:
                //显示图片
                String buildingImg=searchList.getImg();
                holder.imgHead.setTag(R.id.imageid,buildingImg);
                if(holder.imgHead.getTag(R.id.imageid)!=null && buildingImg==holder.imgHead.getTag(R.id.imageid)){
                    Glide.with(context).load(buildingImg).centerCrop().into(holder.imgHead);
                }
                holder.tvTitle.setText(searchList.getName());
                holder.tvDes.setText("在施工地:"+searchList.getConstructioncount()+"户 | 户型解析:"+searchList.getAnalysiscount()+"户 | 相关案例:"+searchList.getCasecount()+"个 ");
                break;
            //在施工地
            case 4:
                 //显示图片
                 String consImg=searchList.getImg();
                 holder.imgHead.setTag(R.id.imageid,consImg);
                 if(holder.imgHead.getTag(R.id.imageid)!=null && consImg==holder.imgHead.getTag(R.id.imageid)){
                     Glide.with(context).load(consImg).centerCrop().into(holder.imgHead);
                 }
                 holder.tvTitle.setText(searchList.getName());
                 holder.tvDes.setText(searchList.getDistrict()+" · "+searchList.getLoupanname()+" · "+searchList.getSquare());
                 holder.tvType.setText(searchList.getStage());
                 break;
            //效果图
            //VR样板房
            case 5:
            case 6:
                 //显示图片
                 String galleryImg=searchList.getImg();
                 holder.imgHead.setTag(R.id.imageid,galleryImg);
                 if(holder.imgHead.getTag(R.id.imageid)!=null && galleryImg==holder.imgHead.getTag(R.id.imageid)){
                     Glide.with(context).load(galleryImg).centerCrop().into(holder.imgHead);
                 }
                 holder.tvName.setText(searchList.getName());
                 holder.tvType.setText(searchList.getHousespace());
                 break;
            //体验店
            case 7:
                 //显示图片
                 String nearImg=searchList.getImg();
                 holder.imgHead.setTag(R.id.imageid,nearImg);
                 if(holder.imgHead.getTag(R.id.imageid)!=null && nearImg==holder.imgHead.getTag(R.id.imageid)){
                     Glide.with(context).load(nearImg).centerCrop().into(holder.imgHead);
                 }
                 holder.tvName.setText(searchList.getName());
                 String des="设计案例：<font color=\"#000000\"><strong>"+searchList.getCasecount()+"</strong></font>套 | 装修工地：<font color=\"#000000\"><strong>"+searchList.getConstructioncount()+"</strong></font>";
                 holder.tvDes.setText(Html.fromHtml(des));
                break;
            //攻略
            case 8:
                //显示图片
                String decorateImg=searchList.getImg();
                holder.imgHead.setTag(R.id.imageid,decorateImg);
                if(holder.imgHead.getTag(R.id.imageid)!=null && decorateImg==holder.imgHead.getTag(R.id.imageid)){
                    Glide.with(context).load(decorateImg).centerCrop().into(holder.imgHead);
                }
                holder.tvName.setText(searchList.getName());
                holder.tvType.setText(searchList.getCname());
                break;
            default:
                break;
        }

        /**
         *进入详情页面
         */
        view.setTag(R.id.tag1,searchList);
        view.setTag(R.id.tag2,typeIndex);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag(R.id.tag1)==null || v.getTag(R.id.tag2)==null){
                    return;
                }
                TotalSearch.SearchList searchList= (TotalSearch.SearchList) v.getTag(R.id.tag1);
                int type=(int) v.getTag(R.id.tag2);
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("id",searchList.getId());
                intent.putExtra("title",searchList.getName());
                switch (type){
                    case 0:
                        intent.putExtra("type",8);
                         break;
                    case 1:
                        intent.putExtra("type",12);
                         break;
                    case 2:
                        intent.putExtra("type",7);
                        break;
                    case 3:
                        intent.putExtra("type",6);
                        break;
                    case 4:
                        intent.putExtra("type",5);
                        break;
                    case 5:
                        intent.putExtra("type",1);
                        break;
                    case 6:
                        intent.putExtra("type",2);
                        break;
                    case 7:
                        intent.putExtra("type",4);
                        break;
                    case 8:
                        intent.putExtra("type",3);
                        break;
                    default:
                        break;
                }
                context.startActivity(intent);
            }
        });
        return view;
    }


    private class ViewHolder{
        private ImageView imgHead,imgStatus;
        private TextView tvName,tvYear,tvLevel,tvType,tvTitle,tvDes;
    }

}
