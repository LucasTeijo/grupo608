package com.example.linterna.clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitClient<T> {

    private Class<T> clazz;

    RetrofitClient(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected T getClient() {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return build.create(clazz);
    }

    protected abstract String getBaseUrl();

}
