package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.service.ContainerService;
import cn.marioquer.dockerplatform.service.ImageService;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.vo.ContainerVO;
import cn.marioquer.dockerplatform.vo.ImageVO;
import cn.marioquer.dockerplatform.vo.ServerVO;
import cn.marioquer.dockerplatform.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marioquer on 2018/4/7.
 */
@RequestMapping("/page")
@Controller
public class UrlController {
    @Autowired
    ServerService serverService;
    @Autowired
    ContainerService containerService;
    @Autowired
    ImageService imageService;

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
    public ModelAndView containerPage(@RequestParam("serverId") int serverId) {
        ModelAndView containerView = new ModelAndView("server/detail/container");
        List<ContainerVO> vos = containerService.getContainerList(serverId);
        containerView.addObject("containerList", vos);
        containerView.addObject("serverId", serverId);
        return containerView;
    }

    @GetMapping("/server/image")
    public ModelAndView imagePage(@RequestParam("serverId") int serverId) {
        ModelAndView imageView = new ModelAndView("server/detail/image");
        List<ImageVO> vos = imageService.getImageList(serverId);
        imageView.addObject("imageList", vos);
        imageView.addObject("serverId", serverId);
        return imageView;
    }

    @GetMapping("/swarm/overview")
    public ModelAndView swarmOverviewPage(HttpSession session) {
        ModelAndView mv = new ModelAndView("swarm/overview");
        UserLoginVO userLoginVO = (UserLoginVO) session.getAttribute("User");

        List<ServerVO> serverAllVOS = serverService.getServerList(userLoginVO.getId());
        System.out.println(serverAllVOS.size());
        List<ServerVO> serverSwarmVOS = new ArrayList<>();
        for (ServerVO server : serverAllVOS) {
            if (server.getSwarmRole() != null) {
                switch (server.getSwarmRole()) {
                    case "head_manager":
                    case "manager":
                    case "worker":
                        serverSwarmVOS.add(server);
                        break;
                }
            }
        }
        mv.addObject("swarmServerList", serverSwarmVOS);
        mv.addObject("allServerList", serverAllVOS);
        return mv;
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
