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

public class ScreeningGalleryActivity extends BaseActivity {

    @BindView(R.id.list_style)
    RecyclerView listStyle;
    @BindView(R.id.list_space)
    RecyclerView listSpace;
    @BindView(R.id.list_element)
    RecyclerView listElement;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.tv_space)
    TextView tvSpace;
    private ScreeningAdapter screeningAdapter;
    //风格名称，空间名称,元素名称
    private String styleName="全部", spaceName="全部", elementName="全部";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_gallery);
        ButterKnife.bind(this);
        //获取风格数据
        getScreening("5", HandlerConstant.GET_SCREENING_STYLE);
        //获取空间数据
        getScreening("4", HandlerConstant.GET_SCREENING_SPACE);
        //获取元素数据
        getScreening("6", HandlerConstant.GET_SCREENING_ELEMENT);
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
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningGalleryActivity.this, 3);
                        listStyle.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningGalleryActivity.this, screening.getData(), 0, new ScreeningAdapter.OnItemClickListener() {
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
                //获取空间数据
                case HandlerConstant.GET_SCREENING_SPACE:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningGalleryActivity.this, 3);
                        listSpace.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningGalleryActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    spaceName = screeningBean.getName();

                                    //埋点
                                    switch (styleName){
                                        case "玄关":
                                            MobclickAgent.onEvent(activity, "gallery_space_1");
                                            break;
                                        case "客厅":
                                            MobclickAgent.onEvent(activity, "gallery_space_2");
                                            break;
                                        case "餐厅":
                                            MobclickAgent.onEvent(activity, "gallery_space_3");
                                            break;
                                        case "厨房":
                                            MobclickAgent.onEvent(activity, "gallery_space_4");
                                            break;
                                        case "卧室":
                                            MobclickAgent.onEvent(activity, "gallery_space_5");
                                            break;
                                        case "儿童房":
                                            MobclickAgent.onEvent(activity, "gallery_space_6");
                                            break;
                                        case "衣帽间":
                                            MobclickAgent.onEvent(activity, "gallery_space_7");
                                            break;
                                        case "卫生间":
                                            MobclickAgent.onEvent(activity, "gallery_space_8");
                                            break;
                                        case "书房":
                                            MobclickAgent.onEvent(activity, "gallery_space_9");
                                            break;
                                        case "阳台":
                                            MobclickAgent.onEvent(activity, "gallery_space_10");
                                            break;
                                        case "庭院":
                                            MobclickAgent.onEvent(activity, "gallery_space_11");
                                            break;
                                        case "其他":
                                            MobclickAgent.onEvent(activity, "gallery_space_12");
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
                        listSpace.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取元素数据
                case HandlerConstant.GET_SCREENING_ELEMENT:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningGalleryActivity.this, 3);
                        listElement.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningGalleryActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    elementName = screeningBean.getName();

                                    //埋点
                                    switch (styleName){
                                        case "家居":
                                            MobclickAgent.onEvent(activity, "gallery_element_1");
                                            break;
                                        case "饰品":
                                            MobclickAgent.onEvent(activity, "gallery_element_2");
                                            break;
                                        case "布艺":
                                            MobclickAgent.onEvent(activity, "gallery_element_3");
                                            break;
                                        case "日用品":
                                            MobclickAgent.onEvent(activity, "gallery_element_4");
                                            break;
                                        case "灯具":
                                            MobclickAgent.onEvent(activity, "gallery_element_5");
                                            break;
                                        case "花品":
                                            MobclickAgent.onEvent(activity, "dgallery_element_6");
                                            break;
                                        case "收藏品":
                                            MobclickAgent.onEvent(activity, "gallery_element_7");
                                            break;
                                        case "画品":
                                            MobclickAgent.onEvent(activity, "degallery_element_8");
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        });
                        listElement.setAdapter(screeningAdapter);
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


    @OnClick({R.id.lin_back,R.id.tv_style,R.id.tv_space,R.id.tv_cancle, R.id.tv_confirm})
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
            case R.id.tv_space:
                if(listSpace.getVisibility()==View.VISIBLE){
                    listSpace.setVisibility(View.GONE);
                    tvSpace.setText("展开");
                }else{
                    listSpace.setVisibility(View.VISIBLE);
                    tvSpace.setText("收起");
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
                if (TextUtils.isEmpty(spaceName)) {
                    ToastUtil.showLong("请选择空间数据");
                    return;
                }
                if (TextUtils.isEmpty(elementName)) {
                    ToastUtil.showLong("请选择元素数据");
                    return;
                }
                if(styleName.equals("全部")){
                    styleName="";
                }
                if(elementName.equals("全部")){
                    elementName="";
                }
                if(spaceName.equals("全部")){
                    spaceName="";
                }
                Intent intent = new Intent();
                intent.putExtra("styleName",styleName);
                intent.putExtra("elementName",elementName);
                intent.putExtra("spaceName",spaceName);
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
