package com.mcw.demo.api;


import com.mcw.demo.DemoApplication;
import com.mcw.demo.util.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";
    public static final String CONTENT_TYPE = "Content-Type:";
    public static final String JSON = "application/json;charset=utf-8";
    public static final String FORM = "application/x-www-form-urlencoded";


    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    //    private static final String ROOT_URL = "";
    private static OkHttpClient mOkHttpClient;
    private static volatile RetrofitClient instance = null;
    private Retrofit mRetrofit;
    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            okhttp3.Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
//                //保存JSESSIONID
//                String cookie = originalResponse.headers().get("Set-Cookie");
//                if (cookie != null) {
//                    String[] values = cookie.split(";");
//                    for (int i = 0; i < values.length; i++) {
//                        if (values[i].contains("JSESSIONID")) {
//                            String jsessionId = values[i].replace("JSESSIONID=", "");
//                            if (!TextUtils.isEmpty(jsessionId)) {
//                                Logger.d("JSESSIONID = " + jsessionId);
//                                DemoApplication.getInstance().setJsessionId(jsessionId);
//                                break;
//                            }
//                        }
//                    }
//                }
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };

    private RetrofitClient() {
        initOkHttpClient();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfigManager.getInstance().getRootUrl())
                .client(mOkHttpClient)
                //拓展添加RxJava的功能，导入的库：compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //使用Gson对Json进行解析，导入的库：compile 'com.squareup.retrofit2:converter-gson:2.0.2'
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    public <T> T create(Class<T> mClass) {
        return mRetrofit.create(mClass);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (OkHttpClient.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(DemoApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            // 向Request Header添加一些业务相关数据，如APP版本，token,同时可以配置post请求的表单数据加密等
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            //日志Interceptor，可以打印日志，compile 'com.squareup.okhttp3:logging-interceptor:3.2.0'
                            .addInterceptor(loggingInterceptor)
//                            //更新token
//                            .addInterceptor(new RefreshTokenInterceptor(BaseApplication.getInstance()))
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }

}
