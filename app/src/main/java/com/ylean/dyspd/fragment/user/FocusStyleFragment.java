package com.ylean.dyspd.fragment.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagCloudView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.init.HobbyActivity;
import com.ylean.dyspd.adapter.init.HobbyAdapter;
import com.ylean.dyspd.adapter.user.FocusStyleAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.FocusCase;
import com.zxdc.utils.library.bean.Hobby;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 关注的风格
 * Created by Administrator on 2019/11/17.
 */

public class FocusStyleFragment extends BaseFragment {

    @BindView(R.id.cloudView)
    TagCloudView cloudView;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    Unbinder unbinder;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    private FocusStyleAdapter focusStyleAdapter;
    //我关注过的风格
    private String myStyle;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    View view=null;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_focus_style, container, false);
        unbinder = ButterKnife.bind(this, view);
        //获取关注的风格
        getFocusCase();

        /**
         * 确认添加
         */
        tvAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(focusStyleAdapter==null){
                    return;
                }
                if(focusStyleAdapter.map.size()==0){
                    ToastUtil.showLong("请选择要添加的风格");
                    return;
                }
                StringBuilder stringBuilder=new StringBuilder();
                for(Map.Entry<String, String> map:focusStyleAdapter.map.entrySet()){
                    stringBuilder.append(map.getKey()+",");
                }
                String styles=stringBuilder.substring(0,stringBuilder.length()-1);
                //设置关注的风格
                setFocus(styles);
            }
        });
        return view;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //返回我关注过的风格
                case HandlerConstant.GET_FOCUS_CASE_SUCCESS:
                      final FocusCase focusCase= (FocusCase) msg.obj;
                      if(focusCase==null){
                          break;
                      }
                      if(focusCase.isSussess()){
                          myStyle=focusCase.getData();
                          //获取推荐关注的风格
                          getHobby();
                      }else{
                          ToastUtil.showLong(focusCase.getDesc());
                      }
                      break;
                //获取推荐关注的风格
                case HandlerConstant.GET_HOBBY_SUCCESS:
                     Hobby hobby= (Hobby) msg.obj;
                     if (hobby == null) {
                         break;
                     }
                     if (hobby.isSussess()) {
                          focusStyleAdapter=new FocusStyleAdapter(mActivity,hobby.getData(),myStyle);
                          cloudView.setAdapter(focusStyleAdapter);
                     } else {
                        ToastUtil.showLong(hobby.getDesc());
                     }
                      break;
                //设置风格回执
                case HandlerConstant.SET_FOCUS_SUCCESS:
                      final BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
                      }
                    if(baseBean.isSussess()){
                        ToastUtil.showLong("设置成功");
                    }else{
                        ToastUtil.showLong(baseBean.getDesc());
                    }
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
     * 获取推荐关注的风格
     */
    private void getHobby() {
        DialogUtil.showProgress(mActivity,"数据加载中...");
        HttpMethod.getHobby(handler);
    }


    /**
     * 获取关注的风格
     */
    private void getFocusCase(){
        if(isVisibleToUser && view!=null && cloudView.getChildCount()==0){
            DialogUtil.showProgress(mActivity,"数据加载中...");
            HttpMethod.getFocusCase(handler);
        }
    }


    /**
     * 设置关注的风格
     */
    private void setFocus(String style){
        DialogUtil.showProgress(mActivity,"添加中...");
        HttpMethod.setFocus(style,handler);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        getFocusCase();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        removeHandler(handler);
    }
}

