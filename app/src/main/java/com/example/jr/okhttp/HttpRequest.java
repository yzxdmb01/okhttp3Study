package com.example.jr.okhttp;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 封装了okhttp网络框架
 * Created by Administrator on 2016-09-05.
 */

public class HttpRequest {
    private static OkHttpClient client = null;

    //post
    public static void post(String url, RequestParams params, BaseHttpCallBack callBack) {
        RequestBody requestBody = params.getRequestBody();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack.mType == String.class) {
                    callBack.onResponse(response.body().string());
                } else {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(response.body().string(), callBack.mType);
                    callBack.onResponse(o);
                }
            }
        });

    }

    //get
    public static void get(String url, RequestParams params, BaseHttpCallBack callBack) {
        url = params.append2Url(url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", "7c46a87256bed33281d4aa4baa6b1af8")
                .build();
        getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callBack.mType == String.class) {
                    callBack.onResponse(response.body().string());
                } else {
                    Gson gson = new Gson();
                    Object o = gson.fromJson(response.body().string(), callBack.mType);
                    callBack.onResponse(o);
                }
            }
        });

    }


    private static OkHttpClient getHttpClient() {
        if (client == null) {
            client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();
        }
        return client;
    }

}
