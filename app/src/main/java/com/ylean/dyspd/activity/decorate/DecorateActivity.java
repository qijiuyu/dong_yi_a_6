package com.ylean.dyspd.activity.decorate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.decorate.DecorateItemAdapter;
import com.ylean.dyspd.utils.PointUtil;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.DecorateType;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 装修菜单类
 * Created by Administrator on 2019/11/7.
 */

public class DecorateActivity extends BaseActivity{

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.img_search)
    ImageView imgSearch;
    @BindView(R.id.img_designer)
    ImageView imgDesigner;
    @BindView(R.id.img_experience)
    ImageView imgExperience;
    @BindView(R.id.tv_fgal)
    TextView tvFGAL;
    @BindView(R.id.tv_altk)
    TextView tvAltk;
    @BindView(R.id.tv_vr)
    TextView tvVr;
    @BindView(R.id.tv_rzfb)
    TextView tvRzfb;
    @BindView(R.id.tv_rzlp)
    TextView tvRzlp;
    @BindView(R.id.tv_zxgd)
    TextView tvZxgd;
    @BindView(R.id.list_decorate)
    RecyclerView decorateList;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorate);
        ButterKnife.bind(this);
    }



    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.img_search, R.id.img_designer, R.id.img_experience, R.id.tv_fgal, R.id.tv_altk, R.id.tv_vr, R.id.tv_rzfb, R.id.tv_rzlp, R.id.tv_zxgd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //搜索
            case R.id.img_search:
                setClass(SearchActivity.class);
                break;
            //设计师
            case R.id.img_designer:
                setClass(DesignerListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,1);
                break;
            //体验店
            case R.id.img_experience:
                setClass(ExperienceActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,2);
                break;
            //风格案例
            case R.id.tv_fgal:
                setClass(CaseListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,3);
                break;
            //案例图库
            case R.id.tv_altk:
                setClass(GalleryListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,4);
                break;
            //VR样板房
            case R.id.tv_vr:
                setClass(VRListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,5);
                break;
            //软装范本
            case R.id.tv_rzfb:
                setClass(SoftLoadingActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,6);
                break;
            //热装楼盘
            case R.id.tv_rzlp:
                setClass(BuildingListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,7);
                break;
            //装修工地
            case R.id.tv_zxgd:
                setClass(ConstructionListActivity.class);
                //埋点
                PointUtil.getInstent().pagePoint(this,8);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //获取装修攻略分类
                case HandlerConstant.GET_DOCORATE_TYPE_SUCCESS:
                    DecorateType decorateType = (DecorateType) msg.obj;
                    if (decorateType == null) {
                        break;
                    }
                    if (decorateType.isSussess() && decorateType.getData() != null) {
                        //展示底部装修攻略数据
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DecorateActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        decorateList.setLayoutManager(linearLayoutManager);
                        decorateList.setAdapter(new DecorateItemAdapter(DecorateActivity.this,decorateType.getData()));
                    } else {
                        ToastUtil.showLong(decorateType.getDesc());
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 获取装修攻略分类
     */
    private void getDecorateType() {
        HttpMethod.getDecorateType(handler);
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取装修攻略分类
        getDecorateType();
    }
}
