package com.ylean.dyspd.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.main.HotCityAdapter;
import com.ylean.dyspd.adapter.user.CollectionAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.CollNum;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的收藏
 * Created by Administrator on 2019/11/14.
 */

public class CollectionActivity extends BaseActivity {

    @BindView(R.id.listView)
    RecyclerView listView;
    private List<Integer> numList=new ArrayList<>();
    private CollectionAdapter collectionAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initView();
        //获取收藏数量
        getCollNum();
    }


    private void initView(){
        List<String> nameList=new ArrayList<>();
        List<Integer> imgList=new ArrayList<>();
        nameList.add("风格案例");
        imgList.add(R.mipmap.collection_fgal);
        nameList.add("设计师");
        imgList.add(R.mipmap.collection_sjs);
        nameList.add("案例图库");
        imgList.add(R.mipmap.collection_altk);
        nameList.add("vr样板间");
        imgList.add(R.mipmap.collection_vr);
        nameList.add("软装范本");
        imgList.add(R.mipmap.collection_rzfb);
        nameList.add("在施工地");
        imgList.add(R.mipmap.collection_zsgd);
        nameList.add("热装楼盘");
        imgList.add(R.mipmap.collection_rzlp);
        nameList.add("体验门店");
        imgList.add(R.mipmap.collection_tymd);
        nameList.add("资讯");
        imgList.add(R.mipmap.collection_zx);

        listView.setLayoutManager(new GridLayoutManager(this, 3));
        collectionAdapter=new CollectionAdapter(this,nameList,imgList,numList);
        listView.setAdapter(collectionAdapter);

        //返回
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CollectionActivity.this.finish();
            }
        });

    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取收藏数量
                case HandlerConstant.GET_COLL_NUM_SUCCESS:
                      CollNum collNum= (CollNum) msg.obj;
                      if(collNum==null){
                          break;
                      }
                      if(collNum.isSussess() && collNum.getData()!=null){
                          numList.add(collNum.getData().getStylecasecount());
                          numList.add(collNum.getData().getDesignercount());
                          numList.add(collNum.getData().getImgcasecount());
                          numList.add(collNum.getData().getVrcasecount());
                          numList.add(collNum.getData().getSoftcasecount());
                          numList.add(collNum.getData().getConstruccount());
                          numList.add(collNum.getData().getLoupancount());
                          numList.add(collNum.getData().getShopcount());
                          numList.add(collNum.getData().getNewscount());
                          collectionAdapter.notifyDataSetChanged();
                      }else{
                          ToastUtil.showLong(collNum.getDesc());
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
     * 获取收藏数量
     */
    private void getCollNum(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getCollNum(handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
