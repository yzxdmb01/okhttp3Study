package com.example.jr.okhttp;

import android.util.Log;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 日志拦截器，
 * Created by Administrator on 2016-09-05.
 */

public class LoggingInterceptor implements Interceptor {
    private final String TAG = "LoggingInterceptor";

    public LoggingInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //获得请求body
        RequestBody requestBody = request.body();
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        MediaType contentType = requestBody.contentType();
        Charset charset = contentType.charset(Charset.forName("UTF-8"));
        Log.i(TAG, request.toString() + "\nbody:" + URLDecoder.decode(buffer.readString(charset), "UTF-8"));
        Response response = chain.proceed(request);
        return response;
    }
}
