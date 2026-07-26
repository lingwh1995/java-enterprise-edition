package org.bluebridge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON 工具类
 *
 * @author lingwh
 * @date 2019/11/14 10:59
 */
public class JsonUtils {

    /**
     * son 转换成对象
     *
     * @param obj
     * @param jsonStr
     * @return
     * @throws IOException
     */
    public static Object jsonToObject(Object obj,String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, obj.getClass());
    }

    /**
     * 对象转换成 json
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String objectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
