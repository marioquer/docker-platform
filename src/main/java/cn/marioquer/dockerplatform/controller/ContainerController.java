package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.service.ContainerService;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.vo.ContainerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContainerController {
    @Autowired
    ContainerService containerService;
    @Autowired
    ServerService serverService;

    @PostMapping("/deployContainer")
    public String deployContainer(String rep, String tag, String port, int ownerId, int serverId) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        if (server.getOwnerId() == ownerId) {
            return containerService.deployContainer(rep, tag, port, serverId);
        } else {
            return "access_denied";
        }
    }

    /**
     *
     * @param serverId 数据库中的serverId
     * @return ContainerVO的List，前端获取成JSON
     */
    @PostMapping("getContainerList")
    public List<ContainerVO> getContainerList(int serverId){
        return containerService.getContainerList(serverId);
    }
}
