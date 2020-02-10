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
 * 筛选设计师
 * Created by Administrator on 2019/11/20.
 */

public class ScreeningDesignerActivity extends BaseActivity {

    @BindView(R.id.list_style)
    RecyclerView listStyle;
    @BindView(R.id.list_store)
    RecyclerView listStore;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.tv_store)
    TextView tvStore;
    @BindView(R.id.list_model)
    RecyclerView listModel;
    @BindView(R.id.list_desiType)
    RecyclerView listDesiType;
    private ScreeningAdapter screeningAdapter;
    //风格名称，户型
    private String styleName="全部",modelName="全部",designerType="全部",storeId="-1";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_designer);
        ButterKnife.bind(this);
        //获取风格数据
        getScreening();
        //获取筛选店铺
        getScreeningStore();
        //获取户型数据
        getScreening("3", HandlerConstant.GET_SCREENING_MODEL);
        //获取设计师筛选类型
        getDesignerType();
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningDesignerActivity.this, 3);
                        listStyle.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningDesignerActivity.this, screening.getData(), 0, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    styleName = screeningBean.getName();
                                }

                            }
                        });
                        listStyle.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取店铺数据
                case HandlerConstant.GET_SCREENING_STORE:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningDesignerActivity.this, 2);
                        listStore.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningDesignerActivity.this, screening.getData(), 1, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    storeId = String.valueOf(screeningBean.getId());
                                }
                            }
                        });
                        listStore.setAdapter(screeningAdapter);
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningDesignerActivity.this, 3);
                        listModel.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningDesignerActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    modelName = screeningBean.getName();
                                }
                            }
                        });
                        listModel.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取设计师类型数据
                case HandlerConstant.GET_DESIGNER_TYPE_SUCCESS:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningDesignerActivity.this, 3);
                        listDesiType.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningDesignerActivity.this, screening.getData(), 4, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    designerType = screeningBean.getName();
                                }
                            }
                        });
                        listDesiType.setAdapter(screeningAdapter);
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


    @OnClick({R.id.lin_back,R.id.tv_style, R.id.tv_store, R.id.tv_cancle, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_style:
                if (listStyle.getVisibility() == View.VISIBLE) {
                    listStyle.setVisibility(View.GONE);
                    tvStyle.setText("展开");
                } else {
                    listStyle.setVisibility(View.VISIBLE);
                    tvStyle.setText("收起");
                }
                break;
            case R.id.tv_store:
                if (listStore.getVisibility() == View.VISIBLE) {
                    listStore.setVisibility(View.GONE);
                    tvStore.setText("展开");
                } else {
                    listStore.setVisibility(View.VISIBLE);
                    tvStore.setText("收起");
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
                if (TextUtils.isEmpty(storeId)) {
                    ToastUtil.showLong("请选择所在店面");
                    return;
                }
                if (TextUtils.isEmpty(modelName)) {
                    ToastUtil.showLong("请选择户型");
                    return;
                }
                if (TextUtils.isEmpty(designerType)) {
                    ToastUtil.showLong("请选择设计师类型");
                    return;
                }
                if(styleName.equals("全部")){
                    styleName="";
                }
                if(storeId.equals("-1")){
                    storeId="";
                }
                if(modelName.equals("全部")){
                    modelName="";
                }
                if(designerType.equals("全部")){
                    designerType="";
                }
                Intent intent = new Intent();
                intent.putExtra("styleName",styleName);
                intent.putExtra("storeId",storeId);
                intent.putExtra("modelName",modelName);
                intent.putExtra("designerType",designerType);
                setResult(100, intent);
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 获取风格数据
     */
    private void getScreening() {
        HttpMethod.getScreening("5", HandlerConstant.GET_SCREENING_STYLE, handler);
    }


    /**
     * 获取筛选店铺
     */
    private void getScreeningStore() {
        HttpMethod.getScreeningStore("6", handler);
    }


    /**
     * 获取风格,户型,面积数据
     */
    private void getScreening(String type, int index) {
        HttpMethod.getScreening(type, index, handler);
    }


    /**
     * 获取设计师筛选类型
     */
    private void getDesignerType(){
        HttpMethod.getDesignerType(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }

}
