package org.bluebridge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author ronin
 */
public class JsonUtils {

    /**
	 * json转换成对象
	 * @param:传入对象，json字符串
	 * @return:Object
	 */
    public static Object jsonToObject(Object obj,String jsonStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonStr, obj.getClass());
    }
    /**
     * 对象转换成json
     * @param:传入对象
     * @return:json字符串
     */
    public static String objectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

}
