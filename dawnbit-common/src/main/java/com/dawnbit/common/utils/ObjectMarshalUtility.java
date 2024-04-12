package com.dawnbit.common.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMarshalUtility {

    /**
     * convert java object class to map
     *
     * @param <T>
     * @param obj
     * @return
     */
    public static <T> Map<?, ?> marshalObject(final T obj) {
        final ObjectMapper oMapper = new ObjectMapper();
        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return oMapper.convertValue(obj, Map.class);
    }

    /**
     * convert map to class
     *
     * @param <T>
     * @param classType
     * @param map
     * @return
     */
    public static <T> T unMarshalObject(final Class<T> classType, final Map<?, ?> map) {
        final ObjectMapper oMapper = new ObjectMapper();
        oMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return oMapper.convertValue(map, classType);
    }

    /**
     * convert java object class to JSON String
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String marshalObjectToJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper oMapper = new ObjectMapper();
        return oMapper.writeValueAsString(obj);
    }
}
