package com.bosh.utils;

import java.io.IOException;

import com.bosh.entity.QuarkResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author wangmt
 *
 */
public class JsonUtils {
    // ����jackson����
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * ��jsonת��ΪQuarkResult
     * @param json
     * @param clazz
     * @return
     */
    public static QuarkResult jsonToQuarkResult(String json, Class<?> clazz) {

        try {
            if (clazz == null) {
                return MAPPER.readValue(json, QuarkResult.class);
            }

            JsonNode jsonNode = MAPPER.readTree(json);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }

            return new QuarkResult(jsonNode.get("status").intValue(), obj, jsonNode.get("error").asText());
        } catch (IOException e) {
            return null;
        }

    }


}
