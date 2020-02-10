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
import com.ylean.dyspd.activity.user.GiftActivity;
import com.ylean.dyspd.adapter.user.CouponsAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.Coupons;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * 优惠券
 * Created by Administrator on 2019/11/8.
 */
public class CouponsFragment extends BaseFragment implements MyRefreshLayoutListener {


    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    private CouponsAdapter couponsAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //页码
    private int page=1;
    private List<Coupons.CouponsBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);

        listView.setDivider(null);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        couponsAdapter=new CouponsAdapter(mActivity,listAll);
        listView.setAdapter(couponsAdapter);
        //获取优惠券列表
        if(listAll.size()==0){
            getCouponsList(HandlerConstant.GET_COUPONS_LIST_SUCCESS1);
        }
        return view;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_COUPONS_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((Coupons) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_COUPONS_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((Coupons) msg.obj);
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
    private void refresh(Coupons coupons){
        if(coupons==null){
            return;
        }
        if(coupons.isSussess()){
            List<Coupons.CouponsBean> list=coupons.getData();
            listAll.addAll(list);
            couponsAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
        }else{
            ToastUtil.showLong(coupons.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getCouponsList(HandlerConstant.GET_COUPONS_LIST_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getCouponsList(HandlerConstant.GET_COUPONS_LIST_SUCCESS2);
    }


    /**
     * 获取优惠券列表
     */
    private void getCouponsList(final int index){
        if(isVisibleToUser && view!=null){
            handler.postDelayed(new Runnable() {
                public void run() {
                    int status=GiftActivity.pagerIndex+1;
                    if(status==2){
                        status=3;
                    }else if(status==3){
                        status=2;
                    }
                    HttpMethod.getCouponsList(String.valueOf(page),String.valueOf(status),index,handler);
                }
            },300);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取优惠券列表
        if(listAll.size()==0){
            getCouponsList(HandlerConstant.GET_COUPONS_LIST_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
