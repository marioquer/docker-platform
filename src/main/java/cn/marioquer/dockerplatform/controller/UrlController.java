package cn.marioquer.dockerplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by marioquer on 2018/4/7.
 */
@Controller
public class UrlController {


    @GetMapping("/hello")
    public String hom2() {
        System.out.println("hello");
        return "hello";
    }
}
