package cn.marioquer.dockerplatform.dao;

import cn.marioquer.dockerplatform.entity.ContainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerDao extends JpaRepository<ContainerEntity, Integer> {
}
