package com.dawnbit.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@SuppressWarnings({"rawtypes", "unchecked"})
public class JsonUtils {

    public static <T> T stringToJson(final String json, final Class<T> typeOfT) {
        return new Gson().fromJson(json, typeOfT);
    }

    public static Object stringToJson(final String json, final String type) {
        try {
            final Class typeClass = Class.forName(type);
            return new Gson().fromJson(json, typeClass);
        } catch (final ClassNotFoundException e) {
            throw new IllegalArgumentException("invalid type:" + type);
        }
    }

    @SuppressWarnings("deprecation")
    public static Object stringToJson(final String json) {
        return new JsonParser().parse(json);
    }

    public static String objectToString(final Object jsonObject) {
        return new Gson().toJson(jsonObject);
    }

    public static String getAsString(final JsonObject jsonObject, final String memberName) {
        final JsonElement element = jsonObject.get(memberName);
        return element == null || element.isJsonNull() ? null : element.getAsString();
    }
}
