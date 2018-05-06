package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.VO.ServerVO;
import cn.marioquer.dockerplatform.dao.ServerDao;
import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.utils.SSHHelper;
import cn.marioquer.dockerplatform.utils.SSHInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServerServiceImpl implements ServerService {

    @Autowired
    ServerDao serverDao;

    @Override
    public List<ServerVO> getServerList(int ownerId) {
        List<ServerVO> serverVOs = new ArrayList<>();
        List<ServerEntity> serverEntities = serverDao.findByOwnerId(ownerId);
        for (ServerEntity entity : serverEntities) {
            ServerVO serverVO = new ServerVO();
            serverVO.setId(entity.getId());
            serverVO.setOwnerId(entity.getOwnerId());
            serverVO.setName(entity.getName());
            serverVO.setIp(entity.getIp());
            serverVO.setPlatform(entity.getPlatform());
            serverVO.setDockerVersion(entity.getDockerVersion());
            serverVO.setCpu(entity.getDockerVersion());
            serverVO.setMemory(entity.getMemory());
            serverVO.setSwarmId(entity.getSwarmId());
            serverVO.setSwarmRole(entity.getSwarmRole());
            serverVOs.add(serverVO);
        }
        return serverVOs;
    }

    @Override
    public boolean installServer(int ownerId, String name, String ip, String uname, String password) {
        boolean result = false;
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(ip, uname, password, 22));
        sshHelper.connect();
//        sshHelper.execFromFile("/shellfile/initialize_server.sh");
        String[] dockerInfo = sshHelper.exec("docker info").split("\\n|\\r\\n|\\r");
        if (dockerInfo[0].startsWith("Container")) {
            result = true;
            String platform = "", docker_version = "", cpu = "", memory = "";
            for (String s : dockerInfo) {
                if (s.startsWith("Operating System")) {
                    platform = s.split(": ")[1];
                } else if (s.startsWith("Server Version")) {
                    docker_version = s.split(": ")[1];
                } else if (s.startsWith("CPUs")) {
                    cpu = s.split(": ")[1];
                } else if (s.startsWith("Total Memory")) {
                    memory = s.split(": ")[1];
                }
            }
            ServerEntity serverEntity = new ServerEntity(ownerId, name, ip, platform, docker_version, cpu, memory, uname, password);
            serverDao.save(serverEntity);
        }
        sshHelper.close();
        return result;
    }
}
