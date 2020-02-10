package com.ylean.dyspd.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.SearchListActivity;
import com.ylean.dyspd.adapter.decorate.SearchFragmentAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.TotalSearch;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.tv_no)
    TextView tvNo;
    Unbinder unbinder;
    //fragment是否可见
    private boolean isVisibleToUser = false;
    private SearchFragmentAdapter searchFragmentAdapter;
    private List<TotalSearch.TotalSearchBean> listAll = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册eventBus
        EventBus.getDefault().register(this);
    }

    View view = null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        searchFragmentAdapter = new SearchFragmentAdapter(mActivity, listAll);
        listView.setAdapter(searchFragmentAdapter);
        //查询全部搜索
        if (listAll.size() == 0) {
            getTotalSearch();
        }
        return view;
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                case HandlerConstant.GET_TOTAL_SEARCH_SUCCESS:
                    final TotalSearch totalSearch = (TotalSearch) msg.obj;
                    if (totalSearch == null) {
                        break;
                    }
                    if (totalSearch.isSussess()) {
                        listAll.addAll(totalSearch.getData());
                        searchFragmentAdapter.notifyDataSetChanged();
                        if(listAll.size()==0){
                            tvNo.setVisibility(View.VISIBLE);
                        }else{
                            tvNo.setVisibility(View.GONE);
                        }
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
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //搜索新的关键字
            case EventStatus.SEARCH_FRAGMENT_BY_KEYS:
                listAll.clear();
                searchFragmentAdapter.notifyDataSetChanged();
                getTotalSearch();
                break;
            default:
                break;
        }
    }


    /**
     * 查询全部搜索
     */
    private void getTotalSearch() {
        if (isVisibleToUser && view != null) {
            HttpMethod.getTotalSearch(SearchListActivity.strKey, handler);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        //查询全部搜索
        if (listAll.size() == 0) {
            getTotalSearch();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        removeHandler(handler);
    }
}
