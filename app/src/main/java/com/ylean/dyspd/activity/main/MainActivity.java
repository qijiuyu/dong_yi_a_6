package com.ylean.dyspd.activity.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.Pivot;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.BuildingListActivity;
import com.ylean.dyspd.activity.decorate.CaseListActivity;
import com.ylean.dyspd.activity.decorate.ConstructionListActivity;
import com.ylean.dyspd.activity.decorate.DecorateProgressActivity;
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.activity.decorate.ExperienceActivity;
import com.ylean.dyspd.activity.decorate.SearchActivity;
import com.ylean.dyspd.activity.user.NewsActivity;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.ylean.dyspd.adapter.main.CardImgAdapter;
import com.ylean.dyspd.adapter.main.MainConsAdapter;
import com.ylean.dyspd.adapter.main.MainDecorateAdapter;
import com.ylean.dyspd.adapter.main.MainDesignerAdapter;
import com.ylean.dyspd.adapter.main.MainHotAdapter;
import com.ylean.dyspd.adapter.main.MainNearAdapter;
import com.ylean.dyspd.adapter.main.MainProcessAdapter;
import com.ylean.dyspd.adapter.main.MainTypeAdapter;
import com.ylean.dyspd.application.MyApplication;
import com.zxdc.utils.library.bean.NewsNum;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.persenter.main.MainPersenter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Banner.BannerBean;
import com.zxdc.utils.library.bean.CaseImg;
import com.zxdc.utils.library.bean.ConstructionList;
import com.zxdc.utils.library.bean.MainBuilding;
import com.zxdc.utils.library.bean.MainCase;
import com.zxdc.utils.library.bean.MainDecorate;
import com.zxdc.utils.library.bean.MainDesigner;
import com.zxdc.utils.library.bean.Move;
import com.zxdc.utils.library.bean.NearList;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.view.HorizontalListView;
import com.zxdc.utils.library.view.MeasureListView;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页菜单类
 * Created by Administrator on 2019/11/7.
 */

public class MainActivity extends BaseActivity implements View.OnTouchListener,MyRefreshLayoutListener {

