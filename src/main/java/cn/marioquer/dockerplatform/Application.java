package cn.marioquer.dockerplatform;
/**
 * Created by marioquer on 2018/4/5.
 */

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
//@Controller
//@EnableAutoConfiguration
//@ComponentScan
public class Application {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "index";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}