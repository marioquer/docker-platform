package cn.marioquer.dockerplatform.controller;

import cn.marioquer.dockerplatform.dao.ServerDao;
import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.utils.SSHHelper;
import cn.marioquer.dockerplatform.utils.SSHInfo;
import cn.marioquer.dockerplatform.utils.enums.ErrorMessage;
import cn.marioquer.dockerplatform.utils.enums.ShellScript;
import cn.marioquer.dockerplatform.utils.enums.UtilString;
import cn.marioquer.dockerplatform.vo.ServerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SwarmController {
    @Autowired
    ServerService serverService;
    @Autowired
    ServerDao serverDao;


    @PostMapping("/addSwarmServer")
    public String deployContainer(String role, String serverName, int ownerId) {
        ServerEntity toJoinServer = serverDao.findByName(serverName);
        if (toJoinServer == null) {
            return ErrorMessage.NOT_FOUND;
        } else if (toJoinServer.getSwarmRole() != null) {
            return ErrorMessage.EXISTS;
        } else {
            List<ServerVO> serverVOS = serverService.getServerList(ownerId);
            for (ServerVO servervo : serverVOS) {
                //短路求值避开null情况
                if (servervo.getSwarmRole() != null && servervo.getSwarmRole().equals("head_manager")) {
                    //head manager get token
                    ServerEntity headServer = serverService.getServerEntity(servervo.getId()).get();
                    SSHHelper sshHelper = new SSHHelper(new SSHInfo(headServer.getIp(), headServer.getUname(), headServer.getPassword()));
                    sshHelper.connect();
                    String returnStr = sshHelper.exec(role.equals("manager") ? ShellScript.SWARM_MANAGER_JOIN : ShellScript.SWARM_WORKER_JOIN);
                    String[] tokenInfo = returnStr.split(UtilString.REG_PATTERN_RETURN);
                    String joinScript = tokenInfo[2].trim();
                    sshHelper.close();
                    //join
                    sshHelper = new SSHHelper(new SSHInfo(toJoinServer.getIp(), toJoinServer.getUname(), toJoinServer.getPassword()));
                    sshHelper.connect();
                    String joinReturnStr = sshHelper.exec(joinScript);
                    if (joinReturnStr.contains("This node joined a swarm as a manager.")) {
                        return ErrorMessage.SUCCESS;
                    } else {
                        //此处的情况判断时间原因不完善
                        return ErrorMessage.FAIL;
                    }
                }
            }
        }
        return ErrorMessage.FAIL;
    }
}
