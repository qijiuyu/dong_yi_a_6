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

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.search.SearchCaseActivity;
import com.ylean.dyspd.adapter.decorate.CaseAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.CaseList;
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
 * 风格案例
 * Created by Administrator on 2019/11/9.
 */

public class CaseListActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.tv_screening)
    TextView tvScreening;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.img_new)
    ImageView imgNew;
    @BindView(R.id.img_sentiment)
    ImageView imgSentiment;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.tv_sentiment)
    TextView tvSentiment;
    private CaseAdapter caseAdapter;
    /**
     * 最新和人气排序
     * 1：升序
     * 2：降序
     */
    private int newSort = 2, sentimentSort = 3;
    /**
     * 筛选：属性,风格,面积,户型
     */
    private String casetype, dstyle, housearea, housetype;
    //页数
    private int page = 1;
    private List<CaseList.CaseListBean> listAll = new ArrayList<>();
    //排序好的集合
    private List<Sort> sortList = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_list);
        ButterKnife.bind(this);
        initView();
        //按照排序查找
        sortSelect(0);
        //获取装修案例数据
        getCaseList(HandlerConstant.GET_CASE_LIST_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView() {
        reList.setMyRefreshLayoutListener(this);
        caseAdapter = new CaseAdapter(this, listAll);
        listView.setAdapter(caseAdapter);
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
                break;
            //搜索
            case R.id.img_search:
                setClass(SearchCaseActivity.class);
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
                break;
            //人气
            case R.id.rel_sentiment:
                if (sentimentSort == 1 || sentimentSort==3) {
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
                break;
            //筛选
            case R.id.tv_screening:
                setClass(ScreeningCaseActivity.class, 100);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_CASE_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((CaseList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_CASE_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((CaseList) msg.obj);
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
    private void refresh(CaseList caseList) {
        if (caseList == null) {
            return;
        }
        if (caseList.isSussess()) {
            List<CaseList.CaseListBean> list = caseList.getData();
            listAll.addAll(list);
            caseAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            if (listAll.size() == 0) {
                tvNo.setVisibility(View.VISIBLE);
            } else {
                tvNo.setVisibility(View.GONE);
            }
        } else {
            ToastUtil.showLong(caseList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getCaseList(HandlerConstant.GET_CASE_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getCaseList(HandlerConstant.GET_CASE_LIST_SUCCESS2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            casetype = data.getStringExtra("caseName");
            dstyle = data.getStringExtra("styleName");
            housearea = data.getStringExtra("areaName");
            housetype = data.getStringExtra("modelName");
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
        tvList.add(tvNew);
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
        sortList.add(new Sort(1, newSort));
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
     * 获取装修案例数据
     */
    private void getCaseList(int index) {
        LogUtils.e(SPUtil.gson.toJson(sortList)+"+++++++++++++++++++++");
        HttpMethod.getCaseList(casetype, dstyle, housearea, housetype, null, String.valueOf(page), SPUtil.gson.toJson(sortList), index, handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
