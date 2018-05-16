package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.vo.UserLoginVO;
import cn.marioquer.dockerplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * @param username
     * @param password
     * @param request
     * @return success / not_found / wrong_password
     */
    @PostMapping(value = "/login")
    public UserLoginVO login(String username, String password, HttpServletRequest request) {
        UserLoginVO user = userService.login(username, password);
        if (user.getLogin_status().equalsIgnoreCase("success")) {
            if (request.getSession(false) != null)
                request.getSession(false).invalidate();
            request.getSession(true);
            request.getSession().setAttribute("User", user);
        }
        return user;
    }

    /**
     * @param username
     * @param password
     * @return success / exists
     */
    @PostMapping(value = "/signup")
    public String signUp(String username, String password) {
        return userService.signUp(username, password);
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("User");
        request.getSession(false).invalidate();
        return "/page/login";
    }
}
