package com.ylean.dyspd.activity.user.collection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.ScreeningCaseActivity;
import com.ylean.dyspd.adapter.user.collection.CollectionGalleryAdapter;
import com.ylean.dyspd.view.CollectionEmptyView;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.GalleryList;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class CollectionGalleryActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.img_new)
    ImageView imgNew;
    @BindView(R.id.lin_select)
    LinearLayout linSelect;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.empryView)
    CollectionEmptyView empryView;
    private CollectionGalleryAdapter collectionGalleryAdapter;
    /**
     * 最新
     * 1：升序
     * 2：降序
     */
    private int newSort=2;
    //页数
    private int page=1;
    /**
     * 筛选属性:风格,元素,空间
     */
    private String dstyle,element,housespace;
    //数据集合
    private List<GalleryList.GalleryBean> listAll=new ArrayList<>();
    //收藏id
    private int id;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coll_details);
        ButterKnife.bind(this);
        initView();
        //获取收藏的案例图库
        getCollGallery(HandlerConstant.GET_GALLERY_LIST_SUCCESS1);
    }


    /**
     * 初始化
     */
    private void initView() {
        tvTitle.setText("收藏的图库");
        reList.setMyRefreshLayoutListener(this);
        collectionGalleryAdapter = new CollectionGalleryAdapter(this, listAll);
        listView.setAdapter(collectionGalleryAdapter);
    }

    @OnClick({R.id.lin_back, R.id.rel_new, R.id.tv_screening})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //最新
            case R.id.rel_new:
                if(newSort==1){
                    newSort=2;
                    imgNew.setImageResource(R.mipmap.click_bottom);
                }else{
                    newSort=1;
                    imgNew.setImageResource(R.mipmap.click_top);
                }
                //刷新列表
                onRefresh(null);
                break;
            //筛选
            case R.id.tv_screening:
                setClass(ScreeningCaseActivity.class, 100);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what) {
                //下刷
                case HandlerConstant.GET_GALLERY_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((GalleryList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_GALLERY_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((GalleryList) msg.obj);
                    break;
                //取消收藏回执
                case HandlerConstant.CANCLE_COLL_SUCCESS:
                      final BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                      if(baseBean.isSussess()){
                          for (int i=0;i<listAll.size();i++){
                                if(listAll.get(i).getId()==id){
                                    listAll.remove(i);
                                    break;
                                }
                          }
                          collectionGalleryAdapter.notifyDataSetChanged();
                          ToastUtil.showLong("取消收藏成功");
                      }else{
                          ToastUtil.showLong(baseBean.getDesc());
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


    /**
     * 刷新界面数据
     */
    private void refresh(GalleryList galleryList) {
        if (galleryList == null) {
            return;
        }
        if (galleryList.isSussess()) {
            List<GalleryList.GalleryBean> list = galleryList.getData();
            listAll.addAll(list);
            collectionGalleryAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
            //没数据显示的视图
            if(list.size()>0){
                empryView.setVisibility(View.GONE);
            }else{
                empryView.setType(5);
                empryView.setVisibility(View.VISIBLE);
            }
        } else {
            ToastUtil.showLong(galleryList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getCollGallery(HandlerConstant.GET_GALLERY_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getCollGallery(HandlerConstant.GET_GALLERY_LIST_SUCCESS2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && data != null) {
            dstyle=data.getStringExtra("styleName");
            element=data.getStringExtra("elementName");
            housespace=data.getStringExtra("spaceName");
            //刷新列表
            onRefresh(null);
        }
    }


    /**
     * 获取收藏的案例图库
     */
    private void getCollGallery(int index) {
        HttpMethod.getCollGallery(dstyle,element,housespace,String.valueOf(page),String.valueOf(newSort),index,handler);
    }


    /**
     * 取消收藏
     */
    public void cancleColl(int id){
        this.id=id;
        DialogUtil.showProgress(this,"取消收藏中...");
        HttpMethod.cancleColl(String.valueOf(id),handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
