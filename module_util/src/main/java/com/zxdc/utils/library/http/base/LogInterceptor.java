package com.zxdc.utils.library.http.base;

import android.text.TextUtils;

import com.zxdc.utils.library.base.BaseApplication;
import com.zxdc.utils.library.bean.BaseBean;
import com.zxdc.utils.library.bean.UserInfo;
import com.zxdc.utils.library.http.HttpApi;
import com.zxdc.utils.library.util.LogUtils;
import com.zxdc.utils.library.util.SPUtil;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
/**
 * HTTP拦截器
 * Created by lyn on 2017/4/13.
 */
public class LogInterceptor implements Interceptor {

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        if (request.method().equals("POST")) {
            request = addPostParameter(request);
        }else{
            request = addGetParameter(request);
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        String body = response.body().string();
        //如果ACCESS_TOKEN失效，自动重新获取一次
        final int code = getCode(body);
        if(code==-401){
            UserInfo userInfo=refresh();
            if(userInfo!=null && userInfo.isSussess()){
                //存储token数据
                SPUtil.getInstance(BaseApplication.getContext()).addString(SPUtil.TOKEN,userInfo.getToken());
                if (request.method().equals("POST")) {
                    request = addPostParameter(request);
                }else{
                    request = addGetParameter(request);
                }
                response = chain.proceed(request);
                body = response.body().string();
            }
        }
        LogUtils.e(String.format("response %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, body));
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), body)).build();
    }


    /**
     * 传递GET请求的全局参数
     * @param request
     * @return
     */
    public Request addGetParameter(Request request){
        HttpUrl.Builder builder = request.url().newBuilder();
        builder.setEncodedQueryParameter("token", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.TOKEN));
        builder.setEncodedQueryParameter("siteid", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.SITEID));
        builder.setEncodedQueryParameter("lat", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.LAT));
        builder.setEncodedQueryParameter("lng", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.LONG));
        Request newRequest = request.newBuilder()
                .method(request.method(), request.body())
                .url(builder.build())
                .build();
        return newRequest;
    }


    /***
     * 添加POST的公共参数
     */
    public Request addPostParameter(Request request) throws IOException {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        FormBody formBody;
        Map<String, String> requstMap = new HashMap<>();
        requstMap.put("token", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.TOKEN));
        requstMap.put("siteid", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.SITEID));
        requstMap.put("lat", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.LAT));
        requstMap.put("lng", SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.LONG));
        LogUtils.e("参数：token="+SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.TOKEN));
        if (request.body().contentLength() > 0 && request.body() instanceof FormBody) {
            formBody = (FormBody) request.body();
            //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
            for (int i = 0; i < formBody.size(); i++) {
                  requstMap.put(formBody.name(i), formBody.value(i));
                  LogUtils.e(request.url() + "参数:" + formBody.name(i) + "=" + formBody.value(i));
            }
        }
        //添加公共参数
        for (String key : requstMap.keySet()) {
            bodyBuilder.add(key, requstMap.get(key));
        }
        formBody = bodyBuilder.build();
        request = request.newBuilder().post(formBody).build();
        return request;
    }


    /**
     * 刷新token
     * @throws IOException
     */
    private UserInfo refresh() throws IOException {
        UserInfo userInfo;
        String openId=SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.OPEN_ID);
        if(TextUtils.isEmpty(openId)){
            final String mobile=SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.ACCOUNT);
            final String password=SPUtil.getInstance(BaseApplication.getContext()).getString(SPUtil.PASSWORD);
            userInfo=Http.getRetrofitNoInterceptor().create(HttpApi.class).login(mobile,password).execute().body();
        }else{
            userInfo=Http.getRetrofitNoInterceptor().create(HttpApi.class).wxLogin(null,null,null,openId).execute().body();
        }
        return userInfo;
    }

    public int getCode(String json) {
        int code = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            code = jsonObject.getInt("code");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

}
