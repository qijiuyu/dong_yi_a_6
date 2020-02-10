package com.ylean.dyspd.fragment.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.user.SignFragmentAdapter;
import com.zxdc.utils.library.bean.OnItemClickCallBack;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.view.DynamicEmptyView;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.News;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 消息：动态
 * Created by Administrator on 2019/11/8.
 */

public class DynamicFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    @BindView(R.id.dy_dmptyView)
    DynamicEmptyView dyDmptyView;
    private SignFragmentAdapter signFragmentAdapter;
    //fragment是否可见
    private boolean isVisibleToUser = false;
    //页码
    private int page = 1;
    private List<News.NewsBean> listAll = new ArrayList<>();
    //消息id
    private int newId;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view = null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        listView.setDivider(null);
        signFragmentAdapter = new SignFragmentAdapter(mActivity, listAll,onItemClickCallBack,2);
        listView.setAdapter(signFragmentAdapter);
        //获取消息列表
        if (listAll.size() == 0) {
            getNews(HandlerConstant.GET_NEWS_SUCCESS1);
        }
        return view;
    }

    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_NEWS_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((News) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_NEWS_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((News) msg.obj);
                    break;
                //设置消息为已读
                case HandlerConstant.SET_NEWS_STATUS_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(baseBean==null){
                        break;
                    }
                    if(baseBean.isSussess()){
                        for (int i=0,len=listAll.size();i<len;i++){
                            if(listAll.get(i).getId()==newId){
                                listAll.get(i).setStatus(1);
                                break;
                            }
                        }
                        signFragmentAdapter.notifyDataSetChanged();
                        //隐藏消息数量的显示
                        EventBus.getDefault().post(new EventBusType(EventStatus.UPDATE_NEWS_NUM));
                    }
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
    private void refresh(News news) {
        if (news == null) {
            return;
        }
        if (news.isSussess()) {
            List<News.NewsBean> list = news.getData();
            listAll.addAll(list);
            signFragmentAdapter.notifyDataSetChanged();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                }
            });
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            //没数据显示的视图
            if(listAll.size()>0){
                dyDmptyView.setVisibility(View.GONE);
            }else{
                dyDmptyView.setVisibility(View.VISIBLE);
            }
        } else {
            ToastUtil.showLong(news.getDesc());
        }
    }


    /**
     * 下刷
     *
     * @param view
     */
    public void onRefresh(View view) {
        page = 1;
        getNews(HandlerConstant.GET_NEWS_SUCCESS1);
    }

    /**
     * 上拉加载更多
     *
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getNews(HandlerConstant.GET_NEWS_SUCCESS2);
    }


    /**
     * 获取消息列表
     */
    private void getNews(int index) {
        if (isVisibleToUser && view != null) {
            HttpMethod.getNews(String.valueOf(page), "2", index, handler);
        }
    }

    /**
     * 设置消息为已读
     */
    private void setNewsStatus(){
        DialogUtil.showProgress(mActivity,"设置中...");
        HttpMethod.setNewsStatus(String.valueOf(newId),handler);
    }

    private OnItemClickCallBack onItemClickCallBack=new OnItemClickCallBack() {
        public void ItemClick(Object object) {
            newId= (int) object;
            //设置消息为已读
            setNewsStatus();
        }
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        //获取消息列表
        if (listAll.size() == 0) {
            getNews(HandlerConstant.GET_NEWS_SUCCESS1);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
