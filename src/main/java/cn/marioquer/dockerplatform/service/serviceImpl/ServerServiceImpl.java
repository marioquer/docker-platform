package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.utils.enums.ErrorMessage;
import cn.marioquer.dockerplatform.utils.enums.ShellScript;
import cn.marioquer.dockerplatform.vo.ServerVO;
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

    private static String RETURN_REG_PATTERN = "\\n|\\r\\n|\\r";

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
            serverVO.setCpu(entity.getCpu());
            serverVO.setMemory(entity.getMemory());
            serverVO.setSwarmId(entity.getSwarmId());
            serverVO.setSwarmRole(entity.getSwarmRole());
            serverVOs.add(serverVO);
        }
        return serverVOs;
    }

    @Override
    public String installServer(int ownerId, String name, String ip, String uname, String password) {
        String result = ErrorMessage.FAIL;
        //已有服务器
        if (serverDao.findByIp(ip) != null) {
            return ErrorMessage.EXISTS;
        }
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(ip, uname, password));
        //密钥错误
        if (sshHelper.connect() == -1) {
            return ErrorMessage.WRG_PSW;
        }
        String[] dockerInfo = sshHelper.exec(ShellScript.DOCKER_INFO).split(RETURN_REG_PATTERN);
        if (existsDocker(dockerInfo)) {
            saveServerEntity(dockerInfo, ownerId, name, ip, uname, password);
            result = ErrorMessage.SUCCESS;
        } else {
            sshHelper.execFromFile("shellfile/initialize_server.sh");
            if (existsDocker(sshHelper.exec(ShellScript.DOCKER_INFO).split(RETURN_REG_PATTERN))) {
                saveServerEntity(dockerInfo, ownerId, name, ip, uname, password);
                result = ErrorMessage.SUCCESS;
            }
        }
        sshHelper.close();
        return result;
    }

    public boolean existsDocker(String[] dockerInfo) {
        if (dockerInfo[0].startsWith("Container")) {
            return true;
        } else {
            return false;
        }
    }

    public void saveServerEntity(String[] dockerInfo, int ownerId, String name, String ip, String uname, String password) {
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
        serverDao.saveAndFlush(serverEntity);
    }
}
