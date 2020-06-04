package com.example.linterna.clients;


import android.annotation.SuppressLint;

import com.example.linterna.Run;
import com.example.linterna.clients.interfaces.Login;
import com.example.linterna.entities.Env;
import com.example.linterna.entities.User;
import com.example.linterna.entities.UserResponse;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginClient extends RetrofitClient<Login> {

    private Consumer<UserResponse> onSuccess;
    private Run onFailure;

    public LoginClient(Consumer<UserResponse> onSuccess, Run onFailure) {
        super(Login.class);
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected String getBaseUrl() {
        return "http://so-unlam.net.ar/";
    }

    public void login(String email, String password) {
        Call<UserResponse> call = getClient().login(new User()
                .setEnv(Env.DEV)
                .setEmail(email)
                .setCommission(2900)
                .setGroup(608)
                .setPassword(password));

        call.enqueue(new Callback<UserResponse>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful() && !"error".equals(response.body().getState())) {
                    onSuccess.accept(response.body());
                } else {
                    onFailure.run();
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                onFailure.run();
            }
        });
    }

}
