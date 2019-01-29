package com.bw.movie.netutil;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

public interface BaseApis<T> {
    @GET
    Observable<ResponseBody> get(@Url String url);

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, String> map);

    @PUT
    Observable<ResponseBody> put(@Url String url, @QueryMap Map<String, String> map);

    @DELETE
    Observable<ResponseBody> delete(@Url String url, @QueryMap Map<String, String> map);

    @Multipart
    @POST
    Observable<ResponseBody> postFormBody(@Url String url, @PartMap Map<String, RequestBody> requestBodyMap);
}
