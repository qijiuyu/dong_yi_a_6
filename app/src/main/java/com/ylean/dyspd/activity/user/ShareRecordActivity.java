package com.ylean.dyspd.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.user.ShareRecordAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.ShareList;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分享记录
 * Created by Administrator on 2019/11/18.
 */

public class ShareRecordActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.listView)
    ListView listView;
    private ShareRecordAdapter shareRecordAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_record);
        ButterKnife.bind(this);
        //获取分享列表
        getShareList();
        //返回
        linBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShareRecordActivity.this.finish();
            }
        });
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.GET_SHARE_LIST_SUCCESS:
                      final ShareList shareList= (ShareList) msg.obj;
                      if(shareList==null){
                          break;
                      }
                      if(shareList.isSussess()){
                          listView.setDivider(null);
                          shareRecordAdapter=new ShareRecordAdapter(ShareRecordActivity.this,shareList.getData());
                          listView.setAdapter(shareRecordAdapter);
                      }else{
                          ToastUtil.showLong(shareList.getDesc());
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
     * 获取分享列表
     */
    private void getShareList(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getShareList(handler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
