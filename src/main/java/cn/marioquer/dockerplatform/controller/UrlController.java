package cn.marioquer.dockerplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by marioquer on 2018/4/7.
 */
@RequestMapping("/page")
@Controller
public class UrlController {

    @GetMapping("/auth")
    public String authPage() {
        return "auth";
    }

    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard/dashboard";
    }

    @GetMapping("/serverList")
    public String serverListPage() {
        return "server/server-list";
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
