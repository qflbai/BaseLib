package com.qflbai.lib.net.retrofit;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author WenXian Bai
 * @Date: 2018/3/13.
 * @Description: 网络请求接口
 */

public interface RetrofitService {
    /**
     * get请求
     *
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> getNet(@Url String urlPath);

    /**
     * get请求
     *
     * @param urlPath  请求路径(基于baseURL)
     * @param paramMap 请求参数
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> getNet(@Url String urlPath, @QueryMap Map<String, Object> paramMap);

    /**
     * get请
     *
     * @param urlPath   请求路径
     * @param headerMap 请求头
     * @param paramMap  请求参数
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> getNet(@Url String urlPath, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> paramMap);

    /**
     * get请求
     *
     * @param urlPath  请求路径(基于baseURL)
     * @param paramMap 请求头
     * @return
     */
    @GET
    Observable<Response<ResponseBody>> getHeaderNet(@Url String urlPath, @HeaderMap Map<String, Object> paramMap);


    /**
     * form表单请求
     *
     * @param urlPath  请求路径
     * @param paramMap 请求参数
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<Response<ResponseBody>> postFormNet(@Url String urlPath, @FieldMap Map<String, Object> paramMap);

    /**
     * form表单请求
     *
     * @param urlPath   请求路径
     * @param headerMap 请求头
     * @param paramMap  请求参数
     * @return
     */
    @POST
    @FormUrlEncoded
    Observable<Response<ResponseBody>> postFormNet(@Url String urlPath, @HeaderMap Map<String, Object> headerMap, @FieldMap Map<String, Object> paramMap);

    /**
     * json格式请求
     *
     * @param urlPath 请求路径
     * @param param   请求参数
     * @return
     */
    @POST
    @Headers("Content-Type: application/json")
    Observable<Response<ResponseBody>> postJsonNet(@Url String urlPath, @Body Object param);

    /**
     * json格式请求
     *
     * @param urlPath   请求路径
     * @param headerMap 请求头
     * @param param     请求参数
     * @return
     */
    @POST
    @Headers("Content-Type: application/json")
    Observable<Response<ResponseBody>> postJsonNet(@Url String urlPath, @HeaderMap Map<String, Object> headerMap, @Body Object param);

    /**
     * 文件下载
     *
     * @param urlPath 下载路径
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String urlPath);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String urlPath, @QueryMap Map<String, Object> map);


    @Streaming
    @GET
    Observable<Response<ResponseBody>> download();

    /**
     * 文件上传
     */
    @Multipart
    @POST
    Observable<Response<ResponseBody>> uplodaOne(@Url String urlPath, @QueryMap Map<String, String> paramMap, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Observable<Response<ResponseBody>> uplodaOnes(@Url String urlPath, @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> paramMap, @Part MultipartBody.Part file);

    @Multipart
    @POST
    Observable<Response<ResponseBody>> uplodas(@Url String urlPath, @QueryMap Map<String, Object> paramMap, @Part List<MultipartBody.Part> parts);

    @Multipart
    @POST
    Observable<Response<ResponseBody>> uploadJsonFile(@Url String urlPath, @HeaderMap Map<String, Object> headerMap, @Part("json") RequestBody jsonBody, @Part MultipartBody.Part file);

}
