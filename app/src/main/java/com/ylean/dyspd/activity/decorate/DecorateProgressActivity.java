package com.ylean.dyspd.activity.decorate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.decorate.DesignerProgressAdapter;
import com.ylean.dyspd.adapter.decorate.DialogDecorateAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.DecorateType;
import com.zxdc.utils.library.bean.MainDecorate;
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
/**
 * 装修前，装修中
 * Created by Administrator on 2019/11/9.
 */

public class DecorateProgressActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    //分类数据集合
    private List<DecorateType.TypeBean> typeList;
    //列表adapter
    private DesignerProgressAdapter designerProgressAdapter;
    //分类id
    private int cid=-1;
    //页数
    private int page = 1;
    public String title;
    private List<MainDecorate.DecorateBean> listAll = new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decorate_progress);
        ButterKnife.bind(this);
        initView();
        //获取装修攻略分类
        DialogUtil.showProgress(this,"数据加载中...");
        getDecorateType();
    }


    /**
     * 初始化
     */
    private void initView() {
        cid = getIntent().getIntExtra("cid", -1);
        title=getIntent().getStringExtra("title");
        tvType.setText(title);
        reList.setMyRefreshLayoutListener(this);
        designerProgressAdapter=new DesignerProgressAdapter(this,listAll,title);
        listView.setAdapter(designerProgressAdapter);
        listView.setDivider(null);
    }

    @OnClick({R.id.lin_back, R.id.tv_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();

                //埋点
                switch (title){
                    case "装修前":
                        MobclickAgent.onEvent(this, "decorate_before_back");
                        break;
                    case "装修中":
                        MobclickAgent.onEvent(this, "decorate_the_back");
                        break;
                    case "装修后":
                        MobclickAgent.onEvent(this, "decorate_after_back");
                        break;
                    default:
                        break;
                }
                break;
            //选择装修类型
            case R.id.tv_type:
                showSelectDialog();
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
                         typeList = decorateType.getData();
                         //开始查询列表数据
                         if(cid!=-1){
                             onRefresh(null);
                         }else{
                             searchList(0);
                         }
                     } else {
                        ToastUtil.showLong(decorateType.getDesc());
                     }
                     break;
                //下刷
                case HandlerConstant.GET_DECORATE_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((MainDecorate) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_DECORATE_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((MainDecorate) msg.obj);
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
    private void refresh(MainDecorate mainDecorate) {
        if (mainDecorate == null) {
            return;
        }
        if (mainDecorate.isSussess()) {
            List<MainDecorate.DecorateBean> list = mainDecorate.getData();
            listAll.addAll(list);
            designerProgressAdapter.notifyDataSetChanged();
            if (list.size() < HttpMethod.pageSize) {
                reList.setIsLoadingMoreEnabled(false);
            }
        } else {
            ToastUtil.showLong(mainDecorate.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page = 1;
        getDecorateList(HandlerConstant.GET_DECORATE_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getDecorateList(HandlerConstant.GET_DECORATE_LIST_SUCCESS2);
    }


    /**
     * 开始查询列表数据
     */
    private void searchList(int position){
        cid=typeList.get(position).getCommonid();
        tvType.setText(typeList.get(position).getCommonvalue());
        onRefresh(null);
    }


    /**
     * 选择装修类型
     */
    private void showSelectDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_decorate, null);
        final PopupWindow popupWindow = DialogUtil.showPopWindow(view);
        popupWindow.showAsDropDown(tvType);

        ListView listView = view.findViewById(R.id.listView);
        DialogDecorateAdapter dialogDecorateAdapter = new DialogDecorateAdapter(this, typeList);
        dialogDecorateAdapter.setId(cid);
        listView.setAdapter(dialogDecorateAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                //开始查询列表数据
                searchList(position);

                //埋点
                switch (title){
                    case "装修前":
                        MobclickAgent.onEvent(DecorateProgressActivity.this, "decorate_before_switch");
                        break;
                    case "装修中":
                        MobclickAgent.onEvent(DecorateProgressActivity.this, "decorate_the_switch");
                        break;
                    case "装修后":
                        MobclickAgent.onEvent(DecorateProgressActivity.this, "decorate_after_switch");
                        break;
                    default:
                        break;
                }
            }
        });
    }


    /**
     * 获取装修攻略分类
     */
    private void getDecorateType() {
        HttpMethod.getDecorateType(handler);
    }

    /**
     * 获取装修列表
     */
    private void getDecorateList(int index) {
        HttpMethod.getDecorateList(String.valueOf(page), String.valueOf(cid), index, handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
