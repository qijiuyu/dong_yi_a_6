package com.ylean.dyspd.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.ylean.dyspd.activity.init.StartActivity;
import com.zxdc.utils.library.util.LogUtils;

import java.util.List;

public class PermissionUtil {

private static AlertDialog.Builder normalDialog;
    /**
     * getPermission 动态获取权限方法
     *
     * @param context 上下文
     * @param isAsk   是否开启权限询问      (Android6.0以下用户可以不开启,所有权限自动可以获得；6.0以上用户若不开启，获取不到某权限时，若你没做相应处理，可能会崩溃)
     * @param isHandOpen   是否询问用户被引导手动开启权限界面   (用户永久禁用某权限时是否引导让用户手动授权权限)
     */
    public static void getPermission(final Context context, boolean isAsk, final boolean isHandOpen,final StartActivity.PermissionCallBack permissionCallBack){
        if(!isAsk)return;

        if (XXPermissions.isHasPermission(context,
                //所需危险权限可以在此处添加：
                Permission.READ_PHONE_STATE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.READ_EXTERNAL_STORAGE,
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.CAMERA)
                ) {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    permissionCallBack.onclick();
                }
            },2000);
        }else {
            XXPermissions.with((Activity)context).permission(
                    //同时在此处添加：
                    Permission.READ_PHONE_STATE,
                    Permission.WRITE_EXTERNAL_STORAGE,
                    Permission.READ_EXTERNAL_STORAGE,
                    Permission.ACCESS_FINE_LOCATION,
                    Permission.ACCESS_COARSE_LOCATION,
                    Permission.CAMERA
            ).request(new OnPermission() {
                @Override
                public void noPermission(List<String> denied, boolean quick) {
                    LogUtils.e(quick+"+++++++++++++++++++++++++++++");
                    if (quick) {
                        //如果是被永久拒绝就跳转到应用权限系统设置页面
                        if(isHandOpen) {
                            if(normalDialog!=null){
                                return;
                            }
                            normalDialog =
                                    new AlertDialog.Builder(context);
                            normalDialog.setTitle("开启权限引导");
                            normalDialog.setMessage("被您永久禁用的权限为应用必要权限，是否需要引导您去手动开启权限呢？");
                            normalDialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    normalDialog=null;
                                    XXPermissions.gotoPermissionSettings(context);
                                }
                            });
                            normalDialog.show();
                        }
                    }else {
                        Log.e("tag","获取权限失败");
                        ((Activity) context).finish();
                    }
                }

                @Override
                public void hasPermission(List<String> granted, boolean isAll) {
                    if (isAll) {
                        Log.e("tag","获取权限成功");
                        permissionCallBack.onclick();
                    }else {
                    }
                }
            });
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }
}
