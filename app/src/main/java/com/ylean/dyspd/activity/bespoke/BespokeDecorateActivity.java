package com.ylean.dyspd.activity.bespoke;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.CaseDetails;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约装修
 * Created by Administrator on 2019/12/1.
 */

public class BespokeDecorateActivity extends BaseActivity {

    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_address)
    EditText etAddress;
    //详情id
    private int id;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bespoke_decorate);
        ButterKnife.bind(this);

        id=getIntent().getIntExtra("id",0);
        //获取案例详情
        getCaseDetails();
    }

    @OnClick({R.id.lin_back, R.id.tv_bespoke})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_bespoke:
                String name = etName.getText().toString().trim();
                String mobile = etMobile.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    ToastUtil.showLong("请输入您的称呼");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    ToastUtil.showLong("请选择您的联系方式");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    ToastUtil.showLong("请输入您所在的楼盘或小区信息");
                    return;
                }
                //预约
                bespoke(address, mobile, name);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //查询详情回执
                case  HandlerConstant.GET_CASE_DETAILS_SUCCESS:
                      CaseDetails caseDetails= (CaseDetails) msg.obj;
                      if(caseDetails==null){
                          break;
                      }
                      if(caseDetails.isSussess()){
                          //展示装修数据
                          showDecorateData(caseDetails.getData());
                      }else{
                          ToastUtil.showLong(caseDetails.getDesc());
                      }
                      break;
                //预约回执
                case HandlerConstant.BESPOKE_SUCCESS:
                    final BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean == null) {
                        break;
                    }
                    if(baseBean.isSussess()){
                        finish();
                    }
                    ToastUtil.showLong(baseBean.getDesc());
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
     * 展示装修数据
     */
    private void showDecorateData(CaseDetails.CaseBean caseBean){
        if(caseBean==null){
            return;
        }
        Glide.with(this).load(caseBean.getImg()).fitCenter().into(imgHead);
        tvName.setText(caseBean.getName());
        tvType.setText(caseBean.getDstyle());
    }


    /**
     * 获取案例详情
     */
    private void getCaseDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getCaseDetails(String.valueOf(id),handler);
    }

    /**
     * 预约
     */
    private void bespoke(String address, String mobile, String name) {
        DialogUtil.showProgress(this, "预约中...");
        String city= SPUtil.getInstance(activity).getString(SPUtil.CITY);
        if(TextUtils.isEmpty(city)){
            city= SPUtil.getInstance(activity).getString(SPUtil.LOCATION_CITY);
        }
        HttpMethod.bespoke(city,address, mobile, name, String.valueOf(id), "2", handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
