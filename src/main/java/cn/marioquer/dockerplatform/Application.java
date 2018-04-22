package cn.marioquer.dockerplatform;
/**
 * Created by marioquer on 2018/4/5.
 */

import cn.marioquer.dockerplatform.entity.User;
import cn.marioquer.dockerplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
//@controller
//@EnableAutoConfiguration
//@ComponentScan
@RestController
public class Application {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        User user = userService.queryUserById(1);
        return user.getUsername();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}