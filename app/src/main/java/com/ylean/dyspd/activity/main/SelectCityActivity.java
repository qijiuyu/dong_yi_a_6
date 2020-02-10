package com.ylean.dyspd.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.main.HotCityAdapter;
import com.ylean.dyspd.adapter.main.SortAdapter;
import com.zxdc.utils.library.bean.City;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.persenter.main.SelectCityPersenter;
import com.ylean.dyspd.utils.pinyin.CharacterParser;
import com.ylean.dyspd.utils.pinyin.PinyinComparator;
import com.ylean.dyspd.utils.pinyin.SideBar;
import com.ylean.dyspd.utils.pinyin.SortModel;
import com.ylean.dyspd.view.TagsLayout;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.HotCity;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 切换城市
 * Created by Administrator on 2019/11/12.
 */
public class SelectCityActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    private SortAdapter sortAdapter;
    private SelectCityPersenter selectCityPersenter;
    /**
     * 头部view
     * 包含历史城市，热门城市
     */
    private View headView;
    /**
     * 0：首页进入的
     * 1：装修中热门楼盘的筛选页面进入
     */
    private int gotoType;
    //所有城市集合
    private List<City.CityBean> cityList=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        ButterKnife.bind(this);
        //注册eventBus
        EventBus.getDefault().register(this);
        initView();
        //获取所有城市
        selectCityPersenter.getCity();
        //获取热门城市
        selectCityPersenter.getHotCity();

        //返回
        linBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SelectCityActivity.this.finish();
            }
        });
    }


    /**
     * 初始化
     */
    private void initView() {
        gotoType=getIntent().getIntExtra("gotoType",0);
        selectCityPersenter=new SelectCityPersenter(this);
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                String zm=String.valueOf(s.charAt(0));
                for (int i=0,len=cityList.size();i<len;i++){
                     if(cityList.get(i).getCode().equals(zm)){
                         listView.setSelection(i+1);
                         break;
                     }
                }

            }
        });
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //显示热门城市
            case EventStatus.SHOW_HOT_CITY:
                  headView= LayoutInflater.from(this).inflate(R.layout.taglayout,null);
                  //显示历史城市
                  showHistoryCity();
                  //显示热门城市
                  showHotCity((List<HotCity.HotCityBean>) eventBusType.getObject());
                  listView.addHeaderView(headView);
                  break;
            //显示所有城市
            case EventStatus.SHOW_ALL_CITY:
                 cityList= (List<City.CityBean>) eventBusType.getObject();
                 sortAdapter = new SortAdapter(SelectCityActivity.this, cityList);
                 listView.setAdapter(sortAdapter);
                  break;
            default:
                break;
        }
    }


    /**
     *显示历史城市
     */
    private void showHistoryCity(){
        final TagsLayout tagsLayout=headView.findViewById(R.id.tags);
        selectCityPersenter.showHistoryCity(tagsLayout);
        //清空搜索历史
        headView.findViewById(R.id.img_clear).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SPUtil.getInstance(SelectCityActivity.this).removeMessage(SPUtil.HISTORY_CITY);
                tagsLayout.removeAllViews();
            }
        });
    }


    /**
     * 显示热门城市
     */
    private void showHotCity(List<HotCity.HotCityBean> list){
        HotCity.HotCityBean totaoCity=new HotCity.HotCityBean();
        totaoCity.setCityname("全国所有城市");
        list.add(0,totaoCity);
        HotCity.HotCityBean localCity=new HotCity.HotCityBean();
        localCity.setCityname("我的附近");
        list.add(1,localCity);

        RecyclerView listHot=headView.findViewById(R.id.list_hot);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        listHot.setLayoutManager(gridLayoutManager);
        listHot.setAdapter(new HotCityAdapter(this, list));
    }


    /**
     * 选择城市
     */
    public void selectCity(String city){
        if(TextUtils.isEmpty(city)){
            ToastUtil.showLong("选择失败");
        }
        if(city.equals("全国所有城市")){
            city="";
        }
        if(city.equals("我的附近")){
            city=SPUtil.getInstance(this).getString(SPUtil.LOCATION_CITY);
        }
        //保存点击过的历史城市
        selectCityPersenter.saveHistoryCity(city);
        if(gotoType==0){
            //保存城市名称
            SPUtil.getInstance(activity).addString(SPUtil.CITY,city);
            EventBus.getDefault().post(new EventBusType(EventStatus.SELECT_CITY_SUCCESS));
        }else{
            if(TextUtils.isEmpty(city)){
                return;
            }
            Intent intent=new Intent();
            intent.putExtra("city",city);
            setResult(100,intent);
        }
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
