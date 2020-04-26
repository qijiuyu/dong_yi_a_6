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
import com.ylean.dyspd.activity.decorate.search.SearchBuildingActivity;
import com.ylean.dyspd.adapter.decorate.BuildingListAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BuildingList;
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
 * 热装楼盘
 * Created by Administrator on 2019/11/9.
 */
public class BuildingListActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.tv_screening)
    TextView tvScreening;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.img_case)
    ImageView imgCase;
    @BindView(R.id.img_model)
    ImageView imgModel;
    @BindView(R.id.tv_no)
    TextView tvNo;
    @BindView(R.id.tv_case)
    TextView tvCase;
    @BindView(R.id.tv_model)
    TextView tvModel;
    private BuildingListAdapter buildingListAdapter;
    /**
     * 案例数和户型解析数排序
     * 1：升序
     * 2：降序
     * 3：默认
     */
    private int caseSort = 2, modelSort = 3;
    private String ordertype,sorttype;
    //所在地区
    private String district;
    //页数
    private int page = 1;
    private List<BuildingList.BuildingBean> listAll = new ArrayList<>();
    //排序好的集合
    private List<Sort> sortList = new ArrayList<>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_list);
        ButterKnife.bind(this);
        initView();
        //按照排序查找
        sortSelect(0);
        //获取热门楼盘列表
        getBuildingList(HandlerConstant.GET_BUILDING_LIST_SUCCESS1);
    }

    /**
     * 初始化
     */
    private void initView() {
        reList.setMyRefreshLayoutListener(this);
        buildingListAdapter = new BuildingListAdapter(this, listAll);
        listView.setAdapter(buildingListAdapter);
        listView.setDividerHeight(0);
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_search, R.id.lin_case, R.id.lin_model, R.id.tv_screening})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();

                //埋点
                MobclickAgent.onEvent(this, "building_list_back");
                break;
            case R.id.img_search:
                setClass(SearchBuildingActivity.class);
                break;
            //案例数
            case R.id.lin_case:
                if (caseSort == 1) {
                    caseSort = 2;
                    imgCase.setImageResource(R.mipmap.click_bottom);
                } else {
                    caseSort = 1;
                    imgCase.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(0);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "building_list_case");
                break;
            //户型解析数
            case R.id.lin_model:
                if (modelSort == 1 || modelSort == 3) {
                    modelSort = 2;
                    imgModel.setImageResource(R.mipmap.click_bottom);
                } else {
                    modelSort = 1;
                    imgModel.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(1);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "building_list_model");
                break;
            //筛选
            case R.id.tv_screening:
                setClass(ScreeningHotActivity.class, 100);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_BUILDING_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((BuildingList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_BUILDING_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((BuildingList) msg.obj);
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
    private void refresh(BuildingList buildingList) {
        if (buildingList == null) {
            return;
        }
        if (buildingList.isSussess()) {
            List<BuildingList.BuildingBean> list = buildingList.getData();
            listAll.addAll(list);
            buildingListAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            if (listAll.size() == 0) {
                tvNo.setVisibility(View.VISIBLE);
            } else {
                tvNo.setVisibility(View.GONE);
            }
        } else {
            ToastUtil.showLong(buildingList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getBuildingList(HandlerConstant.GET_BUILDING_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getBuildingList(HandlerConstant.GET_BUILDING_LIST_SUCCESS2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            district = data.getStringExtra("name");
            //刷新列表
            onRefresh(null);

            //埋点
            MobclickAgent.onEvent(this, "building_list_screening");
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
        tvList.add(tvCase);
        tvList.add(tvModel);
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
        sortList.add(new Sort(1, caseSort));
        sortList.add(new Sort(2, modelSort == 3 ? 2 : modelSort));
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
     * 获取热门楼盘列表
     */
    private void getBuildingList(int index) {
        HttpMethod.getBuildingList(district, null, String.valueOf(page), ordertype,sorttype, index, handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }

}
