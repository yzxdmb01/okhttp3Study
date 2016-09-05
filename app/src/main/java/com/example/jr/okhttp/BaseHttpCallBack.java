package com.example.jr.okhttp;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;

/**
 * Created by Administrator on 2016-09-05.
 */

public abstract class BaseHttpCallBack<T> {
    public Type mType;

    static Type getSuperclassTypeParamater(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseHttpCallBack() {
        mType = getSuperclassTypeParamater(getClass());
    }

    abstract void onFailure(Call call, Exception e);

    abstract void onResponse(T t);
}
