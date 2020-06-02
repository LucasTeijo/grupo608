package com.example.linterna.clients.interfaces;

import com.example.linterna.entities.Event;
import com.example.linterna.entities.EventResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendEvent {

    @POST("api/api/event")
    Call<EventResponse> sendEvent(@Header("token") String token, @Body Event event);

}
