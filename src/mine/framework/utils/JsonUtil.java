package mine.framework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T fromJson(String json, Class<T> clazz)throws Exception{
        return objectMapper.readValue(json, clazz);
    }
    public static String toJson(Object object) throws Exception{
        return objectMapper.writeValueAsString(object);
    }
}
