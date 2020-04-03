package com.ylean.dyspd.activity.decorate.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.decorate.DesignerListAdapter;
import com.ylean.dyspd.adapter.decorate.SearchDesignerAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.DesignerList;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 搜索设计师
 * Created by Administrator on 2019/11/25.
 */
public class SearchDesignerActivity extends BaseActivity implements TextView.OnEditorActionListener,MyRefreshLayoutListener {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.tv_no)
    TextView tvNo;
    private SearchDesignerAdapter designerListAdapter;
    //页数
    private int page=1;
    private List<DesignerList.DesignerBean> listAll=new ArrayList<>();
    //搜索的关键字
    private String name;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dsigner);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        etKey.setOnEditorActionListener(this);
        reList.setMyRefreshLayoutListener(this);
        designerListAdapter = new SearchDesignerAdapter(this,listAll);
        listView.setAdapter(designerListAdapter);
        listView.setDivider(null);
        //取消
        tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SearchDesignerActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            name= etKey.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                ToastUtil.showLong("请输入您要搜索的关键字！");
                return false;
            }
            lockKey(etKey);
            //查询
            page=1;
            getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);

        }
        return false;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_DESIGNER_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((DesignerList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_DESIGNER_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((DesignerList) msg.obj);
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
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
    private void refresh(DesignerList designerList){
        if(designerList==null){
            return;
        }
        if(designerList.isSussess()){
            List<DesignerList.DesignerBean> list=designerList.getData();
            listAll.addAll(list);
            designerListAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
            if(listAll.size()==0){
                tvNo.setVisibility(View.VISIBLE);
            }else{
                tvNo.setVisibility(View.GONE);
            }
        }else{
            ToastUtil.showLong(designerList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getDesignerList(HandlerConstant.GET_DESIGNER_LIST_SUCCESS2);
    }


    /**
     * 获取设计师列表
     */
    private void getDesignerList(int index){
        HttpMethod.getDesignerList(null,null,name,String.valueOf(page),null,null,null,null,index,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
