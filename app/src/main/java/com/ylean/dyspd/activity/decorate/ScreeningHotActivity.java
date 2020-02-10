package com.ylean.dyspd.activity.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.main.SelectCityActivity;
import com.ylean.dyspd.adapter.decorate.ScreeningHotAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Area;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 热装楼盘的筛选界面
 * Created by Administrator on 2019/11/15.
 */

public class ScreeningHotActivity extends BaseActivity {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.lin_city)
    LinearLayout linCity;
    @BindView(R.id.listView)
    ListView listView;
    private ScreeningHotAdapter screeningHotAdapter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_hot);
        ButterKnife.bind(this);
        //根据城市名称获取地区
        getArea(null);
    }


    @OnClick({R.id.lin_back,R.id.tv_cancle, R.id.lin_city,R.id.tv_confirm})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.tv_cancle:
            case R.id.lin_back:
                finish();
                break;
            case R.id.lin_city:
                intent.setClass(this,SelectCityActivity.class);
                intent.putExtra("gotoType",1);
                startActivityForResult(intent,100);
                break;
            //确定
            case R.id.tv_confirm:
                  if(screeningHotAdapter==null){
                      return;
                  }
                  String area=screeningHotAdapter.getSelectArea();
                  if(TextUtils.isEmpty(area)){
                      return;
                  }
                  if(area.equals("全部区域")){
                      area="";
                  }
                  intent.putExtra("name",area);
                  setResult(100,intent);
                  finish();
                  break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //根据城市名称获取地区
                case HandlerConstant.GET_ARES_SUCCESS:
                      final Area area= (Area) msg.obj;
                      if(area==null){
                          break;
                      }
                      if(area.isSussess()){
                          screeningHotAdapter=new ScreeningHotAdapter(ScreeningHotActivity.this,area.getData());
                          listView.setAdapter(screeningHotAdapter);
                      }else{
                          ToastUtil.showLong(area.getDesc());
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==100 && data!=null){
            //根据城市名称获取地区
            getArea(data.getStringExtra("city"));
        }
    }


    /**
     * 根据城市名称获取地区
     */
    private void getArea(String city){
        if(TextUtils.isEmpty(city)){
            city= SPUtil.getInstance(activity).getString(SPUtil.CITY);
            if(TextUtils.isEmpty(city)){
                city= SPUtil.getInstance(activity).getString(SPUtil.LOCATION_CITY);
            }
        }
        tvCity.setText(city);
        DialogUtil.showProgress(this,"数据加载中...");
        HttpMethod.getArea(city,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
