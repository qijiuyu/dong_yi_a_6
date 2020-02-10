package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑昵称
 * Created by Administrator on 2019/11/8.
 */

public class EditNickNameActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.et_neckname)
    EditText etNeckname;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nickname);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        String nickname=getIntent().getStringExtra("nickname");
        etNeckname.setText(nickname);
    }

    @OnClick({R.id.lin_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            case R.id.tv_confirm:
                String nickName=etNeckname.getText().toString().trim();
                if(TextUtils.isEmpty(nickName)){
                    ToastUtil.showLong("请输入您的昵称");
                }else{
                    editUser(nickName);
                }
                break;
            default:
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                //更新用户昵称
                case HandlerConstant.EDIT_USER_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(baseBean==null){
                        break;
                    }
                    if(baseBean.isSussess()){
                        Intent intent=new Intent();
                        intent.putExtra("nickName",etNeckname.getText().toString().trim());
                        setResult(0,intent);
                        finish();
                    }
                    ToastUtil.showLong(baseBean.getDesc());
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 修改用户头像
     * @param nickName
     */
    private void editUser(String nickName){
        DialogUtil.showProgress(this,"昵称更新中...");
        HttpMethod.editUser(null,nickName,handler);
    }
}
