package com.ylean.dyspd.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.utils.SelectPhoto;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.UploadFile;
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.DialogUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.CircleImageView;
import com.zxdc.utils.library.view.ClickTextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人基本信息
 * Created by Administrator on 2019/11/8.
 */

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.lin_back)
    LinearLayout linBack;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.rel_nickname)
    RelativeLayout relNickname;
    @BindView(R.id.tv_edit_mobile)
    TextView tvEditMobile;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    //用户数据对象
    private UserInfo userInfo;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        userInfo= (UserInfo) getIntent().getSerializableExtra("userInfo");
        if(userInfo==null){
            return;
        }
        final UserInfo.UserBean userBean = userInfo.getData();
        if (userBean == null) {
            return;
        }
        //用户头像
        Glide.with(this).load(userBean.getImgurl()).centerCrop().error(R.mipmap.default_head).into(imgHead);
        //昵称
        tvNickname.setText(userBean.getNickname());
        //手机号码
        final String mobile = userBean.getMobile();
        if(!TextUtils.isEmpty(mobile)){
            tvEditMobile.setText("更换手机号");
            tvMobile.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
        }else{
            tvEditMobile.setText("绑定手机号");
        }
    }


    /**
     * 按钮点击事件
     *
     * @param view
     */
    @OnClick({R.id.lin_back, R.id.img_head, R.id.rel_nickname, R.id.tv_edit_mobile})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //选择头像
            case R.id.img_head:
                showPhotoDialog();
                break;
            //编辑昵称
            case R.id.rel_nickname:
                 Intent intent=new Intent(this,EditNickNameActivity.class);
                 intent.putExtra("nickname",tvNickname.getText().toString().trim());
                 startActivityForResult(intent,0);
                break;
            //变更手机号
            case R.id.tv_edit_mobile:
                if(TextUtils.isEmpty(tvMobile.getText().toString().trim())){
                    setClass(BindingMobileActivity.class,1);
                }else{
                    setClass(EditMobileActivity.class,1);
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
                //获取上传的头像路径
                case HandlerConstant.UPLOAD_FILE_SUCCESS:
                      UploadFile uploadFile= (UploadFile) msg.obj;
                      if(uploadFile==null){
                          break;
                      }
                      if(uploadFile.isSussess()){
                         if(uploadFile.getData().length>0){
                             //修改用户头像
                             editUser(uploadFile.getData()[0]);
                         }
                      }else{
                          ToastUtil.showLong(uploadFile.getDesc());
                      }
                      break;
                //更新用户头像
                case HandlerConstant.EDIT_USER_SUCCESS:
                      BaseBean baseBean= (BaseBean) msg.obj;
                      if(baseBean==null){
                          break;
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
     * 展示选择图片的弹框
     */
    private void showPhotoDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo, null);
        final PopupWindow popupWindow = DialogUtil.showPopWindow(view);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
        //拍照
        view.findViewById(R.id.tv_picture).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                SelectPhoto.selectPhoto(UserInfoActivity.this,1);
            }
        });
        //从相册选择
        view.findViewById(R.id.tv_photo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
                SelectPhoto.selectPhoto(UserInfoActivity.this,2);
            }
        });
        view.findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //返回拍照图片
            case SelectPhoto.CODE_CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    File tempFile = new File(SelectPhoto.pai);
                    if (tempFile.isFile()) {
                        SelectPhoto.cropRawPhoto(Uri.fromFile(tempFile),this);
                    }
                }
                break;
            //返回相册选择图片
            case SelectPhoto.CODE_GALLERY_REQUEST:
                if (data != null) {
                    SelectPhoto.cropRawPhoto(data.getData(),this);
                }
                break;
            //返回裁剪的图片
            case SelectPhoto.CODE_RESULT_REQUEST:
                File imgFile= new File(SelectPhoto.crop);
                Glide.with(UserInfoActivity.this).load(Uri.fromFile(imgFile)).into(imgHead);
                uploadFile(imgFile);
                break;
            //返回昵称
            case 0:
                 if(data!=null){
                     tvNickname.setText(data.getStringExtra("nickName"));
                 }
                 break;
            //返回新的手机号码
            case 1:
                 if(data!=null){
                     final String mobile=data.getStringExtra("mobile");
                     tvMobile.setText(mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4, mobile.length()));
                 }
                 break;
            default:
                break;

        }
    }

    /**
     * 上传图片
     * @param file
     */
    private void uploadFile(File file){
        DialogUtil.showProgress(this,"头像上传中...");
        List<File> list=new ArrayList<>();
        list.add(file);
        HttpMethod.uploadFile("5",list,handler);
    }


    /**
     * 修改用户头像
     * @param imgUrl
     */
    private void editUser(String imgUrl){
        DialogUtil.showProgress(this,"数据更新中...");
        HttpMethod.editUser(imgUrl,null,handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
