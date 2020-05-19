package com.example.linterna.clients;


import android.annotation.SuppressLint;

import com.example.linterna.Run;
import com.example.linterna.clients.interfaces.Register;
import com.example.linterna.entities.Env;
import com.example.linterna.entities.User;
import com.example.linterna.entities.UserResponse;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterClient extends RetrofitClient<Register> {

    private Consumer<UserResponse> onSuccess;
    private Run onFailure;

    public RegisterClient(Consumer<UserResponse> onSuccess, Run onFailure) {
        super(Register.class);
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected String getBaseUrl() {
        return "http://so-unlam.net.ar/";
    }

    public void register(String name, String lastname, String email, Integer dni, String password) {
        Call<UserResponse> call = getClient().register(new User()
                .setEnv(Env.TEST)
                .setName(name)
                .setLastname(lastname)
                .setEmail(email)
                .setDni(dni)
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
