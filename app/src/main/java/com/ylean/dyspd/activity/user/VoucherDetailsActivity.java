package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.VoucherDetails;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换记录
 * Created by Administrator on 2019/11/14.
 */

public class VoucherDetailsActivity extends BaseActivity {

    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_old_money)
    TextView tvOldMoney;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_time)
    TextView tvTime;
    //兑换记录id
    private int id;
    private VoucherDetails.VoucherDetailsBean voucherDetailsBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_details);
        ButterKnife.bind(this);
        id=getIntent().getIntExtra("id",0);
        //获取兑换记录详情
        getVoucherDetails();
    }


    @OnClick({R.id.lin_back, R.id.tv_details})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_details:
                 if(voucherDetailsBean==null){
                     return;
                 }
                Intent intent=new Intent(this,GoodDetailsActivity.class);
                intent.putExtra("id",voucherDetailsBean.getSpuid());
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_VOUCHER_DETAILS_SUCCESS:
                      VoucherDetails  voucherDetails= (VoucherDetails) msg.obj;
                      if(voucherDetails==null){
                          break;
                      }
                      if(voucherDetails.isSussess()){
                          voucherDetailsBean=voucherDetails.getData();
                          //展示详情数据
                          showVoucherDetails();
                      }else{
                          ToastUtil.showLong(voucherDetails.getDesc());
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
     * 展示详情数据
     */
    private void showVoucherDetails(){
        if(voucherDetailsBean==null){
            return;
        }
        Glide.with(this).load(voucherDetailsBean.getSpuimg()).centerCrop().into(imgHead);
        tvName.setText(voucherDetailsBean.getSpuname());
        tvOldMoney.setText(Util.setDouble(voucherDetailsBean.getSpuprice(),2));
        tvOldMoney.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        tvUser.setText(voucherDetailsBean.getTelname());
        tvMobile.setText(voucherDetailsBean.getTelphone());
        switch (voucherDetailsBean.getStatus()){
            case 0:
                 tvStatus.setText("待确认");
                 break;
            case 1:
                tvStatus.setText("待领取");
                break;
            case 2:
                tvStatus.setText("已领取");
                break;
            case 3:
                tvStatus.setText("已失效");
                break;
            default:
                break;
        }
        tvTime.setText(voucherDetailsBean.getCreatedate());
    }


    /**
     * 获取兑换记录详情
     */
    private void getVoucherDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getVoucherDetails(String.valueOf(id),handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
