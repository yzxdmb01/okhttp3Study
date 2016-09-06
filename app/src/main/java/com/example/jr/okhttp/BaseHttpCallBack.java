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

    //获取泛型T的具体类型
    static Type getSuperclassTypeParamater(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            return String.class;
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
