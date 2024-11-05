package com.example.randuserface.api;

import com.example.randuserface.models.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomUserApi {
    @GET("api/")
    Call<UserResponse> getUser(@Query("gender") String gender, @Query("nat") String nationality);
}
