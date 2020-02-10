package com.ylean.dyspd.fragment.user;

import android.app.Dialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.VoucherGoodActivity;
import com.ylean.dyspd.adapter.user.VoucherGoodAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.GiftData;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 兑换礼品
 * Created by Administrator on 2019/11/8.
 */

public class VoucherGoodFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    RecyclerView listView;
    Unbinder unbinder;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    //adapter适配器
    private VoucherGoodAdapter voucherGoodAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //页码
    private int page=1;
    //数据集合
    private List<GiftData.GiftDataBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_voucher_good, container, false);
        unbinder = ButterKnife.bind(this, view);

        reList.setMyRefreshLayoutListener(this);
        listView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        voucherGoodAdapter = new VoucherGoodAdapter(mActivity,listAll,onItemClickListener);
        listView.setAdapter(voucherGoodAdapter);
        //获取礼品列表
        if(listAll.size()==0){
            getGiftList(HandlerConstant.GET_GIFT_LIST_SUCCESS1);
        }
        return view;
    }



    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_GIFT_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((GiftData) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_GIFT_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((GiftData) msg.obj);
                    break;
                //兑换礼品
                case HandlerConstant.ADD_GIFT_SUCCESS:
                      BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      ToastUtil.showLong(baseBean.getDesc());
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
    private void refresh(GiftData giftData){
        if(giftData==null){
            return;
        }
        if(giftData.isSussess()){
            List<GiftData.GiftDataBean> list=giftData.getData();
            listAll.addAll(list);
            voucherGoodAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
        }else{
            ToastUtil.showLong(giftData.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getGiftList(HandlerConstant.GET_GIFT_LIST_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getGiftList(HandlerConstant.GET_GIFT_LIST_SUCCESS2);
    }


    private OnItemClickListener onItemClickListener=new OnItemClickListener() {
        public void onItemClick(GiftData.GiftDataBean giftDataBean) {
            showVoucherDialog(giftDataBean);
        }
    };


    /**
     * 展示兑换弹框
     */
    private void showVoucherDialog(final GiftData.GiftDataBean giftDataBean) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_voucher, null);
        final Dialog dialog = DialogUtil.getDialog(mActivity, view);
        ImageView imgHead=view.findViewById(R.id.img_head);
        TextView tvName=view.findViewById(R.id.tv_name);
        TextView tvMoney=view.findViewById(R.id.tv_old_money);
        final EditText etName=view.findViewById(R.id.et_name);
        final EditText etMobille=view.findViewById(R.id.et_mobile);

        Glide.with(mActivity).load(giftDataBean.getImg()).centerCrop().into(imgHead);
        tvName.setText(giftDataBean.getName());
        tvMoney.setText(Util.setDouble(giftDataBean.getPrice(),1));
        tvMoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        //立即兑换
        view.findViewById(R.id.tv_voucher).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name=etName.getText().toString().trim();
                String mobile=etMobille.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    ToastUtil.showLong("请输入您的称呼");
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    ToastUtil.showLong("请输入您的联系方式");
                    return;
                }
                dialog.dismiss();
                //兑换礼品
                addGift(mobile,name,String.valueOf(giftDataBean.getId()));
            }
        });
        //关闭
        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 获取兑换券列表
     */
    private void getGiftList(final int index){
        if(isVisibleToUser && view!=null){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(mActivity instanceof VoucherGoodActivity){
                        VoucherGoodActivity voucherGoodActivity=((VoucherGoodActivity)mActivity);
                        final int typeId=voucherGoodActivity.typeList.get(voucherGoodActivity.pagerIndex).getId();
                        HttpMethod.getGiftList(String.valueOf(typeId),String.valueOf(page),index,handler);
                    }
                }
            },200);
        }
    }


    /**
     * 兑换礼品
     */
    private void addGift(String mobile,String name,String sid){
        DialogUtil.showProgress(mActivity,"兑换中...");
        HttpMethod.addGift(mobile,name,sid,handler);
    }


    public interface OnItemClickListener{
        void onItemClick(GiftData.GiftDataBean giftDataBean);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取礼品列表
        if(listAll.size()==0){
            getGiftList(HandlerConstant.GET_GIFT_LIST_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
