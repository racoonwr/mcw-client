package com.mcw.demo.api;


import java.util.HashMap;
import java.util.Map;

/**
 * Api协议工厂，具体方法代码通过{@link }来生成
 */
public class ApiFactory {
    private static Map<String, Object> mCache = new HashMap();

    public static <T> T getService(Class cls) {
        String key = cls.getSimpleName();
        Object target = mCache.get(key);
        if (target == null) {
            target = RetrofitClient.getInstance().create(cls);
            mCache.put(key, target);
        }
        return (T) target;
    }
}
