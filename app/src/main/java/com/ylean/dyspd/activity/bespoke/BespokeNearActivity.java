package com.ylean.dyspd.activity.bespoke;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.utils.PointUtil;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.NearDetails;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 预约参观体验店
 * Created by Administrator on 2019/12/1.
 */

public class BespokeNearActivity extends BaseActivity {

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
    //详情数据id
    private int id;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bespoke_near);
        ButterKnife.bind(this);

        id=getIntent().getIntExtra("id",0);
        //获取门店详情
        getNearDetails();
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

                //埋点
                Map<String, Object> map = new HashMap<>();
                map.put("name",name);
                MobclickAgent.onEventObject(activity, "store_bespoke_name",map);

                Map<String, Object> map2= new HashMap<>();
                map2.put("mobile",mobile);
                MobclickAgent.onEventObject(activity, "store_bespoke_mobile",map2);

                Map<String, Object> map3 = new HashMap<>();
                map3.put("area",address);
                MobclickAgent.onEventObject(activity, "store_bespoke_area",map3);

                MobclickAgent.onEvent(this, "store_bespoke_btn");
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
                case HandlerConstant.GET_NEAR_DETAILLS_SUCCESS:
                      NearDetails nearDetails= (NearDetails) msg.obj;
                      if(nearDetails==null){
                          break;
                      }
                      if(nearDetails.isSussess()){
                          //展示界面数据
                          showNearData(nearDetails.getData());
                      }else{
                          ToastUtil.showLong(nearDetails.getDesc());
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
                        PointUtil.getInstent().respokePoint(BespokeNearActivity.this);
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
     * 展示界面数据
     */
    private void showNearData(NearDetails.NearBean nearBean){
        if(nearBean==null){
            return;
        }
        Glide.with(this).load(nearBean.getImg()).fitCenter().into(imgHead);
        tvName.setText(nearBean.getName());
        String des="设计案例：<font color=\"#AD9676\"><strong>"+nearBean.getCasecount()+"</strong></font>套 | 设计师：<font color=\"#AD9676\"><strong>"+nearBean.getDesignercount()+"</strong></font> 人";
        tvType.setText(Html.fromHtml(des));
    }


    /**
     * 获取门店详情
     */
    private void getNearDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getNearDetails(String.valueOf(id),handler);
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
        HttpMethod.bespoke(city,address, mobile, name, String.valueOf(id), "3", handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
