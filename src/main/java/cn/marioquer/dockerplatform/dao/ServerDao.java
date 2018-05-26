package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.ServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerDao extends JpaRepository<ServerEntity, Integer> {
    public List<ServerEntity> findByOwnerId(int ownerId);
    public ServerEntity findByIp(String ip);
    public ServerEntity findByName(String name);
}
