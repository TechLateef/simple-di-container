package mine.app;

import mine.Inject;
import mine.framework.annotations.Controller;
import mine.framework.annotations.Get;
import mine.framework.annotations.Post;

@Controller
public class UserController {

    @Inject
    private UserService userService;

    @Get("/hello")
    public String sayHello() {
        return "Hello from GET!";
    }

    @Post("/hello")
    public String postHello(String body) {
        return "POSTED: " + body;
    }
}
