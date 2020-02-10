package com.ylean.dyspd.activity.found;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.adapter.found.PinZhiAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Pinzhi;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MeasureListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
public class PinZhiActivity extends BaseActivity {

    @BindView(R.id.listView)
    MeasureListView listView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinzhi);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this, android.R.color.black);
        //获取品质生活
        getPinzhi();
    }

    @OnClick(R.id.lin_back)
    public void onViewClicked() {
        finish();
    }

    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //获取品质生活
                case HandlerConstant.GET_PIN_ZHI_SUCCESS:
                      Pinzhi pinzhi= (Pinzhi) msg.obj;
                      if(pinzhi==null){
                          break;
                      }
                      if(pinzhi.isSussess()){
                          PinZhiAdapter pinZhiAdapter=new PinZhiAdapter(PinZhiActivity.this,pinzhi.getData());
                          listView.setAdapter(pinZhiAdapter);
                          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                  Intent intent=new Intent(PinZhiActivity.this, WebViewActivity.class);
                                  intent.putExtra("type",11);
                                  startActivity(intent);
                              }
                          });
                      }else{
                          ToastUtil.showLong(pinzhi.getDesc());
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
     * 获取品质生活
     */
    private void getPinzhi(){
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getPinzhi(handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
