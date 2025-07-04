package Custom;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class Processor {

    private void checkIfSerializable(Object object) {
        if(Objects.isNull(object)) {
            throw new IllegalArgumentException("The object to serialize is null");
        }

        Class<?> clazz = object.getClass();
        if(!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new IllegalArgumentException("The class " + clazz.getSimpleName() + " is not annotated with Custom.JsonSerializable");
        }
    }


    private void initializeObject(Object object) throws Exception{
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if(method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                method.invoke(object);
            }
        }
    }

    private String getJsonString(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        Map<String, String> jsonElementsMap = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(JsonElement.class)) {
                jsonElementsMap.put(field.getName(), (String) field.get(object));
            }
        }
        String jsonString = jsonElementsMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                    + entry.getValue() + "\"" )
                .collect(Collectors.joining(","));
        return  "{" + jsonString + "}";
    }

    public String convertToJson(Object object) throws Exception {
        try{
            checkIfSerializable(object);
            initializeObject(object);
            return getJsonString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
