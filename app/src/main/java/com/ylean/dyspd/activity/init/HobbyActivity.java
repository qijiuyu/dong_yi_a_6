package com.ylean.dyspd.activity.init;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagCloudView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.init.HobbyAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.FocusCase;
import com.zxdc.utils.library.bean.Hobby;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 喜好
 * Created by Administrator on 2019/12/1.
 */

public class HobbyActivity extends BaseActivity implements MyRefreshLayoutListener {

    @BindView(R.id.cloudView)
    TagCloudView cloudView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private HobbyAdapter hobbyAdapter;
    /**
     * 1：从动态里面进入的
     */
    private int type;
    //我关注过的风格
    private String myStyle;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby);
        ButterKnife.bind(this);
        initView();
        //获取关注的风格
        getFocusCase();
    }

    /**
     * 初始化
     */
    private void initView() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            tvTitle.setText("风格");
        }
        reList.setMyRefreshLayoutListener(this);
        reList.setIsLoadingMoreEnabled(false);
        if (type == 1) {
            tvGo.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.lin_back, R.id.tv_go, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_go:
                setClass(TripServiceActivity.class);
                break;
            case R.id.tv_confirm:
                if (hobbyAdapter == null) {
                    return;
                }
                if (hobbyAdapter.map.size() == 0) {
                    ToastUtil.showLong("请选择自己喜好的风格");
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (Map.Entry<String, String> map : hobbyAdapter.map.entrySet()) {
                    stringBuilder.append(map.getKey() + ",");
                }
                String styles = stringBuilder.substring(0, stringBuilder.length() - 1);
                //设置关注的风格
                setFocus(styles);
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            reList.refreshComplete();
            switch (msg.what) {
                //返回我关注过的风格
                case HandlerConstant.GET_FOCUS_CASE_SUCCESS:
                    final FocusCase focusCase = (FocusCase) msg.obj;
                    if (focusCase == null) {
                        break;
                    }
                    if (focusCase.isSussess()) {
                        myStyle = focusCase.getData();
                        //获取推荐关注的风格
                        getHobby();
                    } else {
                        ToastUtil.showLong(focusCase.getDesc());
                    }
                    break;
                //获取推荐关注的风格
                case HandlerConstant.GET_HOBBY_SUCCESS:
                    Hobby hobby = (Hobby) msg.obj;
                    if (hobby == null) {
                        break;
                    }
                    if (hobby.isSussess()) {
                        hobbyAdapter = new HobbyAdapter(HobbyActivity.this, hobby.getData(), myStyle);
                        cloudView.setAdapter(hobbyAdapter);
                    } else {
                        ToastUtil.showLong(hobby.getDesc());
                    }
                    break;
                //设置风格回执
                case HandlerConstant.SET_FOCUS_SUCCESS:
                    final BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean == null) {
                        break;
                    }
                    if (baseBean.isSussess()) {
                        if (type == 1) {
                            finish();
                        } else {
                            setClass(TripServiceActivity.class);
                        }
                        ToastUtil.showLong("设置成功");
                    } else {
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

    @Override
    public void onRefresh(View view) {
        //获取关注的风格
        getFocusCase();
    }

    @Override
    public void onLoadMore(View view) {

    }


    /**
     * 获取关注的风格
     */
    private void getFocusCase() {
        DialogUtil.showProgress(this, "数据加载中...");
        HttpMethod.getFocusCase(handler);
    }


    /**
     * 获取推荐关注的风格
     */
    private void getHobby() {
        DialogUtil.showProgress(this, "加载中...");
        HttpMethod.getHobby(handler);
    }

    /**
     * 设置关注的风格
     */
    private void setFocus(String style) {
        DialogUtil.showProgress(this, "添加中...");
        HttpMethod.setFocus(style, handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
