package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.SwarmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SwarmDao extends JpaRepository<SwarmEntity, String> {
}
