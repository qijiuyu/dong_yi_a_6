package com.ylean.dyspd.activity.init;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.persenter.init.RoomEntryPersenter;
import com.ylean.dyspd.view.AreaPopWindow;
import com.ylean.dyspd.view.ModelPopWindow;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Screening;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.util.StatusBarUtils;
import com.zxdc.utils.library.util.ToastUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 房型录入
 * Created by Administrator on 2019/12/1.
 */
public class RoomEntryActivity extends BaseActivity {

    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.rel_room)
    RelativeLayout relRoom;
    @BindView(R.id.rel_area)
    RelativeLayout relArea;
    private ModelPopWindow modelPopWindow;
    private AreaPopWindow areaPopWindow;
    private RoomEntryPersenter roomEntryPersenter;
    //户型数据
    private List<Screening.ScreeningBean> modelList=new ArrayList<>();
    //面积数据
    private List<Screening.ScreeningBean> areaLisst=new ArrayList<>();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_entry);
        ButterKnife.bind(this);
        StatusBarUtils.setStatusBarColor(this, R.color.color_A6814B);
        initView();
    }


    /**
     * 初始化
     */
    private void initView(){
        //注册eventBus
        EventBus.getDefault().register(this);
        roomEntryPersenter=new RoomEntryPersenter(this);
        //获取户型数据
        roomEntryPersenter.getScreening("3", HandlerConstant.GET_SCREENING_MODEL);
        //获取面积数据
        roomEntryPersenter.getScreening("1", HandlerConstant.GET_SCREENING_AREA);
    }

    @OnClick({R.id.lin_back, R.id.tv_go, R.id.rel_room, R.id.rel_area, R.id.tv_cofirm})
    public void onViewClicked(View view) {
        if (!isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.lin_back:
                finish();
                break;
            //跳过
            case R.id.tv_go:
                if (modelPopWindow != null){
                    modelPopWindow.dismiss();
                }
                if(areaPopWindow!=null){
                    areaPopWindow.dismiss();
                }
                setClass(HobbyActivity.class);
                break;
            //选择户型
            case R.id.rel_room:
                if (modelPopWindow != null && modelPopWindow.isShowing()) {
                    modelPopWindow.closeShow();
                } else {
                    modelPopWindow = new ModelPopWindow(this);
                    modelPopWindow.showAsDropDown(relRoom);
                    modelPopWindow.setData(modelList,tvRoom);
                    modelPopWindow.openShow();
                }
                break;
            //选择房屋面积
            case R.id.rel_area:
                if (areaPopWindow != null && areaPopWindow.isShowing()) {
                    areaPopWindow.closeShow();
                } else {
                    areaPopWindow = new AreaPopWindow(this);
                    areaPopWindow.showAsDropDown(relArea);
                    areaPopWindow.setData(areaLisst,tvArea);
                    areaPopWindow.openShow();
                }
                break;
            case R.id.tv_cofirm:
                String room = tvRoom.getText().toString().trim();
                String area = tvArea.getText().toString().trim();
                if (TextUtils.isEmpty(room)) {
                    ToastUtil.showLong("请选择您的户型");
                    return;
                }
                if (TextUtils.isEmpty(area)) {
                    ToastUtil.showLong("请选择您的房屋面积");
                    return;
                }
                roomEntryPersenter.setRoomEntry(area,room);
                break;
            default:
                break;
        }
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            case EventStatus.ROOM_ENTRY_MODEL:
                 modelList= (List<Screening.ScreeningBean>) eventBusType.getObject();
                 break;
            case EventStatus.ROOM_ENTRY_AREA:
                 areaLisst= (List<Screening.ScreeningBean>) eventBusType.getObject();
                break;
            default:
                break;
        }
    }

    /**
     *  两次点击按钮之间的点击间隔不能少于700毫秒
     * @return
     */
    private final int MIN_CLICK_DELAY_TIME = 600;
    private long lastClickTime;
    public boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
