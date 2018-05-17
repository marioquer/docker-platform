package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by marioquer on 2018/4/7.
 */
@RequestMapping("/page")
@Controller
public class UrlController {
    @Autowired
    ServerService serverService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/index/dashboard")
    public ModelAndView dashboardPage() {
        ModelAndView dashboardView = new ModelAndView("dashboard/dashboard");
        return dashboardView;
    }

    @GetMapping("/server/serverList")
    public ModelAndView serverListPage(HttpSession session) {
        ModelAndView serverlistView = new ModelAndView("server/server-list");
        UserLoginVO userLoginVO = (UserLoginVO) session.getAttribute("User");
        serverlistView.addObject("serverList", serverService.getServerList(userLoginVO.getId()));
        return serverlistView;
    }

    @GetMapping("/server/container")
    public String containerPage() {
        return "server/detail/container";
    }

    @GetMapping("/server/image")
    public String imagePage() {
        return "server/detail/image";
    }

    @GetMapping("/swarm/overview")
    public String swarmOverviewPage() {
        return "swarm/overview";
    }

    @GetMapping("/swarm/service")
    public String servicePage() {
        return "swarm/service";
    }

    @GetMapping("/swarm/visualizer")
    public String swarmVisualizerPage() {
        return "swarm/visualizer";
    }

    @GetMapping("/swarm/stack")
    public String swarmStackPage() {
        return "swarm/stack";
    }
}
