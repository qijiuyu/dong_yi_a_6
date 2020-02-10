package com.ylean.dyspd.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.ylean.dyspd.R;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.ClickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改密码
 * Created by Administrator on 2019/11/8.
 */

public class EditPwdActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd2)
    EditText etNewPwd2;
    @BindView(R.id.tv_confirm)
    ClickTextView tvConfirm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pwd);
        ButterKnife.bind(this);
    }


    /**
     * 按钮点击事件
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                 finish();
                break;
            case R.id.tv_confirm:
                String oldPwd=etOldPwd.getText().toString().trim();
                String newPwd=etNewPwd.getText().toString().trim();
                String newPwd2=etNewPwd2.getText().toString().trim();
                if(TextUtils.isEmpty(oldPwd)){
                    ToastUtil.showLong("请输入旧密码！");
                    return;
                }
                if(TextUtils.isEmpty(newPwd)){
                    ToastUtil.showLong("请输入新密码！");
                    return;
                }
                if(TextUtils.isEmpty(newPwd2)){
                    ToastUtil.showLong("请再次输入新密码！");
                    return;
                }
                if(!newPwd.equals(newPwd2)){
                    ToastUtil.showLong("请确保两次输入的新密码是相同的！");
                    return;
                }
                if(newPwd.length()<6 || newPwd.contains("_")){
                    ToastUtil.showLong("新密码由6-12位数字或字母组成，不能有下划线");
                    return;
                }
                //修改密码
                editPassword(newPwd,oldPwd);
                break;
        }
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            DialogUtil.closeProgress();
            switch (msg.what){
                case HandlerConstant.EDIT_PASSWORD_SUCCESS:
                    BaseBean baseBean= (BaseBean) msg.obj;
                    if(baseBean==null){
                        break;
                    }
                    if(baseBean.isSussess()){
                        finish();
                        SPUtil.getInstance(activity).addString(SPUtil.PASSWORD,etNewPwd.getText().toString().trim());
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
     * 修改密码
     * @param newPwd
     * @param oldPwd
     */
    private void editPassword(String newPwd,String oldPwd){
        DialogUtil.showProgress(this,"密码修改中...");
        HttpMethod.editPassword(newPwd,oldPwd,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
