package com.ylean.dyspd.activity.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.search.SearchSoftActivity;
import com.ylean.dyspd.adapter.decorate.SoftLoadingAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.SoftLoadingList;
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
 * 软装范本
 * Created by Administrator on 2019/11/9.
 */

public class SoftLoadingActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.img_new)
    ImageView imgNew;
    @BindView(R.id.img_sentiment)
    ImageView imgSentiment;
    @BindView(R.id.tv_screening)
    TextView tvScreening;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_sentiment)
    TextView tvSentiment;
    private SoftLoadingAdapter softLoadingAdapter;
    /**
     * 最新和人气排序
     * 1：升序
     * 2：降序
     * 3：默认
     */
    private int newSort = 2, sentimentSort = 3;
    private String ordertype,sorttype;
    /**
     * 筛选：风格，面积，户型
     */
    private String dstyle, housearea, housetype;
    //页数
    private int page = 1;
    private List<SoftLoadingList.SoftLoadingBean> listAll = new ArrayList<>();
    //排序好的集合
    private List<Sort> sortList = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_list);
        ButterKnife.bind(this);
        initView();
        //按照排序查找
        sortSelect(0);
        //获取软装范本列表
        getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView() {
        reList.setMyRefreshLayoutListener(this);
        softLoadingAdapter = new SoftLoadingAdapter(this, listAll);
        listView.setAdapter(softLoadingAdapter);
        listView.setDivider(null);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_search, R.id.rel_new, R.id.rel_sentiment, R.id.tv_screening})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();

                //埋点
                MobclickAgent.onEvent(this, "soft_list_back");
                break;
            case R.id.img_search:
                setClass(SearchSoftActivity.class);
                break;
            //最新
            case R.id.rel_new:
                if (newSort == 1) {
                    newSort = 2;
                    imgNew.setImageResource(R.mipmap.click_bottom);
                } else {
                    newSort = 1;
                    imgNew.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(0);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "soft_list_news");
                break;
            //人气
            case R.id.rel_sentiment:
                if (sentimentSort == 1 || sentimentSort == 3) {
                    sentimentSort = 2;
                    imgSentiment.setImageResource(R.mipmap.click_bottom);
                } else {
                    sentimentSort = 1;
                    imgSentiment.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(1);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "soft_list_sentiment");
                break;
            //筛选
            case R.id.tv_screening:
                setClass(ScreeningSoftActivity.class, 100);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_SOFT_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((SoftLoadingList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_SOFT_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((SoftLoadingList) msg.obj);
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
     * 刷新界面数据
     */
    private void refresh(SoftLoadingList softLoadingList) {
        if (softLoadingList == null) {
            return;
        }
        if (softLoadingList.isSussess()) {
            List<SoftLoadingList.SoftLoadingBean> list = softLoadingList.getData();
            listAll.addAll(list);
            softLoadingAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            if (listAll.size() == 0) {
                tvNo.setVisibility(View.VISIBLE);
            } else {
                tvNo.setVisibility(View.GONE);
            }
        } else {
            ToastUtil.showLong(softLoadingList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            dstyle = data.getStringExtra("styleName");
            housearea = data.getStringExtra("areaName");
            housetype = data.getStringExtra("modelName");
            //刷新列表
            onRefresh(null);

            //埋点
            MobclickAgent.onEvent(this, "soft_list_screening");
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
        tvList.add(tvNew);
        tvList.add(tvSentiment);
        //更新选中的标题颜色
        for (int i = 0; i < tvList.size(); i++) {
            TextView tv = tvList.get(i);
            if (position == i) {
                tv.setTextColor(getResources().getColor(R.color.color_b09b7c));
            } else {
                tv.setTextColor(getResources().getColor(android.R.color.black));
            }
        }

        //获取排序的json数据
        sortList.clear();
        sortList.add(new Sort(1, newSort));
        sortList.add(new Sort(2, sentimentSort == 3 ? 2 : sentimentSort));
        for (int i = 0; i < sortList.size(); i++) {
            if (i == position) {
                Sort sort = sortList.get(i);
                sortList.remove(i);
                sortList.add(0, sort);
                break;
            }
        }


        StringBuffer filed=new StringBuffer();
        StringBuffer sort=new StringBuffer();
        for (int i=0;i<sortList.size();i++){
            filed.append(sortList.get(i).getFiled()+",");
            sort.append(sortList.get(i).getSort()+",");
        }
        ordertype=filed.substring(0, filed.length()-1);
        sorttype=sort.substring(0, sort.length()-1);
    }


    /**
     * 获取软装范本列表
     */
    private void getSoftList(int index) {
        HttpMethod.getSoftList(dstyle, housearea, housetype, null, String.valueOf(page), ordertype,sorttype, index, handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }

}
