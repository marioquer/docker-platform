package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.VO.ServerVO;

import java.util.List;

public interface ServerService {
    public List<ServerVO> getServerList(int ownerId);

    public boolean installServer(int ownerId, String name, String ip, String uname, String password);
}
