package com.mcw.demo.api;


import com.mcw.demo.api.exception.ApiException;
import com.mcw.demo.api.response.ApiResponse;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<ApiResponse<T>, T> {

    @Override
    public T call(ApiResponse<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new ApiException(httpResult.getCode(),httpResult.getMessage());
        }
        return httpResult.getData();
    }

}