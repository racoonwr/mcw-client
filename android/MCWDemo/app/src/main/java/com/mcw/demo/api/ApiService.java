package com.mcw.demo.api;

import com.mcw.demo.api.response.ApiResponse;
import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingListItemEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/1
 */
public interface ApiService {
    /**
     * 获取会议列表
     *
     * @return
     */
    @GET("meeting/list/{userId}")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<MeetingListItemEntity>>> getMeetingList(@Path("userId") String userId);

    /**
     * 创建会议
     *
     * @param params
     * @return
     */
    @POST("meeting/create")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Boolean>> createMeeting(@Body Map params);

    /**
     * 获取会议基础信息
     *
     * @return
     */
    @GET("meeting/detail/{meetingId}")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<MeetingBaseInfoEntity>>> getMeetingBaseInfo(@Path("meetingId") String meetingId);


    /**
     * 获取会议纪要信息
     *
     * @return
     */
    @POST("gw/oauthentry/game.timeball.push/1/insert")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Integer>> getSummaryInfo(@Body Map params, @Query("access_token") String token);
}
