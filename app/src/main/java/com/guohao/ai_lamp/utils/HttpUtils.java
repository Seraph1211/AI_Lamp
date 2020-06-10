package com.coolweather.ai_lamp.utils;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "HttpUtils";
    public static final String basicUrl = "http://121.36.26.175:8080/desklamp/";
    public static final String registerUrl = basicUrl + "register";  //注册
    public static final String loginUrl = basicUrl + "signIn";  //登录
    public static final String addDynamicUrl = basicUrl + "addDynamic";  //发布动态
    public static final String deleteDynamicUrl = basicUrl + "deleteDynamic";  //删除动态
    public static final String getDynamicUrl = basicUrl + "Dynamic";  //查看单个动态
    public static final String getDynamicListUrl = basicUrl + "DynamicList";  //查看社区动态列表
    public static final String likeUrl = basicUrl + "likeDynamic";  //为动态点赞
    public static final String unlikeUrl = basicUrl + "deleteLikeDynamic";  //取消点赞
    public static final String addPlanUrl = basicUrl + "addStudyPlan";  //添加学习计划
    public static final String deletePlanUrl = basicUrl + "deleteStudyPlan";  //删除学习计划
    public static final String updatePlanUrl = basicUrl + "updateStudyPlan";  //修改学习计划
    public static final String getDonePlanListUrl = basicUrl + "studyPlanList";  //查看已完成的学习计划列表
    public static final String getUndonePlanListUrl = basicUrl + "unStudyPlanList";  //查看未完成的学习计划列表


    /**
     * 将JavaBean以post的方式发送给服务端
     * @param address
     * @param bean
     * @param callback
     * @param <T>
     */
    public static <T> void post(String address, T bean, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String JSONString = new Gson().toJson(bean);
        Log.d(TAG, "post: JSONString="+JSONString);

        RequestBody formBody = RequestBody.create(JSON, JSONString);

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * 将HashMap中的数据以post的方式发送给服务端
     * @param address
     * @param map
     * @param callback
     */
    public static void post(String address, HashMap map, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String JSONString = new Gson().toJson(map);
        Log.d(TAG, "post: JSONString="+JSONString);

        RequestBody formBody = RequestBody.create(JSON, JSONString);
        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    /**
     * 以get方式向服务器查询数据
     * @param address
     * @param callback
     */
    public static void get(String address, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .get()
                .build();

        client.newCall(request).enqueue(callback);
    }

}
