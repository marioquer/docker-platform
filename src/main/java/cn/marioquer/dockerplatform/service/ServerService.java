package cn.marioquer.dockerplatform.service;

import cn.marioquer.dockerplatform.entity.ServerEntity;
import cn.marioquer.dockerplatform.vo.ServerVO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ServerService {
    public List<ServerVO> getServerList(int ownerId);

    public Optional<ServerEntity> getServerEntity(int serverId);

    public String installServer(int ownerId, String name, String ip, String uname, String password);
}
