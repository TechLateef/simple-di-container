package mine.framework.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import mine.framework.utils.JsonUtil;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;

public class HttpServerWrapper {
    private final Router router;

    public HttpServerWrapper(Router router) {
        this.router = router;
    }

    public void start(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        // Handle all requests with one generic handler
        server.createContext("/", new GenericHandler());

        server.setExecutor(null); // default executor
        server.start();
        System.out.println("🚀 Server started on http://localhost:" + port);
    }

    private class GenericHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) {
            String path = exchange.getRequestURI().getPath();
            String methodType = exchange.getRequestMethod(); // "GET", "POST"

            try {
                Method controllerMethod = router.getHandler(methodType, path);

                if (controllerMethod == null) {
                    sendResponse(exchange, 404, "Route not found: " + methodType + " " + path);
                    return;
                }

                Object controller = router.getControllerInstance(controllerMethod);
                // 🆕 Support request body for POST
                Object result;
                if ("POST".equalsIgnoreCase(methodType) && controllerMethod.getParameterCount() == 1) {
                    // Read the body
                    String body = new String(exchange.getRequestBody().readAllBytes());
                 Class<?> paramType= controllerMethod.getParameterTypes()[0];
                 Object deserializeInput = JsonUtil.fromJson(body, paramType);
                 result = controllerMethod.invoke(controller, deserializeInput);
                } else {
                    //No parameters (GET or no-body POST)
                    result = controllerMethod.invoke(controller);
                }
                String responseJson = JsonUtil.toJson(result);
                sendResponse(exchange, 200, responseJson);

            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "Internal Server Error");
            }
        }

        private void sendResponse(HttpExchange exchange, int statusCode, String responseText) {
            try {
                exchange.sendResponseHeaders(statusCode, responseText.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(responseText.getBytes());
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
