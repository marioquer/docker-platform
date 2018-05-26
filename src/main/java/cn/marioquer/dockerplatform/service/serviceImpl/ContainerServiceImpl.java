package cn.marioquer.dockerplatform.service.serviceImpl;

import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.service.ContainerService;
import cn.marioquer.dockerplatform.service.ServerService;
import cn.marioquer.dockerplatform.utils.SSHHelper;
import cn.marioquer.dockerplatform.utils.SSHInfo;
import cn.marioquer.dockerplatform.utils.enums.ErrorMessage;
import cn.marioquer.dockerplatform.utils.enums.ShellScript;
import cn.marioquer.dockerplatform.utils.enums.UtilString;
import cn.marioquer.dockerplatform.vo.ContainerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContainerServiceImpl implements ContainerService {
    @Autowired
    ServerService serverService;

    @Override
    public List<ContainerVO> getContainerList(int serverId) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(server.getIp(), server.getUname(), server.getPassword()));
        sshHelper.connect();
        String returnStr = sshHelper.exec(ShellScript.CONTAINER_LS_A);
        String[] containerInfo = returnStr.split(UtilString.REG_PATTERN_RETURN);
        List<ContainerVO> containerList = new ArrayList<>();
        for (String line : containerInfo) {
            String[] infoDetail = line.split(UtilString.REG_PATTERN_N_SPACE);
            if (line.startsWith("CONTAINER")) {
                continue;
            } else if (infoDetail.length >= 6) {
                ContainerVO containerVO = new ContainerVO();
                containerVO.setId(infoDetail[0]);
                containerVO.setImage(infoDetail[1].contains(":") ? infoDetail[1] : (infoDetail[1] + ":latest"));
                containerVO.setCreated(infoDetail[3]);
                containerVO.setStatus(infoDetail[4]);
                if (infoDetail.length == 6) {
                    containerVO.setPort("-");
                    containerVO.setName(infoDetail[5]);
                } else {
                    if (infoDetail[5].contains(":"))
                        containerVO.setPort(infoDetail[5].split(":")[1]);
                    else
                        containerVO.setPort(infoDetail[5]);
                    containerVO.setName(infoDetail[6]);
                }
                containerList.add(containerVO);
            }
        }
        return containerList;
    }

    @Override
    public String deployContainer(String repository, String tag, String ports, int serverId) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(server.getIp(), server.getUname(), server.getPassword()));
        sshHelper.connect();
        String script = ShellScript.DOCKER_RUN + " -d";
        //部署端口
        if (!ports.equals("")) {
            script = script + " -p " + ports;
        }
        //部署仓库特定版本
        script = script + " " + repository;
        if (!tag.equals("")) {
            script = script + ":" + tag;
        }
        String returnStr = sshHelper.exec(script);

        System.out.println(script);
        System.out.println(returnStr);

        //输出流是断续的？？？？？？？？？？？待debug

        sshHelper.close();

        String[] deployInfo = returnStr.split(UtilString.REG_PATTERN_RETURN);
        if (deployInfo[deployInfo.length - 1].length() == 64) { //有无镜像都是这样
            return ErrorMessage.SUCCESS;
        } else if (deployInfo[1].contains("port is already allocated")) {
            System.out.println(deployInfo[1]);
            sshHelper.exec(ShellScript.CONTAINER_RM + deployInfo[0].substring(0, 12));
            return ErrorMessage.PORT_UNAVAILABLE;
        } else if (deployInfo[1].contains("pull access denied")) {
            return ErrorMessage.NOT_EXISTS;
        } else {
            return ErrorMessage.FAIL;
        }
    }

    /**
     * 从dockerfile部署容器
     *
     * @param dockerfileUrl
     * @param serverId
     * @return
     */
    @Override
    public String deployContainerByDockerfile(String dockerfileUrl, int serverId) {
        return null;
    }

    /**
     * 从Compose文件部署容器
     *
     * @param composeFileUrl
     * @param serverId
     * @return
     */
    @Override
    public String deployContainerByCompose(String composeFileUrl, int serverId) {
        ServerEntity server = serverService.getServerEntity(serverId).get();
        SSHHelper sshHelper = new SSHHelper(new SSHInfo(server.getIp(), server.getUname(), server.getPassword()));
        sshHelper.connect();
        if (composeFileUrl.endsWith("docker-compose.yml")) {
            composeFileUrl = composeFileUrl.substring(0, composeFileUrl.length() - 19);
        }
        //进入compose file所在目录
        sshHelper.exec("cd " + composeFileUrl);
        //执行up
        String[] composeInfo = sshHelper.exec(ShellScript.COMPOSE_UP_BG).split(UtilString.REG_PATTERN_RETURN);

        //这里由于没试完全全部的可能性分支，因而只加了失败和成功的检查，成功的检查也不完全。
        if (composeInfo[composeInfo.length - 1].contains("done")) {
            return ErrorMessage.SUCCESS;
        } else {
            return ErrorMessage.FAIL;
        }
    }
}
