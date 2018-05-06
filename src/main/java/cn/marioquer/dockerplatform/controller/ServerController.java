package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.VO.ServerVO;
import cn.marioquer.dockerplatform.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ServerController {

    @Autowired
    ServerService serverService;

    @PostMapping("/getServerList")
    public List<ServerVO> getServerList(int ownerId) {
        return serverService.getServerList(ownerId);
    }

    @PostMapping("/addServer")
    public String addServer(int ownerId, String name, String ip, String uname, String password) {
        if (serverService.installServer(ownerId, name, ip, uname, password))
            return "success";
        else
            return "fail";
    }
}
