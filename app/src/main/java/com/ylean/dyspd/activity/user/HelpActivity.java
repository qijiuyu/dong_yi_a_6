package com.ylean.dyspd.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.user.HelpAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Help;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 使用帮助
 * Created by Administrator on 2019/12/13.
 */

public class HelpActivity extends BaseActivity {

    @BindView(R.id.listView)
    ListView listView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);
        //获取帮助列表
        getHelp();
    }

    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        finish();
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_HELP_SUCCESS:
                      Help help= (Help) msg.obj;
                      if(help==null){
                          break;
                      }
                      if(help.isSussess()){
                          HelpAdapter helpAdapter=new HelpAdapter(HelpActivity.this,help.getData());
                          listView.setAdapter(helpAdapter);
                      }else{
                          ToastUtil.showLong(help.getDesc());
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
     * 获取帮助列表
     */
    private void getHelp(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getHelp(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
