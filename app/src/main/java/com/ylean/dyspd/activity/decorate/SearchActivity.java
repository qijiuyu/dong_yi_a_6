package com.ylean.dyspd.activity.decorate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.ylean.dyspd.persenter.decorate.SearchPersenter;
import com.ylean.dyspd.view.TagsLayout;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 搜索页面
 * Created by Administrator on 2019/11/8.
 */
public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.img_clear)
    ImageView imgClear;
    @BindView(R.id.tv_hot)
    TextView tvHot;
    @BindView(R.id.tag_hot)
    TagsLayout tagHot;
    @BindView(R.id.tag_history)
    TagsLayout tagHistory;
    //要搜索的关键字
    private String strKey;
    private SearchPersenter searchPersenter;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initView();
        //获取热门搜索
        searchPersenter.getHotSearch(tagHot);
    }


    /**
     * 初始化
     */
    private void initView() {
        searchPersenter=new SearchPersenter(this);
        String hotSearch = "热门<font color=\"#000000\">" + "搜索" + "</font>";
        tvHot.setText(Html.fromHtml(hotSearch));
        etKey.setOnEditorActionListener(this);
    }

    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.tv_cancle, R.id.img_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                finish();
                break;
            //情空搜索历史
            case R.id.img_clear:
                tagHistory.removeAllViews();
                SPUtil.getInstance(this).removeMessage(SPUtil.SEARCH_KEY);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            strKey = etKey.getText().toString().trim();
            if (TextUtils.isEmpty(strKey)) {
                ToastUtil.showLong("请输入您要搜索的关键字！");
                return false;
            }
            //关闭键盘
            lockKey(etKey);
            //保存搜索过的关键字
            addTabKey();
            etKey.setText(null);
            searchPersenter.gotoSearchList(strKey);
        }
        return false;
    }


    /**
     * 保存搜索过的关键字
     */
    private void addTabKey() {
        String keys = SPUtil.getInstance(this).getString(SPUtil.SEARCH_KEY);
        Map<String, String> keyMap;
        if (!TextUtils.isEmpty(keys)) {
            keyMap = SPUtil.gson.fromJson(keys, Map.class);
        } else {
            keyMap = new HashMap<>();
        }
        keyMap.put(strKey, strKey);
        SPUtil.getInstance(this).addString(SPUtil.SEARCH_KEY, SPUtil.gson.toJson(keyMap));
    }

    @Override
    public void onResume() {
        super.onResume();
        //显示历史搜索记录
        searchPersenter.showHistory(tagHistory);
    }
}
