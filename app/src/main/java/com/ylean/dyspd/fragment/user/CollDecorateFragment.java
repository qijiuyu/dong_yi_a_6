package com.ylean.dyspd.fragment.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.collection.CollDecorateActivity;
import com.ylean.dyspd.adapter.user.CollDecorateAdapter;
import com.ylean.dyspd.view.CollectionEmptyView;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.CollDecorate;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
public class CollDecorateFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.empryView)
    CollectionEmptyView empryView;
    Unbinder unbinder;
    private CollDecorateAdapter collDecorateAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //页码
    private int page=1;
    //第几个fragment
    public int fragmentIndex;
    private List<CollDecorate.DecorateBean> listAll=new ArrayList<>();
    //收藏id
    private int id;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        collDecorateAdapter=new CollDecorateAdapter(mActivity,listAll,onItemCancle);
        listView.setAdapter(collDecorateAdapter);
        //获取收藏的装修攻略
        if(listAll.size()==0){
            getCollDecorate(HandlerConstant.GET_COLL_DECORATE_SUCCESS1);
        }
        return view;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_COLL_DECORATE_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((CollDecorate) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_COLL_DECORATE_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((CollDecorate) msg.obj);
                    break;
                //取消收藏回执
                case HandlerConstant.CANCLE_COLL_SUCCESS:
                    final BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean == null) {
                        break;
                    }
                    if (baseBean.isSussess()) {
                        for (int i = 0; i < listAll.size(); i++) {
                            if (listAll.get(i).getId() == id) {
                                listAll.remove(i);
                                break;
                            }
                        }
                        collDecorateAdapter.notifyDataSetChanged();
                        ToastUtil.showLong("取消收藏成功");
                    }else{
                        ToastUtil.showLong(baseBean.getDesc());
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
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
    private void refresh(CollDecorate collDecorate){
        if(collDecorate==null){
            return;
        }
        if(collDecorate.isSussess()){
            List<CollDecorate.DecorateBean> list=collDecorate.getData();
            listAll.addAll(list);
            collDecorateAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
            //没数据显示的视图
            if(list.size()>0){
                empryView.setVisibility(View.GONE);
            }else{
                empryView.setType(8);
                empryView.setVisibility(View.VISIBLE);
            }
        }else{
            ToastUtil.showLong(collDecorate.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getCollDecorate(HandlerConstant.GET_COLL_DECORATE_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getCollDecorate(HandlerConstant.GET_COLL_DECORATE_SUCCESS2);
    }


    /**
     * 获取收藏的装修攻略
     */
    private void getCollDecorate(int index){
        if(isVisibleToUser && view!=null){
            HttpMethod.getCollDecorate(String.valueOf(page),String.valueOf(CollDecorateActivity.typelist.get(fragmentIndex).getCommonid()),index,handler);
        }
    }

    public OnItemCancleImpl onItemCancle=new OnItemCancleImpl() {
        public void onCancleClick(int id) {
            //取消收藏
            cancleColl(id);
        }
    };

    public interface OnItemCancleImpl{
         void onCancleClick(int id);
    }

    /**
     * 取消收藏
     */
    public void cancleColl(int id) {
        this.id = id;
        DialogUtil.showProgress(mActivity, "取消收藏中...");
        HttpMethod.cancleColl(String.valueOf(id), handler);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取收藏的装修攻略
        if(listAll.size()==0){
            getCollDecorate(HandlerConstant.GET_COLL_DECORATE_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
