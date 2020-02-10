package com.ylean.dyspd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.ylean.dyspd.adapter.TestAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.LogUtils;

/**
 * Created by Administrator on 2019/12/8.
 */

public class TestActivity extends BaseActivity {

    ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView=new ListView(this);
        setContentView(listView);

        listView.setAdapter(new TestAdapter(this));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                LogUtils.e(firstVisibleItem+"++++++++++++="+visibleItemCount+"++++++++++"+totalItemCount);

            }
        });
    }
}
