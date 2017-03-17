package com.mcw.demo.api;

import com.mcw.demo.api.response.ApiResponse;
import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.model.SummaryInfoEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
     * 获取与会人员
     *
     * @return
     */
    @GET("user/all")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<SelectedUserEntity>>> getAllUser();

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
     * 开始会议
     *
     * @param params
     * @return
     */
    @POST("meeting/start")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Boolean>> startMeeting(@Body Map params);

    /**
     * 结束会议
     *
     * @param params
     * @return
     */
    @POST("meeting/end")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Boolean>> endMeeting(@Body Map params);



    /**
     * 获取会议列表
     *
     * @return
     */
    @GET("vote/list/{meetingId}")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<MyVoteItemEntity>>> getVoteList(@Path("meetingId") String meetingId);

    /**
     * 创建会议纪要
     *
     * @param params
     * @return
     */
    @POST("summary/create")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<Boolean>> createSummary(@Body Map params);

    /**
     * 获取会议基础信息
     *
     * @return
     */
    @GET("summary/detail/{summaryInfoId}")
    @Headers("Content-Type:" + RetrofitClient.JSON)
    Observable<ApiResponse<List<SummaryInfoEntity>>> getSummaryInfo(@Path("summaryInfoId") String summaryInfoId);

}
