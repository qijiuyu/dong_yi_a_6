package com.ylean.dyspd.fragment.user;

import android.content.Intent;
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
import com.ylean.dyspd.activity.user.GiftActivity;
import com.ylean.dyspd.activity.user.VoucherDetailsActivity;
import com.ylean.dyspd.adapter.user.VoucherAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.Voucher;
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
 * 兑换券
 * Created by Administrator on 2019/11/8.
 */

public class VoucherFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    Unbinder unbinder;
    private VoucherAdapter voucherAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //页码
    private int page=1;
    //数据集合
    private List<Voucher.VoucherBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        listView.setDivider(null);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        voucherAdapter=new VoucherAdapter(mActivity,listAll);
        listView.setAdapter(voucherAdapter);
        //获取兑换券列表
        if(listAll.size()==0){
            getVoucherList(HandlerConstant.GET_VOUCHER_LIST_SUCCESS1);
        }
        return view;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_VOUCHER_LIST_SUCCESS1:
                     reList.refreshComplete();
                     listAll.clear();
                     refresh((Voucher) msg.obj);
                     break;
                //上拉
                case HandlerConstant.GET_VOUCHER_LIST_SUCCESS2:
                     reList.loadMoreComplete();
                     refresh((Voucher) msg.obj);
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
    private void refresh(Voucher voucher){
      if(voucher==null){
          return;
      }
      if(voucher.isSussess()){
          List<Voucher.VoucherBean> list=voucher.getData();
          listAll.addAll(list);
          voucherAdapter.notifyDataSetChanged();
          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mActivity,VoucherDetailsActivity.class);
                intent.putExtra("id",listAll.get(position).getId());
                startActivity(intent);
            }
        });
          if(list.size()<HttpMethod.pageSize){
              reList.setIsLoadingMoreEnabled(false);
          }
      }else{
          ToastUtil.showLong(voucher.getDesc());
      }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getVoucherList(HandlerConstant.GET_VOUCHER_LIST_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getVoucherList(HandlerConstant.GET_VOUCHER_LIST_SUCCESS2);
    }


    /**
     * 获取兑换券列表
     */
    private void getVoucherList(final int index){
        if(isVisibleToUser && view!=null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    HttpMethod.getVoucherList(String.valueOf(page),String.valueOf(GiftActivity.pagerIndex),index,handler);
                }
            },300);
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取兑换券列表
        if(listAll.size()==0){
            getVoucherList(HandlerConstant.GET_VOUCHER_LIST_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
