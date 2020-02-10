package com.ylean.dyspd.fragment.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.DesignerListActivity;
import com.ylean.dyspd.adapter.user.DesignerFragmentAdapter;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.DesignerList;
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
import butterknife.Unbinder;

/**
 * 设计师
 * Created by Administrator on 2019/11/8.
 */

public class DesignerFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.lin_no)
    LinearLayout linNo;
    Unbinder unbinder;
    private DesignerFragmentAdapter designerFragmentAdapter;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    //页码
    private int page=1;
    private List<DesignerList.DesignerBean> listAll=new ArrayList<>();
    //关注的id
    private int id;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View view=null;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_designer, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        designerFragmentAdapter=new DesignerFragmentAdapter(mActivity,listAll,cancleFocus);
        listView.setAdapter(designerFragmentAdapter);
        /**
         * 去关注设计师
         */
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setClass(DesignerListActivity.class);
            }
        });

        /**
         * 获取关注的设计师列表
         */
        if(listAll.size()==0){
            getFocusDesigner(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
        }
        return view;
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
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
                //取消关注回执
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
                        designerFragmentAdapter.notifyDataSetChanged();
                    }
                    ToastUtil.showLong(baseBean.getDesc());
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
            designerFragmentAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
            if(listAll.size()==0){
                linNo.setVisibility(View.VISIBLE);
            }else{
                linNo.setVisibility(View.GONE);
            }
        }else{
            ToastUtil.showLong(designerList.getDesc());
        }
    }


    /**
     * 下刷
     * @param view
     */
    public void onRefresh(View view) {
        page=1;
        getFocusDesigner(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
    }

    /**
     * 上拉加载更多
     * @param view
     */
    public void onLoadMore(View view) {
        page++;
        getFocusDesigner(HandlerConstant.GET_DESIGNER_LIST_SUCCESS2);
    }


    private CancleFocusImpl cancleFocus=new CancleFocusImpl() {
        public void cancleFocus(int id) {
            //取消关注
            cancleColl(id);
        }
    };


    /**
     * 获取关注的设计师列表
     */
    private void getFocusDesigner(int index){
        if(isVisibleToUser && view!=null){
            HttpMethod.getFocusDesigner(String.valueOf(page),index,handler);
        }
    }


    /**
     * 取消关注
     */
    public void cancleColl(int id){
        this.id=id;
        DialogUtil.showProgress(mActivity,"取消关注中...");
        HttpMethod.cancleColl(String.valueOf(id),handler);
    }


    public interface CancleFocusImpl{
         void cancleFocus(int id);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取关注的设计师列表
        if(listAll.size()==0){
            getFocusDesigner(HandlerConstant.GET_DESIGNER_LIST_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        removeHandler(handler);
    }
}
