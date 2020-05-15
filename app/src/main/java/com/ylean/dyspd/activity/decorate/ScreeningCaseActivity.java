package com.ylean.dyspd.activity.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.main.ScreeningAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Screening;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 筛选风格案例
 * Created by Administrator on 2019/11/20.
 */

public class ScreeningCaseActivity extends BaseActivity {

    @BindView(R.id.list_style)
    RecyclerView listStyle;
    @BindView(R.id.list_model)
    RecyclerView listModel;
    @BindView(R.id.list_area)
    RecyclerView listArea;
    @BindView(R.id.list_case)
    RecyclerView listCase;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    private ScreeningAdapter screeningAdapter;
    //风格名称，户型名称，面积名称,案例属性名称
    private String styleName="全部", modelName="全部", areaName="全部",caseName="全部";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_case);
        ButterKnife.bind(this);
        //获取风格数据
        getScreening("5", HandlerConstant.GET_SCREENING_STYLE);
        //获取户型数据
        getScreening("3", HandlerConstant.GET_SCREENING_MODEL);
        //获取面积数据
        getScreening("1", HandlerConstant.GET_SCREENING_AREA);
        //获取案例属性
        getScreeningCase();
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Screening screening = null;
            switch (msg.what) {
                //获取风格数据
                case HandlerConstant.GET_SCREENING_STYLE:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningCaseActivity.this, 3);
                        listStyle.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningCaseActivity.this, screening.getData(), 0, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    styleName = screeningBean.getName();

                                    //埋点
                                    switch (styleName){
                                        case "现代简约":
                                            MobclickAgent.onEvent(activity, "case_screening_1");
                                            break;
                                        case "欧式古典":
                                            MobclickAgent.onEvent(activity, "case_screening_2");
                                            break;
                                        case "新中式":
                                            MobclickAgent.onEvent(activity, "case_screening_3");
                                            break;
                                        case "法式":
                                            MobclickAgent.onEvent(activity, "case_screening_4");
                                            break;
                                        case "北欧":
                                            MobclickAgent.onEvent(activity, "case_screening_5");
                                            break;
                                        case "美式乡村":
                                            MobclickAgent.onEvent(activity, "case_screening_6");
                                            break;
                                        case "简欧":
                                            MobclickAgent.onEvent(activity, "case_screening_7");
                                            break;
                                        case "现代前卫":
                                            MobclickAgent.onEvent(activity, "case_screening_8");
                                            break;
                                        case "雅致主义":
                                            MobclickAgent.onEvent(activity, "case_screening_9");
                                            break;
                                        case "新古典":
                                            MobclickAgent.onEvent(activity, "case_screening_10");
                                            break;
                                        case "地中海":
                                            MobclickAgent.onEvent(activity, "case_screening_11");
                                            break;
                                        case "其他":
                                            MobclickAgent.onEvent(activity, "case_screening_12");
                                            break;
                                        default:
                                            break;
                                    }
                                }

                            }
                        });
                        listStyle.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取户型数据
                case HandlerConstant.GET_SCREENING_MODEL:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningCaseActivity.this, 3);
                        listModel.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningCaseActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    modelName = screeningBean.getName();


                                    //埋点
                                    switch (styleName){
                                        case "别墅":
                                            MobclickAgent.onEvent(activity, "case_model_1");
                                            break;
                                        case "跃层":
                                            MobclickAgent.onEvent(activity, "case_model_2");
                                            break;
                                        case "普通住宅":
                                            MobclickAgent.onEvent(activity, "case_model_3");
                                            break;
                                        case "会所":
                                            MobclickAgent.onEvent(activity, "case_model_4");
                                            break;
                                        case "一居室":
                                            MobclickAgent.onEvent(activity, "case_model_5");
                                            break;
                                        case "二居室":
                                            MobclickAgent.onEvent(activity, "case_model_6");
                                            break;
                                        case "三居室":
                                            MobclickAgent.onEvent(activity, "case_model_7");
                                            break;
                                        case "四居室":
                                            MobclickAgent.onEvent(activity, "case_model_8");
                                            break;
                                        case "Loft":
                                            MobclickAgent.onEvent(activity, "case_model_9");
                                            break;
                                        case "复式":
                                            MobclickAgent.onEvent(activity, "case_model_10");
                                            break;
                                    }
                                }
                            }
                        });
                        listModel.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取面积数据
                case HandlerConstant.GET_SCREENING_AREA:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningCaseActivity.this, 3);
                        listArea.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningCaseActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    areaName = screeningBean.getName();

                                    //埋点
                                    switch (styleName) {
                                        case "1000平米以上":
                                            MobclickAgent.onEvent(activity, "case_area_1");
                                            break;
                                        case "501-1000平米以上":
                                            MobclickAgent.onEvent(activity, "case_area_2");
                                            break;
                                        case "321-500平米":
                                            MobclickAgent.onEvent(activity, "case_area_3");
                                            break;
                                        case "181-320平米":
                                            MobclickAgent.onEvent(activity, "case_area_4");
                                            break;
                                        case "121-180平米":
                                            MobclickAgent.onEvent(activity, "case_area_5");
                                            break;
                                        case "120平米一下":
                                            MobclickAgent.onEvent(activity, "case_area_6");
                                            break;
                                    }
                                }
                            }
                        });
                        listArea.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取案例属性
                case HandlerConstant.GET_SCREENING_CASE:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningCaseActivity.this, 3);
                        listCase.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningCaseActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    caseName = screeningBean.getName();

                                    //埋点
                                    switch (styleName) {
                                        case "实景图":
                                            MobclickAgent.onEvent(activity, "case_attribute_1");
                                            break;
                                        case "效果图":
                                            MobclickAgent.onEvent(activity, "case_attribute_2");
                                            break;
                                    }
                                }
                            }
                        });
                        listCase.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
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


    @OnClick({R.id.lin_back,R.id.tv_style,R.id.tv_cancle, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_style:
                if(listStyle.getVisibility()==View.VISIBLE){
                    listStyle.setVisibility(View.GONE);
                    tvStyle.setText("展开");
                }else{
                    listStyle.setVisibility(View.VISIBLE);
                    tvStyle.setText("收起");
                }
                  break;
            case R.id.lin_back:
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(styleName)) {
                    ToastUtil.showLong("请选择风格");
                    return;
                }
                if (TextUtils.isEmpty(modelName)) {
                    ToastUtil.showLong("请选择户型");
                    return;
                }
                if (TextUtils.isEmpty(caseName)) {
                    ToastUtil.showLong("请选择案例属性");
                    return;
                }
                if (TextUtils.isEmpty(areaName)) {
                    ToastUtil.showLong("请选择面积");
                    return;
                }
                if(caseName.equals("全部")){
                    caseName="";
                }
                if(styleName.equals("全部")){
                    styleName="";
                }
                if(areaName.equals("全部")){
                    areaName="";
                }
                if(modelName.equals("全部")){
                    modelName="";
                }
                Intent intent = new Intent();
                intent.putExtra("caseName",caseName);
                intent.putExtra("styleName",styleName);
                intent.putExtra("areaName",areaName);
                intent.putExtra("modelName",modelName);
                setResult(100, intent);
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 获取风格,户型,面积数据
     */
    private void getScreening(String type, int index) {
        HttpMethod.getScreening(type, index, handler);
    }


    /**
     * 获取案例属性
     */
    private void getScreeningCase(){
        HttpMethod.getScreeningCase(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
