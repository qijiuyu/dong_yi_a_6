package com.ylean.dyspd.activity.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.search.SearchDesignerActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.adapter.decorate.DesignerListAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.ChiefDesigner;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.bean.Sort;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设计师列表页
 * Created by Administrator on 2019/11/8.
 */

public class DesignerListActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.tv_screening)
    TextView tvScreening;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.img_year)
    ImageView imgYear;
    @BindView(R.id.img_sentiment)
    ImageView imgSentiment;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_sentiment)
    TextView tvSentiment;
    private DesignerListAdapter designerListAdapter;
    /**
     * 工作年限和人气排序
     * 1：升序
     * 2：降序
     * 3：默认
     */
    private int yearSort = 2, sentimentSort = 3;
    /**
     * 筛选：风格，门店id ，户型 ， 设计师类型
     */
    private String style, shopid, modelName, designerType;
    //页数
    private int page = 1;
    private List<DesignerList.DesignerBean> listAll = new ArrayList<>();
    private View headView;
    //排序好的集合
    private List<Sort> sortList = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_list);
        ButterKnife.bind(this);
        initView();
        //按照排序查找
        sortSelect(0);
        //获取首席设计师
        getChiefDegister();
        //获取设计师列表
        getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView() {
        reList.setMyRefreshLayoutListener(this);
        designerListAdapter = new DesignerListAdapter(this, listAll);
        listView.setAdapter(designerListAdapter);
        listView.setDivider(null);
    }

    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_search, R.id.lin_year, R.id.lin_sentiment, R.id.tv_screening})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //搜索
            case R.id.img_search:
                setClass(SearchDesignerActivity.class);
                break;
            //工作年限
            case R.id.lin_year:
                if (yearSort == 1) {
                    yearSort = 2;
                    imgYear.setImageResource(R.mipmap.click_bottom);
                } else {
                    yearSort = 1;
                    imgYear.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(0);
                //刷新列表
                onRefresh(null);
                break;
            //人气
            case R.id.lin_sentiment:
                if (sentimentSort == 1 || sentimentSort == 3) {
                    sentimentSort = 2;
                    imgSentiment.setImageResource(R.mipmap.click_bottom);
                }else{
                    sentimentSort = 1;
                    imgSentiment.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(1);
                //刷新列表
                onRefresh(null);
                break;
            //筛选
            case R.id.tv_screening:
                setClass(ScreeningDesignerActivity.class, 100);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //获取首席设计师
                case HandlerConstant.GET_CHIEF_DEGISTER_SUCCESS:
                    final ChiefDesigner chiefDesigner = (ChiefDesigner) msg.obj;
                    if (chiefDesigner == null) {
                        break;
                    }
                    if (chiefDesigner.isSussess()) {
                        //显示首席设计师
                        showHeadImg(chiefDesigner.getData());
                    }
                    break;
                //下刷
                case HandlerConstant.GET_DESIGNER_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((DesignerList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_DESIGNER_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((DesignerList) msg.obj);
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 显示首席设计师
     *
     * @param designerBean
     */
    private void showHeadImg(final ChiefDesigner.DesignerBean designerBean) {
        if (designerBean == null || TextUtils.isEmpty(designerBean.getCommonvalue())) {
            return;
        }
        headView = LayoutInflater.from(this).inflate(R.layout.chief_img, null);
        ImageView imgChief = headView.findViewById(R.id.img_chief);
        Glide.with(DesignerListActivity.this).load(designerBean.getCommonvalue()).centerCrop().into(imgChief);
        listView.addHeaderView(headView);
        /**
         * 进入首席设计师详情页面
         */
        imgChief.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(DesignerListActivity.this, DecorateWebView.class);
                intent.putExtra("type", 9);
                intent.putExtra("id", designerBean.getCommonid());
                intent.putExtra("title", designerBean.getCommonvalue());
                startActivity(intent);
            }
        });
    }


    /**
     * 刷新界面数据
     */
    private void refresh(DesignerList designerList) {
        if (designerList == null) {
            return;
        }
        if (designerList.isSussess()) {
            List<DesignerList.DesignerBean> list = designerList.getData();
            listAll.addAll(list);
            designerListAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            if (listAll.size() == 0) {
                tvNo.setVisibility(View.VISIBLE);
            } else {
                tvNo.setVisibility(View.GONE);
            }
        } else {
            ToastUtil.showLong(designerList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            style = data.getStringExtra("styleName");
            shopid = data.getStringExtra("storeId");
            modelName = data.getStringExtra("modelName");
            designerType = data.getStringExtra("designerType");
            //刷新列表
            onRefresh(null);
        }
    }


    /**
     * 按照排序查找
     *
     * @param position
     */
    private void sortSelect(int position) {
        //排序的标题
        List<TextView> tvList = new ArrayList<>();
        tvList.add(tvYear);
        tvList.add(tvSentiment);
        //更新选中的标题颜色
        for (int i=0;i<tvList.size();i++){
            TextView tv=tvList.get(i);
            if(position==i){
                tv.setTextColor(getResources().getColor(R.color.color_b09b7c));
            }else{
                tv.setTextColor(getResources().getColor(android.R.color.black));
            }
        }

        //获取排序的json数据
        sortList.clear();
        sortList.add(new Sort(1, yearSort));
        sortList.add(new Sort(2, sentimentSort==3 ? 2 : sentimentSort));
        for (int i=0;i<sortList.size();i++){
            if(i==position){
                Sort sort=sortList.get(i);
                sortList.remove(i);
                sortList.add(0,sort);
                break;
            }
        }
    }


    /**
     * 获取首席设计师
     */
    private void getChiefDegister() {
        HttpMethod.getChiefDegister(handler);
    }


    /**
     * 获取设计师列表
     */
    private void getDesignerList(int index) {
        HttpMethod.getDesignerList(designerType, modelName, null, String.valueOf(page), shopid, SPUtil.gson.toJson(sortList), style, index, handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
