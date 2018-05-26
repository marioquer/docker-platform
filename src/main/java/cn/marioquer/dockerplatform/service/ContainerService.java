package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.vo.ContainerVO;

import java.util.List;

public interface ContainerService {
    public List<ContainerVO> getContainerList(int serverId);

    public String deployContainer(String repository, String tag, String ports, int serverId);

    public String deployContainerByDockerfile(String dockerfileUrl, int serverId);

    public String deployContainerByCompose(String composeFileUrl, int serverId);
}
