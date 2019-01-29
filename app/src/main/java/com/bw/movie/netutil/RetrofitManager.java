package com.bw.movie.netutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bw.movie.application.BaseApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.QueryMap;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager {
    private final String BASE_URL = "http://172.17.8.100/movieApi/";
    private static RetrofitManager instance;

    public static synchronized RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }
    private BaseApis baseApis;

    private RetrofitManager() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //拿到请求
                Request original = chain.request();
                //取出保存的userId,sessionId
                SharedPreferences swl = BaseApplication.getApplication().getSharedPreferences("swl", Context.MODE_PRIVATE);
                String sessionId = swl.getString("sessionId", "");
                String userId = swl.getString("userId", "");

                //重新构造请求
                Request.Builder newBuilder = original.newBuilder();
                //把原来请求的原样参数放进去
                newBuilder.method(original.method(),original.body());
                //添加特殊的userId,sessionId
                if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)){
                    newBuilder.addHeader("userId",userId);
                    newBuilder.addHeader("sessionId",sessionId);
                }
                //打包
                Request request = newBuilder.build();

                return chain.proceed(request);
            }
        });
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        baseApis = retrofit.create(BaseApis.class);
    }

    /**
     * 可以这样生成Map<String, RequestBody> requestBodyMap
     * Map<String, String> requestDataMap这里面放置上传数据的键值对。
     */
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }



    /**
     * get请求
     */
    public void get(String url,HttpListener listener) {

        baseApis.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));


    }




    /**
     * 表单post请求
     */
    public void postFormBody(String url, Map<String, RequestBody> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseApis.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }

    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }

    /**
     * put请求
     */
    public void put(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }

    /**
     * delete请求
     */
    public void delete(String url, Map<String, String> map,HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApis.put(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));

    }


    public Observer getObserver(final HttpListener listener){

        Observer observer = new Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String data = responseBody.string();
                    if (listener != null) {
                        listener.onSuccess(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }

            @Override
            public void onCompleted() {

            }
        };
        return observer;
    }


    public HttpListener  listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }

    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }
}
