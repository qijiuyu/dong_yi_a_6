package com.ylean.dyspd.activity.init;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.zxdc.utils.library.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/12/7.
 */
public class TripServiceActivity extends BaseActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_service);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.lin_back, R.id.tv_go, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_go:
            case R.id.tv_confirm:
                setClass(TabActivity.class);
                finish();
                break;
            default:
                break;
        }
    }
}
