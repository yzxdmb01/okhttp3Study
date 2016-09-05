package com.example.jr.okhttp;


import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
                Gson gson = new Gson();
                Object obj = gson.fromJson(response.body().string(), callBack.mType);
                callBack.onResponse(obj);
            }
        });

    }

    private static OkHttpClient getHttpClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

}
