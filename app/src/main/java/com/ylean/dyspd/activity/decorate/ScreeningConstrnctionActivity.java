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
 * 筛选施工工地
 * Created by Administrator on 2019/11/20.
 */

public class ScreeningConstrnctionActivity extends BaseActivity {

    @BindView(R.id.list_progress)
    RecyclerView listProgress;
    @BindView(R.id.list_model)
    RecyclerView listModel;
    private ScreeningAdapter screeningAdapter;
    //所在阶段名称，户型名称，面积名称
    private String progressName="全部", modelName="全部";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_constrnction);
        ButterKnife.bind(this);
        //获取筛选施工阶段
        getScreeningConstrnction();
        //获取户型数据
        getScreening("3", HandlerConstant.GET_SCREENING_MODEL);
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Screening screening = null;
            switch (msg.what) {
                //获取筛选施工阶段
                case HandlerConstant.GET_SCREENING_CONSTRNCTION:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningConstrnctionActivity.this, 3);
                        listProgress.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningConstrnctionActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    progressName = screeningBean.getName();
                                }

                            }
                        });
                        listProgress.setAdapter(screeningAdapter);
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningConstrnctionActivity.this, 3);
                        listModel.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningConstrnctionActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
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
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    @OnClick({R.id.lin_back,R.id.tv_cancle, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(progressName)) {
                    ToastUtil.showLong("请选择所在阶段");
                    return;
                }
                if (TextUtils.isEmpty(modelName)) {
                    ToastUtil.showLong("请选择户型");
                    return;
                }
                if(progressName.equals("全部")){
                    progressName="";
                }
                if(modelName.equals("全部")){
                    modelName="";
                }
                Intent intent = new Intent();
                intent.putExtra("progressName",progressName);
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
     * 获取筛选施工阶段
     */
    private void getScreeningConstrnction(){
        HttpMethod.getScreeningConstrnction(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
