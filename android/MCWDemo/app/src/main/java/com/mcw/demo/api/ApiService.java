//package com.mcw.demo.api;
//
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.http.Body;
//import retrofit2.http.Headers;
//import retrofit2.http.POST;
//import retrofit2.http.Query;
//
///**
// * 类描述
// * <p>
// * version 1.0
// *
// * @author racoon
// * @email wurun@zizizizizi.com
// * @date 2017/3/1
// */
//public interface ApiService {
//    /**
//     * 获取用户信息
//     *
//     * @param params
//     * @return
//     */
//    @POST("gw/oauthentry/xzaccount.student.studentAllInfo.byStudentId/1/get")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<User>> getUserInfo(@Body Map params, @Query("access_token") String token);
//
//    /**
//     * 获取首页数据
//     *
//     * @param params
//     * @return
//     */
//    @POST("gw/oauthentry/game.timeball.homepage/1/get")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<HomeData>> getDataInfo(@Body Map params,@Query("access_token") String token);
//
//    /**
//     * 获取首页推送历史数据
//     *
//     * @param params
//     * @return
//     */
//    @POST("gw/oauthentry/game.timeball.push/1/get")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<List<PushData>>> getPushDataInfo(@Body Map params, @Query("access_token") String token);
//
//    /**
//     * 推送数据上传
//     *
//     * @param params
//     * @return
//     */
//    @POST("gw/oauthentry/game.timeball.push/1/insert")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<Integer>> setPushDataInfo(@Body Map params,@Query("access_token") String token);
//
//
//    /**
//     * http://192.168.60.79:8096/timeballpush/update
//     * 更新推送的消息的标记状态
//     */
//    @POST("gw/oauthentry/game.timeball.push/1/update")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<Integer>> updatePushDataInfo(@Body Map params,@Query("access_token") String token);
//
//    /**
//     * 获取战报数据
//     */
//    @POST("gw/oauthentry/game.timeball.resport/1/get")
//    @Headers("Content-Type:" + RetrofitClient.JSON)
//    Observable<ApiResponse<List<SquareData>>> getBattlefieldInfo(@Body Map params,@Query("access_token") String token);
//}
