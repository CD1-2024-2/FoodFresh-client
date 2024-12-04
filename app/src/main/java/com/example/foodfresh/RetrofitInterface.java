package com.example.foodfresh;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/api/v1/user/register")
    Call<Void> register_api_post(@Body RegisterDM signin);

    @POST("/api/v1/user/login")
    Call<LoginActivity.UserInfoTest> login_api_post(@Body LoginDM login);

    @POST("/api/v1/refrigerator/{refrigeratorId}/food/add")
    Call<Void> create_food_api(@Path("refrigeratorId") String refrigeratorId, @Body FoodDM food);
}
