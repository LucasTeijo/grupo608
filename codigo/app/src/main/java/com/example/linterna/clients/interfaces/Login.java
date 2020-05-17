package com.example.linterna.clients.interfaces;

import com.example.linterna.entities.User;
import com.example.linterna.entities.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Login {

    @POST("api/api/login")
    Call<UserResponse> call(@Body User user);

}