    @BindView(R.id.re_list)
    public MyRefreshLayout reList;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycleView)
    DiscreteScrollView recycleView;
    @BindView(R.id.list_type)
    RecyclerView listType;
    @BindView(R.id.tv_case_num)
    TextView tvCaseNum;
    @BindView(R.id.tv_case_totalnum)
    TextView tvCaseTotalNum;
    @BindView(R.id.list_hot)
    DiscreteScrollView listHot;
    @BindView(R.id.list_construction)
    MeasureListView listConstruction;
    @BindView(R.id.list_near)
    DiscreteScrollView listNear;
    @BindView(R.id.tv_near_num)
    TextView tvNearNum;
    @BindView(R.id.tv_near_totalnum)
    TextView tvNearTotalNum;
    @BindView(R.id.list_process)
    RecyclerView listProcess;
    @BindView(R.id.list_decorate)
    MeasureListView listDecorate;
    @BindView(R.id.tv_hot_num)
    TextView tvHotNum;
    @BindView(R.id.tv_hot_totalnum)
    TextView tvHotTotalnum;
    @BindView(R.id.list_designer)
    DiscreteScrollView listDesigner;
    @BindView(R.id.tv_designer_num)
    TextView tvDesignerNum;
    @BindView(R.id.tv_designer_totalnum)
    TextView tvDesignerTotalnum;
    @BindView(R.id.img_brand)
    ImageView imgBrand;
    @BindView(R.id.lin_designer)
    LinearLayout linDesigner;
    @BindView(R.id.lin_hot)
    LinearLayout linHot;
    @BindView(R.id.lin_construction)
    LinearLayout linConstruction;
    @BindView(R.id.lin_near)
    LinearLayout linNear;
    @BindView(R.id.lin_case)
    LinearLayout linCase;
    @BindView(R.id.view_news)
    View viewNews;
    //MVP对象
    private MainPersenter mainPersenter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
        //开始定位
        mainPersenter.startLocation();
        //是否首次进入首页
        mainPersenter.isOpenMain();
    }


    /**
     * 初始化
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initView() {
        scrollView.setOnTouchListener(this);
        mainPersenter = new MainPersenter(this);
        reList.setMyRefreshLayoutListener(this);
        reList.setIsLoadingMoreEnabled(false);

        //装修流程
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        listProcess.setLayoutManager(linearLayoutManager);
        MainProcessAdapter mainProcessAdapter = new MainProcessAdapter(this);
        listProcess.setAdapter(mainProcessAdapter);
    }


    @OnClick({R.id.tv_more_case,R.id.tv_more_designer, R.id.tv_more_construction, R.id.tv_more_near, R.id.tv_more_before, R.id.tv_more_hot, R.id.lin_city, R.id.img_search, R.id.lin_sxlf, R.id.rel_wyyf, R.id.rel_zcjj, R.id.rel_zssj, R.id.rel_wdfg, R.id.rel_jsq,R.id.img_brand,R.id.img_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //城市切换
            case R.id.lin_city:
                setClass(SelectCityActivity.class);
                break;
            //搜索
            case R.id.img_search:
                setClass(SearchActivity.class);
                break;
            //私享量房
            case R.id.lin_sxlf:
                gotoWebView(3);
                break;
            //无忧验房
            case R.id.rel_wyyf:
                gotoWebView(4);
                break;
            //专车接驾
            case R.id.rel_zcjj:
                gotoWebView(5);
                break;
            //专属设计
            case R.id.rel_zssj:
                gotoWebView(6);
                break;
            //我的风格
            case R.id.rel_wdfg:
                gotoWebView(7);
                break;
            //计算器
            case R.id.rel_jsq:
                gotoWebView(8);
                break;
            //案例精选更多
            case R.id.tv_more_case:
                setClass(CaseListActivity.class);
                break;
            //更多设计师
            case R.id.tv_more_designer:
                setClass(DesignerListActivity.class);
                break;
            //更多在施工地
            case R.id.tv_more_construction:
                setClass(ConstructionListActivity.class);
                break;
            //更多装修攻略
            case R.id.tv_more_before:
                setClass(DecorateProgressActivity.class);
                break;
            //热装楼盘更多
            case R.id.tv_more_hot:
                setClass(BuildingListActivity.class);
                break;
            //附近门店更多
            case R.id.tv_more_near:
                setClass(ExperienceActivity.class);
                break;
            //进入品牌菜单
            case R.id.img_brand:
                EventBus.getDefault().post(new EventBusType(EventStatus.GO_TO_BRAND));
                break;
            //进入消息界面
            case R.id.img_news:
                 setClass(NewsActivity.class);
                 break;
            default:
                break;
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //左上角展示城市名称
            case EventStatus.SHOW_MAIN_CITY:
                 tvCity.setText(SPUtil.getInstance(this).getString(SPUtil.CITY));
                 break;
            //显示banner
            case EventStatus.SHOW_MAIN_BANNER:
                setBanner((List<BannerBean>) eventBusType.getObject());
                break;
            //显示案例
            case EventStatus.SHOW_MAIN_CASE:
                showCase((List<MainCase.MainCaseBean>) eventBusType.getObject());
                break;
            //显示案例图片
            case EventStatus.SHOW_MAIN_CASE_IMG:
                  showCaseImg((List<CaseImg>) eventBusType.getObject());
                  break;
            //显示设计师
            case EventStatus.SHOW_MAIN_DESIGNER:
                showDesigner((List<MainDesigner.MainDesignerBean>) eventBusType.getObject());
                scrollView.scrollTo(0, 0);
                break;
            //展示热装楼盘
            case EventStatus.SHOW_MAIN_BUILDONG:
                showBuilding((List<MainBuilding.BuildingBean>) eventBusType.getObject());
                scrollView.scrollTo(0, 0);
                break;
            //展示在施工地
            case EventStatus.SHOW_MAIN_CONSTRUCTION:
                showConstruction((List<ConstructionList.ConstructionBean>) eventBusType.getObject());
                scrollView.scrollTo(0, 0);
                break;
            //展示附近门店
            case EventStatus.SHOW_MAIN_NEAR:
                showNear((List<NearList.NearBean>) eventBusType.getObject());
                scrollView.scrollTo(0, 0);
                break;
            //展示品牌图片
            case EventStatus.SHOW_MAIN_BRAND:
                  String imgUrl= (String) eventBusType.getObject();
                 Glide.with(this).load(imgUrl).centerCrop().into(imgBrand);
                  break;
            //展示装修攻略
            case EventStatus.SHOW_MAIN_DECORATE:
                showDecorate((List<MainDecorate.DecorateBean>) eventBusType.getObject());
                scrollView.scrollTo(0, 0);
                break;
            //展示活动
            case EventStatus.SHOW_MAIN_MOVE:
                showActivity((Move.MoveBean) eventBusType.getObject());
                break;
            //切换城市
            case EventStatus.SELECT_CITY_SUCCESS:
                 updateCity();
                 break;
            //展示消息数量
            case EventStatus.SHOW_NEWS_NUM:
                final NewsNum.NewsNumBean newsNumBean= (NewsNum.NewsNumBean) eventBusType.getObject();
                if(newsNumBean==null){
                    return;
                }
                if(newsNumBean.getHdcount()>0 || newsNumBean.getDtcount()>0 || newsNumBean.getGgcount()>0){
                    viewNews.setVisibility(View.VISIBLE);
                }else{
                    viewNews.setVisibility(View.GONE);
                }
                  break;
            default:
                break;
        }
    }



    /**
     * 设置顶部广告/精选专题的banner轮播图
     */
    public void setBanner(List<BannerBean> list) {
       try {
           if(list==null || list.size()==0){
               list=new ArrayList<>();
               banner.update(list);
               return;
           }
           banner.setVisibility(View.VISIBLE);
           //设置样式，里面有很多种样式可以自己都看看效果
           banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
           //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
           banner.setBannerAnimation(Transformer.Default);
           //设置图片加载器，图片加载器在下方
           banner.setImageLoader(new ABImageLoader());
           //设置图片集合
           banner.setImages(list);
           //设置轮播间隔时间
           banner.setDelayTime(3000);
           //设置是否为自动轮播，默认是true
           banner.isAutoPlay(true);
           //设置指示器的位置，小点点，居中显示
           banner.setIndicatorGravity(BannerConfig.CENTER);
           //banner设置方法全部调用完毕时最后调用
           banner.start();
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    public class ABImageLoader extends ImageLoader {
        public void displayImage(Context context, Object path, ImageView imageView) {
            if(path==null){
                return;
            }
            BannerBean bannerBean = (BannerBean) path;
            Glide.with(context).load(bannerBean.getImg()).into(imageView);
            imageView.setTag(R.id.tag1,bannerBean);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final BannerBean bannerBean = (BannerBean) v.getTag(R.id.tag1);
                    Intent intent = new Intent(MainActivity.this, DecorateWebView.class);
                    intent.putExtra("type", 3);
                    intent.putExtra("id",bannerBean.getTypeid());
                    if(bannerBean.getType()==2){
                        intent.putExtra("url", bannerBean.getUrl());
                    }
                    intent.putExtra("title",bannerBean.getTitle());
                    startActivity(intent);
                }
            });
        }
    }


    /**
     * 显示案例数据
     */
    private void showCase(List<MainCase.MainCaseBean> list) {
        if(list==null){
            list=new ArrayList<>();
        }
        if(list.size()==0){
            linCase.setVisibility(View.GONE);
            return;
        }else{
            linCase.setVisibility(View.VISIBLE);
        }
        listType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        MainTypeAdapter mainTypeAdapter = new MainTypeAdapter(this, list, new MainTypeAdapter.OnItemClickListener() {
            public void onItemClick(MainCase.MainCaseBean mainCaseBean) {
                //显示案例图片
                if(mainCaseBean.getList()!=null){
                    showCaseImg(mainCaseBean.getList());
                }else{
                    showCaseImg(null);
                    mainPersenter.getMainCaseImg(mainCaseBean.getStyle());
                }
            }
        });
        listType.setAdapter(mainTypeAdapter);
        //显示案例图片
        if(list.size()>0){
            showCaseImg(list.get(0).getList());
        }
    }


    /**
     * 显示案例图片
     */
    private void showCaseImg(List<CaseImg> caseImgList) {
        if(caseImgList==null){
            caseImgList=new ArrayList<>();
        }
        final int totalSize=caseImgList.size();
        //显示图片数量
        tvCaseNum.setText(totalSize>0 ? "1" : "0");
        tvCaseTotalNum.setText("/"+totalSize);
        if(totalSize==0){
            recycleView.setAdapter(null);
            recycleView.setVisibility(View.GONE);
            return;
        }
        recycleView.setVisibility(View.VISIBLE);
        //显示图片列表
        recycleView.setOrientation(DSVOrientation.HORIZONTAL);
        recycleView.setAdapter(new CardImgAdapter(this, caseImgList));
        recycleView.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());

        //设置左右循环切换
        recycleView.setHasFixedSize(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        //防止报错An instance of OnFlingListener already set.
        recycleView.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(recycleView);
        recycleView.scrollToPosition(totalSize * 10);
        recycleView.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                tvCaseNum.setText(String.valueOf((adapterPosition%totalSize)+1));
            }
        });
    }


    /**
     * 显示设计师
     */
    private void showDesigner(List<MainDesigner.MainDesignerBean> list) {
        if (list == null) {
            list=new ArrayList<>();
        }
        if(list.size()==0){
            linDesigner.setVisibility(View.GONE);
            return;
        }else{
            linDesigner.setVisibility(View.VISIBLE);
        }
        listDesigner.setOrientation(DSVOrientation.HORIZONTAL);
        listDesigner.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
//        final LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        listDesigner.setLayoutManager(linearLayoutManager);
        listDesigner.setAdapter(new MainDesignerAdapter(this, list));
        tvDesignerNum.setText(list.size()>0 ? "1" : "0");
        tvDesignerTotalnum.setText("/"+list.size());
        listDesigner.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                tvDesignerNum.setText(String.valueOf(++adapterPosition));
            }
        });
