package mine;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyInjector {
    private final Map<Class<?>, Object> componentMap = new HashMap<>();

    public MyInjector(String basePackage) {
        // 1. Scan for all classes with @Component
        Reflections reflections = new Reflections(basePackage);

        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);

        for(Class<?> clazz: components) {
            System.out.println("Found component: " + clazz.getName());
            // 2. Instantiate them
            try{
                Object instance = clazz.getDeclaredConstructor().newInstance();
                componentMap.put(clazz, instance);

                System.out.println("Create instance: " + instance);

            } catch (Exception e) {
                System.err.println("Failed to instantiate " + clazz.getName());
                e.printStackTrace();
            }
        }



        // 3. Inject fields with @Inject
        for(Object object: componentMap.values()) {
            injectDependencies(object);
        }
    }

    private void injectDependencies(Object object) {
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Inject.class)) {
                try {
                    Class<?> fieldType = field.getType();
                    Object dependency = componentMap.get(fieldType);
                    if (dependency == null) {
                        throw new RuntimeException("No component found for type: " + fieldType.getName());

                    }
                    field.setAccessible(true);
                    field.set(object, dependency);
                    System.out.println("Injected " + fieldType.getSimpleName() +
                            " into " + object.getClass().getSimpleName());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to inject dependency", e);

                }
            }
        }
    }
    public <T> T getObject(Class<T> clazz) {
        return clazz.cast(componentMap.get(clazz));
    }
}
