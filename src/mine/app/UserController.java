package mine.app;

import mine.Inject;
import mine.app.dto.HelloDto;
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
    public HelloDto postHello(HelloDto dto) {
        dto.setName("Hello " + dto.getName());
        dto.setAge(dto.getAge() + 1);
        return dto;
    }


}
