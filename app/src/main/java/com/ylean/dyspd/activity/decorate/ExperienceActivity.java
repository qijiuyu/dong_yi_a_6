package com.ylean.dyspd.activity.decorate;

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
import com.ylean.dyspd.activity.decorate.search.SearchExperienceActivity;
import com.ylean.dyspd.adapter.decorate.ExperienceAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.NearList;
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
 * 体验店
 * Created by Administrator on 2019/11/9.
 */

public class ExperienceActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.img_distance)
    ImageView imgDistance;
    @BindView(R.id.img_case)
    ImageView imgCase;
    @BindView(R.id.img_designer)
    ImageView imgDesigner;
    @BindView(R.id.img_model)
    ImageView imgModel;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.tv_case)
    TextView tvCase;
    @BindView(R.id.tv_designer)
    TextView tvDesigner;
    @BindView(R.id.tv_model)
    TextView tvModel;
    private ExperienceAdapter experienceAdapter;
    /**
     * 距离，案例数，设计师数，户型解析数排序
     * 1：升序
     * 2：降序
     */
    private int distanceSort = 1, caseSort = 3, designerSort = 3, modelSort = 3;
    private String ordertype,sorttype;
    //页数
    private int page = 1;
    private List<NearList.NearBean> listAll = new ArrayList<>();
    //排序好的集合
    private List<Sort> sortList = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_list);
        ButterKnife.bind(this);
        initView();
        //按照排序查找
        sortSelect(0);
        //获取体验店列表
        getNearList(HandlerConstant.GET_NEAR_LIST_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView() {
        reList.setMyRefreshLayoutListener(this);
        experienceAdapter = new ExperienceAdapter(this, listAll);
        listView.setAdapter(experienceAdapter);
        listView.setDivider(null);
    }


    @OnClick({R.id.lin_back, R.id.img_search, R.id.lin_distance, R.id.lin_case, R.id.lin_designer, R.id.lin_model})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                //埋点
                MobclickAgent.onEvent(this, "store_list_back");
                break;
            //搜索
            case R.id.img_search:
                setClass(SearchExperienceActivity.class);
                break;
            //距离
            case R.id.lin_distance:
                if (distanceSort == 1) {
                    distanceSort = 2;
                    imgDistance.setImageResource(R.mipmap.click_bottom);
                } else {
                    distanceSort = 1;
                    imgDistance.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(0);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "store_list_distance");
                break;
            //案例数
            case R.id.lin_case:
                if (caseSort == 1 || caseSort==3) {
                    caseSort = 2;
                    imgCase.setImageResource(R.mipmap.click_bottom);
                } else {
                    caseSort = 1;
                    imgCase.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(1);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "store_list_case");
                break;
            //设计师数
            case R.id.lin_designer:
                if (designerSort == 1 || designerSort==3) {
                    designerSort = 2;
                    imgDesigner.setImageResource(R.mipmap.click_bottom);
                } else {
                    designerSort = 1;
                    imgDesigner.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(2);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "store_list_designer");
                break;
            //户型解析数
            case R.id.lin_model:
                if (modelSort == 1 || modelSort==3) {
                    modelSort = 2;
                    imgModel.setImageResource(R.mipmap.click_bottom);
                } else {
                    modelSort = 1;
                    imgModel.setImageResource(R.mipmap.click_top);
                }
                //按照排序查找
                sortSelect(3);
                //刷新列表
                onRefresh(null);

                //埋点
                MobclickAgent.onEvent(this, "store_list_model");
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_NEAR_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((NearList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_NEAR_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((NearList) msg.obj);
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
    private void refresh(NearList nearList) {
        if (nearList == null) {
            return;
        }
        if (nearList.isSussess()) {
            List<NearList.NearBean> list = nearList.getData();
            listAll.addAll(list);
            experienceAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
        } else {
            ToastUtil.showLong(nearList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getNearList(HandlerConstant.GET_NEAR_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getNearList(HandlerConstant.GET_NEAR_LIST_SUCCESS2);
    }


    /**
     * 获取排序数据
     *
     * @return
     */
    private String getSort() {
        List<Sort> sortList = new ArrayList<>();
        sortList.add(new Sort(1, distanceSort));
        sortList.add(new Sort(2, caseSort));
        sortList.add(new Sort(3, designerSort));
        sortList.add(new Sort(4, modelSort));
        return SPUtil.gson.toJson(sortList);
    }

    /**
     * 按照排序查找
     *
     * @param position
     */
    private void sortSelect(int position) {
        //排序的标题
        List<TextView> tvList = new ArrayList<>();
        tvList.add(tvDistance);
        tvList.add(tvCase);
        tvList.add(tvDesigner);
        tvList.add(tvModel);
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
        sortList.add(new Sort(1, distanceSort));
        sortList.add(new Sort(2, caseSort==3 ? 2 : caseSort));
        sortList.add(new Sort(3, designerSort==3 ? 2 : designerSort));
        sortList.add(new Sort(4, modelSort==3 ? 2 : modelSort));
        for (int i=0;i<sortList.size();i++){
            if(i==position){
                Sort sort=sortList.get(i);
                sortList.remove(i);
                sortList.add(0,sort);
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
     * 获取体验店列表
     */
    private void getNearList(int index) {
        HttpMethod.getNearList(null, String.valueOf(page), ordertype,sorttype, index, handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
