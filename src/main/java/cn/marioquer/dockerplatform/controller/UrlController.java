package cn.marioquer.dockerplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by marioquer on 2018/4/7.
 */
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

    @GetMapping("/node/nodelist")
    public String nodeListPage() {
        return "node/node-list";
    }

    @GetMapping("/node/detail/container")
    public String containerPage() {
        return "node/detail/container";
    }

    @GetMapping("/node/detail/image")
    public String imagePage() {
        return "node/detail/image";
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
