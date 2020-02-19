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
import com.ylean.dyspd.utils.PointUtil;
import com.ylean.dyspd.view.TagsLayout;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.ConstructionDetails;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 预约参观工地
 * Created by Administrator on 2019/12/1.
 */

public class BespokeConstructionActivity extends BaseActivity {


    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
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
        setContentView(R.layout.activity_bespoke_cons);
        ButterKnife.bind(this);

        id=getIntent().getIntExtra("id",0);
        //获取施工地详情
        getConsDetails();
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
                case HandlerConstant.GET_CONS_DETAILS_SUCCESS:
                      ConstructionDetails constructionDetails= (ConstructionDetails) msg.obj;
                      if(constructionDetails==null){
                          break;
                      }
                      if(constructionDetails.isSussess()){
                          //展示工地数据
                          showConstructionData(constructionDetails.getData());
                      }else{
                          ToastUtil.showLong(constructionDetails.getDesc());
                      }
                      break;
                //预约回执
                case HandlerConstant.BESPOKE_SUCCESS:
                    final BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean == null) {
                        break;
                    }
                    if(baseBean.isSussess()){
                        //报名埋点
                        PointUtil.getInstent().respokePoint(BespokeConstructionActivity.this);
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
     * 展示工地数据
     */
    private void showConstructionData(ConstructionDetails.ConstructionBean constructionBean){
        if(constructionBean==null){
            return;
        }
        Glide.with(this).load(constructionBean.getImg()).fitCenter().into(imgHead);
        tvType.setText(constructionBean.getStage());
        tvTitle.setText(constructionBean.getName());
        tvDes.setText(""+constructionBean.getCityname()+" · "+constructionBean.getLoupanname()+" ·"+constructionBean.getSquare()+"");
    }


    /**
     * 获取施工地详情
     */
    private void getConsDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getConsDetails(String.valueOf(id),handler);
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
        HttpMethod.bespoke(city,address, mobile, name, String.valueOf(id), "5", handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
