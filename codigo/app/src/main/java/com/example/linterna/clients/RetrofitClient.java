package com.example.linterna.clients;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

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
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()))
                .build();

        return build.create(clazz);
    }

    protected abstract String getBaseUrl();

}
