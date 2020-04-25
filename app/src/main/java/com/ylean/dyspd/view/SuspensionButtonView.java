package com.ylean.dyspd.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.TabActivity;
import com.ylean.dyspd.activity.web.CustomerWebView;
import com.ylean.dyspd.activity.web.WebViewActivity;
import com.ylean.dyspd.utils.AnimUtils;
import com.zxdc.utils.library.bean.Telphone;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.util.Util;
import java.util.ArrayList;
import java.util.List;
/**
 * 悬浮框view
 * Created by Administrator on 2019/12/12.
 */
public class SuspensionButtonView extends RelativeLayout implements View.OnClickListener,View.OnTouchListener{
    private Context context;
    ImageView imgService,imgCustomer,imgMain,imgTel;
    /**
     * true:打开悬浮框
     * flae：关闭悬浮框
     */
    private boolean isOpen=false;
    //按钮菜单集合
    private List<ImageView> serviceList=new ArrayList<>();
    public SuspensionButtonView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SuspensionButtonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public SuspensionButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.suspension_button,null);
        imgService=view.findViewById(R.id.img_service);
        imgCustomer=view.findViewById(R.id.img_customer);
        imgMain=view.findViewById(R.id.img_main);
        imgTel=view.findViewById(R.id.img_tel);
        imgService.setOnClickListener(this);
        imgCustomer.setOnClickListener(this);
        imgMain.setOnClickListener(this);
        imgTel.setOnClickListener(this);
        imgService.setOnTouchListener(this);

        serviceList.add(imgCustomer);
        serviceList.add(imgMain);
        serviceList.add(imgTel);
        addView(view);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //打开/关闭悬浮框
            case R.id.img_service:
                if(!isOpen){
                    openMean();
                    imgService.setImageResource(R.mipmap.tab_service_click);
                }else{
                    closeMean();
                    imgService.setImageResource(R.mipmap.tab_service);
                }
                break;
            //客服
            case R.id.img_customer:
                intent.setClass(context, CustomerWebView.class);
                context.startActivity(intent);
                //埋点
                if(imgMain.getTag().toString().equals("0")){
                    MobclickAgent.onEvent(context, "main_customer");
                }
                break;
            //回到首页
            case R.id.img_main:
                 intent.setClass(context, TabActivity.class);
                 context. startActivity(intent);
                 break;
            //打电话
            case R.id.img_tel:
                 String mobile= SPUtil.getInstance(context).getString(SPUtil.TEL);
                 if(!TextUtils.isEmpty(mobile)){
                     intent.setAction(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + mobile));
                     context.startActivity(intent);
                     //埋点
                     if(imgMain.getTag().toString().equals("0")){
                         MobclickAgent.onEvent(context, "main_tel");
                     }
                 }else{
                     //获取客服电话
                     getCall();
                 }
                break;
            default:
                break;
        }
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                //获取客服电话
                case HandlerConstant.GET_CALL_SUCCESS:
                    Telphone telphone = (Telphone) msg.obj;
                    if (telphone == null) {
                        break;
                    }
                    if (telphone.isSussess()) {
                        SPUtil.getInstance(context).addString(SPUtil.TEL,telphone.getData());
                        Intent intent=new Intent();
                        intent.setAction(Intent.ACTION_DIAL).setData(Uri.parse("tel:" + telphone.getData()));
                        context.startActivity(intent);
                        //埋点
                        if(imgMain.getTag().toString().equals("0")){
                            MobclickAgent.onEvent(context, "main_tel");
                        }
                    }
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
     * 展开菜单
     */
    public void openMean(){
        AnimUtils.showAnim((Activity)context,serviceList,imgService,animEndCallBack);
    }


    /**
     * 关闭菜单
     */
    public void closeMean(){
        AnimUtils.closeAnim((Activity)context,serviceList,imgService,animEndCallBack);
    }


    /**
     * 界面上下滑动时隐藏或者显示
     * @param y1
     * @param y2
     */
    public void isVisible(final float y1, final float y2){
        handler.postDelayed(new Runnable() {
            public void run() {
                if(y1==y2){
                    imgService.setVisibility(View.VISIBLE);
                }else{
                    if(Math.abs(y1 - y2) < Util.dip2px(context, 10)){
                        return;
                    }
                    imgService.setVisibility(View.GONE);
                    imgCustomer.setVisibility(View.GONE);
                    imgTel.setVisibility(View.GONE);
                    imgService.setImageResource(R.mipmap.tab_service);
                    if (isOpen) {
                        isOpen = false;
                        closeMean();
                    }
                }
            }
        },300);
    }


    float x1,y1, x2,y2;
    int statusBarHeight;//状态栏高度
    int imgWidth,imgHeight; //拖动控件的宽高
    int widthPixels,heightPixels;//屏幕的宽高
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getRawX();
            y1=event.getRawY();
            //获取状态栏高度
            statusBarHeight=Util.getStatusBarHeight((Activity)context);
            //获取拖动控件的宽高
            imgWidth=imgService.getWidth();
            imgHeight=imgService.getHeight();
            //获取屏幕的宽高
            widthPixels=Util.getDeviceWH(context,1);
            heightPixels=Util.getDeviceWH(context,2);
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            x2 = event.getRawX();
            y2=event.getRawY();
            //如果是打开情况，那么在移动的时候就要关闭掉
            if (isOpen) {
                closeMean();
            }

            if((Math.abs(x1 - x2) > Util.dip2px(context, 2)) || (Math.abs(y1 - y2) > Util.dip2px(context, 2))){
                if(x2>imgWidth/2 && x2<(widthPixels-imgWidth/2)){
                    imgService.setX(x2 - imgWidth / 2);
                }
                if(y2>(imgHeight/2+statusBarHeight) && y2<(heightPixels-(imgHeight/2))){
                    imgService.setY(y2 - imgHeight / 2 - statusBarHeight);
                }
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getRawX();
            y2=event.getRawY();
            //如果没有移动，就触发onClick点击事件
            if (Math.abs(x1 - x2) < Util.dip2px(context, 6)) {
                return false;
            } else {
                if(x2>imgWidth/2 && x2<(widthPixels-imgWidth/2) && y2>(imgHeight/2+statusBarHeight) && y2<(heightPixels-(imgHeight/2))){
                    AnimUtils.setXY(imgService, (int) (x2 - imgWidth / 2), (int) (y2 - imgHeight / 2 - statusBarHeight));
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 获取客服电话
     */
    private void getCall() {
        HttpMethod.getCall(handler);
    }



    public AnimEndCallBack animEndCallBack=new AnimEndCallBack() {
        @Override
        public void openAnimEnd() {
            isOpen=true;
        }

        @Override
        public void closeAnimEnd() {
            isOpen=false;
            imgService.setImageResource(R.mipmap.tab_service);
        }
    };

    public interface AnimEndCallBack{
        void openAnimEnd();
        void closeAnimEnd();
    }

    /**
     * 当前页面就在首页
     */
    public void isMain(){
        imgMain.setTag("0");
    }

}
