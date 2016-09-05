package com.example.jr.okhttp;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 请求参数
 * Created by Administrator on 2016-09-05.
 */

public class RequestParams {
    LinkedHashMap<String, String> params = new LinkedHashMap<>();

    public RequestParams() {
    }

    public void addParams(String key, String value) {
        params.put(key, value);
    }

    public RequestBody getRequestBody() {
        RequestBody body;
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        body = builder.build();
        return body;
    }
}
