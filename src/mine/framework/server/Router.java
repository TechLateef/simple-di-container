package mine.framework.server;

import mine.MyInjector;
import mine.framework.annotations.Controller;
import mine.framework.annotations.Get;
import mine.framework.annotations.Post;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

public class Router {
    private final Map<String, Method> routeMap = new HashMap<>();
    private final Map<Method, Object> controllerInstances = new HashMap<>();
    private  final MyInjector injector;

    public Router(String basePackage) {
        this.injector = new MyInjector(basePackage);
        // Scan for all classes with @Controller
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);

        for (Class<?> controllerClass : controllers) {
            //Instantiate them
            try {
                Object instance = controllerClass.getDeclaredConstructor().newInstance();

                // Scan all methods with @Get and @Post
                for (Method method : controllerClass.getDeclaredMethods()) {
                    // if its Get add it to routeMap
                    if (method.isAnnotationPresent(Get.class)) {
                        String path = method.getAnnotation(Get.class).value();
                        routeMap.put("GET " + path, method);
                        controllerInstances.put(method, instance);
                        System.out.println("Registered GET " + path + " -> " + method.getName());
                    }

                    if (method.isAnnotationPresent(Post.class)) {
                        String path = method.getAnnotation(Post.class).value();
                        routeMap.put("POST " + path, method);
                        controllerInstances.put(method, instance);
                        System.out.println("Registered POST " + path + " -> " + method.getName());
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate controller: " + controllerClass.getName(), e);
            }
        }
    }

    public Method getHandler(String method, String path) {
        return routeMap.get(method + " " + path);
    }

    public Object getControllerInstance(Method handlerMethod) {
        return controllerInstances.get(handlerMethod);
    }
}
