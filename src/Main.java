import mine.MyInjector;
import mine.ServiceA;
import mine.framework.server.HttpServerWrapper;
import mine.framework.server.Router;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            Router router = new Router("mine.app");
            HttpServerWrapper server = new HttpServerWrapper(router);
            server.start(8080);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server failed to start.");
        }
    }

}