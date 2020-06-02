package com.example.linterna.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final Gson JSON;

    static {
        JSON = new Gson();
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        return JSON.fromJson(json, new ListParameterizedType(clazz));
    }

    public static String toJson(Object obj) {
        return JSON.toJson(obj);
    }

    private static class ListParameterizedType implements ParameterizedType {

        private Type type;

        private ListParameterizedType(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }

    }

}