//        listDesigner.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                int firstVisibleItemPosition=0;
//                switch (newState){  //判断RecyclerView滑动不同的状态
//
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        //获得当前显示在第一个item的位置
//                        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                        break;
//                    case RecyclerView.SCROLL_STATE_DRAGGING:
//                        //获得当前显示在第一个item的位置
//                        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                        tvDesignerNum.setText(String.valueOf(++firstVisibleItemPosition));
//                        LogUtils.e(firstVisibleItemPosition+"++++++++++++++++++a");
//                        break;
//                    case RecyclerView.SCROLL_STATE_SETTLING:
//                        LogUtils.e(firstVisibleItemPosition+"++++++++++++++++++b");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
    }


    /**
     * 展示热装楼盘
     */
    private void showBuilding(List<MainBuilding.BuildingBean> list) {
        if(list==null){
            list=new ArrayList<>();
        }
        if(list.size()==0){
            linHot.setVisibility(View.GONE);
            return;
        }else{
            linHot.setVisibility(View.VISIBLE);
        }
        final int totalSize=list.size();
        tvHotNum.setText(totalSize>0 ? "1" : "0");
        tvHotTotalnum.setText("/"+totalSize);
        if(totalSize==0){
            listHot.setAdapter(null);
            return;
        }
        listHot.setOrientation(DSVOrientation.HORIZONTAL);
        listHot.setAdapter(new MainHotAdapter(this, list));

        //设置左右循环切换
        listHot.setHasFixedSize(true);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        //防止报错An instance of OnFlingListener already set.
        listHot.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(listHot);
        listHot.scrollToPosition(totalSize * 10);
        listHot.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                tvHotNum.setText(String.valueOf((adapterPosition%totalSize)+1));
            }
        });
    }


    /**
     * 展示在施工地
     *
     * @param list
     */
    private void showConstruction(List<ConstructionList.ConstructionBean> list) {
        if(list==null || list.size()==0){
            linConstruction.setVisibility(View.GONE);
            return;
        }else{
            linConstruction.setVisibility(View.VISIBLE);
        }
        MainConsAdapter mainConsAdapter=new MainConsAdapter(this,list);
        listConstruction.setAdapter(mainConsAdapter);
    }


    /**
     * 展示附近门店
     */
    private void showNear(List<NearList.NearBean> list) {
        if(list==null){
            list=new ArrayList<>();
        }
        if(list.size()==0){
            linNear.setVisibility(View.GONE);
            return;
        }else{
            linNear.setVisibility(View.VISIBLE);
        }
        listNear.setOrientation(DSVOrientation.HORIZONTAL);
        listNear.setAdapter(new MainNearAdapter(this, list));
        tvNearNum.setText(list.size()>0 ? "1" : "0");
        tvNearTotalNum.setText("/"+list.size());
        listNear.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                tvNearNum.setText(String.valueOf(++adapterPosition));
            }
        });
    }


    /**
     * 展示装修攻略数据
     *
     * @param list
     */
    private void showDecorate(List<MainDecorate.DecorateBean> list) {
        MainDecorateAdapter mainDecorateAdapter = new MainDecorateAdapter(this, list);
        listDecorate.setAdapter(mainDecorateAdapter);
    }


    /**
     * 展示活动
     *
     * @param moveBean
     */
    private void showActivity(final Move.MoveBean moveBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_main_move, null);
        final Dialog dialog = DialogUtil.getDialog(this, view);
        ImageView imgHead = view.findViewById(R.id.img_head);
        final ImageView imgCheck=view.findViewById(R.id.img_check);
        Glide.with(this).load(moveBean.getImg()).into(imgHead);
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DecorateWebView.class);
                intent.putExtra("type", 11);
                intent.putExtra("id",moveBean.getId());
                intent.putExtra("url", moveBean.getUrl());
                startActivity(intent);
                dialog.dismiss();
            }
        });
        imgCheck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                if(v.getTag().equals("0")){
                    v.setTag("1");
                    imgCheck.setImageResource(R.mipmap.adve_check_yes);
                    mainPersenter.addActivityId(String.valueOf(moveBean.getId()),true);
                }else{
                    v.setTag("0");
                    imgCheck.setImageResource(R.mipmap.adve_check_no);
                    mainPersenter.addActivityId(String.valueOf(moveBean.getId()),false);
                }
            }
        });
        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    /**
     * 切换城市
     */
    private void updateCity(){
        //清空所有数据
        setBanner(null);
        showCase(null);
        showCaseImg(null);
        showDesigner(null);
        showBuilding(null);
        showConstruction(null);
        showNear(null);
        showDecorate(null);
        String city=SPUtil.getInstance(activity).getString(SPUtil.CITY);
        if(TextUtils.isEmpty(city)){
            city="全国城市";
        }
        tvCity.setText(city);
        //重新查询所有数据
        mainPersenter.getSite();
    }


    @Override
    public void onRefresh(View view) {
        //重新查询所有数据
        mainPersenter.getSite();
    }

    @Override
    public void onLoadMore(View view) {

    }


    /**
     * 监听scrollview滑动
     */
    public boolean onTouch(View v, final MotionEvent event) {
        EventBus.getDefault().post(new EventBusType(EventStatus.SUSPENSION_STATUS, event));
        return false;
    }

    /**
     * 加载h5页面
     * @param type
     */
    private void gotoWebView(int type){
        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
        //获取消息数量
        if(MyApplication.isLogin()){
            mainPersenter.getNewsNum();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
