package com.example.linterna.clients.interfaces;

import com.example.linterna.entities.User;
import com.example.linterna.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Register {

    @POST("api/api/register")
    Call<UserResponse> call(@Body User user);

}
