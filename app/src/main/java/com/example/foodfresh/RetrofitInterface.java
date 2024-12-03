package com.example.foodfresh;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @POST("/api/v1/user/register")
    Call<String> register_api_post(@Body RegisterDM signin);

    @POST("/api/v1/user/login")
    Call<LoginActivity.UserInfoTest> login_api_post(@Body LoginDM login);

    @GET("/api/v1/refrigerator/accessible")
    Call<List<RefrigDM>> refrig_api_get(@Query("userId") String userId);
    @POST("/api/v1/refrigerator/create")
    Call<MessageDM> addRefrig_api_post(@Body RefrigDM refrig);

    @POST("/api/v1/refrigerator/{refrigeratorId}/share/add")
    Call<MessageDM> addMember_api_post(
            @Path("refrigeratorId") String refrigId,
            @Query("userId") String userId,
            @Body MemberDM addMember);
    @POST("/api/v1/refrigerator/{refrigeratorId}/share/remove")
    Call<MessageDM> delMember_api_post(
            @Path("refrigeratorId") String refrigId,
            @Query("userId") String userId,
            @Body MemberDM delMember);


    // 식품 추가 api
    @POST("/api/v1/refrigerator/{refrigeratorId}/food/add")
    Call<MessageDM> addFood_api_post(
            @Path("refrigeratorId") String refrigId,
            @Body FoodDM food);
}
