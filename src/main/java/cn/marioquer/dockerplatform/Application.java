package cn.marioquer.dockerplatform;
/**
 * Created by marioquer on 2018/4/5.
 */

import cn.marioquer.dockerplatform.dao.UserDao;
import cn.marioquer.dockerplatform.entity.UserEntity;
import cn.marioquer.dockerplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
//@controller
//@EnableAutoConfiguration
//@ComponentScan
@RestController
public class Application {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @GetMapping("/user")
    List<UserEntity> home() {
        List<UserEntity> result = userDao.findByUsername("d");

//        System.out.println(user.getUsername());
//        UserEntity user = new UserEntity();
//        user.setId(1);
//        user.setUsername("aa");
//        user.setPassword("aadfsdf");
        return result;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}