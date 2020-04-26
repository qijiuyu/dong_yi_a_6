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
import com.zxdc.utils.library.bean.BuildingDetails;
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

public class BespokeBuildingActivity extends BaseActivity {

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
        setContentView(R.layout.activity_bespoke_building);
        ButterKnife.bind(this);
        initView();
        //获取楼盘详情
        getBuildingDetails();
    }

    /**
     * 初始化
     */
    private void initView(){
        id=getIntent().getIntExtra("id",0);
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
                MobclickAgent.onEventObject(activity, "building_bespoke_name",map);

                Map<String, Object> map2= new HashMap<>();
                map2.put("mobile",mobile);
                MobclickAgent.onEventObject(activity, "building_bespoke_mobile",map2);

                Map<String, Object> map3 = new HashMap<>();
                map3.put("area",address);
                MobclickAgent.onEventObject(activity, "building_bespoke_area",map3);

                MobclickAgent.onEvent(this, "building_bespoke_btn");
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //详情回执
                case HandlerConstant.GET_BUILDING_DETAILS_SUCCESS:
                      BuildingDetails buildingDetails= (BuildingDetails) msg.obj;
                      if(buildingDetails==null){
                          break;
                      }
                      if(buildingDetails.isSussess()){
                          //展示楼盘数据
                          showBuildingData(buildingDetails.getData());
                      }else{
                          ToastUtil.showLong(buildingDetails.getDesc());
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
                        PointUtil.getInstent().respokePoint(BespokeBuildingActivity.this);
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
     * 展示楼盘数据
     */
    private void showBuildingData(BuildingDetails.BuildingBean buildingBean){
        if(buildingBean==null){
            return;
        }
        Glide.with(this).load(buildingBean.getImg()).fitCenter().into(imgHead);
        tvName.setText(buildingBean.getName());
        String des="在施工地：<font color=\"#AD9676\"><strong>"+buildingBean.getConstructioncount()+"</strong></font>户 | 相关案例：<font color=\"#AD9676\"><strong>"+buildingBean.getCasecount()+"</strong></font> 个";
        tvType.setText(Html.fromHtml(des));
    }


    /**
     * 获取楼盘详情
     */
    private void getBuildingDetails(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getBuildingDetails(String.valueOf(id),handler);
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
        HttpMethod.bespoke(city,address, mobile, name, String.valueOf(id), "4", handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
