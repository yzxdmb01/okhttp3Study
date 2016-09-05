package com.example.jr.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
        long t1 = System.nanoTime();
        Request request = chain.request();
        Log.i(TAG, String.format("Sending request %s on %s%n%s", request.url(), request.method(), request.headers()));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
//        System.out.println(String.format("Received response for %s in %.1fms%n%s",
//                request.url(), (t2 - t1) / 1e6d, response.headers()));
        Log.i(TAG, String.format("Received response for %s in %.1fms%n%s", request.url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
